package org.cerion.apidemos.service;


import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.HashSet;
import java.util.Set;

public class MessageService extends Service {

    private static final String TAG = MessageService.class.getSimpleName();
    final Messenger mMessenger = new Messenger(new IncomingHandler());
    private Set<Messenger> mMessengerSet = new HashSet<>();
    private Thread mThread;

    //Register/unregister to receive interval update messages
    public static final int MESSAGE_REGISTER = 0;
    public static final int MESSAGE_UNREGISTER = 1;
    public static final int MESSAGE_REGISTER_BROADCAST = 2;

    //Command to run some action but not reply
    public static final int MESSAGE_DO_ACTION = 3;

    //Reply to message with some response
    public static final int MESSAGE_GET_VALUE = 4;

    private class IncomingHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {

            switch(msg.what) {
                case MESSAGE_DO_ACTION:
                    doAction(msg.arg1,msg.arg2);
                    break;
                case MESSAGE_GET_VALUE:
                    Message reply = Message.obtain();
                    reply.what = MESSAGE_GET_VALUE;
                    reply.arg1 = getValue(msg.arg1,msg.arg2);

                    try {
                        msg.replyTo.send(reply);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;

                case MESSAGE_REGISTER:

                    if(mMessengerSet.contains(msg.replyTo))
                        Log.d(TAG,"already registered");
                    else {
                        Log.d(TAG,"registered");
                        mMessengerSet.add(msg.replyTo);
                    }

                    break;

                case MESSAGE_UNREGISTER:

                    if(mMessengerSet.contains(msg.replyTo)) {
                        mMessengerSet.remove(msg.replyTo);
                        Log.d(TAG, "unregistered");
                    }
                    else {
                        Log.d(TAG, "not registered");
                    }

                    break;
            }

        }
    }

    private void doAction(int arg1, int arg2) {
        Log.d(TAG,"action(" + arg1 + "," + arg2 + ")");
    }

    private int getValue(int arg1, int arg2) {
        Log.d(TAG,"getValue(" + arg1 + "," + arg2 + ")");
        return arg1 + arg2;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mThread = new Thread(new Runnable() {
            @Override
            public void run() {

                while(true) {

                    for (Messenger m : mMessengerSet) {
                        try {
                            m.send(Message.obtain(null, MESSAGE_REGISTER_BROADCAST));
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Log.d(TAG,"ending thread");
                        break;
                    }

                }

            }
        });
        mThread.start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG,"onBind");
        return mMessenger.getBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG,"onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG,"onDestroy");
        mThread.interrupt();
        super.onDestroy();
    }
}
