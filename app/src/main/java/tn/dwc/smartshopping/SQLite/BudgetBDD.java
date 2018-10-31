package tn.dwc.smartshopping.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import tn.dwc.smartshopping.Entities.Budget;

public class BudgetBDD {

    private static final int VERSION_BDD = 2;
    private static final String NAME_BDD = "budget.db";

    private SQLiteDatabase bdd;

    private DBHelper dbHelper;

    public BudgetBDD(Context context) {
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

    public long insertTop(Budget dt) {

        // TODO Add Article to data base
        ContentValues values = new ContentValues();

        values.put(DBHelper.BUDGET, dt.getBudget());
        values.put(DBHelper.SPENT, dt.getSpent());





        bdd.insert(DBHelper.TABLE_BUDGET, null, values);

        return 0;
    }

    public int removeAllArticles() { // TODO Remove all Table
        bdd.execSQL("DELETE FROM " + DBHelper.TABLE_BUDGET);
        return 0;
    }

    public List<Budget> selectAll() {
        List<Budget> list = new ArrayList<Budget>();
        // TODO Get list of Article
        Cursor cursor = bdd.query(DBHelper.TABLE_BUDGET, new String[] {

                        DBHelper.BUDGET, DBHelper.SPENT}, null, null, null, null,
                null);

        if (cursor.moveToFirst()) {
            do {
                Budget p = new Budget(cursor.getString(0),
                        cursor.getString(1));
                list.add(p);
            } while (cursor.moveToNext());
        }
        return list;
    }
}
