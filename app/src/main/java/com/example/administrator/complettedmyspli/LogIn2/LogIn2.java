package com.example.administrator.complettedmyspli.LogIn2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.example.administrator.complettedmyspli.Post;
import com.example.administrator.complettedmyspli.R;
import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import java.util.HashMap;

public class LogIn2 extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private EditText emailedt,passwordedt;
    private CheckBox rememberme;
    private Button logIn;

    final String TAAG=this.getClass().getName();
       SharedPreferences pref;
    SharedPreferences.Editor editor;
    boolean checkFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in2);

        emailedt = (EditText) findViewById(R.id.emailedt);
        passwordedt = (EditText) findViewById(R.id.passwordedt);

        rememberme = (CheckBox) findViewById(R.id.rememberme);



        logIn = (Button) findViewById(R.id.logIn);


        pref=getSharedPreferences("Login2.conf", Context.MODE_PRIVATE);
         editor=pref.edit();

        final String username=pref.getString("user_name","");
        final String password=pref.getString("password","");
        String id =pref.getString("id","");

        HashMap data = new HashMap();
        data.put("txtUsername", username);
        data.put("txtPassword", password);
        data.put("id","");

        if(!(username.equals("")&&password.equals("")&&id.equals(""))){
            PostResponseAsyncTask task = new PostResponseAsyncTask(LogIn2.this, data, new AsyncResponse() {
                @Override
                public void processFinish(String s) {



                    Log.d(TAAG, s);
                    if (s.contains("success")) {
                        if(checkFlag==true){
                            editor.putString("user_name", emailedt.getText().toString());
                            editor.putString("password", passwordedt.getText().toString());
                            editor.putString("id","");
                            editor.apply();

                        }


                        Intent intent = new Intent(LogIn2.this, Post.class);
                        startActivity(intent);
                    }
                }
            });
            task.execute("http://devsinai.com/DrSiani/Login2.php");

        }

        rememberme.setOnCheckedChangeListener(this);
        checkFlag=rememberme.isChecked();
        Log.d(TAAG,"checkFlag"+checkFlag);


        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap data = new HashMap();
                Log.d(TAAG,pref.getString("user_name",""));
                Log.d(TAAG,pref.getString("password",""));
                Log.d(TAAG,pref.getString("id",""));




                data.put("txtUsername", emailedt.getText().toString());
                data.put("txtPassword", passwordedt.getText().toString());
                data.put("id","");

                final PostResponseAsyncTask task = new PostResponseAsyncTask(LogIn2.this, data, new AsyncResponse() {
                    @Override
                    public void processFinish(String s) {

                        editor.putString("user_name", emailedt.getText().toString());
                        editor.putString("password", passwordedt.getText().toString());

                        Log.d(TAAG, s);
                        if (s.contains("success")) {
                            if(checkFlag==true){
                                editor.putString("user_name", emailedt.getText().toString());
                                editor.putString("password", passwordedt.getText().toString());
                                editor.putString("id","");
                                editor.apply();

                            }


                            Intent intent = new Intent(LogIn2.this, Post.class);
                            Bundle bundle=new Bundle();

                            bundle.putString("name",s.replace("name","name"));
                            bundle.putString("name",s.replace("name","name"));
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    }
                });
                task.execute("http://devsinai.com/DrSiani/Login2.php");

            }

        });

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        checkFlag=isChecked;
        Log.d(TAAG,"checkFlag"+checkFlag);

    }
}
