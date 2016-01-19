package com.yueyang.travel.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yueyang.travel.R;
import com.yueyang.travel.Utils.GlideUtils;
import com.yueyang.travel.model.bean.HotPlace;
import com.yueyang.travel.view.wiget.CircleImageView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Yang on 2016/1/19.
 */
public class HotPlaceAdapter extends RecyclerView.Adapter<HotPlaceAdapter.ViewHolder> {

    private Context mContext;
    private List<HotPlace> hotPlaceList;

    public HotPlaceAdapter(Context mContext, List<HotPlace> hotPlaceList) {
        this.mContext = mContext;
        this.hotPlaceList = hotPlaceList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.holder_hot, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HotPlace hotPlace = hotPlaceList.get(position);
        holder.holderHotTitle.setText(hotPlace.getTitle());
        GlideUtils.loadImg(mContext, hotPlace.getPlaceBgUrl(), holder.holderHotBg);
        GlideUtils.loadImg(mContext, hotPlace.getAuthorPhotoUrl(), holder.holderHotAvatar);
        holder.holderHotRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start to WebActivity
            }
        });
    }

    @Override
    public int getItemCount() {
        return hotPlaceList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.holder_hot_bg)
        ImageView holderHotBg;
        @Bind(R.id.holder_hot_avatar)
        CircleImageView holderHotAvatar;
        @Bind(R.id.holder_hot_title)
        TextView holderHotTitle;
        @Bind(R.id.holder_hot_rl)
        RelativeLayout holderHotRl;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
