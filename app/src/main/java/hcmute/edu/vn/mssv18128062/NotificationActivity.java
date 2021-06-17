package hcmute.edu.vn.mssv18128062;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class NotificationActivity extends AppCompatActivity {

    ListView lvNoti;
    ArrayList<Notification> notificationArrayList;
    private Database db;
    NotificationAdapter notificationAdapter;
    ImageButton info;
    ImageButton cart;
    ImageButton notification;
    private SQLiteDatabase sqLiteDatabase;
    ImageButton imageButtonBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        lvNoti = (ListView)findViewById(R.id.lvNoti);

        notificationArrayList = new ArrayList<>();
        db = new Database(this);
        sqLiteDatabase = db.getReadableDatabase();

        Cursor cursor = db.GetData("SELECT * FROM NOTIFICATION");
        while (cursor.moveToNext()){
            notificationArrayList.add(new Notification(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                    cursor.getInt(3)));
        }

        notificationAdapter = new NotificationAdapter(this, R.layout.layout_custom_noti, notificationArrayList);
        lvNoti.setAdapter(notificationAdapter);

        lvNoti.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), NotificationDetailActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });

        imageButtonBack = (ImageButton)findViewById(R.id.btnback);
        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomePageActivity.class);
                startActivity(intent);
            }
        });
        cart = (ImageButton)findViewById(R.id.cart);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new  Intent(getBaseContext(), CartActivity.class);
                intent.putExtra("name", "noti");
                startActivity(intent);
            }
        });
        //

        notification = (ImageButton)findViewById(R.id.notification);
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new  Intent(getBaseContext(), NotificationActivity.class);
                startActivity(intent);
            }
        });

        //

        info = (ImageButton)findViewById(R.id.profile);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new  Intent(getBaseContext(), MyInfoActivity.class);
                startActivity(intent);
            }
        });
    }
}
