package com.example.administrator.complettedmyspli.Retrofit.NetWork;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 01/06/2017.
 */

public class APICleint {
    public static final String BASE_URL = "http://devsinai.com/SocialNetwork/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();;
        }
        return  retrofit;
    }
}
