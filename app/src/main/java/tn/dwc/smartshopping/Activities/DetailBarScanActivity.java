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
import tn.dwc.smartshopping.Entities.Products;
import tn.dwc.smartshopping.Entities.ShoppingList;
import tn.dwc.smartshopping.R;
import tn.dwc.smartshopping.SQLite.BudgetBDD;
import tn.dwc.smartshopping.SQLite.ProductsBDD;
import tn.dwc.smartshopping.SQLite.ShoppingListBDD;

public class DetailBarScanActivity extends AppCompatActivity {

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
                startActivity(new Intent(DetailBarScanActivity.this, QrFindActivity.class));
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


                ((ViewHolder) holder).details.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent i;
                                i = new Intent(holder.itemView.getContext(), DetailsActivity.class);
                                i.putExtra("product_id", parallaxRecyclerAdapter.getData().get(position).getProduct_id());
                                i.putExtra("product_name", parallaxRecyclerAdapter.getData().get(position).getProduct_name());
                                i.putExtra("product_price", parallaxRecyclerAdapter.getData().get(position).getProduct_price());
                                i.putExtra("product_sheve_id", parallaxRecyclerAdapter.getData().get(position).getProduct_sheve_id());
                                if (parallaxRecyclerAdapter.getData().get(position).getProduct_sub_category().equals("none")) {
                                    i.putExtra("product_category", parallaxRecyclerAdapter.getData().get(position).getProduct_category());
                                } else {

                                    i.putExtra("product_category", parallaxRecyclerAdapter.getData().get(position).getProduct_category() + " - " + parallaxRecyclerAdapter.getData().get(position).getProduct_sub_category());
                                }
                                i.putExtra("product_description", parallaxRecyclerAdapter.getData().get(position).getProduct_desc());
                                i.putExtra("product_picture", parallaxRecyclerAdapter.getData().get(position).getProduct_image());

                                holder.itemView.getContext().startActivity(i);
                            }
                        });

                ((ViewHolder) holder).add.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                id = parallaxRecyclerAdapter.getData().get(position).getProduct_id();
                                id_sheve = parallaxRecyclerAdapter.getData().get(position).getProduct_sheve_id();
                                name = parallaxRecyclerAdapter.getData().get(position).getProduct_name();
                                price = parallaxRecyclerAdapter.getData().get(position).getProduct_price();
                                bitmapdata = parallaxRecyclerAdapter.getData().get(position).getProduct_image();

                                Log.e("Product selected", name);
                                if (parallaxRecyclerAdapter.getData().get(position).getProduct_sub_category().equals("none")) {
                                    category = parallaxRecyclerAdapter.getData().get(position).getProduct_category();
                                } else {

                                    category = parallaxRecyclerAdapter.getData().get(position).getProduct_category() + " - " + parallaxRecyclerAdapter.getData().get(position).getProduct_sub_category();
                                }
                                productsList = new ArrayList<ShoppingList>();
                                shoppingListBDD = new ShoppingListBDD(holder.itemView.getContext());
                                shoppingListBDD.open();
                                productsList = shoppingListBDD.selectAll();

                                shoppingListBDD.close();

                                if (productsList.isEmpty()) {
                                    new MaterialDialog.Builder(holder.itemView.getContext())
                                            .title("No shopping list")
                                            .iconRes(R.drawable.smart_shopping_logo)
                                            .maxIconSize(100)
                                            .content("Create a new shopping list ?")
                                            .backgroundColor(Color.parseColor("#ffffff"))
                                            .titleColor(Color.parseColor("#000000"))
                                            .contentColor(Color.parseColor("#80000000"))
                                            .positiveColor(Color.parseColor("#3498db"))
                                            .negativeColor(Color.parseColor("#000000"))
                                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                                @Override
                                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {


                                                    dialog.dismiss();


                                                    dialog2 = new MaterialDialog.Builder(holder.itemView.getContext())
                                                            .title("Shopping list Informations")
                                                            .customView(R.layout.dialog_customview, true)
                                                            .backgroundColor(Color.parseColor("#ffffff"))
                                                            .titleColor(Color.parseColor("#000000"))
                                                            .contentColor(Color.parseColor("#80000000"))
                                                            .iconRes(R.drawable.smart_shopping_logo)
                                                            .maxIconSize(100)
                                                            .positiveText("Done")
                                                            .negativeText("Cancel")
                                                            .positiveColor(Color.parseColor("#3498db"))
                                                            .negativeColor(Color.parseColor("#000000"))
                                                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                                                @Override
                                                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {


                                                                    if (withbudget) {

                                                                        show_budget(holder);
                                                                    } else {

                                                                        //adding shopping list without budget
                                                                        shoppingListBDD = new ShoppingListBDD(holder.itemView.getContext());
                                                                        shoppingListBDD.open();
                                                                        shoppingListBDD.insertTop(new ShoppingList(Shopping_list_name, id, id_sheve, name, bitmapdata, category, price, "date", "false"));
                                                                        shoppingListBDD.close();
                                                                        new SnackBar.Builder(DetailBarScanActivity.this)

                                                                                .withMessage(name + " added to " + Shopping_list_name) //   Spent / Budget :"+


                                                                                .withTypeFace(Typeface.DEFAULT_BOLD)


                                                                                .withActionMessage("Close")
                                                                                .withStyle(SnackBar.Style.INFO)
                                                                                .withTextColorId(R.color.my_color_2)
                                                                                .withBackgroundColorId(R.color.my_color)
                                                                                .withDuration(SnackBar.SHORT_SNACK)
                                                                                .show();

                                                                    }

                                                                }
                                                            })

                                                            .onNegative(new MaterialDialog.SingleButtonCallback() {
                                                                @Override
                                                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                                                }
                                                            })
                                                            .build();

                                                    positiveAction = dialog2.getActionButton(DialogAction.POSITIVE);
                                                    //noinspection ConstantConditions
                                                    Input1 = (EditText) dialog2.getCustomView().findViewById(R.id.password);
                                                    Input1.addTextChangedListener(new TextWatcher() {
                                                        @Override
                                                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                                        }

                                                        @Override
                                                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                            positiveAction.setEnabled(s.toString().trim().length() > 0);
                                                        }

                                                        @Override
                                                        public void afterTextChanged(Editable s) {

                                                            Shopping_list_name = s.toString();
                                                        }
                                                    });


                                                    final CheckBox checkbox = (CheckBox) dialog2.getCustomView().findViewById(R.id.showPassword);
                                                    checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                        @Override
                                                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                                            if (isChecked) {
                                                                withbudget = true;
                                                                Container.budget_enabled = true;
                                                                SharedPreferences.Editor editor= sharedpreferences.edit();
                                                                editor.putString("budget","true");
                                                                editor.commit();

                                                            } else {
                                                                withbudget = false;
                                                                Container.budget_enabled = false;
                                                                SharedPreferences.Editor editor= sharedpreferences.edit();
                                                                editor.putString("budget","false");
                                                                editor.commit();

                                                            }
                                                        }
                                                    });

                                                    int widgetColor = ThemeSingleton.get().widgetColor;


                                                    //  MDTintHelper.setTint(checkbox,
                                                    //        widgetColor == 0 ? Color.parseColor("#3498db") : widgetColor);

                                                    MDTintHelper.setTint(Input1,
                                                            widgetColor == 0 ? Color.parseColor("#3498db") : widgetColor);

                                                    dialog2.show();
                                                    positiveAction.setEnabled(false);

                                                }
                                            })
                                            .onNegative(new MaterialDialog.SingleButtonCallback() {
                                                @Override
                                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                    dialog.dismiss();
                                                }
                                            })
                                            .positiveText("Create")
                                            .negativeText("Cancel").build().show();


                                } else {
                                    boolean exist = false;
                                    for (int i = 0; i < productsList.size(); i++) {

                                        if (productsList.get(i).getShopping_Product_id().equals(id)) {
                                            exist = true;
                                        }
                                    }
                                    if (exist == false) {
                                        Shopping_list_name = productsList.get(0).getShopping_list_name();
                                        if (sharedpreferences.getString("budget", "false").equals("true")) {
                                            productBudgetList = new ArrayList<Budget>();
                                            budgetBDD = new BudgetBDD(holder.itemView.getContext());
                                            budgetBDD.open();
                                            productBudgetList = budgetBDD.selectAll();
                                            budgetBDD.close();
                                            Budget_value = productBudgetList.get(0).getBudget();
                                            Log.e(" before Rounded", Float.valueOf(productBudgetList.get(0).getSpent()) + "");
                                            Float value = Float.valueOf(productBudgetList.get(0).getSpent()) + Float.valueOf(price);
                                            Log.e("Rounded", String.format("%.3f", value) + "");//new DecimalFormat("##.##").format(value)
                                            Log.e("Spent value", value + "");
                                            Log.e("budget", productBudgetList.get(0).getBudget() + "nothing");
                                            if (value > Float.valueOf(productBudgetList.get(0).getBudget())) {
//ERREUR BUDGET LIMIT ADD more BUDGET OR CANCEL
                                                dialog4 = new MaterialDialog.Builder(holder.itemView.getContext())
                                                        .title("Budget limit reached")
                                                        .customView(R.layout.dialog_customview3, true)
                                                        .positiveText("Increase")
                                                        .negativeText("Cancel")
                                                        .iconRes(R.drawable.smart_shopping_logo)
                                                        .maxIconSize(100)
                                                        .titleColor(Color.parseColor("#000000"))
                                                        .contentColor(Color.parseColor("#80000000"))
                                                        .backgroundColor(Color.parseColor("#ffffff"))
                                                        .positiveColor(Color.parseColor("#3498db"))
                                                        .negativeColor(Color.parseColor("#000000"))
                                                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                                                            @Override
                                                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                                show_budget2(holder);


                                                            }
                                                        })

                                                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                                                            @Override
                                                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                                            }
                                                        })
                                                        .build();
                                                TextView spent_budget = (TextView) dialog4.getCustomView().findViewById(R.id.spent_budget_value);
                                                TextView product_price = (TextView) dialog4.getCustomView().findViewById(R.id.product_price_value);

                                                spent_budget.setText(String.format("%.3f", value) + " DT / " + String.format("%.3f", Float.valueOf(Budget_value)) + " DT");
                                                product_price.setText(String.format("%.3f", Float.valueOf(price)) + " DT");

                                                dialog4.show();


                                            } else {

                                                shoppingListBDD = new ShoppingListBDD(holder.itemView.getContext());
                                                shoppingListBDD.open();
                                                shoppingListBDD.insertTop(new ShoppingList(productsList.get(0).getShopping_list_name(), id, id_sheve, name, bitmapdata, category, price, "date", "false"));
                                                shoppingListBDD.close();
                                                productBudgetList = new ArrayList<Budget>();
                                                BudgetBDD budgetBDD = new BudgetBDD(holder.itemView.getContext());
                                                budgetBDD.open();
                                                productBudgetList = budgetBDD.selectAll();

                                                Float new_value = Float.valueOf(price) + Float.valueOf(productBudgetList.get(0).getSpent());
                                                budgetBDD.removeAllArticles();
                                                budgetBDD.insertTop(new Budget(String.format("%.3f", Float.valueOf(Budget_value)), String.format("%.3f", new_value)));
                                                budgetBDD.close();
                                                new SnackBar.Builder(DetailBarScanActivity.this)


                                                        .withMessage(name + " added to " + Shopping_list_name + "\nSpent :    " + String.format("%.3f", Float.valueOf(new_value)) + " DT" +
                                                                "\nBudget : " +
                                                                String.format("%.3f", Float.valueOf(Budget_value)) + " DT") //

                                                        .withTypeFace(Typeface.DEFAULT_BOLD)


                                                        .withActionMessage("Close")
                                                        .withStyle(SnackBar.Style.INFO)
                                                        .withTextColorId(R.color.my_color_2)
                                                        .withBackgroundColorId(R.color.my_color)
                                                        .withDuration(SnackBar.SHORT_SNACK)
                                                        .show();

                                            }
                                        } else {
                                            shoppingListBDD = new ShoppingListBDD(holder.itemView.getContext());
                                            shoppingListBDD.open();
                                            shoppingListBDD.insertTop(new ShoppingList(productsList.get(0).getShopping_list_name(), id, id_sheve, name, bitmapdata, category, price, "date", "false"));
                                            shoppingListBDD.close();
                                            productBudgetList = new ArrayList<Budget>();
                                            BudgetBDD budgetBDD = new BudgetBDD(holder.itemView.getContext());
                                            budgetBDD.open();
                                            productBudgetList = budgetBDD.selectAll();

                                            if(!productBudgetList.isEmpty()){
                                                Float new_value = Float.valueOf(price) + Float.valueOf(productBudgetList.get(0).getSpent());
                                                budgetBDD.removeAllArticles();
                                                budgetBDD.insertTop(new Budget(String.format("%.3f", Float.valueOf(productBudgetList.get(0).getBudget())), String.format("%.3f", new_value)));
                                                budgetBDD.close();
                                            }

                                            new SnackBar.Builder(DetailBarScanActivity.this)

                                                    .withMessage(name + " added to " + Shopping_list_name) //   Spent / Budget :"+


                                                    .withTypeFace(Typeface.DEFAULT_BOLD)


                                                    .withActionMessage("Close")
                                                    .withStyle(SnackBar.Style.INFO)
                                                    .withTextColorId(R.color.my_color_2)
                                                    .withBackgroundColorId(R.color.my_color)
                                                    .withDuration(SnackBar.SHORT_SNACK)
                                                    .show();
                                        }


                                    } else {

                                        new MaterialDialog.Builder(holder.itemView.getContext())
                                                .title("Can't be added")
                                                .iconRes(R.drawable.smart_shopping_logo)
                                                .maxIconSize(100)
                                                .titleColor(Color.parseColor("#000000"))
                                                .contentColor(Color.parseColor("#80000000"))
                                                .backgroundColor(Color.parseColor("#ffffff"))
                                                .content(name + " already exists on your current shopping list")
                                                .positiveText("Ok")

                                                .positiveColor(Color.parseColor("#3498db"))


                                                .build().show();

                                    }
                                }


                            }
                        });



            }


            @Override
            public RecyclerView.ViewHolder onCreateViewHolderImpl(ViewGroup viewGroup,final ParallaxRecyclerAdapter<Products> parallaxRecyclerAdapter, int viewType) {


                return new ViewHolder(getLayoutInflater().inflate(R.layout.list_item_card_small, viewGroup, false));




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


    }
});




        mRecyclerView.setAdapter(adapter);

    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image1;
        public   ButtonFlat add;
        public TextView title1;
        public TextView oldprice1;
        public ButtonFlat details;
        public ViewHolder(View itemView) {
            super(itemView);
             add = (ButtonFlat) itemView.findViewById(R.id.buttonflat3);
             image1 = (ImageView) itemView.findViewById(R.id.imageView24);
             title1 = (TextView) itemView.findViewById(R.id.title2);
             oldprice1 = (TextView) itemView.findViewById(R.id.price_value);
             details = (ButtonFlat) itemView.findViewById(R.id.buttonflat33);
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

































    public void show_budget(final RecyclerView.ViewHolder holder){

        dialog3 =  new MaterialDialog.Builder(holder.itemView.getContext())
                .title("Budget")
                .customView(R.layout.dialog_customview2, true)
                .positiveText("Done")
                .negativeText("Cancel")
                .iconRes(R.drawable.smart_shopping_logo)
                .maxIconSize(100)
                .positiveColor(Color.parseColor("#3498db"))
                .negativeColor(Color.parseColor("#000000"))
                .titleColor(Color.parseColor("#000000"))
                .contentColor(Color.parseColor("#80000000"))
                .backgroundColor(Color.parseColor("#ffffff"))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                    if (Float.valueOf(price) > Float.valueOf(Budget_value)) {
                                        dialog4 = new MaterialDialog.Builder(holder.itemView.getContext())
                                                .title("Budget limit reached")
                                                .customView(R.layout.dialog_customview3, true)
                                                .positiveText("Increase")
                                                .negativeText("Cancel")
                                                .iconRes(R.drawable.smart_shopping_logo)
                                                .maxIconSize(100)
                                                .positiveColor(Color.parseColor("#3498db"))
                                                .negativeColor(Color.parseColor("#000000"))
                                                .titleColor(Color.parseColor("#000000"))
                                                .contentColor(Color.parseColor("#80000000"))
                                                .backgroundColor(Color.parseColor("#ffffff"))
                                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                                    @Override
                                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                        show_budget(holder);


                                                    }
                                                })

                                                .onNegative(new MaterialDialog.SingleButtonCallback() {
                                                    @Override
                                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                                    }
                                                })
                                                .build();
                                        TextView spent_budget = (TextView) dialog4.getCustomView().findViewById(R.id.spent_budget_value);
                                        TextView product_price = (TextView) dialog4.getCustomView().findViewById(R.id.product_price_value);

                                        spent_budget.setText(String.format("%.3f", Float.valueOf(price)) + " DT / " + String.format("%.3f", Float.valueOf(Budget_value)) + " DT");
                                        product_price.setText(String.format("%.3f", Float.valueOf(price)) + " DT");

                                        dialog4.show();


                                    } else {
                                        shoppingListBDD = new ShoppingListBDD(holder.itemView.getContext());
                                        shoppingListBDD.open();
                                        shoppingListBDD.insertTop(new ShoppingList(Shopping_list_name, id, id_sheve, name, bitmapdata, category, price, "date", "false"));
                                        shoppingListBDD.close();

                                        BudgetBDD budgetBDD = new BudgetBDD(holder.itemView.getContext());
                                        budgetBDD.open();
                                        budgetBDD.removeAllArticles();
                                        budgetBDD.insertTop(new Budget(String.format("%.3f", Float.valueOf(Budget_value)), String.format("%.3f", Float.valueOf(price))));
                                        budgetBDD.close();
                                        new SnackBar.Builder(DetailBarScanActivity.this)

                                                .withMessage(name + " added to " + Shopping_list_name + "\nSpent :    " + String.format("%.3f", Float.valueOf(price)) + " DT" +
                                                        "\nBudget : " +
                                                        String.format("%.3f", Float.valueOf(Budget_value)) + " DT") //


                                                .withTypeFace(Typeface.DEFAULT_BOLD)


                                                .withActionMessage("Close")
                                                .withStyle(SnackBar.Style.INFO)
                                                .withTextColorId(R.color.my_color_2)
                                                .withBackgroundColorId(R.color.my_color)
                                                .withDuration(SnackBar.SHORT_SNACK)
                                                .show();

                                    }


                                }
                            }

                )

                .

                        onNegative(new MaterialDialog.SingleButtonCallback() {
                                       @Override
                                       public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                       }
                                   }

                        )
                .

                        build();

        positiveAction=dialog3.getActionButton(DialogAction.POSITIVE);
        //noinspection ConstantConditions
        Input2 = (EditText) dialog3.getCustomView().findViewById(R.id.password2);
        Input2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                positiveAction.setEnabled(s.toString().trim().length() > 0);
            }

            @Override
            public void afterTextChanged(Editable s) {

                Log.e("teeeext", s.toString());
                Budget_value=s.toString();
            }
        });




        int widgetColor = ThemeSingleton.get().widgetColor;

        MDTintHelper.setTint(Input2,
                widgetColor == 0 ? Color.parseColor("#3498db") : widgetColor);

        dialog3.show();
        positiveAction.setEnabled(false);
    }

    public void show_budget2(final RecyclerView.ViewHolder holder){

        dialog3 =  new MaterialDialog.Builder(holder.itemView.getContext())
                .title("Budget")
                .customView(R.layout.dialog_customview4, true)
                .positiveText("Done")
                .negativeText("Cancel")
                .iconRes(R.drawable.smart_shopping_logo)
                .maxIconSize(100)
                .positiveColor(Color.parseColor("#3498db"))
                .negativeColor(Color.parseColor("#000000"))
                .titleColor(Color.parseColor("#000000"))
                .contentColor(Color.parseColor("#80000000"))
                .backgroundColor(Color.parseColor("#ffffff"))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Float value = Float.valueOf(productBudgetList.get(0).getSpent()) + Float.valueOf(price);
                        if (value > Float.valueOf(Budget_value)) {

                            dialog4 = new MaterialDialog.Builder(holder.itemView.getContext())
                                    .title("Budget limit reached")
                                    .customView(R.layout.dialog_customview3, true)
                                    .positiveText("Increase")
                                    .negativeText("Cancel")
                                    .iconRes(R.drawable.smart_shopping_logo)
                                    .maxIconSize(100)
                                    .positiveColor(Color.parseColor("#3498db"))
                                    .negativeColor(Color.parseColor("#000000"))
                                    .titleColor(Color.parseColor("#000000"))
                                    .contentColor(Color.parseColor("#80000000"))
                                    .backgroundColor(Color.parseColor("#ffffff"))
                                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                            show_budget2(holder);


                                        }
                                    })

                                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                        }
                                    })
                                    .build();
                            TextView spent_budget = (TextView) dialog4.getCustomView().findViewById(R.id.spent_budget_value);
                            TextView product_price = (TextView) dialog4.getCustomView().findViewById(R.id.product_price_value);

                            spent_budget.setText(String.format("%.3f", value) + " DT / " + String.format("%.3f", Float.valueOf(Budget_value)) + " DT");
                            product_price.setText(String.format("%.3f", Float.valueOf(price)) + " DT");

                            dialog4.show();


                        } else {
                            shoppingListBDD = new ShoppingListBDD(holder.itemView.getContext());
                            shoppingListBDD.open();
                            shoppingListBDD.insertTop(new ShoppingList(Shopping_list_name, id, id_sheve, name, bitmapdata, category, price, "date", "false"));
                            shoppingListBDD.close();
                            productBudgetList = new ArrayList<Budget>();
                            BudgetBDD budgetBDD = new BudgetBDD(holder.itemView.getContext());
                            budgetBDD.open();
                            productBudgetList = budgetBDD.selectAll();
                            Float new_value = Float.valueOf(price) + Float.valueOf(productBudgetList.get(0).getSpent());
                            budgetBDD.removeAllArticles();
                            budgetBDD.insertTop(new Budget(String.format("%.3f", Float.valueOf(Budget_value)), String.format("%.3f", new_value)));
                            budgetBDD.close();
                            new SnackBar.Builder(DetailBarScanActivity.this)

                                    .withMessage(name + " added to " + Shopping_list_name + "\nSpent :    " + String.format("%.3f", Float.valueOf(new_value)) + " DT" +
                                            "\nBudget : " +
                                            String.format("%.3f", Float.valueOf(Budget_value)) + " DT") //

                                    .withTypeFace(Typeface.DEFAULT_BOLD)


                                    .withActionMessage("Close")
                                    .withStyle(SnackBar.Style.INFO)
                                    .withTextColorId(R.color.my_color_2)
                                    .withBackgroundColorId(R.color.my_color)
                                    .withDuration(SnackBar.SHORT_SNACK)
                                    .show();
                        }


                    }
                })

                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                    }
                })
                .build();

        positiveAction = dialog3.getActionButton(DialogAction.POSITIVE);
        //noinspection ConstantConditions
        Input2 = (EditText) dialog3.getCustomView().findViewById(R.id.password3);
        TextView old_budget = (TextView)dialog3.getCustomView().findViewById(R.id.old_budget);

        old_budget.setText(Budget_value+" DT");
        Input2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                positiveAction.setEnabled(s.toString().trim().length() > 0);
            }

            @Override
            public void afterTextChanged(Editable s) {

                Log.e("teeeext", s.toString());
                Budget_value=s.toString();
            }
        });




        int widgetColor = ThemeSingleton.get().widgetColor;

        MDTintHelper.setTint(Input2,
                widgetColor == 0 ? Color.parseColor("#3498db") : widgetColor);

        dialog3.show();
        positiveAction.setEnabled(false);
    }


}
