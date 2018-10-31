package tn.dwc.smartshopping.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;

import java.util.ArrayList;
import java.util.List;

import tn.dwc.smartshopping.Adapters.HistoryRecyclerViewAdapter;
import tn.dwc.smartshopping.Entities.History;
import tn.dwc.smartshopping.Entities.ShoppingList;
import tn.dwc.smartshopping.R;
import tn.dwc.smartshopping.SQLite.HistoryBDD;

public class HistoryFragment extends Fragment {
    View v;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    List<History> historyList;
    HistoryBDD historyBDD;
    private List<History> mContentItems;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_recycleview_history, container, false);





    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContentItems = new ArrayList<History>();

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerVieww);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);


        historyBDD = new HistoryBDD(getActivity());
        historyBDD.open();
        historyList= historyBDD.selectAll();
        historyBDD.close();
        if(historyList.isEmpty()){

            TextView no_shopping_display= (TextView)view.findViewById(R.id.no_shopping_displays);
            ImageView imageViewpic=(ImageView)view.findViewById(R.id.imageViewpics);
            RelativeLayout relativeLayout= (RelativeLayout)view.findViewById(R.id.relative_recycle);
            relativeLayout.setVisibility(View.INVISIBLE);
            imageViewpic.setVisibility(View.VISIBLE);

            no_shopping_display.setVisibility(View.VISIBLE);
        }

        else {


            mAdapter = new RecyclerViewMaterialAdapter(new HistoryRecyclerViewAdapter(mContentItems, getActivity()));
            mRecyclerView.setAdapter(mAdapter);


            int j = 0;



            for (int i = 0; i < historyList.size(); i++) {

                mContentItems.add(i, new History(historyList.get(i).getHistory_Name(),
                        historyList.get(i).getBudget(),
                        historyList.get(i).getProducts_Number(),
                        historyList.get(i).getCreation_Date()
                                ));

                mAdapter.notifyDataSetChanged();


            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        super.onCreateOptionsMenu(menu, inflater);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case  android.R.id.home:


                break;
        }
        return true;
    }

}



