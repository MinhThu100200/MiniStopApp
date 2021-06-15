package hcmute.edu.vn.mssv18128062;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {

    ListView lvOrder;
    ArrayList<Booked> orderArrayList;
    private Database db;
    OrderAdapter orderAdapter;
    private SQLiteDatabase sqLiteDatabase;
    ImageButton info;
    ImageButton cart;
    ImageButton notification;
    ImageButton imageButtonBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        lvOrder = (ListView)findViewById(R.id.lvOrder);
        imageButtonBack = (ImageButton)findViewById(R.id.btnback);
        //
        db = new Database(this);
        sqLiteDatabase = db.getReadableDatabase();
        Intent intent = getIntent();
        //name = getIntent().getStringExtra("name");
        orderArrayList = new ArrayList<>();

        String Query = "SELECT * FROM BOOKED";
        Cursor cursor = db.GetData(Query);

        while (cursor.moveToNext()){
            orderArrayList.add(new Booked(cursor.getInt(0), cursor.getString(1), cursor.getInt(2),
                    cursor.getString(3), cursor.getFloat(4)));
        }

        orderAdapter = new OrderAdapter(this, R.layout.layout_custom_order, orderArrayList);

        lvOrder.setAdapter(orderAdapter);
        lvOrder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), OrderDetailActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });


        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomePageActivity.class);
                intent.putExtra("name", "order");
                startActivity(intent);
            }
        });
        cart = (ImageButton)findViewById(R.id.cart);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new  Intent(getBaseContext(), CartActivity.class);
                intent.putExtra("name", "order");
                startActivity(intent);
            }
        });
        //

        notification = (ImageButton)findViewById(R.id.notification);
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentRate = new  Intent(getBaseContext(), NotificationActivity.class);
                startActivity(intentRate);
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