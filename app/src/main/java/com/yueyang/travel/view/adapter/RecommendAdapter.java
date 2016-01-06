package com.yueyang.travel.view.adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yueyang.travel.R;
import com.yueyang.travel.Utils.GlideUtils;
import com.yueyang.travel.model.bean.Topic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yang on 2015/12/10.
 */
public class RecommendAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<Object> mItems;
    private final int TYPE_PAGER = 0;
    private final int TYPE_FIND = 1;
    private final int TYPE_BORDER = 2;
    private final int TYPE_HEADER = 3;
    private final int TYPE_TOPIC = 4;

    private String[] headers = { "发现下一站" , "看热门游记" };

    public RecommendAdapter(Context mContext, List<Object> mItems) {
        this.mContext = mContext;
        this.mItems = mItems;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        switch (viewType){
            case TYPE_PAGER:
                View pagerView = inflater.inflate(R.layout.holder_pager,parent,false);
                viewHolder = new PagerHolder(pagerView);
                break;
            case TYPE_FIND:
                View findView = inflater.inflate(R.layout.holder_find,parent,false);
                viewHolder = new FindHolder(findView);
                break;
            case TYPE_BORDER:
                View borderView = inflater.inflate(R.layout.holder_border,parent,false);
                viewHolder = new BorderHolder(borderView);
                break;
            case TYPE_HEADER:
                View headerView = inflater.inflate(R.layout.holder_header,parent,false);
                viewHolder = new HeaderHolder(headerView);
                break;
            case TYPE_TOPIC:
                View topicView = inflater.inflate(R.layout.holder_topic,parent,false);
                viewHolder = new TopicHolder(topicView);
                break;
            default:
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()){
            case TYPE_PAGER:
                PagerHolder pagerHolder = (PagerHolder) holder;
                ArrayList<ImageView> imgList = new ArrayList<>();
                ImageView imgView1 = (ImageView) LayoutInflater.from(mContext).inflate(R.layout.adapter_pager,null);
                ImageView imgView2 = (ImageView) LayoutInflater.from(mContext).inflate(R.layout.adapter_pager,null);
                ImageView imgView3 = (ImageView) LayoutInflater.from(mContext).inflate(R.layout.adapter_pager,null);

                imgView1.setImageResource(R.drawable.banner);
                imgView2.setImageResource(R.drawable.banner2);
                imgView3.setImageResource(R.drawable.banner3);

                imgList.add(imgView1);
                imgList.add(imgView2);
                imgList.add(imgView3);

                TopPagerAdapter pagerAdapter = new TopPagerAdapter(imgList);
                pagerHolder.headerPager.setAdapter(pagerAdapter);
                break;
            case TYPE_HEADER:
                switch (position){
                    case 3:
                        HeaderHolder headerHolder = (HeaderHolder) holder;
                        headerHolder.headerText.setText(headers[0]);
                        break;
                }
                break;
            case TYPE_TOPIC:
                TopicHolder topicHolder = (TopicHolder) holder;
                Topic topic = (Topic) mItems.get(position);
                Log.e("topic_url", topic.getImgUrl());
                GlideUtils.loadImg(mContext,topic.getImgUrl(), topicHolder.topicImg);
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
        if (position == 0){
            return TYPE_PAGER;
        }else if (position == 1){
            return TYPE_FIND;
        }else if (position == 2){
            return TYPE_BORDER;
        }else if (isHeader(position)){
            return TYPE_HEADER;
        }else if(position == 4 | position == 5| position == 6){
            return TYPE_TOPIC;
        }else {
            return -1;
        }
    }

    public static boolean isHeader(int position){
        return position == 3;
    }

    public class PagerHolder extends RecyclerView.ViewHolder{

        public ViewPager headerPager;

        public PagerHolder(View itemView) {
            super(itemView);
            headerPager = (ViewPager) itemView.findViewById(R.id.holder_pager);
        }
    }

    public class FindHolder extends RecyclerView.ViewHolder{

        public FindHolder(View itemView) {
            super(itemView);
        }
    }

    public class BorderHolder extends RecyclerView.ViewHolder{

        public BorderHolder(View itemView) {
            super(itemView);
        }
    }

    public class HeaderHolder extends RecyclerView.ViewHolder{

        private TextView headerText;

        public HeaderHolder(View itemView) {
            super(itemView);
            headerText = (TextView) itemView.findViewById(R.id.holder_header_text);
        }
    }

    public class TopicHolder extends RecyclerView.ViewHolder{

        private ImageView topicImg;

        public TopicHolder(View itemView) {
            super(itemView);
            topicImg = (ImageView) itemView.findViewById(R.id.holder_topic_img);
        }
    }
}












