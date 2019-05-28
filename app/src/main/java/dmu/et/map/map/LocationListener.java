package dmu.et.map.map;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;

import dmu.et.map.views.MapView;

public class LocationListener implements android.location.LocationListener {

    private GeoMap geoMap;

    public LocationListener(GeoMap geoMap){
        this.geoMap = geoMap;
    }
    @Override
    public void onLocationChanged(Location location) {
        this.geoMap.setDeviceLocation(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
