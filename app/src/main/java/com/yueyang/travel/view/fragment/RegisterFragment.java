package com.yueyang.travel.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.arrownock.social.IAnSocialCallback;
import com.yueyang.travel.R;
import com.yueyang.travel.Utils.SnackbarUtils;
import com.yueyang.travel.manager.IMManager;
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
 * Created by Yang on 2015/12/18.
 */
public class RegisterFragment extends Fragment {

    @Bind(R.id.user_name_et)
    EditText userNameEt;
    @Bind(R.id.user_pass_et)
    EditText userPassEt;
    @Bind(R.id.register_btn)
    Button registerBtn;
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

    private void initRegister(){

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadProgress();
                UserManager.getInstance(getContext())
                        .signUp(userNameEt.getText().toString(), userPassEt.getText().toString(), "nickname",new IAnSocialCallback() {
                            @Override
                            public void onSuccess(JSONObject jsonObject) {
                                hideProgress();
                                SnackbarUtils.getSnackbar(registerBtn, getString(R.string.register_success));
                                JSONObject userJson = null;
                                try {
                                    userJson = jsonObject.getJSONObject("response").getJSONObject("user");
                                    User user = new User(userJson);
                                    user.update();
                                    afterRegister(user, userNameEt.getText().toString(), userPassEt.getText().toString(), "nickname");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(JSONObject jsonObject) {
                                hideProgress();
                                SnackbarUtils.getSnackbar(registerBtn, getString(R.string.register_error));
                            }
                        });
            }
        });
    }

    private void afterRegister(User user,String name,String pass,String nickName){
        if (!SpfHelper.getInstance(getContext()).hasSignIn()) {
            SpfHelper.getInstance(getContext()).saveUserInfo(name,
                    pass,
                    "NickName",
                    user.userId, user.clientId);
        }
        IMManager.getInstance(getContext()).connect(user.clientId);
        UserManager.getInstance(getContext()).setCurrentUser(user);

        goToMainActivity();
    }

    private void  goToMainActivity(){
        Intent i = new Intent(getActivity(), MainActivity.class);
        if (payload != null) {
            i.putExtra(Constants.INTENT_EXTRA_KEY_PAYLOAD, payload);
        }
        startActivity(i);
        hideProgress();
        getActivity().finish();
    }

    private void loadProgress(){
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgress(){
        progressBar.setVisibility(View.INVISIBLE);
    }
}







