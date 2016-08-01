package com.tonikamitv.loginregister;

import static com.tonikamitv.loginregister.Constants.UAT_MAIN_BUILDING_ID;
import static com.tonikamitv.loginregister.Constants.UAT_MAIN_BUILDING_LATITUDE;
import static com.tonikamitv.loginregister.Constants.UAT_MAIN_BUILDING_LONGITUDE;
import static com.tonikamitv.loginregister.Constants.UAT_MAIN_BUILDING_RADIUS_METERS;
import static com.tonikamitv.loginregister.Constants.CONNECTION_FAILURE_RESOLUTION_REQUEST;
import static com.tonikamitv.loginregister.Constants.GEOFENCE_EXPIRATION_TIME;
import static com.tonikamitv.loginregister.Constants.TAG;
import static com.tonikamitv.loginregister.Constants.UAT_FOUNDERS_ID;
import static com.tonikamitv.loginregister.Constants.UAT_FOUNDERS_LATITUDE;
import static com.tonikamitv.loginregister.Constants.UAT_FOUNDERS_LONGITUDE;
import static com.tonikamitv.loginregister.Constants.UAT_FOUNDERS_RADIUS_METERS;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.system.ErrnoException;
import android.util.Log;
import android.widget.Toast;

import com.google.ads.AdRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.xml.sax.ErrorHandler;

import java.util.ArrayList;
import java.util.List;

public class GeofenceActivity extends Activity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    // Internal List of Geofence objects. In a real app, these might be provided by an API based on
    // locations within the user's proximity.
    List<Geofence> mGeofenceList;

    // These will store hard-coded geofences in this sample app.
    private SimpleGeofence mUATMainGeofence;
    private SimpleGeofence mUATFoundersGeofence;

    // Persistent storage for geofences.
    private SimpleGeofenceStore mGeofenceStorage;

    private LocationServices mLocationService;
    // Stores the PendingIntent used to request geofence monitoring.
    private PendingIntent mGeofenceRequestIntent;
    private GoogleApiClient mApiClient;

    // Defines the allowable request types (in this example, we only add geofences).
    private enum REQUEST_TYPE {ADD}
    private REQUEST_TYPE mRequestType;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Rather than displayng this activity, simply display a toast indicating that the geofence
        // service is being created. This should happen in less than a second.
        if (!isGooglePlayServicesAvailable()) {
            Log.e(TAG, "Google Play services unavailable.");
            finish();
            return;
        }

        mApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        mApiClient.connect();

        // Instantiate a new geofence storage area.
        mGeofenceStorage = new SimpleGeofenceStore(this);
        // Instantiate the current List of geofences.
        mGeofenceList = new ArrayList<Geofence>();
        createGeofences();

        setUpMapIfNeeded();

        // create marker
        MarkerOptions marker1 = new MarkerOptions().position(
                new LatLng(UAT_MAIN_BUILDING_LATITUDE, UAT_MAIN_BUILDING_LONGITUDE)).title("UAT Main Buliding Hotspot");

        MarkerOptions marker2 = new MarkerOptions().position(
                new LatLng(UAT_FOUNDERS_LATITUDE, UAT_FOUNDERS_LONGITUDE)).title("UAT Founders Hotspot");

        // Changing marker icon
        marker1.icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_ROSE));

        marker2.icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_ROSE));

        // adding marker1
        mMap.addMarker(marker1);
        CameraPosition cameraPosition1 = new CameraPosition.Builder()
                .target(new LatLng(33.377827, -111.976005)).zoom(19).build();
        mMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition1));


        // adding marker2
        mMap.addMarker(marker2);
        CameraPosition cameraPosition2 = new CameraPosition.Builder()
                .target(new LatLng(33.377191, -111.975874)).zoom(19).build();
        mMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition2));
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        if (mMap != null) {
            Log.e(TAG, "Loading Map");
        }
        mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        if (mMap == null) {
            Log.e(TAG, "Error");
        }
    }

/**
 * In this sample, the geofences are predetermined and are hard-coded here. A real app might
 * dynamically create geofences based on the user's location.
 */
    public void createGeofences() {
        // Create internal "flattened" objects containing the geofence data.
        mUATMainGeofence = new SimpleGeofence(
                UAT_MAIN_BUILDING_ID,                // geofenceId.
                UAT_MAIN_BUILDING_LATITUDE,
                UAT_MAIN_BUILDING_LONGITUDE,
                UAT_MAIN_BUILDING_RADIUS_METERS,
                GEOFENCE_EXPIRATION_TIME,
                Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT
        );
        mUATFoundersGeofence = new SimpleGeofence(
                UAT_FOUNDERS_ID,                // geofenceId.
                UAT_FOUNDERS_LATITUDE,
                UAT_FOUNDERS_LONGITUDE,
                UAT_FOUNDERS_RADIUS_METERS,
                GEOFENCE_EXPIRATION_TIME,
                Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT
        );

        // Store these flat versions in SharedPreferences and add them to the geofence list.
        mGeofenceStorage.setGeofence(UAT_MAIN_BUILDING_ID, mUATMainGeofence);
        mGeofenceStorage.setGeofence(UAT_FOUNDERS_ID, mUATFoundersGeofence);
        mGeofenceList.add(mUATMainGeofence.toGeofence());
        mGeofenceList.add(mUATFoundersGeofence.toGeofence());
    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // If the error has a resolution, start a Google Play services activity to resolve it.
        if (connectionResult.hasResolution()) {
            try {
                connectionResult.startResolutionForResult(this,
                        CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                Log.e(TAG, "Exception while resolving connection error.", e);
            }
        } else {
            int errorCode = connectionResult.getErrorCode();
            Log.e(TAG, "Connection to Google Play services failed with error code " + errorCode);
        }
    }

    /**
     * Once the connection is available, send a request to add the Geofences.
     */
    @Override
    public void onConnected(Bundle connectionHint) {
        // Get the PendingIntent for the geofence monitoring request.
        // Send a request to add the current geofences.
        mGeofenceRequestIntent = getGeofenceTransitionPendingIntent();
        LocationServices.GeofencingApi.addGeofences(mApiClient, mGeofenceList,
                mGeofenceRequestIntent);
        Toast.makeText(this, getString(R.string.start_geofence_service), Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onConnectionSuspended(int i) {
        if (null != mGeofenceRequestIntent) {
            LocationServices.GeofencingApi.removeGeofences(mApiClient, mGeofenceRequestIntent);
        }
    }


    /**
     * Checks if Google Play services is available.
     * @return true if it is.
     */
    private boolean isGooglePlayServicesAvailable() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == resultCode) {
            if (Log.isLoggable(TAG, Log.DEBUG)) {
                Log.d(TAG, "Google Play services is available.");
            }
            return true;
        } else {
            Log.e(TAG, "Google Play services is unavailable.");
            return false;
        }
    }

    /**
     * Create a PendingIntent that triggers GeofenceTransitionIntentService when a geofence
     * transition occurs.
     */
    private PendingIntent getGeofenceTransitionPendingIntent() {
        Intent intent = new Intent(this, GeofenceTransitionsIntentService.class);
        return PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

}