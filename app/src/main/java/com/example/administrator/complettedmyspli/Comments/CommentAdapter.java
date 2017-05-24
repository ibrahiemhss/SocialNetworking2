package com.example.administrator.complettedmyspli.Comments;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.complettedmyspli.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 23/05/2017.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyHolder> {

    List<ListComments> Mylist;
    Context mContext;
  
    public CommentAdapter(ArrayList<ListComments> mylist, Context mContext) {
        
        this.mContext=mContext;
        this.Mylist=mylist;
    }

    public CommentAdapter(List<ListComments> mylist) {
    }


    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_comments, parent, false);
        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));

        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        ListComments comments = Mylist.get(position);
        holder.Name.setText(comments.getName());
        holder.Comment.setText(comments.getComments());
    }
@Override
    public int getItemCount()
        {
            if(Mylist!=null){
                return Mylist.size();

            }
            return 0 ;

        }



         public class MyHolder extends RecyclerView.ViewHolder{

        // Typeface customTypeOne = Typeface.createFromAsset(itemView.getContext().getAssets(), "DroidNaskh-Regular.ttf");



        TextView Name;
        TextView Comment;
        MyHolder(View itemView) {
            super(itemView);

            Name = (TextView) itemView.findViewById(R.id.textNamesss);
            Comment = (TextView) itemView.findViewById(R.id.textCommentsss);
        }

}}