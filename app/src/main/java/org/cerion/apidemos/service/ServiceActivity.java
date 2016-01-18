package org.cerion.apidemos.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.cerion.apidemos.R;

public class ServiceActivity extends AppCompatActivity {

    private static final String TAG = ServiceActivity.class.getSimpleName();
    private static final IntentFilter mBroadcastFilter = new IntentFilter(SimpleBroadcastReceiver.ACTION_BROADCAST);
    private static final SimpleBroadcastReceiver mBroadcastReceiver = new SimpleBroadcastReceiver();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_activity);

        //1. Intent Service with BroadcastReceiver
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ServiceActivity.this, SimpleIntentService.class);
                startService(intent);
            }
        });


        //TODO 6.4 bind to activity

        //2. Bind Service to this Activity
        findViewById(R.id.bind).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bindService();
            }
        });

        findViewById(R.id.unbind).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unbindService();
            }
        });

        mTextCounter = (TextView)findViewById(R.id.textCounter);

        //http://stackoverflow.com/questions/18779610/android-service-what-happen-at-the-end-of-activity-life
        findViewById(R.id.start_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ServiceActivity.this, SimpleService.class);
                startService(intent);
            }
        });

        findViewById(R.id.stop_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ServiceActivity.this, SimpleService.class);
                if(!stopService(intent))
                    Toast.makeText(ServiceActivity.this, "Service not running", Toast.LENGTH_SHORT).show();
            }
        });

        //6.5. Handler and ResultReceiver or Messenger

        //TODO message with handler
        //http://viktorbresan.blogspot.com/2012/09/intentservice-and-inter-process.html

        //TODO AIDL for service in another process
        //http://developer.android.com/guide/components/aidl.html


    }

    private void toast(String message) {
        Toast.makeText(ServiceActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mBroadcastReceiver, mBroadcastFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mBroadcastReceiver);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(); //Recommended to bind/unbind with start/stop instead of pause/resume
    }

    //-----------------------------------------------
    //For Bound service
    private boolean mBound = false;
    private BoundService mBoundService;
    private Handler mHandler = new Handler();
    private TextView mTextCounter;

    private void bindService() {
        Intent intent = new Intent(ServiceActivity.this, BoundService.class);
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
        mBound = true;
    }

    private void unbindService() {
        if(mBound) {
            unbindService(mServiceConnection);
            mBoundService = null;
            mBound = false;
        }
    }

    //For bound service
    public ServiceConnection mServiceConnection = new ServiceConnection(){

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG,"onServiceConnected()");
            mBoundService = ((BoundService.SimpleBinder)service).getService();
            startBindCounter();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG,"onServiceDisconnected()");

        }

    };

    private void startBindCounter() {

        new Thread(new Runnable() {

            public void run() {
                while (mBoundService != null) {

                    // Update the counter
                    update();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                update();
            }

            private void update() {
                mHandler.post(new Runnable() {
                    public void run() {
                        if(mBoundService != null)
                            mTextCounter.setText(mBoundService.getCount() + "");
                        else
                            mTextCounter.setText("Stopped");
                    }
                });
            }

        }).start();

    }

    /*
    private void sendNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(android.R.drawable.sym_def_app_icon)
                .setContentTitle("Service running")
                .setTicker("Playing")
                .setWhen(System.currentTimeMillis())
                .setOngoing(true);

        Intent intent = new Intent(this,ServiceActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this,0,intent,0);
        builder.setContentIntent(contentIntent);
        Notification notification = builder.build();
        NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1,notification);
    }
    */
}
