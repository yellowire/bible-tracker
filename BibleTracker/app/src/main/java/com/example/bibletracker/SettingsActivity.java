package com.example.bibletracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SeekBarPreference;
import androidx.preference.SwitchPreferenceCompat;

public class SettingsActivity extends AppCompatActivity {

    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);

            SeekBarPreference seeker = findPreference("remindTime");
            int progress = seeker.getValue();
            String summary = progressSwitch(progress);
            seeker.setSummary(summary);
            seeker.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    final int progress = Integer.valueOf(String.valueOf(newValue));
                    String summary = progressSwitch(progress);
                    preference.setSummary(summary);
                    return true;
                }
            });

            SwitchPreferenceCompat remind = findPreference("remind");
            assert remind != null;
            remind.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    Intent intent = new Intent(getContext(), ToBeAdded.class);
                    startActivity(intent);
                    return false;
                }
            });
        }
    }

    private static String progressSwitch(int progress) {
        String summary;
        switch(progress) {
            case 0:
                summary = "12:00a";
                break;
            case 1: case 2: case 3: case 4: case 5: case 6: case 7: case 8: case 9: case 10: case 11:
                summary = progress + ":00a";
                break;
            case 12:
                summary = "12:00p";
                break;
            case 13: case 14: case 15: case 16: case 17: case 18: case 19: case 20: case 21: case 22: case 23:
                summary = (progress - 12) + ":00p";
                break;
            default:
                summary = "12:00p";
        }
        return summary;
    }
}