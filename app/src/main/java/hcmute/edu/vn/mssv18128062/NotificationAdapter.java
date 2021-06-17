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

public class NotificationAdapter extends ArrayAdapter {

    List<Notification>notificationList;
    public NotificationAdapter(@NonNull Context context, int resource, ArrayList<Notification> notificationArrayList) {
        super(context, resource);
        notificationList = notificationArrayList;
    }

    @Override
    public int getCount() {
        return notificationList.size();
    }

    @Override
    public Object getItem(int position) {
        return notificationList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    class ViewHolder{
        TextView titleNoti;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NotificationAdapter.ViewHolder holder;
        if(convertView == null)
        {
            holder = new NotificationAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_custom_noti, parent, false);
            holder.titleNoti = (TextView) convertView.findViewById(R.id.titleNoti);

            convertView.setTag(holder);
        }else{
            holder = (NotificationAdapter.ViewHolder) convertView.getTag();
        }

        Notification addressStore = (Notification) this.getItem(position);
        holder.titleNoti.setText(addressStore.get_title());
        //int imgId = addressStore.get_picture();
        //byte[] image = addressStore.get_picture();
        ///Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
        //holder.imageViewFood.setImageBitmap(bitmap);
        return convertView;
    }
}
