package com.example.administrator.complettedmyspli;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class justPost extends AppCompatActivity implements View.OnClickListener{


    private Button chooseButn,uploadButn;
    private EditText edetname,editsubject;
    private ImageView imageview22;
    private final int IMG_REQUEST=1;
    private Bitmap bitmap;
    private String uploadeImageUrl="http://devsinai.com/DrSiani/imageUploadPostDr/uploadimagepost.php";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_just_post);

        chooseButn= (Button) findViewById(R.id.chooseButn);
        uploadButn= (Button) findViewById(R.id.uploadButn);

        edetname= (EditText) findViewById(R.id.edetname);
        editsubject= (EditText) findViewById(R.id.editsubject);

        imageview22= (ImageView) findViewById(R.id.imageview22);

        chooseButn.setOnClickListener(this);
        uploadButn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.chooseButn:
                selectImage();
                break;
            case R.id.uploadButn:
                uploadImage();
                break;
        }
    }
    private void selectImage(){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMG_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==IMG_REQUEST&&resultCode==RESULT_OK&&data!=null)
        {

            Uri path=data.getData();
            try {
                bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                imageview22.setImageBitmap(bitmap);
                imageview22.setVisibility(View.VISIBLE);
                edetname.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void uploadImage()
    {

        StringRequest stringRequest=new StringRequest(Request.Method.POST, uploadeImageUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String Response=jsonObject.getString("response");
                            Toast.makeText(justPost.this,Response,Toast.LENGTH_LONG).show();
                            imageview22.setImageResource(0);
                            imageview22.setVisibility(View.GONE);
                            edetname.setText("");
                            edetname.setVisibility(View.GONE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params =new HashMap<>();
                params.put("imagepost",edetname.getText().toString().trim());

                params.put("image",imageToString(bitmap));
                return params;
            }
        };

        Mysingletone.getInstance(justPost.this).addToRequestque(stringRequest);
    }
    private String imageToString(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgbystes = byteArrayOutputStream.toByteArray();

        return Base64.encodeToString(imgbystes,Base64.DEFAULT);
    }
}

