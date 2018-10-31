package tn.dwc.smartshopping.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;

import java.util.ArrayList;
import java.util.List;

import tn.dwc.smartshopping.Adapters.BabiesRecyclerViewAdapter;
import tn.dwc.smartshopping.Entities.Products;
import tn.dwc.smartshopping.Entities.Promotions;
import tn.dwc.smartshopping.SQLite.ProductsBDD;
import tn.dwc.smartshopping.SQLite.PromotionsBDD;
import tn.dwc.smartshopping.R;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class BabiesFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
List<Promotions> promotionsList;
    PromotionsBDD promotionsBDD;
    List<Products> productsList;
    List<Products>productsList2;
    ProductsBDD productBDD;
    private static final int ITEM_COUNT = 100;

    private List<Products> mContentItems = new ArrayList<>();

    public static BabiesFragment newInstance() {
        return new BabiesFragment();
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

        mAdapter = new RecyclerViewMaterialAdapter(new BabiesRecyclerViewAdapter(mContentItems,getActivity()));
        mRecyclerView.setAdapter(mAdapter);
        productsList = new ArrayList<Products>();
        productsList2 = new ArrayList<Products>();
        productBDD = new ProductsBDD(getActivity());
        productBDD.open();
        productsList = productBDD.selectAll();
productBDD.close();
        int j =0;

        for(Products p : productsList){

            if(p.getProduct_category().toString().equals("BabiesKids")){

                productsList2.add(p);
            }
        }
            for (int i=0; i < productsList2.size(); i++)
            {

                mContentItems.add(i, new Products(productsList2.get(i).getProduct_id(),
                        productsList2.get(i).getProduct_sheve_id(), productsList2.get(i).getProduct_name(), productsList2.get(i).getProduct_desc(),
                        productsList2.get(i).getProduct_image(), productsList2.get(i).getProduct_category(), productsList2.get(i).getProduct_sub_category()
                        , productsList2.get(i).getProduct_price(), productsList2.get(i).getProduct_created_at()));

                    mAdapter.notifyDataSetChanged();


            }

        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);
    }
}