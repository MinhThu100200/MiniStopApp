package hcmute.edu.vn.mssv18128062;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    ListView lvFood;
    ArrayList<Product> productArrayList;
    private Database db;
    CartAdapter cartAdapter;
    private SQLiteDatabase sqLiteDatabase;
    SharedPreferences sharedPreferencesCart;
    Button buttonBook;
    TextView txtTotal;
    ImageButton imageButtonBack;
    Intent intentFoward;
    Cursor cursor;
    String name;
    int positionCate;
    int positionPro;
    ImageButton info;
    ImageButton cart;
    ImageButton notification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);


        intentFoward = getIntent();
        name = getIntent().getStringExtra("name");
        positionCate = getIntent().getIntExtra("positionCate", -1);
        positionPro = getIntent().getIntExtra("positionPro", -1);

        lvFood = (ListView)findViewById(R.id.lvFood);
        buttonBook = (Button)findViewById(R.id.Book);
        //
        txtTotal = (TextView)findViewById(R.id.total);

        productArrayList = new ArrayList<>();
        db = new Database(this);
        sqLiteDatabase = db.getReadableDatabase();

        sharedPreferencesCart = getSharedPreferences("dataCart", MODE_PRIVATE);
        if(sharedPreferencesCart.getFloat("total", 0) != 0)
        {
            buttonBook.setEnabled(true);
        }

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

        lvFood.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(CartActivity.this, CartDetailActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);

            }
        });
        imageButtonBack = (ImageButton)findViewById(R.id.btnback);
        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.equals("foodDetail"))
                {
                    Intent intentBack = new Intent(getApplicationContext(),FoodDetailActivity.class);
                    intentBack.putExtra("positionCate", positionCate);
                    intentBack.putExtra("positionPro", positionPro);
                    startActivity(intentBack);
                }
                else if(name.equals("confirm"))
                {
                    Intent intentBack = new Intent(getApplicationContext(), ConfirmAddressActivity.class);
                    startActivity(intentBack);
                }
                else if(name.equals("home"))
                {
                    Intent intentBack = new Intent(getApplicationContext(), HomePageActivity.class);
                    intentBack.putExtra("name", "home");
                    startActivity(intentBack);
                }
                else if(name.equals("product")){
                    Intent intentBack = new Intent(getApplicationContext(), HomePageActivity.class);
                    intentBack.putExtra("name", "product");
                    startActivity(intentBack);
                }
                else if(name.equals("point")){
                    Intent intentBack = new Intent(getApplicationContext(), HomePageActivity.class);
                    intentBack.putExtra("name", "point");
                    startActivity(intentBack);
                }
                else if(name.equals("promotion")){
                    Intent intentBack = new Intent(getApplicationContext(), HomePageActivity.class);
                    intentBack.putExtra("name", "promotion");
                    startActivity(intentBack);
                }
                else if(name.equals("other")){
                    Intent intentBack = new Intent(getApplicationContext(), HomePageActivity.class);
                    intentBack.putExtra("name", "other");
                    startActivity(intentBack);
                }
                else if(name.equals("food")){
                    Intent intentBack = new Intent(getApplicationContext(), HomePageActivity.class);
                    intentBack.putExtra("positionCate", positionCate);
                    startActivity(intentBack);
                }

                //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.contant_main, new Home()).commit();
            }
        });
        buttonBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ConfirmAddressActivity.class);
                startActivity(intent);
            }
        });
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
    }

}
