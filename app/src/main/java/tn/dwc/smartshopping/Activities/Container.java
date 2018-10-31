package tn.dwc.smartshopping.Activities;

import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
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

import tn.dwc.smartshopping.Entities.Products;
import tn.dwc.smartshopping.Entities.User;
import tn.dwc.smartshopping.Fragments.HistoryFragment;
import tn.dwc.smartshopping.Fragments.MyShoppingListFragment;
import tn.dwc.smartshopping.Fragments.SmartShoppingFragment;
import tn.dwc.smartshopping.R;
import tn.dwc.smartshopping.SQLite.ProductsBDD;
import tn.dwc.smartshopping.SQLite.UserBDD;


public class Container extends AppCompatActivity {
    public static final String ARG_PARENTS = "Parents";
    MaterialDialog dialog3, dialog2;
    private AccountHeader headerResult = null;
    private Toolbar toolbar;
    public ImageView image;
    SharedPreferences sharedpreferences;
    String name, email, pictureurl;
    public static Drawer result;
    public static boolean budget_enabled = false;
    Bitmap profile_pic = null;
    Bitmap bitmap;
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    List<User> userList;
    UserBDD userBDD;

    ProductsBDD productsBDD;
    List<Products> productsLinu1;
    Fragment f1;
    Fragment f2;
    Fragment f3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.overview_layout);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);


        }


        userList = new ArrayList<User>();
        userBDD = new UserBDD(getApplicationContext());
        userBDD.open();

        userList = userBDD.selectAll();

        userBDD.close();
        byte[] bitmapdata = userList.get(0).getUser_Picture();
        // if bitmapdata is the byte array then getting bitmap goes like this

        Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapdata, 0,
                bitmapdata.length);
        profile_pic = bitmap;
        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        toolbar.setTitle("Smart Shopping");
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

        toolbar.setBackgroundColor(Color.parseColor("#303F9F"));

        //toolbar.getBackground().setAlpha(1);
        headerResult = new AccountHeaderBuilder()
                .withActivity(Container.this)
                .withHeaderBackground(R.drawable.back3)
                .addProfiles(
                        new ProfileDrawerItem().withName(userList.get(0).getUser_FullName()).withEmail(userList.get(0).getUser_Email()).withIcon(profile_pic)
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();

        final PrimaryDrawerItem item1 = new PrimaryDrawerItem().withName("Smart Shopping").withIcon(R.drawable.home).withTextColor(Color.parseColor("#7f8c8d")).withSelectedTextColor(Color.parseColor("#303F9F")).withSelectedIcon(R.drawable.home_selected);
        final PrimaryDrawerItem item2 = new PrimaryDrawerItem().withName("My Shopping List").withIcon(R.drawable.shop_list).withTextColor(Color.parseColor("#7f8c8d")).withSelectedTextColor(Color.parseColor("#303F9F")).withSelectedIcon(R.drawable.shop_list_selected);
        final PrimaryDrawerItem item3 = new PrimaryDrawerItem().withName("History").withIcon(R.drawable.history).withTextColor(Color.parseColor("#7f8c8d")).withSelectedTextColor(Color.parseColor("#303F9F")).withSelectedIcon(R.drawable.history_selected);
        final PrimaryDrawerItem item4 = new PrimaryDrawerItem().withName("Settings").withIcon(R.drawable.settings).withTextColor(Color.parseColor("#7f8c8d")).withSelectedTextColor(Color.parseColor("#303F9F")).withSelectedIcon(R.drawable.settings_selected);


//create the drawer and remember the `Drawer` result object
        result = new DrawerBuilder()

                .withActivity(Container.this)
                .withToolbar(toolbar)

                .withAccountHeader(headerResult)
                .withTranslucentStatusBar(true)
                .addDrawerItems(
                        item1,

                        item2, item3,
                        new ProfileSettingDrawerItem()
                )
                .addStickyDrawerItems(item4)
                // .withDelayOnDrawerClose(2000)
                .withHasStableIds(true)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D


                        // result.getDrawerLayout().closeDrawer(Gravity.LEFT);


                        if (item1.isSelected()) {

                            toolbar.setTitle("Smart Shopping");
                            f1 = new SmartShoppingFragment();


                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.fragment_container, f1);

                            fragmentTransaction.commit();
                        }

                        if (item2.isSelected()) {


                            toolbar.setTitle("My Shopping List");
                            f2 = new MyShoppingListFragment();

                            FragmentManager fragmentManager = getSupportFragmentManager();
                            final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.fragment_container, f2, "tag");

                            fragmentTransaction.commit();

                        }
                        if (item3.isSelected()) {
                            toolbar.setTitle("History");
                            f3 = new HistoryFragment();

                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.fragment_container, f3);

                            fragmentTransaction.commit();


                        }

                        if (drawerItem.getIdentifier() == item4.getIdentifier()) {


                        }


                        return false;
                    }

                })
                .withSavedInstance(savedInstanceState)

                .build();

/*
        if (savedInstanceState != null) {
            //Restore the fragment's instance
            f1 = getSupportFragmentManager().getFragment(
                    savedInstanceState, "mContent");
            f2 = getSupportFragmentManager().getFragment(
                    savedInstanceState, "mContent2");

        }
else{

        }

*/

        f1 = new SmartShoppingFragment();
        f2 = new MyShoppingListFragment();
        f3 = new HistoryFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, f1);

        fragmentTransaction.commit();

        //  mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, 0, 0);
        // mDrawer.setDrawerListener(mDrawerToggle);
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
    protected void onSaveInstanceState(Bundle outState) {
        //add the values which need to be saved from the drawer to the bundle
        outState = result.saveInstanceState(outState);
        //add the values which need to be saved from the accountHeader to the bundle
        outState = headerResult.saveInstanceState(outState);

        //  getSupportFragmentManager().putFragment(outState, "mContent", f1);
        // getSupportFragmentManager().putFragment(outState, "mContent2", f2);
        super.onSaveInstanceState(outState);


    }

    @Override
    public void onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else {

            dialog2 = new MaterialDialog.Builder(Container.this)
                    .title("Smart Shopping")
                    .content("Do you want to quit ?")
                    .backgroundColor(Color.parseColor("#ffffff"))
                    .titleColor(Color.parseColor("#000000"))
                    .contentColor(Color.parseColor("#80000000"))
                    .iconRes(R.drawable.smart_shopping_logo)
                    .maxIconSize(100)
                    .positiveText("Quit")
                    .negativeText("Cancel")
                    .positiveColor(Color.parseColor("#3498db"))
                    .negativeColor(Color.parseColor("#000000"))
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            finish();

                        }
                    })

                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        }
                    })
                    .build();


            dialog2.show();

        }
    }


}

