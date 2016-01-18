package com.yueyang.travel.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yueyang.travel.R;
import com.yueyang.travel.model.bean.Desitination;
import com.yueyang.travel.presenter.DesitinationPresenter;
import com.yueyang.travel.view.IDestinationView;
import com.yueyang.travel.view.adapter.DesitinationAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Yang on 2015/12/12.
 */
public class DesitinationFragment extends Fragment implements IDestinationView{

    @Bind(R.id.recycler_desitination)
    RecyclerView recyclerDesitination;
    private DesitinationAdapter adapter;
    private List<Object> mItems;
    private DesitinationPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.fragment_desitination, container, false);

        ButterKnife.bind(this, view);
        initRecyclerView();
        initPresenter();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void initRecyclerView(){

        final GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerDesitination.setLayoutManager(mLayoutManager);
        recyclerDesitination.setHasFixedSize(true);
//        recyclerDesitination.addItemDecoration(new GridSpacingItemDecoration(2, 20, true));
        recyclerDesitination.setItemAnimator(new DefaultItemAnimator());

        mItems = new ArrayList<>();
        adapter = new DesitinationAdapter(getActivity(),mItems);
        recyclerDesitination.setAdapter(adapter);
    }

    private void initPresenter(){
        presenter = new DesitinationPresenter(this);
        presenter.loadDesitinations();
    }

    @Override
    public void loadDesitinations(List<Desitination> desitinations) {
        mItems.addAll(desitinations);
        adapter.notifyDataSetChanged();
    }
}
