package com.example.keepnotes.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.keepnotes.models.Notes;
import com.example.keepnotes.params.Params;

import java.util.ArrayList;
import java.util.List;

public class DbHandler extends SQLiteOpenHelper {

    public DbHandler(Context context) {
        super(context, Params.DB_NAME, null, Params.DB_VERSION);
    }
    public static final String TAG = "Sharath";

    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.d(TAG, "Create Cur Table being run is : " + Params.CREATE_TABLE_CUR_NOTES);
        db.execSQL(Params.CREATE_TABLE_CUR_NOTES);
        Log.d(TAG, "Create Cur Table Successful : " + Params.CREATE_TABLE_CUR_NOTES);

        Log.d(TAG, "Create Trash Table being run is : " + Params.CREATE_TABLE_TRASH_NOTES);
        db.execSQL(Params.CREATE_TABLE_TRASH_NOTES);
        Log.d(TAG, "Create Trash Table Successful : " + Params.CREATE_TABLE_TRASH_NOTES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long addNoteToCurNotes (Notes notes) {
        long affectedRows;
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Params.KEY_NOTE_TEXT, notes.getText());

        affectedRows = db.insert(Params.TABLE_NAME_CUR_NOTES, null, values);
        db.close();

        Log.d(TAG, "Successfully Inserted To Cur Notes" + notes.toString());

        return affectedRows;
    }

    public long addNoteToTrashNotes (Notes notes) {
        long affectedRows;
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Params.KEY_NOTE_TEXT, notes.getText());

        affectedRows = db.insert(Params.TABLE_NAME_TRASH_NOTES, null, values);
        db.close();

        Log.d(TAG, "Successfully Inserted To Trash Notes" + notes.toString());

        return affectedRows;
    }

    public Notes getCurNoteById (int id) {
        Notes notes = new Notes();
        boolean set = false;

        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                Params.KEY_ID,
                Params.KEY_NOTE_TEXT
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = Params.KEY_ID + " = ?";
        String[] selectionArgs = { String.valueOf(id) };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                Params.KEY_ID + " ASC";

        Cursor cursor = db.query(
                Params.TABLE_NAME_CUR_NOTES,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );


//        Cursor cursor = db.rawQuery(Params.SELECT_ALL_CUR_NOTES, null);
        if (cursor.moveToFirst()) {
            do {
                notes.setId(Integer.parseInt(cursor.getString(0)));
                notes.setText(cursor.getString(1));
                set = true;
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        if (!set) {
            return null;
        }
        return notes;
    }

    public List<Notes> getAllCurNotes () {
        List<Notes> notesList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(Params.SELECT_ALL_CUR_NOTES, null);

        if (cursor.moveToFirst()) {
            do {
                Notes notes = new Notes();
                notes.setId(Integer.parseInt(cursor.getString(0)));
                notes.setText(cursor.getString(1));
                notesList.add(notes);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return notesList;
    }

    public List<Notes> getAllTrashNotes () {
        List<Notes> notesList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(Params.SELECT_ALL_TRASH_NOTES, null);

        if (cursor.moveToFirst()) {
            do {
                Notes notes = new Notes();
                notes.setId(Integer.parseInt(cursor.getString(0)));
                notes.setText(cursor.getString(1));
                notesList.add(notes);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return notesList;
    }

    public int getCurCount () {
        int count;
        SQLiteDatabase db  = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(Params.SELECT_ALL_CUR_NOTES, null);

        count = cursor.getCount();

        cursor.close();
        db.close();

        return count;
    }

    public int getTrashCount () {
        int count;
        SQLiteDatabase db  = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(Params.SELECT_ALL_TRASH_NOTES, null);

        count = cursor.getCount();

        cursor.close();
        db.close();

        return count;
    }

    public int updateCurNote (Notes notes) {
        int affectedRows;
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Params.KEY_NOTE_TEXT, notes.getText());

        affectedRows = db.update(Params.TABLE_NAME_CUR_NOTES, values, Params.KEY_ID + " = ?",
                new String[]{String.valueOf(notes.getId())});

        db.close();

        return affectedRows;
    }

    public int deleteCurNotes (int id) {
        int affectedRows;
        SQLiteDatabase db = this.getWritableDatabase();

        affectedRows = db.delete(Params.TABLE_NAME_CUR_NOTES, Params.KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();

        return affectedRows;
    }

    public int deleteTrashNotes (int id) {
        int affectedRows;
        SQLiteDatabase db = this.getWritableDatabase();

        affectedRows = db.delete(Params.TABLE_NAME_TRASH_NOTES, Params.KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();

        return affectedRows;
    }

    public boolean deleteAllTrashNotes () {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("delete from "+ Params.TABLE_NAME_TRASH_NOTES);

        db.close();

        return true;
    }

}
