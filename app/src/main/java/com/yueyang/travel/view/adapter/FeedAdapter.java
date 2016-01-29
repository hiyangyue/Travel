package com.yueyang.travel.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.yueyang.travel.R;
import com.yueyang.travel.Utils.BitmapUtils;
import com.yueyang.travel.Utils.GlideUtils;
import com.yueyang.travel.Utils.ParseUtils;
import com.yueyang.travel.Utils.SnackbarUtils;
import com.yueyang.travel.manager.SocialManager;
import com.yueyang.travel.model.Constants;
import com.yueyang.travel.model.bean.Post;
import com.yueyang.travel.view.activity.CommentActivity;
import com.yueyang.travel.view.activity.PhotoDetailActivity;
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
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final Post post = postList.get(position);
        holder.feedName.setText(post.getUser().nickname);
        holder.feedContent.setText(post.getContent());

        GlideUtils.loadImg(mContext, post.photoUrls, holder.feedImage);
        if (post.user.userPhotoUrl != null) {
            GlideUtils.loadImg(mContext, post.user.userPhotoUrl, holder.feedHeaderImage);
        }

        holder.feedShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareImage(holder.feedShare,post.getPhotoUrls());
            }
        });


        holder.feedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PhotoDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(Constants.TRANSITIONS_AVATAR, post.user.userPhotoUrl);
                bundle.putString(Constants.TRANSITIONS_NICK_NAME, post.getUser().nickname);
                bundle.putString(Constants.TRANSITIONS_PHOTO, post.photoUrls);
                bundle.putString(Constants.TRANSITIONS_TIME, String.valueOf(post.createdAt));
                bundle.putString(Constants.TRANSITIONS_CONTENT, post.content);
                bundle.putString(Constants.TRANSITIONS_POST_ID, post.postId);
                intent.putExtras(bundle);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Pair<View, String> p1 =
                            Pair.create((View) holder.feedHeaderImage, mContext.getString(R.string.transitions_avatar));
                    Pair<View, String> p2 =
                            Pair.create((View) holder.feedName, mContext.getString(R.string.transitions_nickname));
                    Pair<View, String> p4 =
                            Pair.create((View) holder.feedImage, mContext.getString(R.string.transitions_photo));
                    ActivityOptionsCompat options = ActivityOptionsCompat
                            .makeSceneTransitionAnimation((Activity) mContext, p1, p2, p4);
                    mContext.startActivity(intent, options.toBundle());
                } else {
                    mContext.startActivity(intent);
                }

            }
        });


        isLike(post, holder.feedLike);
        holder.feedLike.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                addLike(holder.itemView, post, holder.feedLike);
            }
        });

        holder.feedComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CommentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(Constants.SEND_POST_ID, post.postId);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
    }

    private void isLike(Post post, final ImageView likeImg) {
        SocialManager.getLikeIdByUser(mContext, post.postId, new SocialManager.FetchLikeByIdCallback() {
            @Override
            public void onFailure(JSONObject jsonObject) {

            }

            @Override
            public void onSuccess(JSONObject jsonObject) {

                try {
                    boolean hasLike = ParseUtils.isHasLike(jsonObject);
                    if (hasLike) {
                        likeImg.setImageResource(R.drawable.icon_like_red);
                    } else {
                        likeImg.setImageResource(R.drawable.icon_like_grey);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void addLike(final View view, final Post post, final ImageView likeImg) {
        SocialManager.createLike(mContext, post.user, post, new SocialManager.LikeCallback() {
            @Override
            public void onFailure(JSONObject object) {
                SocialManager.getLikeIdByUser(mContext, post.postId, new SocialManager.FetchLikeByIdCallback() {
                    @Override
                    public void onFailure(JSONObject jsonObject) {

                    }

                    @Override
                    public void onSuccess(JSONObject jsonObject) {

                        String likeId = null;
                        try {
                            likeId = ParseUtils.getLikeId(jsonObject);
                            SocialManager.deleteLike(mContext, likeId, post, new SocialManager.LikeCallback() {
                                @Override
                                public void onFailure(JSONObject object) {

                                }

                                @Override
                                public void onSuccess(JSONObject object) {
                                    likeImg.setImageResource(R.drawable.icon_like_grey);
                                    SnackbarUtils.getSnackbar(view, view.getResources().getString(R.string.cancel_like));
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }

            @Override
            public void onSuccess(JSONObject object) {
                likeImg.setImageResource(R.drawable.icon_like_red);
                SnackbarUtils.getSnackbar(view, view.getResources().getString(R.string.add_like));
            }
        });

    }

    public void shareImage(final View view,String photoUrl) {
        Glide.with(mContext).load(photoUrl)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        shareIntent(view,BitmapUtils.getUriFromBitmap(mContext, resource));
                    }
                });
    }

    private void shareIntent(View view,Uri uri) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        if (isIntentSafe(shareIntent)) {
            mContext.startActivity(shareIntent);
        }else {
            SnackbarUtils.getSnackbar(view,mContext.getString(R.string.no_received));
        }
    }

    private boolean isIntentSafe(Intent intent) {
        PackageManager packageManager = mContext.getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
        return activities.size() > 0;
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.feed_header_image)
        CircleImageView feedHeaderImage;
        @Bind(R.id.feed_name)
        TextView feedName;
        @Bind(R.id.feed_share)
        ImageView feedShare;
        @Bind(R.id.feed_content)
        TextView feedContent;
        @Bind(R.id.feed_image)
        ImageView feedImage;
        @Bind(R.id.feed_like)
        public ImageView feedLike;
        @Bind(R.id.feed_comment)
        ImageView feedComment;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

}
