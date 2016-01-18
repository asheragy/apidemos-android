package org.cerion.apidemos.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class SimpleService extends Service {

    private static final String TAG = SimpleService.class.getSimpleName();
    //http://www.tutorialspoint.com/android/android_services.htm

    private boolean mRunning;

    public SimpleService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        mRunning = true;
        Thread thread = new Thread()
        {
            @Override
            public void run()
            {

                while(mRunning) {
                    Log.d(TAG, "...");
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        };

        thread.start();

        Toast.makeText(this, "Service Started", Toast.LENGTH_SHORT).show();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRunning = false;
        Toast.makeText(this, "Service Stoped", Toast.LENGTH_SHORT).show();
    }
}
