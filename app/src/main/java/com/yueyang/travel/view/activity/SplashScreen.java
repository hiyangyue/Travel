package com.yueyang.travel.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.yueyang.travel.R;
import com.yueyang.travel.domin.manager.SpfHelper;
import com.yueyang.travel.domin.manager.UserManager;
import com.yueyang.travel.model.Constants;
import com.yueyang.travel.model.bean.User;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import za.co.riggaroo.materialhelptutorial.TutorialItem;
import za.co.riggaroo.materialhelptutorial.tutorial.MaterialTutorialActivity;

/**
 * Created by Yang on 2015/12/17.
 */
public class SplashScreen extends AppCompatActivity {

    @Bind(R.id.splash_image)
    ImageView splashImage;

    private String payload;
    private static final int REQUEST_CODE = 12345;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        Animation animation = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.splash);
        splashImage.startAnimation(animation);
        checkBundle();

//        Thread timerThread = new Thread() {
//            public void run() {
//                try {
//                    sleep(3000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } finally {
                   isFirstLogin();
//                }
//            }
//        };
//        timerThread.start();
    }

    private void isFirstLogin(){
        if (SpfHelper.getInstance(this).getFirstLogin()){
            signIn();
        }else {
            SpfHelper.getInstance(this).updateLogin();
            startGuide();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE){
            goToLoginActivity();
        }
    }

    public void startGuide(){
        Intent mainAct = new Intent(this, MaterialTutorialActivity.class);
        mainAct.putParcelableArrayListExtra(MaterialTutorialActivity.MATERIAL_TUTORIAL_ARG_TUTORIAL_ITEMS, getTutorialItems(this));
        startActivityForResult(mainAct, REQUEST_CODE);
    }

    private ArrayList<TutorialItem> getTutorialItems(Context context) {
        TutorialItem tutorialItem1 = new TutorialItem(
                context.getString(R.string.guide_title_1),
                context.getString(R.string.guide_sub_title_1),
                R.color.colorAccent, R.drawable.bg_1,  0);

        TutorialItem tutorialItem2 = new TutorialItem(
                context.getString(R.string.guide_title_2),
                context.getString(R.string.guide_sub_title_2),
                R.color.colorPrimaryDark, R.drawable.bg_2,  0);

//        TutorialItem tutorialItem3 = new TutorialItem(
//                context.getString(R.string.title),
//                context.getString(R.string.sub_title),
//                R.color.colorAccent, R.drawable.bg_3,  0);
//
//        TutorialItem tutorialItem4 = new TutorialItem(
//                context.getString(R.string.title),
//                context.getString(R.string.sub_title),
//                R.color.colorPrimaryDark, R.drawable.bg_4,  0);

        ArrayList<TutorialItem> tutorialItems = new ArrayList<>();
        tutorialItems.add(tutorialItem1);
        tutorialItems.add(tutorialItem2);
//        tutorialItems.add(tutorialItem3);
//        tutorialItems.add(tutorialItem4);

        return tutorialItems;
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

}
