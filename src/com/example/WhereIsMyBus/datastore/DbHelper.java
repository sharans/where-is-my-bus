package com.example.WhereIsMyBus.datastore;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "bus_details";
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
//        db.execSQL("CREATE TABLE IF NOT EXISTS" + TABLE_NAME + "("
//                + COLUMN_ID + " INTEGER PRIMARY KEY, "
//                + COLUMN_BUS_NUM + " TEXT, "
//                + COLUMN_SOURCE + " TEXT, "
//                + COLUMN_DEST + " TEXT)");
//
//        Log.d(LOGCAT, "Created");
//    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_BUS_NUM + " TEXT, "
                + COLUMN_SOURCE + " TEXT, "
                + COLUMN_DEST + " TEXT)");
        Log.d(LOGCAT, "Inside onCreate db");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table "+ TABLE_NAME);
        onCreate(db);
    }

    public void addRecord(String source, String destination, String busNum) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_ID,1);
        values.put(COLUMN_BUS_NUM,busNum);
        values.put(COLUMN_SOURCE,source);
        values.put(COLUMN_DEST,destination );

        // Inserting Row
        int id = (int) db.insertWithOnConflict(TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_IGNORE);
        if (id == -1) {
            db.update(TABLE_NAME, values, COLUMN_ID + "=?", new String[] {String.valueOf(1)});
        }

        db.close(); // Closing database connection
    }

}