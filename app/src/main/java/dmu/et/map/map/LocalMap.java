package dmu.et.map.map;

import android.content.Context;
import android.os.Build;
import android.util.Pair;

import androidx.annotation.RequiresApi;

public class LocalMap extends GeoMap{
    Context context;
    @RequiresApi(api = Build.VERSION_CODES.M)
    public LocalMap(Context context) {
        super(context);
    }

    public Pair<Float, Float> gpsToMap(float latitude, float longtiud, float width, float height){
        float ratioX = (latitude - GRID_ORIGEN_X )/ GRID_DISTANCE_X;
        float ratioY = (longtiud - GRID_ORIGEN_Y )/ GRID_DISTANCE_Y;

        float left = width * ratioX;
        float right = height * ratioY;
        return new Pair<>(new Float(left), new Float(right));
    }
}
