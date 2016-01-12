package com.yueyang.travel.view.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;

import com.yueyang.travel.R;

/**
 * Created by Yang on 2016/1/12.
 */
public class SettingFragment extends PreferenceFragment
        implements Preference.OnPreferenceClickListener {

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
        wifiMode.setOnPreferenceClickListener(this);
        notifyMode.setOnPreferenceClickListener(this);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        wifiOn = sp.getBoolean("preference_wifi",false);
        notifyOn = sp.getBoolean("preference_notify",false);

    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = sp.edit();

        if (preference == wifiMode){
            editor.putBoolean("preference_wifi",!wifiOn);
            editor.apply();
            wifiOn = sp.getBoolean("preference_wifi",false);
            Log.e("wifi_changed","..." + wifiOn);
            return true;
        }

        if (preference == notifyMode){
            editor.putBoolean("preference_notify",!notifyOn);
            editor.apply();
            notifyOn = sp.getBoolean("preference_notify",false);
            Log.e("notify_changed","...." + notifyOn);
        }
        return true;
    }

}
