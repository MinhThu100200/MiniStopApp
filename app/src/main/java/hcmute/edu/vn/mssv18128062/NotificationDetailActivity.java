package hcmute.edu.vn.mssv18128062;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class NotificationDetailActivity extends AppCompatActivity {

    Button close;
    TextView info, title;
    ImageView imageView;
    private Database db;
    private SQLiteDatabase sqLiteDatabase;
    int pos;
    int dem = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_detail);

        close = (Button)findViewById(R.id.close);
        info = (TextView)findViewById(R.id.info);
        title = (TextView)findViewById(R.id.title);
        imageView = (ImageView)findViewById(R.id.imgNoti);

        Intent intent = getIntent();
        pos = getIntent().getIntExtra("position", -1);
        db = new Database(this);
        sqLiteDatabase = db.getReadableDatabase();

        Cursor cursor = db.GetData("SELECT * FROM NOTIFICATION");
        while (cursor.moveToNext()){
            if(pos == dem)
            {
                String titleNoti = cursor.getString(1);
                title.setText(titleNoti);
                String des = cursor.getString(2);
                info.setText(des);
                imageView.setImageResource(cursor.getInt(3));
            }
            dem++;
        }
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentBack = new Intent(getApplicationContext(), NotificationActivity.class);
                startActivity(intentBack);
            }
        });


    }
}