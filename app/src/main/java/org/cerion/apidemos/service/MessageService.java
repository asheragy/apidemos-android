package org.cerion.apidemos.service;


import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.util.Log;

public class MessageService extends Service {

    private static final String TAG = MessageService.class.getSimpleName();
    final Messenger mMessenger = new Messenger(new IncomingHandler());

    private class IncomingHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            Log.d(TAG,"handleMessage");
        }
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
        super.onDestroy();
    }
}
