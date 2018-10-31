package tn.dwc.smartshopping.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import tn.dwc.smartshopping.Entities.HistoryShoppingLists;
import tn.dwc.smartshopping.Entities.ShoppingList;

public class HistoryShoppingListBDD {

    private static final int VERSION_BDD = 3;
    private static final String NAME_BDD = "historyshoppinglist.db";

    private SQLiteDatabase bdd;

    private DBHelper dbHelper;

    public HistoryShoppingListBDD(Context context) {
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

    public long insertTop(HistoryShoppingLists dt) {

        // TODO Add Article to data base
        ContentValues values = new ContentValues();
        values.put(DBHelper.SHOPPING_List_NAME, dt.getShopping_list_name());
        values.put(DBHelper.SHOPPING_PRODUCT_ID, dt.getShopping_Product_id());
        values.put(DBHelper.SHOPPING_PRODUCT_SHEVE_ID, dt.getShopping_Product_sheve_id());
        values.put(DBHelper.SHOPPING_PRODUCT_NAME, dt.getShopping_Product_name());
        values.put(DBHelper.SHOPPING_PRODUCT_IMAGE, dt.getShopping_Product_image());
        values.put(DBHelper.SHOPPING_PRODUCT_CATEGORY, dt.getShopping_Product_category());

        values.put(DBHelper.SHOPPING_PRODUCT_PRICE, dt.getShopping_Product_price());
        values.put(DBHelper.SHOPPING_PRODUCT_CREATED_AT, dt.getShopping_Product_created_at());
        values.put(DBHelper.SHOPPING_PRODUCT_STATE, dt.getShopping_product_state());


        bdd.insert(DBHelper.TABLE_HISTORY_SHOPPING_LIST, null, values);

        return 0;
    }

    public int UpdateById(String id){

        bdd.execSQL("UPDATE " + DBHelper.TABLE_HISTORY_SHOPPING_LIST + " SET "+DBHelper.SHOPPING_PRODUCT_STATE+" = 'true' WHERE " + DBHelper.SHOPPING_PRODUCT_ID  +" = '"+id+"'");
        return 0;
    }
    public int UpdateById1(String id){

        bdd.execSQL("UPDATE " + DBHelper.TABLE_HISTORY_SHOPPING_LIST + " SET "+DBHelper.SHOPPING_PRODUCT_STATE+" = 'false' WHERE " + DBHelper.SHOPPING_PRODUCT_ID  +" = '"+id+"'");
        return 0;
    }
    public int removeById(String id){

        bdd.execSQL("DELETE FROM " + DBHelper.TABLE_HISTORY_SHOPPING_LIST + " WHERE " + DBHelper.SHOPPING_PRODUCT_ID  +" = '"+id+"'");
        return 0;
    }
    public int removeAllArticles() { // TODO Remove all Table
        bdd.execSQL("DELETE FROM " + DBHelper.TABLE_HISTORY_SHOPPING_LIST);
        return 0;
    }

    public List<ShoppingList> selectAll() {
        List<ShoppingList> list = new ArrayList<ShoppingList>();
        // TODO Get list of Article
        Cursor cursor = bdd.query(DBHelper.TABLE_HISTORY_SHOPPING_LIST, new String[] {

                        DBHelper.SHOPPING_List_NAME,  DBHelper.SHOPPING_PRODUCT_ID, DBHelper.SHOPPING_PRODUCT_SHEVE_ID, DBHelper.SHOPPING_PRODUCT_NAME, DBHelper.SHOPPING_PRODUCT_IMAGE,DBHelper.SHOPPING_PRODUCT_CATEGORY,
                        DBHelper.SHOPPING_PRODUCT_PRICE, DBHelper.SHOPPING_PRODUCT_CREATED_AT , DBHelper.SHOPPING_PRODUCT_STATE}, null, null, null, null,
                null);

        if (cursor.moveToFirst()) {
            do {
                ShoppingList p = new ShoppingList(cursor.getString(0),
                        cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getBlob(4), cursor.getString(5),
                        cursor.getString(6), cursor.getString(7),cursor.getString(8));
                list.add(p);
            } while (cursor.moveToNext());
        }
        return list;
    }
}
