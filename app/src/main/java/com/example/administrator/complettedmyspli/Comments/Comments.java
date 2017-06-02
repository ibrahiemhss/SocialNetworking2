package com.example.administrator.complettedmyspli.Comments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.administrator.complettedmyspli.Likes.LikesAdapter;
import com.example.administrator.complettedmyspli.Likes.LikesModels;
import com.example.administrator.complettedmyspli.R;
import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Comments extends AppCompatActivity {
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
    String id,id_Comment;

    final String TAAG=this.getClass().getName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_likes_dialoge);

        JSON_DATA_WEB_CALL();

        recyclerView=(RecyclerView)findViewById(R.id.listLkes) ;
        Likelist = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerViewlayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(recyclerViewlayoutManager);
        recyclerViewadapterLikes = new LikesAdapter(Likelist);
        recyclerView.setAdapter(recyclerViewadapterLikes);
        recyclerView.setAdapter(adapter);

        pref=getSharedPreferences("Login2.conf", Context.MODE_PRIVATE);
        id = pref.getString("id","id");
        editor=pref.edit();

        prefComment =getSharedPreferences("prefCommentId.conf", Context.MODE_PRIVATE);
        id_Comment = prefComment.getString("post_id","post_id");


      /*  pd = ProgressDialog.show(context,"Please Wait...","Please Wait...");
        try{
            Thread.sleep(2000);
        }catch(Exception e) {

        }*/
        }
    public void JSON_DATA_WEB_CALL() {
      // bbnnnnnnnnnnnnnnn  editorComment = prefComment.edit();
        HashMap data = new HashMap();
        data.put("post_id", id_Comment);
        PostResponseAsyncTask task = new PostResponseAsyncTask(Comments.this, data, new AsyncResponse() {
            @Override
            public void processFinish(String s) {

                JSONArray array = null;
                Log.d(TAAG, s);

                editorComment.putString("post_id", s.concat("post_id"));
                JSON_PARSE_DATA_AFTER_WEBCALL_LIKES(array);

                editorComment.putString("id", "");
                editorComment.apply();


            }
    });
            task.execute(urlLikes);

        }


    public void JSON_PARSE_DATA_AFTER_WEBCALL_LIKES(JSONArray array) {

        for (int i = 0; i < array.length(); i++) {

            LikesModels lllllll = new LikesModels();

            JSONObject json = null;
            try {
                json = array.getJSONObject(i);
                id_Comment = json.getString("post_id");
                prefComment = getSharedPreferences("prefCommentId.conf", Context.MODE_PRIVATE);
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

        recyclerViewadapterLikes = new LikesAdapter((ArrayList<LikesModels>) Likelist, this);

        recyclerView.setAdapter(recyclerViewadapterLikes);
    }





}
