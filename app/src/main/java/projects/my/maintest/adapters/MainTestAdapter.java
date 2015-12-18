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
    private final static int FRAGMENTS_COUNT = 4;
    private final static String[] names = new String[] { "List", "Scaling", "Service", "Map" };

    public MainTestAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return new ListFragment_();
            case 1: return new ScalingFragment_();
            case 2: return new ServiceFragment_();
            case 3: return new MapFragment_();
            default:
                throw new IndexOutOfBoundsException(String.valueOf(position));
        }
    }

    @Override
    public int getCount() {
        return FRAGMENTS_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return names[position];
    }
}