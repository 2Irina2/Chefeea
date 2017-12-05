package com.example.android.chefeea;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;

/**
 * Created by irina on 05.12.2017.
 */

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener{
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.pref_visualizer);

        PreferenceScreen preferenceScreen = getPreferenceScreen();
        SharedPreferences sharedPreferences = preferenceScreen.getSharedPreferences();
        int count = preferenceScreen.getPreferenceCount();

        for(int i = 0; i < count; i++){
            Preference preference = preferenceScreen.getPreference(i);

            if(preference instanceof ListPreference){
                ListPreference listPreference = (ListPreference) preference;
                String value = sharedPreferences.getString(listPreference.getValue(), "");

                preference.setSummary(listPreference.getEntry());

                int preferenceIndex = listPreference.findIndexOfValue(value);
                if(preferenceIndex >= 0){
                    listPreference.setSummary(listPreference.getEntries()[preferenceIndex]);
                }
            }
        }


    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        Preference preference = findPreference(s);
        if(null != preference){
            if(preference instanceof ListPreference){
                String value = sharedPreferences.getString(preference.getKey(), "");

                int preferenceIndex = ((ListPreference) preference).findIndexOfValue(value);
                if(preferenceIndex >= 0) {
                    preference.setSummary(((ListPreference) preference)
                            .getEntries()[preferenceIndex]);
                }
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }
}
