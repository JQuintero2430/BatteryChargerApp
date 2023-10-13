package com.jquintero.batterycharger;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    int batteryLevel = 0;
    volatile boolean isCharging = false;
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

        btnChargeBattery.setOnClickListener(v -> controlBatteryCharging(5, 100));
        btnDischargeBattery.setOnClickListener(v -> controlBatteryCharging(-5, 0));
    }

    private void controlBatteryCharging(int delta, int limit) {
        isCharging = delta > 0;
        new Thread(() -> {
            while ((isCharging ? batteryLevel < limit : batteryLevel > limit) && (isCharging == (delta > 0))) {
                batteryLevel += delta;
                if (delta > 0 && batteryLevel > limit || delta < 0 && batteryLevel < limit) {
                    batteryLevel = limit;
                }
                runOnUiThread(this::updateBatteryLevel); // Update UI on the UI thread
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void updateBatteryLevel() {
        batteryLevelTextView.setText("Battery Level: " + batteryLevel + "%");
        batteryProgressBar.setProgress(batteryLevel);
    }
}
