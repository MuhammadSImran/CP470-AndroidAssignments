package com.example.androidassignments;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ChatDatabaseHelperTest {

    private ChatDatabaseHelper dbHelper;
    private SQLiteDatabase db;

    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        dbHelper = new ChatDatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    @After
    public void tearDown() {
        dbHelper.close();
        db.close();
    }

    @Test
    public void testDatabaseCreation() {
        assertTrue(db.isOpen());
    }

    @Test
    public void testInsertAndRetrieveMessage() {
        ContentValues values = new ContentValues();
        values.put(ChatDatabaseHelper.KEY_MESSAGE, "Hello, World!");

        long newRowId = db.insert(ChatDatabaseHelper.TABLE_NAME, null, values);
        assertTrue(newRowId != -1);

        Cursor cursor = db.query(ChatDatabaseHelper.TABLE_NAME, null, null, null, null, null, null);
        assertTrue(cursor.moveToFirst());

        String retrievedMessage = cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE));
        assertEquals("Hello, World!", retrievedMessage);

        cursor.close();
    }

    @Test
    public void testUpgradeDropsTable() {
        int oldVersion = db.getVersion();
        dbHelper.onUpgrade(db, oldVersion, oldVersion + 1);
        Cursor cursor = db.query(ChatDatabaseHelper.TABLE_NAME, null, null, null, null, null, null);
        assertEquals(0, cursor.getCount());
        cursor.close();
    }
}

