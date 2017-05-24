package com.example.administrator.complettedmyspli.Comments;

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
import com.example.administrator.complettedmyspli.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Comments extends AppCompatActivity {
    List<ListComments> Mylist;
    CommentAdapter adapterRVcomment;

    RecyclerView.LayoutManager recyclerViewlayoutManager, recyclerViewlayoutManager2;
    RecyclerView.Adapter  recyclerViewadapterComment;
    ProgressBar progressBar;
    String URL = "http://devsinai.com/DrSiani/commentsList.php";
    String JSON_ID = "user_id";
    String JSON_NAME = "name";
    String JIMAG_Name = "imagepost";
    String COMMENT = "comment";
    String JSON_TIME = "created_at";
    String JSON_ID_POST = "post_id";
    SwipeRefreshLayout swip;

    private JsonArrayRequest jsonArrayRequest;

    private RequestQueue requestQueue;
    String id, id2;




    private RecyclerView.LayoutManager layoutManager;

    RecyclerView recyclerView;
    SharedPreferences pref;
    SharedPreferences pref2;

    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        recyclerView=(RecyclerView)findViewById(R.id.RvListComent) ;
        Mylist = new ArrayList<>();
        JSON_DATA_WEB_CALL();

        //swip = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        recyclerView.setHasFixedSize(true);
        recyclerViewlayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(recyclerViewlayoutManager);
        recyclerViewadapterComment = new CommentAdapter(Mylist);
        recyclerView.setAdapter(recyclerViewadapterComment);





    }  public void JSON_DATA_WEB_CALL() {

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

            ListComments modelsComment = new ListComments();

            JSONObject json = null;
            try {
                json = array.getJSONObject(i);

//                id2 = pref.getString("user_id", "user_id");

//                editor.putString("user_id", json.getString("user_id"));

                modelsComment.setName(json.getString(JSON_NAME));

                modelsComment.setComments(json.getString(COMMENT));


            } catch (JSONException e) {

                e.printStackTrace();
            }
            Mylist.add(modelsComment);
        }

        recyclerViewadapterComment = new CommentAdapter((ArrayList<ListComments>) Mylist, this);

        recyclerView.setAdapter(recyclerViewadapterComment);
    }

    public void COMMENTS_JSON_DATA_WEB_CALL() {

    }
}

