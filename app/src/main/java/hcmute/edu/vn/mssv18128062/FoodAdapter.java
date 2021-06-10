package hcmute.edu.vn.mssv18128062;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FoodAdapter extends ArrayAdapter {

    List<Product> foodList;

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
        TextView txtNameCategory;
        ImageView imageViewCategory;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FoodAdapter.ViewHolder holder;
        if(convertView == null)
        {
            holder = new FoodAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_custome_category, null);
            holder.txtNameCategory = (TextView) convertView.findViewById(R.id.nameFood);
            holder.imageViewCategory = (ImageView) convertView.findViewById(R.id.imageFood);

            convertView.setTag(holder);
        }else{
            holder = (FoodAdapter.ViewHolder) convertView.getTag();
        }


        Product addressStore = (Product) this.getItem(position);

        int a = addressStore.get_picture();
        holder.imageViewCategory.setImageResource(addressStore.get_picture());
        holder.txtNameCategory.setText(addressStore.getName());



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
        //mlist.clear();
    }

}
