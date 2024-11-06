package com.example.androidassignments;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ChatDatabaseHelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "Messages.db";
    public static final int VERSION_NUM = 2;

    public static final String TABLE_NAME = "Messages";
    public static final String KEY_ID = "id";
    public static final String KEY_MESSAGE = "MESSAGE";


    public ChatDatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.i("ChatDatabaseHelper", "Calling onCreate");

        String CREATE_MESSAGES_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_MESSAGE + " TEXT" + ");";

        db.execSQL(CREATE_MESSAGES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("ChatDatabaseHelper", "Calling onUpgrade, oldVersion=" + oldVersion + " newVersion=" + newVersion);
        String DROP_TABLE_SQL = " DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(DROP_TABLE_SQL);

        onCreate(db);
    }

}
