package com.example.administrator.complettedmyspli.Likes;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.WindowManager;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.complettedmyspli.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LikesDialoge  extends Dialog {
    private LikesAdapter adapter;
    RecyclerView recyclerView;
    private RequestQueue mRequestQueue;
    private List<LikesModels> Likelist ;
    private ProgressDialog pd;
    SharedPreferences prefComment,pref;
    SharedPreferences.Editor editorComment,editor;
    private JsonArrayRequest jsonArrayRequest;
    RecyclerView.LayoutManager recyclerViewlayoutManager;
    RecyclerView.Adapter  recyclerViewadapterLikes;
    private RequestQueue requestQueue;
    String urlLikes = "http://devsinai.com/SocialNetwork/getIdLikes.php";

    final String TAAG=this.getClass().getName();



    String id,id_Comment;

    Context context;

    public LikesDialoge(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        this.context = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_likes_dialoge);


        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes((WindowManager.LayoutParams) params);

        JSON_DATA_WEB_CALL();

        recyclerView=(RecyclerView)findViewById(R.id.listLkes) ;
       Likelist = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerViewlayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(recyclerViewlayoutManager);
        recyclerViewadapterLikes = new LikesAdapter(Likelist);
        recyclerView.setAdapter(recyclerViewadapterLikes);


        pref=context.getSharedPreferences("Login2.conf", Context.MODE_PRIVATE);
        id = pref.getString("id","id");
        editor=pref.edit();

        prefComment =context.getSharedPreferences("prefCommentId.conf", Context.MODE_PRIVATE);
        Log.d(TAAG,prefComment.getString("post_id",""));
        editorComment = prefComment.edit();

      /*  pd = ProgressDialog.show(context,"Please Wait...","Please Wait...");
        try{
            Thread.sleep(2000);
        }catch(Exception e) {

        }*/
    } public void JSON_DATA_WEB_CALL() {

        jsonArrayRequest = new JsonArrayRequest(urlLikes,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        JSONObject jsonObject= null;
                        try {
                            jsonObject = response.getJSONObject(0);

                            editorComment.putString("post_id",jsonObject.getString("post_id"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        JSON_PARSE_DATA_AFTER_WEBCALL_LIKES(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                })
        ;

        requestQueue = Volley.newRequestQueue(context);

        requestQueue.add(jsonArrayRequest);
    }

    public void JSON_PARSE_DATA_AFTER_WEBCALL_LIKES(JSONArray array) {

        for (int i = 0; i < array.length(); i++) {

            LikesModels lllllll = new LikesModels();

            JSONObject json = null;
            try {
                json = array.getJSONObject(i);
                id_Comment = json.getString("post_id");
                prefComment = context.getSharedPreferences("prefCommentId.conf", Context.MODE_PRIVATE);
                id_Comment = prefComment.getString("post_id", "post_id");

                editorComment = prefComment.edit();

                editorComment.putString("post_id", json.getString("post_id"));

                editorComment.commit();

                lllllll.setName(json.getString("name"));

                lllllll.setIdPostLikes(json.getString("post_id"));



            } catch (JSONException e) {

                e.printStackTrace();
            }
            Likelist.add(lllllll);
        }

        recyclerViewadapterLikes = new LikesAdapter((ArrayList<LikesModels>) Likelist, context);

        recyclerView.setAdapter(recyclerViewadapterLikes);
    }

    String id_post;

    public void post_id(String id_post){
        this.id_post=id_post;
    }




    }
