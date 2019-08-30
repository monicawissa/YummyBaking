package com.example.moka.yummybaking;

import android.annotation.SuppressLint;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;


import com.example.moka.yummybaking.json.Recipe;
import com.example.moka.yummybaking.model.Ingredient;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class BakingAppRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context context;
    private int appWidgetId;
    private ArrayList<Ingredient> ingredientsDataList = new ArrayList<>();


    public BakingAppRemoteViewsFactory(Context context, Intent intent) {
        this.context = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }


    @Override
    public void onCreate() {
        Gson gson=new Gson();
        //get json string from saved
        SharedPreferences sharedPreferences = context.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE);
        String json = sharedPreferences.getString("json", "");;
        Recipe rec =gson.fromJson(json,Recipe.class);
        ingredientsDataList=rec.getIngredients();

    }

    @Override
    public void onDataSetChanged() {
        Gson gson=new Gson();
        //get json string from saved
        SharedPreferences sharedPreferences = context.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE);
        String json = sharedPreferences.getString("json", "");;
        Recipe rec =gson.fromJson(json,Recipe.class);
        ingredientsDataList=rec.getIngredients();
    }

    @Override
    public void onDestroy() {

        ingredientsDataList.clear();

    }

    @Override
    public int getCount() {
            return ingredientsDataList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteView = new RemoteViews(context.getPackageName(),
                R.layout.list_view_row);

        remoteView.setTextViewText(R.id.txtIngredient, ingredientsDataList.get(position).getIngredient());
        remoteView.setTextViewText(R.id.txtmeasure, ingredientsDataList.get(position).getMeasure());
        remoteView.setTextViewText(R.id.txtQuantity, Double.toString( ingredientsDataList.get(position).getQuantity()));
        return remoteView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }


}
