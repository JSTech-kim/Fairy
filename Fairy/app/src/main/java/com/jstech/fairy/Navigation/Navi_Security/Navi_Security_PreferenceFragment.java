package com.jstech.fairy.Navigation.Navi_Security;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.jstech.fairy.R;

public class Navi_Security_PreferenceFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        addPreferencesFromResource(R.xml.passcode_preferences);
    }
}
