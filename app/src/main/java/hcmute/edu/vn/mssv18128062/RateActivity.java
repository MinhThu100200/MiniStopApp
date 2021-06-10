package hcmute.edu.vn.mssv18128062;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

public class RateActivity extends AppCompatActivity {

    Button btnSubmit;
    RatingBar ratingBar;
    float myRating;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8), (int)(height*.4));

        btnSubmit = (Button)findViewById(R.id.submit);
        ratingBar = (RatingBar)findViewById(R.id.ratingBar);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                int ratingNow = (int)rating;
                String message = null;

                myRating = ratingBar.getRating();

                switch (ratingNow){
                    case 1:
                        message = "Tôi rất tiếc vì điều này :(";
                        break;
                    case 2:
                        message = "Chúng tôi luôn muốn phục vụ bạn!";
                        break;
                    case 3:
                        message = "Cảm ơn bạn đã đóng góp.";
                        break;
                    case 4:
                        message = "Tuyệt vời cảm ơn bạn rất nhiều!";
                        break;
                    case 5:
                        message = "Cảm ơn bạn, bạn thật tuyệt vời!";
                        break;
                    default:
                        message = "Cảm ơn bạn ạ!";
                        break;
                }

                Toast.makeText(RateActivity.this, "" + message, Toast.LENGTH_LONG).show();
            }
        });

    }
}