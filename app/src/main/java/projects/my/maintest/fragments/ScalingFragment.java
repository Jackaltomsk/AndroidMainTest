package projects.my.maintest.fragments;


import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;

import projects.my.maintest.R;
import projects.my.maintest.activities.MainActivity;

/**
 * Фрагмент для работы с изображениями.
 */
@EFragment(R.layout.fragment_scaling)
public class ScalingFragment extends Fragment implements FragmentCommon {

    @Override
    public CharSequence getTitle() {
        return "Scaling";
    }

    @Click(R.id.btnScalingGallery)
    void galleryClick() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        getActivity().startActivityForResult(galleryIntent, Constants.REQ_CODE_IMG_FROM_GALLERY);
    }

    @Click(R.id.btnScalingCamera)
    void cameraClick() {
        requestPermissions();
        PackageManager pm = getActivity().getPackageManager();
        if (pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            getActivity().startActivityForResult(cameraIntent, Constants.REQ_CODE_IMG_FROM_CAMERA);
        }
        else Toast.makeText(getActivity(), R.string.toast_no_camera_found, Toast.LENGTH_SHORT)
                .show();
    }

    private void requestPermissions() {
        // Запросим подтверждение у пользователя, если у нас >= 23.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Activity act = getActivity();
            if (ContextCompat.checkSelfPermission(act, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(act,
                        new String[]{ Manifest.permission.CAMERA },
                        MainActivity.PERMISSIONS_REQUEST_CAMERA);
            }
        }
    }
}