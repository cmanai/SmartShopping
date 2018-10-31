package tn.dwc.smartshopping.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	public static final String TABLE_PRODUCTS = "products";
	public static final String TABLE_PROMOTIONS = "promotions";
    public static final String TABLE_MAPS_SHELVE = "maps_shelve";
    public static final String TABLE_MAPS_PRODUCT = "maps_product";
    public static final String TABLE_USER = "user";
    public static final String TABLE_SHOPPING_LIST = "shopping_list";
    public static final String TABLE_BUDGET = "budget";
    public static final String TABLE_HISTORY_SHOPPING_LIST = "history_shopping_list";
    public static final String TABLE_HISTORY = "history";
	// PRODUCT
    public static final String PRODUCT_ID = "product_id";
    public static final String PRODUCT_SHEVE_ID = "product_sheve_id";
    public static final String PRODUCT_NAME = "product_name";
    public static final String PRODUCT_IMAGE = "product_image";
    public static final String PRODUCT_CATEGORY = "product_category";
    public static final String PRODUCT_SUB_CATEGORY = "product_sub_category";
    public static final String PRODUCT_PRICE = "product_price";
    public static final String PRODUCT_CREATED_AT = "product_date";
    public static final String PRODUCT_DESC = "product_desc";
    // PROMOTION
    public static final String PROMOTION_ID = "promotion_id";
    public static final String PROMOTION_SHEVE_ID = "promotion_sheve_id";
    public static final String PROMOTION_NAME = "promotion_name";
    public static final String PROMOTION_IMAGE = "promotion_image";
    public static final String PROMOTION_CATEGORY = "promotion_category";
    public static final String PROMOTION_SUB_CATEGORY = "promotion_sub_category";
    public static final String PROMOTION_OLD_PRICE = "promotion_old_price";
    public static final String PROMOTION_NEW_PRICE = "promotion_new_price";
    public static final String PROMOTION_POURCENTAGE = "promotion_pourcentage";
    public static final String PROMOTION_DESC = "promotion_desc";
    // MAPS_SHELVE
    public static final String SHELVE_ID = "shelve_id";
    public static final String MAP_SH = "map_sh";
    public static final String QR_ID = "qr_id";
    public static final String H_B = "h_b";
    // MAPS_PRODUCT
    public static final String SHELVE_ID_P = "shelve_id_p";
    public static final String PRODUCT_ID_P = "product_id_m";
    public static final String MAP_P = "map_p";
    public static final String H_B_P = "h_b_p";
    //USER

    public static final String USER_FULLNAME = "fullname";
    public static final String USER_EMAIL = "email";
    public static final String USER_BIRTHDAY = "birthday";
    public static final String USER_PICTURE = "picture";

    //Shopping_list
    public static final String SHOPPING_List_NAME = "shopping_list_name";
    public static final String SHOPPING_PRODUCT_ID = "shopping_product_id";
    public static final String SHOPPING_PRODUCT_SHEVE_ID = "shopping_product_sheve_id";
    public static final String SHOPPING_PRODUCT_NAME = "shopping_product_name";
    public static final String SHOPPING_PRODUCT_IMAGE = "shopping_product_image";
    public static final String SHOPPING_PRODUCT_CATEGORY = "shopping_product_category";
    public static final String SHOPPING_PRODUCT_SUB_CATEGORY = "shopping_product_sub_category";
    public static final String SHOPPING_PRODUCT_PRICE = "shopping_product_price";
    public static final String SHOPPING_PRODUCT_CREATED_AT = "shopping_product_date";
    public static final String SHOPPING_PRODUCT_STATE = "shopping_product_state";


    //BUDGET
    public static final String BUDGET = "total_budget";
    public static final String SPENT = "spent";
//HISTORY

    public static final String HISTORY_NAME= "history_name";
    public static final String HISTORY_BUDGET= "history_budget";
    public static final String HISTORY_PRODUCTS_NUMBER= "history_products_number";
    public static final String HISTORY_CREATED_AT= "history_created_at";

	// Table Creation
	private static final String CREATE_PRODUCTS = "CREATE TABLE " + TABLE_PRODUCTS
			+ " (" + PRODUCT_ID + " TEXT, " + PRODUCT_SHEVE_ID + " TEXT," + PRODUCT_NAME + " TEXT, " +PRODUCT_DESC + " TEXT, " + PRODUCT_IMAGE + " BLOB, "
            + PRODUCT_CATEGORY + " TEXT,"
            + PRODUCT_SUB_CATEGORY  + " TEXT, " + PRODUCT_PRICE + " TEXT, " + PRODUCT_CREATED_AT + " TEXT);";
    private static final String CREATE_PROMOTION = "CREATE TABLE " + TABLE_PROMOTIONS
            + " (" + PROMOTION_ID + " TEXT, " + PROMOTION_SHEVE_ID + " TEXT," + PROMOTION_NAME + " TEXT, " +PROMOTION_DESC + " TEXT, " + PROMOTION_IMAGE + " BLOB, "
            + PROMOTION_CATEGORY + " TEXT,"
            + PROMOTION_SUB_CATEGORY + " TEXT, " + PROMOTION_OLD_PRICE + " TEXT, " + PROMOTION_NEW_PRICE + " TEXT, " + PROMOTION_POURCENTAGE + " TEXT);";


	private static final String CREATE_MAPS_SHELVE = "CREATE TABLE " + TABLE_MAPS_SHELVE
            + " (" + SHELVE_ID + " TEXT,"+ MAP_SH + " BLOB,"+QR_ID+" TEXT,"+H_B+" TEXT);";
    private static final String CREATE_MAPS_PRODUCT = "CREATE TABLE " + TABLE_MAPS_PRODUCT
            + " (" + SHELVE_ID_P + " TEXT,"+ MAP_P + " BLOB,"+PRODUCT_ID_P+" TEXT,"+H_B_P+" TEXT);";
    private static final String CREATE_USER = "CREATE TABLE " + TABLE_USER
            + " (" + USER_FULLNAME + " TEXT,"+ USER_EMAIL + " TEXT,"+ USER_BIRTHDAY + " TEXT,"+ USER_PICTURE + " BLOB);";

    private static final String CREATE_SHOPPING_LIST = "CREATE TABLE " + TABLE_SHOPPING_LIST
            + " (" + SHOPPING_List_NAME + " TEXT, "
            + SHOPPING_PRODUCT_ID + " TEXT, " + SHOPPING_PRODUCT_SHEVE_ID + " TEXT," + SHOPPING_PRODUCT_NAME + " TEXT, " + SHOPPING_PRODUCT_IMAGE + " BLOB, "
            + SHOPPING_PRODUCT_CATEGORY + " TEXT,"
            + SHOPPING_PRODUCT_PRICE + " TEXT, " + SHOPPING_PRODUCT_CREATED_AT + " TEXT,"+ SHOPPING_PRODUCT_STATE + " TEXT);";



    private static final String CREATE_BUDGET = "CREATE TABLE " + TABLE_BUDGET
            + " (" + BUDGET + " TEXT,"+ SPENT + " TEXT);";

    private static final String CREATE_HISTORY_SHOPPING_LIST = "CREATE TABLE " + TABLE_HISTORY_SHOPPING_LIST
            + " (" + SHOPPING_List_NAME + " TEXT, "
            + SHOPPING_PRODUCT_ID + " TEXT, " + SHOPPING_PRODUCT_SHEVE_ID + " TEXT," + SHOPPING_PRODUCT_NAME + " TEXT, " + SHOPPING_PRODUCT_IMAGE + " BLOB, "
            + SHOPPING_PRODUCT_CATEGORY + " TEXT,"
            + SHOPPING_PRODUCT_PRICE + " TEXT, " + SHOPPING_PRODUCT_CREATED_AT + " TEXT,"+ SHOPPING_PRODUCT_STATE + " TEXT);";

    private static final String CREATE_HISTORY = "CREATE TABLE " + TABLE_HISTORY
            + " (" + HISTORY_NAME + " TEXT,"+ HISTORY_BUDGET +" TEXT,"+HISTORY_PRODUCTS_NUMBER+" TEXT,"+HISTORY_CREATED_AT+ " TEXT);";
	public DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Create Data Base
		db.execSQL(CREATE_MAPS_SHELVE);
        db.execSQL(CREATE_MAPS_PRODUCT);
		db.execSQL(CREATE_PRODUCTS);
        db.execSQL(CREATE_PROMOTION);
        db.execSQL(CREATE_USER);
        db.execSQL(CREATE_SHOPPING_LIST);
        db.execSQL(CREATE_BUDGET);
        db.execSQL(CREATE_HISTORY);
        db.execSQL(CREATE_HISTORY_SHOPPING_LIST);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int old, int newVersion) {

		// TODO Re-Create Data Base
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROMOTIONS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MAPS_SHELVE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MAPS_PRODUCT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOPPING_LIST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BUDGET);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY_SHOPPING_LIST);

		onCreate(db);
	}

}
