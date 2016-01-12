package com.yueyang.travel.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yueyang.travel.R;
import com.yueyang.travel.Utils.GlideUtils;
import com.yueyang.travel.Utils.ParseUtils;
import com.yueyang.travel.manager.SocialManager;
import com.yueyang.travel.model.Constants;
import com.yueyang.travel.model.bean.Post;
import com.yueyang.travel.view.activity.CommentActivity;
import com.yueyang.travel.view.wiget.CircleImageView;

import org.json.JSONException;
import org.json.JSONObject;

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
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final Post post = postList.get(position);
        holder.feedName.setText(post.getUser().nickname);
        holder.feedTime.setText(String.valueOf(post.createdAt));
        holder.feedContent.setText(post.getContent());

        GlideUtils.loadImg(mContext,post.photoUrls,holder.feedImage);
        if (post.user.userPhotoUrl != null){
            GlideUtils.loadImg(mContext,post.user.userPhotoUrl,holder.feedHeaderImage);
        }

        holder.feedLike.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                isLike(post,holder.feedLike);
            }
        });

        holder.feedComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CommentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(Constants.SEND_POST_ID,post.postId);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
    }

    private void isLike(final Post post, final ImageView likeImg){
        if (post.likeCount > 0){

            SocialManager.getLikeIdByUser(mContext, post.postId, new SocialManager.FetchLikeyByIdCallback() {
                @Override
                public void onFailure(JSONObject jsonObject) {

                }

                @Override
                public void onSuccess(final JSONObject jsonObject) {
                    try {
                        String likeId = ParseUtils.getLikeId(jsonObject);
                        if (likeId != null){
                            SocialManager.deleteLike(mContext, likeId, post, new SocialManager.LikeCallback() {
                                @Override
                                public void onFailure(JSONObject object) {

                                }

                                @Override
                                public void onSuccess(JSONObject object) {

                                }
                            });
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

        }else {
            SocialManager.createLike(mContext, post.user, post, new SocialManager.LikeCallback() {
                @Override
                public void onFailure(JSONObject object) {

                }

                @Override
                public void onSuccess(JSONObject object) {
                    likeImg.setImageResource(R.drawable.icon_like_grey);
                }
            });
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
        @Bind(R.id.feed_comment)
        ImageView feedComment;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this,itemView);
        }
    }

}
