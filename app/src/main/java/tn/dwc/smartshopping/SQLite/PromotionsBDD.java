package tn.dwc.smartshopping.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;



import java.util.ArrayList;
import java.util.List;

import tn.dwc.smartshopping.Entities.Promotions;

public class PromotionsBDD {

	private static final int VERSION_BDD = 3;
	private static final String NAME_BDD = "promotionss.db";

	private SQLiteDatabase bdd;

	private DBHelper dbHelper;

	public PromotionsBDD(Context context) {
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

	public long insertTop(Promotions dt) {

		// TODO Add Article to data base
		ContentValues values = new ContentValues();

		values.put(DBHelper.PROMOTION_ID, dt.getPromotion_id());
		values.put(DBHelper.PROMOTION_SHEVE_ID, dt.getPromotion_sheve_id());
		values.put(DBHelper.PROMOTION_NAME, dt.getPromotion_name());
		values.put(DBHelper.PROMOTION_DESC, dt.getPromotion_Product_desc());
		values.put(DBHelper.PROMOTION_IMAGE, dt.getPromotion_image());
        values.put(DBHelper.PROMOTION_CATEGORY, dt.getPromotion_category());
		values.put(DBHelper.PROMOTION_SUB_CATEGORY, dt.getPromotion_sub_category());
		values.put(DBHelper.PROMOTION_OLD_PRICE, dt.getPromotion_old_price());
		values.put(DBHelper.PROMOTION_NEW_PRICE, dt.getPromotion_new_price());
		values.put(DBHelper.PROMOTION_POURCENTAGE, dt.getPromotion_pourcentage());


		bdd.insert(DBHelper.TABLE_PROMOTIONS, null, values);

		return 0;
	}

	public int removeAllArticles() { // TODO Remove all Table
		bdd.execSQL("DELETE FROM " + DBHelper.TABLE_PROMOTIONS);
		return 0;
	}

	public List<Promotions> selectAll() {
		List<Promotions> list = new ArrayList<Promotions>();
		// TODO Get list of Article
		Cursor cursor = bdd.query(DBHelper.TABLE_PROMOTIONS, new String[] {

		DBHelper.PROMOTION_ID, DBHelper.PROMOTION_SHEVE_ID, DBHelper.PROMOTION_NAME,DBHelper.PROMOTION_DESC, DBHelper.PROMOTION_IMAGE,
                        DBHelper.PROMOTION_CATEGORY,
				DBHelper.PROMOTION_SUB_CATEGORY, DBHelper.PROMOTION_OLD_PRICE, DBHelper.PROMOTION_NEW_PRICE,
				DBHelper.PROMOTION_POURCENTAGE }, null, null, null, null,
				null);

		if (cursor.moveToFirst()) {
			do {
				Promotions p = new Promotions(cursor.getString(0),
						cursor.getString(1), cursor.getString(2),
						cursor.getString(3), cursor.getBlob(4),cursor.getString(5),
						cursor.getString(6), cursor.getString(7),
						cursor.getString(8),cursor.getString(9));
				list.add(p);
			} while (cursor.moveToNext());
		}
		return list;
	}
}
