package projects.my.maintest.fragments;


import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;

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
@SuppressWarnings("WeakerAccess")
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
        adapter.setClickListener(new ListItemsAdapter.OnClickListener() {
            @Override
            public void onClick(int modelId) {
                editPopup(modelId);
            }

            @Override
            public void onLongClick(View v, final int modelId) {
                registerForContextMenu(v);
                v.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
                    @Override
                    public void onCreateContextMenu(ContextMenu menu, View v,
                                                    ContextMenu.ContextMenuInfo menuInfo) {
                        MenuItem edit = menu.add(R.string.context_menu_item_edit);
                        final MenuItem delete = menu.add(R.string.context_menu_item_delete);
                        edit.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                editPopup(modelId);
                                return true;
                            }
                        });
                        delete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                deleteElement(modelId);
                                return true;
                            }
                        });
                    }
                });
            }
        });
        listRecyclerView.setHasFixedSize(true);
        listRecyclerView.setVerticalScrollBarEnabled(true);
        listRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));
        listRecyclerView.setAdapter(adapter);
    }

    @OptionsItem(R.id.menuFragmentListAdd)
    void addItem() {
        createPopup(new ListItem());
    }

    private void createPopup(final ListItem model) {
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

    private void editPopup(int modelId) {
        try {
            ListItem model = itemDao.queryForId(modelId);
            createPopup(model);
        } catch (SQLException e) {
            Log.e(TAG, "Ошибка получения значения по идентификатору" +
                    String.valueOf(modelId));
        }
    }

    private void deleteElement(int modelId) {
        try {
            ListItem model = itemDao.queryForId(modelId);
            itemDao.delete(model);
            adapter.notifyDataSetChanged();
        } catch (SQLException e) {
            Log.e(TAG, "Ошибка получения значения по идентификатору" +
                    String.valueOf(modelId));
        }
    }
}