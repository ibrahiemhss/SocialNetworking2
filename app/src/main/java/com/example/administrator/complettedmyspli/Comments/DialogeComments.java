package com.example.administrator.complettedmyspli.Comments;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.example.administrator.complettedmyspli.Retrofit.Models.CommentsRETF;
import com.example.administrator.complettedmyspli.Retrofit.NetWork.APICleint;
import com.example.administrator.complettedmyspli.Retrofit.Servise.APIService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Administrator on 24/05/2017.
 */

public class DialogeComments extends Dialog {
   public List<ListComments> Mylist;
    CommentAdapter adapterRVcomment;
    RecyclerView.LayoutManager recyclerViewlayoutManager, recyclerViewlayoutManager2;
    RecyclerView.Adapter  recyclerViewadapterComment;
    String URLcommest = "http://devsinai.com/SocialNetwork/GetIdComments.php";
    String URLAddComment="http://devsinai.com/SocialNetwork/AddComment.php";
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
    SharedPreferences prefComment,pref;
    String id,id_Comment;
    SharedPreferences.Editor editorComment,editor;
    public Context c;

    public Dialog d;
    public Button yesSignoutt,noySignout;
    TextView txtAddComment;
    EditText editTextComments;
    ProgressBar progressBar;

    final String TAAG=this.getClass().getName();

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
        progressBar= (ProgressBar) findViewById(R.id.progressbarComment);

        recyclerView=(RecyclerView)findViewById(R.id.RvListComent) ;
        Mylist = new ArrayList<>();
       JSON_DATA_WEB_CALL();

        //swip = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        recyclerView.setHasFixedSize(true);
        recyclerViewlayoutManager = new LinearLayoutManager(c);
        recyclerView.setLayoutManager(recyclerViewlayoutManager);
      //  recyclerViewadapterComment = new CommentAdapter(Mylist);
        recyclerView.setAdapter(recyclerViewadapterComment);
        // textComent = (TextView) findViewById(R.id.textComent);
        // editcomment = (EditText) findViewById(R.id.editcomment);
        pref=c.getSharedPreferences("Login2.conf", Context.MODE_PRIVATE);
        id = pref.getString("id","id");
        editor=pref.edit();
        prefComment =c.getSharedPreferences("prefCommentId.conf", Context.MODE_PRIVATE);
        id_Comment = prefComment.getString("post_id", "post_id");
        editorComment = prefComment.edit();

        txtAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                progressBar.setVisibility(View.VISIBLE);
                final String COMMENT=editTextComments.getText().toString();
                final String id_user=pref.getString("id","");
                final String post_id =prefComment.getString("post_id","");


                if(COMMENT.equals("")){
                    Toast.makeText(DialogeComments.this.c,"ادخل تعليق",Toast.LENGTH_LONG).show();

                }
                else {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URLAddComment,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                        progressBar.setVisibility(View.GONE);
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
                            params.put("user_id", id_user);
                            params.put("post_id", post_id);
                            params.put("comment", COMMENT);


                            return params;
                        }
                    };
                    Mysingletone.getInstance(DialogeComments.this.c).addToRequestque(stringRequest);

                }}
        });

    }
    private void getCOMMENTSDetails(){
       try{

           final APIService service= APICleint.getClient().create(APIService.class);

           Call<List<CommentsRETF>> call=service.getCOMMENTSDetails();
           call.enqueue(new Callback<List<CommentsRETF>>() {
               @Override
               public void onResponse(Call<List<CommentsRETF>> call, retrofit2.Response<List<CommentsRETF>> response) {
                  String post_id= service.post_id("post_id");

                   List<CommentsRETF> commentsRETFs=response.body();
                   editorComment.putString("post_id",post_id);
                 recyclerView=(RecyclerView)findViewById(R.id.RvListComent) ;
                   Mylist = new ArrayList<>();
                   // JSON_DATA_WEB_CALL();

                   //swip = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
                   recyclerView.setHasFixedSize(true);
                   recyclerViewlayoutManager = new LinearLayoutManager(c);
                   recyclerView.setLayoutManager(recyclerViewlayoutManager);
                   recyclerViewadapterComment = new CommentAdapter(commentsRETFs);
                   recyclerView.setAdapter(recyclerViewadapterComment);

               }

               @Override
               public void onFailure(Call<List<CommentsRETF>> call, Throwable t) {

                   Log.d("onFailure",t.toString());
               }
           });

       }
       catch (Exception  e){

       }

    }


    public void JSON_DATA_WEB_CALL() {

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

                id_Comment = prefComment.getString("post_id", id_post);
                editorComment = prefComment.edit();

              //  editorComment.putString("post_id", json.getString("post_id"));

////              id2 = pref.getString("user_id", "user_id");
                final String post_id=json.getString("post_id").toString();


                    modelsComment.setTextNamesss(json.getString("name"));

                    modelsComment.setTextCommentsss(json.getString("comment"));
                   // modelsComment.setTextPost_id(json.getString("post_id"));




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
