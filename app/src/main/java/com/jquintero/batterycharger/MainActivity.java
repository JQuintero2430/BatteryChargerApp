package com.jquintero.batterycharger;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    int batteryLevel = 0;
    boolean isCharging = false;
    TextView batteryLevelTextView;
    ProgressBar batteryProgressBar;
    Button btnChargeBattery, btnDischargeBattery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        batteryProgressBar = findViewById(R.id.batteryProgressBar);
        batteryLevelTextView = findViewById(R.id.batteryLevelTextView);
        btnChargeBattery = findViewById(R.id.btnChargeBattery);
        btnDischargeBattery = findViewById(R.id.btnDischargeBattery);

        btnChargeBattery.setOnClickListener(v -> {
            startSolarCharging();
        });

        btnDischargeBattery.setOnClickListener(v -> {
            startBatteryDischarging();
        });

    }
    private void startSolarCharging() {
        boolean isCharging = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isCharging && batteryLevel < 100) {
                    batteryLevel += 5;
                    if (batteryLevel > 100) {
                        batteryLevel = 100;
                    }
                    updateBatteryLevel();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        }).start();
    }

    private void startBatteryDischarging() {
        boolean isCharging = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isCharging && batteryLevel > 0) {
                    batteryLevel -= 5;
                    if (batteryLevel < 0) {
                        batteryLevel = 0;
                    }
                    updateBatteryLevel();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void updateBatteryLevel() {
        batteryLevelTextView.setText("Battery Level: " + batteryLevel + "%");
        batteryProgressBar.setProgress(batteryLevel);
    }
}