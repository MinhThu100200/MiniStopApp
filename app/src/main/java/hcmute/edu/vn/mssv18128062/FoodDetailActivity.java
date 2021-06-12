package hcmute.edu.vn.mssv18128062;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ramotion.circlemenu.CircleMenuView;

import java.util.ArrayList;

public class FoodDetailActivity extends AppCompatActivity {

    ImageButton info;
    ImageButton cart;
    ImageButton notification;
    Intent intentBack;
    int positionCate;
    Database db;
    ArrayList<Product> productArrayList;
    private SQLiteDatabase sqLiteDatabase;
    int positionPro;
    ImageButton imageButtonBack;
    ImageView imgFood;
    RateAdapter rateAdapter;
    TextView nameFood;
    TextView priceFood;
    TextView description;
    ArrayList<Rate> rateArrayList;
    ListView lvRate;
    Intent intent;
    int dem  = 0;
    int id;
    SharedPreferences sharedPreferencesCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        intent = getIntent();

        imgFood = (ImageView)findViewById(R.id.imgFood);
        nameFood = (TextView)findViewById(R.id.nameFood);
        priceFood = (TextView)findViewById(R.id.priceFood);
        description = (TextView)findViewById(R.id.description);
        lvRate = (ListView)findViewById(R.id.lvRate);
        productArrayList = new ArrayList<>();
        positionCate = getIntent().getIntExtra("positionCate", -1);
        positionPro = getIntent().getIntExtra("positionPro", -1);
        Toast.makeText(this.getApplication(), "" + positionCate, Toast.LENGTH_SHORT).show();


        db = new Database(this);
        sqLiteDatabase = db.getReadableDatabase();
        productArrayList = db.getProductByCategory(positionCate + 1);

        for(Product pro:productArrayList){
            if(dem == positionPro)
            {
                id = pro.getID();
                imgFood.setImageResource(pro.get_picture());
                nameFood.setText(pro.getName());
                priceFood.setText("Giá: " + pro.get_price() + " VND");
                description.setText(pro.getDescription());
                break;
            }
            dem++;
        }
        rateArrayList = new ArrayList<>();
        rateArrayList = db.getRateByIdPro(id);
        rateAdapter = new RateAdapter(this, R.layout.layout_custom_rate, rateArrayList);

        lvRate.setAdapter(rateAdapter);

        imageButtonBack = (ImageButton)findViewById(R.id.btnback);
        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentBack = new Intent(getApplicationContext(),FoodActivity.class);
                intentBack.putExtra("position", positionCate);
                startActivity(intentBack);
                //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.contant_main, new Home()).commit();
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
        sharedPreferencesCart = getSharedPreferences("dataCart", MODE_PRIVATE);



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
                        intentBack = new Intent(getApplicationContext(),HomePageActivity.class);
                        startActivity(intentBack);
                        break;
                    case 1:
                        Intent intentRate = new Intent(FoodDetailActivity.this, RateActivity.class);
                        intentRate.putExtra("Id", id);
                        intentRate.putExtra("positionCate", positionCate);
                        intentRate.putExtra("positionPro", positionPro);
                        startActivity(intentRate);
                        break;
                    case 2:
                        SharedPreferences.Editor editor = sharedPreferencesCart.edit();
                        if(sharedPreferencesCart.getInt(""+id, -1) == id)
                        {
                            int amount = sharedPreferencesCart.getInt("amount" + id, 0);

                            editor.putInt("amount" + id, amount + 1);
                        }
                        else
                        {
                            editor.putInt(""+id, id );
                            editor.putInt("amount" + id, 1);
                            int amountAll = sharedPreferencesCart.getInt("amountAllPro", 0);
                            editor.putInt("amountAllPro", amountAll + 1);
                            //editor.put
                        }
                        editor.commit();
                        Toast.makeText(getBaseContext(), "Đã thêm vao giỏ", Toast.LENGTH_SHORT).show();
                        break;

                }
            }
        });

    }
}