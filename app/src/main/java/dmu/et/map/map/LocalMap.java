package dmu.et.map.map;

import android.content.Context;
import android.graphics.RectF;
import android.os.Build;
import android.util.Pair;

import java.nio.MappedByteBuffer;

import androidx.annotation.RequiresApi;
import dmu.et.map.views.MapView;

public class LocalMap extends GeoMap{
    MapView view;
    @RequiresApi(api = Build.VERSION_CODES.M)
    public LocalMap( MapView view) {
        super(view);
        this.view = view;
    }

    public static Pair<Float, Float> gpsToMap(float latitude, float longtuid, float width, float height){
        float ratioX = (latitude - GRID_ORIGEN_X )/ GRID_DISTANCE_X;
        float ratioY = (longtuid - GRID_ORIGEN_Y )/ GRID_DISTANCE_Y;

        float left = width * ratioX;
        float right = height * ratioY;
        return new Pair<>(new Float(left), new Float(right));
    }

    public static Pair<Float, Float> mapToGps(float x, float y,float width, float height){
        float ratioX = x/width;
        float ratioY = y/height;

        float latitude = GRID_ORIGEN_X+(GRID_DISTANCE_X*ratioX);
        float longtuid = GRID_ORIGEN_Y+(GRID_DISTANCE_X*ratioY);

        return new Pair<>(new Float(latitude), new Float(longtuid));
    }

    public static Pair<Integer, Integer> getDisplayCordinate(float latitude, float longtuide, RectF display){
        Pair<Float,Float> place = LocalMap.gpsToMap(
                                        latitude, longtuide,
                                display.right-display.left,
                                display.bottom-display.top);

        return new Pair<>(Math.round(place.first.floatValue()+display.left),
                Math.round(place.second.floatValue()+display.top));

    }
}
