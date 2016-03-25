package org.cerion.apidemos.service;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import org.cerion.apidemos.R;
import org.cerion.apidemos.Tools;

public class MessageServiceActivity extends AppCompatActivity {

    private static final String TAG = MessageServiceActivity.class.getSimpleName();
    private Messenger mService;
    private boolean mBound;

    private Messenger mActivityMessenger = new Messenger(new IncomingHandler());

    private class IncomingHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {

            switch(msg.what) {
                case MessageService.MESSAGE_GET_VALUE:
                    Log.d(TAG,"value = " + msg.arg1);
                    break;
                case MessageService.MESSAGE_REGISTER_BROADCAST:
                    Log.d(TAG,"received registered message broadcast");
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_service);
        setTitle(TAG);


        findViewById(R.id.action).setOnClickListener( getListener(MessageService.MESSAGE_DO_ACTION) );
        findViewById(R.id.get_value).setOnClickListener( getListener(MessageService.MESSAGE_GET_VALUE));
        findViewById(R.id.register).setOnClickListener( getListener(MessageService.MESSAGE_REGISTER));
        findViewById(R.id.unregister).setOnClickListener( getListener(MessageService.MESSAGE_UNREGISTER));
    }


    private View.OnClickListener getListener(final int type) {

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mBound)
                    try {
                        Message msg = Message.obtain(null, type);

                        switch(type) {
                            case MessageService.MESSAGE_DO_ACTION:
                                msg.arg1 = Tools.getRandomInteger();
                                msg.arg2 = Tools.getRandomInteger();
                                break;
                            case MessageService.MESSAGE_GET_VALUE:
                                msg.arg1 = Tools.getRandomInteger();
                                msg.arg2 = Tools.getRandomInteger();
                                msg.replyTo = mActivityMessenger;
                                break;

                            case MessageService.MESSAGE_REGISTER:
                            case MessageService.MESSAGE_UNREGISTER:
                                msg.replyTo = mActivityMessenger;
                                break;
                        }

                        mService.send(msg);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
            }
        };
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            // This is called when the connection with the service has been
            // established, giving us the object we can use to
            // interact with the service.  We are communicating with the
            // service using a Messenger, so here we get a client-side
            // representation of that from the raw IBinder object.
            mService = new Messenger(service);
            mBound = true;
        }

        public void onServiceDisconnected(ComponentName className) {
            // This is called when the connection with the service has been
            // unexpectedly disconnected -- that is, its process crashed.
            mService = null;
            mBound = false;
        }
    };


    @Override
    protected void onStart() {
        super.onStart();
        // Bind to the service
        bindService(new Intent(this, MessageService.class), mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Unbind from the service
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }


}
