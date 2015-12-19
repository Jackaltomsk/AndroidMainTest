package projects.my.maintest.fragments;


import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import projects.my.maintest.R;
import projects.my.maintest.adapters.ListItemsAdapter;
import projects.my.maintest.db.dao.GenericDao;
import projects.my.maintest.db.infrastructure.DbManager;
import projects.my.maintest.db.models.ListItem;
import projects.my.maintest.popups.ListItemPopup;

/**
 * Фрагмент со списком значений.
 */
@EFragment(R.layout.fragment_list)
@OptionsMenu(R.menu.fragment_list)
public class ListFragment extends Fragment implements FragmentCommon {

    @Override
    public CharSequence getTitle() {
        return "List";
    }

    @ViewById
    RecyclerView listRecyclerView;
    ListItemsAdapter adapter;
    private GenericDao<ListItem> itemDao;

    @AfterViews
    void init() {
        itemDao = DbManager.getDbContext().getGenericDao(ListItem.class);
        adapter = new ListItemsAdapter(itemDao);
        listRecyclerView.setHasFixedSize(true);
        listRecyclerView.setVerticalScrollBarEnabled(true);
        listRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));
        listRecyclerView.setAdapter(adapter);
    }

    @OptionsItem(R.id.menuFragmentListAdd)
    void addItem() {
        ListItemPopup popup = new ListItemPopup(getActivity(), new ListItem());
        popup.showAtLocation(this.getView(), Gravity.TOP, 10, 10);
    }
}