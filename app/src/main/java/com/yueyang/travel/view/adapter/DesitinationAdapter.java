package com.yueyang.travel.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yueyang.travel.R;
import com.yueyang.travel.Utils.MaterialUtils;
import com.yueyang.travel.view.activity.DesDetailActivity;
import com.yueyang.travel.model.bean.Desitination;

import java.util.List;

/**
 * Created by Yang on 2015/12/12.
 */
public class DesitinationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Object> mItems;
    private Context mContext;
    private final int TYPE_HOT = 1;

    public DesitinationAdapter(Context mContext,List<Object> mItems){
        this.mContext = mContext;
        this.mItems = mItems;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(mContext);

        switch (viewType){
            case TYPE_HOT:
                View hotView = inflater.inflate(R.layout.holder_hot_country,null);
                viewHolder = new HotCountryHolder(hotView);
                break;
            default:
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()){
            case TYPE_HOT:
                Desitination desitination = (Desitination) mItems.get(position);
                final HotCountryHolder hotCountryHolder = (HotCountryHolder) holder;
                final ImageView hotImg = hotCountryHolder.hotCountryImg;
                hotCountryHolder.hotCountryCnname.setText(desitination.getCnName());
                hotCountryHolder.hotCountryEnname.setText(desitination.getEnName());
                MaterialUtils.setPattle(mContext,hotImg,desitination.getPhotoUrl(),hotCountryHolder.hotCountryRl);
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_HOT;
    }

    public static boolean isHeader(int position){
        return position == 0;
    }

    public class HotCountryHolder extends RecyclerView.ViewHolder{

        private ImageView hotCountryImg;
        private TextView hotCountryEnname,hotCountryCnname;
        private RelativeLayout hotCountryRl;

        public HotCountryHolder(View itemView) {
            super(itemView);

            hotCountryImg = (ImageView) itemView.findViewById(R.id.img_hot_country);
            hotCountryCnname = (TextView) itemView.findViewById(R.id.text_hot_country_cnname);
            hotCountryEnname = (TextView) itemView.findViewById(R.id.text_hot_country_enname);
            hotCountryRl = (RelativeLayout) itemView.findViewById(R.id.rl_hot_country);
            hotCountryImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Desitination desitination = (Desitination) mItems.get(getLayoutPosition());
                    Bundle bundle = new Bundle();
                    bundle.putInt("country_id",desitination.getId());
                    bundle.putString("cn_name",desitination.getCnName());
                    bundle.putString("en_name",desitination.getEnName());
                    Intent intent = new Intent(mContext, DesDetailActivity.class);
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                }
            });
        }
    }


}









