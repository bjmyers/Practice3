package com.example.practice3.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.practice3.product.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "product_database";
    private static final int DATABASE_VERSION = 1;
    private static final String PRODUCTS_TABLE = "products";
    private static final String ID_KEY = "id";
    private static final String NAME_KEY = "name";
    private static final String DESCRIPTION_KEY = "description";
    private static final String SELLER_KEY = "year";
    private static final String PRICE_KEY = "rating";
    private static final String IMAGE_KEY = "image";

    public ProductDatabaseHelper(final Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(initializeTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // No migrations to make yet
        return;
    }

    private String initializeTable() {
        return "CREATE TABLE " + PRODUCTS_TABLE + "(" +
                ID_KEY + " INTEGER PRIMARY KEY, "+
                NAME_KEY + " TEXT," +
                DESCRIPTION_KEY + " TEXT," +
                SELLER_KEY + " TEXT," +
                PRICE_KEY + " REAL" +
                IMAGE_KEY + " INTEGER" +
                ")";
    }

    public List<Product> getAllProducts() {
        final List<Product> products = new ArrayList<>();
        // Select all products
        final String selectQuery = "SELECT * FROM " + PRODUCTS_TABLE;
        final SQLiteDatabase database = this.getWritableDatabase();
        final Cursor cursor = database.rawQuery(selectQuery, null);

        // Iterate over all products, build them and put them into the list
        if (cursor.moveToFirst()) {
            do {
                final Product product = new Product(
                        cursor.getInt(0), // ID
                        cursor.getString(1), // NAME
                        cursor.getString(2), // DESC
                        cursor.getString(3), // SELLER
                        cursor.getFloat(4), // PRICE
                        cursor.getInt(5) // Image ID
                );
                products.add(product);
                final boolean isFirst = cursor.isFirst();
                final boolean isLast = cursor.isLast();
                int i = 0;
            } while (cursor.moveToNext());
        }

        // Close connections and return
        cursor.close();
        database.close();
        return products;
    }

    public boolean isDatabaseEmpty() {
        boolean isEmpty = true;
        SQLiteDatabase database = getWritableDatabase();
        // Select all products
        Cursor cursor = database.rawQuery("SELECT COUNT(*) FROM " + PRODUCTS_TABLE, null);
        if (cursor != null) {
            cursor.moveToFirst();
            // Find the value of the first column
            int count = cursor.getInt(0);
            if (count > 0) {
                isEmpty = false;
            }
            cursor.close();
        }
        return isEmpty;
    }

    public void populateMoviesDatabase(){
        // Build a bunch of initial products and put them into the database
        SQLiteDatabase database = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(ID_KEY, 1);
        values.put(NAME_KEY, "Civic");
        values.put(DESCRIPTION_KEY, "Sedan");
        values.put(SELLER_KEY, "Honda");
        values.put(PRICE_KEY, 10000.0);
        database.insert(PRODUCTS_TABLE, null, values);

        values = new ContentValues();
        values.put(ID_KEY, 2);
        values.put(NAME_KEY, "Escape");
        values.put(DESCRIPTION_KEY, "SUV");
        values.put(SELLER_KEY, "Ford");
        values.put(PRICE_KEY, 15000.99);
        database.insert(PRODUCTS_TABLE, null, values);

        values = new ContentValues();
        values.put(ID_KEY, 3);
        values.put(NAME_KEY, "Ram");
        values.put(DESCRIPTION_KEY, "Pickup truck");
        values.put(SELLER_KEY, "Dodge");
        values.put(PRICE_KEY, 23556.5555);
        database.insert(PRODUCTS_TABLE, null, values);

        database.close();
    }

}
