package tn.dwc.smartshopping.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import tn.dwc.smartshopping.Entities.History;

public class HistoryBDD {

    private static final int VERSION_BDD = 2;
    private static final String NAME_BDD = "history.db";

    private SQLiteDatabase bdd;

    private DBHelper dbHelper;

    public HistoryBDD(Context context) {
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

    public long insertTop(History dt) {

        // TODO Add Article to data base
        ContentValues values = new ContentValues();

        values.put(DBHelper.HISTORY_NAME, dt.getHistory_Name());
        values.put(DBHelper.HISTORY_BUDGET, dt.getBudget());
        values.put(DBHelper.HISTORY_PRODUCTS_NUMBER, dt.getProducts_Number());
        values.put(DBHelper.HISTORY_CREATED_AT, dt.getCreation_Date());





        bdd.insert(DBHelper.TABLE_HISTORY, null, values);

        return 0;
    }

    public int removeAllArticles() { // TODO Remove all Table
        bdd.execSQL("DELETE FROM " + DBHelper.TABLE_HISTORY);
        return 0;
    }

    public List<History> selectAll() {
        List<History> list = new ArrayList<History>();
        // TODO Get list of Article
        Cursor cursor = bdd.query(DBHelper.TABLE_HISTORY, new String[] {

                        DBHelper.HISTORY_NAME, DBHelper.HISTORY_BUDGET, DBHelper.HISTORY_PRODUCTS_NUMBER, DBHelper.HISTORY_CREATED_AT}, null, null, null, null,
                null);

        if (cursor.moveToFirst()) {
            do {
                History p = new History(cursor.getString(0),
                        cursor.getString(1),cursor.getString(2),cursor.getString(3));
                list.add(p);
            } while (cursor.moveToNext());
        }
        return list;
    }
}
