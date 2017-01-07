package com.aliakseipilko.flightdutytracker.view.fragment;


import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.RingtonePreference;
import android.text.TextUtils;

import com.aliakseipilko.flightdutytracker.R;
import com.aliakseipilko.flightdutytracker.dagger.components.DaggerStorageComponent;
import com.aliakseipilko.flightdutytracker.dagger.components.StorageComponent;
import com.aliakseipilko.flightdutytracker.dagger.modules.PrefsModule;

import javax.inject.Inject;

public class SettingsFragment extends PreferenceFragment {

    @Inject
    SharedPreferences prefs;

    private Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();
            if (preference instanceof ListPreference) {
                // For list preferences, look up the correct display value in
                // the preference's 'entries' list.
                ListPreference listPreference = (ListPreference) preference;
                int index = listPreference.findIndexOfValue(stringValue);

                // Set the summary to reflect the new value.
                preference.setSummary(
                        index >= 0
                                ? listPreference.getEntries()[index]
                                : null);

            } else if (preference instanceof RingtonePreference) {
                // For ringtone preferences, look up the correct display value
                // using RingtoneManager.
                if (TextUtils.isEmpty(stringValue)) {
                    // Empty values correspond to 'silent' (no ringtone).
                    preference.setSummary("Silent");

                } else {
                    Ringtone ringtone = RingtoneManager.getRingtone(
                            preference.getContext(), Uri.parse(stringValue));

                    if (ringtone == null) {
                        // Clear the summary if there was a lookup error.
                        preference.setSummary(null);
                    } else {
                        // Set the summary to reflect the new ringtone display
                        // name.
                        String name = ringtone.getTitle(preference.getContext());
                        preference.setSummary(name);
                    }
                }
            } else {
                // For all other preferences, set the summary to the value's
                // simple string representation.
                preference.setSummary(stringValue);
            }
            return true;
        }
    };
    private Preference.OnPreferenceChangeListener sBindHoursPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object o) {
            prefs.edit().putInt(preference.getKey(), Integer.parseInt(o.toString())).commit();
            preference.setSummary(o.toString());
            return true;
        }
    };

    private void bindPreferenceSummaryToValue(Preference preference) {
        // Set the listener to watch for value changes.
        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

        // Trigger the listener immediately with the preference's
        // current value.
        sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                prefs.getString(preference.getKey(), ""));
    }

    private void bindPreferenceSummaryToHoursValue(Preference preference) {
        // Set the listener to watch for value changes.
        preference.setOnPreferenceChangeListener(sBindHoursPreferenceSummaryToValueListener);

        // Trigger the listener immediately with the preference's
        // current value.
        sBindHoursPreferenceSummaryToValueListener.onPreferenceChange(preference,
                prefs.getInt(preference.getKey(), 60));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.prefs);
        setHasOptionsMenu(true);

        StorageComponent component = DaggerStorageComponent.builder()
                .prefsModule(new PrefsModule(getActivity().getApplicationContext()))
                .build();
        component.inject(this);

        bindPreferenceSummaryToValue(findPreference("airportCodeType"));

        bindPreferenceSummaryToHoursValue(findPreference("max7DayDutyHours"));
        bindPreferenceSummaryToHoursValue(findPreference("max28DayDutyHours"));
        bindPreferenceSummaryToHoursValue(findPreference("maxYearDutyHours"));

        bindPreferenceSummaryToHoursValue(findPreference("max7DayFlightHours"));
        bindPreferenceSummaryToHoursValue(findPreference("max28DayFlightHours"));
        bindPreferenceSummaryToHoursValue(findPreference("maxYearFlightHours"));
    }

}
