package com.example.moka.yummybaking.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moka.yummybaking.R;
import com.example.moka.yummybaking.model.Baking;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.MyViewHolder> {

    private Context mContext;
    private List<Baking> recipeList = new ArrayList<>();

    public RecipeAdapter(Context mContext, List<Baking> recipeList) {
        this.recipeList = recipeList;
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_card, parent, false);

        return new MyViewHolder(view);
    }



    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Baking recipe = recipeList.get(position);
        holder.title.setText(recipe.getName());
        switch (position){
            case 0:
                Picasso.with(mContext)
                        .load(R.drawable.nutellacake)

                        .into(holder.image);
                break;
            case 1:
                Picasso.with(mContext)
                        .load(R.drawable.brownies)

                        .into(holder.image);
                break;
            case 2:
                Picasso.with(mContext)
                        .load(R.drawable.yellowcake)

                        .into(holder.image);
                break;
            case 3:
                Picasso.with(mContext)
                        .load(R.drawable.cheesecake)

                        .into(holder.image);
                break;
            default:

                Picasso.with(mContext)
                        .load(R.drawable.nutellacake)

                        .into(holder.image);


        }


    }


    @Override
    public int getItemCount(){
        return recipeList.size();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView title;
        ImageView image;

        public MyViewHolder(View view){

            super(view);
            title = (TextView) view.findViewById(R.id.recipe_title);
            image = (ImageView) view.findViewById(R.id.recipeImage);

            view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){
                        Baking clickedDataItem = recipeList.get(pos);

                        /*Intent intent = new Intent(mContext, RecipeDetailActivity.class);
                        intent.putExtra("ecipe", clickedDataItem);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);*/
                        Toast.makeText(v.getContext(), "You clicked " + clickedDataItem.getName(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
