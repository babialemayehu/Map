package dmu.et.map.map;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.util.Pair;

import androidx.annotation.RequiresApi;
import dmu.et.map.views.MapView;

public class GeoMap {
    public static final float GRID_ORIGEN_Y = 10.327004f;
    public static final float GRID_ORIGEN_X = 37.739847f;
    public static final float GRID_X = 37.839847f;
    public static final float GRID_Y = 10.427004f;
    public static final float GRID_DISTANCE_X = GRID_X - GRID_ORIGEN_X;
    public static final float GRID_DISTANCE_Y = GRID_Y - GRID_ORIGEN_Y;

    public static final Pair<Float, Float> GRID_ORIGEN = new Pair<>(new Float(GRID_ORIGEN_X), new Float(GRID_ORIGEN_Y));
    public static final Pair<Float, Float> GRID_CORDINATE = new Pair<>(new Float(GRID_X), new Float(GRID_Y));
    public static final Pair<Float, Float> GRID_DISTANCE = new Pair<>(new Float(GRID_DISTANCE_X), new Float(GRID_DISTANCE_Y));

    public static final long MIN_TIME = 1000;
    public static final long MIN_DISTANCE = 10;

    private LocationManager mLocationManager;
    private String provider;
    private MapView view;

    private Location deviceLocation;


    private Location pinPoint;

    @RequiresApi(api = Build.VERSION_CODES.M)

    public GeoMap(MapView view) {
        this.view = view;
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.POWER_MEDIUM);
        mLocationManager = (LocationManager) this.view.getContext().getSystemService(Context.LOCATION_SERVICE);
        this.provider = this.mLocationManager.getBestProvider(criteria, true);

        if (this.view.getContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                this.view.getContext().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        this.deviceLocation = mLocationManager.getLastKnownLocation(this.provider);
        this.listenLocation();
    }
    public void listenLocation(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (this.view.getContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    this.view.getContext().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
        }
        mLocationManager.requestLocationUpdates(this.provider, MIN_TIME, MIN_DISTANCE, new LocationListener(this));
    }

    public LocationManager getLocationManager() {
        return mLocationManager;
    }

    public Pair<Float, Float> getGridDistance(){ return GRID_DISTANCE; }
    public Pair<Float, Float> getGridOrgin(){ return GRID_ORIGEN; }
    public Pair<Float, Float> getGridCordinate(){ return GRID_CORDINATE; }

    public Location getDeviceLocation() {return deviceLocation;}
    public void setDeviceLocation(Location diviceLocation) {
        this.deviceLocation = diviceLocation;
        this.view.postInvalidate();
    }

    public Location getPinPoint() {return pinPoint;}
    public void setPinPoint(Location pinPoint) {
        this.pinPoint = pinPoint;
        this.view.postInvalidate();
    }

    public Location getLocationInstance(float latitude, float longitude){
        Location loc = new Location(this.provider);
        loc.setLatitude((double)latitude);
        loc.setLongitude((double)longitude);
        return loc;
    }
    public Location getLocationInstance(Pair<Float, Float> loc){
        return this.getLocationInstance(loc.first, loc.second);
    }
}
