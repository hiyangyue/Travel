package com.yueyang.travel.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.yueyang.travel.R;
import com.yueyang.travel.manager.SpfHelper;
import com.yueyang.travel.manager.UserManager;
import com.yueyang.travel.model.Constants;
import com.yueyang.travel.model.bean.User;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Yang on 2015/12/17.
 */
public class SplashScreen extends AppCompatActivity {

    @Bind(R.id.splash_image)
    ImageView splashImage;

    private String payload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        Animation animation = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.splash);
        splashImage.startAnimation(animation);
        checkBundle();
        signIn();

//
//        Thread timerThread = new Thread() {
//            public void run() {
//                try {
//                    sleep(3000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } finally {
//                    signIn();
//                }
//            }
//        };
//        timerThread.start();
    }

    private void signIn(){
        if (SpfHelper.getInstance(this).hasSignIn()){
            User user = new User();
            user.userName = SpfHelper.getInstance(this).getMyUsername();
            user.clientId = SpfHelper.getInstance(this).getMyClientId();
            user.userId = SpfHelper.getInstance(this).getMyUserId();
            UserManager.getInstance(this).setCurrentUser(user);
            goToMainActivity();
        }else {
            goToLoginActivity();
        }
    }

    private void goToLoginActivity() {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();
    }

    private void checkBundle() {
        if (getIntent().hasExtra(Constants.INTENT_EXTRA_KEY_PAYLOAD)) {
            payload = getIntent().getStringExtra(Constants.INTENT_EXTRA_KEY_PAYLOAD);
        }
    }

    private void goToMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        if (payload != null) {
            i.putExtra(Constants.INTENT_EXTRA_KEY_PAYLOAD, payload);
        }
        startActivity(i);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
