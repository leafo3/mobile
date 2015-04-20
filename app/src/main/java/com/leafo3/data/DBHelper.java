package com.leafo3.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.leafo3.model.Leaf;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alberto Rubalcaba on 4/19/2015.
 */
public class DBHelper extends SQLiteOpenHelper{
    // Define the version and database file name
    private static final String DB_NAME = "leafmealone.db";
    private static final int DB_VERSION = 1;

    // Use a static class to defined the data structure
    // This will come in very handy if you using Agile
    // As your development model
    private static class LeafTable {
        private static final String NAME = "leaf";
        private static final String COL_ID = "id";
        private static final String COL_TITLE = "title";
        private static final String COL_COMMENT = "comment";
        private static final String COL_IMAGE_PATH = "image_path";
        private static final String COL_DAMAGE_CLASS= "damage_class";
        private static final String COL_LOCATION= "location";

    }

    private SQLiteDatabase db;

    // Constructor to simplify Business logic access to the repository
    public DBHelper(Context context) {

        super(context, DB_NAME, null, DB_VERSION);
        // Android will look for the database defined by DB_NAME
        // And if not found will invoke your onCreate method
        this.db = this.getWritableDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Android has created the database identified by DB_NAME
        // The new database is passed to you vai the db arg
        // Now it is up to you to create the Schema.
        // This schema creates a very simple user table, in order
        // Store user login credentials
        db.execSQL(String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT, %s INTEGER, %s TEXT)",
                LeafTable.NAME, LeafTable.COL_ID,
                LeafTable.COL_TITLE, LeafTable.COL_COMMENT,
                LeafTable.COL_IMAGE_PATH, LeafTable.COL_DAMAGE_CLASS,
                LeafTable.COL_LOCATION));

    }

    public void insertLeaf(Leaf leaf){
        ContentValues values = getContentValues(leaf);
        db.insert(LeafTable.NAME, null, values);
    }

    public List<Leaf> getAll(){
        List<Leaf> leafs = new ArrayList<Leaf>();
        Cursor cursor = null;
        try{
            cursor = db.rawQuery("SELECT * FROM " + LeafTable.NAME, new String[]{});
            if(cursor.moveToFirst()){
                do{
                    Leaf leaf = new Leaf();
                    leaf.setId(cursor.getInt(0));
                    leaf.setTitle(cursor.getString(1));
                    leaf.setComment(cursor.getString(2));
                    leaf.setImageUrl(cursor.getString(3));
                    leaf.setDamageClass(cursor.getInt(4));
                    leaf.setLocation(cursor.getString(4));

                    leafs.add(leaf);
                }while(cursor.moveToNext());
            }
        }catch(Exception ex){
            Log.e("DBHelper", "Error: " + ex.getMessage());
        }

        return leafs;
    }

    private ContentValues getContentValues(Leaf leaf) {
        ContentValues values = new ContentValues();
        values.put(LeafTable.COL_TITLE, leaf.getTitle());
        values.put(LeafTable.COL_COMMENT, leaf.getComment());
        values.put(LeafTable.COL_DAMAGE_CLASS, leaf.getDamageClass());
        values.put(LeafTable.COL_IMAGE_PATH, leaf.getImageUrl());
        values.put(LeafTable.COL_LOCATION, leaf.getLocation());
        return values;
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // Later when you change the DB_VERSION
        // This code will be invoked to bring your database
        // Upto the correct specification
    }

}
