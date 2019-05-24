package dmu.et.map.map;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.util.Pair;

import androidx.annotation.RequiresApi;

public class GeoMap {
    public static final float GRID_ORIGEN_X = 10.327004f;
    public static final float GRID_ORIGEN_Y = 37.739847f;
    public static final float GRID_Y = 37.839847f;
    public static final float GRID_X = 10.427004f;
    public static final float GRID_DISTANCE_X = GRID_X - GRID_ORIGEN_X;
    public static final float GRID_DISTANCE_Y = GRID_Y - GRID_ORIGEN_Y;

    public static final Pair<Float, Float> GRID_ORIGEN = new Pair<>(new Float(GRID_ORIGEN_X), new Float(GRID_ORIGEN_Y));
    public static final Pair<Float, Float> GRID_CORDINATE = new Pair<>(new Float(GRID_X), new Float(GRID_Y));
    public static final Pair<Float, Float> GRID_DISTANCE = new Pair<>(new Float(GRID_DISTANCE_X), new Float(GRID_DISTANCE_Y));

    public static final long MIN_TIME = 1000;
    public static final long MIN_DISTANCE = 10;

    private LocationManager mLocationManager;

    @RequiresApi(api = Build.VERSION_CODES.M)

    GeoMap(Context context) {
        if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                context.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        mLocationManager.requestLocationUpdates("gps", MIN_TIME, MIN_DISTANCE, new LocationListener());
    }
    public LocationManager getLocation() {
        return mLocationManager;
    }

    public Pair<Float, Float> getGridDistance(){ return GRID_DISTANCE; }
    public Pair<Float, Float> getGridOrgin(){ return GRID_ORIGEN; }
    public Pair<Float, Float> getGridCordinate(){ return GRID_CORDINATE; }
}
