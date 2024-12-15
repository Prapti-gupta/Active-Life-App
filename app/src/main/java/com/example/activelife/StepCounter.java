package com.example.activelife;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class StepCounter extends AppCompatActivity implements SensorEventListener {

    private TextView stepCountTextView;
    private TextView distanceTextView;
    private TextView timeTextView;
    private Button pauseButton;
    private TextView changeTargetText;
    private SensorManager sensorManager;
    private Sensor stepCounterSensor;
    private int StepCount = 0;
    private ProgressBar progressBar;
    private int initialStepCount = -1;

    private boolean isPaused = false;
    private long timePause = 0;
    private float stepLengthInMeters = 0.762f;
    private long startTime;
    private int stepCountTarget = 5000;
    private TextView stepCountTargetTextView;
    private Handler timeHandler = new Handler();
    private Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            long milis = System.currentTimeMillis() - startTime;
            int seconds = (int) (milis / 1000);
            int min = seconds / 60;
            seconds = seconds % 60;
            timeTextView.setText(String.format(Locale.getDefault(), "%02d:%02d", min, seconds));
            timeHandler.postDelayed(this, 1000);
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        if (stepCounterSensor != null) {
            sensorManager.unregisterListener(this);
            timeHandler.removeCallbacks(timerRunnable);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Load updated step target from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("ActiveLifeLogin", MODE_PRIVATE);
        stepCountTarget = sharedPreferences.getInt("stepCountTarget", 5000); // Default is 5000

        progressBar.setMax(stepCountTarget);
        stepCountTargetTextView.setText("Step Goal: " + stepCountTarget);

        if (stepCounterSensor != null) {
            sensorManager.registerListener(this, stepCounterSensor, SensorManager.SENSOR_DELAY_NORMAL);
            timeHandler.postDelayed(timerRunnable, 0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Base_Theme_ActiveLife);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_counter);

        // Check if the alert has been shown before
        SharedPreferences sharedPreferences = getSharedPreferences("ActiveLifeLogin", MODE_PRIVATE);
        boolean isAlertShown = sharedPreferences.getBoolean("isAlertShown", false);

        if (!isAlertShown) {
            // Show the alert dialog
            showAlertToEnableActivitySensing();

            // Update SharedPreferences so the alert is only shown once
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isAlertShown", true);
            editor.apply();
        }

        stepCountTextView = findViewById(R.id.stepcount);
        distanceTextView = findViewById(R.id.distance);
        timeTextView = findViewById(R.id.time);
        pauseButton = findViewById(R.id.pausebt);
        changeTargetText = findViewById(R.id.changeTargetText);
        stepCountTargetTextView = findViewById(R.id.target);
        progressBar = findViewById(R.id.progressbar);

        startTime = System.currentTimeMillis();

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        stepCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        progressBar.setMax(stepCountTarget);
        stepCountTargetTextView.setText("Step Goal: " + stepCountTarget);

        if (stepCounterSensor == null) {
            stepCountTextView.setText("Step Counter not available");
        }

        // Underline and set click listener for the change target text
        changeTargetText.setPaintFlags(changeTargetText.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        changeTargetText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openChangeStepTarget();
            }
        });
    }

    private void showAlertToEnableActivitySensing() {
        // Build the alert dialog
        new AlertDialog.Builder(this)
                .setTitle("Enable Activity Sensing")
                .setMessage("To track your steps, please enable activity sensing (Physical Activity) in settings.")
                .setPositiveButton("Go to Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Redirect to settings
                        Intent intent = new Intent(Settings.ACTION_SETTINGS);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void openChangeStepTarget() {
        Intent intent = new Intent(this, ChangeStepTarget.class);
        startActivity(intent);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        if (sensorEvent.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            if (initialStepCount == -1) {
                initialStepCount = (int) sensorEvent.values[0];
            }
            StepCount = (int) sensorEvent.values[0] - initialStepCount;
            stepCountTextView.setText("" + StepCount);
            progressBar.setProgress(StepCount);

            if (StepCount >= stepCountTarget) {
                stepCountTargetTextView.setText("Step Goal Achieved!");
            }

            float distanceInKm = StepCount * stepLengthInMeters / 1000;
            distanceTextView.setText(String.format(Locale.getDefault(), "%.2f km", distanceInKm));
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    public void onPauseClicker(View view) {
        if (isPaused) {
            isPaused = false;
            pauseButton.setText("Pause");
            startTime = System.currentTimeMillis() - timePause;
            timeHandler.postDelayed(timerRunnable, 0);
        } else {
            isPaused = true;
            pauseButton.setText("Resume");
            timeHandler.removeCallbacks(timerRunnable);
            timePause = System.currentTimeMillis() - startTime;
        }
    }
}
