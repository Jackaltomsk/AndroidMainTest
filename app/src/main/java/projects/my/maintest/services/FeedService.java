package projects.my.maintest.services;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.JsonSyntaxException;
import com.stanfy.gsonxml.GsonXml;
import com.stanfy.gsonxml.GsonXmlBuilder;
import com.stanfy.gsonxml.XmlParserCreator;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EService;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import projects.my.maintest.volley.QueueHolder;
import projects.my.maintest.volley.models.Result;

/**
 * Сервис загрузки рассылки.
 */
@SuppressLint("Registered")
@SuppressWarnings("WeakerAccess")
@EService
public class FeedService extends Service implements FeedServiceContract {
    private static final String TAG = FeedService.class.getSimpleName();
    private static final int REQUEST_TIMEOUT_MS = 15000;

    private final FeedBinder feedBinder = new FeedBinder();
    private OnFeedRecievedListener feedRecievedListener;
    private boolean isInProgressFlag;

    @Bean
    QueueHolder queueHolder;

    public class FeedBinder extends Binder {
        public FeedService getService() {
            return FeedService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return feedBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void setFeedRecievedListener(OnFeedRecievedListener feedRecievedListener) {
        this.feedRecievedListener = feedRecievedListener;
    }

    @Override
    public boolean isInProgess() {
        return isInProgressFlag;
    }

    @Override
    public void requestFeed() {
        if (isInProgressFlag) return; // если запрос все еще в работе, не будем создавать нового.
        isInProgressFlag = true;
        StringRequest request = new StringRequest("http://storage.space-o.ru/testXmlFeed.xml",
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

                        Result model = null;
                        try {
                            String decoded = URLDecoder.decode(URLEncoder.encode(response,
                                    "iso8859-1"), "UTF-8");
                            GsonXml gsonXml = new GsonXmlBuilder()
                                    .setXmlParserCreator(parserCreator)
                                    .create();
                            model = gsonXml.fromXml(decoded, Result.class);
                        }
                        catch (UnsupportedEncodingException e) {
                            Log.e(TAG, "Ошибка изменения кодировки: " + e);
                        }
                        catch (JsonSyntaxException e) {
                            Log.e(TAG, "Ошибка парсинга xml: " + e);
                        }
                        finally {
                            isInProgressFlag = false;
                            if (feedRecievedListener != null) {
                                feedRecievedListener.OnFeedRecieved(model);
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Ошибка запроса данных: " + error);
                isInProgressFlag = false;
                if (feedRecievedListener != null) {
                    feedRecievedListener.OnFeedRecieved(null);
                }
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(REQUEST_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queueHolder.addToRequestQueue(request);
    }
}
