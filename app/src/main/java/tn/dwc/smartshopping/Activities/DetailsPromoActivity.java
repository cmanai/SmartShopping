
/*
 * Copyright (C) 2013 Manuel Peinado
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
    package tn.dwc.smartshopping.Activities;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.internal.MDTintHelper;
import com.afollestad.materialdialogs.internal.ThemeSingleton;
import com.gc.materialdesign.views.ButtonFloat;
import com.github.mrengineer13.snackbar.SnackBar;
import com.manuelpeinado.fadingactionbar.extras.actionbarcompat.FadingActionBarHelper;

import java.util.ArrayList;
import java.util.List;

import tn.dwc.smartshopping.SQLite.BudgetBDD;
import tn.dwc.smartshopping.Entities.Budget;
import tn.dwc.smartshopping.Entities.ShoppingList;
import tn.dwc.smartshopping.R;
import tn.dwc.smartshopping.SQLite.ShoppingListBDD;

public class DetailsPromoActivity extends AppCompatActivity implements SnackBar.OnMessageClickListener  {
String name,price,description,id,id_sheve,category,discount,old_price,new_price;
    Drawable picture;
    SharedPreferences sharedpreferences;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  image =  new BitmapDrawable(BitmapFactory.decodeByteArray(fileData, 0, fileData.length));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);


        }

        Window window = getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(Color.parseColor("#303F9F"));
        sharedpreferences = getSharedPreferences("SmartShopping", Context.MODE_PRIVATE);


        final ActionBar actionBar = getSupportActionBar();

            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayUseLogoEnabled(false);
            actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle("Home");
        id = getIntent().getStringExtra("product_id");
        id_sheve = getIntent().getStringExtra("product_sheve_id");
        name = getIntent().getStringExtra("product_name");
        price= getIntent().getStringExtra("product_price");
        description =  getIntent().getStringExtra("product_description");
        category =  getIntent().getStringExtra("product_category");

        old_price =  getIntent().getStringExtra("product_old_price");
        new_price =  getIntent().getStringExtra("product_new_price");
        discount =  getIntent().getStringExtra("product_pourcentage");

         bitmapdata = getIntent().getByteArrayExtra("product_picture");
        // if bitmapdata is the byte array then getting bitmap goes like this

        Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapdata, 0,
                bitmapdata.length);
       // Drawable d = new BitmapDrawable(getResources(), bitmap);
       // picture =  d;
        FadingActionBarHelper helper = new FadingActionBarHelper()
                .actionBarBackground(R.drawable.ab_background)
                .headerLayout(R.layout.header)

                .contentLayout(R.layout.activity_details_promo);
        View v = helper.createView(this);
        ImageView img = (ImageView) v.findViewById(R.id.image_header);
        img.setImageBitmap(bitmap);
        TextView title_p = (TextView) v.findViewById(R.id.title_product);

        TextView category_p = (TextView) v.findViewById(R.id.category_product);
        TextView description_p = (TextView) v.findViewById(R.id.description_product);
        TextView discount_p = (TextView) v.findViewById(R.id.price_discount);
        TextView old_price_p = (TextView) v.findViewById(R.id.old_price_pp);
        TextView new_price_p = (TextView) v.findViewById(R.id.new_price_pp);
        old_price_p.setText(old_price+" DT");
        new_price_p.setText(new_price+" DT");
        discount_p.setText("- "+discount+"%");
        title_p.setText(name);
        ButtonFloat add = (ButtonFloat)v.findViewById(R.id.add_to_list_promo);

        category_p.setText("("+category+")");
        description_p.setText(description);
        setContentView(v);
        helper.initActionBar(this);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productsList = new ArrayList<ShoppingList>();
                shoppingListBDD = new ShoppingListBDD(DetailsPromoActivity.this);
                shoppingListBDD.open();
                productsList = shoppingListBDD.selectAll();

                shoppingListBDD.close();

                if (productsList.isEmpty()) {
                    builder = new MaterialDialog.Builder(DetailsPromoActivity.this)
                            .title("No shopping list")
                            .iconRes(R.drawable.smart_shopping_logo)
                            .maxIconSize(100)
                            .titleColor(Color.parseColor("#000000"))
                            .contentColor(Color.parseColor("#80000000"))
                            .backgroundColor(Color.parseColor("#ffffff"))
                            .content("Create a new shopping list ?")
                            .positiveColor(Color.parseColor("#3498db"))
                            .negativeColor(Color.parseColor("#000000"))
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {


                                    dialog1.dismiss();


                                    dialog2 = new MaterialDialog.Builder(DetailsPromoActivity.this)
                                            .title("Shopping list Informations")
                                            .customView(R.layout.dialog_customview, true)
                                            .positiveText("Done")
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


                                                    if (withbudget) {

                                                        show_budget();
                                                    } else {

                                                        //adding shopping list without budget
                                                        shoppingListBDD = new ShoppingListBDD(DetailsPromoActivity.this);
                                                        shoppingListBDD.open();
                                                        shoppingListBDD.insertTop(new ShoppingList(Shopping_list_name, id, id_sheve, name, bitmapdata, category, new_price, "date", "false"));
                                                        shoppingListBDD.close();
                                                        new SnackBar.Builder(DetailsPromoActivity.this)
                                                                .withOnClickListener(DetailsPromoActivity.this)
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
                                    MDTintHelper.setTint(checkbox,
                                            widgetColor == 0 ? Color.parseColor("#3498db") : widgetColor);

                                    MDTintHelper.setTint(Input1,
                                            widgetColor == 0 ? Color.parseColor("#3498db") : widgetColor);

                                    dialog2.show();
                                    positiveAction.setEnabled(false);

                                }
                            })
                            .onNegative(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    dialog1.dismiss();
                                }
                            })
                            .positiveText("Create")
                            .negativeText("Cancel");


                    dialog1 = builder.build();
                    dialog1.show();


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
                            budgetBDD = new BudgetBDD(DetailsPromoActivity.this);
                            budgetBDD.open();
                            productBudgetList = budgetBDD.selectAll();
                            budgetBDD.close();
                            Budget_value = productBudgetList.get(0).getBudget();
                            Log.e(" before Rounded", Float.valueOf(productBudgetList.get(0).getSpent()) + "");
                            Float value = Float.valueOf(productBudgetList.get(0).getSpent()) + Float.valueOf(new_price);
                            Log.e("Rounded", String.format("%.3f", value) + "");//new DecimalFormat("##.##").format(value)
                            Log.e("Spent value", value + "");
                            Log.e("budget", productBudgetList.get(0).getBudget() + "nothing");
                            if (value > Float.valueOf(productBudgetList.get(0).getBudget())) {
//ERREUR BUDGET LIMIT ADD more BUDGET OR CANCEL
                                dialog4 = new MaterialDialog.Builder(DetailsPromoActivity.this)
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
                                                show_budget2();


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
                                product_price.setText(String.format("%.3f", Float.valueOf(new_price)) + " DT");
                           /* positiveAction = dialog4.getActionButton(DialogAction.POSITIVE);
                            //noinspection ConstantConditions
                            Input2 = (EditText) dialog4.getCustomView().findViewById(R.id.password2);
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
*/
                                dialog4.show();


                            } else {

                                shoppingListBDD = new ShoppingListBDD(DetailsPromoActivity.this);
                                shoppingListBDD.open();
                                shoppingListBDD.insertTop(new ShoppingList(productsList.get(0).getShopping_list_name(), id, id_sheve, name, bitmapdata, category, new_price, "date", "false"));
                                shoppingListBDD.close();
                                productBudgetList = new ArrayList<Budget>();
                                BudgetBDD budgetBDD = new BudgetBDD(DetailsPromoActivity.this);
                                budgetBDD.open();
                                productBudgetList = budgetBDD.selectAll();

                                Float new_value = Float.valueOf(new_price) + Float.valueOf(productBudgetList.get(0).getSpent());
                                budgetBDD.removeAllArticles();
                                budgetBDD.insertTop(new Budget(String.format("%.3f", Float.valueOf(Budget_value)), String.format("%.3f", new_value)));
                                budgetBDD.close();
                                new SnackBar.Builder(DetailsPromoActivity.this)
                                        .withOnClickListener(DetailsPromoActivity.this)
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
                        } else if(sharedpreferences.getString("budget", "false").equals("false")) {
                            shoppingListBDD = new ShoppingListBDD(DetailsPromoActivity.this);
                            shoppingListBDD.open();
                            shoppingListBDD.insertTop(new ShoppingList(productsList.get(0).getShopping_list_name(), id, id_sheve, name, bitmapdata, category, new_price, "date", "false"));
                            shoppingListBDD.close();
                            productBudgetList = new ArrayList<Budget>();
                            BudgetBDD budgetBDD = new BudgetBDD(DetailsPromoActivity.this);
                            budgetBDD.open();
                            productBudgetList = budgetBDD.selectAll();

                            if(!productBudgetList.isEmpty()){
                                Float new_value = Float.valueOf(new_price) + Float.valueOf(productBudgetList.get(0).getSpent());
                                budgetBDD.removeAllArticles();
                                budgetBDD.insertTop(new Budget(String.format("%.3f", Float.valueOf(productBudgetList.get(0).getBudget())), String.format("%.3f", new_value)));
                                budgetBDD.close();
                            }

                            new SnackBar.Builder(DetailsPromoActivity.this)
                                    .withOnClickListener(DetailsPromoActivity.this)
                                    .withMessage(name + " added to " + Shopping_list_name) //   Spent / Budget :"+



                                    .withTypeFace(Typeface.DEFAULT_BOLD)


                                    .withActionMessage("Close")
                                    .withStyle(SnackBar.Style.INFO)
                                    .withTextColorId(R.color.my_color_2)
                                    .withBackgroundColorId(R.color.my_color)
                                    .withDuration(SnackBar.SHORT_SNACK)
                                    .show();
                        }


                    }   else{

                        new MaterialDialog.Builder(DetailsPromoActivity.this)
                                .title("Can't be added")
                                .titleColor(Color.parseColor("#000000"))
                                .contentColor(Color.parseColor("#80000000"))
                                .iconRes(R.drawable.smart_shopping_logo)
                                .maxIconSize(100)
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_splash_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case  android.R.id.home:
                finish();

           break;
        }
        return true;
    }


    public void show_budget(){

        dialog3 =  new MaterialDialog.Builder(DetailsPromoActivity.this)
                .title("Budget")
                .customView(R.layout.dialog_customview2, true)
                .positiveText("Done")
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

                                    if (Float.valueOf(new_price) > Float.valueOf(Budget_value)) {
                                        dialog4 = new MaterialDialog.Builder(DetailsPromoActivity.this)
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
                                                        show_budget();


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

                                        spent_budget.setText(String.format("%.3f", Float.valueOf(new_price)) + " DT / " + String.format("%.3f", Float.valueOf(Budget_value)) + " DT");
                                        product_price.setText(String.format("%.3f", Float.valueOf(new_price)) + " DT");
                           /* positiveAction = dialog4.getActionButton(DialogAction.POSITIVE);
                            //noinspection ConstantConditions
                            Input2 = (EditText) dialog4.getCustomView().findViewById(R.id.password2);
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
*/
                                        dialog4.show();
                                        // positiveAction.setEnabled(false);


                                    } else {
                                        shoppingListBDD = new ShoppingListBDD(DetailsPromoActivity.this);
                                        shoppingListBDD.open();
                                        shoppingListBDD.insertTop(new ShoppingList(Shopping_list_name, id, id_sheve, name, bitmapdata, category, new_price, "date", "false"));
                                        shoppingListBDD.close();

                                        BudgetBDD budgetBDD = new BudgetBDD(DetailsPromoActivity.this);
                                        budgetBDD.open();
                                        budgetBDD.removeAllArticles();
                                        budgetBDD.insertTop(new Budget(String.format("%.3f", Float.valueOf(Budget_value)), String.format("%.3f", Float.valueOf(new_price))));
                                        budgetBDD.close();
                                        new SnackBar.Builder(DetailsPromoActivity.this)
                                                .withOnClickListener(DetailsPromoActivity.this)
                                                .withMessage(name + " added to " + Shopping_list_name + "\nSpent :    " + String.format("%.3f", Float.valueOf(new_price)) + " DT" +
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

    public void show_budget2(){

        dialog3 =  new MaterialDialog.Builder(DetailsPromoActivity.this)
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
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Float value = Float.valueOf(productBudgetList.get(0).getSpent())+Float.valueOf(new_price);
                        if (value > Float.valueOf(Budget_value)) {

                            dialog4 = new MaterialDialog.Builder(DetailsPromoActivity.this)
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
                                            show_budget2();


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

                            spent_budget.setText(String.format("%.3f",value)+" DT / "+String.format("%.3f",Float.valueOf(Budget_value))+" DT");
                            product_price.setText(String.format("%.3f", Float.valueOf(new_price)) + " DT");
                           /* positiveAction = dialog4.getActionButton(DialogAction.POSITIVE);
                            //noinspection ConstantConditions
                            Input2 = (EditText) dialog4.getCustomView().findViewById(R.id.password2);
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
*/
                            dialog4.show();




                        } else {
                            shoppingListBDD = new ShoppingListBDD(DetailsPromoActivity.this);
                            shoppingListBDD.open();
                            shoppingListBDD.insertTop(new ShoppingList(Shopping_list_name, id, id_sheve, name, bitmapdata, category, new_price, "date", "false"));
                            shoppingListBDD.close();
                            productBudgetList = new ArrayList<Budget>();
                            BudgetBDD budgetBDD = new BudgetBDD(DetailsPromoActivity.this);
                            budgetBDD.open();
                            productBudgetList = budgetBDD.selectAll();
                            Float new_value = Float.valueOf(new_price) +Float.valueOf(productBudgetList.get(0).getSpent());
                            budgetBDD.removeAllArticles();
                            budgetBDD.insertTop(new Budget(String.format("%.3f", Float.valueOf(Budget_value)), String.format("%.3f", new_value)));
                            budgetBDD.close();
                            new SnackBar.Builder(DetailsPromoActivity.this)
                                    .withOnClickListener(DetailsPromoActivity.this)
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
    @Override
    public void onMessageClick(Parcelable token) {

    }
}