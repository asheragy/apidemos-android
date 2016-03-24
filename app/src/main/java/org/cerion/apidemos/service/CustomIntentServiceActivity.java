package org.cerion.apidemos.service;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.cerion.apidemos.R;

public class CustomIntentServiceActivity extends AppCompatActivity {

    private static final String TAG = CustomIntentServiceActivity.class.getSimpleName();
    private static final String EXTRA_NUMBER = "number";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_intent_service);

        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(int i = 1; i < 100; i++)
                    square(i);
            }
        });

    }

    private void square(int value) {

        //Intent intent = new Intent(CustomIntentServiceActivity.this, MyIntentService.class);
        //Intent intent = new Intent(CustomIntentServiceActivity.this, MyCustomIntentService.class);
        Intent intent = new Intent(CustomIntentServiceActivity.this, MultiThreadService.class);
        intent.putExtra(EXTRA_NUMBER, value);
        startService(intent);
    }

    private static void doWork(int value) {

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.d(TAG, value  + " squared = " + value*value);
    }

    /**
     * Implements an IntentService using a regular Service
     */
    public static class MyCustomIntentService extends Service {

        LooperThread mThread;

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        @Override
        public void onCreate() {

            mThread = new LooperThread();
            mThread.mService = this;
            mThread.start();

            //TODO, is there a better way?
            while(mThread.mHandler == null) {
                //Wait for creation
            }

        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {

            //Log.d(TAG,"startId = " + startId);
            Message msg = new Message();
            msg.arg1 = startId;
            msg.arg2 = intent.getIntExtra(EXTRA_NUMBER,0);
            mThread.mHandler.sendMessage(msg);

            return Service.START_NOT_STICKY;
        }

        @Override
        public void onDestroy() {
            Log.d(TAG,"onDestroy");
            super.onDestroy();
        }


        private class LooperThread extends Thread {

            Handler mHandler;
            MyCustomIntentService mService;

            @Override
            public void run() {

                Looper.prepare();

                mHandler = new Handler() {

                    public void handleMessage(Message msg) {
                        doWork(msg.arg2);
                        mService.stopSelf(msg.arg1);
                    }
                };

                Looper.loop();
            }
        }
    }










    /**
     * Basic intent service that everything above is trying to copy
     */
    public static class MyIntentService extends IntentService {

        /**
         * Creates an IntentService.  Invoked by your subclass's constructor.
         */
        public MyIntentService() {
            super("MyIntentService");
        }

        @Override
        protected void onHandleIntent(Intent intent) {
            doWork( intent.getIntExtra(EXTRA_NUMBER,0) );
        }

        @Override
        public void onDestroy() {
            Log.d(TAG,"onDestroy");
            super.onDestroy();
        }
    }

}
