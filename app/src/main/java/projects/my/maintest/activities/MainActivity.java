package projects.my.maintest.activities;

import android.app.Fragment;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import projects.my.maintest.R;
import projects.my.maintest.adapters.MainTestAdapter;
import projects.my.maintest.common.ActivityUtils;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @ViewById
    ViewPager fragmentPager;

    @ViewById(R.id.sliding_tabs)
    TabLayout tabs;

    @AfterViews
    void init() {
        ActivityUtils.setToolbar(this, false);
        initViewPager(fragmentPager);
        setupTabs(tabs, fragmentPager);
    }

    /**
     * Реализует инициализапцию вьюпейджера и адаптера страниц.
     */
    private void initViewPager(ViewPager pager) {
        MainTestAdapter pageAdapter = new MainTestAdapter(getFragmentManager());
        pager.setAdapter(pageAdapter);

        // Пререндерим вью для фрагментов.
        Fragment[] fragments = pageAdapter.getFragments();
        for (int i = 0; i < fragments.length; i++) {
            pageAdapter.instantiateItem(pager, i);
        }

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