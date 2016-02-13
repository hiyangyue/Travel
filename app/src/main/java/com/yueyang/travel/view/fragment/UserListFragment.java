package com.yueyang.travel.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yueyang.travel.R;
import com.yueyang.travel.domin.Utils.GlideUtils;
import com.yueyang.travel.domin.Utils.SnackbarUtils;
import com.yueyang.travel.domin.manager.UserManager;
import com.yueyang.travel.model.Constants;
import com.yueyang.travel.model.bean.User;
import com.yueyang.travel.view.activity.UserProfileActivity;
import com.yueyang.travel.view.wiget.CircleImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Yang on 2016/1/15.
 */
public class UserListFragment extends Fragment {

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    private List<User> users;
    private UserListAdapter adapter;

    private boolean isFollow;
    private String userId;

    public static UserListFragment getInstance(boolean isFollow, String userId) {
        UserListFragment fragment = new UserListFragment();
        Bundle args = new Bundle();
        args.putBoolean(Constants.IS_FOLLOW, isFollow);
        args.putString(Constants.USER_ID, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.base_layout, container, false);
        ButterKnife.bind(this, view);
        init();
        initRecyclerView();
        if (isFollow) {
            getFriendList(userId);
        } else {
            getFollowList(userId);
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void init() {
        userId = getArguments().getString(Constants.USER_ID);
        isFollow = getArguments().getBoolean(Constants.IS_FOLLOW);
    }

    private void initRecyclerView() {

        users = new ArrayList<>();
        adapter = new UserListAdapter(users);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    private void getFriendList(String userId) {
        UserManager.getInstance(getActivity()).fetchFriendList(userId, new UserManager.FetchUserListCallback() {
            @Override
            public void onSuccess(List<User> userList) {
                users.addAll(userList);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String errorMessage) {
                SnackbarUtils.getSnackbar(recyclerView, getResources().getString(R.string.login_error));
            }
        });
    }

    private void getFollowList(String userId) {
        UserManager.getInstance(getActivity()).fetchFollowers(userId, new UserManager.FetchUserListCallback() {
            @Override
            public void onSuccess(List<User> userList) {
                users.addAll(userList);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String errorMessage) {
                SnackbarUtils.getSnackbar(recyclerView, getResources().getString(R.string.login_error));
            }
        });
    }

    public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {

        private List<User> userList;

        public UserListAdapter(List<User> userList) {
            this.userList = userList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.holder_user_list, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final User user = userList.get(position);
            holder.userNickname.setText(user.nickname);
            if (user.userPhotoUrl != null) {
                GlideUtils.loadImg(getContext(), user.userPhotoUrl, holder.userListAvatar,40,40);
            }
            holder.userListRl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), UserProfileActivity.class);
                    Bundle args = new Bundle();
                    args.putBoolean(Constants.IS_CURRENT,false);
                    args.putString(Constants.USER_ID, user.userId);
                    args.putString(Constants.USER_AVATAR_URL,user.userPhotoUrl);
                    args.putString(Constants.USER_NICKNAME,user.nickname);
                    intent.putExtras(args);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return userList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            @Bind(R.id.user_list_avatar)
            CircleImageView userListAvatar;
            @Bind(R.id.user_list_nickname)
            TextView userNickname;
            @Bind(R.id.user_list_rl)
            RelativeLayout userListRl;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }

    }
}
