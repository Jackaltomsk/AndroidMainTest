package projects.my.maintest.fragments;


import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.j256.ormlite.dao.Dao;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import java.sql.SQLException;

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

    private static final String TAG = ListFragment.class.getSimpleName();

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
        final ListItem model = new ListItem();
        ListItemPopup popup = new ListItemPopup(getActivity(), model);
        popup.setOnModelChangeListener(new ListItemPopup.OnModelChangeListener() {
            @Override
            public void onModelChange() {
                try {
                    itemDao.createOrUpdate(model);
                    adapter.notifyDataSetChanged();
                } catch (SQLException e) {
                    Log.e(TAG, "Ошибка сохранения модели: " + e);
                }
            }
        });
        popup.show(this.getView());
    }
}