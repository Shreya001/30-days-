package com.example.android.shushme;

/*
* Copyright (C) 2017 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*  	http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.os.Build;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

public class GeofenceBroadcastReceiver extends BroadcastReceiver {

    public static final String TAG = GeofenceBroadcastReceiver.class.getSimpleName();

    /***
     * Handles the Broadcast message sent when the Geofence Transition is triggered
     * Careful here though, this is running on the main thread so make sure you start an AsyncTask for
     * anything that takes longer than say 10 second to run
     *
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {

        GeofencingEvent event = GeofencingEvent.fromIntent(intent);

        int trasitisiontype = event.getGeofenceTransition();

        if(trasitisiontype == Geofence.GEOFENCE_TRANSITION_ENTER){
                setRingerMode(context, AudioManager.RINGER_MODE_SILENT);
        }else if(trasitisiontype == Geofence.GEOFENCE_TRANSITION_EXIT){
                setRingerMode(context, AudioManager.RINGER_MODE_NORMAL);
        }else{
                Log.i(TAG, String.format("Unkown transition: %d",trasitisiontype));
        }

    }

    public void setRingerMode(Context context, int mode){
        NotificationManager nm = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT<24 || (Build.VERSION.SDK_INT>=25 && !nm.isNotificationPolicyAccessGranted())){
            AudioManager am = (AudioManager) context.getSystemService(context.AUDIO_SERVICE);
            am.setRingerMode(mode);        }
    }
}
