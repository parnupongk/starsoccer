/*
Copyright 2015 Google Inc. All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package com.isport_starsoccer.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;
import com.isport_starsoccer.MainActivity;
import com.isport_starsoccer.R;


/**
 * Service used for receiving GCM messages. When a message is received this service will log it.
 */
public class GcmService extends GcmListenerService {

    private static final String TAG = "StarSoccer";
    private NotificationManager mNotificationManager = null;
    private LoggingService.Logger logger;

    public GcmService() {
        logger = new LoggingService.Logger(this);
    }

    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");
        sendNotification("Received GCM Message: " + message);
    }

    @Override
    public void onDeletedMessages() {
        sendNotification("Deleted messages on server");
    }

    @Override
    public void onMessageSent(String msgId) {
        sendNotification("Upstream message sent. Id=" + msgId);
    }

    @Override
    public void onSendError(String msgId, String error) {
        sendNotification("Upstream message send error. Id=" + msgId + ", error" + error);
    }

    // Put the message into a notification and post it.
    // This is just one simple example of what you might choose to do with
    // a GCM message.
    private void sendNotification(String msg) {
        Log.i(TAG, "GCM Registration Mess: " + msg);

        String detail="",title="";
        Bitmap imgBig = null;
        mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent viewIntent = new Intent(this, MainActivity.class);
        PendingIntent viewPendingIntent = PendingIntent.getActivity(this, 0, viewIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notif = new Notification.Builder(this)
                .setContentText(detail)
                .setContentTitle(title)
                .setSmallIcon(R.drawable.ic_launcher)
                .setLargeIcon(imgBig)
                .setContentIntent(viewPendingIntent)
                .addAction(R.drawable.star, " ", viewPendingIntent)
                .addAction(R.drawable.star, " ", PendingIntent.getActivity(this, 0,viewIntent, PendingIntent.FLAG_UPDATE_CURRENT))
                .setStyle(new Notification.BigPictureStyle().bigPicture(imgBig).setBigContentTitle(detail))
                .build();

        //Log.i("sportpool", "sportpool title " + title);

        mNotificationManager.notify(0, notif);
    }
}
