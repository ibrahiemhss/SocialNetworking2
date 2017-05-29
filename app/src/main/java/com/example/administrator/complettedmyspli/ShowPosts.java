package com.example.administrator.complettedmyspli;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.complettedmyspli.RecyclerPosts.Models;
import com.example.administrator.complettedmyspli.RecyclerPosts.RecyclerViewAdapter;
import com.example.administrator.complettedmyspli.Comments.ListComments;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ShowPosts extends AppCompatActivity {
    List<Models> GetDataAdapter13;
    List<ListComments> listComment;

    RecyclerView recyclerView, recyclerViewComment;
    RecyclerView.LayoutManager recyclerViewlayoutManager, recyclerViewlayoutManager2;
    RecyclerView.Adapter recyclerViewadapter, recyclerViewadapterComment;
    ProgressBar progressBar;
    String URL = "http://devsinai.com/SocialNetwork/show_list_posts.php";
    String JSON_ID = "user_id";
    String JSON_NAME = "name";
    String JIMAG_Name = "imagepost";
    String JSON_SUBJECT = "postcontent";
    String JSON_TIME = "created_at";
    String JSON_ID_POST = "post_id";
    SwipeRefreshLayout swip;

    private JsonArrayRequest jsonArrayRequest;

    private RequestQueue requestQueue;
    String id, id2;


    SharedPreferences pref;
    SharedPreferences pref2;

    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_posts);

        JSON_DATA_WEB_CALL();
        GetDataAdapter13 = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView1);

        swip = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        recyclerView.setHasFixedSize(true);
        recyclerViewlayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(recyclerViewlayoutManager);
        recyclerViewadapter = new RecyclerViewAdapter(GetDataAdapter13);
        recyclerView.setAdapter(recyclerViewadapter);








        pref = getSharedPreferences("Login2.conf", Context.MODE_PRIVATE);
        id = pref.getString("id", "id");

        editor = pref.edit();

        swip.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                JSON_DATA_WEB_CALL();

            }
        });

    }

    public void JSON_DATA_WEB_CALL() {

        jsonArrayRequest = new JsonArrayRequest(URL,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        //swip.setVisibility(View.GONE);

                        JSON_PARSE_DATA_AFTER_WEBCALL(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(jsonArrayRequest);
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


            } catch (JSONException e) {

                e.printStackTrace();
            }
            GetDataAdapter13.add(GetDataAdapter2);
        }

        recyclerViewadapter = new RecyclerViewAdapter((ArrayList<Models>) GetDataAdapter13, this);

        recyclerView.setAdapter(recyclerViewadapter);
    }



}