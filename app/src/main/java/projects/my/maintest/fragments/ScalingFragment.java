package projects.my.maintest.fragments;


import android.Manifest;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.MediaStore;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;

import projects.my.maintest.R;
import projects.my.maintest.activities.MainActivity;
import projects.my.maintest.common.ActivityUtils;

/**
 * Фрагмент для работы с изображениями.
 */
@EFragment(R.layout.fragment_scaling)
public class ScalingFragment extends Fragment implements FragmentCommon {

    private static final String TAG = ScalingFragment.class.getSimpleName();

    @Override
    public CharSequence getTitle() {
        return "Scaling";
    }

    @AfterViews
    void init() {
        ActivityUtils.requestPermissions(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE,
                MainActivity.PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        ActivityUtils.requestPermissions(getActivity(), Manifest.permission.CAMERA,
                MainActivity.PERMISSIONS_REQUEST_CAMERA);
    }

    @Click(R.id.btnScalingGallery)
    void galleryClick() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        getActivity().startActivityForResult(galleryIntent, Constants.REQ_CODE_IMG_FROM_GALLERY);
    }

    @Click(R.id.btnScalingCamera)
    void cameraClick() {
        PackageManager pm = getActivity().getPackageManager();
        if (pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            // Будем получать только тамб вместо полноразмерного изображения.
            // При добавлении extra приходит null intent.
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            getActivity().startActivityForResult(cameraIntent, Constants.REQ_CODE_IMG_FROM_CAMERA);
        }
        else Toast.makeText(getActivity(), R.string.toast_no_camera_found, Toast.LENGTH_SHORT)
                .show();
    }
}