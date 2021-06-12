package hcmute.edu.vn.mssv18128062;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class FoodActivity extends AppCompatActivity {

    ListView lvFood;
    ArrayList<Product> productArrayList;
    private Database db;
    FoodAdapter productAdapter;
    ImageButton info;
    ImageButton cart;
    ImageButton notification;
    Intent intentBack;
    Intent intentFood;
    SearchView searchViewFood;
    //TextView txtName;
    //Intent intent;
    //EditText filterText;
    int positionCate;
    //TextView txtTitle;
    ImageButton imageButtonBack;
    private SQLiteDatabase sqLiteDatabase;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    int dem = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);


        //txtTitle = (TextView)findViewById(R.id.titleFood);


        //txtTitle.setText();
        Intent intent = getIntent();

        positionCate = getIntent().getIntExtra("position", -1);

        Toast.makeText(this.getApplication(), "" + positionCate, Toast.LENGTH_SHORT).show();

        imageButtonBack = (ImageButton)findViewById(R.id.btnback);
        lvFood = (ListView)findViewById(R.id.lvFood);
        productArrayList = new ArrayList<>();
        db = new Database(this);
        sqLiteDatabase = db.getReadableDatabase();
        productArrayList = db.getProductByCategory(positionCate + 1);
        productAdapter = new FoodAdapter(this, R.layout.layout_custom_food, productArrayList);

        lvFood.setAdapter(productAdapter);
        lvFood.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intentFood = new Intent(getBaseContext(), FoodDetailActivity.class);
                intentFood.putExtra("positionPro", position);
                intentFood.putExtra("positionCate", positionCate);
                startActivity(intentFood);
                Toast.makeText(getApplicationContext(), "" + position, Toast.LENGTH_SHORT).show();
            }
        });

        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentBack = new Intent(getApplicationContext(),HomePageActivity.class);
                intentBack.putExtra("flag", positionCate);
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


        searchViewFood = (SearchView)findViewById(R.id.searchFood);
        searchViewFood.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(dem != 0)
                {
                    productArrayList.clear();
                    productArrayList = db.getProductByCategory(positionCate + 1);
                    productArrayList.clear();
                    productAdapter = new FoodAdapter(getApplicationContext(), R.layout.layout_custom_food, productArrayList);
                }
                productAdapter.filter(newText);
                //lvAddress.setAdapter(addressAdapter);
                dem++;
                return false;
            }
        });

    }



}