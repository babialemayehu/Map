package dmu.et.map.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;

import dmu.et.map.R;
import dmu.et.map.map.LocalMap;
import dmu.et.map.model.Location;

public class Renderer {
    private Canvas canvas;
    private RectF display;
    private Location loc;
    private MapView view;

    Renderer(MapView view){
        super();
        this.loc = new Location(view.getContext());
        this.view = view;
    }

    public void update(Canvas canvas,RectF display){
        this.display = display;
        if(this.display != null)
            this.renderLocations(canvas,loc.get());
        this.renderDeviceLocaiton(canvas);
        this.renderPinPoint(canvas);
    }

    public void renderLocations(Canvas canvas, ArrayList<Location> locations){
        for(Location loc: locations){
            Pair<Integer, Integer> cordinate = LocalMap.getDisplayCordinate(loc.getLatitude(), loc.getLongtude(), this.display);
            this.renderText(canvas,loc,cordinate);
        }
    }

    public void renderText(Canvas canvas,Location loc, int x, int y){
        Paint p = new Paint();
        p.setTextSize(42);
        p.setColor(Color.RED);
        canvas.drawText(loc.getName(), x, y, p);
    }

    public void renderText(Canvas canvas,Location loc, Pair<Integer, Integer> cordinate){
        this.renderText(canvas,loc,cordinate.first,cordinate.second);
    }

    public void renderDeviceLocaiton(Canvas canvas){
        android.location.Location deviceLocation = this.view.getGeoMap().getDeviceLocation();
        Log.e("loc", "  "+deviceLocation.getAccuracy());
        Pair<Integer, Integer> cordinate = LocalMap.getDisplayCordinate(
                (float)deviceLocation.getLatitude(),
                (float)deviceLocation.getLongitude(),
                this.display
        );
        final int RAD = 18;
        Paint p = new Paint();
        p.setColor(Color.BLUE);
        canvas.drawCircle(cordinate.first,cordinate.second,RAD, p);
        p.setARGB(75, 100, 120, 255);
        canvas.drawCircle(cordinate.first,cordinate.second,RAD+(deviceLocation.getAccuracy()*this.view.getScale()), p);
    }

    public void renderPinPoint(Canvas canvas){
        Drawable pinPointIcon = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            pinPointIcon = this.view.getResources().getDrawable(R.drawable.pin_point,null);
        }
        pinPointIcon.setBounds(200,200,220,220);
        pinPointIcon.draw(canvas);
    }
}
