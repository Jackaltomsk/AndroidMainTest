package projects.my.maintest.common;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;

import java.util.Locale;

import projects.my.maintest.R;

/**
 * Содержит вспомогательные методы для активити.
 */
public class ActivityUtils {
    /**
     * Реализует инициализацию тулбара.
     * @param activity Текущая активити.
     * @param setUpNavigation Флаг установки навигации в тулбаре.
     */
    public static void setToolbar(AppCompatActivity activity, boolean setUpNavigation) {
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
        if (setUpNavigation) {
            android.support.v7.app.ActionBar actionBar = activity.getSupportActionBar();
            if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public static void setLocale(Context context, String lang) {
        Resources res = context.getResources();
        Configuration conf = res.getConfiguration();
        if (!conf.locale.getLanguage().equals(lang)) {
            Locale myLocale = new Locale(lang);
            DisplayMetrics dm = res.getDisplayMetrics();
            conf.locale = myLocale;
            res.updateConfiguration(conf, dm);
        }
    }
}