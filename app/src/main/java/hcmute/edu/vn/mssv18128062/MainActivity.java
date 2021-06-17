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
        //String des = "Chào mừng tình yêu đã đến đồng hành cùng mình nè. Love u 3000 !!!";
        //db.insertNotification("Welcome!!!", des, idImg);
        //int idImg1 = R.drawable.info;
        //String info = "Sắp tới sẽ có rất nhiều chương trình ưu đãi hấp dẫn cho các khách hàng thân yêu. Cửa hàng sẽ thông báo sớm nhất để anh chị em mình tham gia ạ. Cảm ơn anh chị rất nhiều vì đã dồng hàng đến giờ phút này ạ";
        //db.insertNotification("Sự kiện sắp tới", info, idImg1);

        //db.insertAddress("2/5 đường 68 phường Hiệp Phú Quận 9 tp.Thủ Đức", R.drawable.ch1);
        //db.insertAddress("33 Lê Văn Việt phường Hiệp Phú quận 9 tp.Thủ Đức", R.drawable.ch2);
        //db.insertAddress("90 Võ Văn Ngân phường Hiệp Phú quận Thủ Đức tp.Thủ Đức", R.drawable.ch3);
        //db.insertAddress("88 Nguyễn Tri Phương phường 3 quận 6 tp.Trà Vinh", R.drawable.ch4);
        //db.insertAddress("12 Nguyễn Đình Chiểu phường 3 quận 6 tp.Trà Vinh", R.drawable.ch5);
         //1
        //db.insertCategory("Mỳ cay", R.drawable.spicy);
        //db.insertProduct("Mỳ cay hải sản", 40000, "Hương vị đê mê cho tín đồ mỳ cay, với độ cay xé lưỡi sẽ kích thích vị giác của bạn", 1, R.drawable.spicy);
         //2
        //db.insertCategory("Đồ uống", R.drawable.drink);
        //db.insertProduct("Nước cam", 30000, "Nước cam 100% cam tươi cung cấp vitamin C cho bạn khỏe mạnh", 2, R.drawable.organe);
        //db.insertProduct("Nước cam cà rốt", 50000, "Nước trái cây mix, xịn khỏi chê luôn đó nha..Thử nhanh nào khách iu ơi!", 2, R.drawable.organ_carrot);
        //db.insertProduct("Nước ép táo", 45000, "Nước ép táo, với 100% táo nhập từ Mỹ sẽ giúp bạn cảm thấy sảng khoái đó nhe!!!!!", 2, R.drawable.apple);
        //db.insertProduct("Bạc sĩu", 30000, "Với những bạn muốn uống cà phê mà sợ đắng thì bạc sĩu là một lựa chọn thích hợp và tuyệt vời.", 2, R.drawable.bacsiu);
        //db.insertProduct("Sinh tố bơ", 40000, "Một ly sinh tố bơ sẽ giúp bạn cảm thấy haizzz hôm nay ngọt vậy là đủ tuyệt cú mèo luôn nè!!", 2, R.drawable.bo);
        //db.insertProduct("Nước ép cà chua", 35000, "Nếu một ngày buồn bã thất tình thay vì khóc sự lựa chọn tuyệt vời của bạn là một ly nước ép cà chua để mình sáng mắt ra nè. Đừng buồn nhe!!!!", 2, R.drawable.cachua);

        //db.insertProduct("Nước ép dưa hấu", 45000, "Nói các bạn biết giống dưa này mình lấy từ dưa con cháu của giống dưa của Mai An Tiêm, đảm bảo uống vô nhớ luôn lịch sử!!!", 2, R.drawable.duahau);
        //db.insertProduct("Sinh tố mít", 45000, "Nếu đang stress mình nghĩ lựa thêm tý đường cho cuộc sống là vô cùng hợp lý và sinh tố mít rất hân hạnh phục vỵ bạn iu nè <3", 2, R.drawable.mit);

        //db.insertProduct("Cà phê", 30000, "Cà phê học theo StartBuck dành cho mấy đỗ nghèo khỉ muốn thử nè!!", 2, R.drawable.soda);

        //3
        //db.insertCategory("Cơm", R.drawable.com_ga);
        //db.insertProduct("Cơm gà", 45000, "Cơm gà được chế biến từ những con gà vườn thịt rắn chắc và mạnh mẽ, sẽ làm cho bạn ghiền đó", 3, R.drawable.com_ga);
        //4
        //db.insertCategory("Kimbap", R.drawable.kimbap);
        //db.insertProduct("Kimbap truyền thống", 30000, "Với những hạt cơm dẻo dai được cuộn trong miếng rong biển vô cùng thơm ngon", 4, R.drawable.kimbap);
        //5
        //db.insertCategory("Salad", R.drawable.salad);
        //db.insertProduct("Salad trái cây", 60000, "Một món ăn healthy biết bao nè bạn yêu ơi~", 5, R.drawable.salad);
        //6
        //db.insertCategory("Đồ tây", R.drawable.sandwich);
        //db.insertProduct("Sandwich", 30000, "Bắt đầu buổi sáng với một miếng sandwich nóng hỏi thì tuyệt vời lắm đó!", 6, R.drawable.sandwich);
        //db.insertProduct("Mý ý", 60000, "Một đĩa mỳ ý khiến đôi uyên ương càng ưng ý ngại gì không thử nè~", 6, R.drawable.my_y);
        //7
        //db.insertCategory("Chả chiên", R.drawable.cha_chien);
        //db.insertProduct("Chả chiên", 30000, "Nóng hỏi vừa thổi vừa ăn nè~", 7, R.drawable.cha_chien);


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
                    Toast.makeText(getBaseContext(), "Nhập tên đăng nhập và password luôn bạn iu <3 ơi!", Toast.LENGTH_SHORT).show();
                }
                else {
                    String query = "SELECT * FROM USERS";
                    Cursor cursor = db.GetData(query);
                    while (cursor.moveToNext()){
                        if(cursor.getString(1).equals(username) && cursor.getString(2).equals(password)){
                            Toast.makeText(getBaseContext(), "Đăng nhập thành công rồi nè!", Toast.LENGTH_SHORT).show();
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