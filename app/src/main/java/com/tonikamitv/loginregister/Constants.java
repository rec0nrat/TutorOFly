package com.tonikamitv.loginregister;

import android.net.Uri;

import com.google.android.gms.location.Geofence;

/**
 * Created by cbilb on 7/26/2016.
 */
public final class Constants {

    private Constants() {
    }

    public static final String TAG = "ExampleGeofencingApp";

    // Request code to attempt to resolve Google Play services connection failures.
    public final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    // Timeout for making a connection to GoogleApiClient (in milliseconds).
    public static final long CONNECTION_TIME_OUT_MS = 100;

    // For the purposes of this demo, the geofences are hard-coded and should not expire.
    // An app with dynamically-created geofences would want to include a reasonable expiration time.
    public static final long GEOFENCE_EXPIRATION_TIME = Geofence.NEVER_EXPIRE;

    // Geofence parameters for the Android building on Google's main campus in Mountain View.
    public static final String UAT_MAIN_BUILDING_ID = "1";
    public static final double UAT_MAIN_BUILDING_LATITUDE = 33.377827;
    public static final double UAT_MAIN_BUILDING_LONGITUDE = -111.976005;
    public static final float UAT_MAIN_BUILDING_RADIUS_METERS = 60.0f;

    // Geofence parameters for the Yerba Buena Gardens near the Moscone Center in San Francisco.
    public static final String UAT_FOUNDERS_ID = "2";
    public static final double UAT_FOUNDERS_LATITUDE = 33.377191;
    public static final double UAT_FOUNDERS_LONGITUDE = -111.975874;
    public static final float UAT_FOUNDERS_RADIUS_METERS = 72.0f;


    // The constants below are less interesting than those above.

    // Path for the DataItem containing the last geofence id entered.
    public static final String GEOFENCE_DATA_ITEM_PATH = "/geofenceid";
    public static final Uri GEOFENCE_DATA_ITEM_URI =
            new Uri.Builder().scheme("wear").path(GEOFENCE_DATA_ITEM_PATH).build();
    public static final String KEY_GEOFENCE_ID = "geofence_id";

    // Keys for flattened geofences stored in SharedPreferences.
    public static final String KEY_LATITUDE = "com.example.wearable.geofencing.KEY_LATITUDE";
    public static final String KEY_LONGITUDE = "com.example.wearable.geofencing.KEY_LONGITUDE";
    public static final String KEY_RADIUS = "com.example.wearable.geofencing.KEY_RADIUS";
    public static final String KEY_EXPIRATION_DURATION =
            "com.example.wearable.geofencing.KEY_EXPIRATION_DURATION";
    public static final String KEY_TRANSITION_TYPE =
            "com.example.wearable.geofencing.KEY_TRANSITION_TYPE";
    // The prefix for flattened geofence keys.
    public static final String KEY_PREFIX = "com.example.wearable.geofencing.KEY";

    // Invalid values, used to test geofence storage when retrieving geofences.
    public static final long INVALID_LONG_VALUE = -999l;
    public static final float INVALID_FLOAT_VALUE = -999.0f;
    public static final int INVALID_INT_VALUE = -999;

}