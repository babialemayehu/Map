package dmu.et.map.views;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.ViewTreeObserver;

import com.github.chrisbanes.photoview.OnMatrixChangedListener;
import com.github.chrisbanes.photoview.PhotoView;

import androidx.annotation.RequiresApi;
import dmu.et.map.R;
import dmu.et.map.map.GeoMap;
import dmu.et.map.map.MapViewListener;

public class MapView extends PhotoView implements ViewTreeObserver.OnGlobalLayoutListener {
    public static final float MAX_SCALE = 5f;

    private int MAP_IMG = R.drawable.map;
    private float mInitWidth;
    private float mInitHeiht;

    private MapViewListener mListener;

    private Canvas canvas;
    private Renderer renderer;
    private GeoMap geoMap;

    @RequiresApi(api = Build.VERSION_CODES.M)
    public MapView(Context context) {
        super(context);
        init(context, null);
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public MapView(Context context, AttributeSet attr) {
        super(context, attr);
        init(context, attr);
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public MapView(Context context, AttributeSet attr, int defStyle) {
        super(context, attr, defStyle);
        init(context, attr);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void init(Context context, AttributeSet attr) {
        this.mListener = new MapViewListener(this);
        this.renderer = new Renderer(this);
        this.geoMap = new GeoMap(this);
        setImageResource(this.MAP_IMG);
        getViewTreeObserver().addOnGlobalLayoutListener(this);
        setOnMatrixChangeListener(this.mListener);
        setOnPhotoTapListener(this.mListener);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.canvas = canvas;
        this.renderer.update(canvas, getDisplayRect());
        this.geoMap.listenLocation();
    }
    @Override
    public void onGlobalLayout() {
        setScale(initialScale());
        setMinimumScale(initialScale());
        setMaximumScale(MAX_SCALE);
    }

    private float initialScale(){
        Bitmap map_img = BitmapFactory.decodeResource(getResources(), this.MAP_IMG);

        float newWidth = (map_img.getWidth()*getHeight())/map_img.getHeight();
        if(newWidth > getWidth()) {
            float newHeihgt = (map_img.getHeight()*getWidth())/ map_img.getWidth();
            this.mInitWidth = getWidth();
            this.mInitHeiht = newHeihgt;
            return getHeight()/ newHeihgt;
        }else {
            this.mInitWidth = newWidth;
            this.mInitHeiht = getHeight();
            return getWidth()/ newWidth;
        }

    }

    public Canvas getCanvas() {return canvas;}
    public Renderer getRenderer() {return renderer;}

    public int getMapWidth(){return Math.round(this.mInitWidth*getScale());}
    public int getMapHeight(){return Math.round(this.mInitHeiht*getScale());}

    public float getInitWidth(){ return this.mInitWidth; }
    public float getInitHeiht(){ return this.mInitHeiht; }

    public GeoMap getGeoMap() { return geoMap; }
}
