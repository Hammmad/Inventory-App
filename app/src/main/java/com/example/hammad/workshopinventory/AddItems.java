package com.example.hammad.workshopinventory;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.database.Cursor;


import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import Database.WorkshopDbHelper;
import Database.EntryContract;

public class AddItems extends AppCompatActivity {

    private static final int REQUEST_CODE = 1;
    Bitmap bmp;
    byte[] image;
    WorkshopDbHelper workshopDbHelper = new WorkshopDbHelper(this);
    Spinner supplierSpinner;
    boolean isUpdate;
    ArrayAdapter<CharSequence> stringArrayAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_items);
        Toolbar toolbar = (Toolbar) findViewById(R.id.add_items_toolbar);
        setSupportActionBar(toolbar);

        populateSpinner();



        Bundle bundle = getIntent().getExtras();
        isUpdate = bundle.getBoolean("Isupdate");

        if(isUpdate){
            LoadDataIntoFields();
        }



        OnImageClickLisetener();

    }

    private void LoadDataIntoFields() {

        Bundle bundle = getIntent().getExtras();
        String partNumber = bundle.getString("selectedPartNo");
        Cursor cursor = workshopDbHelper.RetrieveData(partNumber);
        cursor.moveToFirst();

        String name = cursor.getString(cursor.getColumnIndex(EntryContract.Entry.NAME_COLUMN));
        String partNo = cursor.getString(cursor.getColumnIndex(EntryContract.Entry.PART_NO_COLUMN));
        int qty = cursor.getInt(cursor.getColumnIndex(EntryContract.Entry.QUANTITY_COLUMN));
        String price = cursor.getString(cursor.getColumnIndex(EntryContract.Entry.PRICE_COLUMN));
        String redNo = cursor.getString(cursor.getColumnIndex(EntryContract.Entry.REFERENCE_NO_COLUMN));
        String pubNo = cursor.getString(cursor.getColumnIndex(EntryContract.Entry.PUBLICATION_NO_COLUMN));
        String supplier = cursor.getString(cursor.getColumnIndex(EntryContract.Entry.SUPPLIER_COLUMN));

        EditText nameEditText = (EditText) findViewById(R.id.item_name_editText);
        EditText partnoEditText = (EditText) findViewById(R.id.part_no_editText);
        EditText refNoEdittext = (EditText) findViewById(R.id.ref_no_editText);
        EditText pubNoEditText = (EditText) findViewById(R.id.pub_no_editText);
        EditText qtyEditText = (EditText) findViewById(R.id.quantity_editText);
        EditText priceEditText = (EditText) findViewById(R.id.price_editText);
        Spinner supplierSpinner = (Spinner) findViewById(R.id.supplier_spinner);

        nameEditText.setText(name);
        partnoEditText.setText(partNo);
        refNoEdittext.setText(redNo);
        pubNoEditText.setText(pubNo);
        qtyEditText.setText(String.valueOf(qty));
        priceEditText.setText(price);
        supplierSpinner.setSelection(stringArrayAdapter.getPosition(supplier));
    }


    private void populateSpinner() {
        supplierSpinner = (Spinner) findViewById(R.id.supplier_spinner);

        stringArrayAdapter = ArrayAdapter.createFromResource(this,
                R.array.supplier_array,android.R.layout.simple_spinner_item);
        stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        supplierSpinner.setAdapter(stringArrayAdapter);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        
        int id = item.getItemId();
        switch(id){
            case R.id.action__save:{

                EditText nameEditText = (EditText) findViewById(R.id.item_name_editText);
                String name = String.valueOf(nameEditText.getText());
                EditText partnoEditText = (EditText) findViewById(R.id.part_no_editText);
                String partno = String.valueOf(partnoEditText.getText());
                EditText refNoEdittext = (EditText) findViewById(R.id.ref_no_editText);
                String refNo = String.valueOf(refNoEdittext.getText());
                EditText pubNoEditText = (EditText) findViewById(R.id.pub_no_editText);
                String pubNo = String.valueOf(pubNoEditText.getText());
                EditText qtyEditText = (EditText) findViewById(R.id.quantity_editText);
                int qty = Integer.parseInt(String.valueOf(qtyEditText.getText()));
                EditText priceEditText = (EditText) findViewById(R.id.price_editText);
                String price = String.valueOf(priceEditText.getText());
                String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                Log.e("DEBUDDING", date);
                String supplierName =supplierSpinner.getSelectedItem().toString();



                if(isUpdate)
                    workshopDbHelper.updateEnrty(name,partno,refNo,pubNo,supplierName,qty,price,date,image);
                if(!isUpdate)
                    workshopDbHelper.InsertData(name,partno,refNo,pubNo,supplierName,qty,price,date,image);
            }
        }
        
        
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

          getMenuInflater().inflate(R.menu.add_items_menu, menu);

        Bundle bundle = getIntent().getExtras();
        isUpdate = bundle.getBoolean("Isupdate");

        MenuItem item = (MenuItem) menu.findItem(R.id.action__save);
        if(isUpdate){

            item.setTitle("Update");
        }



        return true;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){

            Bundle extras = data.getExtras();
            bmp = (Bitmap) extras.get("data");


            ImageView itemImage = (ImageView) findViewById(R.id.item_imageview);

            if (itemImage != null) {
                itemImage.setImageBitmap(bmp);
                image = bitmapTobyte(bmp);
            }


        }
    }

    public void Capturephoto(){

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.withAppendedPath(mLocationfile,TargetFilename));

        if(intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intent,REQUEST_CODE);
        }

    }
    private void OnImageClickLisetener() {
        ImageView itemImage = (ImageView) findViewById(R.id.item_imageview);
        itemImage.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Capturephoto();

            }
        });

    }

    private byte[] bitmapTobyte(Bitmap bmp) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG,0,stream);
        return stream.toByteArray();

    }
}
