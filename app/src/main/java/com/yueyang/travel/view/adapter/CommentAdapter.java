package com.yueyang.travel.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yueyang.travel.R;
import com.yueyang.travel.domin.Utils.GlideUtils;
import com.yueyang.travel.model.bean.Comment;
import com.yueyang.travel.view.wiget.CircleImageView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Yang on 2016/1/10.
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private Context mContext;
    private List<Comment> commentList;

    public CommentAdapter(Context mContext, List<Comment> commentList) {
        this.mContext = mContext;
        this.commentList = commentList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.holder_comment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Comment comment = commentList.get(position);
        holder.commitTv.setText(comment.content);
        holder.commentName.setText(comment.commentUser.nickname);
        if (comment.commentUser.userPhotoUrl != null) {
            GlideUtils.loadImg(mContext, comment.commentUser.userPhotoUrl, holder.commitAvatar,30,30);
        }
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.commit_avatar)
        CircleImageView commitAvatar;
        @Bind(R.id.comment_name)
        TextView commentName;
        @Bind(R.id.commit_tv)
        TextView commitTv;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
