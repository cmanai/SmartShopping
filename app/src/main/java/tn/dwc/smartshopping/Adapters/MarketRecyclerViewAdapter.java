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

import tn.dwc.smartshopping.SQLite.BudgetBDD;
import tn.dwc.smartshopping.Activities.Container;
import tn.dwc.smartshopping.Activities.DetailsActivity;
import tn.dwc.smartshopping.Entities.Budget;
import tn.dwc.smartshopping.Entities.Products;
import tn.dwc.smartshopping.Entities.ShoppingList;
import tn.dwc.smartshopping.R;
import tn.dwc.smartshopping.SQLite.ShoppingListBDD;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class MarketRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements SnackBar.OnMessageClickListener   {

    List<Products> contents;
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

    Activity context;
    public MarketRecyclerViewAdapter(List<Products> contents,Activity context) {

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
            case TYPE_HEADER: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_card_big, parent, false);
                return new RecyclerView.ViewHolder(view) {
                };
            }
            case TYPE_CELL: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_card_small, parent, false);
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

                ImageView image1 = (ImageView)  holder.itemView.findViewById(R.id.imageView24);
                TextView title1 = (TextView)  holder.itemView.findViewById(R.id.title2);
                TextView oldprice1 = (TextView)  holder.itemView.findViewById(R.id.price_value);



                bitmapdata = contents.get(position).getProduct_image();
                // if bitmapdata is the byte array then getting bitmap goes like this

                Bitmap bitmap1 = BitmapFactory.decodeByteArray(bitmapdata, 0,
                        bitmapdata.length);
                image1.setImageBitmap(bitmap1);



                title1.setText(contents.get(position).getProduct_name());

                oldprice1.setText(contents.get(position).getProduct_price() + " DT");
                ButtonFlat details = (ButtonFlat)holder.itemView.findViewById(R.id.buttonflat33);
                details.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent i;
                        i = new Intent(holder.itemView.getContext(), DetailsActivity.class);
                        i.putExtra("product_id", contents.get(position).getProduct_id());
                        i.putExtra("product_name", contents.get(position).getProduct_name());
                        i.putExtra("product_price", contents.get(position).getProduct_price());
                        i.putExtra("product_sheve_id", contents.get(position).getProduct_sheve_id());
                        if (contents.get(position).getProduct_sub_category().isEmpty()) {
                            i.putExtra("product_category", contents.get(position).getProduct_category());
                        } else {

                            i.putExtra("product_category", contents.get(position).getProduct_category() + " - " + contents.get(position).getProduct_sub_category());
                        }
                        i.putExtra("product_description", contents.get(position).getProduct_desc());
                        i.putExtra("product_picture", contents.get(position).getProduct_image());

                        holder.itemView.getContext().startActivity(i);
                    }
                });

                ButtonFlat add = (ButtonFlat) holder.itemView.findViewById(R.id.buttonflat3);
                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        id = contents.get(position).getProduct_id();
                        id_sheve = contents.get(position).getProduct_sheve_id();
                        name = contents.get(position).getProduct_name();
                        price = contents.get(position).getProduct_price();
                        bitmapdata = contents.get(position).getProduct_image();

                        Log.e("Product selected", name);
                        if (contents.get(position).getProduct_sub_category().equals("none")) {
                            category = contents.get(position).getProduct_category();
                        } else {

                            category = contents.get(position).getProduct_category() + " - " + contents.get(position).getProduct_sub_category();
                        }
                        productsList = new ArrayList<ShoppingList>();
                        shoppingListBDD = new ShoppingListBDD(holder.itemView.getContext());
                        shoppingListBDD.open();
                        productsList = shoppingListBDD.selectAll();

                        shoppingListBDD.close();

                        if (productsList.isEmpty()) {
                            new MaterialDialog.Builder(holder.itemView.getContext())
                                    .title("No shopping list")
                                    .content("Create a new shopping list ?")
                                    .backgroundColor(Color.parseColor("#ffffff"))
                                    .titleColor(Color.parseColor("#000000"))
                                    .iconRes(R.drawable.smart_shopping_logo)
                                    .maxIconSize(100)
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
                                                    .positiveText("Done")
                                                    .iconRes(R.drawable.smart_shopping_logo)
                                                    .maxIconSize(100)
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
                                                                new SnackBar.Builder(context)

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
                                        new SnackBar.Builder(context)


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
                                    BudgetBDD budgetBDD = new BudgetBDD(holder.itemView.getContext());
                                    budgetBDD.open();
                                    productBudgetList = budgetBDD.selectAll();

                                    if(!productBudgetList.isEmpty()){
                                        Float new_value = Float.valueOf(price) + Float.valueOf(productBudgetList.get(0).getSpent());
                                        budgetBDD.removeAllArticles();
                                        budgetBDD.insertTop(new Budget(String.format("%.3f", Float.valueOf(productBudgetList.get(0).getBudget())), String.format("%.3f", new_value)));
                                        budgetBDD.close();
                                    }

                                    new SnackBar.Builder(context)

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
                                        .titleColor(Color.parseColor("#000000"))
                                        .iconRes(R.drawable.smart_shopping_logo)
                                        .maxIconSize(100)
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
                break;

        }
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
                                        new SnackBar.Builder(context)

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
                            new SnackBar.Builder(context)

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

    @Override
    public void onMessageClick(Parcelable token) {

    }
}