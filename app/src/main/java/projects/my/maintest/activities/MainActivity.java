package projects.my.maintest.activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import projects.my.maintest.R;
import projects.my.maintest.adapters.MainTestAdapter;
import projects.my.maintest.common.ActivityUtils;

@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.activity_main)
public class MainActivity extends AppCompatActivity implements BackPressedListeners {

    @ViewById
    ViewPager fragmentPager;

    @ViewById
    TabLayout slidingTabs;

    /**
     * Слушатели события нажатия кнопки "назад".
     */
    private final ArrayList<BackPressedListener> backPressedListeners = new ArrayList<>();

    public void addBackPressedListener(BackPressedListener listener) {
        if (listener != null && !backPressedListeners.contains(listener)) {
            backPressedListeners.add(listener);
        }
    }

    public void removeBackPressedListener(BackPressedListener listener) {
        if (listener != null) {
            backPressedListeners.remove(listener);
        }
    }

    @AfterViews
    void init() {
        ActivityUtils.setToolbar(this, false);
        initViewPager(fragmentPager);
        setupTabs(slidingTabs, fragmentPager);
    }

    @OptionsItem(R.id.menuPrefCall)
    void startPref() {
        Intent prefIntent = new Intent(this, PreferencesActivity_.class);
        startActivity(prefIntent);
    }

    /**
     * Реализует инициализапцию вьюпейджера и адаптера страниц.
     */
    private void initViewPager(ViewPager pager) {
        MainTestAdapter pageAdapter = new MainTestAdapter(getFragmentManager());
        pager.setAdapter(pageAdapter);

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    public void onBackPressed() {
        for (BackPressedListener ls : backPressedListeners) {
            if (ls.onBackPressed()) return;
        }
        super.onBackPressed();
    }

    /**
     * Реализует инициализацию табов.
     */
    private void setupTabs(final TabLayout tabs, final ViewPager pager) {
        tabs.setupWithViewPager(pager);
        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                pager.setCurrentItem(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }
}