package tn.dwc.smartshopping.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import tn.dwc.smartshopping.Entities.User;

public class UserBDD {

    private static final int VERSION_BDD = 2;
    private static final String NAME_BDD = "user.db";

    private SQLiteDatabase bdd;

    private DBHelper dbHelper;

    public UserBDD(Context context) {
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

    public long insertTop(User dt) {

        // TODO Add Article to data base
        ContentValues values = new ContentValues();

        values.put(DBHelper.USER_FULLNAME, dt.getUser_FullName());
        values.put(DBHelper.USER_EMAIL, dt.getUser_Email());
        values.put(DBHelper.USER_BIRTHDAY, dt.getUser_Birthday());
        values.put(DBHelper.USER_PICTURE, dt.getUser_Picture());





        bdd.insert(DBHelper.TABLE_USER, null, values);

        return 0;
    }

    public int removeAllArticles() { // TODO Remove all Table
        bdd.execSQL("DELETE FROM " + DBHelper.TABLE_USER);
        return 0;
    }

    public List<User> selectAll() {
        List<User> list = new ArrayList<User>();
        // TODO Get list of Article
        Cursor cursor = bdd.query(DBHelper.TABLE_USER, new String[] {

                        DBHelper.USER_FULLNAME, DBHelper.USER_EMAIL,DBHelper.USER_BIRTHDAY,DBHelper.USER_PICTURE}, null, null, null, null,
                null);

        if (cursor.moveToFirst()) {
            do {
                User p = new User(cursor.getString(0),cursor.getString(1),cursor.getString(2),
                        cursor.getBlob(3));
                list.add(p);
            } while (cursor.moveToNext());
        }
        return list;
    }
}
