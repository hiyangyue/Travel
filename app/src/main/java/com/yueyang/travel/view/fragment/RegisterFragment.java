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
import com.yueyang.travel.domin.manager.IMManager;
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
 * Created by Yang on 2015/12/18.
 */
public class RegisterFragment extends Fragment {

    @Bind(R.id.user_name_et)
    EditText userNameEt;
    @Bind(R.id.user_pass_et)
    EditText userPassEt;
    @Bind(R.id.register_btn)
    Button registerBtn;
    @Bind(R.id.user_nick_et)
    EditText userNickEt;

    private ProgressDialog progressDialog;
    private String payload;

    private void checkBundle() {
        if (getActivity().getIntent().hasExtra(Constants.INTENT_EXTRA_KEY_PAYLOAD)) {
            payload = getActivity().getIntent().getStringExtra(Constants.INTENT_EXTRA_KEY_PAYLOAD);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_register, container, false);
        ButterKnife.bind(this, view);
        checkBundle();
        initRegister();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void initRegister() {

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validate()){
                    showProgressDialog();
                    UserManager.getInstance(getContext())
                            .signUp(userNameEt.getText().toString(), userPassEt.getText().toString(), userNickEt.getText().toString(), new IAnSocialCallback() {
                                @Override
                                public void onSuccess(JSONObject jsonObject) {
                                    hideProgressDialog();
                                    SnackbarUtils.getSnackbar(registerBtn, getString(R.string.register_success));
                                    try {
                                        User user = ParseUtils.getUserFromRegister(jsonObject);
                                        afterRegister(user, userNameEt.getText().toString(), userPassEt.getText().toString(), userNickEt.getText().toString());
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onFailure(JSONObject jsonObject) {
                                    hideProgressDialog();
                                    SnackbarUtils.getSnackbar(registerBtn, getString(R.string.register_error));
                                }
                            });
                }

            }
        });
    }

    private void afterRegister(User user, String name, String pass, String nickName) {

        SpfHelper.getInstance(getContext()).saveUserInfo(name,
                pass,
                nickName,
                user.userId, user.clientId, user.userPhotoUrl);
        
        IMManager.getInstance(getContext()).connect(user.clientId);
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
        progressDialog.setMessage("注册中...");
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
        String nickName = userNickEt.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            userNameEt.setError("至少三个字母");
            valid = false;
        } else {
            userNameEt.setError(null);
        }

        if (nickName.isEmpty()){
            userNickEt.setError("昵称不能为空");
            valid = false;
        }else {
            userNickEt.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            userPassEt.setError("密码长度建议在4~10个之间");
            valid = false;
        } else {
           userPassEt.setError(null);
        }

        return valid;
    }

}







