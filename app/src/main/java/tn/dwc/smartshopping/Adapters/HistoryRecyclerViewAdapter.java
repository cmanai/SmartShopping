package tn.dwc.smartshopping.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.gc.materialdesign.views.ButtonFlat;
import com.github.mrengineer13.snackbar.SnackBar;

import java.util.List;

import tn.dwc.smartshopping.Entities.Budget;
import tn.dwc.smartshopping.Entities.History;
import tn.dwc.smartshopping.Entities.ShoppingList;
import tn.dwc.smartshopping.R;
import tn.dwc.smartshopping.SQLite.BudgetBDD;
import tn.dwc.smartshopping.SQLite.ShoppingListBDD;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class HistoryRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  implements SnackBar.OnMessageClickListener  {

    List<History> contents;
    SharedPreferences sharedpreferences;
    String name,price,description,id,id_sheve,category,discount,old_price;
    Drawable picture;
    ShoppingListBDD shoppingListBDD;
    List<History> productsList;
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
    public HistoryRecyclerViewAdapter(List<History> contents, Activity context) {

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
                        .inflate(R.layout.list_item_card_small_history, parent, false);
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
                TextView title1 = (TextView) holder.itemView.findViewById(R.id.title2344);
                TextView budget_history = (TextView) holder.itemView.findViewById(R.id.budget_history);

                TextView product_history = (TextView) holder.itemView.findViewById(R.id.product_number);
                TextView date_history = (TextView) holder.itemView.findViewById(R.id.creation_date);


                Bitmap icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.smart_shopping_logo);
                image1.setImageBitmap(icon);


                product_history.setText(contents.get(position).getProducts_Number() + " Products");
               // date_history.setText(contents.get(position).getCreation_Date());
                title1.setText(contents.get(position).getHistory_Name());

                budget_history.setText(contents.get(position).getBudget());




                break;

        }
    }



    @Override
    public void onMessageClick(Parcelable token) {

    }

}