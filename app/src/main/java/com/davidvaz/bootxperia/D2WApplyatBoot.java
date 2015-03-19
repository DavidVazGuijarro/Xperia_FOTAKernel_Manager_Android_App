package com.davidvaz.bootxperia;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.io.IOException;

public class D2WApplyatBoot extends BroadcastReceiver {

    public static final String PREFS_NAME = "M5SettingsPrefs";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
            boolean d2w_persisted = settings.getBoolean("d2w", true);
                if (d2w_persisted){
                    try {
                        Runtime.getRuntime().exec(new String[] { "su", "-c", "echo enabled > /sys/devices/virtual/input/clearpad/wakeup_gesture"});
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            // Nasty Hack to close Cam on CM12, as it eats up a lot of CPU and doesn't work.
            try {
                Runtime.getRuntime().exec(new String[] { "su", "-c", "pgrep camera | xargs kill"});
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}