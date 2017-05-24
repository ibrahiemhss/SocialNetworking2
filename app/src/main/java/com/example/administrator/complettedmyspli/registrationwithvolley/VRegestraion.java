package com.example.administrator.complettedmyspli.registrationwithvolley;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.administrator.complettedmyspli.Mysingletone;
import com.example.administrator.complettedmyspli.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class VRegestraion extends AppCompatActivity {

    Button reg_bn;
    EditText Name, Email, UserName, Password, ConfirmPassword;
    String name, email, username, password, confirmpassword;
    String reg_url = "http://devsinai.com/DrSiani/register.php";
    android.app.AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vregestraion);

        reg_bn = (Button) findViewById(R.id.reg_reigester);
        Name = (EditText) findViewById(R.id.reg_name);
        Email = (EditText) findViewById(R.id.reg_mail);
        UserName = (EditText) findViewById(R.id.reg_usernam);
        Password = (EditText) findViewById(R.id.reg_password);
        ConfirmPassword = (EditText) findViewById(R.id.reg_confirmpass);

        builder = new android.app.AlertDialog.Builder(VRegestraion.this);


        reg_bn.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {

                                          name = Name.getText().toString();
                                          email = Email.getText().toString();
                                          username = UserName.getText().toString();
                                          password = Password.getText().toString();
                                          confirmpassword = ConfirmPassword.getText().toString();

                                          if (name.equals("") || email.equals("") || username.equals("") || password.equals("") || confirmpassword.equals("")) {
                                              builder.setTitle("something went wrong .....");
                                              builder.setMessage("please fill all fields");
                                              diplayAlert("input_error");
                                          } else {
                                              if (!(password.equals(confirmpassword))) {
                                                  builder.setTitle("something went wrong .....");
                                                  builder.setMessage("not correct");
                                                  diplayAlert("input_error");
                                              } else {
                                                  StringRequest stringRequest = new StringRequest(Request.Method.POST, reg_url,
                                                          new Response.Listener<String>() {
                                                              @Override
                                                              public void onResponse(String response) {
                                                                  // Toast.makeText(VRegestraion.this,response,Toast.LENGTH_LONG).show();

                                                                  try {
                                                                      JSONArray jsonArray = new JSONArray(response);
                                                                      JSONObject jsonObject = jsonArray.getJSONObject(0);
                                                                      String code = jsonObject.getString("code");
                                                                      String message = jsonObject.getString("message");
                                                                      builder.setTitle("التسجيل :");
                                                                      builder.setMessage(message);
                                                                      diplayAlert(code);
                                                                      builder.show();
                                                                  } catch (JSONException e) {
                                                                      e.printStackTrace();
                                                                  }

                                                              }
                                                          }, new Response.ErrorListener() {
                                                      @Override
                                                      public void onErrorResponse(VolleyError error) {
                                                          // Toast.makeText(VRegestraion.this,error.toString(),Toast.LENGTH_LONG).show();


                                                      }
                                                  }) {
                                                      @Override
                                                      protected Map<String, String> getParams() throws AuthFailureError {

                                                          Map<String, String> params = new HashMap<String, String>();
                                                          params.put("name", name);
                                                          params.put("email", email);
                                                          params.put("user_name", username);
                                                          params.put("password", password);

                                                          return params;
                                                      }
                                                  };

                                                  Mysingletone.getInstance(VRegestraion.this).addToRequestque(stringRequest);
                                              }
                                          }

                                      }
                                  }
        );
    }

    protected final void diplayAlert(final String code) {


        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (code.equals("input_error")) {
                    Password.setText("");
                    ConfirmPassword.setText("");
                } else if (code.equals("registration_success")) {

                    finish();

                } else if (code.equals("registeration_faild")) {
                    Name.setText("");
                    Email.setText("");
                    UserName.setText("");
                    Password.setText("");
                    ConfirmPassword.setText("");

                }

            }
        });

        android.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
}