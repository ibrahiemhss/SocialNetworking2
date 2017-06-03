package com.example.administrator.complettedmyspli;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ProgressBar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.complettedmyspli.Comments.ListComments;
import com.example.administrator.complettedmyspli.RecyclerPosts.Models;
import com.example.administrator.complettedmyspli.RecyclerPosts.RecyclerViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowPosts extends AppCompatActivity {
    List<Models> GetDataAdapter13;
    List<ListComments> listComment;

    RecyclerView recyclerView, recyclerViewComment;
    RecyclerView.LayoutManager recyclerViewlayoutManager, recyclerViewlayoutManager2;
    RecyclerViewAdapter recyclerViewadapter;
    ProgressBar progressBar;
    String URL = "http://devsinai.com/SocialNetwork/show_list_posts.php";

    String JSON_ID = "user_id";
    String JSON_NAME = "name";
    String JIMAG_Name = "imagepost";
    String JSON_SUBJECT = "postcontent";
    String JSON_TIME = "created_at";
    String JSON_ID_POST = "post_id";
    String JSON_LIKE_COUNTS = "likes_count";
    String JSON_isLiked = "isLiked";

    SwipeRefreshLayout swip;

    private RequestQueue requestQueue;
    String id, id2;


    SharedPreferences pref;
    SharedPreferences pref2;

    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_posts);

        GetDataAdapter13 = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView1);

        swip = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        recyclerView.setHasFixedSize(true);
        recyclerViewlayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(recyclerViewlayoutManager);
        recyclerViewadapter = new RecyclerViewAdapter(GetDataAdapter13,this);
        recyclerView.setAdapter(recyclerViewadapter);


        pref = getSharedPreferences("Login2.conf", Context.MODE_PRIVATE);
        id = pref.getString("id", "id");

        editor = pref.edit();
        JSON_DATA_WEB_CALL();

        swip.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                JSON_DATA_WEB_CALL();

            }
        });

    }

    public void JSON_DATA_WEB_CALL() {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray jsonArray=new JSONArray(response);
                            JSON_PARSE_DATA_AFTER_WEBCALL(jsonArray);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params=new HashMap<String, String>();
                params.put("user_id", id);


                return params;
            }
        };

        requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);
    }


    public void JSON_PARSE_DATA_AFTER_WEBCALL(JSONArray array) {

        for (int i = 0; i < array.length(); i++) {

            Models GetDataAdapter2 = new Models();

            JSONObject json = null;
            try {
                json = array.getJSONObject(i);

                id2 = pref.getString("user_id", "user_id");

                editor.putString("user_id", json.getString("user_id"));

                GetDataAdapter2.setName(json.getString(JSON_NAME));

                GetDataAdapter2.setSubject(json.getString(JSON_SUBJECT));
                GetDataAdapter2.setImageItems(json.getString(JIMAG_Name));
                GetDataAdapter2.setTimeapost(json.getString(JSON_TIME));
                GetDataAdapter2.setId_user(json.getString(JSON_ID));
                GetDataAdapter2.setId_post(json.getString(JSON_ID_POST));
                String isliked = json.getString(JSON_isLiked);
                GetDataAdapter2.setEditubdate(json.getString("postcontent"));

                Log.v("ARRAY_RESPONSE",isliked);
                if (isliked.equals("true")) {
                    GetDataAdapter2.setisLiked(true);
                    Log.v("ARRAY_RESPONSE", " true " + "at :" + JSON_ID + " " + json.getString(JSON_ID) + " " + JSON_NAME + " " + json.getString(JSON_NAME) + " " + JSON_ID_POST + " " + json.getString(JSON_ID_POST) + "    id2" + " " + id2 + "  id" + id);
                } else {
                    GetDataAdapter2.setisLiked(false);
                    Log.v("ARRAY_RESPONSE", " false " + "at :" + JSON_ID + " " + json.getString(JSON_ID) + " " + JSON_NAME + " " + json.getString(JSON_NAME) + " " + JSON_ID_POST + " " + json.getString(JSON_ID_POST) + "    id2" + " " + id2 + "  id" + id);

                }

                GetDataAdapter2.setLikeCounts(json.getString(JSON_LIKE_COUNTS));


            } catch (JSONException e) {

                e.printStackTrace();
            }
            GetDataAdapter13.add(GetDataAdapter2);
        }
        recyclerViewadapter.setArray(GetDataAdapter13);
        recyclerViewadapter.notifyDataSetChanged();
        /*recyclerViewadapter = new RecyclerViewAdapter((ArrayList<Models>) GetDataAdapter13, this);

        recyclerView.setAdapter(recyclerViewadapter);*/
    }


}