package Adapter;

import android.content.Context;
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

        ImageView image = (ImageView) convertView.findViewById(R.id.itemimage_imageview);
            image.setImageBitmap(currentItemDesc.getMimage());
        }else{
            TextView price = (TextView) convertView.findViewById(R.id.price_textview);
            TextView priceView = (TextView) convertView.findViewById(R.id.priceView_textView);
            TextView qtyView = (TextView) convertView.findViewById(R.id.quantity_available);
            qtyView.setText("Quantity Sold");
            price.setText(currentItemDesc.getMprice());

            ImageView image = (ImageView) convertView.findViewById(R.id.itemimage_imageview);
            image.setVisibility(View.GONE);
        }

        return convertView;


    }
}
