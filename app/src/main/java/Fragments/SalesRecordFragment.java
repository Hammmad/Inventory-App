package Fragments;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hammad.workshopinventory.Description;
import com.example.hammad.workshopinventory.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import Adapter.DescAdapter;
import Database.EntryContract;
import Database.SalesContract;
import Database.SalesDbHelper;
import Database.WorkshopDbHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class SalesRecordFragment extends Fragment {

    WorkshopDbHelper workshopDbHelper;
    SalesDbHelper salesDbHelper;
    View rootView;

    Cursor cursor;


    public SalesRecordFragment() {
        // Required empty public constructor
    }

    public interface CallbackSalesFragment{

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        rootView = inflater.inflate(R.layout.fragment_main, container,false);


        salesDbHelper = new SalesDbHelper(getActivity());
            ArrayList<Description> arrayList = PopulateListView();
        ListView listView = (ListView) rootView.findViewById(R.id.items_listview);
        DescAdapter adapter = new DescAdapter(getActivity(),arrayList);

        listView.setAdapter(adapter);




        return rootView;
    }

    public ArrayList<Description> PopulateListView(){

        ArrayList<Description> descriptionArrayList = new ArrayList<>();

        cursor = salesDbHelper.RetrieveSalesRecords();
        cursor.moveToFirst();
        while (cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex(SalesContract.Sales.NAME_COLUMN));
            String partNo = cursor.getString(cursor.getColumnIndex(SalesContract.Sales.PART_NO_COLUMN));
            int qty = cursor.getInt(cursor.getColumnIndex(SalesContract.Sales.QUANTITY_COLUMN));
            String date = cursor.getString(cursor.getColumnIndex(SalesContract.Sales.DATE_COLUMN));



            descriptionArrayList.add(new Description(name,partNo,qty,date));
        }
        return descriptionArrayList;


    }


}

