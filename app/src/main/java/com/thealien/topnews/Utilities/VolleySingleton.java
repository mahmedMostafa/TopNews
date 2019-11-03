package com.thealien.topnews.Utilities;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

//the idea for the volley singleton is to create one object of it with only one request queue not more for memory usage
public class VolleySingleton {

    private static VolleySingleton mInstance;

    private RequestQueue mRequestQueue;

    //by making this constructor private only this class can access it
    private VolleySingleton(Context context) {
        //we make the context for the whole life time of the application not just a single activity
        mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());

    }

    //this method is to access the VolleySingleton object once and at one thread at a time
    public static synchronized VolleySingleton getInstance(Context context) {

        if (mInstance == null) {
            mInstance = new VolleySingleton(context);
        }

        return mInstance;
    }

    //this method is to access the request queue from the out side
    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }
}