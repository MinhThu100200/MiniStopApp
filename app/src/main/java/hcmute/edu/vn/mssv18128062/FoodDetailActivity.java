package hcmute.edu.vn.mssv18128062;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.ramotion.circlemenu.CircleMenuView;

public class FoodDetailActivity extends AppCompatActivity {

    ImageButton info;
    ImageButton cart;
    ImageButton notification;
    Intent intentBack;
    int positionCate;
    int positionPro;
    ImageButton imageButtonBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        Intent intent = getIntent();

        positionCate = getIntent().getIntExtra("positionCate", -1);
        positionPro = getIntent().getIntExtra("positionPro", -1);
        Toast.makeText(this.getApplication(), "" + positionCate, Toast.LENGTH_SHORT).show();

        imageButtonBack = (ImageButton)findViewById(R.id.btnback);
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
                        startActivity(new Intent(FoodDetailActivity.this, RateActivity.class));
                        break;
                    case 2:
                        break;

                }
            }
        });
    }
}