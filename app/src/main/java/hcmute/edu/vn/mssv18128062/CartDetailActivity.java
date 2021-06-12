package hcmute.edu.vn.mssv18128062;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CartDetailActivity extends AppCompatActivity {


    ImageButton info;
    ImageButton cart;
    ImageButton notification;
    TextView nameFood;
    TextView priceFood;
    TextView description;
    EditText txtAmount;
    ImageButton up;
    ImageButton imageButtonBack;
    ImageView imgFood;
    ImageButton down;
    Button editAmount;
    Button delete;
    Intent intent;
    SharedPreferences sharedPreferencesCart;
    private SQLiteDatabase sqLiteDatabase;
    Database db;
    int dem = 0;
    int id;
    double price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_detail);

        imgFood = (ImageView)findViewById(R.id.imgFood);
        nameFood = (TextView)findViewById(R.id.nameFood);
        priceFood = (TextView)findViewById(R.id.priceFood);
        description = (TextView)findViewById(R.id.description);
        txtAmount = (EditText)findViewById(R.id.amount);
        up = (ImageButton)findViewById(R.id.up);
        down = (ImageButton)findViewById(R.id.down);
        editAmount = (Button)findViewById(R.id.edit);
        delete = (Button)findViewById(R.id.delete);

        sharedPreferencesCart = getSharedPreferences("dataCart", MODE_PRIVATE);
        db = new Database(this);
        sqLiteDatabase = db.getReadableDatabase();

        intent = getIntent();
        int pos = getIntent().getIntExtra("position", -1);
        String query = "SELECT * FROM PRODUCT";
        Cursor cursor = db.GetData(query);

        while (cursor.moveToNext())
        {
            if(sharedPreferencesCart.getInt(""+cursor.getInt(0), -1) == cursor.getInt(0)){
                if(dem == pos){
                    id = cursor.getInt(0);
                    imgFood.setImageResource(cursor.getInt(5));
                    nameFood.setText(cursor.getString(1));
                    price = cursor.getDouble(2);
                    priceFood.setText("Giá: " + price + " VND");
                    description.setText(cursor.getString(3));
                    String amount = "" + sharedPreferencesCart.getInt("amount"+cursor.getInt(0), -1);
                    txtAmount.setText(amount);
                    break;
                }
                dem ++ ;
            }
        }
        imageButtonBack = (ImageButton)findViewById(R.id.btnback);
        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentBack = new Intent(getApplicationContext(),CartActivity.class);
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

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amountNow = txtAmount.getText().toString();
                int amount = 1 + Integer.parseInt(amountNow);
                String a = "" + amount;
                txtAmount.setText(a);
            }
        });

        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amountNow = txtAmount.getText().toString();
                if(Integer.parseInt(amountNow) <= 1)
                {
                    delete();
                }
                else
                {
                    int amount = -1 + Integer.parseInt(amountNow);
                    String a = "" + amount;
                    txtAmount.setText(a);
                }

            }
        });

        editAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferencesCart.edit();
                int amountCart = sharedPreferencesCart.getInt("amount"+id, 0);
                String amount = txtAmount.getText().toString();
                int amountNow = Integer.parseInt(amount);
                float total = sharedPreferencesCart.getFloat("total", 0);
                float totalNow = (float)(total - price*(amountCart - amountNow));
                editor.putInt("amount"+id, amountNow);
                editor.putFloat("total", totalNow);
                editor.commit();
                Toast.makeText(getBaseContext(), "Đã sửa thành công", Toast.LENGTH_SHORT).show();
                finish();
                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(intent);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferencesCart.edit();
                int amount = sharedPreferencesCart.getInt("amount"+id, 0);
                float total = sharedPreferencesCart.getFloat("total", 0);
                float totalPro = (float) (amount*price);
                float totalNow = total - totalPro;
                editor.putInt(""+id, -2);
                editor.putInt("amount"+id, 0);
                editor.putFloat("total", totalNow);
                editor.commit();
                Toast.makeText(getBaseContext(), "Đã xóa thành công", Toast.LENGTH_SHORT).show();
                finish();
                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(intent);
            }
        });

    }

    public void delete(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        // Setting Alert Dialog Title
        alertDialogBuilder.setTitle("Xóa sản phẩm");
        // Icon Of Alert Dialog
        alertDialogBuilder.setIcon(R.drawable.ic_baseline_question_answer_24);
        // Setting Alert Dialog Message
        alertDialogBuilder.setMessage("Bạn muốn xóa sản phẩm ra khỏi giỏ");
        alertDialogBuilder.setCancelable(false);

        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                SharedPreferences.Editor editor = sharedPreferencesCart.edit();
                int amount = sharedPreferencesCart.getInt("amount"+id, 0);
                float total = sharedPreferencesCart.getFloat("total", 0);
                float totalPro = (float) (amount*price);
                float totalNow = total - totalPro;
                editor.putInt(""+id, -2);
                editor.putInt("amount"+id, 0);
                editor.putFloat("total", totalNow);
                editor.commit();
                Toast.makeText(getBaseContext(), "Đã xóa thành công", Toast.LENGTH_SHORT).show();
                finish();
                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(intent);
            }
        });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialogBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}