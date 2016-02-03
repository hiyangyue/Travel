package com.yueyang.travel.view.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.arrownock.social.IAnSocialCallback;
import com.yueyang.travel.R;
import com.yueyang.travel.domin.Utils.ParseUtils;
import com.yueyang.travel.domin.Utils.SnackbarUtils;
import com.yueyang.travel.domin.manager.SpfHelper;
import com.yueyang.travel.domin.manager.UserManager;
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

    private ProgressDialog progressDialog;
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
                if (validate()){
                    showProgressDialog();

                    UserManager.getInstance(getContext())
                            .login(userNameEt.getText().toString(), userPassEt.getText().toString(), new IAnSocialCallback() {
                                @Override
                                public void onSuccess(JSONObject jsonObject) {
                                    hideProgressDialog();
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
                                    hideProgressDialog();
                                    SnackbarUtils.getSnackbar(loginBtn, getString(R.string.login_error));
                                }
                            });
                }
                break;
        }
    }

    private void afterLogin(User user,String userName,String pass){
        if (!SpfHelper.getInstance(getContext()).hasSignIn()) {
            SpfHelper.getInstance(getContext()).saveUserInfo(userName,
                    pass,user.nickname,user.userId, user.clientId,user.userPhotoUrl);
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
        getActivity().finish();
    }

    private void showProgressDialog(){
        progressDialog = new ProgressDialog(getActivity(),
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("登入中...");
        progressDialog.show();
    }

    private void hideProgressDialog(){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
            }
        });
    }

    private boolean validate() {
        boolean valid = true;

        String name = userNameEt.getText().toString();
        String password = userPassEt.getText().toString();

        if (name.isEmpty()) {
            userNameEt.setError("用户名不能为空");
            valid = false;
        } else {
            userNameEt.setError(null);
        }

        if (password.isEmpty()) {
            userPassEt.setError("密码不能为空");
            valid = false;
        } else {
            userPassEt.setError(null);
        }

        return valid;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}













