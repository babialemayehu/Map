package dmu.et.map.activities;

//import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import dmu.et.map.views.MapView;
import dmu.et.map.R;


public class MainActivity extends Activity {

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final MapView photoView = (MapView) findViewById(R.id.x);

    }
}
