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
import tn.dwc.smartshopping.Activities.QRProductFindActivity;
import tn.dwc.smartshopping.Entities.Budget;
import tn.dwc.smartshopping.Entities.Maps_Product;
import tn.dwc.smartshopping.Entities.Products;
import tn.dwc.smartshopping.Entities.ShoppingList;
import tn.dwc.smartshopping.R;
import tn.dwc.smartshopping.SQLite.BudgetBDD;
import tn.dwc.smartshopping.SQLite.Maps_ProductBDD;
import tn.dwc.smartshopping.SQLite.ShoppingListBDD;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class FindRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  implements SnackBar.OnMessageClickListener  {

    List<Products> contents;

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
    Bitmap bitmap2;
    Bitmap bitmap3;
    Maps_ProductBDD maps_productBDD;
    List<Maps_Product> maps_products;
    List<Maps_Product> maps_products2;
    MaterialDialog.Builder builder;
    Context par;
    SharedPreferences sharedpreferences;
    ViewGroup par2;
    static final int TYPE_HEADER = 0;
    static final int TYPE_CELL = 1;
    public Activity getContext() {
        return context;
    }

    public void setContext(Activity context) {
        this.context = context;
    }

    public String getHB() {
        return HB;
    }

    public void setHB(String HB) {
        this.HB = HB;
    }

    String HB;
    Activity context;
    public FindRecyclerViewAdapter(List<Products> contents, Activity context,String HB) {
        sharedpreferences = context.getSharedPreferences("SmartShopping", Context.MODE_PRIVATE);
        this.contents = contents;
        this.context=context;
        sharedpreferences = context.getSharedPreferences("SmartShopping", Context.MODE_PRIVATE);
        this.HB = HB;
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
            case TYPE_HEADER: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_card_big, parent, false);
                return new RecyclerView.ViewHolder(view) {
                };
            }
            case TYPE_CELL: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_card_small_find, parent, false);
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

                ImageView image1 = (ImageView) holder.itemView.findViewById(R.id.imageView24);
                TextView title1 = (TextView) holder.itemView.findViewById(R.id.title2);
                TextView oldprice1 = (TextView) holder.itemView.findViewById(R.id.price_value);
                ButtonFlat find = (ButtonFlat) holder.itemView.findViewById(R.id.buttonflatfind);


                bitmapdata = contents.get(position).getProduct_image();
                // if bitmapdata is the byte array then getting bitmap goes like this

                Bitmap bitmap1 = BitmapFactory.decodeByteArray(bitmapdata, 0,
                        bitmapdata.length);
                image1.setImageBitmap(bitmap1);


                title1.setText(contents.get(position).getProduct_name());

                oldprice1.setText(contents.get(position).getProduct_price() + " DT");

find.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        maps_products= new ArrayList<>();
        maps_products2= new ArrayList<>();

        maps_productBDD= new Maps_ProductBDD(context);
        maps_productBDD.open();
        maps_products = maps_productBDD.selectAll();
        maps_productBDD.close();int i=0;
        for (Maps_Product m : maps_products){

            if(m.getH_B_p().equals(HB)
                    &&m.getProduct_Id_p().equals(contents.get(position).getProduct_id())
                    &&m.getShelve_Id_p().equals(contents.get(position).getProduct_sheve_id())){

                bitmap3 = BitmapFactory.decodeByteArray(m.getMaps_p(), 0,
                        m.getMaps_p().length);
            }

        }

        if(bitmap3!=null) {


            Intent intent = new Intent(context,QRProductFindActivity.class);
            intent.putExtra("shelve_id",contents.get(position).getProduct_sheve_id());
            intent.putExtra("shelve_hb",HB);
            intent.putExtra("product_id", contents.get(position).getProduct_id());
            context.startActivity(intent);
        }
        else{
            new SnackBar.Builder(context)

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

              break;

        }
    }


    @Override
    public void onMessageClick(Parcelable token) {

    }

}