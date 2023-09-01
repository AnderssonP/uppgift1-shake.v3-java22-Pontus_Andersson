package com.example.inlmning1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private TextView textX, textY, textZ;
    private ConstraintLayout bg;
    private Button imgBtn;
    private FrameLayout frame;
    private Button nextAct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(MainActivity.this, accelerometer, sensorManager.SENSOR_DELAY_NORMAL);

        textX = findViewById(R.id.xValue);
        textY = findViewById(R.id.yValue);
        textZ = findViewById(R.id.zValue);
        bg = findViewById(R.id.layout);
        imgBtn = findViewById(R.id.fragmentBtn);
        nextAct = findViewById(R.id.nextActivity);

        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                fm.beginTransaction().add(R.id.frame, rockFragment.class, null).commit();
        }
});

        nextAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(i);
            }
        });

        if (accelerometer == null){
            Log.d("Här","Null värde");
            Toast.makeText(this,"null", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            textX.setText("X-Acceleration: " + x);
            textY.setText("Y-Acceleration: " + y);
            textZ.setText("Z-Acceleration: " + z);

            frame = findViewById(R.id.frame);
            frame.setRotation(z);
            frame.setRotationX(x);
            frame.setRotationY(y);
            if (x < -3 || x > 3){
                Log.d("X", "onSensorChanged: "+ x);
            } else if (y < -10 || y > 10) {
                Log.d("Y", "onSensorChanged: "+y);
            } else if (z < -1 || z > 1) {
                Log.d("Z", "onSensorChanged: "+z);
            }

            if (x > y && x > z){
                bg.setBackgroundColor(Color.RED);
            } else if (y > x && y > z) {
                bg.setBackgroundColor(Color.GREEN);
            }else{
                bg.setBackgroundColor(Color.BLUE);
            }
        }
    }

    @Override
    protected void onPause() {
        sensorManager.unregisterListener(this);
        super.onPause();
    }

    @Override
    protected void onResume() {
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        sensorManager.unregisterListener(this);
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        sensorManager.unregisterListener(this);
        super.onStop();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }
}