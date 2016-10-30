package Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.database.Cursor;
import android.widget.TextView;


import com.example.hammad.workshopinventory.R;

import Database.WorkshopDbHelper;
import Database.EntryContract;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {

    WorkshopDbHelper workshopDbHelper;
    Cursor cursor;
    View rootview;
    CallbackDetailFragment callbackDetailFragment;

    public DetailFragment() {
        // Required empty public constructor
    }

    public interface CallbackDetailFragment{
        public String getPartNo();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootview = inflater.inflate(R.layout.fragment_detail, container, false);
        retrieveInfo();

        return rootview;
    }

    private void retrieveInfo() {
        workshopDbHelper = new WorkshopDbHelper(getActivity());
        String requiredPartNo = callbackDetailFragment.getPartNo();
        cursor = workshopDbHelper.RetrieveData(requiredPartNo);

        cursor.moveToFirst();

            String name = cursor.getString(cursor.getColumnIndex(EntryContract.Entry.NAME_COLUMN));
            String partNo = cursor.getString(cursor.getColumnIndex(EntryContract.Entry.PART_NO_COLUMN));
            int qty = cursor.getInt(cursor.getColumnIndex(EntryContract.Entry.QUANTITY_COLUMN));
            String price = cursor.getString(cursor.getColumnIndex(EntryContract.Entry.PRICE_COLUMN));
            String redNo = cursor.getString(cursor.getColumnIndex(EntryContract.Entry.REFERENCE_NO_COLUMN));
            String pubNo = cursor.getString(cursor.getColumnIndex(EntryContract.Entry.PUBLICATION_NO_COLUMN));
            String supplier = cursor.getString(cursor.getColumnIndex(EntryContract.Entry.SUPPLIER_COLUMN));
            String date = cursor.getString(cursor.getColumnIndex(EntryContract.Entry.DATE_COLUMN));

            TextView nameTextView = (TextView) rootview.findViewById(R.id.Name_textview);
            TextView partNoTextView = (TextView) rootview.findViewById(R.id.partNo_textView);
            TextView refNoTextView = (TextView) rootview.findViewById(R.id.refNo_textView);
            TextView pubNoTextView = (TextView) rootview.findViewById(R.id.pubNo_textView);
            TextView qtyTextView = (TextView) rootview.findViewById(R.id.qty_textView);
            TextView priceTextView = (TextView) rootview.findViewById(R.id.price_textView);
            TextView supplierTextView = (TextView) rootview.findViewById(R.id.supplier_textView);
            TextView dateTextView = (TextView) rootview.findViewById(R.id.date_textview);


            nameTextView.setText(String.format("Name:\t%s", name));
            partNoTextView.setText(String.format("Part No:\t%s", partNo));
            refNoTextView.setText(String.format("Reference No:\t%s", redNo));
            pubNoTextView.setText(String.format("Publication No:\t%s", pubNo));
            qtyTextView.setText(String.format("Quantity:\t%s", qty));
            priceTextView.setText(String.format("Price:\t%s", price));
            supplierTextView.setText(String.format("Supplier:\t%s", supplier));
            dateTextView.setText(String.format("Last Update:\t%s", date));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callbackDetailFragment = (CallbackDetailFragment) context;
    }



}

