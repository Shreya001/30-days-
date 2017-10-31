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

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;

import java.util.ArrayList;
import java.util.List;

public class Geofencing implements ResultCallback{
    private static final int GEOFENCE_RADIUS = 15;
    private static final int GEOFENCE_TIMEOUT = 24*60*60*1000;
    private static final String TAG = Geofencing.class.getSimpleName();

    private Context mContext;
    private GoogleApiClient mClient;
    private List<Geofence> geofenceList;
    private PendingIntent mGeofencePendingIntent;

    Geofencing(Context context , GoogleApiClient googleApiClient){
        mClient = googleApiClient;
        mContext = context;
        geofenceList = new ArrayList<>();
        mGeofencePendingIntent =null;
    }

    public void registerGeofences(){
        if(mClient == null || !mClient.isConnected() || geofenceList == null || geofenceList.size() == 0)
            return;
        try {
            LocationServices.GeofencingApi.addGeofences(mClient, getRequest(), getPEndingIntent()).setResultCallback(this);
        }catch (SecurityException e){
            Log.i(TAG,"ERROR");
        }
    }

    public void unregisterGeofences(){
        if(mClient == null || !mClient.isConnected())
            return;
        try {
            LocationServices.GeofencingApi.removeGeofences(
                    mClient,
                    getPEndingIntent()
            ).setResultCallback(this);
        }catch (SecurityException e){
            Log.e(TAG,e.getMessage());
        }


    }

    public void updateGeofenceList(PlaceBuffer places){
        geofenceList =  new ArrayList<>();

        if(places == null || places.getCount()==0)return;

        for(Place place : places){
            String placeUID = place.getId().toString();
            double placeLAT = place.getLatLng().latitude;
            double placeLOG = place.getLatLng().longitude;

            Geofence geofence = new Geofence.Builder()
                    .setRequestId(placeUID)
                    .setCircularRegion(placeLAT,placeLOG,GEOFENCE_RADIUS)
                    .setExpirationDuration(GEOFENCE_TIMEOUT)
                    .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT)
                    .build();

            geofenceList.add(geofence);

        }
    }

    private GeofencingRequest getRequest (){
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
                .addGeofences(geofenceList);
        return builder.build();

    }

    private PendingIntent getPEndingIntent() {
        if (mGeofencePendingIntent != null) {
            return mGeofencePendingIntent;
        }

        Intent intent = new Intent(mContext, GeofenceBroadcastReceiver.class);
        mGeofencePendingIntent = PendingIntent.getBroadcast(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return mGeofencePendingIntent;

    }


    @Override
    public void onResult(@NonNull Result result) {
        Log.e(TAG,String.format("ERROR",result.getStatus().toString()));
    }
}
