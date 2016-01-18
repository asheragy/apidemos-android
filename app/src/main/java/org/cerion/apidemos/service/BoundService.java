package org.cerion.apidemos.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class BoundService extends Service {

    private static final String TAG = BoundService.class.getSimpleName();
    private final SimpleBinder mBinder = new SimpleBinder();
    private int mCount = 0;
    private boolean mRunning = false;

    public BoundService() {
        Log.d(TAG,"onCreate()");

        mRunning = true;
        Thread thread = new Thread()
        {
            @Override
            public void run()
            {
                while(mRunning) {
                    mCount++;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();
    }

    public int getCount() {
        return mCount;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind()");
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG,"onUnbind()");
        return super.onUnbind(intent);
    }

    public class SimpleBinder extends Binder {

        BoundService getService() {
            return BoundService.this;
        }

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG,"onStartCommand()");
        return Service.START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy()");
        mRunning = false;
    }
}
