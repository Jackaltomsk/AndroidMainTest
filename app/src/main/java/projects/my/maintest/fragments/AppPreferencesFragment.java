package projects.my.maintest.fragments;

import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import org.androidannotations.annotations.AfterPreferences;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.PreferenceByKey;
import org.androidannotations.annotations.PreferenceChange;
import org.androidannotations.annotations.PreferenceScreen;

import projects.my.maintest.R;
import projects.my.maintest.common.ActivityUtils;

/**
 * Фрагмент настроек.
 */
@EFragment
@PreferenceScreen(R.xml.preferences)
public class AppPreferencesFragment extends PreferenceFragment {

    private static final String TAG = AppPreferencesFragment.class.getSimpleName();

    @PreferenceByKey(R.string.preferences_chosen_lang)
    ListPreference prefList;

    @AfterPreferences
    void init() {
        if (prefList.getValue() != null) prefList.setSummary(prefList.getValue());
    }

    @PreferenceChange(R.string.preferences_chosen_lang)
    void langChanges(Preference preference, String newValue) {
        if (prefList.getValue() == null || !prefList.getValue().equals(newValue)) {
            prefList.setSummary(newValue);
            ActivityUtils.setLocale(getActivity(), newValue);
            getActivity().recreate();
        }
    }
}