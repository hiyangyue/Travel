package com.yueyang.travel.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yueyang.travel.R;
import com.yueyang.travel.domin.Utils.GlideUtils;
import com.yueyang.travel.model.bean.CityDetail;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Yang on 2016/1/24.
 */
public class CityDetailAdapter extends RecyclerView.Adapter<CityDetailAdapter.ViewHolder> {

    private Context mContext;
    private List<CityDetail> cityDetailList;

    public CityDetailAdapter(Context mContext, List<CityDetail> cityDetailList) {
        this.mContext = mContext;
        this.cityDetailList = cityDetailList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.holder_city_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CityDetail cityDetail = cityDetailList.get(position);
        holder.cityTitle.setText(cityDetail.getTitle());
        holder.cityDetail.setText(cityDetail.getDescription());
        if (cityDetail.getPhotoUrl() != null){
            GlideUtils.loadImg(mContext,cityDetail.getPhotoUrl(),holder.cityImg);
        }
    }

    @Override
    public int getItemCount() {
        return cityDetailList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.city_title)
        TextView cityTitle;
        @Bind(R.id.city_detail)
        TextView cityDetail;
        @Bind(R.id.city_img)
        ImageView cityImg;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
