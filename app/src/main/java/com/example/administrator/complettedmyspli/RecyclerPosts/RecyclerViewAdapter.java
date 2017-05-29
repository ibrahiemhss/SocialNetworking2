package com.example.administrator.complettedmyspli.RecyclerPosts;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.administrator.complettedmyspli.Comments.DialogeComments;
import com.example.administrator.complettedmyspli.Mysingletone;
import com.example.administrator.complettedmyspli.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 25/04/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    Context context;
    SharedPreferences pref;
    String id;
    SharedPreferences prefComment;
    String id_Comment;
    SharedPreferences.Editor editor,editorComment;
    List<Models> modelsList;
    android.app.AlertDialog.Builder builder;




    //    final String TAAG=this.getClass().getName();
///////////////////////////////////////////////////////////////////////
    public RecyclerViewAdapter(ArrayList<Models> getDataAdapter, Context context){


        if (getDataAdapter != null)
            modelsList = new ArrayList<>(getDataAdapter);
        else modelsList = new ArrayList<>();
        this.modelsList = getDataAdapter;
        this.context = context;
    }

    public RecyclerViewAdapter(List<Models> getDataAdapter13) {
    }


    ///////////////////////////////////////////////////////////////////////
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_items, parent, false);





        return new ViewHolder(v);

    }


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

       // AdapterRVcomment bbbb=new AdapterRVcomment(mCommentList);
        final Models models = modelsList.get(position);
        pref =context.getSharedPreferences("Login2.conf", Context.MODE_PRIVATE);
        id = pref.getString("id", "id");
        editor = pref.edit();

        Picasso
                .with(context)
                .load(models.getImageItems())
                .into((holder.SubjectImage));



        holder.NameTextView.setText(models.getName());
        holder.SubjectTextView.setText(models.getSubject());
        holder.timePost.setText(models.getTimeapost());
        holder. textId.setText(models.getId_user());
        holder. text_id_post.setText(models.getId_post());
        holder.BtComents.setText(models.getTextComent());
//        holder.editcomment.setText(models.getEditcomment());



        final String text_user_id =holder. textId.getText().toString();
        final String post_id=holder.text_id_post.getText().toString();
        if(id.equals(text_user_id)) {
            holder.textId.setVisibility(View.VISIBLE);

            }
        else if (id != (text_user_id)) {
            holder.textId.setVisibility(View.GONE);
        }
            holder.textId.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final PopupMenu popup = new PopupMenu(context, holder.textId);
                    popup.inflate(R.menu.options_menu);
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                            switch (item.getItemId()) {
                                case R.id.menu1:

                                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

                                    // Setting Dialog Title
                                    alertDialog.setTitle("Confirm Delete...");

                                    // Setting Dialog Message
                                    alertDialog.setMessage("Are you sure you want delete this?");

                                    // Setting Icon to Dialog
                                    alertDialog.setIcon(R.drawable.delete);

                                    // Setting Positive "Yes" Button
                                    alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,int which) {
                                            String delet_url="http://devsinai.com/SocialNetwork/DeletePosts.php";

                                            final StringRequest stringRequest = new StringRequest(Request.Method.POST, delet_url,
                                                    new Response.Listener<String>() {
                                                        @Override
                                                        public void onResponse(String response) {
                                                            //   Log.d(TAAG,response);
                                                            if (response.equals("Record Deleted Successfully")) {
                                                                Toast.makeText(RecyclerViewAdapter.this.context, "deleted success", Toast.LENGTH_LONG).show();
                                                            } else {
                                                                Toast.makeText(RecyclerViewAdapter.this.context, "error while delleting item", Toast.LENGTH_LONG).show();
                                                            }
                                                        }
                                                    }, new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {

                                                    if(error !=null){
                                                        Toast.makeText(RecyclerViewAdapter.this.context,"somthing wrong while delleting item",Toast.LENGTH_LONG).show();

                                                    }
                                                }
                                            }){
                                                @Override
                                                protected Map<String, String> getParams() throws AuthFailureError {

                                                    Map<String,String> param=new HashMap<String, String>();
                                                    param.put("post_id",post_id) ;
                                                    param.put("user_id",text_user_id) ;
                                                    return param;
                                                }
                                            }
                                                    ;

                                            Mysingletone.getInstance(RecyclerViewAdapter.this.context).addToRequestque(stringRequest);


                                        }
                                    });

                                    // Setting Negative "NO" Button
                                    alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();

                                        }
                                    });

                                    // Showing Alert Message
                                    alertDialog.show();


                                    break;
                                case R.id.menu2:
                                    //handle menu2 click
                                    break;

                            }
                            return false;
                        }
                    });

                    popup.show();

                }
            });




          //////////////////////////////////////////////
        final DialogeComments dialogeComments=new DialogeComments(context);
        holder.BtComents.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                    final StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://devsinai.com/SocialNetwork/GetIdComments.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                DialogeComments dialogeComments1=new DialogeComments(context);
                                try {
                                    JSONArray jsonArray=new JSONArray(response);
                                    JSONObject jsonObject=jsonArray.getJSONObject(0);
                                    id_Comment = jsonObject.getString("post_id");

                                    dialogeComments1.post_id((models.getId_post()));

                                    prefComment=context.getSharedPreferences("prefCommentId.conf", Context.MODE_PRIVATE);
                                    id_Comment = prefComment.getString("post_id","post_id");

                                    editorComment=prefComment.edit();

                                    editorComment.putString("post_id",jsonObject.getString("post_id"));

                                    editorComment.commit();
                                    dialogeComments1.show();


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        if(error !=null){
                            Toast.makeText(RecyclerViewAdapter.this.context,"somthing wrong while delleting item",Toast.LENGTH_LONG).show();

                        }
                    }
                }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {

                            Map<String,String> params=new HashMap<String, String>();
                            params.put("post_id",post_id);


                            return params;
                        }
                }
                        ;

                Mysingletone.getInstance(RecyclerViewAdapter.this.context).addToRequestque(stringRequest);


            }
        });






    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public int getItemCount() {
if(modelsList!=null){
    return modelsList.size();

}
return 0 ;


    }


//////////////////////////////////////////////////////////////
    class ViewHolder extends RecyclerView.ViewHolder /*implements View.OnLongClickListener*/ {

        public TextView NameTextView;
        public TextView SubjectTextView;
        public TextView timePost;
        public ImageView SubjectImage;
        public ImageView SyncImage;
        public TextView textId;
        public TextView text_id_post;
        public final TextView BtComents;
        public EditText editcomment;
        public ImageView Like;
    RecyclerView lstMedicines;

    Fragment mFragment;




        ItemLongclicklistener itemLongclicklistener;

        public ViewHolder(View itemView) {
            super(itemView);


            NameTextView = (TextView) itemView.findViewById(R.id.title);
            SubjectTextView = (TextView) itemView.findViewById(R.id.description);
            timePost = (TextView) itemView.findViewById(R.id.timepost);
            textId = (TextView) itemView.findViewById(R.id.id_user);
            text_id_post = (TextView) itemView.findViewById(R.id.id_post);
            SubjectImage = (ImageView) itemView.findViewById(R.id.imageViewpost);
            BtComents = (TextView) itemView.findViewById(R.id.BtComents);
            lstMedicines=(RecyclerView)itemView.findViewById(R.id.RvListComent) ;

           RecyclerView.LayoutManager layout = new LinearLayoutManager(context);
            layout.setAutoMeasureEnabled(true);




            Like = (ImageView) itemView.findViewById(R.id.Like);
        }
        }




}