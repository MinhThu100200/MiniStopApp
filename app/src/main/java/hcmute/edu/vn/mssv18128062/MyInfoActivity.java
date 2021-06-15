package hcmute.edu.vn.mssv18128062;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class MyInfoActivity extends AppCompatActivity {


    ImageView imageView;
    ImageButton btnImg;
    private SQLiteDatabase sqLiteDatabase;
    SharedPreferences sharedPreferencesUser;
    private Database db;
    EditText name;
    EditText email;
    EditText username;
    EditText password;
    EditText phone;
    Button update;
    Button close;

    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
        //
        name = (EditText)findViewById(R.id.name);
        email = (EditText)findViewById(R.id.email);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        phone = (EditText)findViewById(R.id.phone);
        update = (Button)findViewById(R.id.update_info);
        close = (Button) findViewById(R.id.log_out);
        imageView = (ImageView)findViewById(R.id.img);
        btnImg = (ImageButton)findViewById(R.id.btnimg);

        sharedPreferencesUser = getSharedPreferences("dataLogin", MODE_PRIVATE);
        db = new Database(this);
        sqLiteDatabase = db.getReadableDatabase();
        String emailUser = sharedPreferencesUser.getString("email", "");
        email.setText(emailUser);
        String nameUser = sharedPreferencesUser.getString("name", "");
        name.setText(nameUser);
        String usernameUser = sharedPreferencesUser.getString("username", "");
        username.setText(usernameUser);
        String passwordUser = sharedPreferencesUser.getString("password", "");
        password.setText(passwordUser);
        String phoneUser = sharedPreferencesUser.getString("phone", "");
        phone.setText(phoneUser);


        Cursor user = db.GetData("SELECT * FROM USERS");
        while(user.moveToNext()){
            if(user.getString(3).equals(emailUser))
            {
                id = user.getInt(0);
                username.setText(user.getString(1));
                password.setText(user.getString(2));
                phone.setText(user.getString(5));
                byte[] img = user.getBlob(7);
                Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
                imageView.setImageBitmap(bitmap);
            }
        }



        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameUpdate = username.getText().toString().trim();
                String passwordUpdate = password.getText().toString().trim();
                String emailUpdate = email.getText().toString().trim();
                String nameUpdate = name.getText().toString().trim();
                String phoneUpdate = phone.getText().toString().trim();
                BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArray);
                byte[] image = byteArray.toByteArray();

                db.insertUser(new User(id, usernameUpdate, passwordUpdate, emailUpdate, nameUpdate, phoneUpdate, 0, image));
                Toast.makeText(getBaseContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor editor = sharedPreferencesUser.edit();
                editor.putString("email", emailUpdate);
                editor.putString("name", nameUpdate);
                editor.putString("phone", phoneUpdate);
                editor.putString("username", usernameUpdate);
                editor.putString("password", passwordUpdate);
                editor.commit();
            }
        });


        btnImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto , 0);//one can be replaced with any action code
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomePageActivity.class);
                intent.putExtra("name", "home");
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){

            Uri selectedImage = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                //Bitmap img = rotateImage(bitmap, 360);
                //img = rotateImage(img, 90);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
