package tn.dwc.smartshopping.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;



import java.util.ArrayList;
import java.util.List;

import tn.dwc.smartshopping.Entities.Products;

public class ProductsBDD {

    private static final int VERSION_BDD = 3;
    private static final String NAME_BDD = "productss.db";

    private SQLiteDatabase bdd;

    private DBHelper dbHelper;

    public ProductsBDD(Context context) {
        super();
        dbHelper = new DBHelper(context, NAME_BDD, null, VERSION_BDD);
    }

    public void open() {
        // TODO Open Data Base
        bdd = dbHelper.getWritableDatabase();
    }

    public void close() {
        // TODO Close Data Base
        dbHelper.close();
    }

    public SQLiteDatabase getBDD() {
        return bdd;
    }

    public long insertTop(Products dt) {

        // TODO Add Article to data base
        ContentValues values = new ContentValues();

        values.put(DBHelper.PRODUCT_ID, dt.getProduct_id());
        values.put(DBHelper.PRODUCT_SHEVE_ID, dt.getProduct_sheve_id());
        values.put(DBHelper.PRODUCT_NAME, dt.getProduct_name());
        values.put(DBHelper.PRODUCT_DESC, dt.getProduct_desc());
        values.put(DBHelper.PRODUCT_IMAGE, dt.getProduct_image());
        values.put(DBHelper.PRODUCT_CATEGORY, dt.getProduct_category());
        values.put(DBHelper.PRODUCT_SUB_CATEGORY, dt.getProduct_sub_category());
        values.put(DBHelper.PRODUCT_PRICE, dt.getProduct_price());
        values.put(DBHelper.PRODUCT_CREATED_AT, dt.getProduct_created_at());



        bdd.insert(DBHelper.TABLE_PRODUCTS, null, values);

        return 0;
    }

    public int removeAllArticles() { // TODO Remove all Table
        bdd.execSQL("DELETE FROM " + DBHelper.TABLE_PRODUCTS);
        return 0;
    }

    public List<Products> selectAll() {
        List<Products> list = new ArrayList<Products>();
        // TODO Get list of Article
        Cursor cursor = bdd.query(DBHelper.TABLE_PRODUCTS, new String[] {

                        DBHelper.PRODUCT_ID, DBHelper.PRODUCT_SHEVE_ID, DBHelper.PRODUCT_NAME, DBHelper.PRODUCT_DESC,DBHelper.PRODUCT_IMAGE,DBHelper.PRODUCT_CATEGORY,
                        DBHelper.PRODUCT_SUB_CATEGORY, DBHelper.PRODUCT_PRICE, DBHelper.PRODUCT_CREATED_AT }, null, null, null, null,
                null);

        if (cursor.moveToFirst()) {
            do {
                Products p = new Products(cursor.getString(0),
                        cursor.getString(1), cursor.getString(2), cursor.getString(3),
                        cursor.getBlob(4), cursor.getString(5), cursor.getString(6),
                        cursor.getString(7), cursor.getString(8));
                list.add(p);
            } while (cursor.moveToNext());
        }
        return list;
    }
}
