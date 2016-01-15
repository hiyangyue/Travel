package com.yueyang.travel.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.arrownock.social.IAnSocialCallback;
import com.yueyang.travel.R;
import com.yueyang.travel.Utils.ParseUtils;
import com.yueyang.travel.Utils.SnackbarUtils;
import com.yueyang.travel.manager.SpfHelper;
import com.yueyang.travel.manager.UserManager;
import com.yueyang.travel.model.Constants;
import com.yueyang.travel.model.bean.User;
import com.yueyang.travel.view.activity.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Yang on 2015/12/17.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    @Bind(R.id.user_name_et)
    EditText userNameEt;
    @Bind(R.id.user_pass_et)
    EditText userPassEt;
    @Bind(R.id.login_btn)
    Button loginBtn;
    @Bind(R.id.progress_bar)
    ProgressBar progressBar;

    private String payload;

    private void checkBundle() {
        if (getActivity().getIntent().hasExtra(Constants.INTENT_EXTRA_KEY_PAYLOAD)) {
            payload = getActivity().getIntent().getStringExtra(Constants.INTENT_EXTRA_KEY_PAYLOAD);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        checkBundle();
        loginBtn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                loadProgress();
                UserManager.getInstance(getContext())
                        .login(userNameEt.getText().toString(), userPassEt.getText().toString(), new IAnSocialCallback() {
                            @Override
                            public void onSuccess(JSONObject jsonObject) {
                                hideProgress();
                                SnackbarUtils.getSnackbar(loginBtn, getString(R.string.login_success));
                                try {
                                    User user = ParseUtils.getUserFromRegister(jsonObject);
                                    afterLogin(user,userNameEt.getText().toString(),userPassEt.getText().toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(JSONObject jsonObject) {
                                hideProgress();
                                SnackbarUtils.getSnackbar(loginBtn, getString(R.string.login_error));
                            }
                        });
                break;
        }
    }

    private void afterLogin(User user,String userName,String pass){
        if (!SpfHelper.getInstance(getContext()).hasSignIn()) {
            SpfHelper.getInstance(getContext()).saveUserInfo(userName,
                    pass,user.nickname,user.userId, user.clientId);
        }
        UserManager.getInstance(getContext()).setCurrentUser(user);

        goToMainActivity();
    }

    private void goToMainActivity() {
        Intent i = new Intent(getActivity(), MainActivity.class);
        if (payload != null) {
            i.putExtra(Constants.INTENT_EXTRA_KEY_PAYLOAD, payload);
        }
        startActivity(i);
        hideProgress();
        getActivity().finish();
    }

    private void loadProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}













