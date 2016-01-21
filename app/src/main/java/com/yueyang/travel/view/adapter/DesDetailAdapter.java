package com.yueyang.travel.view.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yueyang.travel.R;
import com.yueyang.travel.Utils.GlideUtils;
import com.yueyang.travel.model.bean.City;
import com.yueyang.travel.view.fragment.CityDetailFragment;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Yang on 2015/12/15.
 */
public class DesDetailAdapter extends RecyclerView.Adapter<DesDetailAdapter.ViewHolder> {

    private Context mContext;
    private List<City> cityList;

    public DesDetailAdapter(Context mContext, List<City> cityList) {
        this.mContext = mContext;
        this.cityList = cityList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.holder_city, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        City city = cityList.get(position);
        holder.cityCnName.setText(city.getCityName());
        holder.cityEnName.setText(city.getEnCityName());
//        holder.cityBeenStr.setText(city.getCityBeenStr());
        GlideUtils.loadImg(mContext,city.getCityImgUrl(),holder.cityImg);
//        MaterialUtils.setPattle(mContext, holder.cityImg, city.getCityImgUrl(), holder.cityRl);
    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.city_img)
        ImageView cityImg;
        @Bind(R.id.city_cn_name)
        TextView cityCnName;
        @Bind(R.id.city_en_name)
        TextView cityEnName;
//        @Bind(R.id.city_been_str)
//        TextView cityBeenStr;
        @Bind(R.id.cityRl)
        RelativeLayout cityRl;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            cityImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    City city = cityList.get(getLayoutPosition());
                    CityDetailFragment cityDetailFragment = CityDetailFragment
                            .getInstance(city.getCityId(),city.getCityImgUrl(),city.getCityName(),city.getEnCityName());
                    FragmentTransaction ft =
                            ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction();
                    ft.addToBackStack(null);
                    ft.replace(R.id.fragment_container,cityDetailFragment).commit();

                }
            });
        }
    }
}
