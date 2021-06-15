package hcmute.edu.vn.mssv18128062;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ConfirmAddressActivity extends AppCompatActivity {

    ListView lvFood;
    ArrayList<Product> productArrayList;
    private Database db;
    CartAdapter cartAdapter;
    private SQLiteDatabase sqLiteDatabase;
    SharedPreferences sharedPreferencesCart;
    Button btnConfirm;
    TextView txtTotal;
    ImageButton imageButtonBack;
    Cursor cursor;
    ImageButton info;
    ImageButton cart;
    ImageButton notification;
    SharedPreferences sharedPreferencesUser;
    EditText txtAddress;
    int flag = 0;
    int idBooked;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_address);

        lvFood = (ListView)findViewById(R.id.lvFood);
        btnConfirm = (Button)findViewById(R.id.confirm);
        progressBar = (ProgressBar)findViewById(R.id.simpleProgressBar);
        //
        txtTotal = (TextView)findViewById(R.id.total);
        txtAddress = (EditText)findViewById(R.id.address);
        //
        productArrayList = new ArrayList<>();

        sharedPreferencesUser = getSharedPreferences("dataLogin", MODE_PRIVATE);

        if(sharedPreferencesUser.getString("address", "").equals("")){
            flag = 1;
        }
        else{
            txtAddress.setText(sharedPreferencesUser.getString("address", ""));
        }
        db = new Database(this);
        sqLiteDatabase = db.getReadableDatabase();

        sharedPreferencesCart = getSharedPreferences("dataCart", MODE_PRIVATE);
        String query = "SELECT * FROM PRODUCT";
        cursor = db.GetData(query);
        while (cursor.moveToNext())
        {
            if(sharedPreferencesCart.getInt(""+cursor.getInt(0), -1) == cursor.getInt(0)){
                int amount = sharedPreferencesCart.getInt("amount" + cursor.getInt(0), -1);
                productArrayList.add(new Product(cursor.getInt(0), cursor.getString(1), cursor.getFloat(2),
                        cursor.getString(3), amount, cursor.getInt(5)));
            }
        }

        //productArrayList = db.getProductByCategory(positionCate + 1);
        cartAdapter = new CartAdapter(this, R.layout.layout_custom_cart, productArrayList);

        lvFood.setAdapter(cartAdapter);

        String total = "" + sharedPreferencesCart.getFloat("total", 0);
        txtTotal.setText(total);
        //
        cart = (ImageButton)findViewById(R.id.cart);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new  Intent(getBaseContext(), CartActivity.class);
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

        imageButtonBack = (ImageButton)findViewById(R.id.btnback);
        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                intent.putExtra("name", "confirm");
                startActivity(intent);
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                SharedPreferences.Editor editorUser = sharedPreferencesUser.edit();
                SharedPreferences.Editor editor = sharedPreferencesCart.edit();
                String address = txtAddress.getText().toString().trim();

                if(address.equals(""))
                {
                    Toast.makeText(getBaseContext(), "Bạn ơi nhập địa chỉ nhận hàng nè!", Toast.LENGTH_SHORT).show();
                }
                else{

                    editorUser.putString("address", address);
                    editorUser.commit();
                    LocalDateTime lt = LocalDateTime.now();
                    DateTimeFormatter formatToday = DateTimeFormatter.ofPattern("MM/dd/yy:hh:mm:ss");
                    String datenow = lt.format(formatToday);
                    float total = sharedPreferencesCart.getFloat("total", 0);
                    int point = (int)(total/5000);
                    db.insertBooked(new Booked(datenow, 0, address, total));
                    Cursor cursorBooked = db.getBookedByDate(datenow);
                    //int a = cursorBooked.getCount();
                    String query = "SELECT * FROM PRODUCT";
                    cursor = db.GetData(query);

                    while (cursor.moveToNext())
                    {
                        if(sharedPreferencesCart.getInt(""+cursor.getInt(0), -1) == cursor.getInt(0)){
                            int amount = sharedPreferencesCart.getInt("amount" + cursor.getInt(0), -1);

                            while (cursorBooked.moveToNext()){
                                idBooked = cursorBooked.getInt(0);
                            }

                            db.insertOrder(new Order(cursor.getInt(0), cursor.getFloat(2),
                                    amount, idBooked));

                            editor.putInt(""+cursor.getInt(0), -1);
                            editor.putInt("amount" + cursor.getInt(0), 0);
                            editor.commit();
                        }
                    }

                    if(sharedPreferencesUser.getInt("id", -1) == -1)
                    {
                        String email = sharedPreferencesUser.getString("email", "");
                        Cursor user = db.GetData("SELECT * FROM USERS");
                        while(user.moveToNext()){
                            if(user.getString(3).equals(email))
                            {
                                int id = user.getInt(0);
                                int totalPoint = user.getInt(6) + point;
                                db.QueryData("UPDATE USERS SET POINT='"+totalPoint+"' WHERE ID=" + id +"");
                            }
                        }
                    }
                    else{
                        int id = sharedPreferencesUser.getInt("id", -1);
                        db.updateUserPoint(point, id);
                    }
                    editor.putFloat("total", 0);
                    editor.commit();

                }
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getBaseContext(), "Đã đặt hàng thành công rồi bạn iu <3 ơi!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(intent);
            }
        });

    }
}