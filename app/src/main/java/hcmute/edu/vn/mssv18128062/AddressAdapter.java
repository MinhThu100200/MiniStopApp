package hcmute.edu.vn.mssv18128062;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AddressAdapter extends ArrayAdapter {


    List<Address> mlist;
    public AddressAdapter(Context context, int resource, ArrayList<Address> arrayAddress) {
        super(context, resource);
        mlist = arrayAddress;
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    class ViewHolder{
        TextView txtAdrress;
        ImageView imageViewFood;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null)
        {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_custome, parent, false);
            holder.txtAdrress = (TextView) convertView.findViewById(R.id.address);
            holder.imageViewFood = (ImageView) convertView.findViewById(R.id.imageViewCustome);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        Address addressStore = (Address)this.getItem(position);
        holder.txtAdrress.setText(addressStore.getDescription());

        byte[] image = addressStore.get_picture();
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
        holder.imageViewFood.setImageBitmap(bitmap);
        return convertView;
    }
    //filter
    public void filter(String charText)
    {

        charText = charText.toLowerCase(Locale.getDefault());
       if (charText.length() != 0){
            List<Address> filter = new ArrayList<>();

            for(Address adr: mlist){
                //List<Address> mlist = new ArrayList<>();
                if(adr.getDescription().toLowerCase(Locale.getDefault()).contains(charText)){
                    filter.add(adr);
                }
            }
            mlist.clear();
            mlist.addAll(filter);
        }
        notifyDataSetChanged();
        //mlist.clear();
    }

}
