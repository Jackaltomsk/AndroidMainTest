package projects.my.maintest.fragments;


import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import projects.my.maintest.R;
import projects.my.maintest.activities.MainActivity;

/**
 * Фрагмент карты и текста с координатами.
 */
@EFragment(R.layout.fragment_location)
public class LocationFragment extends Fragment implements FragmentCommon {
    private static final String TAG = LocationFragment.class.getSimpleName();

    @ViewById
    TextView mapCoordinatesText;

    @ViewById
    MapView mapView;

    private Marker myLocationMarker;

    @Override
    public CharSequence getTitle() {
        return "Map";
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @AfterViews
    void init() {
        mapView.onCreate(null);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final GoogleMap googleMap) {
                requestPermissions();
                UiSettings settings = googleMap.getUiSettings();
                settings.setZoomControlsEnabled(true);
                settings.setMyLocationButtonEnabled(true);
                try {
                    googleMap.setMyLocationEnabled(true);
                } catch (SecurityException ex) {
                    Log.e(TAG, "Недостаточно прав для определения местоположения: ", ex);
                }

                final LocationManager service = (LocationManager) LocationFragment.this.getActivity()
                        .getSystemService(Context.LOCATION_SERVICE);
                boolean enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);
                if (!enabled) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }
                googleMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                    @Override
                    public void onMyLocationChange(Location location) {
                        LatLng coords = new LatLng(location.getLatitude(), location.getLongitude());
                        setCoordinates(coords.latitude, coords.longitude);
                        if (myLocationMarker != null) myLocationMarker.remove();
                        myLocationMarker = googleMap.addMarker(new MarkerOptions()
                                .position(coords));
                        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(coords, 13);
                        googleMap.animateCamera(cameraUpdate);
                    }
                });

                googleMap.setOnMyLocationButtonClickListener(
                        new GoogleMap.OnMyLocationButtonClickListener() {
                            @Override
                            public boolean onMyLocationButtonClick() {
                                return locationButtonLogic(googleMap, service);
                            }
                        }
                );

                // Определим местоположение
                locationButtonLogic(googleMap, service);
            }
        });
    }

    @SuppressWarnings("deprecation")
    private boolean locationButtonLogic(GoogleMap googleMap, LocationManager service) {
        try {
            Location location = googleMap.getMyLocation();

            if (location == null) {
                Criteria criteria = new Criteria();
                String provider = service.getBestProvider(criteria, false);
                location = service.getLastKnownLocation(provider);
            }

            LatLng coords;

            if (location == null) {
                // внесем координаты по умолчанию
                coords = new LatLng(56.455157, 84.951246);
            }
            else {
                coords = new LatLng(location.getLatitude(),
                        location.getLongitude());
            }

            setCoordinates(coords.latitude, coords.longitude);
            MarkerOptions markerOpts = new MarkerOptions();
            if (myLocationMarker != null) myLocationMarker.remove();
            myLocationMarker = googleMap.addMarker(markerOpts
                    .position(coords));
            CameraUpdate cameraUpdate = CameraUpdateFactory
                    .newLatLngZoom(coords, 13);
            googleMap.animateCamera(cameraUpdate);
        }
        catch (SecurityException ex) {
            Log.e(TAG, "Недостаточно прав для определения местоположения: ", ex);
        }
        catch (Exception ex) {
            Log.e(TAG, "Ошибка определения местоположения: ", ex);
        }
        return true;
    }

    private void requestPermissions() {
        // Запросим подтверждение у пользователя, если у нас >= 23.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Activity act = getActivity();
            if (ContextCompat.checkSelfPermission(act, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(act,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MainActivity.PERMISSIONS_REQUEST_FINE_LOCATION);
            }
        }
    }

    public void setCoordinates(double latitude, double longitude) {
        String text = String.format("lat: %f, long: %f", latitude, longitude);
        mapCoordinatesText.setText(text);
    }
}
