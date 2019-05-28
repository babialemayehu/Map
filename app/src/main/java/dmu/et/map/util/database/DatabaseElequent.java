package dmu.et.map.util.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Pair;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;

public class DatabaseElequent extends SQLiteOpenHelper {
    private static final int VERSION  = 1;
    public static final String LOGIC_AND = "AND";
    public static final String LOGIC_OR = "OR";
    public static final String GREATERTHAN = ">";
    public static final String LESSTHAN = "<";
    public static final String EQUAL = "=";
    public static final String GREATERTHAN_OR_EQUAL = ">=";
    public static final String LESSTHAN_OR_EQUAL = "<=";
    public static final String NOT_EQUAL = "<>";
    public static final String CREATD_AT ="created_at";
    public static final String UPDATED_AT = "updated_at";

    private String name;
    private ArrayList<Pair<String, String>> columns;
    private StringBuilder whereClose;

    public DatabaseElequent(@Nullable Context context, @Nullable String name, ArrayList<Pair<String, String>> columns) {
        super(context, name, null, VERSION);
        this.name = name;
        this.columns = columns;
        this.whereClose = new StringBuilder();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder createQuery = new StringBuilder();
        createQuery.append("CREATE TABLE ");
        createQuery.append(this.name);
        createQuery.append(" (id integer primary key");
        for(Pair<String, String> col: this.columns){
            createQuery.append(" ,").append(col.first).append(" ").append(col.second);
        }
        createQuery.append(" , ").append(DatabaseElequent.CREATD_AT).append(" NUMERIC")
                .append(" ,").append(DatabaseElequent.UPDATED_AT).append(" NUMERIC)");
        db.execSQL(createQuery.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+this.name);
        onCreate(db);
    }

    public Cursor insert(HashMap<String, Object> rows){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        for(Map.Entry<String, Object> col: rows.entrySet()){
            contentValues.put(col.getKey(), col.getValue().toString());
        }
        String now =new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        contentValues.put(DatabaseElequent.CREATD_AT, now);
        contentValues.put(DatabaseElequent.UPDATED_AT, now);
        long inserted = db.insert(this.name, null, contentValues);
        if(inserted != 0){
            Cursor cursor =  db.rawQuery("SELECT * FROM "+this.name+" ORDER BY id DESC ", null);
            cursor.moveToFirst();
            return cursor;
        }else return null;
    }

    public Cursor update(HashMap<String,Object> rows){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        for(Map.Entry<String, Object> col: rows.entrySet()){
            contentValues.put(col.getKey(), col.getValue().toString());
        }
        String now =new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        contentValues.put(DatabaseElequent.UPDATED_AT, now);
        long inserted = db.update(this.name,contentValues, this.whereClose.toString(), null );
        if(inserted != 0){
            Cursor cursor =  db.query(this.name, null, this.whereClose.toString(), null, null, null,null);
            return cursor;
        }else return null;
    }

    public boolean delete(){
        SQLiteDatabase db = this.getReadableDatabase();
        long del = db.delete(this.name, this.whereClose.toString(), null);
        return (del > 0);
    }
    public Cursor _get(String[] cols){
        SQLiteDatabase db = getReadableDatabase();
        Cursor restult = db.query(this.name, cols, this.whereClose.toString(), null, null, null, null);
        return restult;
    }
    public Cursor _get(){
        return this._get(null);
    }
    public DatabaseElequent where(String col, String op, Object value, String logic){
        if(this.whereClose.length() > 0) {
            this.whereClose.append(" ").append(logic);
        }
        this.whereClose.append(" ").append(col).append(op);
        if(value instanceof String){
            this.whereClose.append("'").append(value).append("'");
        }else{
            this.whereClose.append(value);
        }
        return this;
    }

    public DatabaseElequent where(String col, String op, Object value){
        return this.where(col, op , value, DatabaseElequent.LOGIC_AND);
    }
    public DatabaseElequent orWhere(String col, String op, Object value){
        return this.where(col, op, value, DatabaseElequent.LOGIC_OR);
    }

    public DatabaseElequent where(String col, Object value){
        return this.where(col, DatabaseElequent.EQUAL, value, DatabaseElequent.LOGIC_AND);
    }
    public DatabaseElequent orWhere(String col, Object value){
        return this.where(col, DatabaseElequent.EQUAL, value, DatabaseElequent.LOGIC_OR);
    }

    public DatabaseElequent whereNot(String col, Object value){
        return this.where(col,DatabaseElequent.NOT_EQUAL, value, DatabaseElequent.LOGIC_AND);
    }
    public DatabaseElequent orWhereNot(String col, Object value){
        return this.where(col,DatabaseElequent.NOT_EQUAL, value, DatabaseElequent.LOGIC_OR);
    }
}
