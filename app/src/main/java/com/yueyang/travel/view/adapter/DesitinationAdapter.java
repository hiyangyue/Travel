package com.yueyang.travel.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yueyang.travel.R;
import com.yueyang.travel.domin.Utils.MaterialUtils;
import com.yueyang.travel.model.bean.Desitination;
import com.yueyang.travel.view.activity.DesDetailActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Yang on 2015/12/12.
 */
public class DesitinationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Object> mItems;
    private Context mContext;
    private final int TYPE_HOT = 1;

    public DesitinationAdapter(Context mContext, List<Object> mItems) {
        this.mContext = mContext;
        this.mItems = mItems;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(mContext);

        switch (viewType) {
            case TYPE_HOT:
                View hotView = inflater.inflate(R.layout.holder_hot_country, null);
                viewHolder = new HotCountryHolder(hotView);
                break;
            default:
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case TYPE_HOT:
                Desitination desitination = (Desitination) mItems.get(position);
                final HotCountryHolder hotCountryHolder = (HotCountryHolder) holder;
                final ImageView hotImg = hotCountryHolder.hotCountryImg;
                hotCountryHolder.hotCountryCnname.setText(desitination.getCnName());
                hotCountryHolder.hotCountryEnname.setText(desitination.getEnName());
                MaterialUtils.setPattle(mContext, hotImg, desitination.getPhotoUrl(), hotCountryHolder.hotCountryRl);
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

    public static boolean isHeader(int position) {
        return position == 0;
    }

    public class HotCountryHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.card_view)
        CardView cardView;
        @Bind(R.id.img_hot_country)
        ImageView hotCountryImg;
        @Bind(R.id.text_hot_country_cnname)
        TextView hotCountryCnname;;
        @Bind(R.id.text_hot_country_enname)
        TextView hotCountryEnname;
        @Bind(R.id.rl_hot_country)
        RelativeLayout hotCountryRl;


        public HotCountryHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Desitination des = (Desitination) mItems.get(getLayoutPosition());
                    Bundle bundle = new Bundle();
                    bundle.putInt("country_id", des.getId());
                    bundle.putString("cn_name", des.getCnName());
                    bundle.putString("en_name", des.getEnName());
                    Intent intent = new Intent(mContext, DesDetailActivity.class);
                    intent.putExtras(bundle);
//                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((AppCompatActivity)mContext);
//                    mContext.startActivity(intent,options.toBundle());
                    mContext.startActivity(intent);
                }
            });
        }
    }


}









