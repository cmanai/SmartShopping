package tn.dwc.smartshopping.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;



import java.util.ArrayList;
import java.util.List;

import tn.dwc.smartshopping.Entities.Maps_Shelve;

public class Maps_ShelveBDD {

    private static final int VERSION_BDD = 3;
    private static final String NAME_BDD = "maps_shelve.db";

    private SQLiteDatabase bdd;

    private DBHelper dbHelper;

    public Maps_ShelveBDD(Context context) {
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

    public long insertTop(Maps_Shelve dt) {

        // TODO Add Article to data base
        ContentValues values = new ContentValues();

        values.put(DBHelper.SHELVE_ID, dt.getShelve_Id());
        values.put(DBHelper.MAP_SH, dt.getMaps_Sh());

        values.put(DBHelper.QR_ID, dt.getQr_ID());

        values.put(DBHelper.H_B, dt.getH_B());





        bdd.insert(DBHelper.TABLE_MAPS_SHELVE, null, values);

        return 0;
    }

    public int removeAllArticles() { // TODO Remove all Table
        bdd.execSQL("DELETE FROM " + DBHelper.TABLE_MAPS_SHELVE);
        return 0;
    }

    public List<Maps_Shelve> selectAll() {
        List<Maps_Shelve> list = new ArrayList<Maps_Shelve>();
        // TODO Get list of Article
        Cursor cursor = bdd.query(DBHelper.TABLE_MAPS_SHELVE, new String[] {

                        DBHelper.SHELVE_ID, DBHelper.MAP_SH,DBHelper.QR_ID,DBHelper.H_B}, null, null, null, null,
                null);

        if (cursor.moveToFirst()) {
            do {
                Maps_Shelve p = new Maps_Shelve(cursor.getString(0),
                        cursor.getBlob(1),cursor.getString(2),cursor.getString(3));
                list.add(p);
            } while (cursor.moveToNext());
        }
        return list;
    }
}
