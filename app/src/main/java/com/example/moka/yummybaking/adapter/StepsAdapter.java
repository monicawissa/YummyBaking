package com.example.moka.yummybaking.adapter;


import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.moka.yummybaking.R;
import com.example.moka.yummybaking.model.Step;

import java.util.ArrayList;




public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.RecyclerHolder> {

    Context context;
    private LayoutInflater inflater;
    private ArrayList<Step> steps;
    public int[] trackers;


    public StepsAdapter(Context context, ArrayList<Step> steps, int[] trackers) {
        this.context = context;
        this.steps = steps;
        inflater = LayoutInflater.from(context);
        this.trackers = trackers;
    }


    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.step_card, parent, false);
        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerHolder holder, final int position) {

        holder.title.setText(steps.get(position).getShortDescription());
        if(trackers[position]==1)
        {
            holder.root.setBackgroundColor(ContextCompat.getColor(context,R.color.colorPrimary));
            holder.title.setTextColor(ContextCompat.getColor(context,R.color.white));
        }
        else{
            holder.root.setBackgroundColor(ContextCompat.getColor(context,R.color.recipe_list_background));
            holder.title.setTextColor(ContextCompat.getColor(context,R.color.black));
        }

    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    class RecyclerHolder extends RecyclerView.ViewHolder {
        TextView title;
        CardView root;

        RecyclerHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            root = (CardView) itemView.findViewById(R.id.root);
        }
    }


}

