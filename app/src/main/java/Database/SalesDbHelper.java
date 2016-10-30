package Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;



/**
 * Created by shekh chilli on 10/28/2016.
 */

public class SalesDbHelper extends SQLiteOpenHelper {


    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE "+ SalesContract.Sales.TABLE_NAME+" ("
                    + SalesContract.Sales._ID+" INTEGER PRIMARY KEY,"
                    + SalesContract.Sales.NAME_COLUMN+" TEXT,"
                    + SalesContract.Sales.PART_NO_COLUMN+" INTEGER,"
                    + SalesContract.Sales.QUANTITY_COLUMN+" INTEGER,"
                    + SalesContract.Sales.DATE_COLUMN+" TEXT"
                    + " )";
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS "+ SalesContract.Sales.TABLE_NAME;

    private static final String DATABASE_NAME = "SalesRecord.db";
    private static final int DATABASE_VERSION= 2;

    public SalesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db,oldVersion,newVersion);
    }

    public void AddRecord(String name, String partNo, int qty, String date){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SalesContract.Sales.NAME_COLUMN,name);
        values.put(SalesContract.Sales.PART_NO_COLUMN,partNo);
        values.put(SalesContract.Sales.QUANTITY_COLUMN,qty);
        values.put(SalesContract.Sales.DATE_COLUMN,date);

        db.insert(SalesContract.Sales.TABLE_NAME, null , values);

    }

    public Cursor RetrieveSalesRecords(){

        SQLiteDatabase db = this.getReadableDatabase();

        String sql = String.format("SELECT * FROM %s", SalesContract.Sales.TABLE_NAME);

        return db.rawQuery(sql, null);
    }


}
