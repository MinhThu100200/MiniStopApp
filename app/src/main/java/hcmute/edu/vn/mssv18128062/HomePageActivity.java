package hcmute.edu.vn.mssv18128062;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import android.app.Fragment;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomePageActivity extends AppCompatActivity {

    ImageButton info;

    BottomNavigationView bottomNavigationView;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hompage);

        bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigationView);

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
