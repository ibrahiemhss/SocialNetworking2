package com.example.administrator.complettedmyspli;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//import com.google.android.gms.common.api.GoogleApiClient;


public class Post extends AppCompatActivity {
    TextView viewemail, viewname;

    ProgressBar progressBar;

    private final int IMG_REQUEST = 1;

    final String TAAG = this.getClass().getName();

    private EditText  editsubject;

    private Button button, btnup, delete, logOutBt, showPosts;

    private JsonArrayRequest jsonArrayRequest;

    private RequestQueue requestQueue;
    String id, id2;


    private ImageView imagepost;
    private Bitmap bitmap;

    private String uploadeImageUrl = "http://devsinai.com/SocialNetwork/postWithImage.php";

    SharedPreferences pref;
    SharedPreferences pref2;

    SharedPreferences.Editor editor;
    SharedPreferences.Editor editor2;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
   // private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);



        viewemail = (TextView) findViewById(R.id.viewemail);
        viewname = (TextView) findViewById(R.id.viewname);


        imagepost = (ImageView) findViewById(R.id.imagepost);


        editsubject = (EditText) findViewById(R.id.editsubject);

        btnup = (Button) findViewById(R.id.btnup);

        delete = (Button) findViewById(R.id.delete);

        logOutBt = (Button) findViewById(R.id.logOutBt);
        showPosts = (Button) findViewById(R.id.showPosts);

        Bundle bundle = getIntent().getExtras();
        viewname.setText("Welcom " + bundle.getString("name"));
        viewemail.setText("Email : " + bundle.getString("email"));


        progressBar = (ProgressBar) findViewById(R.id.progressBar1);

        button = (Button) findViewById(R.id.button);


        pref = getSharedPreferences("Login2.conf", Context.MODE_PRIVATE);
        id = pref.getString("id", "id");

        editor = pref.edit();

        pref2 = getSharedPreferences("post.conf", Context.MODE_PRIVATE);

        editor = pref.edit();


        logOutBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor = pref.edit();
                editor.clear();
                editor.commit();
                Intent intent = new Intent(Post.this, MainActivity.class);
                startActivity(intent);
                finish();                            //Add this line to close activity immediately
            }
        });
        showPosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Post.this, ShowPosts.class);
                startActivity(intent);

            }
        });
        btnup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImages();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Posts();
                Toast.makeText(Post.this, id, Toast.LENGTH_LONG).show();
                Toast.makeText(Post.this, id2, Toast.LENGTH_LONG).show();


            }

        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bitmap.recycle();
                imagepost.setVisibility(View.GONE);

                delete.setVisibility(View.GONE);


            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
       // client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    private void Posts() {

        final StringRequest stringRequest = new StringRequest(Request.Method.POST, uploadeImageUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            progressBar.setVisibility(View.VISIBLE);

                            JSONObject jsonObject = new JSONObject(response);

                            String Response = jsonObject.getString("response");
                            Toast.makeText(Post.this, Response, Toast.LENGTH_LONG).show();
                            if(response.equals("تم النشر للصوره والمنشور")||response.equals("تم النشر")){
                                progressBar.setVisibility(View.GONE);

                            }
                            progressBar.setVisibility(View.GONE);

                            imagepost.setImageResource(0);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                Map<String, String> params = new HashMap<>();


                params.put("user_id", id);            // Add this line to send USER ID to server
                params.put("postcontent", editsubject.getText().toString().trim());
                if (bitmap != null) {
                    delete.setVisibility(View.VISIBLE);
                    params.put("imagepost", imageToString(bitmap).trim());
                    bitmap.recycle();

                }

                {

                }

                return params;
            }
        };

        Mysingletone.getInstance(Post.this).addToRequestque(stringRequest);
    }

    private void selectImages() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMG_REQUEST);
    }

    private String imageToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream);
        byte[] imgbystes = byteArrayOutputStream.toByteArray();

        return Base64.encodeToString(imgbystes, Base64.DEFAULT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null) {

            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                imagepost.setImageBitmap(bitmap);

                delete.setVisibility(View.VISIBLE);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}