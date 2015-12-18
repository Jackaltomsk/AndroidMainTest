package projects.my.maintest.fragments;


import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import projects.my.maintest.R;
import projects.my.maintest.adapters.ListItemsAdapter;

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
    ListItemsAdapter adapter;

    @AfterViews
    void init() {
        adapter = new ListItemsAdapter();
        listRecyclerView.setHasFixedSize(true);
        listRecyclerView.setVerticalScrollBarEnabled(true);
        listRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));
        listRecyclerView.setAdapter(adapter);
    }
}