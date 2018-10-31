package tn.dwc.smartshopping.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.melnykov.fab.FloatingActionButton;
import com.quinny898.library.persistentsearch.SearchBox;
import com.quinny898.library.persistentsearch.SearchBox.MenuListener;
import com.quinny898.library.persistentsearch.SearchBox.SearchListener;
import com.quinny898.library.persistentsearch.SearchResult;

import java.util.ArrayList;
import java.util.List;


import tn.dwc.smartshopping.Adapters.FindRecyclerViewAdapter;
import tn.dwc.smartshopping.Entities.Budget;
import tn.dwc.smartshopping.Entities.Products;
import tn.dwc.smartshopping.Entities.ShoppingList;
import tn.dwc.smartshopping.R;
import tn.dwc.smartshopping.SQLite.BudgetBDD;
import tn.dwc.smartshopping.SQLite.ProductsBDD;
import tn.dwc.smartshopping.SQLite.ShoppingListBDD;

public class QrFindActivity extends AppCompatActivity {
    String name,price,description,id,id_sheve,category,discount,old_price;
    Drawable picture;
    ShoppingListBDD shoppingListBDD;
    List<ShoppingList> productsList;
    List<Products> productsList3;
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
    String qr_id;
    static final int TYPE_HEADER = 0;
    static final int TYPE_CELL = 1;
    String hb;
    RecyclerView mRecyclerView;

    List<Products> productsList2;
    ProductsBDD productsBDD;
    List<Products> mContentItems;

    private SearchBox search;
    private Toolbar toolbar;
    private RecyclerView.Adapter mAdapter;
    ImageView icon;
    TextView textView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchview);
        search = (SearchBox) findViewById(R.id.searchbox);
        search.enableVoiceRecognition(this);
search.setLogoText("Search product by name");
        search.setHint("Search product by name");
        search.setSearchString("");
        search.setSelected(true);
        search.setHovered(true);
        toolbar = (Toolbar) findViewById(R.id.toolbar4);
icon =(ImageView)findViewById(R.id.imageViewpics2);
        textView=(TextView)findViewById(R.id.no_shopping_displays2);
        Window window = getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(Color.parseColor("#303F9F"));
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
        toolbar.setBackgroundColor(Color.parseColor("#303F9F"));

        openSearch();
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                openSearch();
                return true;
            }
        });


        mContentItems = new ArrayList<Products>();

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewwww);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(QrFindActivity.this);
        mRecyclerView.setLayoutManager(layoutManager);
       // mRecyclerView.setHasFixedSize(true);

        mAdapter = new RecyclerViewMaterialAdapter(new FindRecyclerViewAdapter(mContentItems,QrFindActivity.this,hb));
        mRecyclerView.setAdapter(mAdapter);

        productsList2 = new ArrayList<Products>();
        productsBDD = new ProductsBDD(QrFindActivity.this);
        productsBDD.open();
        productsList2 = productsBDD.selectAll();
        productsBDD.close();


        for (int i=0; i < productsList2.size(); i++)
        {

            mContentItems.add(i, new Products(productsList2.get(i).getProduct_id(),
                    productsList2.get(i).getProduct_sheve_id(), productsList2.get(i).getProduct_name(), productsList2.get(i).getProduct_desc(),
                    productsList2.get(i).getProduct_image(), productsList2.get(i).getProduct_category(), productsList2.get(i).getProduct_sub_category()
                    , productsList2.get(i).getProduct_price(), productsList2.get(i).getProduct_created_at()));

            mAdapter.notifyDataSetChanged();


        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    public void openSearch() {
        toolbar.setTitle(" ");
        search.revealFromMenuItem(R.id.action_search, this);
        /*for (int x = 0; x < 10; x++) {
            SearchResult option = new SearchResult("Result "
                    + Integer.toString(x), getResources().getDrawable(
                    R.drawable.ic_history));
            search.addSearchable(option);
        }*/
        search.setMenuListener(new MenuListener() {

            @Override
            public void onMenuClick() {
                // Hamburger has been clicked

            }

        });
        search.setSearchListener(new SearchListener() {

            @Override
            public void onSearchOpened() {
                // Use this to tint the screen

            }

            @Override
            public void onSearchClosed() {
                // Use this to un-tint the screen
                closeSearch();
            }

            @Override
            public void onSearchTermChanged(String term) {
                // React to the search term changing
                // Called after it has updated results

                mRecyclerView.invalidate();
                mAdapter.notifyItemRangeRemoved(0, mContentItems.size());
                mContentItems.clear();
                mRecyclerView.removeAllViews();
                mContentItems = new ArrayList<Products>();
                productsList3 = new ArrayList<Products>();
                mAdapter = new RecyclerViewMaterialAdapter(new FindRecyclerViewAdapter(mContentItems, QrFindActivity.this,hb));
                mRecyclerView.setAdapter(mAdapter);
                for (Products p : productsList2) {
                    if (p.getProduct_name().toString().toUpperCase().contains(term.toUpperCase())) {


                        productsList3.add(p);
                    }
                }
                if (productsList3.isEmpty()) {
                    icon.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.VISIBLE);

                } else {
                    for (int i = 0; i < productsList3.size(); i++) {

                        mContentItems.add(i, new Products(productsList3.get(i).getProduct_id(),
                                productsList3.get(i).getProduct_sheve_id(), productsList3.get(i).getProduct_name(), productsList3.get(i).getProduct_desc(),
                                productsList3.get(i).getProduct_image(), productsList3.get(i).getProduct_category(), productsList3.get(i).getProduct_sub_category()
                                , productsList3.get(i).getProduct_price(), productsList3.get(i).getProduct_created_at()));


                        mAdapter.notifyDataSetChanged();
                        icon.setVisibility(View.INVISIBLE);
                        textView.setVisibility(View.INVISIBLE);


                    }
                }


            }

            @Override
            public void onSearch(String searchTerm) {

                toolbar.setTitle(searchTerm);

                mRecyclerView.invalidate();
                mAdapter.notifyItemRangeRemoved(0, mContentItems.size());
                mContentItems.clear();
                mRecyclerView.removeAllViews();
                mContentItems = new ArrayList<Products>();
                productsList3 = new ArrayList<Products>();
                mAdapter = new RecyclerViewMaterialAdapter(new FindRecyclerViewAdapter(mContentItems, QrFindActivity.this,hb));
                mRecyclerView.setAdapter(mAdapter);
                for (Products p : productsList2) {
                    if (p.getProduct_name().toString().toUpperCase().contains(searchTerm.toUpperCase())) {


                        productsList3.add(p);
                    }
                }
                if (productsList3.isEmpty()) {
                    icon.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.VISIBLE);

                } else {
                    for (int i = 0; i < productsList3.size(); i++) {

                        mContentItems.add(i, new Products(productsList3.get(i).getProduct_id(),
                                productsList3.get(i).getProduct_sheve_id(), productsList3.get(i).getProduct_name(), productsList3.get(i).getProduct_desc(),
                                productsList3.get(i).getProduct_image(), productsList3.get(i).getProduct_category(), productsList3.get(i).getProduct_sub_category()
                                , productsList3.get(i).getProduct_price(), productsList3.get(i).getProduct_created_at()));


                        mAdapter.notifyDataSetChanged();
                        icon.setVisibility(View.INVISIBLE);
                        textView.setVisibility(View.INVISIBLE);


                    }
                }

            }

            @Override
            public void onResultClick(SearchResult result) {
                //React to result being clicked


            }

            @Override
            public void onSearchCleared() {

                mContentItems = new ArrayList<Products>();

                mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewwww);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(QrFindActivity.this);
                mRecyclerView.setLayoutManager(layoutManager);
                // mRecyclerView.setHasFixedSize(true);

                mAdapter = new RecyclerViewMaterialAdapter(new FindRecyclerViewAdapter(mContentItems, QrFindActivity.this,hb));
                mRecyclerView.setAdapter(mAdapter);

                productsList2 = new ArrayList<Products>();
                productsBDD = new ProductsBDD(QrFindActivity.this);
                productsBDD.open();
                productsList2 = productsBDD.selectAll();
                productsBDD.close();


                for (int i = 0; i < productsList2.size(); i++) {

                    mContentItems.add(i, new Products(productsList2.get(i).getProduct_id(),
                            productsList2.get(i).getProduct_sheve_id(), productsList2.get(i).getProduct_name(), productsList2.get(i).getProduct_desc(),
                            productsList2.get(i).getProduct_image(), productsList2.get(i).getProduct_category(), productsList2.get(i).getProduct_sub_category()
                            , productsList2.get(i).getProduct_price(), productsList2.get(i).getProduct_created_at()));

                    mAdapter.notifyDataSetChanged();


                }
            }

        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1234 && resultCode == RESULT_OK) {
            ArrayList<String> matches = data
                    .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            search.populateEditText(matches.get(0));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    protected void closeSearch() {
       // search.hideCircularly(this);
        if(search.getSearchText().isEmpty())toolbar.setTitle("Smart Shopping");
    }


}