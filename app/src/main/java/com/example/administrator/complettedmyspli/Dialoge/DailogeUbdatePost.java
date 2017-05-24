package com.example.administrator.complettedmyspli.Dialoge;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.complettedmyspli.R;

import java.util.HashMap;

public class DailogeUbdatePost extends Dialog implements View.OnClickListener {

    String HttpUrlDeleteRecord = "http://devsinai.com/DrSiani/DeletePosts.php";

    public Context c;
    public Dialog d;
    public Button deletDialog,ubdateDialoge;
    TextView textDialogAdd;
    ProgressDialog progressDialog2;
    HashMap<String,String> hashMap = new HashMap<>();
    String finalResult ;
    HttpParse httpParse = new HttpParse();






    public DailogeUbdatePost(Context a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dailoge_ubdate_post);

        deletDialog= (Button) findViewById(R.id.deletDialog);
        ubdateDialoge= (Button) findViewById(R.id.ubdateDialoge);


        deletDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostDelete(finalResult);

            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.BackBtnDialogDR:

                dismiss();

                break;
            default:
                break;
        }
        dismiss();
    }
    public void PostDelete(final String uerId) {

        class DeletePosts extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog2 = ProgressDialog.show(getContext(), "Loading Data", null, true, true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog2.dismiss();

                Toast.makeText(getContext(), httpResponseMsg.toString(), Toast.LENGTH_LONG).show();



            }

            @Override
            protected String doInBackground(String... params) {


                hashMap.put("user_id", params[0]);

                finalResult = httpParse.postRequest(hashMap, HttpUrlDeleteRecord);

                return finalResult;
            }
        }

        DeletePosts deletePosts = new DeletePosts();

        deletePosts.execute(uerId);
    }

}
