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
import com.example.moka.yummybaking.model.Ingredient;

import java.util.ArrayList;




public class ingredientAdapter extends RecyclerView.Adapter<ingredientAdapter.RecyclerHolder> {

    Context context;
    private LayoutInflater inflater;
    private ArrayList<Ingredient> ingredients;



    public ingredientAdapter(Context context , ArrayList<Ingredient> ingredients) {
        this.context = context;
        this.ingredients=ingredients;

        inflater=LayoutInflater.from(context);
    }


    @Override
    public ingredientAdapter.RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.ingredient_card,null);
        return new ingredientAdapter.RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerHolder holder, int position) {

        holder.name.setText(ingredients.get(position).getIngredient());
        holder.quantity.setText(""+ingredients.get(position).getQuantity());
        holder.measure.setText(ingredients.get(position).getMeasure());


    }


    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    class RecyclerHolder extends RecyclerView.ViewHolder {
        TextView name,quantity,measure,widget;
        RelativeLayout rel;

        RecyclerHolder(final View itemView) {
            super(itemView);
            name=(TextView) itemView.findViewById(R.id.name);
            quantity=(TextView) itemView.findViewById(R.id.quantity);
            measure=(TextView) itemView.findViewById(R.id. measure);
            widget=(TextView) itemView.findViewById(R.id. widget);
            rel=(RelativeLayout)itemView.findViewById(R.id.rel);

        }
    }

}
