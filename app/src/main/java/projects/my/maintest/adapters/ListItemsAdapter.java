package projects.my.maintest.adapters;

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
import projects.my.maintest.db.models.ListItem;

/**
 * Адаптер элементов списка.
 */
public class ListItemsAdapter extends RecyclerView.Adapter<ListItemsAdapter.ViewHolder> {
    private static final String TAG = ListItemsAdapter.class.getSimpleName();
    private ListItemExtension ext;
    private OnClickListener clickListener;

    /**
     * Интерфейс слушателя событий клика по элементу списка.
     */
    public interface OnClickListener {
        /**
         * Обычный клик.
         * @param modelId Идентификатор модели, отождествленной с элементом.
         */
        void onClick(int modelId);

        /**
         * Долгий клик.
         * @param v Вью, на котором произошло событие.
         * @param modelId Идентификатор модели, отождествленной с элементом.
         */
        void onLongClick(View v, int modelId);
    }

    public ListItemsAdapter(GenericDao<ListItem> itemDao) {
        ext = new ListItemExtension(itemDao);
    }

    /**
     * Данные элемента списка.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout itemView;
        private ImageView imageView;
        private TextView textView;
        private CheckBox checkBox;
        private int modelId;
        private OnClickListener clickListener;

        public ViewHolder(LinearLayout v, OnClickListener listener) {
            super(v);
            itemView = v;
            imageView = (ImageView) v.findViewById(R.id.recyclerItemIcon);
            textView = (TextView) v.findViewById(R.id.recyclerItemText);
            checkBox = (CheckBox) v.findViewById(R.id.recyclerItemCheckBox);
            clickListener = listener;
            createListeners();
        }

        private void createListeners() {
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    ext.saveIsChecked(ViewHolder.this.modelId, isChecked);
                    changeIcon(isChecked);
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onClick(modelId);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    clickListener.onLongClick(v, modelId);
                    return false;
                }
            });
        }

        void changeIcon(boolean isChecked) {
            this.imageView.setImageResource(isChecked ?
                    R.drawable.ic_star_black_24dp : R.drawable.ic_star_border_black_24dp);
        }
    }

    @Override
    public ListItemsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_recycler, parent, false);
        return new ViewHolder(v, clickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ListItem model = ext.getItemAt(position);
        holder.modelId = model.getId();
        holder.textView.setText(model.getText());
        holder.checkBox.setChecked(model.isChecked());
        holder.changeIcon(model.isChecked());
    }

    @Override
    public int getItemCount() {
        return ext.getCount();
    }

    /**
     * Устанавливает обработчик осбытий по клику и долгому клику на элемент списка.
     * @param clickListener Обработчик событий кликов.
     */
    public void setClickListener(OnClickListener clickListener) {
        this.clickListener = clickListener;
    }
}
