package projects.my.maintest.adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.Arrays;

import projects.my.maintest.fragments.FragmentCommon;
import projects.my.maintest.fragments.ListFragment_;
import projects.my.maintest.fragments.MapFragment_;
import projects.my.maintest.fragments.ScalingFragment_;
import projects.my.maintest.fragments.ServiceFragment_;

/**
 * Адаптер страниц.
 */
public class MainTestAdapter extends FragmentPagerAdapter {
    private Fragment[] fragments;
    private final static int FRAGMENTS_COUNT = 4;

    public MainTestAdapter(FragmentManager fm) {
        super(fm);
        fragments = new Fragment[FRAGMENTS_COUNT];
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                if (fragments[position] == null) {
                    Fragment frg = new ListFragment_();
                    fragments[position] = frg;
                }
                return fragments[position];
            case 1:
                if (fragments[position] == null) {
                    Fragment frg = new ScalingFragment_();
                    fragments[position] = frg;
                }
                return fragments[position];
            case 2:
                if (fragments[position] == null) {
                    Fragment frg = new ServiceFragment_();
                    fragments[position] = frg;
                }
                return fragments[position];
            case 3:
                if (fragments[position] == null) {
                    Fragment frg = new MapFragment_();
                    fragments[position] = frg;
                }
                return fragments[position];
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return ((FragmentCommon) getItem(position)).getTitle();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (fragments[position] == null) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            fragments[position] = fragment;
            return fragment;
        }
        else return fragments[position];
    }

    public Fragment[] getFragments() {
        // Отдаем новый массив, чтобы нельзя было изменить внутренний.
        return Arrays.copyOf(fragments, fragments.length);
    }
}