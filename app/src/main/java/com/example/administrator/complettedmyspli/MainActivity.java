package com.example.administrator.complettedmyspli;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.example.administrator.complettedmyspli.Comments.DialogeComments;
import com.example.administrator.complettedmyspli.registrationwithvolley.VRegestraion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity /*implements CompoundButton.OnCheckedChangeListener*/ {

     private final static String  LOGIN_URL = "http://devsinai.com/DrSiani/login.inc.php";


     EditText editTextUsername;
    EditText editTextPassword;
     Button buttonLogin,btnRegister,gotologin2,goToPostRecycler;
     String username;
    String password;
    String id;
    AlertDialog.Builder builder;
    SharedPreferences pref;
    private CheckBox rememberme;

    SharedPreferences.Editor editor;
    boolean checkFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        editTextUsername = (EditText) findViewById(R.id.username_txt);
        editTextPassword = (EditText) findViewById(R.id.password_txt);

        buttonLogin = (Button) findViewById(R.id.login_btn);
        btnRegister= (Button) findViewById(R.id.register_btn);
        gotologin2= (Button) findViewById(R.id.gotologin2);


        rememberme = (CheckBox) findViewById(R.id.rememberme);

      //  rememberme.setOnCheckedChangeListener(this);
        final DialogeComments dialogeComments=new DialogeComments(this);
        checkFlag=rememberme.isChecked();
        gotologin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogeComments.show();
            }
        });



        builder=new  AlertDialog.Builder(this);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,VRegestraion.class));
            }
        });
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                username=editTextUsername.getText().toString();
                password=editTextPassword.getText().toString();

                if(username.equals("")||password.equals("")){
                    builder.setTitle("somthing wrong");
                    desplayalert("Enter a valid username and password");
                }
                else{
                    StringRequest stringRequest=new StringRequest(Request.Method.POST, LOGIN_URL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    try {
                                        JSONArray jsonArray=new JSONArray(response);
                                        JSONObject jsonObject=jsonArray.getJSONObject(0);

                                        pref=getSharedPreferences("Login2.conf", Context.MODE_PRIVATE);
                                        id = pref.getString("id","id");

                                        editor=pref.edit();

                                        editor.putString("user_name", username);
                                        editor.putString("password", password);
                                        editor.putString("id",jsonObject.getString("id"));


                                        editor.commit();

                                        String code=jsonObject.getString("code");
                                        if(code.equals("login_failed")){
                                            builder.setTitle("Login Error");
                                            desplayalert(jsonObject.getString("message"));
                                        }
                                        else {
                                            Intent intent=new Intent(MainActivity.this,Post.class);
                                            Bundle bundle=new Bundle();
                                            bundle.putString("name",jsonObject.getString("name"));
                                            bundle.putString("email",jsonObject.getString("email"));
                                            intent.putExtras(bundle);
                                            startActivity(intent);
                                            Toast.makeText(MainActivity.this,id,Toast.LENGTH_LONG).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_LONG).show();
                            VolleyLog.e("Error: ", error.getMessage());
                            error.printStackTrace();

                        }
                    }
                    )
                    {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {

                            Map<String,String> params=new HashMap<String, String>();
                            params.put("user_name",username);
                            params.put("password",password);

                            return params;
                        }
                    };
                    Mysingletone.getInstance(MainActivity.this).addToRequestque(stringRequest);
                }

            }
        });

    }
    private void desplayalert( String message){

        builder.setMessage(message);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                editTextUsername.setText("");
                editTextPassword.setText("");

            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }

/*
public void startActivty(){

    SharedPreferences preferences=PreferenceManager.getDefaultSharedPreferences(this);
    String name=preferences.getString("user_name","");
    String pass=preferences.getString("password","");
    HashMap data = new HashMap();
    data.put("txtUsername", username);
    data.put("txtPassword", password);
    data.put("id","");

    if(!(name.equalsIgnoreCase("")&&pass.equalsIgnoreCase("")))
    {

        StringRequest stringRequest=new StringRequest(Request.Method.POST, LOGIN_URL,
                new Response.Listener<String>() {


                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);


                            String code = jsonObject.getString("code");


                            if (code.equals("login_success")) {
                                if(checkFlag==true){
                                    editor.putString("user_name", editTextUsername.getText().toString());
                                    editor.putString("password", editTextPassword.getText().toString());
                                    editor.putString("id","");
                                    editor.apply();

                                }


                            }
                            Intent intent = new Intent(MainActivity.this, Post.class);

                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


    }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }}

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        checkFlag=isChecked;
    }
*/
}