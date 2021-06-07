package hcmute.edu.vn.mssv18128062;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    Button login;
    TextView txtSignUp;
    TextView txtFogotPass;
    public static Database db;
    ImageView ch;

    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ch = (ImageView)findViewById(R.id.ch);
        db = new Database(this);

        db.QueryData("CREATE TABLE IF NOT EXISTS USER(ID INTEGER PRIMARY KEY AUTOINCREMENT, USERNAME TEXT, " +
                "PASSWORD TEXT, EMAIL TEXT, ROLE INTEGER)");
        //db.QueryData("INSERT INTO USER VALUES(NULL, 'mint', '12345', '123456'," + 1 +")");
        db.QueryData("CREATE TABLE IF NOT EXISTS CATEGORY( ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, PICTURE BLOB)");
        db.QueryData("CREATE TABLE IF NOT EXISTS ADDRESS( ID INTEGER PRIMARY KEY AUTOINCREMENT, DESCRIPTION TEXT, PICTURE BLOB)");

        BitmapDrawable bitmapDrawable = (BitmapDrawable) ch.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArray);
        byte [] img = byteArray.toByteArray();

        //db.insertAddress("aaaaaaaaa", img);
        // Inserting users
        Log.d("Insert: ", "Inserting ..");
        //db.addUser(new User("Ravi", "1234", "12345", 1));
        //db.addUser(new User("Nam", "1234", "12345", 1));
        //userdao.insertUser("mint", "12345", "minhthuthum@gmail.com", 1);
        Log.d("Reading: ", "Reading all contacts..");
        //List<User> users = userdao.getAllUsers(db);

        //for (User user : users) {
            //String log = "Id: " + user.getID() + " ,Username: " + user.getUsername() + " ,Password: " +
                    //user.getPassword() + " ,Email: " + user.getEmail() + " ,Role: " + user.getRole();
            // Writing users to log
            //Log.d("Name: ", log);
        //}
        //Cursor cursor = db.GetData("select * from Address");
        //while (cursor.moveToNext())
        //{
          //  String name = cursor.getString(1);
            //int id = cursor.getInt(0);
            //Toast.makeText(this, "" + id, Toast.LENGTH_SHORT).show();
            //Log.d("id", ""+id);
        //}

        txtSignUp = (TextView) findViewById(R.id.txtSingUp);
        txtFogotPass = (TextView) findViewById(R.id.txtForgotPass);

        //yeu cau nguoi dung cung cap ten
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);

        // Set the dimensions of the sign-in button.
        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

       signInButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               switch (v.getId()) {
                   case R.id.sign_in_button:
                       signIn();
                       break;
                   // ...
               }
           }
       });

        //Sign Up
        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new  Intent(getBaseContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });

        //forgotpass
        txtFogotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new  Intent(getBaseContext(), ForgotPassActivity.class);
                startActivity(intent);
            }
        });
    }


    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Dang Nhap", "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 2);
    }
    private void updateUI(GoogleSignInAccount account) {
        Intent intent = new  Intent(getBaseContext(), HomePageActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == 2) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    public Bitmap getBitmap(String path) {
        Bitmap bitmap=null;
        try {
            File f= new File(path);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            bitmap = BitmapFactory.decodeStream(new FileInputStream(f), null, options);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap ;
    }
}