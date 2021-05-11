package hcmute.edu.vn.mssv18128062;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    Button login;
    TextView txtSignUp;
    TextView txtFogotPass;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHandler db = new DatabaseHandler(this);
        // Inserting users
        Log.d("Insert: ", "Inserting ..");
        //db.addUser(new User("Ravi", "1234", "12345", 1));
        //db.addUser(new User("Nam", "1234", "12345", 1));

        Log.d("Reading: ", "Reading all contacts..");
        List<User> users = db.getAllUsers();

        for (User user : users) {
            String log = "Id: " + user.getID() + " ,Username: " + user.getUsername() + " ,Password: " +
                    user.getPassword() + " ,Email: " + user.getEmail() + " ,Role: " + user.getRole();
            // Writing users to log
            Log.d("Name: ", log);
        }

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
}