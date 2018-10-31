package com.example.prime.fbmmk.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

import com.example.prime.fbmmk.Model.Fb;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prime on 10/29/18.
 */

public class Databasehelper extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "fb_db";



    public Databasehelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);


    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // create notes table
        db.execSQL(Fb.CREATE_TABLE);

    }
    // Upgrading database

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Fb.TABLE_NAME);
        // Create tables again
        onCreate(db);

    }
    public long insertUser(String firstname, String lastname, String password, String phone,
                           String email) throws SQLException
    {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Fb.COLUMN_FIRSTNAME,  firstname);
        values.put(Fb.COLUMN_LASTNAME,  lastname);
        values.put(Fb.COLUMN_PASSWORD,  password);
        values.put(Fb.COLUMN_PHONE,  phone);
        values.put(Fb.COLUMN_EMAIL,  email);
        // insert row
        long id = db.insert(Fb.TABLE_NAME, null, values);
        // close db connection
        db.close();
        // return newly inserted row id
        return id;
    }
    public Fb getUser(String email)throws SQLException
                {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Fb.TABLE_NAME,
                new String[]{Fb.COLUMN_EMAIL, Fb.COLUMN_PASSWORD},
                Fb.COLUMN_EMAIL + "=?", new String[]{String.valueOf(email)}, null,
                null,null, null);
        if (cursor != null)
            cursor.moveToFirst();
            // prepare note object
            Fb fb = new Fb(
                    cursor.getInt(cursor.getColumnIndex(Fb.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(Fb.COLUMN_FIRSTNAME)),
                    cursor.getString(cursor.getColumnIndex(Fb.COLUMN_LASTNAME)),
                    cursor.getString(cursor.getColumnIndex(Fb.COLUMN_EMAIL)),
                    cursor.getString(cursor.getColumnIndex(Fb.COLUMN_PHONE)),
                    cursor.getString(cursor.getColumnIndex(Fb.COLUMN_PASSWORD))
            );
            // close the db connection
            cursor.close();
            return fb;

    }
//    public List<Fb> getAllNote(){
//        List<Fb> notes = new ArrayList<>();
//
//        // Select All Query
//
//        String selectQuery = "SELECT * FROM " + Fb.TABLE_NAME + " ORDER BY "
//                + Fb.COLUMN_TIMESTAMP + " DESC";
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()) {
//            do {
//                Note note = new Note();
//                note.setId(cursor.getInt(cursor.getColumnIndex(Note.COLUMN_ID)));
//                note.setNote(cursor.getString(cursor.getColumnIndex(Note.COLUMN_NOTE)));
//                note.setTimestamp(cursor.getString(cursor.getColumnIndex(Note.COLUMN_TIMESTAMP)));
//                notes.add(note);
//
//            }while (cursor.moveToNext());
//
//        }
//        // close db connection
//        db.close();
//        // return notes list
//        return notes;
//    }
    public int updateUser(Fb fb){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Fb.COLUMN_PASSWORD, fb.getPassword());
        // updating row
        return db.update(Fb.TABLE_NAME, values, Fb.COLUMN_ID + " = ?",
                new String[]{String.valueOf(fb.getId())});
    }
//    public void deleteNote(Note note){
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(Note.TABLE_NAME, Note.COLUMN_ID + " = ?",
//                new String[]{String.valueOf(note.getId())});
//        db.close();
//    }
    public int getNoteCount(){
        String countQuery=" SELECT * FROM " + Fb.TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        // return count
        return count;

    }

}
