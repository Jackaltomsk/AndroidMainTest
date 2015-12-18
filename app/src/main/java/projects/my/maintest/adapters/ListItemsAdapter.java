package projects.my.maintest.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import projects.my.maintest.R;
import projects.my.maintest.db.dao.GenericDao;
import projects.my.maintest.db.dao.extensions.ListItemExtension;
import projects.my.maintest.db.infrastructure.DbManager;
import projects.my.maintest.db.models.ListItem;

/**
 * Адаптер элементов списка.
 */
public class ListItemsAdapter extends RecyclerView.Adapter<ListItemsAdapter.ViewHolder> {
    private static final String TAG = ListItemsAdapter.class.getSimpleName();

    private ListItem[] dataset;
    private GenericDao<ListItem> itemDao;
    private ListItemExtension ext;
    private Activity activity;
    private RecyclerView.OnScrollListener viewScrollListener;


    public ListItemsAdapter() {
        itemDao = DbManager.getDbContext().getGenericDao(ListItem.class);
        ext = new ListItemExtension(itemDao);
        dataset = ext.getSavedItems();
    }
    /*private void updateDataset(boolean isFirstPage, boolean getNext) {
        GalleryImage[] data = getNext ? paginator.getNextPage() : paginator.getPrevPage();
        if (data != null) dataset = data;

        if (isFirstPage) this.notifyDataSetChanged();
        else this.notifyItemRangeChanged(0, Paginator.ITEMS_PER_PAGE);
    }*/

    /**
     * Данные элемента списка.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout itemView;
        private ImageView imageView;
        private TextView textView;
        private CheckBox checkBox;

        public ViewHolder(LinearLayout v) {
            super(v);
            itemView = v;
            imageView = (ImageView) v.findViewById(R.id.recyclerItemIcon);
            textView = (TextView) v.findViewById(R.id.recyclerItemText);
            checkBox = (CheckBox) v.findViewById(R.id.recyclerItemCheckBox);
            createListeners();
        }

        private void createListeners() {
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    changeIcon(isChecked);
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        void changeIcon(boolean isChecked) {
            this.imageView.setImageResource(isChecked ?
                    R.drawable.ic_star_black_24dp : R.drawable.ic_star_border_black_24dp);
        }
    }

    /*public void init(ImageLoader imgLoader, Activity act) {
        imageLoader = imgLoader;
        activity = act;
        paginator.setImgDatasetUpdatedListener(this);
        paginator.fetchImages();
        viewScrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                GridLayoutManager lmgr = (GridLayoutManager) recyclerView.getLayoutManager();
                if (dy > 0) //check for scroll down
                {
                    int lastItemPos = lmgr.findLastCompletelyVisibleItemPosition();
                    int itemCount = lmgr.getItemCount();

                    if (lastItemPos == (itemCount - 1)) {
                        Log.v(TAG, "Достигнут конец страницы.");
                        updateDataset(false, true);
                        recyclerView.scrollToPosition(0);
                        Toast.makeText(activity, "Страница " + paginator.getUiPage(),
                                Toast.LENGTH_SHORT).show();
                    }
                }
                else if (dy < 0) {
                    if (lmgr.findFirstCompletelyVisibleItemPosition() == 0) {
                        Log.v(TAG, "Достигнуто начало страницы.");
                        updateDataset(false, false);
                        if (paginator.getUiPage() > 0) {
                            recyclerView.scrollToPosition(Paginator.ITEMS_PER_PAGE - 1);
                        }
                        Toast.makeText(activity, "Страница " + paginator.getUiPage(),
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };
    }*/

    @Override
    public ListItemsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_recycler, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ListItem model = dataset[position];
        holder.textView.setText(model.getText());
        holder.checkBox.setChecked(model.isChecked());
        holder.changeIcon(model.isChecked());
    }

    @Override
    public int getItemCount() {
        return dataset.length;
    }

    public RecyclerView.OnScrollListener getViewScrollListener() {
        return viewScrollListener;
    }
}
