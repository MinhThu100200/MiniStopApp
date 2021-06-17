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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.ramotion.circlemenu.CircleMenuView;

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
    //Button check;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);


        intentFoward = getIntent();
        name = getIntent().getStringExtra("name");
        positionCate = getIntent().getIntExtra("positionCate", -1);
        positionPro = getIntent().getIntExtra("positionPro", -1);

        lvFood = (ListView)findViewById(R.id.lvFood);
        //lvFood.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

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

        float total = sharedPreferencesCart.getFloat("total", 0);
        txtTotal.setText("Tổng: "+ total + "VND");

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
                else if(name.equals("order")){
                    Intent intentBack = new Intent(getApplicationContext(), OrderActivity.class);
                    intentBack.putExtra("name", "cart");
                    startActivity(intentBack);
                }
                else if(name.equals("noti")){
                    Intent intentBack = new Intent(getApplicationContext(), NotificationActivity.class);
                    intentBack.putExtra("name", "noti");
                    startActivity(intentBack);
                }
                else {
                    Intent intentBack = new Intent(getApplicationContext(), CartActivity.class);
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


        final CircleMenuView circleMenuView = findViewById(R.id.circleMenu);
        circleMenuView.setEventListener( new CircleMenuView.EventListener(){
            @Override
            public void onMenuOpenAnimationStart(@NonNull CircleMenuView view) {
                //do some
            }

            @Override
            public void onButtonClickAnimationStart(@NonNull CircleMenuView view, int buttonIndex) {
                super.onButtonClickAnimationStart(view, buttonIndex);
                switch (buttonIndex){
                    case 0:
                        Intent intentHome = new Intent(getApplicationContext(), HomePageActivity.class);
                        intentHome.putExtra("name", "home");
                        startActivity(intentHome);
                        break;
                    case 1:
                        Intent intentOrder = new Intent(getApplicationContext(), OrderActivity.class);
                        intentOrder.putExtra("name", "cart");
                        startActivity(intentOrder);
                        break;
                    case 2:
                        Intent intentProduct = new Intent(getApplicationContext(), HomePageActivity.class);
                        intentProduct.putExtra("name", "product");
                        startActivity(intentProduct);
                        break;
                }
            }
        });
    }

}
