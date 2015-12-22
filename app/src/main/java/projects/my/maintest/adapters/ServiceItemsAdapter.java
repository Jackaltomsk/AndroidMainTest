package projects.my.maintest.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import projects.my.maintest.R;
import projects.my.maintest.volley.models.Quote;
import projects.my.maintest.volley.models.Result;

/**
 * Адаптер элементов списка, данные для которого получаются сервисом.
 */
public class ServiceItemsAdapter extends RecyclerView.Adapter<ServiceItemsAdapter.ViewHolder> {
    private static final String TAG = ServiceItemsAdapter.class.getSimpleName();
    private Result data;

    public ServiceItemsAdapter(Result data) {
        this.data = data;
    }

    /**
     * Данные элемента списка.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView idView;
        private TextView dateView;
        private TextView textView;

        public ViewHolder(LinearLayout v) {
            super(v);
            idView = (TextView) v.findViewById(R.id.serviceRecyclerItemId);
            dateView = (TextView) v.findViewById(R.id.serviceRecyclerItemDate);
            textView = (TextView) v.findViewById(R.id.serviceRecyclerItemText);
        }
    }

    @Override
    public ServiceItemsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_recycler_service, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Quote model = data.getQuoteAt(position);
        holder.idView.setText(String.valueOf(model.getId()));
        holder.dateView.setText(model.getDate());
        holder.textView.setText(model.getText());
    }

    @Override
    public int getItemCount() {
        return data.getQuotesCount();
    }
}