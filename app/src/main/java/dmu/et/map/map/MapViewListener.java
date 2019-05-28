package dmu.et.map.map;

import android.graphics.RectF;
import android.location.Location;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.github.chrisbanes.photoview.OnMatrixChangedListener;
import com.github.chrisbanes.photoview.OnPhotoTapListener;
import com.github.chrisbanes.photoview.OnScaleChangedListener;

import androidx.annotation.RequiresApi;
import dmu.et.map.views.MapView;

public class MapViewListener implements
        OnMatrixChangedListener,
        OnScaleChangedListener,
        ViewTreeObserver.OnScrollChangedListener,
        View.OnClickListener,
        OnPhotoTapListener {
    LocalMap mLocalMap;
    MapView view;

    @RequiresApi(api = Build.VERSION_CODES.M)
    public MapViewListener(MapView view){
        this.view = view;
        this.mLocalMap = new LocalMap(this.view);
    }
    @Override
    public void onClick(View v) {

    }

    @Override
    public void onScrollChanged() {

    }

    @Override
    public void onMatrixChanged(RectF rect) {
        if(this.view.getCanvas() != null)
            this.view.getRenderer().update(this.view.getCanvas(),rect);
    }

    @Override
    public void onScaleChange(float scaleFactor, float focusX, float focusY) {

    }

    @Override
    public void onPhotoTap(ImageView view, float x, float y)
    {
        Location loc = this.view.getGeoMap().getLocationInstance(
                (GeoMap.GRID_DISTANCE_X*x)+GeoMap.GRID_ORIGEN_X,
                (GeoMap.GRID_DISTANCE_Y*y)+GeoMap.GRID_ORIGEN_Y);
        this.view.getGeoMap().setPinPoint(loc);
    }
}
