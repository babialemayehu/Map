package dmu.et.map.map;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.ViewTreeObserver;

import com.github.chrisbanes.photoview.OnMatrixChangedListener;
import com.github.chrisbanes.photoview.OnScaleChangedListener;

import androidx.annotation.RequiresApi;
import dmu.et.map.views.MapView;

public class MapViewListener implements
        OnMatrixChangedListener,
        OnScaleChangedListener,
        ViewTreeObserver.OnScrollChangedListener,
        View.OnClickListener {

    Context context;
    LocalMap mLocalMap;
    MapView view;
    Rect rect;
    @RequiresApi(api = Build.VERSION_CODES.M)
    public MapViewListener(MapView view, Rect rect){
        this.view = view;
        this.context = view.getContext();
        this.mLocalMap = new LocalMap(this.context);
    }
    @Override
    public void onClick(View v) {

    }

    @Override
    public void onScrollChanged() {

    }

    @Override
    public void onMatrixChanged(RectF rect) {
        Pair<Float,Float> place =this.mLocalMap.gpsToMap(
                GeoMap.GRID_ORIGEN_X+(GeoMap.GRID_DISTANCE_X/2),
                GeoMap.GRID_ORIGEN_Y+(GeoMap.GRID_DISTANCE_Y/2),
                rect.right-rect.left, rect.bottom-rect.top);

        this.rect = new Rect(Math.round(place.first.floatValue()+rect.left),
                            Math.round(place.second.floatValue()+rect.right),
                     Math.round(place.first.floatValue()+30f),
                Math.round(place.second.floatValue()+30f));
        this.view.setRect(this.rect);
        Log.i("screen", "rect: "+this.rect);
        Log.i("screen", "rect: "+rect + " <<<<==== ");

        view.postInvalidate();
    }

    @Override
    public void onScaleChange(float scaleFactor, float focusX, float focusY) {

    }
}
