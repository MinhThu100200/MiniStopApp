package hcmute.edu.vn.mssv18128062;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
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
import java.util.Locale;

public class FoodAdapter extends ArrayAdapter {

    List<Product> foodList;
    private Database db;
    private SQLiteDatabase sqLiteDatabase;
    public FoodAdapter(@NonNull Context context, int resource, ArrayList<Product>productArrayList) {
        super(context, resource);
        foodList = productArrayList;

    }
    @Override
    public int getCount() {
        return foodList.size();
    }

    @Override
    public Object getItem(int position) {
        return foodList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder{
        TextView txtNameFood;
        ImageView imageViewFood;
        TextView txtPrice;
        TextView txtAmount;

        RatingBar ratingBar;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FoodAdapter.ViewHolder holder;
        if(convertView == null)
        {
            holder = new FoodAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_custom_food, null);
            db = new Database(getContext());
            sqLiteDatabase = db.getReadableDatabase();
            holder.txtNameFood = (TextView) convertView.findViewById(R.id.nameFood);
            holder.imageViewFood = (ImageView) convertView.findViewById(R.id.imageFood);
            holder.txtPrice = (TextView) convertView.findViewById(R.id.priceFood);
            holder.txtAmount = (TextView)convertView.findViewById(R.id.amountRating);
            holder.ratingBar = (RatingBar) convertView.findViewById(R.id.ratingBar2);
            convertView.setTag(holder);
        }else{
            holder = (FoodAdapter.ViewHolder) convertView.getTag();
        }


        Product addressStore = (Product) this.getItem(position);

        int count = db.getCountRating(addressStore.getID());
        float rating = db.getRatingByFood(addressStore.getID());
        int a = addressStore.get_picture();
        holder.imageViewFood.setImageResource(addressStore.get_picture());
        holder.txtNameFood.setText(addressStore.getName());
        holder.txtPrice.setText(""+addressStore.get_price());
        holder.txtAmount.setText(""+ count);
        holder.ratingBar.setRating(rating);

        return convertView;
    }
    //filter
    public void filter(String charText)
    {
        charText = charText.toLowerCase(Locale.getDefault());

        if(charText.length() == 0)
        {


        }else {
            List<Product> filter = new ArrayList<>();
            for(Product adr: foodList){
                //List<Address> mlist = new ArrayList<>();
                if(adr.getName().toLowerCase(Locale.getDefault()).contains(charText)){
                    filter.add(adr);
                }
            }
            foodList.clear();
            foodList.addAll(filter);
        }
        notifyDataSetChanged();
    }

}
