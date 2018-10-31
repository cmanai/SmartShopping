package tn.dwc.smartshopping.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.internal.MDTintHelper;
import com.afollestad.materialdialogs.internal.ThemeSingleton;
import com.gc.materialdesign.views.ButtonFlat;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.github.mrengineer13.snackbar.SnackBar;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.util.ArrayList;
import java.util.List;

import tn.dwc.smartshopping.Activities.Container;
import tn.dwc.smartshopping.Adapters.AnimalsRecyclerViewAdapter;
import tn.dwc.smartshopping.Adapters.ShoppingListRecyclerViewAdapter;
import tn.dwc.smartshopping.Entities.Budget;
import tn.dwc.smartshopping.Entities.History;
import tn.dwc.smartshopping.Entities.HistoryShoppingLists;
import tn.dwc.smartshopping.Entities.Products;
import tn.dwc.smartshopping.Entities.Promotions;
import tn.dwc.smartshopping.Entities.ShoppingList;
import tn.dwc.smartshopping.R;
import tn.dwc.smartshopping.SQLite.BudgetBDD;
import tn.dwc.smartshopping.SQLite.HistoryBDD;
import tn.dwc.smartshopping.SQLite.HistoryShoppingListBDD;
import tn.dwc.smartshopping.SQLite.ProductsBDD;
import tn.dwc.smartshopping.SQLite.PromotionsBDD;
import tn.dwc.smartshopping.SQLite.ShoppingListBDD;

public class MyShoppingListFragment extends Fragment {
    View v;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    EditText Input1,Input2;
    View positiveAction;
    SharedPreferences sharedpreferences;
    ShoppingListBDD  shoppingListBDD;
    HistoryBDD historyBDD;
    HistoryShoppingListBDD historyShoppingListBDD;
    List<ShoppingList> shoppingList;
    List<ShoppingList> productsList;
    CheckBox budget_check ;
    List<Budget> productBudgetList;
    MaterialDialog dialog3,dialog2,dialog1,dialog4,dialog5,dialog6;
    TextView Spent_Budget_text ;
    TextView Spent_Budget_value;
    TextView my_shopping_list_name;
    private static final int ITEM_COUNT = 100;
    BudgetBDD budgetBDD;
    private List<ShoppingList> mContentItems;
    String price;
    String Budget_value;
    ImageView edit_budget_image;
    TextView edit_budget_text;
    View vieww;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

//Save the fragment's state here


    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }



    public static MyShoppingListFragment newInstance() {
        return new MyShoppingListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        sharedpreferences = getActivity().getSharedPreferences("SmartShopping", Context.MODE_PRIVATE);

        return inflater.inflate(R.layout.fragment_recycleview_shopping_list, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContentItems = new ArrayList<ShoppingList>();
        vieww=view;
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        budget_check = (CheckBox) view.findViewById(R.id.enable_budget);
        Spent_Budget_text = (TextView) view.findViewById(R.id.Spent_Budget_text);
        Spent_Budget_value = (TextView) view.findViewById(R.id.Spent_Budget_value);
        my_shopping_list_name = (TextView) view.findViewById(R.id.my_shopping_list_name);
        edit_budget_text = (TextView)view.findViewById(R.id.edit_budget_text);
        edit_budget_image = (ImageView)view.findViewById(R.id.edit_budget_image);
        productBudgetList = new ArrayList<Budget>();
        budgetBDD = new BudgetBDD(getActivity());
        budgetBDD.open();
        productBudgetList = budgetBDD.selectAll();
        budgetBDD.close();



        shoppingList = new ArrayList<ShoppingList>();

        shoppingListBDD = new ShoppingListBDD(getActivity());
        shoppingListBDD.open();
        shoppingList = shoppingListBDD.selectAll();
        shoppingListBDD.close();

        if(shoppingList.isEmpty()){
            Button validate = (Button)vieww.findViewById(R.id.validate_shopping_list);
            RecyclerView recyle = (RecyclerView)vieww.findViewById(R.id.recyclerView);
            RelativeLayout relativeLayout = (RelativeLayout)vieww.findViewById(R.id.relative_recycle);
            TextView no_shopping_display= (TextView)vieww.findViewById(R.id.no_shopping_display);
            ImageView imageViewpic=(ImageView)vieww.findViewById(R.id.imageViewpic);
            imageViewpic.setVisibility(View.VISIBLE);
            validate.setVisibility(View.INVISIBLE);
            no_shopping_display.setVisibility(View.VISIBLE);
            recyle.setVisibility(View.INVISIBLE);
            relativeLayout.setVisibility(View.INVISIBLE);
        }
        else
        {
            Button validate = (Button)vieww.findViewById(R.id.validate_shopping_list);
            my_shopping_list_name.setText(shoppingList.get(0).getShopping_list_name());
            if (sharedpreferences.getString("budget", "false").equals("true")) {
                budget_check.setChecked(true);
                Spent_Budget_text.setVisibility(View.VISIBLE);
                Spent_Budget_value.setVisibility(View.VISIBLE);
                edit_budget_text.setVisibility(View.VISIBLE);
                edit_budget_image.setVisibility(View.VISIBLE);
                Spent_Budget_value.setText(productBudgetList.get(0).getSpent() + "DT / " + productBudgetList.get(0).getBudget() + " DT");


            } else {
                edit_budget_text.setVisibility(View.INVISIBLE);
                edit_budget_image.setVisibility(View.INVISIBLE);
                budget_check.setChecked(false);
                Spent_Budget_text.setVisibility(View.INVISIBLE);
                Spent_Budget_value.setVisibility(View.INVISIBLE);

            }

            budget_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (isChecked) {
                        productBudgetList = new ArrayList<Budget>();
                        budgetBDD = new BudgetBDD(getActivity());
                        budgetBDD.open();
                        productBudgetList = budgetBDD.selectAll();
                        budgetBDD.close();
                        if (productBudgetList.isEmpty()) {
                            float total = 0;
                            for (int i = 0; i < shoppingList.size(); i++) {

                                total = total + Float.valueOf(shoppingList.get(i).getShopping_Product_price());

                            }
                            price = String.valueOf(total);
                            show_budget();
                        } else {
                            edit_budget_text.setVisibility(View.VISIBLE);
                            edit_budget_image.setVisibility(View.VISIBLE);
                            budget_check.setChecked(true);
                            Spent_Budget_text.setVisibility(View.VISIBLE);
                            Spent_Budget_value.setVisibility(View.VISIBLE);
                            productBudgetList = new ArrayList<Budget>();
                            budgetBDD = new BudgetBDD(getActivity());
                            budgetBDD.open();
                            productBudgetList = budgetBDD.selectAll();
                            budgetBDD.close();
                            Spent_Budget_value.setText(productBudgetList.get(0).getSpent() + "DT / " + productBudgetList.get(0).getBudget() + " DT");


                            sharedpreferences.edit().remove("SmartShopping").commit();
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            sharedpreferences = getActivity().getSharedPreferences("SmartShopping", Context.MODE_PRIVATE);
                            editor.putString("budget", "true");
                            editor.commit();
                            if (Float.valueOf(productBudgetList.get(0).getSpent()) > Float.valueOf(productBudgetList.get(0).getBudget())) {
//ERREUR BUDGET LIMIT ADD more BUDGET OR CANCEL
                                dialog4 = new MaterialDialog.Builder(getActivity())
                                        .title("Budget limit reached")
                                        .autoDismiss(false)
                                        .cancelable(false)
                                        .customView(R.layout.dialog_customview5, true)
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
                                                show_budget2();


                                            }
                                        })

                                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                                            @Override
                                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                dialog4.setCancelable(true);
                                                dialog4.dismiss();
                                                budget_check.setChecked(false);
                                                Spent_Budget_text.setVisibility(View.INVISIBLE);
                                                Spent_Budget_value.setVisibility(View.INVISIBLE);
                                                sharedpreferences.edit().remove("SmartShopping").commit();
                                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                                sharedpreferences = getActivity().getSharedPreferences("SmartShopping", Context.MODE_PRIVATE);
                                                editor.putString("budget", "false");
                                                editor.commit();
                                            }
                                        })
                                        .build();
                                TextView spent_budget = (TextView) dialog4.getCustomView().findViewById(R.id.spent_budget_value1);


                                spent_budget.setText(String.format("%.3f", Float.valueOf(productBudgetList.get(0).getSpent())) + " DT / " + String.format("%.3f", Float.valueOf(productBudgetList.get(0).getBudget())) + " DT");


                                dialog4.show();
                            }
                        }


                    } else {
                        budget_check.setChecked(false);
                        edit_budget_text.setVisibility(View.INVISIBLE);
                        edit_budget_image.setVisibility(View.INVISIBLE);
                        Spent_Budget_text.setVisibility(View.INVISIBLE);
                        Spent_Budget_value.setVisibility(View.INVISIBLE);
                        sharedpreferences.edit().remove("SmartShopping").commit();
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        sharedpreferences = getActivity().getSharedPreferences("SmartShopping", Context.MODE_PRIVATE);
                        editor.putString("budget", "false");
                        editor.commit();

                    }

                }
            });
            ShoppingListRecyclerViewAdapter ax = new ShoppingListRecyclerViewAdapter(mContentItems, getActivity());
            mAdapter = new RecyclerViewMaterialAdapter(ax);
            mRecyclerView.setAdapter(mAdapter);


            int j = 0;



            for (int i = 0; i < shoppingList.size(); i++) {

                mContentItems.add(i, new ShoppingList(shoppingList.get(i).getShopping_list_name(),
                        shoppingList.get(i).getShopping_Product_id(),
                        shoppingList.get(i).getShopping_Product_sheve_id(),
                        shoppingList.get(i).getShopping_Product_name(),
                        shoppingList.get(i).getShopping_Product_image(),
                        shoppingList.get(i).getShopping_Product_category(),
                        shoppingList.get(i).getShopping_Product_price(),
                        shoppingList.get(i).getShopping_Product_created_at(),
                        shoppingList.get(i).getShopping_product_state()));

                mAdapter.notifyDataSetChanged();


            }


            //  mAdapter.notifyItemRemoved(2);


            //  MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);

            edit_budget_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show_budget3();
                }
            });
            edit_budget_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show_budget3();
                }
            });
validate.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        dialog4 = new MaterialDialog.Builder(getActivity())
                .title("Shopping list")

                .content("Validate shopping list ?")
                .positiveText("Validate")
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
                        new SnackBar.Builder(getActivity())

                                .withMessage(shoppingList.get(0).getShopping_list_name()
                                        + " has been saved on history section") //


                                .withTypeFace(Typeface.DEFAULT_BOLD)


                                .withActionMessage("Close")
                                .withStyle(SnackBar.Style.INFO)
                                .withTextColorId(R.color.my_color_2)
                                .withBackgroundColorId(R.color.my_color)
                                .withDuration(SnackBar.SHORT_SNACK)
                                .show();


                        Button validate = (Button)vieww.findViewById(R.id.validate_shopping_list);
                        RecyclerView recyle = (RecyclerView)vieww.findViewById(R.id.recyclerView);
                        RelativeLayout relativeLayout = (RelativeLayout)vieww.findViewById(R.id.relative_recycle);
                        TextView no_shopping_display= (TextView)vieww.findViewById(R.id.no_shopping_display);
                        ImageView imageViewpic=(ImageView)vieww.findViewById(R.id.imageViewpic);
                        imageViewpic.setVisibility(View.VISIBLE);
                        validate.setVisibility(View.INVISIBLE);
                        no_shopping_display.setVisibility(View.VISIBLE);
                        recyle.setVisibility(View.INVISIBLE);
                        relativeLayout.setVisibility(View.INVISIBLE);
                        budgetBDD = new BudgetBDD(getActivity());
                        budgetBDD.open();
                        productBudgetList = budgetBDD.selectAll();
                        budgetBDD.close();
                        shoppingListBDD = new ShoppingListBDD(getActivity());
                        shoppingListBDD.open();
                       shoppingList= shoppingListBDD.selectAll();
                        shoppingListBDD.close();
historyShoppingListBDD= new HistoryShoppingListBDD(getActivity());
                        historyShoppingListBDD.open();
                        for(int i=0;i<shoppingList.size();i++){
                            historyShoppingListBDD.insertTop(new HistoryShoppingLists(
                                    shoppingList.get(i).getShopping_list_name(),shoppingList.get(i).getShopping_Product_id()
                                    ,shoppingList.get(i).getShopping_Product_sheve_id(),shoppingList.get(i).getShopping_Product_name()
                                    ,shoppingList.get(i).getShopping_Product_image(),shoppingList.get(i).getShopping_Product_category()
                                    ,shoppingList.get(i).getShopping_Product_price(),shoppingList.get(i).getShopping_Product_created_at()
                                    ,shoppingList.get(i).getShopping_product_state()
                            ));

                        }

                        historyShoppingListBDD.close();

if(!productBudgetList.isEmpty()) {
    historyBDD = new HistoryBDD(getActivity());
    historyBDD.open();
    historyBDD.insertTop(new History(shoppingList.get(0).getShopping_list_name(),
            productBudgetList.get(0).getSpent() + " DT / " + productBudgetList.get(0).getBudget()+" DT"
            , String.valueOf(shoppingList.size()), "dd/mm/yyyy"
    ));
    historyBDD.close();
}
                        else  {

    historyBDD = new HistoryBDD(getActivity());
    historyBDD.open();
    historyBDD.insertTop(new History(shoppingList.get(0).getShopping_list_name(),
            "none"
            ,String.valueOf(shoppingList.size()),"dd/mm/yyyy"
    ));
    historyBDD.close();

                        }


                        shoppingListBDD = new ShoppingListBDD(getActivity());
                        shoppingListBDD.open();
                        shoppingListBDD.removeAllArticles();
                        shoppingListBDD.close();
                        budgetBDD = new BudgetBDD(getActivity());
                        budgetBDD.open();
                        budgetBDD.removeAllArticles();
                        budgetBDD.close();

                        sharedpreferences.edit().remove("SmartShopping").commit();
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        sharedpreferences = getActivity().getSharedPreferences("SmartShopping", Context.MODE_PRIVATE);
                        editor.putString("budget", "false");
                        editor.commit();


                    }
                })

                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {



                    }
                })
                .build();

        dialog4.show();


    }
});

        }
        }

    public class ShoppingListRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  implements SnackBar.OnMessageClickListener {

        List<ShoppingList> contents;
        SharedPreferences sharedpreferences;
        String name, price, description, id, id_sheve, category, discount, old_price;
        Drawable picture;
        ShoppingListBDD shoppingListBDD;
        List<ShoppingList> productsList;
        String Budget_Reached = "no";
        BudgetBDD budgetBDD;

        String Budget_value, Shopping_list_name;
        MaterialDialog dialog3, dialog2, dialog1, dialog4;
        List<Budget> productBudgetList;
        EditText Input1, Input2;
        View positiveAction;
        boolean withbudget;
        byte[] bitmapdata;
        MaterialDialog.Builder builder;
        Context par;
        ViewGroup par2;
        static final int TYPE_HEADER = 0;
        static final int TYPE_CELL = 1;

        public Activity getContext() {
            return context;
        }

        public void setContext(Activity context) {
            this.context = context;
        }



        Activity context;

        public ShoppingListRecyclerViewAdapter(List<ShoppingList> contents, Activity context) {

            this.contents = contents;
            this.context = context;

            sharedpreferences = context.getSharedPreferences("SmartShopping", Context.MODE_PRIVATE);
        }

        @Override
        public int getItemViewType(int position) {
            switch (position) {


                default:
                    return TYPE_CELL;
            }
        }

        @Override
        public int getItemCount() {
            return contents.size();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = null;

            switch (viewType) {

                case TYPE_CELL: {
                    view = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.list_item_card_small_shopping_list, parent, false);
                    return new RecyclerView.ViewHolder(view) {
                    };
                }
            }
            return null;
        }


        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
            switch (getItemViewType(position)) {

                case TYPE_CELL:

                    final ImageView image1 = (ImageView) holder.itemView.findViewById(R.id.imageView244);
                    final TextView title1 = (TextView) holder.itemView.findViewById(R.id.title23);
                    final TextView oldprice1 = (TextView) holder.itemView.findViewById(R.id.price_value1);
final  ImageView image2=(ImageView)holder.itemView.findViewById(R.id.imageView245);
final TextView pricetext =(TextView)holder.itemView.findViewById(R.id.price1);

                    bitmapdata = contents.get(position).getShopping_Product_image();
                    // if bitmapdata is the byte array then getting bitmap goes like this

                    Bitmap bitmap1 = BitmapFactory.decodeByteArray(bitmapdata, 0,
                            bitmapdata.length);
                    image1.setImageBitmap(bitmap1);


                    title1.setText(contents.get(position).getShopping_Product_name());

                    oldprice1.setText(contents.get(position).getShopping_Product_price() + " DT");

                    ButtonFlat remove = (ButtonFlat) holder.itemView.findViewById(R.id.buttonflat333);
                    final ButtonFlat done = (ButtonFlat) holder.itemView.findViewById(R.id.buttonflat34);
final ButtonFlat undo = (ButtonFlat)holder.itemView.findViewById(R.id.buttonflat345);

                    if(contents.get(position).getShopping_product_state().equals("true")){

                        image1.setAlpha((float) 0.3);
                        title1.setAlpha((float) 0.3);
                        pricetext.setAlpha((float) 0.3);
                        oldprice1.setAlpha((float) 0.3);
                        image2.setVisibility(View.VISIBLE);
                        done.setVisibility(View.INVISIBLE);
                        undo.setVisibility(View.VISIBLE);
                    }
                    remove.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.e("products count before", contents.size() + " yes");
                            Log.e("product position", position + " yes");
final String name =  contents.get(position).getShopping_Product_name();
                            final String list_name = contents.get(position).getShopping_list_name();
                            dialog5 = new MaterialDialog.Builder(getActivity())
                                    .title(name)
                                    .iconRes(R.drawable.smart_shopping_logo)
                                    .maxIconSize(100)
                                    .content("Remove this product ?")
                                    .positiveText("Remove")
                                    .negativeText("Cancel")
                                    .positiveColor(Color.parseColor("#3498db"))
                                    .negativeColor(Color.parseColor("#000000"))
                                    .titleColor(Color.parseColor("#000000"))
                                    .contentColor(Color.parseColor("#80000000"))
                                    .backgroundColor(Color.parseColor("#ffffff"))
                                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                            shoppingListBDD = new ShoppingListBDD(holder.itemView.getContext());
                                            shoppingListBDD.open();
                                            shoppingListBDD.removeById(contents.get(position).getShopping_Product_id());
                                            shoppingListBDD.close();
                                            contents.remove(position);

                                            //mRecyclerView.removeViewAt(position);

                                            notifyItemRemoved(position);
                                            notifyItemRangeChanged(position, contents.size());
                                            Log.e("products count after", contents.size() + " yes");

                                            mAdapter.notifyDataSetChanged();
                                            new SnackBar.Builder(context)

                                                    .withMessage(name
                                                            + " has been removed") //


                                                    .withTypeFace(Typeface.DEFAULT_BOLD)


                                                    .withActionMessage("Close")
                                                    .withStyle(SnackBar.Style.INFO)
                                                    .withTextColorId(R.color.my_color_2)
                                                    .withBackgroundColorId(R.color.my_color)
                                                    .withDuration(SnackBar.SHORT_SNACK)
                                                    .show();
                                            shoppingListBDD = new ShoppingListBDD(holder.itemView.getContext());
                                            shoppingListBDD.open();
                                            shoppingList = shoppingListBDD.selectAll();
                                            shoppingListBDD.close();
                                            if (shoppingList.isEmpty()) {

                                                dialog5 = new MaterialDialog.Builder(getActivity())
                                                        .title("Empty shopping list")
                                                        .iconRes(R.drawable.smart_shopping_logo)
                                                        .maxIconSize(100)
                                                        .cancelable(false)
                                                        .content(list_name + " will be deleted")
                                                        .positiveText("Ok")

                                                        .positiveColor(Color.parseColor("#3498db"))

                                                        .titleColor(Color.parseColor("#000000"))
                                                        .contentColor(Color.parseColor("#80000000"))
                                                        .backgroundColor(Color.parseColor("#ffffff"))
                                                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                                                            @Override
                                                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                                                dialog5.setCancelable(true);
                                                                dialog5.dismiss();
                                                                new SnackBar.Builder(context)

                                                                        .withMessage(list_name
                                                                                + " has been deleted") //


                                                                        .withTypeFace(Typeface.DEFAULT_BOLD)


                                                                        .withActionMessage("Close")
                                                                        .withStyle(SnackBar.Style.INFO)
                                                                        .withTextColorId(R.color.my_color_2)
                                                                        .withBackgroundColorId(R.color.my_color)
                                                                        .withDuration(SnackBar.SHORT_SNACK)
                                                                        .show();


                                                                Button validate = (Button)vieww.findViewById(R.id.validate_shopping_list);
                                                                RecyclerView recyle = (RecyclerView)vieww.findViewById(R.id.recyclerView);
                                                                RelativeLayout relativeLayout = (RelativeLayout)vieww.findViewById(R.id.relative_recycle);
                                                                TextView no_shopping_display= (TextView)vieww.findViewById(R.id.no_shopping_display);
                                                                ImageView imageViewpic=(ImageView)vieww.findViewById(R.id.imageViewpic);
                                                                imageViewpic.setVisibility(View.VISIBLE);
                                                                validate.setVisibility(View.INVISIBLE);
                                                                no_shopping_display.setVisibility(View.VISIBLE);
                                                                recyle.setVisibility(View.INVISIBLE);
                                                                relativeLayout.setVisibility(View.INVISIBLE);



                                                                shoppingListBDD = new ShoppingListBDD(getActivity());
                                                                shoppingListBDD.open();
                                                                shoppingListBDD.removeAllArticles();
                                                                shoppingListBDD.close();
                                                                budgetBDD = new BudgetBDD(getActivity());
                                                                budgetBDD.open();
                                                                budgetBDD.removeAllArticles();
                                                                budgetBDD.close();

                                                                sharedpreferences.edit().remove("SmartShopping").commit();
                                                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                                                sharedpreferences = getActivity().getSharedPreferences("SmartShopping", Context.MODE_PRIVATE);
                                                                editor.putString("budget", "false");
                                                                editor.commit();
/*
                                                               SmartShoppingFragment f2 = new SmartShoppingFragment();

                                                                FragmentManager fragmentManager = getFragmentManager();
                                                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                                                fragmentTransaction.replace(R.id.fragment_container, f2, "tag");

                                                                fragmentTransaction.commit();
                                                                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Smart Shopping");
                                                                Container.result.setSelection(Container.result.getCurrentSelectedPosition()-1);
                                                            */
                                                            }
                                                        })

                                                        .build();

                                                dialog5.show();
                                            }

                                        }
                                    })

                                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                        }
                                    })
                                    .build();

                            dialog5.show();



                        }
                    });

                    done.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final String name =  contents.get(position).getShopping_Product_name();
                            dialog6 = new MaterialDialog.Builder(getActivity())
                                    .title(name)

                                    .content("Purchase this product ?")
                                    .positiveText("Purchase")
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


                                            image1.setAlpha((float) 0.3);
                                            title1.setAlpha((float) 0.3);
                                            pricetext.setAlpha((float) 0.3);
                                            oldprice1.setAlpha((float) 0.3);
                                            image2.setVisibility(View.VISIBLE);
                                            done.setVisibility(View.INVISIBLE);
                                            undo.setVisibility(View.VISIBLE);
                                            new SnackBar.Builder(context)

                                                    .withMessage(name
                                                            +" has been Purchased") //


                                                    .withTypeFace(Typeface.DEFAULT_BOLD)


                                                    .withActionMessage("Close")
                                                    .withStyle(SnackBar.Style.INFO)
                                                    .withTextColorId(R.color.my_color_2)
                                                    .withBackgroundColorId(R.color.my_color)
                                                    .withDuration(SnackBar.SHORT_SNACK)
                                                    .show();
                                            shoppingListBDD = new ShoppingListBDD(getActivity());
                                            shoppingListBDD.open();
                                            shoppingListBDD.UpdateById(contents.get(position).getShopping_Product_id());
                                            shoppingListBDD.close();


                                        }
                                    })

                                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                        }
                                    })
                                    .build();
                            dialog6.show();


                        }
                    });

                    undo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final String name =  contents.get(position).getShopping_Product_name();
                            dialog6 = new MaterialDialog.Builder(getActivity())
                                    .title(name)
                                    .iconRes(R.drawable.smart_shopping_logo)
                                    .maxIconSize(100)
                                    .content("Undo the purchase ?")
                                    .positiveText("Undo")
                                    .negativeText("Cancel")
                                    .positiveColor(Color.parseColor("#3498db"))
                                    .negativeColor(Color.parseColor("#000000"))
                                    .titleColor(Color.parseColor("#000000"))
                                    .contentColor(Color.parseColor("#80000000"))
                                    .backgroundColor(Color.parseColor("#ffffff"))
                                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {


                                            image1.setAlpha((float) 1);
                                            title1.setAlpha((float) 1);
                                            pricetext.setAlpha((float) 1);
                                            oldprice1.setAlpha((float) 1);
                                            image2.setVisibility(View.INVISIBLE);
                                            done.setVisibility(View.VISIBLE);
                                            undo.setVisibility(View.INVISIBLE);
                                            new SnackBar.Builder(context)

                                                    .withMessage(name
                                                            +" Purchase Undone ") //


                                                    .withTypeFace(Typeface.DEFAULT_BOLD)


                                                    .withActionMessage("Close")
                                                    .withStyle(SnackBar.Style.INFO)
                                                    .withTextColorId(R.color.my_color_2)
                                                    .withBackgroundColorId(R.color.my_color)
                                                    .withDuration(SnackBar.SHORT_SNACK)
                                                    .show();

                                            shoppingListBDD = new ShoppingListBDD(getActivity());
                                            shoppingListBDD.open();
                                            shoppingListBDD.UpdateById1(contents.get(position).getShopping_Product_id());
                                            shoppingListBDD.close();

                                        }
                                    })

                                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                        }
                                    })
                                    .build();
                            dialog6.show();

                        }
                    });


                    break;

            }
        }


        @Override
        public void onMessageClick(Parcelable token) {

        }
    }



        public void show_budget2(){
        productBudgetList = new ArrayList<Budget>();
        budgetBDD = new BudgetBDD(getActivity());
        budgetBDD.open();
        productBudgetList = budgetBDD.selectAll();
        budgetBDD.close();
        dialog3 =  new MaterialDialog.Builder(getActivity())
                .title("Budget")
                .customView(R.layout.dialog_customview4, true)
                .positiveText("Done")
                .negativeText("Cancel")
                .iconRes(R.drawable.smart_shopping_logo)
                .maxIconSize(100)
                .titleColor(Color.parseColor("#000000"))
                .contentColor(Color.parseColor("#80000000"))
                .backgroundColor(Color.parseColor("#ffffff"))
                .positiveColor(Color.parseColor("#3498db"))
                .negativeColor(Color.parseColor("#000000"))
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                        budget_check.setChecked(false);
                        Spent_Budget_text.setVisibility(View.INVISIBLE);
                        Spent_Budget_value.setVisibility(View.INVISIBLE);
                        sharedpreferences.edit().remove("SmartShopping").commit();
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        sharedpreferences = getActivity().getSharedPreferences("SmartShopping", Context.MODE_PRIVATE);
                        editor.putString("budget", "false");
                        editor.commit();
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Float value = Float.valueOf(productBudgetList.get(0).getSpent());
                        if (value > Float.valueOf(Budget_value)) {

                            dialog4 = new MaterialDialog.Builder(getActivity())
                                    .title("Budget limit reached")
                                    .autoDismiss(false)
                                    .cancelable(false)
                                    .iconRes(R.drawable.smart_shopping_logo)
                                    .maxIconSize(100)
                                    .customView(R.layout.dialog_customview5, true)
                                    .positiveText("Increase")
                                    .negativeText("Cancel")
                                    .titleColor(Color.parseColor("#000000"))
                                    .contentColor(Color.parseColor("#80000000"))
                                    .backgroundColor(Color.parseColor("#ffffff"))
                                    .positiveColor(Color.parseColor("#3498db"))
                                    .negativeColor(Color.parseColor("#000000"))
                                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                            show_budget2();


                                        }
                                    })

                                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                            budget_check.setChecked(false);
                                            Spent_Budget_text.setVisibility(View.INVISIBLE);
                                            Spent_Budget_value.setVisibility(View.INVISIBLE);
                                            sharedpreferences.edit().remove("SmartShopping").commit();
                                            SharedPreferences.Editor editor = sharedpreferences.edit();
                                            sharedpreferences = getActivity().getSharedPreferences("SmartShopping", Context.MODE_PRIVATE);
                                            editor.putString("budget", "false");
                                            editor.commit();
                                            dialog4.setCancelable(true);
                                            dialog4.dismiss();
                                        }
                                    })
                                    .build();
                            TextView spent_budget = (TextView) dialog4.getCustomView().findViewById(R.id.spent_budget_value1);

                            spent_budget.setText(String.format("%.3f", value) + " DT / " + String.format("%.3f", Float.valueOf(productBudgetList.get(0).getBudget())) + " DT");


                            dialog4.show();


                        } else {

                            productBudgetList = new ArrayList<Budget>();
                            BudgetBDD budgetBDD = new BudgetBDD(getActivity());
                            budgetBDD.open();
                            productBudgetList = budgetBDD.selectAll();

                            budgetBDD.removeAllArticles();
                            budgetBDD.insertTop(new Budget(String.format("%.3f", Float.valueOf(Budget_value)), String.format("%.3f", Float.valueOf(productBudgetList.get(0).getSpent()))));
                            budgetBDD.close();
                            BudgetBDD budgetBDD1 = new BudgetBDD(getActivity());
                            budgetBDD1.open();
                            productBudgetList = budgetBDD1.selectAll();


                            budgetBDD1.close();
                            dialog4.dismiss();
                            Spent_Budget_value.setText(productBudgetList.get(0).getSpent() + "DT / " + productBudgetList.get(0).getBudget() + " DT");
                            new SnackBar.Builder(getActivity())

                                    .withMessage("Your Budget has changed to ") //


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

        old_budget.setText(productBudgetList.get(0).getBudget()+" DT");
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

        dialog3

                .show();
        positiveAction.setEnabled(false);
    }


    public void show_budget3(){
        productBudgetList = new ArrayList<Budget>();
        budgetBDD = new BudgetBDD(getActivity());
        budgetBDD.open();
        productBudgetList = budgetBDD.selectAll();
        budgetBDD.close();
        dialog3 =  new MaterialDialog.Builder(getActivity())
                .title("Budget")
                .customView(R.layout.dialog_customview4, true)
                .positiveText("Done")
                .negativeText("Cancel")
                .iconRes(R.drawable.smart_shopping_logo)
                .maxIconSize(100)
                .titleColor(Color.parseColor("#000000"))
                .contentColor(Color.parseColor("#80000000"))
                .backgroundColor(Color.parseColor("#ffffff"))
                .positiveColor(Color.parseColor("#3498db"))
                .negativeColor(Color.parseColor("#000000"))
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {


                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Float value = Float.valueOf(productBudgetList.get(0).getSpent());
                        if (value > Float.valueOf(Budget_value)) {

                            dialog4 = new MaterialDialog.Builder(getActivity())
                                    .title("Budget limit reached")
                                    .iconRes(R.drawable.smart_shopping_logo)
                                    .maxIconSize(100)
                                    .customView(R.layout.dialog_customview5, true)
                                    .positiveText("Increase")
                                    .negativeText("Cancel")
                                    .titleColor(Color.parseColor("#000000"))
                                    .contentColor(Color.parseColor("#80000000"))
                                    .backgroundColor(Color.parseColor("#ffffff"))
                                    .positiveColor(Color.parseColor("#3498db"))
                                    .negativeColor(Color.parseColor("#000000"))
                                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                            show_budget2();


                                        }
                                    })

                                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {


                                        }
                                    })
                                    .build();
                            TextView spent_budget = (TextView) dialog4.getCustomView().findViewById(R.id.spent_budget_value1);

                            spent_budget.setText(String.format("%.3f", value) + " DT / " + String.format("%.3f", Float.valueOf(productBudgetList.get(0).getBudget())) + " DT");


                            dialog4.show();


                        } else {

                            productBudgetList = new ArrayList<Budget>();
                            BudgetBDD budgetBDD = new BudgetBDD(getActivity());
                            budgetBDD.open();
                            productBudgetList = budgetBDD.selectAll();

                            budgetBDD.removeAllArticles();
                            budgetBDD.insertTop(new Budget(String.format("%.3f", Float.valueOf(Budget_value)), String.format("%.3f", Float.valueOf(productBudgetList.get(0).getSpent()))));
                            budgetBDD.close();
                            BudgetBDD budgetBDD1 = new BudgetBDD(getActivity());
                            budgetBDD1.open();
                            productBudgetList = budgetBDD1.selectAll();


                            budgetBDD1.close();
                            dialog4.dismiss();
                            Spent_Budget_value.setText(productBudgetList.get(0).getSpent() + "DT / " + productBudgetList.get(0).getBudget() + " DT");
                            new SnackBar.Builder(getActivity())

                                    .withMessage("Your Budget has changed to ") //


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

        old_budget.setText(productBudgetList.get(0).getBudget()+" DT");
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

        dialog3

                .show();
        positiveAction.setEnabled(false);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        super.onCreateOptionsMenu(menu, inflater);

    }

    public void show_budget(){

        dialog3 =  new MaterialDialog.Builder(getActivity())
                .title("Budget")
                .customView(R.layout.dialog_customview2, true)
                .positiveText("Done")
                .negativeText("Cancel")
                .cancelable(false)
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
                                        dialog4 = new MaterialDialog.Builder(getActivity())
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
                                                        show_budget();


                                                    }
                                                })

                                                .onNegative(new MaterialDialog.SingleButtonCallback() {
                                                    @Override
                                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                        budget_check.setChecked(false);
                                                        Spent_Budget_text.setVisibility(View.INVISIBLE);
                                                        Spent_Budget_value.setVisibility(View.INVISIBLE);
                                                        sharedpreferences.edit().remove("SmartShopping").commit();
                                                        SharedPreferences.Editor editor = sharedpreferences.edit();
                                                        sharedpreferences = getActivity().getSharedPreferences("SmartShopping", Context.MODE_PRIVATE);
                                                        editor.putString("budget", "false");
                                                        editor.commit();
                                                        dialog3.setCancelable(true);
                                                        dialog4.dismiss();
                                                    }
                                                })
                                                .build();
                                        TextView spent_budget = (TextView) dialog4.getCustomView().findViewById(R.id.spent_budget_value);
                                        TextView product_price = (TextView) dialog4.getCustomView().findViewById(R.id.product_price_value);

                                        spent_budget.setText(String.format("%.3f", Float.valueOf(price)) + " DT / " + String.format("%.3f", Float.valueOf(Budget_value)) + " DT");
                                        product_price.setText(String.format("%.3f", Float.valueOf(price)) + " DT");

                                        dialog4.show();


                                    } else {

                                        dialog3.setCancelable(true);
                                        dialog3.dismiss();
                                        BudgetBDD budgetBDD = new BudgetBDD(getActivity());
                                        budgetBDD.open();
                                        budgetBDD.removeAllArticles();
                                        budgetBDD.insertTop(new Budget(String.format("%.3f", Float.valueOf(Budget_value)), String.format("%.3f", Float.valueOf(price))));
                                        budgetBDD.close();
                                        BudgetBDD budgetBDD1 = new BudgetBDD(getActivity());
                                        budgetBDD1.open();
                                        productBudgetList = budgetBDD1.selectAll();


                                        budgetBDD1.close();
                                        edit_budget_text.setVisibility(View.VISIBLE);
                                        edit_budget_image.setVisibility(View.VISIBLE);
                                        Spent_Budget_text.setVisibility(View.VISIBLE);
                                        Spent_Budget_value.setVisibility(View.VISIBLE);


                                        Spent_Budget_value.setText(productBudgetList.get(0).getSpent() + "DT / " + productBudgetList.get(0).getBudget() + " DT");
                                        SharedPreferences.Editor editor= sharedpreferences.edit();
                                        editor.putString("budget", "true");
                                        editor.commit();
                                        new SnackBar.Builder(getActivity())

                                                .withMessage("Budget Created") //


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
                                           dialog3.setCancelable(true);
                                           dialog3.dismiss();
                                           budget_check.setChecked(false);
                                           edit_budget_text.setVisibility(View.INVISIBLE);
                                           edit_budget_image.setVisibility(View.INVISIBLE);
                                           Spent_Budget_text.setVisibility(View.INVISIBLE);
                                           Spent_Budget_value.setVisibility(View.INVISIBLE);
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection

        return true;
    }

}



