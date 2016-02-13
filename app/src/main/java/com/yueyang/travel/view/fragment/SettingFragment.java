package com.yueyang.travel.view.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;

import com.yueyang.travel.R;
import com.yueyang.travel.domin.Utils.SnackbarUtils;
import com.yueyang.travel.domin.manager.SpfHelper;
import com.yueyang.travel.view.activity.LoginActivity;

/**
 * Created by Yang on 2016/1/12.
 */
public class SettingFragment extends PreferenceFragment
        implements Preference.OnPreferenceClickListener{

    private CheckBoxPreference wifiMode,notifyMode;
    private Preference exitPreference,clearCachePreference,sourcePreference,aboutPreference;
    private boolean wifiOn,notifyOn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference);

        init();
    }

    private void init(){
        wifiMode = (CheckBoxPreference) findPreference("preference_wifi");
        notifyMode = (CheckBoxPreference) findPreference("preference_notify");
        exitPreference = findPreference("preference_exit");
        wifiMode.setOnPreferenceClickListener(this);
        notifyMode.setOnPreferenceClickListener(this);
        exitPreference.setOnPreferenceClickListener(this);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        wifiOn = sp.getBoolean("preference_wifi",false);
        notifyOn = sp.getBoolean("preference_notify",false);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = sp.edit();

        if (preference == exitPreference){
            booleanExit();
        }else if (preference == wifiMode){
            editor.putBoolean("preference_wifi",!wifiOn);
            editor.apply();
            wifiOn = sp.getBoolean("preference_wifi",false);
            return true;
        }else if (preference == notifyMode){
            editor.putBoolean("preference_notify",!notifyOn);
            editor.apply();
            notifyOn = sp.getBoolean("preference_notify",false);
        }
        return true;
    }

    private void booleanExit(){
        AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity(),R.style.MyDialog);
        builder.setTitle(getString(R.string.exit_title))
                .setPositiveButton(getString(R.string.sure), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                exit();
            }
        }).setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).show();
    }

    private void exit(){
        SpfHelper.getInstance(getActivity()).clearUserInfo();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        SnackbarUtils.getSnackbar(getView(),getString(R.string.exit_success));
    }

}
