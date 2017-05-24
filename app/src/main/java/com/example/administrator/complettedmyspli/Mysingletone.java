package com.example.administrator.complettedmyspli;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Administrator on 24/04/2017.
 */

public class Mysingletone {

    private static Mysingletone mInstance;
    private static RequestQueue requestQueue;
    private static Context mCtx;

    public Mysingletone(Context context) {
        mCtx=context;
        requestQueue=getRequestQueue();
    }

    public RequestQueue getRequestQueue() {
       if (requestQueue == null) {
           requestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
       }return requestQueue;
   }

    public static synchronized Mysingletone getInstance(Context context){

        if (mInstance==null){
            mInstance = new Mysingletone(context);
        }return mInstance;
    }
    public <T>void addToRequestque(Request<T> request){
        DefaultRetryPolicy  retryPolicy = new DefaultRetryPolicy(0, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(retryPolicy);
        requestQueue.add(request);
    }
}
