package hcmute.edu.vn.mssv18128062;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CategoryAdapter extends ArrayAdapter {

    private Integer imgId[] = {R.drawable.spicy_food, R.drawable.ch};
    List<Category> categoryList;
    public CategoryAdapter(Context context, int resource, ArrayList<Category>categoryArrayList) {
        super(context, resource);
        categoryList = categoryArrayList;
    }
    @Override
    public int getCount() {
        return categoryList.size();
    }

    @Override
    public Object getItem(int position) {
        return categoryList.get(position);
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
        CategoryAdapter.ViewHolder holder;
        if(convertView == null)
        {
            holder = new CategoryAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_custome_category, parent, false);
            holder.txtNameCategory = (TextView) convertView.findViewById(R.id.nameFood);
            holder.imageViewCategory = (ImageView) convertView.findViewById(R.id.imageFood);

            convertView.setTag(holder);
        }else{
            holder = (CategoryAdapter.ViewHolder) convertView.getTag();
        }


        Category addressStore = (Category) this.getItem(position);

        int a = addressStore.get_picture();
        int b = R.drawable.spicy_food;
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
            List<Category> filter = new ArrayList<>();

            for(Category adr: categoryList){
                //List<Address> mlist = new ArrayList<>();
                if(adr.getName().toLowerCase(Locale.getDefault()).contains(charText)){
                    filter.add(adr);
                }
            }
            categoryList.clear();
            categoryList.addAll(filter);
        }
        notifyDataSetChanged();
        //mlist.clear();
    }

}

