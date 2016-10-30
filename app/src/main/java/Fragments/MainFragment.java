package Fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.NumberPicker;


import com.example.hammad.workshopinventory.AddItems;
import com.example.hammad.workshopinventory.Description;
import com.example.hammad.workshopinventory.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


import Adapter.DescAdapter;
import Database.SalesDbHelper;
import Database.WorkshopDbHelper;
import Database.EntryContract;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    View rootview;
    WorkshopDbHelper workshopDbHelper;
    Cursor cursor;
    ListView list;
    CallBackMainFragment callBackMainFragment;
    View myview;
    AlertDialog dialog;
    String selectedItemPartNo;
    SalesDbHelper salesDbHelper;
    int numberPickerSelectedQty;
    private int selectedItemQty;



    public MainFragment() {
        // Required empty public constructor
    }

    public interface CallBackMainFragment{
        public void displayDetailFragment();

        public void displaySalesRecordFragment();

        public void sendpartNo(String partNo);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_main, container, false);

        salesDbHelper = new SalesDbHelper(getActivity().getApplicationContext());
        PopulateListView();

        OnitemsListitemClickLisetener();


        return rootview;
    }

    private void OnDetailImageClickListener() {

        ImageView detailImage = (ImageView) myview.findViewById(R.id.detail_imageView);
        detailImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                callBackMainFragment.displayDetailFragment();


            }
        });
        }


    private void OnitemsListitemClickLisetener() {

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView text = (TextView) view.findViewById(R.id.partNo_textview);
                TextView qty = (TextView) view.findViewById(R.id.quantity_textview);
                selectedItemQty = Integer.parseInt(String.valueOf(qty.getText()));
                selectedItemPartNo = String.valueOf(text.getText());
                Cursor cursor = workshopDbHelper.RetrieveImage(selectedItemPartNo);
                cursor.moveToFirst();
                byte[] imageInByte = cursor.getBlob(cursor.getColumnIndex(EntryContract.Entry.IMAGE_COLUMN));
                Bitmap image;

                if(imageInByte!=null) {
                    image = BitmapFactory.decodeByteArray(imageInByte, 0, imageInByte.length);
                }else{
                    image = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);

                }
                    callBackMainFragment.sendpartNo(selectedItemPartNo);


                AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
                myview = getActivity().getLayoutInflater().inflate(R.layout.item_options_dialog,null);

                ImageView detailImageView = (ImageView) myview.findViewById(R.id.detail_imageView);
                detailImageView.setImageBitmap(image);
                builder.setView(myview);
                dialog = builder.create();
                dialog.show();
                OnDetailImageClickListener();
                OnUpdateImageClickListener();
                OnDeleteImageClickLisetener();
                OnSellTextClickLisetener();




            }
        });



    }

    private void OnSellTextClickLisetener() {
        TextView sellTextView = (TextView) myview.findViewById(R.id.sell_textView);
        sellTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View pickerView = getActivity().getLayoutInflater().inflate(R.layout.quantity_picker,null);

                final NumberPicker qtypicker = (NumberPicker) pickerView.findViewById(R.id.qty_numberpicker);
                qtypicker.setMinValue(1);
                qtypicker.setMaxValue(selectedItemQty);
                builder.setView(pickerView);


                builder.setPositiveButton("Sell", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        SalesRecordFragment frg = new SalesRecordFragment();
                        numberPickerSelectedQty = qtypicker.getValue();
                        AddRecordFromComponentsToSales();
                        UpdateQuantity();
                        PopulateListView();
                        dialog.dismiss();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.create().show();

            }
        });
    }

    private void PopulateListView() {
        list = (ListView) rootview.findViewById(R.id.items_listview);
        TextView emptyMessage = (TextView) rootview.findViewById(R.id.empty_listview_message);
        ArrayList<Description> arraylist = getInformation();


        emptyMessage.setVisibility(View.INVISIBLE);
        DescAdapter adapter = new DescAdapter(getActivity(), arraylist);
        list.setAdapter(adapter);
    }

    private void UpdateQuantity() {
        int remainingQty = selectedItemQty - numberPickerSelectedQty;
        workshopDbHelper.UpdateQuantity(selectedItemPartNo, remainingQty);
    }

    private void AddRecordFromComponentsToSales() {
        Cursor cursor = workshopDbHelper.RetrieveData(selectedItemPartNo);


        cursor.moveToFirst();

        String name = cursor.getString(cursor.getColumnIndex(EntryContract.Entry.NAME_COLUMN));
        String partNo = cursor.getString(cursor.getColumnIndex(EntryContract.Entry.PART_NO_COLUMN));
        int qty = numberPickerSelectedQty;
        String date = new SimpleDateFormat("yyy-MM-dd").format(new Date());



        salesDbHelper.AddRecord(name, partNo, qty, date);

    }

    private void OnDeleteImageClickLisetener() {
        ImageView deleteImage = (ImageView) myview.findViewById(R.id.delete_imageView);
        deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Do you want to Delete item?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        workshopDbHelper.deleteEntry(selectedItemPartNo);
                        onResume();
                        Toast.makeText(getActivity(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });
    }

    private void OnUpdateImageClickListener() {

        ImageView updateImage = (ImageView) myview.findViewById(R.id.update_imageView);
        updateImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                Intent intent = new Intent(getActivity(), AddItems.class);
                intent.putExtra("Isupdate", true);
                intent.putExtra("selectedPartNo", selectedItemPartNo);
                startActivity(intent);

            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        PopulateListView();
    }

    @Override
    public void onResume() {

        super.onResume();
        PopulateListView();



    }

    public ArrayList<Description> getInformation(){

        ArrayList<Description> DescArraylist = new ArrayList<>();
        workshopDbHelper = new WorkshopDbHelper(getActivity());
        cursor = workshopDbHelper.RetrieveData();

        cursor.moveToFirst();
        while(cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(EntryContract.Entry.NAME_COLUMN));
            String partNo = cursor.getString(cursor.getColumnIndex(EntryContract.Entry.PART_NO_COLUMN));
            int qty = cursor.getInt(cursor.getColumnIndex(EntryContract.Entry.QUANTITY_COLUMN));
            String price = cursor.getString(cursor.getColumnIndex(EntryContract.Entry.PRICE_COLUMN));
            byte[] imageinbyte = cursor.getBlob(cursor.getColumnIndex(EntryContract.Entry.IMAGE_COLUMN));

            if(imageinbyte!=null) {
                Bitmap imageBitmap = BitmapFactory.decodeByteArray(imageinbyte, 0, imageinbyte.length);
                DescArraylist.add(new Description(imageBitmap, name, partNo, price, qty));
            }else{
                Bitmap bmp = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);

                DescArraylist.add(new Description(bmp, name, partNo, price, qty));

            }
        }
        return DescArraylist;
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

//        inflater.inflate(R.menu.main_menu, menu);
//
//        MenuItemCompat.OnActionExpandListener actionExpandListener = new MenuItemCompat.OnActionExpandListener() {
//            @Override
//            public boolean onMenuItemActionExpand(MenuItem item) {
//                return true;
//            }
//
//            @Override
//            public boolean onMenuItemActionCollapse(MenuItem item) {
//                return true;
//            }
//        };
//
//        MenuItem searchitem = menu.findItem(R.id.action_search);
//        MenuItemCompat.setOnActionExpandListener(searchitem,actionExpandListener);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

//        int id = item.getItemId();
//        switch (id){
//            case R.id.action_add:{
//                Intent intent = new Intent(getActivity(), AddItems.class);
//                intent.putExtra("Isupdate", false);
//                startActivity(intent);
//            }
//            case R.id.action_salesrecords:{
//                callBackMainFragment.displaySalesRecordFragment();
//            }
//        }
        return false;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callBackMainFragment = (CallBackMainFragment) context;
    }
}
