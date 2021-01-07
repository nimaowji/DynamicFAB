package com.nimaowji.dynamicfab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.app.ActionBar;
import android.content.Intent;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private TextView myText;
    private FloatingActionButton myFab;
    private ImageButton Twitter;

    private SensorManager sensorManager;
    private Sensor sensor;
    private SensorEventListener sensorEventListener;

    private CoordinatorLayout.LayoutParams params;

    private float y;

    private static String twitter_user_name = "nima_owji";

    // Created by Nima Owji
    // Please don't use these codes without permission!


    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_FASTEST); // Register Listener
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(sensorEventListener); // Unregister Listener
    }

    // Created by Nima Owji
    // Please don't use these codes without permission!

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Getting Components
        myText = (TextView) findViewById(R.id.myText);
        myFab = (FloatingActionButton) findViewById(R.id.myFAB);
        Twitter = (ImageButton) findViewById(R.id.twitter);


        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE); // Getting sensor manager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION); // Getting Orientation sensor

        if (sensor == null) // check if this sensor is available or not
        {
            Toast.makeText(this, "This device doesn't have sensor", Toast.LENGTH_LONG).show(); // Show messages if it wasn't available
            finish();
        }

        params = (CoordinatorLayout.LayoutParams) myFab.getLayoutParams(); // Getting Layout params for FAB

        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {

                y = (sensorEvent.values[2]); // Getting Y axis of device
                int orientation = getResources().getConfiguration().orientation; // device orientation

                if (orientation == Configuration.ORIENTATION_PORTRAIT) // If device was portrait
                {

                    if (y < -20f) // If he is holding his phone with right hand
                    {
                        params.gravity = Gravity.RIGHT | Gravity.BOTTOM;
                        myFab.setLayoutParams(params);
                    }
                    else if (y > 20f) // If he is holding his phone with left hand
                    {
                        params.gravity = Gravity.LEFT | Gravity.BOTTOM;
                        myFab.setLayoutParams(params);
                    }


                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };

        // Created by Nima Owji
        // Please don't use these codes without permission!

        // Say Hi
        myFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Hi", Toast.LENGTH_SHORT).show();
            }
        });

        // My twitter
        Twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=" + twitter_user_name)));
                }catch (Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/#!/" + twitter_user_name)));
                }
            }
        });
    }
}