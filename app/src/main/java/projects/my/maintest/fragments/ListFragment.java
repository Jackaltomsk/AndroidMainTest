package projects.my.maintest.fragments;


import android.app.Fragment;
import android.support.v7.widget.RecyclerView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import projects.my.maintest.R;

/**
 * Фрагмент со списком значений.
 */
@EFragment(R.layout.fragment_list)
public class ListFragment extends Fragment implements FragmentCommon {

    @Override
    public CharSequence getTitle() {
        return "List";
    }

    @ViewById
    RecyclerView listRecyclerView;

    @AfterViews
    void init() {

    }
}
