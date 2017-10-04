package com.jstech.fairy.Navigation.Navi_Security;

import android.app.FragmentManager;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.SwitchPreference;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.jstech.fairy.R;

public class Navi_Secuity extends AppCompatActivity {
    private static final String KEY_PASSCODE_FRAGMENT = "passcode-fragment";
    private static final String KEY_PREFERENCE_FRAGMENT = "preference-fragment";

    private PasscodePreferenceFragment mPasscodePreferenceFragment;
    private Navi_Security_PreferenceFragment mSamplePreferenceFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManager fragmentManager = getFragmentManager();
        mSamplePreferenceFragment = (Navi_Security_PreferenceFragment) fragmentManager.findFragmentByTag(KEY_PREFERENCE_FRAGMENT);
        mPasscodePreferenceFragment = (PasscodePreferenceFragment) fragmentManager.findFragmentByTag(KEY_PASSCODE_FRAGMENT);

        if (mSamplePreferenceFragment == null || mPasscodePreferenceFragment == null) {
            Bundle passcodeArgs = new Bundle();
            passcodeArgs.putBoolean(PasscodePreferenceFragment.KEY_SHOULD_INFLATE, false);
            mSamplePreferenceFragment = new Navi_Security_PreferenceFragment();
            mPasscodePreferenceFragment = new PasscodePreferenceFragment();
            mPasscodePreferenceFragment.setArguments(passcodeArgs);

            fragmentManager.beginTransaction()
                    .replace(android.R.id.content, mPasscodePreferenceFragment, KEY_PASSCODE_FRAGMENT)
                    .add(android.R.id.content, mSamplePreferenceFragment, KEY_PREFERENCE_FRAGMENT)
                    .commit();
        }
    }
    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onStart() {
        super.onStart();
        Preference togglePreference = mSamplePreferenceFragment.findPreference(
                getString(R.string.pref_key_passcode_toggle));
        Preference changePreference = mSamplePreferenceFragment.findPreference(
                getString(R.string.pref_key_change_passcode));

        if (togglePreference != null && changePreference != null) {
            mPasscodePreferenceFragment.setPreferences(togglePreference, changePreference);
            ((SwitchPreference) togglePreference).setChecked(
                    AppLockManager.getInstance().getAppLock().isPasswordLocked());
        }
    }

}
