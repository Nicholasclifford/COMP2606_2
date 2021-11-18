package com.example.product_management;

import android.content.Context;
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
                + "StockInTransit INTEGER,"
                + "ReorderQuantity INTEGER);");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}
