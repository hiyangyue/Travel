package com.yueyang.travel.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yueyang.travel.R;
import com.yueyang.travel.domin.manager.UserManager;
import com.yueyang.travel.model.bean.User;
import com.yueyang.travel.view.adapter.RecommendUserAdapter;
import com.yueyang.travel.view.wiget.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Yang on 2016/1/23.
 */
public class ExploreFragment extends Fragment {


    @Bind(R.id.recycler_user)
    RecyclerView recyclerUser;

    private RecommendUserAdapter adapter;
    private List<User> userList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_explore, container, false);
        ButterKnife.bind(this, view);
        setUpRecyclerView();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void setUpRecyclerView(){
        userList = new ArrayList<>();
        User user = UserManager.getInstance(getContext()).getCurrentUser();
        for (int i = 0 ; i < 5 ;i ++){
            userList.add(user);
        }
        adapter = new RecommendUserAdapter(getContext(),userList);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerUser.setLayoutManager(layoutManager);
        recyclerUser.addItemDecoration(new GridSpacingItemDecoration(1,20,true));
        recyclerUser.setHasFixedSize(true);
        recyclerUser.setAdapter(adapter);

    }
}












