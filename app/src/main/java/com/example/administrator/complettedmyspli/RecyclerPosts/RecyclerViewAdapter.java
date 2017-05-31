package com.example.administrator.complettedmyspli.RecyclerPosts;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
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
    SharedPreferences.Editor editor, editorComment;
    List<Models> modelsList;
    android.app.AlertDialog.Builder builder;
    String URL_ADD_LIKES = "http://devsinai.com/SocialNetwork/AddLike.php";
    String URL_Delete_LIKES = "http://devsinai.com/SocialNetwork/DeletLikes.php";


    //    final String TAAG=this.getClass().getName();
///////////////////////////////////////////////////////////////////////
    public RecyclerViewAdapter(List<Models> getDataAdapter, Context context) {
        this.modelsList = getDataAdapter;
        this.context = context;
    }

    public RecyclerViewAdapter(List<Models> getDataAdapter13) {
    }

    public void setArray(List<Models> list){
        this.modelsList = list;
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
        pref = context.getSharedPreferences("Login2.conf", Context.MODE_PRIVATE);
        id = pref.getString("id", "id");
        editor = pref.edit();

        Picasso
                .with(context)
                .load(models.getImageItems())
                .into((holder.SubjectImage));


        holder.NameTextView.setText(models.getName());
        holder.SubjectTextView.setText(models.getSubject());
        holder.timePost.setText(models.getTimeapost());
        holder.textId.setText(models.getId_user());
        holder.text_id_post.setText(models.getId_post());
        holder.BtComents.setText(models.getTextComent());
        holder.editcomment.setText(models.getTextComent());
        holder.LikeCounts.setText(models.getLikeCounts());

        if (models.getisLiked()) {
            holder.LikeB.setImageResource(R.drawable.like);
        } else {
            holder.LikeB.setImageResource(R.drawable.no_like);
        }
        holder.LikeB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "";

                if (models.getisLiked()) {
                    url = URL_Delete_LIKES;
                    holder.LikeB.setImageResource(R.drawable.no_like);
                    models.setisLiked(false);
                } else {

                    url = URL_ADD_LIKES;
                    holder.LikeB.setImageResource(R.drawable.like);
                    models.setisLiked(true);
                }
                 StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject jsonObject = new JSONObject(response);
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

                        Log.v("PARAMS","user_id : "+id+ " , post_id :"+models.getId_post());
                        params.put("user_id", id);
                        params.put("post_id", models.getId_post());
                        // Add this line to send USER ID to server
// Add this line to send USER ID to server


                        {

                        }

                        return params;
                    }
                };
               /* RequestQueue requestQueue = Volley.newRequestQueue(context);


                requestQueue.add(stringRequest);*/

                Mysingletone.getInstance(RecyclerViewAdapter.this.context).addToRequestque(stringRequest);

            }


        });
//        holder.editcomment.setText(models.getEditcomment());


        final String text_user_id = holder.textId.getText().toString();
        final String post_id = holder.text_id_post.getText().toString();
        if (id.equals(text_user_id)) {
            holder.textId.setVisibility(View.VISIBLE);

        } else if (id != (text_user_id)) {
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
                                    public void onClick(DialogInterface dialog, int which) {
                                        String delet_url = "http://devsinai.com/SocialNetwork/DeletePosts.php";

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

                                                if (error != null) {
                                                    Toast.makeText(RecyclerViewAdapter.this.context, "somthing wrong while delleting item", Toast.LENGTH_LONG).show();

                                                }
                                            }
                                        }) {
                                            @Override
                                            protected Map<String, String> getParams() throws AuthFailureError {

                                                Map<String, String> param = new HashMap<String, String>();
                                                param.put("post_id", post_id);
                                                param.put("user_id", text_user_id);
                                                return param;
                                            }
                                        };

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
        holder.BtComents.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                holder.progressbar.setVisibility(View.VISIBLE);


                final String COMMENT=holder.editcomment.getText().toString();
                holder.editcomment.setVisibility(View.VISIBLE);

                final StringRequest stringRequest2 = new StringRequest(Request.Method.POST, "http://devsinai.com/SocialNetwork/GetIdComments.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONArray jsonArray = new JSONArray(response);
                                    JSONObject jsonObject = jsonArray.getJSONObject(0);

                                    holder.progressbar.setVisibility(View.GONE);

                                    id_Comment = jsonObject.getString("post_id");
                                    DialogeComments dialogeComments1 = new DialogeComments(context);
                                    dialogeComments1.post_id((models.getId_post()));
                                    prefComment = context.getSharedPreferences("prefCommentId.conf", Context.MODE_PRIVATE);
                                    id_Comment = prefComment.getString("post_id", "post_id");

                                    editorComment = prefComment.edit();

                                    editorComment.putString("post_id", jsonObject.getString("post_id"));

                                    editorComment.commit();
                                    final String id_user = pref.getString("id", "");
                                    final String post_id = prefComment.getString("post_id", "");
                                    if(COMMENT.equals("")){
                                        Toast.makeText(RecyclerViewAdapter.this.context, "ادخل تعليق", Toast.LENGTH_LONG).show();
                                        dialogeComments1.show();

                                    }else {

                                        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, "http://devsinai.com/SocialNetwork/AddComment.php",
                                                new Response.Listener<String>() {
                                                    @Override
                                                    public void onResponse(String response) {
                                                        try {
                                                            JSONArray jsonArray = new JSONArray(response);
                                                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                                                            holder.progressbar.setVisibility(View.GONE);
                                                            String Response = jsonObject.getString("response");
                                                            Toast.makeText(RecyclerViewAdapter.this.context, Response, Toast.LENGTH_LONG).show();

                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }

                                                    }
                                                }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Toast.makeText(RecyclerViewAdapter.this.context, "Error", Toast.LENGTH_LONG).show();
                                                VolleyLog.e("Error: ", error.getMessage());
                                                error.printStackTrace();
                                            }
                                        }) {
                                            @Override
                                            protected Map<String, String> getParams() throws AuthFailureError {

                                                Map<String, String> params = new HashMap<String, String>();
                                                params.put("user_id", id_user);
                                                params.put("post_id", post_id);
                                                params.put("comment", COMMENT);
                                                ;

                                                return params;
                                            }
                                        };
                                        Mysingletone.getInstance(RecyclerViewAdapter.this.context).addToRequestque(stringRequest1);

                                        dialogeComments1.show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        if (error != null) {
                            Toast.makeText(RecyclerViewAdapter.this.context, "somthing wrong while delleting item", Toast.LENGTH_LONG).show();

                        }
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> params = new HashMap<String, String>();
                        params.put("post_id", post_id);


                        return params;
                    }
                };

                Mysingletone.getInstance(RecyclerViewAdapter.this.context).addToRequestque(stringRequest2);


            }
        });




    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public int getItemCount() {
        if (modelsList != null) {
            return modelsList.size();

        }
        return 0;


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
        public TextView LikeCounts;
        public ImageView LikeB;
        private ProgressBar progressbar;
        ListView listLkes;


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
            editcomment = (EditText) itemView.findViewById(R.id.EditCommentADB);
            LikeCounts = (TextView) itemView.findViewById(R.id.LikeCounts);
            LikeB = (ImageView) itemView.findViewById(R.id.LikeB);
            progressbar = (ProgressBar) itemView.findViewById(R.id.progressbarRess);
            listLkes = (ListView) itemView.findViewById(R.id.listLkes);



        }
    }

}class CmmentsModel{
    public String getName() {
        return name;
    }

    private String name;

    void setName(String name) {
        this.name = name;
    }

}


class VolleyAdapter extends BaseAdapter {
    public ArrayList<CmmentsModel> CommentsModels ;
    private LayoutInflater lf;

    @Override
    public int getCount() {
        return CommentsModels.size();
    }

    @Override
    public Object getItem(int i) {
        return CommentsModels.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh ;
        if(view == null){
            vh = new ViewHolder();
            view = lf.inflate(R.layout.list_row_likes,null);
            vh.Names = (TextView) view.findViewById(R.id.Comment_Name);

            view.setTag(vh);
        }
        else{
            vh = (ViewHolder) view.getTag();
        }

        CmmentsModel nm = CommentsModels.get(i);
        vh.Names.setText(nm.getName());
        return view;
    }

    class  ViewHolder{
        TextView Names;


    }

}
