package projects.my.maintest.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import projects.my.maintest.R;

@EActivity(R.layout.activity_splash)
/**
 * Активити стартового экрана.
 */
public class SplashActivity extends AppCompatActivity {

    @ViewById(R.id.splashId)
    View layout;

    @AfterViews
    void init() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.splah_fade_in);
        final Context context = this;
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(context, MainActivity_.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        layout.startAnimation(anim);
    }
}