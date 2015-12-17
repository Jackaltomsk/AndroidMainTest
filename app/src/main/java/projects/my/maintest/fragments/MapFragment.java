package projects.my.maintest.fragments;


import android.app.Fragment;

import org.androidannotations.annotations.EFragment;

import projects.my.maintest.R;

/**
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.fragment_map)
public class MapFragment extends Fragment implements FragmentCommon {

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public CharSequence getTitle() {
        return "Map";
    }
}
