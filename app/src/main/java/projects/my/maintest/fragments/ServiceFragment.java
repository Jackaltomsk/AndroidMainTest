package projects.my.maintest.fragments;


import android.app.Fragment;

import org.androidannotations.annotations.EFragment;

import projects.my.maintest.R;

/**
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.fragment_service)
public class ServiceFragment extends Fragment implements FragmentCommon {

    public ServiceFragment() {
        // Required empty public constructor
    }

    @Override
    public CharSequence getTitle() {
        return "Service";
    }
}
