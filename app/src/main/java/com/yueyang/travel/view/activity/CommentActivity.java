package com.yueyang.travel.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.arrownock.social.IAnSocialCallback;
import com.yueyang.travel.R;
import com.yueyang.travel.Utils.ParseUtils;
import com.yueyang.travel.manager.SocialManager;
import com.yueyang.travel.manager.UserManager;
import com.yueyang.travel.model.Constants;
import com.yueyang.travel.model.bean.Comment;
import com.yueyang.travel.view.adapter.CommentAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Yang on 2016/1/10.
 */
public class CommentActivity extends BaseActivity implements View.OnClickListener{

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.recycler_comments)
    RecyclerView recyclerComments;
    @Bind(R.id.comment_tv)
    EditText commentTv;
    @Bind(R.id.comment_btn)
    Button commentBtn;

    private String postId;
    private String replyUserId;
    private String userId;
    private CommentAdapter commentAdapter;
    private List<Comment> commentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        init();
        getAllComments();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_comment;
    }

    @Override
    public void initToolbar() {
        super.initToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.commit_title);
    }

    private void init(){
        Bundle bundle = getIntent().getExtras();
        postId = bundle.getString(Constants.SEND_POST_ID);

        commentBtn.setOnClickListener(this);
        commentList = new ArrayList<>();
        commentAdapter = new CommentAdapter(this,commentList);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerComments.setLayoutManager(manager);
        recyclerComments.setHasFixedSize(true);
        recyclerComments.setItemAnimator(new DefaultItemAnimator());
        recyclerComments.setAdapter(commentAdapter);
    }

    private void getAllComments(){
        SocialManager.fetchCommentByPostId(this, postId, new SocialManager.FetchCommentCallback() {
            @Override
            public void onFailure() {

            }

            @Override
            public void onSuccess(List<Comment> data) {
                commentList.addAll(data);
                commentAdapter.notifyDataSetChanged();
            }
        });
    }

    public void showSoftKeyboard(View view) {
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    private void hideKeyBroad(){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.comment_btn:
                String content = commentTv.getText().toString();
                commentTv.setText("");
                SocialManager.createComment(this,
                        postId,
                        replyUserId,
                        UserManager.getInstance(this).getCurrentUser().userId,
                        content,
                        new IAnSocialCallback() {
                            @Override
                            public void onSuccess(JSONObject jsonObject) {

                                hideKeyBroad();
                                try {
                                    Comment comment = ParseUtils.getComment(jsonObject);
                                    commentList.add(comment);
                                    commentAdapter.notifyDataSetChanged();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(JSONObject jsonObject) {

                            }
                        });
        }
    }
}
