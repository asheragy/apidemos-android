package org.cerion.apidemos.service;

import android.app.IntentService;
import android.content.Intent;

import org.cerion.apidemos.Tools;

public class SimpleIntentService extends IntentService {

    private static final String TAG = SimpleIntentService.class.getSimpleName();

    public SimpleIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Tools.sleep(2); //simulate doing work

        //TODO add LocalBroadcastManager its better for local messages only
        Intent i = new Intent(SimpleBroadcastReceiver.ACTION_BROADCAST);
        i.putExtra(SimpleBroadcastReceiver.EXTRA_MESSAGE,"Finished SimpleIntentService onHandleIntent");
        sendBroadcast(i);
    }
}
