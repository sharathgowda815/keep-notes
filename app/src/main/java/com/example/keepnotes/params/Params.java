package com.example.keepnotes.params;

public class Params {

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "notes_db.db";

    public static final String TABLE_NAME_CUR_NOTES = "cur_notes_table";
    public static final String TABLE_NAME_TRASH_NOTES = "trash_notes_table";

    public static final String KEY_ID = "id";
    public static final String KEY_NOTE_TEXT = "note_text";

    public static final String CREATE_TABLE_CUR_NOTES =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_CUR_NOTES + "("
                    + KEY_ID + " INTEGER PRIMARY KEY, "
                    + KEY_NOTE_TEXT + " TEXT" + ");";

    public static final String CREATE_TABLE_TRASH_NOTES =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_TRASH_NOTES + "("
                    + KEY_ID + " INTEGER PRIMARY KEY, "
                    + KEY_NOTE_TEXT + " TEXT" + ");";

    public static final String SELECT_ALL_CUR_NOTES = "SELECT * FROM " + TABLE_NAME_CUR_NOTES;
    public static final String SELECT_ALL_TRASH_NOTES = "SELECT * FROM " + TABLE_NAME_TRASH_NOTES;

}
