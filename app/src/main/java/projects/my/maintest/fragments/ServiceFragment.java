package projects.my.maintest.fragments;


import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.stanfy.gsonxml.GsonXml;
import com.stanfy.gsonxml.GsonXmlBuilder;
import com.stanfy.gsonxml.XmlParserCreator;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import projects.my.maintest.R;
import projects.my.maintest.adapters.ListItemsAdapter;
import projects.my.maintest.adapters.ServiceItemsAdapter;
import projects.my.maintest.db.infrastructure.DbManager;
import projects.my.maintest.db.models.ListItem;
import projects.my.maintest.volley.QueueHolder;
import projects.my.maintest.volley.models.Result;

/**
 * Фрагмент отображения рассылки, загруженной сервисом.
 */
@EFragment(R.layout.fragment_service)
public class ServiceFragment extends Fragment implements FragmentCommon {

    private static final String TAG = ServiceFragment.class.getSimpleName();

    @ViewById
    Button btnStartServiceRead;

    @ViewById
    RecyclerView listServiceRecyclerView;
    ServiceItemsAdapter adapter;

    @Bean
    QueueHolder queueHolder;

    @Override
    public CharSequence getTitle() {
        return "Service";
    }

    @AfterViews
    void init() {
        //adapter = new ListItemsAdapter(itemDao);

        listServiceRecyclerView.setHasFixedSize(true);
        listServiceRecyclerView.setVerticalScrollBarEnabled(true);
        listServiceRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));
        //listServiceRecyclerView.setAdapter(adapter);
    }

    @Click(R.id.btnStartServiceRead)
    void readFeed() {
        queueHolder.addToRequestQueue(new StringRequest("http://storage.space-o.ru/testXmlFeed.xml",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        XmlParserCreator parserCreator = new XmlParserCreator() {
                            @Override
                            public XmlPullParser createParser() {
                                try {
                                    return XmlPullParserFactory.newInstance().newPullParser();
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        };

                        try {
                            String decoded = URLDecoder.decode(URLEncoder.encode(response, "iso8859-1"), "UTF-8");
                            GsonXml gsonXml = new GsonXmlBuilder()
                                    .setXmlParserCreator(parserCreator)
                                    .create();
                            Result model = gsonXml.fromXml(decoded, Result.class);
                            adapter = new ServiceItemsAdapter(model);
                            listServiceRecyclerView.setAdapter(adapter);
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Ошибка запроса данных: " + error);
            }
        }));
    }
}
