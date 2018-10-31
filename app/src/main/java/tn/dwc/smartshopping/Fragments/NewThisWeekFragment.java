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

import tn.dwc.smartshopping.Entities.Products;
import tn.dwc.smartshopping.Entities.Promotions;
import tn.dwc.smartshopping.Adapters.NewThisWeekRecyclerViewAdapter;
import tn.dwc.smartshopping.SQLite.ProductsBDD;
import tn.dwc.smartshopping.SQLite.PromotionsBDD;
import tn.dwc.smartshopping.R;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class NewThisWeekFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
List<Promotions> promotionsList;
    PromotionsBDD promotionsBDD;
    List<Products> productsList;
    ProductsBDD productBDD;
    private static final int ITEM_COUNT = 100;

    private List<Products> mContentItems = new ArrayList<>();

    public static NewThisWeekFragment newInstance() {
        return new NewThisWeekFragment();
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

        mAdapter = new RecyclerViewMaterialAdapter(new NewThisWeekRecyclerViewAdapter(mContentItems,getActivity()));
        mRecyclerView.setAdapter(mAdapter);
        productsList = new ArrayList<Products>();
        productBDD = new ProductsBDD(getActivity());
        productBDD.open();
        productsList = productBDD.selectAll();
productBDD.close();
        int j =0;

            for (int i = productsList.size()-1; i > productsList.size()-11; i--)
            {

                mContentItems.add(j, new Products(productsList.get(i).getProduct_id(),
                        productsList.get(i).getProduct_sheve_id(),productsList.get(i).getProduct_name(), productsList.get(i).getProduct_desc(),
                        productsList.get(i).getProduct_image(),productsList.get(i).getProduct_category(),productsList.get(i).getProduct_sub_category()
                ,productsList.get(i).getProduct_price(),productsList.get(i).getProduct_created_at()));
            mAdapter.notifyDataSetChanged();
                j++;
            }

      MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);
    }
}