package com.example.administrator.complettedmyspli.FirebaseMessags;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.iid.FirebaseInstanceId;

/**
 * Created by Administrator on 21/06/2017.
 */

public class FirebaseInstanceIdServices extends com.google.firebase.iid.FirebaseInstanceIdService {
    SharedPreferences prefToken;

    @Override
    public void onTokenRefresh() {

        String token = FirebaseInstanceId.getInstance().getToken();

        registerToken(token);
        prefToken=getSharedPreferences("register.conf", Context.MODE_PRIVATE);


        prefToken = getApplicationContext().getSharedPreferences("register.conf", Context.MODE_PRIVATE);
        SharedPreferences.Editor editorToken = prefToken.edit();
        editorToken.putString("Token", token);
        editorToken.commit();

    }

    private void registerToken(String token) {

    }
}