package projects.my.maintest.activities;

import android.support.v7.app.AppCompatActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import projects.my.maintest.R;
import projects.my.maintest.common.ActivityUtils;
import projects.my.maintest.fragments.AppPreferencesFragment_;

@EActivity(R.layout.activity_preferences)
public class PreferencesActivity extends AppCompatActivity {

    @AfterViews
    void init() {
        ActivityUtils.setToolbar(this, true);
        getFragmentManager().beginTransaction().replace(R.id.pref_content,
                new AppPreferencesFragment_()).commit();
    }
}