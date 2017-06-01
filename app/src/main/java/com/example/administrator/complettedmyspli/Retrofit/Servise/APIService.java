package com.example.administrator.complettedmyspli.Retrofit.Servise;

import com.example.administrator.complettedmyspli.Retrofit.MSG;
import com.example.administrator.complettedmyspli.Retrofit.Models.CommentsRETF;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Administrator on 01/06/2017.
 */

public interface APIService {
    @GET("GetIdComments.php")
    Call<List<CommentsRETF>> getCOMMENTSDetails();

    @FormUrlEncoded
    @POST("getIdLikes.php")
    String post_id(@Field("post_id") String Post_id);



}
