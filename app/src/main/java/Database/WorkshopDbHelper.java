package Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by shekh chilli on 10/23/2016.
 */

public class WorkshopDbHelper extends SQLiteOpenHelper {

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE "+ EntryContract.Entry.TABLE_NAME+" ("
            + EntryContract.Entry._ID+" INTEGER PRIMARY KEY,"
            + EntryContract.Entry.NAME_COLUMN+" TEXT,"
            + EntryContract.Entry.PART_NO_COLUMN+" INTEGER,"
            + EntryContract.Entry.REFERENCE_NO_COLUMN+" INTEGER,"
            + EntryContract.Entry.PUBLICATION_NO_COLUMN+" INTEGER,"
            + EntryContract.Entry.SUPPLIER_COLUMN+" TEXT,"
            + EntryContract.Entry.QUANTITY_COLUMN+" INTEGER,"
            + EntryContract.Entry.PRICE_COLUMN+" INTEGER,"
            + EntryContract.Entry.DATE_COLUMN+ " TEXT,"
            + EntryContract.Entry.IMAGE_COLUMN+" BLOB"
            +" )";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS "+ EntryContract.Entry.TABLE_NAME;
    private static final String DATABASE_NAME = "Workshop.db";
    private static final int DATABASE_VERSION = 1;





    public WorkshopDbHelper(Context context) {
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

    public void InsertData(String name, String partNumber,String refNo, String pubNo, String supplier, int quantity,
                           String price,String date, byte[] image){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EntryContract.Entry.NAME_COLUMN,name);
        values.put(EntryContract.Entry.PART_NO_COLUMN, partNumber);
        values.put(EntryContract.Entry.REFERENCE_NO_COLUMN, refNo);
        values.put(EntryContract.Entry.PUBLICATION_NO_COLUMN, pubNo);
        values.put(EntryContract.Entry.SUPPLIER_COLUMN, supplier);
        values.put(EntryContract.Entry.QUANTITY_COLUMN, quantity);
        values.put(EntryContract.Entry.PRICE_COLUMN, price);
        values.put(EntryContract.Entry.DATE_COLUMN,date);
        values.put(EntryContract.Entry.IMAGE_COLUMN, image);
        db.insert(EntryContract.Entry.TABLE_NAME,null, values);
    }

    public Cursor RetrieveData(){

        SQLiteDatabase db = getReadableDatabase();

//        String[] projection ={
//                EntryContract.Entry._ID,
//                EntryContract.Entry.SUPPLIER_COLUMN,
//                EntryContract.Entry.PART_NO_COLUMN
//        };
//        String selection = EntryContract.Entry.PART_NO_COLUMN+" = ?";
//        String[] selectionArgs = {partnumber};


        String sql = String.format("SELECT * FROM %s", EntryContract.Entry.TABLE_NAME);


        return db.rawQuery(sql,null);
    }

    public Cursor RetrieveData(String partnumber){

        SQLiteDatabase db = getReadableDatabase();

//        String[] projection ={
//                EntryContract.Entry._ID,
//                EntryContract.Entry.SUPPLIER_COLUMN,
//                EntryContract.Entry.PART_NO_COLUMN
//        };
//        String selection = EntryContract.Entry.PART_NO_COLUMN+" = ?";
        String[] selectionArgs = {partnumber};


        String sql = String.format("SELECT * FROM %s WHERE %s = ?", EntryContract.Entry.TABLE_NAME,EntryContract.Entry.PART_NO_COLUMN);


        return db.rawQuery(sql,selectionArgs);
    }

    public Cursor RetrieveImage(String partnumber){

        SQLiteDatabase db = getReadableDatabase();

        String[] projection ={
                  EntryContract.Entry.PART_NO_COLUMN,
                    EntryContract.Entry.IMAGE_COLUMN
        };
        String selection = EntryContract.Entry.PART_NO_COLUMN+" = ? ";
        String[] selectionArgs = {partnumber};


//        String sql = String.format("SELECT * FROM %s WHERE %s = ?", EntryContract.Entry.TABLE_NAME,EntryContract.Entry.PART_NO_COLUMN);


        return db.query(EntryContract.Entry.TABLE_NAME,projection, selection, selectionArgs,null,null,null);
    }

    public void deleteEntry(String partnumber){
        SQLiteDatabase db = getReadableDatabase();

        String selection = EntryContract.Entry.PART_NO_COLUMN+" LIKE ? ";
        String[] selectionArgs = {partnumber};


        db.delete(EntryContract.Entry.TABLE_NAME,selection,selectionArgs);
    }

    public void updateEnrty(String name, String partNumber,String refNo, String pubNo, String supplier, int quantity,
                            String price,String date, byte[] image){

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(EntryContract.Entry.NAME_COLUMN,name);
        values.put(EntryContract.Entry.PART_NO_COLUMN, partNumber);
        values.put(EntryContract.Entry.REFERENCE_NO_COLUMN, refNo);
        values.put(EntryContract.Entry.PUBLICATION_NO_COLUMN, pubNo);
        values.put(EntryContract.Entry.SUPPLIER_COLUMN, supplier);
        values.put(EntryContract.Entry.QUANTITY_COLUMN, quantity);
        values.put(EntryContract.Entry.PRICE_COLUMN, price);
        values.put(EntryContract.Entry.DATE_COLUMN, date);
        values.put(EntryContract.Entry.IMAGE_COLUMN, image);

        String selection = EntryContract.Entry.PART_NO_COLUMN+" LIKE ? ";
        String[] selectionArgs = {partNumber};

        db.update(EntryContract.Entry.TABLE_NAME, values, selection,selectionArgs);
    }

    public void UpdateQuantity(String partNumber , int quantity){

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(EntryContract.Entry.QUANTITY_COLUMN, quantity);

        String selection = EntryContract.Entry.PART_NO_COLUMN+" LIKE ? ";
        String[] selectionArgs = {partNumber};

        db.update(EntryContract.Entry.TABLE_NAME, values, selection,selectionArgs);
    }
}
