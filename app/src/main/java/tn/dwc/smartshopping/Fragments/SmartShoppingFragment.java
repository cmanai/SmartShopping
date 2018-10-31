package tn.dwc.smartshopping.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;

import java.util.List;

import tn.dwc.smartshopping.Activities.BarActivity;
import tn.dwc.smartshopping.Activities.FindActivity;
import tn.dwc.smartshopping.Activities.QRActivity;
import tn.dwc.smartshopping.Activities.QrFindActivity;
import tn.dwc.smartshopping.Entities.Products;
import tn.dwc.smartshopping.R;
import tn.dwc.smartshopping.SQLite.ProductsBDD;

/**
 * Created by Chi on 11/12/2015.
 */
public class    SmartShoppingFragment  extends Fragment {
    View v;
    ProductsBDD productsBDD;
    List<Products> productsList;
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    private MaterialViewPager mViewPager;
    private FloatingActionButton fab1;
    private FloatingActionButton fab2;
    FloatingActionMenu menu1;
Bundle out;
    PromotionsFragment f1;
    NewThisWeekFragment f2;
    DrinksFragment f3;
    AnimalsFragment f4;
    BabiesFragment f5;
    MarketFragment f6;
    GroceryFragment f7;
    DeliFragment f8;
    CreameryFragment f9;
    AppliancesFragment f10;
    private String mParentString;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        menu1.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {
            @Override
            public void onMenuToggle(boolean opened) {
                String text = "";
                if (opened) {
                    text = "Menu opened";
                } else {
                    text = "Menu closed";
                }
                //Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
            }
        });

        fab1.setOnClickListener(clickListener);
        fab2.setOnClickListener(clickListener);
            setHasOptionsMenu(true);


        // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);


        mViewPager.getViewPager().setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                switch (position % 10) {
                    case 0:
                        return f1;
                    case 1:
                        return f2;
                    case 2:
                        return f3;
                    case 3:
                        return f4;
                    case 4:
                        return f5;
                    case 5:
                        return f6;
                    case 6:
                        return f7;
                    case 7:
                        return f8;
                    case 8:
                        return f9;
                    case 9:
                        return f10;
                    default:
                        return f10;
                }
            }

            @Override
            public int getCount() {
                return 10;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position % 10) {
                    case 0:
                        return "Promo";

                    case 1:
                        return "New This week";
                    case 2:
                        return "Drinks";
                    case 3:
                        return "Animals";
                    case 4:
                        return "Babies - Kids";
                    case 5:
                        return "Market";
                    case 6:
                        return "Grocery";
                    case 7:
                        return "Deli";
                    case 8:
                        return "Creamery";
                    case 9:
                        return "Appliances";
                }
                return "";
            }
        });

        mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {
                switch (page) {
                    case 0:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.blue, null);
                    case 1:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.blue, null);
                    case 2:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.blue, null);
                    case 3:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.blue, null);
                    case 4:
                        return HeaderDesign.fromColorResAndDrawable(R.color.blue, null);
                    case 5:
                        return HeaderDesign.fromColorResAndDrawable(R.color.blue, null);
                    case 6:
                        return HeaderDesign.fromColorResAndDrawable(R.color.blue, null);
                    case 7:
                        return HeaderDesign.fromColorResAndDrawable(R.color.blue, null);
                    case 8:
                        return HeaderDesign.fromColorResAndDrawable(R.color.blue, null);
                }

                //execute others actions if needed (ex : modify your header logo)

                return null;
            }
        });
        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());

/*
        View logo = v.findViewById(R.id.logo_white);
        if (logo != null)
            logo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.notifyHeaderChanged();
                    Toast.makeText(getActivity(), "Yes, the title is clickable", Toast.LENGTH_SHORT).show();
                }
            });
            */

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onResume() {


        super.onResume();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        f1 = PromotionsFragment.newInstance();
        f2 = NewThisWeekFragment.newInstance();
        f3 = DrinksFragment.newInstance();
        f4 = AnimalsFragment.newInstance();
        f5 = BabiesFragment.newInstance();
        f6 = MarketFragment.newInstance();
        f7 = GroceryFragment.newInstance();
        f8 = DeliFragment.newInstance();
        f9 = CreameryFragment.newInstance();
        f10 = AppliancesFragment.newInstance();



        out = savedInstanceState;



    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.smartshopping_layout, container, false);
        mViewPager = (MaterialViewPager)v.findViewById(R.id.materialViewPager);



        fab1 = (FloatingActionButton) v.findViewById(R.id.fab22);
        fab2 = (FloatingActionButton) v.findViewById(R.id.fab32);
        menu1 = (FloatingActionMenu) v.findViewById(R.id.menu2);
        return  v;
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String text = "";

            switch (v.getId()) {
                case R.id.fab22:
                    text = fab1.getLabelText();
                    menu1.close(true);

                    Intent intent1 = new Intent(getActivity(), BarActivity.class);

                    startActivity(intent1);

                    break;
                case R.id.fab32:
                    text = fab2.getLabelText();
                    menu1.close(true);
                    Intent intent = new Intent(getActivity(), QRActivity.class);

                    startActivity(intent);

                   /* try {
                        Intent intent = new Intent(ACTION_SCAN);
                        intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
                        startActivityForResult(intent, 1);
                    } catch (ActivityNotFoundException anfe) {
                        showDialog(getActivity(), "No Scanner Found",
                                "Download a scanner code activity?", "Yes", "No")
                                .show();
                    }*/
                    break;

            }

            //Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
        }
    };



    private static AlertDialog showDialog(final Activity act,
                                          CharSequence title, CharSequence message, CharSequence buttonYes,
                                          CharSequence buttonNo) {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(message);
        downloadDialog.setPositiveButton(buttonYes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Uri uri = Uri.parse("market://search?q=pname:"
                                + "com.google.zxing.client.android");
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        try {
                            act.startActivity(intent);
                        } catch (ActivityNotFoundException anfe) {

                        }
                    }
                });
        downloadDialog.setNegativeButton(buttonNo,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
        return downloadDialog.show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                startActivity(new Intent(getActivity(), FindActivity.class));
                return false;

            default:
                break;
        }

        return false;
    }

}
