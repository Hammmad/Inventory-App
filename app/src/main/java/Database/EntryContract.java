package Database;

import android.provider.BaseColumns;

/**
 * Created by shekh chilli on 10/23/2016.
 */

public class EntryContract {

    public EntryContract() {
    }

    public class Entry implements BaseColumns{

        public static final String TABLE_NAME = "Components";
        public static final String NAME_COLUMN = "Name";
        public static final String PART_NO_COLUMN = "PartNo";
        public static final String REFERENCE_NO_COLUMN = "ReferenceNo";
        public static final String PUBLICATION_NO_COLUMN = "PublicationNo";
        public static final String SUPPLIER_COLUMN = "Supplier";
        public static final String DATE_COLUMN = "Date";
        public static final String QUANTITY_COLUMN= "Quantity";
        public static final String PRICE_COLUMN = "Price";
        public static final String IMAGE_COLUMN = "Image";

    }


}
