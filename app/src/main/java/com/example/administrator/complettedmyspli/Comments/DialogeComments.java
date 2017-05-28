package com.example.administrator.complettedmyspli.Comments;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.complettedmyspli.Mysingletone;
import com.example.administrator.complettedmyspli.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 24/05/2017.
 */

public class DialogeComments extends Dialog {
   public List<ListComments> Mylist;
    CommentAdapter adapterRVcomment;
    RecyclerView.LayoutManager recyclerViewlayoutManager, recyclerViewlayoutManager2;
    RecyclerView.Adapter  recyclerViewadapterComment;
    ProgressBar progressBar;
    String URLcommest = "http://devsinai.com/DrSiani/GetIdComments.php";
    String URLAddComment="http://devsinai.com/DrSiani/imageUploadPostDr/AddComment.php";
    String JSON_ID = "user_id";
    String JSON_NAME = "name";
    String JIMAG_Name = "imagepost";
    String COMMENT = "comment";
    String JSON_TIME = "created_at";
    String JSON_ID_POST = "post_id";
    SwipeRefreshLayout swip;

    private JsonArrayRequest jsonArrayRequest;

    private RequestQueue requestQueue;




    private RecyclerView.LayoutManager layoutManager;

    RecyclerView recyclerView;
    SharedPreferences prefComment;
    String id_Comment;
    SharedPreferences.Editor editorComment;
    public Context c;

    public Dialog d;
    public Button yesSignoutt,noySignout;
    TextView txtAddComment;
    EditText editTextComments;


    public DialogeComments(Context a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_comments);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes((WindowManager.LayoutParams) params);

        editTextComments= (EditText) findViewById(R.id.editTextComments);
        txtAddComment= (TextView) findViewById(R.id.txtAddComment);

        recyclerView=(RecyclerView)findViewById(R.id.RvListComent) ;
        Mylist = new ArrayList<>();
        JSON_DATA_WEB_CALL();

        //swip = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        recyclerView.setHasFixedSize(true);
        recyclerViewlayoutManager = new LinearLayoutManager(c);
        recyclerView.setLayoutManager(recyclerViewlayoutManager);
        recyclerViewadapterComment = new CommentAdapter(Mylist);
        recyclerView.setAdapter(recyclerViewadapterComment);
        // textComent = (TextView) findViewById(R.id.textComent);
        // editcomment = (EditText) findViewById(R.id.editcomment);

        prefComment =c.getSharedPreferences("prefCommentId.conf", Context.MODE_PRIVATE);
        id_Comment = prefComment.getString("post_id", "post_id");
        editorComment = prefComment.edit();

        txtAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Comment=editTextComments.getText().toString();



                if(Comment.equals("")){
                    Toast.makeText(DialogeComments.this.c,"ادخل تعليق",Toast.LENGTH_LONG).show();

                }
                else {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URLAddComment,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONArray jsonArray = new JSONArray(response);
                                        JSONObject jsonObject = jsonArray.getJSONObject(0);

                                        prefComment =c.getSharedPreferences("prefCommentId.conf", Context.MODE_PRIVATE);
                                        id_Comment = prefComment.getString("post_id", "post_id");
                                        editorComment = prefComment.edit();

                                        editorComment.putString("post_id", jsonObject.getString("post_id"));


                                        editorComment.commit();


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(DialogeComments.this.c, "Error", Toast.LENGTH_LONG).show();
                            VolleyLog.e("Error: ", error.getMessage());
                            error.printStackTrace();
                        }}) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {

                            Map<String, String> params = new HashMap<String, String>();
                            params.put("comment", Comment);
                            params.put("post_id", id_Comment);



                            return params;
                        }
                    };
                    Mysingletone.getInstance(DialogeComments.this.c).addToRequestque(stringRequest);

                }}
        });

    }  public void JSON_DATA_WEB_CALL() {

        jsonArrayRequest = new JsonArrayRequest(URLcommest,

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
                        JSON_PARSE_DATA_AFTER_WEBCALL(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                })
        ;

        requestQueue = Volley.newRequestQueue(c);

        requestQueue.add(jsonArrayRequest);
    }


    public void JSON_PARSE_DATA_AFTER_WEBCALL(JSONArray array) {

        for (int i = 0; i < array.length(); i++) {

            ListComments modelsComment = new ListComments();

            JSONObject json = null;
            try {
                json = array.getJSONObject(i);
                prefComment =c.getSharedPreferences("prefCommentId.conf", Context.MODE_PRIVATE);
                id_Comment = prefComment.getString("post_id", "post_id");
                editorComment = prefComment.edit();

                editorComment.putString("post_id", json.getString("post_id"));

////              id2 = pref.getString("user_id", "user_id");
                final String post_id=json.getString("post_id").toString();

                if(id_Comment.equals(post_id)) {
                    modelsComment.setTextNamesss(json.getString("name"));

                    modelsComment.setTextCommentsss(json.getString("comment"));
                    modelsComment.setTextPost_id(json.getString("post_id"));


                }

            } catch (JSONException e) {

                e.printStackTrace();
            }
            Mylist.add(modelsComment);
        }

        recyclerViewadapterComment = new CommentAdapter((ArrayList<ListComments>) Mylist, c);

        recyclerView.setAdapter(recyclerViewadapterComment);
    }
    String id_post;
    public void post_id(String id_post){
        this.id_post=id_post;
    }



}
