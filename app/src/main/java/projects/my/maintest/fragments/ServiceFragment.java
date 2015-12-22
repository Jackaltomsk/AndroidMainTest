package projects.my.maintest.fragments;


import android.app.Fragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.ViewById;

import projects.my.maintest.R;
import projects.my.maintest.adapters.ServiceItemsAdapter;
import projects.my.maintest.services.FeedService;
import projects.my.maintest.services.FeedServiceContract;
import projects.my.maintest.services.FeedService_;
import projects.my.maintest.services.OnFeedRecievedListener;
import projects.my.maintest.volley.models.Result;

/**
 * Фрагмент отображения рассылки, загруженной сервисом.
 */
@EFragment(R.layout.fragment_service)
public class ServiceFragment extends Fragment implements FragmentCommon {
    private static final String TAG = ServiceFragment.class.getSimpleName();
    private boolean bound;
    private ServiceConnection serviceConnection;
    private FeedServiceContract feedService;

    @ViewById
    Button btnStartServiceRead;

    @ViewById
    RecyclerView listServiceRecyclerView;
    ServiceItemsAdapter adapter;

    @ViewById
    ProgressBar serviceProgressBar;

    @InstanceState
    boolean isFeedAcquired;

    @Override
    public CharSequence getTitle() {
        return "Service";
    }

    @AfterViews
    void init() {
        createServiceBinding();
        listServiceRecyclerView.setHasFixedSize(true);
        listServiceRecyclerView.setVerticalScrollBarEnabled(true);
        listServiceRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (bound) {
            feedService.setFeedRecievedListener(null);
            getActivity().unbindService(serviceConnection);
        }
    }

    @Click(R.id.btnStartServiceRead)
    void readFeed() {
        serviceProgressBar.setVisibility(View.VISIBLE);
        btnStartServiceRead.setEnabled(false);
        feedService.requestFeed();
    }

    /**
     * Реализует запуск и биндинг сервиса.
     */
    private void createServiceBinding() {
        Intent intent = new Intent(getActivity(), FeedService_.class);
        getActivity().startService(intent);
        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName className, IBinder service) {
                FeedService.FeedBinder binder = (FeedService.FeedBinder) service;
                feedService = binder.getService();
                feedService.setFeedRecievedListener(new OnFeedRecievedListener() {
                    @Override
                    public void OnFeedRecieved(Result data) {
                        if (data != null) {
                            adapter = new ServiceItemsAdapter(data);
                            listServiceRecyclerView.setAdapter(adapter);
                            isFeedAcquired = true;
                        }
                        else Toast.makeText(getActivity(), R.string.toast_feed_error_loading,
                                Toast.LENGTH_SHORT).show();
                        serviceProgressBar.setVisibility(View.INVISIBLE);
                        btnStartServiceRead.setEnabled(true);
                    }
                });
                bound = true;
                boolean isInProgress = feedService.isInProgess();
                btnStartServiceRead.setEnabled(!isInProgress);
                serviceProgressBar.setVisibility(isInProgress ? View.VISIBLE : View.INVISIBLE);

                // Прочитаем данные заново.
                if (isFeedAcquired) {
                    ServiceFragment.this.readFeed();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName arg0) {
                bound = false;
                if (btnStartServiceRead != null) btnStartServiceRead.setEnabled(bound);
            }
        };
        getActivity().bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }
}
