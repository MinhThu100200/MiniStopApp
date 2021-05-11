package hcmute.edu.vn.mssv18128062;


import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Date;
import java.text.SimpleDateFormat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;

import android.graphics.Matrix;
public class SignUpActivity extends AppCompatActivity {

    TextView txtLogin;
    Button btnChoose, btnTakePhoto;
    ImageView imageView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        txtLogin = (TextView)findViewById(R.id.txtLogin);
        btnChoose = (Button)findViewById(R.id.btnChoose);
        btnTakePhoto = (Button)findViewById(R.id.btnTakePhoto);
        imageView = (ImageView)findViewById(R.id.imageView);

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
    }

    //public static Bitmap rotateImage(Bitmap source, float angle) {
       // Matrix matrix = new Matrix();
       // matrix.postRotate(angle);
       // return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    //}

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
