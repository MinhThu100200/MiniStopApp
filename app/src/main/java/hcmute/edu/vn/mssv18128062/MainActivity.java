package hcmute.edu.vn.mssv18128062;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    Button login;
    TextView txtSignUp;
    TextView txtFogotPass;
    ImageView ch;
    public static Database db;
    SharedPreferences sharedPreferencesUser;
    SharedPreferences sharedPreferencesCart;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        db = new Database(this);

        db.QueryData("CREATE TABLE IF NOT EXISTS USERS(ID INTEGER PRIMARY KEY AUTOINCREMENT, USERNAME TEXT, " +
                "PASSWORD TEXT, EMAIL TEXT, NAME TEXT, PHONE TEXT, POINT INTEGER, PICTURE BLOB)");
        //db.QueryData("INSERT INTO USER VALUES(NULL, 'mint', '12345', '123456'," + 1 +")");
        db.QueryData("CREATE TABLE IF NOT EXISTS CATEGORY( ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, IMAGE_ID INTEGER )");
        db.QueryData("CREATE TABLE IF NOT EXISTS ADDRESS( ID INTEGER PRIMARY KEY AUTOINCREMENT, DESCRIPTION TEXT, IMAGE_ID INTEGER)");
        db.QueryData("CREATE TABLE IF NOT EXISTS PRODUCT( ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, PRICE FLOAT, DESCRIPTION TEXT, " +
                "ID_CATEGORY INTEGER, IMAGE_ID INTEGER)");
        db.QueryData("CREATE TABLE IF NOT EXISTS RATE( ID INTEGER PRIMARY KEY AUTOINCREMENT, ID_PRODUCT INTEGER, NAME TEXT, DATE_RATING TEXT, " +
                "RATING FLOAT, CMT TEXT)");
        db.QueryData("CREATE TABLE IF NOT EXISTS BOOKED( ID INTEGER PRIMARY KEY AUTOINCREMENT, DATE_ORDER TEXT, STATUS INTEGER, ADDRESS TEXT, TOTAL FLOAT)");
        db.QueryData("CREATE TABLE IF NOT EXISTS ORDERS( ID INTEGER PRIMARY KEY AUTOINCREMENT, ID_PRODUCT INTEGER, PRICE FLOAT, AMOUNT_PRODUCT INTEGER, " +
             "ID_BOOKED INTEGER)");

        db.QueryData("CREATE TABLE IF NOT EXISTS NOTIFICATION( ID, INFOMATION, IMAGE_ID)");



        db.insertAddress("2/5 đường 68 phường Hiệp Phú Quận 9 tp.Thủ Đức", R.drawable.ch1);
        db.insertAddress("33 Lê Văn Việt phường Hiệp Phú quận 9 tp.Thủ Đức", R.drawable.ch2);
        db.insertAddress("90 Võ Văn Ngân phường Hiệp Phú quận Thủ Đức tp.Thủ Đức", R.drawable.ch3);
        db.insertAddress("88 Nguyễn Tri Phương phường 3 quận 6 tp.Trà Vinh", R.drawable.ch4);
        db.insertAddress("12 Nguyễn Đình Chiểu phường 3 quận 6 tp.Trà Vinh", R.drawable.ch5);
         //1
        db.insertCategory("Mỳ cay", R.drawable.spicy);
        db.insertProduct("Mỳ cay hải sản", 40000, "Hương vị đê mê cho tín đồ mỳ cay, với độ cay xé lưỡi sẽ kích thích vị giác của bạn", 1, R.drawable.spicy);
         //2
        db.insertCategory("Đồ uống", R.drawable.drink);
        db.insertProduct("Nước cam", 30000, "Nước cam 100% cam tươi cung cấp vitamin C cho bạn khỏe mạnh", 2, R.drawable.organe);
        db.insertProduct("Nước cam cà rốt", 50000, "Nước trái cây mix, xịn khỏi chê luôn đó nha..Thử nhanh nào khách iu ơi!", 2, R.drawable.organ_carrot);
        //3
        db.insertCategory("Cơm", R.drawable.com_ga);
        db.insertProduct("Cơm gà", 45000, "Cơm gà được chế biến từ những con gà vườn thịt rắn chắc và mạnh mẽ, sẽ làm cho bạn ghiền đó", 3, R.drawable.com_ga);
        //4
        db.insertCategory("Kimbap", R.drawable.kimbap);
        db.insertProduct("Kimbap truyền thống", 30000, "Với những hạt cơm dẻo dai được cuộn trong miếng rong biển vô cùng thơm ngon", 4, R.drawable.kimbap);
        //5
        db.insertCategory("Salad", R.drawable.salad);
        db.insertProduct("Salad trái cây", 60000, "Một món ăn healthy biết bao nè bạn yêu ơi~", 5, R.drawable.salad);
        //6
        db.insertCategory("Đồ tây", R.drawable.sandwich);
        db.insertProduct("Sandwich", 30000, "Bắt đầu buổi sáng với một miếng sandwich nóng hỏi thì tuyệt vời lắm đó!", 6, R.drawable.sandwich);
        db.insertProduct("Mý ý", 60000, "Một đĩa mỳ ý khiến đôi uyên ương càng ưng ý ngại gì không thử nè~", 6, R.drawable.my_y);
        //7
        db.insertCategory("Chả chiên", R.drawable.cha_chien);
        db.insertProduct("Chả chiên", 30000, "Nóng hỏi vừa thổi vừa ăn nè~", 7, R.drawable.cha_chien);


        sharedPreferencesCart = getSharedPreferences("dataCart", MODE_PRIVATE);
        String query = "SELECT * FROM PRODUCT";
        int count = 0;
        Cursor allProduct = db.GetData(query);

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

        if (account != null) {
            String personName = account.getDisplayName();
            String personGivenName = account.getGivenName();
            String personFamilyName = account.getFamilyName();
            String personEmail = account.getEmail();
            String personId = account.getId();
            Uri personPhoto = account.getPhotoUrl();
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.camera);
            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArray);
            byte[] image = byteArray.toByteArray();
             sharedPreferencesUser = getSharedPreferences("dataLogin", MODE_PRIVATE);
             SharedPreferences.Editor editor = sharedPreferencesUser.edit();
             editor.putString("name", personName);
             editor.putString("email", personEmail);
            //editor.put
             editor.commit();
             int flag = 0;
            Cursor user = db.GetData("SELECT * FROM USERS");
            while(user.moveToNext()){
                if(user.getString(3).equals(personEmail))
                {
                   flag = 1;
                   break;

                }
            }
            if(flag == 0)
            {
                db.insertUser(new User("", "", personEmail, personName, "", 0, image));
            }

             Intent intent = new  Intent(getBaseContext(), HomePageActivity.class);
            //intent.putExtra("urip", personPhoto);
             startActivity(intent);
            //ch.setImageURI(personPhoto);
        }


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