package hcmute.edu.vn.mssv18128062;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class RateAdapter extends ArrayAdapter {

    List<Rate> listRate;
    public RateAdapter(@NonNull Context context, int resource, ArrayList<Rate> rateArrayList) {
        super(context, resource);
        listRate = rateArrayList;
    }

    @Override
    public int getCount() {
        return listRate.size();
    }

    @Override
    public Object getItem(int position) {
        return listRate.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    class ViewHolder{
        TextView txtName;
        TextView txtTime;
        TextView txtCmt;
        RatingBar ratingBar;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RateAdapter.ViewHolder holder;
        if(convertView == null)
        {
            holder = new RateAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_custom_rate, parent, false);
            holder.txtName= (TextView) convertView.findViewById(R.id.name);
            holder.txtTime = (TextView) convertView.findViewById(R.id.time);
            holder.txtCmt = (TextView) convertView.findViewById(R.id.cmt);
            holder.ratingBar = (RatingBar) convertView.findViewById(R.id.ratingBar);

            convertView.setTag(holder);
        }else{
            holder = (RateAdapter.ViewHolder) convertView.getTag();
        }

        Rate addressStore = (Rate) this.getItem(position);
        holder.txtName.setText(addressStore.get_name());
        holder.txtTime.setText("* " + addressStore.get_dateRating());
        holder.txtCmt.setText(addressStore.get_cmt());
        holder.ratingBar.setRating(addressStore.get_rating());
        //int imgId = addressStore.get_picture();

        //byte[] image = addressStore.get_picture();
        ///Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
        //holder.imageViewFood.setImageBitmap(bitmap);
        return convertView;
    }
}
