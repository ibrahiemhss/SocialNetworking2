package com.example.administrator.complettedmyspli.Likes;

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
 * Created by Administrator on 31/05/2017.
 */

public class LikesAdapter extends RecyclerView.Adapter<LikesAdapter.MyHolder> {

 List<LikesModels> likestsModels ;
        Context context;

public LikesAdapter(ArrayList<LikesModels> likelist,Context context) {
        this.context=context;
        this.likestsModels=likelist;
        }

    public LikesAdapter(List<LikesModels> likelist ) {
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
        LikesModels likesM = likestsModels.get(position);

        holder.Names.setText(likesM.getName());
        holder.IdOfPostIdLikes.setText(likesM.getIdPostLikes());

        }
@Override
public int getItemCount()
        {
        if(likestsModels!=null){
        return likestsModels.size();

        }
        return 0 ;

        }



public class MyHolder extends RecyclerView.ViewHolder{

    // Typeface customTypeOne = Typeface.createFromAsset(itemView.getContext().getAssets(), "DroidNaskh-Regular.ttf");




    TextView Names;
    TextView IdOfPostIdLikes;

    MyHolder(View itemView) {
        super(itemView);

        Names = (TextView) itemView.findViewById(R.id.textCommentsss);
        IdOfPostIdLikes = (TextView) itemView.findViewById(R.id.textNamesss);

    }

}}
