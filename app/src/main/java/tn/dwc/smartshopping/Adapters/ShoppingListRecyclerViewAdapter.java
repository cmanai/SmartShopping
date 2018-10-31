package tn.dwc.smartshopping.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.internal.MDTintHelper;
import com.afollestad.materialdialogs.internal.ThemeSingleton;
import com.gc.materialdesign.views.ButtonFlat;
import com.github.mrengineer13.snackbar.SnackBar;

import java.util.ArrayList;
import java.util.List;

import tn.dwc.smartshopping.Activities.Container;
import tn.dwc.smartshopping.Activities.DetailsActivity;
import tn.dwc.smartshopping.Entities.Budget;
import tn.dwc.smartshopping.Entities.Products;
import tn.dwc.smartshopping.Entities.ShoppingList;
import tn.dwc.smartshopping.R;
import tn.dwc.smartshopping.SQLite.BudgetBDD;
import tn.dwc.smartshopping.SQLite.ShoppingListBDD;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class ShoppingListRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  implements SnackBar.OnMessageClickListener  {

    List<ShoppingList> contents;
    SharedPreferences sharedpreferences;
    String name,price,description,id,id_sheve,category,discount,old_price;
    Drawable picture;
    ShoppingListBDD shoppingListBDD;
    List<ShoppingList> productsList;
    String Budget_Reached="no";
    BudgetBDD budgetBDD;

    String Budget_value,Shopping_list_name;
    MaterialDialog dialog3,dialog2,dialog1,dialog4;
    List<Budget> productBudgetList;
    EditText Input1,Input2;
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

    public RecyclerView getmRecyclerView() {
        return mRecyclerView;
    }

    public void setmRecyclerView(RecyclerView mRecyclerView) {
        this.mRecyclerView = mRecyclerView;
    }

    RecyclerView mRecyclerView;
    Activity context;
    public ShoppingListRecyclerViewAdapter(List<ShoppingList> contents, Activity context) {

        this.contents = contents;
        this.context=context;

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

                ImageView image1 = (ImageView) holder.itemView.findViewById(R.id.imageView244);
                TextView title1 = (TextView) holder.itemView.findViewById(R.id.title23);
                TextView oldprice1 = (TextView) holder.itemView.findViewById(R.id.price_value1);


                bitmapdata = contents.get(position).getShopping_Product_image();
                // if bitmapdata is the byte array then getting bitmap goes like this

                Bitmap bitmap1 = BitmapFactory.decodeByteArray(bitmapdata, 0,
                        bitmapdata.length);
                image1.setImageBitmap(bitmap1);



                title1.setText(contents.get(position).getShopping_Product_name());

                oldprice1.setText(contents.get(position).getShopping_Product_price() + " DT");

                ButtonFlat remove = (ButtonFlat) holder.itemView.findViewById(R.id.buttonflat333);
                ButtonFlat done = (ButtonFlat) holder.itemView.findViewById(R.id.buttonflat34);

remove.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Log.e("products count before", contents.size() + " yes");
        Log.e("product position", position + " yes");


        shoppingListBDD = new ShoppingListBDD(holder.itemView.getContext());
        shoppingListBDD.open();
        shoppingListBDD.removeById(contents.get(position).getShopping_Product_id());
        shoppingListBDD.close();
        contents.remove(position);

        mRecyclerView.removeViewAt(position);

        notifyItemRemoved(position);
        notifyItemRangeChanged(position, contents.size());
        Log.e("products count after", contents.size() + " yes");

        notifyDataSetChanged();

    }
});


                break;

        }
    }



    @Override
    public void onMessageClick(Parcelable token) {

    }

}