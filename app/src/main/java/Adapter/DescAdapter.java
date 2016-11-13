package Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hammad.workshopinventory.Description;
import com.example.hammad.workshopinventory.R;

import java.util.ArrayList;

/**
 * Created by Hammad on 10/24/2016.
 */

public class DescAdapter extends ArrayAdapter<Description> implements Filterable{
    public DescAdapter(Context context, ArrayList<Description> descList) {
        super(context,0, descList);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Description currentItemDesc = getItem(position);
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.itenName_textview);
        name.setText(currentItemDesc.getMname());

        TextView partNo = (TextView) convertView.findViewById(R.id.partNo_textview);
        partNo.setText(currentItemDesc.getMpartNo());

        TextView quantity = (TextView) convertView.findViewById(R.id.quantity_textview);
        quantity.setText(String.format("%s", currentItemDesc.getMquantity()));



        if(!currentItemDesc.hasImage()) {
            TextView price = (TextView) convertView.findViewById(R.id.price_textview);
            price.setText(String.format("$ %s", currentItemDesc.getMprice()));

        ImageView image = (ImageView) convertView.findViewById(R.id.itemimageImageView);

            Bitmap resized = Bitmap.createScaledBitmap(currentItemDesc.getMimage(), 150, 150, true);
            Bitmap circularImage = getRoundedRectBitmap(resized, 100);
            image.setImageBitmap(circularImage);
        }else{
            TextView price = (TextView) convertView.findViewById(R.id.price_textview);

            TextView qtyView = (TextView) convertView.findViewById(R.id.quantity_available);
            qtyView.setText("Sold");
            price.setText(currentItemDesc.getMprice());

            ImageView image = (ImageView) convertView.findViewById(R.id.itemimageImageView);
            image.setVisibility(View.GONE);
        }

        return convertView;


    }
    public static Bitmap getRoundedRectBitmap(Bitmap bitmap, int pixels) {
        Bitmap result = null;
        try {
            result = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(result);

            int color = 0xff424242;
            Paint paint = new Paint();
            Rect rect = new Rect(0, 0, 200, 200);

            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
            canvas.drawCircle(80, 80, 80, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);

        } catch (NullPointerException e) {
        } catch (OutOfMemoryError o) {
        }
        return result;
    }

}
