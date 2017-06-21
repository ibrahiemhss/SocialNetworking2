package com.example.administrator.complettedmyspli;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.complettedmyspli.FirebaseMessags.FirebaseInstanceIdServices;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Register extends AppCompatActivity {
    EditText username,Password,adress,email;
    Button register;
    String finalurl;
    FirebaseInstanceIdServices firebaseInstanceIdService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = (EditText) findViewById(R.id.username);
        Password = (EditText) findViewById(R.id.Password);
        adress = (EditText) findViewById(R.id.address);
        email = (EditText) findViewById(R.id.email);

        register = (Button) findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String users = username.getText().toString();
                String pass = Password.getText().toString();
                String em = email.getText().toString();
                String add = adress.getText().toString();

                String url = "http://devsinai.com/insertusers.php";
                String usernamekey = "?username=";
                String passwordkey = "&password=";
                String emailkey = "&email=";
                String adresskey = "&adress=";

                try {
                    finalurl = url + usernamekey + URLEncoder.encode(users, "UTF-8") + passwordkey
                            + URLEncoder.encode(pass, "UTF-8") +
                            emailkey + URLEncoder.encode(em, "UTF-8") + adresskey + URLEncoder.encode(add, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            URL insertfileurl = new URL(finalurl);
                            HttpURLConnection insertURLConnection = (HttpURLConnection) insertfileurl.openConnection();
                            InputStreamReader resultstreamReader = new InputStreamReader(insertURLConnection.getInputStream());
                            BufferedReader bufferedReader = new BufferedReader(resultstreamReader);
                            final String result = bufferedReader.readLine();

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    Toast.makeText(Register.this, result, Toast.LENGTH_LONG).show();
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }
                };

                Thread thread = new Thread(runnable);
                thread.start();

            }
        });
    }
}
