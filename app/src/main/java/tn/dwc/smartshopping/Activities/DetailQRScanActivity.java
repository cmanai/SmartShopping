package tn.dwc.smartshopping.Activities;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.internal.MDTintHelper;
import com.afollestad.materialdialogs.internal.ThemeSingleton;
import com.gc.materialdesign.views.ButtonFlat;
import com.github.mrengineer13.snackbar.SnackBar;
import com.melnykov.fab.FloatingActionButton;
import com.poliveira.parallaxrecyclerview.ParallaxRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import tn.dwc.smartshopping.Entities.Budget;
import tn.dwc.smartshopping.Entities.Maps_Product;
import tn.dwc.smartshopping.Entities.Products;
import tn.dwc.smartshopping.Entities.ShoppingList;
import tn.dwc.smartshopping.R;
import tn.dwc.smartshopping.SQLite.BudgetBDD;
import tn.dwc.smartshopping.SQLite.Maps_ProductBDD;
import tn.dwc.smartshopping.SQLite.ProductsBDD;
import tn.dwc.smartshopping.SQLite.ShoppingListBDD;

public class DetailQRScanActivity extends AppCompatActivity {

    String name,price,description,id,id_sheve,category,discount,old_price;
    Drawable picture;
    ShoppingListBDD shoppingListBDD;
    List<ShoppingList> productsList;
    String Budget_Reached="no";
    BudgetBDD budgetBDD;
    FloatingActionButton fab;
    String Budget_value,Shopping_list_name;
    MaterialDialog dialog3,dialog2,dialog1,dialog4;
    List<Budget> productBudgetList;
    EditText Input1,Input2;
    View positiveAction;
    boolean withbudget;
    byte[] bitmapdata;
    MaterialDialog.Builder builder;
    Context par;
    SharedPreferences sharedpreferences;
    ViewGroup par2;
    String shelve_id;
    byte[] map_sh;
    Bitmap bitmap2;
    Bitmap bitmap3;
    Maps_ProductBDD maps_productBDD;
    List<Maps_Product> maps_products;
    List<Maps_Product> maps_products2;
    String qr_id;
    static final int TYPE_HEADER = 0;
    static final int TYPE_CELL = 1;
    String hb;
     RecyclerView mRecyclerView;

    List<Products> productsList2;
    ProductsBDD productsBDD;
     List<Products> mContentItems;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_qr);

        //  image =  new BitmapDrawable(BitmapFactory.decodeByteArray(fileData, 0, fileData.length));


        Window window = getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(Color.parseColor("#303F9F"));





        toolbar = (Toolbar) findViewById(R.id.toolbar3);
        toolbar.setTitle(" ");
        // mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (toolbar != null) {

            setSupportActionBar(toolbar);

            final ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayShowHomeEnabled(true);
                actionBar.setDisplayShowTitleEnabled(true);
                actionBar.setDisplayUseLogoEnabled(false);
                actionBar.setHomeButtonEnabled(true);

                //actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#e74c3c")));
            }
        }

        fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.show(true);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DetailQRScanActivity.this, QrFindActivity.class));
            }
        });
        shelve_id = getIntent().getStringExtra("shelve_id");
        map_sh = getIntent().getByteArrayExtra("shelve_map");
        qr_id = getIntent().getStringExtra("shelve_qr");
        hb = getIntent().getStringExtra("shelve_hb");

        bitmap2 = BitmapFactory.decodeByteArray(map_sh, 0,
                map_sh.length);
        mContentItems = new ArrayList<Products>();
        productsList2= new ArrayList<Products>();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewww);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setHasFixedSize(true);





        int j = 0;
        productsBDD = new ProductsBDD(this);
        productsBDD.open();
        mContentItems = productsBDD.selectAll();
        productsBDD.close();

/*
        mRecyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
if(scrollX > oldScrollX){

 fab.hide(true);
}
                else if(scrollX < oldScrollX){

                    fab.show(true);
                }

            }
        });
        */

        final   ParallaxRecyclerAdapter<Products> adapter = new ParallaxRecyclerAdapter<Products>(mContentItems) {


            @Override
            public void onBindViewHolderImpl(final RecyclerView.ViewHolder holder, final ParallaxRecyclerAdapter<Products> parallaxRecyclerAdapter, final int position) {



                        bitmapdata = mContentItems.get(position).getProduct_image();
                        // if bitmapdata is the byte array then getting bitmap goes like this

                        Bitmap bitmap1 = BitmapFactory.decodeByteArray(bitmapdata, 0,
                                bitmapdata.length);
                ((ViewHolder) holder).image1.setImageBitmap(bitmap1);


                ((ViewHolder) holder).title1.setText(parallaxRecyclerAdapter.getData().get(position).getProduct_name());

                ((ViewHolder) holder).oldprice1.setText(parallaxRecyclerAdapter.getData().get(position).getProduct_price() + " DT");
                ((ViewHolder)holder).find.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        maps_products= new ArrayList<>();
                        maps_products2= new ArrayList<>();

                        maps_productBDD= new Maps_ProductBDD(getApplicationContext());
                        maps_productBDD.open();
                        maps_products = maps_productBDD.selectAll();
                        maps_productBDD.close();int i=0;
                        for (Maps_Product m : maps_products){

                            Log.e("extra 1",hb);
                            Log.e("extra 2",shelve_id);
                            Log.e("extra 3",parallaxRecyclerAdapter.getData().get(position).getProduct_id());
                            Log.e("m",m.getH_B_p());
                            Log.e("m",m.getProduct_Id_p());
                            Log.e("m",m.getShelve_Id_p());
                            if(m.getH_B_p().equals(hb)
                                    &&m.getProduct_Id_p().equals(parallaxRecyclerAdapter.getData().get(position).getProduct_id())
                                    &&m.getShelve_Id_p().equals(shelve_id)){

                                bitmap3 = BitmapFactory.decodeByteArray(m.getMaps_p(), 0,
                                        m.getMaps_p().length);
                            }

                        }

                        if(bitmap3!=null) {

                            Intent intent = new Intent(DetailQRScanActivity.this, QRProductFindActivity.class);
                            intent.putExtra("shelve_id", parallaxRecyclerAdapter.getData().get(position).getProduct_sheve_id());
                            intent.putExtra("shelve_hb", hb);
                            intent.putExtra("product_id", parallaxRecyclerAdapter.getData().get(position).getProduct_id());
                            startActivity(intent);
                        }
                        else{
                            new SnackBar.Builder(DetailQRScanActivity.this)

                            .withMessage("product Map not available yet") //   Spent / Budget :"+
                                    .withTypeFace(Typeface.DEFAULT_BOLD)


                                    .withActionMessage("Close")
                                    .withStyle(SnackBar.Style.INFO)
                                    .withTextColorId(R.color.my_color_2)
                                    .withBackgroundColorId(R.color.my_color)
                                    .withDuration(SnackBar.MED_SNACK)
                                    .show();
                        }
                    }
                });




            }


            @Override
            public RecyclerView.ViewHolder onCreateViewHolderImpl(ViewGroup viewGroup,final ParallaxRecyclerAdapter<Products> parallaxRecyclerAdapter, int viewType) {


                return new ViewHolder(getLayoutInflater().inflate(R.layout.list_item_card_small_find, viewGroup, false));




            }

            @Override
            public int getItemCountImpl(ParallaxRecyclerAdapter<Products> parallaxRecyclerAdapter) {
                return mContentItems.size();
            }
        };



        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        View child = getLayoutInflater().inflate(R.layout.header2, mRecyclerView,false);
LinearLayout ll =(LinearLayout)child.findViewById(R.id.linear1);

        ll.setBackgroundColor(Color.parseColor("#303F9F"));
        ImageView img = (ImageView) child.findViewById(R.id.image_header);
        img.setImageBitmap(bitmap2);
        adapter.setParallaxHeader(child, mRecyclerView);

        adapter.setData(mContentItems);
adapter.setOnParallaxScroll(new ParallaxRecyclerAdapter.OnParallaxScroll() {
    @Override
    public void onParallaxScroll(float percentage, float offset, View parallax) {

        Drawable c = toolbar.getBackground();
        c.setAlpha(Math.round(percentage * 255));
        toolbar.setBackground(c);
        Log.e("scroll",parallax.getVerticalScrollbarPosition()+"");

if( percentage>0){
    toolbar.setTitle("Smart Shopping");


}
        else{
    toolbar.setTitle(" ");
        }

    }
});




        mRecyclerView.setAdapter(adapter);

    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image1;
        public   ButtonFlat find;
        public TextView title1;
        public TextView oldprice1;
        public ButtonFlat details;
        public ViewHolder(View itemView) {
            super(itemView);
             find = (ButtonFlat) itemView.findViewById(R.id.buttonflatfind);
             image1 = (ImageView) itemView.findViewById(R.id.imageView24);
             title1 = (TextView) itemView.findViewById(R.id.title2);
             oldprice1 = (TextView) itemView.findViewById(R.id.price_value);

        }



    }












    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }












}
