package org.cerion.apidemos.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;


public class SimpleBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = SimpleIntentService.class.getSimpleName();

    public static final String ACTION_BROADCAST = "action_broadcast";
    public static final String EXTRA_MESSAGE = "message";

    @Override
    public void onReceive(Context context, Intent intent) {
        String message = intent.getStringExtra(EXTRA_MESSAGE);
        Toast.makeText(context, "BroadcastReceiver: " + message, Toast.LENGTH_SHORT).show();
    }
}
