package com.yueyang.travel.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yueyang.travel.R;
import com.yueyang.travel.Utils.GlideUtils;
import com.yueyang.travel.model.bean.Post;
import com.yueyang.travel.view.wiget.CircleImageView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Yang on 2015/12/19.
 */
public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {

    private Context mContext;
    private List<Post> postList;

    public FeedAdapter(Context mContext, List<Post> postList) {
        this.mContext = mContext;
        this.postList = postList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.holder_feed, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Post post = postList.get(position);
        holder.feedName.setText(post.getUser().nickname);
        holder.feedTime.setText(String.valueOf(post.createdAt));
        holder.feedContent.setText(post.getContent());

        GlideUtils.loadImg(mContext,post.photoUrls,holder.feedImage);
        if (post.user.userPhotoUrl != null){
            GlideUtils.loadImg(mContext,post.user.userPhotoUrl,holder.feedHeaderImage);
        }
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.feed_header_image)
        CircleImageView feedHeaderImage;
        @Bind(R.id.feed_name)
        TextView feedName;
        @Bind(R.id.feed_time)
        TextView feedTime;
        @Bind(R.id.feed_content)
        TextView feedContent;
        @Bind(R.id.feed_image)
        ImageView feedImage;
        @Bind(R.id.feed_like)
        ImageView feedLike;
        @Bind(R.id.feed_commit)
        ImageView feedCommit;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this,itemView);
        }
    }
}
