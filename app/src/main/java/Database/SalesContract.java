package Database;

import android.provider.BaseColumns;

/**
 * Created by shekh chilli on 10/29/2016.
 */

public class SalesContract {

    public SalesContract(){

    }

    public class Sales implements BaseColumns {

        public static final String TABLE_NAME = "Record";
        public static final String NAME_COLUMN = "Name";
        public static final String PART_NO_COLUMN = "PartNo";
        public static final String DATE_COLUMN = "Date";
        public static final String QUANTITY_COLUMN= "QuantityRemaining";

    }
}
