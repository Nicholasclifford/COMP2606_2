package com.example.product_management;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ProductManagementDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "product_management"; // the name of our database
    private static final int DB_VERSION = 1; // the version of the database

    ProductManagementDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Product ("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "Name TEXT, "
                + "StockOnHand INTEGER, "
                + "StockInTransit INTEGER, "
                + "Price REAL,"
                + "ReorderQuantity INTEGER,"
                + "ReorderAmount INTEGER);");

        insertproduct(db,"nails", 20,0,5.30f,0,0);
        insertproduct(db,"hammer",100,0,6.00f,0,0);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabase(db, oldVersion, newVersion);

    }

    public static void insertproduct(SQLiteDatabase db, String name, int onhand, int ontransit, float price, int quanity, int amount) {
        ContentValues product =new ContentValues();
        product.put("Name",name);
        product.put("StockOnHand",onhand);
        product.put("StockInTransit",ontransit);
        product.put("Price",price);
        product.put("ReorderQuantity",quanity);
        product.put("ReorderAmount",amount);
        db.insert("Product",null,product);
    }

    public void updateMyDatabase(SQLiteDatabase db,int oldVersion, int newVersion)
    {
        if(oldVersion<1)
        {
            db.execSQL("CREATE TABLE Product ("
                    + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "Name TEXT, "
                    + "StockOnHand INTEGER, "
                    + "StockInTransit INTEGER, "
                    + "Price REAL,"
                    + "ReorderQuantity INTEGER,"
                    + "ReorderAmount INTEGER);");

            insertproduct(db,"nails", 20,0,5.30f,0,0);
            insertproduct(db,"hammer",100,0,6.00f,0,0);

        }
        if(oldVersion<2)
        {
            db.execSQL("Alter table Product add column DIRTYBIT default 'FALSE'");
        }
    }
}
