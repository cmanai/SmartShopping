package tn.dwc.smartshopping.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import tn.dwc.smartshopping.Entities.Maps_Product;
import tn.dwc.smartshopping.Entities.Maps_Shelve;

public class Maps_ProductBDD {

    private static final int VERSION_BDD = 5;
    private static final String NAME_BDD = "maps_product.db";

    private SQLiteDatabase bdd;

    private DBHelper dbHelper;

    public Maps_ProductBDD(Context context) {
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

    public long insertTop(Maps_Product dt) {

        // TODO Add Article to data base
        ContentValues values = new ContentValues();

        values.put(DBHelper.SHELVE_ID_P, dt.getShelve_Id_p());
        values.put(DBHelper.MAP_P, dt.getMaps_p());

        values.put(DBHelper.PRODUCT_ID_P, dt.getProduct_Id_p());

        values.put(DBHelper.H_B_P, dt.getH_B_p());





        bdd.insert(DBHelper.TABLE_MAPS_PRODUCT, null, values);

        return 0;
    }

    public int removeAllArticles() { // TODO Remove all Table
        bdd.execSQL("DELETE FROM " + DBHelper.TABLE_MAPS_PRODUCT);
        return 0;
    }

    public List<Maps_Product> selectAll() {
        List<Maps_Product> list = new ArrayList<Maps_Product>();
        // TODO Get list of Article
        Cursor cursor = bdd.query(DBHelper.TABLE_MAPS_PRODUCT, new String[] {

                        DBHelper.SHELVE_ID_P, DBHelper.MAP_P,DBHelper.PRODUCT_ID_P,DBHelper.H_B_P}, null, null, null, null,
                null);

        if (cursor.moveToFirst()) {
            do {
                Maps_Product p = new Maps_Product(cursor.getString(0),
                        cursor.getBlob(1),cursor.getString(2),cursor.getString(3));
                list.add(p);
            } while (cursor.moveToNext());
        }
        return list;
    }
}
