package tn.dwc.smartshopping.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;


import java.util.ArrayList;
import java.util.List;

import tn.dwc.smartshopping.Entities.Maps_Product;
import tn.dwc.smartshopping.Entities.Products;
import tn.dwc.smartshopping.Entities.Promotions;
import tn.dwc.smartshopping.SQLite.Maps_ProductBDD;
import tn.dwc.smartshopping.SQLite.ProductsBDD;
import tn.dwc.smartshopping.SQLite.PromotionsBDD;
import tn.dwc.smartshopping.Adapters.PromotionsRecyclerViewAdapter;
import tn.dwc.smartshopping.R;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class    PromotionsFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
List<Promotions> promotionsList;
    PromotionsBDD promotionsBDD;
    List<Products> productsList;
    ProductsBDD productBDD;
    private static final int ITEM_COUNT = 100;

    private List<Promotions> mContentItems = new ArrayList<>();

    public static PromotionsFragment newInstance() {
        return new PromotionsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recycleview, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new RecyclerViewMaterialAdapter(new PromotionsRecyclerViewAdapter(mContentItems,getActivity()));
        mRecyclerView.setAdapter(mAdapter);
        promotionsList = new ArrayList<Promotions>();
        promotionsBDD = new PromotionsBDD(getActivity());
        promotionsBDD.open();
        promotionsList = promotionsBDD.selectAll();

        promotionsBDD.close();
        productsList = new ArrayList<Products>();
        productBDD = new ProductsBDD(getActivity());
        productBDD.open();
        productsList = productBDD.selectAll();

        promotionsBDD.close();
        List<Maps_Product> maps_products= new ArrayList<>();
        Maps_ProductBDD maps_product = new Maps_ProductBDD(getActivity());
        maps_product.open();
        maps_products = maps_product.selectAll();
        maps_product.close();
        Log.e("mapsproductssize", "" + maps_products.size());
        Log.e("promotionssize", "" + promotionsList.size());
        Log.e("producttsize",""+ productsList.size());
        for (int i = 0; i < promotionsList.size(); ++i)
        {

            mContentItems.add(i, new Promotions(promotionsList.get(i).getPromotion_id(), promotionsList.get(i).getPromotion_sheve_id(),
                    promotionsList.get(i).getPromotion_name(), promotionsList.get(i).getPromotion_Product_desc(), promotionsList.get(i).getPromotion_image(), promotionsList.get(i).getPromotion_category()
                    , promotionsList.get(i).getPromotion_sub_category(), promotionsList.get(i).getPromotion_old_price(), promotionsList.get(i).getPromotion_new_price()
                    , promotionsList.get(i).getPromotion_pourcentage()));
            mAdapter.notifyDataSetChanged();
        }
      /*  List<User> li = new ArrayList<User>();
        UserBDD u = new UserBDD(getActivity());
        u.open();
        li=u.selectAll();
        u.close();

        for (int i = 0; i < 20; ++i)
        {

            mContentItems.add(i, new Promotions("promo"+i, "promo"+i,
                    "promo"+i,li.get(0).getUser_Picture() , "xy"
                    , "z", "100","50"
                    ,"50"));
            mAdapter.notifyDataSetChanged();
        }
*/
        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);

    }
}