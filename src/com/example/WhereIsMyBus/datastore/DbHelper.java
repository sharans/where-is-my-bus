package com.example.WhereIsMyBus.datastore;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.WhereIsMyBus.BusDetails;

public class DbHelper extends SQLiteOpenHelper {

    public static final String BUS_DETAILS_TABLE = "bus_details";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_BUS_NUM = "bus_number";
    public static final String COLUMN_SOURCE = "source";
    public static final String COLUMN_DEST = "destination";
    private static final String LOGCAT = null;
    private static final String DATABASE_NAME = "busDetails";
    private static final int DATABASE_VERSION = 1;

    public DbHelper(Context context, String name,
                               SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

//    public DbHelper(Context context){
//        super(context, "bus_details.db", null, 1);
//        db.execSQL("CREATE TABLE IF NOT EXISTS" + BUS_DETAILS_TABLE + "("
//                + COLUMN_ID + " INTEGER PRIMARY KEY, "
//                + COLUMN_BUS_NUM + " TEXT, "
//                + COLUMN_SOURCE + " TEXT, "
//                + COLUMN_DEST + " TEXT)");
//
//        Log.d(LOGCAT, "Created");
//    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + BUS_DETAILS_TABLE + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_BUS_NUM + " TEXT, "
                + COLUMN_SOURCE + " TEXT, "
                + COLUMN_DEST + " TEXT)");
        Log.d(LOGCAT, "Inside onCreate db");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table "+ BUS_DETAILS_TABLE);
        onCreate(db);
    }

    public BusDetails getBusDetails(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("select " + COLUMN_SOURCE + ", "+
                                                COLUMN_DEST + ", " +
                                                COLUMN_BUS_NUM +
                                    " from " + BUS_DETAILS_TABLE, null);
        if (cursor != null)
            cursor.moveToFirst();
        db.close();
        return new BusDetails(cursor.getString(0), cursor.getString(1), cursor.getString(2));
    }


    public void insertOrUpdateBusDetails(String source, String destination, String busNum) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_ID,1);
        values.put(COLUMN_BUS_NUM,busNum);
        values.put(COLUMN_SOURCE,source);
        values.put(COLUMN_DEST,destination );

        // Inserting Row
        int id = (int) db.insertWithOnConflict(BUS_DETAILS_TABLE, null, values, SQLiteDatabase.CONFLICT_IGNORE);
        if (id == -1) {
            db.update(BUS_DETAILS_TABLE, values, COLUMN_ID + "=?", new String[] {String.valueOf(1)});
        }

        db.close(); // Closing database connection
    }

}