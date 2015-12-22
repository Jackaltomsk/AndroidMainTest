package projects.my.maintest.activities;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import projects.my.maintest.R;
import projects.my.maintest.common.ActivityUtils;

@EActivity(R.layout.activity_scaling)
public class ScalingActivity extends AppCompatActivity {

    @ViewById
    WebView scalingWebView;

    @AfterViews
    void init() {
        ActivityUtils.setToolbar(this, true);
        scalingWebView.getSettings().setBuiltInZoomControls(true);
        scalingWebView.getSettings().setDisplayZoomControls(true);
        processIntent();
    }

    private void processIntent() {
        Intent intent = getIntent();
        Uri uri = intent.getData();
        if (uri != null) {
            String imagePath = "";
            String[] imgData = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(uri, imgData, null, null, null);

            if (cursor != null) {
                int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                imagePath = cursor.getString(index);
            }
            else imagePath = uri.getPath();
            scalingWebView.loadUrl("file:///" + imagePath);
        }
    }
}
