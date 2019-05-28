package dmu.et.map.model;

import android.content.Context;
import android.database.Cursor;
import android.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;

import dmu.et.map.util.database.DatabaseElequent;

public class Location extends DatabaseElequent {
    private static final String LATITUDE="latitude";
    private static final String LONGTUDE="longtude";
    private static final String NAME = "name";
    private static final String ICON = "icon";
    private static final String LEVEL = "level";

    private float latitude;
    private float longtude;
    private String name;
    private String icon;
    private int level;
    private Context context;

    public Location(Context context) {
        super(context, "location", new ArrayList<>(Arrays.asList(
                            new Pair<String, String>(Location.LATITUDE, "REAL")
                            ,new Pair<String, String>(Location.LONGTUDE, "REAL")
                            ,new Pair<String, String>(Location.NAME, "TEXT")
                            ,new Pair<String, String>(Location.ICON, "TEXT")
                            ,new Pair<String, String>(Location.LEVEL, "INTEGER"))));
    }

    public ArrayList<Location> get(){
        Cursor cursor = super._get();
        ArrayList<Location> rows= new ArrayList<>();
        while(cursor.moveToNext()){
            Location loc = new Location(this.context);
            loc.setLatitude(cursor.getFloat(cursor.getColumnIndex(Location.LATITUDE)));
            loc.setLongtude(cursor.getFloat(cursor.getColumnIndex(Location.LONGTUDE)));
            loc.setName(cursor.getString(cursor.getColumnIndex(Location.NAME)));
            loc.setIcon(cursor.getString(cursor.getColumnIndex(Location.ICON)));
            loc.setLevel(cursor.getInt(cursor.getColumnIndex(Location.LEVEL)));
            rows.add(loc);
        }
        return rows;
    }
    public float getLatitude() {return latitude; }

    public float getLongtude() {return longtude;}

    public String getName() {return name;}

    public String getIcon() {return icon;}

    public int getLevel() {return level; }

    public void setLatitude(float latitude) {this.latitude = latitude;}

    public void setLongtude(float longtude) {this.longtude = longtude;}

    public void setName(String name) {this.name = name; }

    public void setIcon(String icon) {this.icon = icon;}

    public void setLevel(int level) {this.level = level; }
}
