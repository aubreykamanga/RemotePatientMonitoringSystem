package com.example.rpms;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * The type Fire app.
 */
public class FireApp extends Application {

    @Override
    public void onCreate(){
        super.onCreate();

        Firebase.setAndroidContext(this);
    }

}
