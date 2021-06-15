package hcmute.edu.vn.mssv18128062;


import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.text.SimpleDateFormat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;

import android.graphics.Matrix;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    TextView txtLogin;
    Button btnChoose, btnTakePhoto;
    ImageView imageView;
    EditText name;
    EditText email;
    EditText username;
    EditText password;
    EditText phone;
    Button signUp;
    private SQLiteDatabase sqLiteDatabase;
    SharedPreferences sharedPreferencesUser;
    private Database db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        txtLogin = (TextView)findViewById(R.id.txtLogin);
        btnChoose = (Button)findViewById(R.id.btnChoose);
        btnTakePhoto = (Button)findViewById(R.id.btnTakePhoto);
        imageView = (ImageView)findViewById(R.id.imageView);
        name = (EditText)findViewById(R.id.name);
        email = (EditText)findViewById(R.id.email);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        phone = (EditText)findViewById(R.id.phone);
        signUp = (Button)findViewById(R.id.btnSignUp);
        //int iamge = R.drawable.ch;
        //imageView.setImageResource(iamge);
        sharedPreferencesUser = getSharedPreferences("dataLogin", MODE_PRIVATE);
        db = new Database(this);
        sqLiteDatabase = db.getReadableDatabase();
        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent BackIntent = new Intent();
                setResult(RESULT_OK,BackIntent);
                finish();
            }
        });
        //choose photo
        btnChoose.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto , 0);//one can be replaced with any action code
            }
        });

        //take photo
        btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                try {
                   startActivityForResult(takePictureIntent, 1);
                } catch (ActivityNotFoundException e) {
                    // display error state to the user
                }


            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameAdd = username.getText().toString().trim();
                String passwordAdd = password.getText().toString().trim();
                String emailAdd = email.getText().toString().trim();
                String nameAdd = name.getText().toString().trim();
                String phoneAdd = phone.getText().toString().trim();
                BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArray);
                byte[] image = byteArray.toByteArray();
                db.insertUser(new User(usernameAdd, passwordAdd, emailAdd, nameAdd, phoneAdd, 0, image));
                Toast.makeText(getBaseContext(), "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case 0:
                if(resultCode == RESULT_OK){

                    Uri selectedImage = imageReturnedIntent.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                        //Bitmap img = rotateImage(bitmap, 360);
                        //img = rotateImage(img, 90);
                       // Bitmap imgRound = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
                      //  Canvas canvas = new Canvas(imgRound);
                      //  Paint paint = new Paint();
                       // paint.setAntiAlias(true);
                      //  paint.setShader(new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
                      //  canvas.drawRoundRect(new RectF(100, 100, bitmap.getWidth(), bitmap.getHeight()), 100, 100, paint);
                      //  imageView.setImageBitmap(imgRound);
                        imageView.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                break;
            case 1:
                if(resultCode == RESULT_OK){

                    Bitmap bitmap = imageReturnedIntent.getExtras().getParcelable("data");
                    //Bitmap img = rotateImage(imageBitmap, 360);
                    //img = rotateImage(img, 90);
                    imageView.setImageBitmap(bitmap);
                    //imageView.setImageBitmap(Bitmap.createScaledBitmap(imageBitmap, 250, 250, false ));
                }
                break;
        }
    }

}
