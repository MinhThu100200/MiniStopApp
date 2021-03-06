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
import android.widget.EditText;
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
    EditText usernameLogin;
    EditText passwordLogin;
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

        db.QueryData("CREATE TABLE IF NOT EXISTS NOTIFICATION( ID INTEGER PRIMARY KEY AUTOINCREMENT, TITLE TEXT, INFOMATION TEXT, IMAGE_ID INTEGER)");


        //int idImg = R.drawable.welcome;
        //String des = "Ch??o m???ng t??nh y??u ???? ?????n ?????ng h??nh c??ng m??nh n??. Love u 3000 !!!";
        //db.insertNotification("Welcome!!!", des, idImg);
        //int idImg1 = R.drawable.info;
        //String info = "S???p t???i s??? c?? r???t nhi???u ch????ng tr??nh ??u ????i h???p d???n cho c??c kh??ch h??ng th??n y??u. C???a h??ng s??? th??ng b??o s???m nh???t ????? anh ch??? em m??nh tham gia ???. C???m ??n anh ch??? r???t nhi???u v?? ???? d???ng h??ng ?????n gi??? ph??t n??y ???";
        //db.insertNotification("S??? ki???n s???p t???i", info, idImg1);

        //db.insertAddress("2/5 ???????ng 68 ph?????ng Hi???p Ph?? Qu???n 9 tp.Th??? ?????c", R.drawable.ch1);
        //db.insertAddress("33 L?? V??n Vi???t ph?????ng Hi???p Ph?? qu???n 9 tp.Th??? ?????c", R.drawable.ch2);
        //db.insertAddress("90 V?? V??n Ng??n ph?????ng Hi???p Ph?? qu???n Th??? ?????c tp.Th??? ?????c", R.drawable.ch3);
        //db.insertAddress("88 Nguy???n Tri Ph????ng ph?????ng 3 qu???n 6 tp.Tr?? Vinh", R.drawable.ch4);
        //db.insertAddress("12 Nguy???n ????nh Chi???u ph?????ng 3 qu???n 6 tp.Tr?? Vinh", R.drawable.ch5);
         //1
        //db.insertCategory("M??? cay", R.drawable.spicy);
        //db.insertProduct("M??? cay h???i s???n", 40000, "H????ng v??? ???? m?? cho t??n ????? m??? cay, v???i ????? cay x?? l?????i s??? k??ch th??ch v??? gi??c c???a b???n", 1, R.drawable.spicy);
         //2
        //db.insertCategory("????? u???ng", R.drawable.drink);
        //db.insertProduct("N?????c cam", 30000, "N?????c cam 100% cam t????i cung c???p vitamin C cho b???n kh???e m???nh", 2, R.drawable.organe);
        //db.insertProduct("N?????c cam c?? r???t", 50000, "N?????c tr??i c??y mix, x???n kh???i ch?? lu??n ???? nha..Th??? nhanh n??o kh??ch iu ??i!", 2, R.drawable.organ_carrot);
        //db.insertProduct("N?????c ??p t??o", 45000, "N?????c ??p t??o, v???i 100% t??o nh???p t??? M??? s??? gi??p b???n c???m th???y s???ng kho??i ???? nhe!!!!!", 2, R.drawable.apple);
        //db.insertProduct("B???c s??u", 30000, "V???i nh???ng b???n mu???n u???ng c?? ph?? m?? s??? ?????ng th?? b???c s??u l?? m???t l???a ch???n th??ch h???p v?? tuy???t v???i.", 2, R.drawable.bacsiu);
        //db.insertProduct("Sinh t??? b??", 40000, "M???t ly sinh t??? b?? s??? gi??p b???n c???m th???y haizzz h??m nay ng???t v???y l?? ????? tuy???t c?? m??o lu??n n??!!", 2, R.drawable.bo);
        //db.insertProduct("N?????c ??p c?? chua", 35000, "N???u m???t ng??y bu???n b?? th???t t??nh thay v?? kh??c s??? l???a ch???n tuy???t v???i c???a b???n l?? m???t ly n?????c ??p c?? chua ????? m??nh s??ng m???t ra n??. ?????ng bu???n nhe!!!!", 2, R.drawable.cachua);

        //db.insertProduct("N?????c ??p d??a h???u", 45000, "N??i c??c b???n bi???t gi???ng d??a n??y m??nh l???y t??? d??a con ch??u c???a gi???ng d??a c???a Mai An Ti??m, ?????m b???o u???ng v?? nh??? lu??n l???ch s???!!!", 2, R.drawable.duahau);
        //db.insertProduct("Sinh t??? m??t", 45000, "N???u ??ang stress m??nh ngh?? l???a th??m t?? ???????ng cho cu???c s???ng l?? v?? c??ng h???p l?? v?? sinh t??? m??t r???t h??n h???nh ph???c v??? b???n iu n?? <3", 2, R.drawable.mit);

        //db.insertProduct("C?? ph??", 30000, "C?? ph?? h???c theo StartBuck d??nh cho m???y ????? ngh??o kh??? mu???n th??? n??!!", 2, R.drawable.soda);

        //3
        //db.insertCategory("C??m", R.drawable.com_ga);
        //db.insertProduct("C??m g??", 45000, "C??m g?? ???????c ch??? bi???n t??? nh???ng con g?? v?????n th???t r???n ch???c v?? m???nh m???, s??? l??m cho b???n ghi???n ????", 3, R.drawable.com_ga);
        //4
        //db.insertCategory("Kimbap", R.drawable.kimbap);
        //db.insertProduct("Kimbap truy???n th???ng", 30000, "V???i nh???ng h???t c??m d???o dai ???????c cu???n trong mi???ng rong bi???n v?? c??ng th??m ngon", 4, R.drawable.kimbap);
        //5
        //db.insertCategory("Salad", R.drawable.salad);
        //db.insertProduct("Salad tr??i c??y", 60000, "M???t m??n ??n healthy bi???t bao n?? b???n y??u ??i~", 5, R.drawable.salad);
        //6
        //db.insertCategory("????? t??y", R.drawable.sandwich);
        //db.insertProduct("Sandwich", 30000, "B???t ?????u bu???i s??ng v???i m???t mi???ng sandwich n??ng h???i th?? tuy???t v???i l???m ????!", 6, R.drawable.sandwich);
        //db.insertProduct("M?? ??", 60000, "M???t ????a m??? ?? khi???n ????i uy??n ????ng c??ng ??ng ?? ng???i g?? kh??ng th??? n??~", 6, R.drawable.my_y);
        //7
        //db.insertCategory("Ch??? chi??n", R.drawable.cha_chien);
        //db.insertProduct("Ch??? chi??n", 30000, "N??ng h???i v???a th???i v???a ??n n??~", 7, R.drawable.cha_chien);


        sharedPreferencesCart = getSharedPreferences("dataCart", MODE_PRIVATE);
        String query = "SELECT * FROM PRODUCT";
        int count = 0;
        Cursor allProduct = db.GetData(query);

        txtSignUp = (TextView) findViewById(R.id.txtSingUp);
        txtFogotPass = (TextView) findViewById(R.id.txtForgotPass);
        login = (Button)findViewById(R.id.btnLogin);
        usernameLogin = (EditText)findViewById(R.id.username);
        passwordLogin = (EditText)findViewById(R.id.password);
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
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameLogin.getText().toString().trim();
                String password = passwordLogin.getText().toString().trim();
                if(username.equals("") || password.equals("")){
                    Toast.makeText(getBaseContext(), "Nh???p t??n ????ng nh???p v?? password lu??n b???n iu <3 ??i!", Toast.LENGTH_SHORT).show();
                }
                else {
                    String query = "SELECT * FROM USERS";
                    Cursor cursor = db.GetData(query);
                    while (cursor.moveToNext()){
                        if(cursor.getString(1).equals(username) && cursor.getString(2).equals(password)){
                            Toast.makeText(getBaseContext(), "????ng nh???p th??nh c??ng r???i n??!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), HomePageActivity.class);
                            startActivity(intent);
                            sharedPreferencesUser = getSharedPreferences("dataLogin", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferencesUser.edit();
                            editor.putString("username", username);
                            editor.putString("password", password);
                            editor.putString("name", cursor.getString(4));
                            editor.putString("email", cursor.getString(3));
                            editor.putString("phone", cursor.getString(5));
                            editor.putInt("id", cursor.getInt(0));
                            editor.putInt("point", cursor.getInt(6));
                            editor.commit();
                            break;
                        }
                    }
                }
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

             int flag = 0;
            Cursor user = db.GetData("SELECT * FROM USERS");
            while(user.moveToNext()){
                if(user.getString(3).equals(personEmail))
                {
                   flag = 1;
                    editor.putString("username", user.getString(1));
                    editor.putString("password", user.getString(2));
                    editor.putString("name", user.getString(4));
                    editor.putString("email", user.getString(3));
                    editor.putString("phone", user.getString(5));
                    editor.putInt("id", user.getInt(0));
                    editor.putInt("point", user.getInt(6));
                    editor.commit();
                   break;

                }
            }
            if(flag == 0)
            {
                db.insertUser(new User("", "", personEmail, personName, "", 0, image));
                editor.putString("name", personName);
                editor.putString("email", personEmail);
                editor.commit();
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