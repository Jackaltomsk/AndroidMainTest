package projects.my.maintest.volley;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.androidannotations.annotations.EBean;

/**
 * Синглтон очереди запросов Volley.
 */
@EBean(scope = EBean.Scope.Singleton)
public class QueueHolder {
    private RequestQueue requestQueue;
    private Context ctx;

    QueueHolder(Context context) {
        ctx = context;
        requestQueue = Volley.newRequestQueue(ctx);
        requestQueue.start();
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        requestQueue.add(req);
    }

    public Context getContext() {
        return ctx;
    }
}