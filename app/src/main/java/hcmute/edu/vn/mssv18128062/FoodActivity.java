package hcmute.edu.vn.mssv18128062;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class FoodActivity extends AppCompatActivity {

    ListView lvFood;
    ArrayList<Product> productArrayList;
    private Database db;
    FoodAdapter productAdapter;
    private SQLiteDatabase sqLiteDatabase;
    int dem = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        Intent intent = getIntent();

        int position = getIntent().getIntExtra("position", -1);

        Toast.makeText(this.getApplication(), "" + position, Toast.LENGTH_SHORT).show();

        lvFood = (ListView)findViewById(R.id.lvFood);
        productArrayList = new ArrayList<>();
        db = new Database(this);
        sqLiteDatabase = db.getReadableDatabase();
        productArrayList = db.getProductByCategory(position);
        productAdapter = new FoodAdapter(this, R.layout.layout_custome, productArrayList);

        lvFood.setAdapter(productAdapter);

    }

}