package com.example.inlmning1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class Activity2 extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;

    private Sensor stepOMeter;

    private int totalSteps = 0;
    private TextView counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        stepOMeter = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        sensorManager.registerListener(Activity2.this, stepOMeter, sensorManager.SENSOR_DELAY_NORMAL);

        counter = findViewById(R.id.counter);


        if (stepOMeter == null){
            counter.setText("Stegräknare fungerar inte på denna enhet");
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType()== Sensor.TYPE_STEP_COUNTER){
            totalSteps = (int) sensorEvent.values[0];
            counter.setText("Antal steg: "+ totalSteps);
            Log.d("step", "onSensorChanged: " + totalSteps);
            Log.d("event", "onSensorChanged: " + sensorEvent);

        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
            sensorManager.registerListener(this,stepOMeter,sensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (stepOMeter != null){
            sensorManager.unregisterListener(this);
        }
    }
}