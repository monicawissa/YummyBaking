package com.example.moka.yummybaking;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.widget.RemoteViews;

import com.example.moka.yummybaking.json.Recipe;
import com.google.gson.Gson;
/**
 * Implementation of App Widget functionality.
 */
public class bakingWidget extends AppWidgetProvider {


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them

        for (int appWidgetId : appWidgetIds) {
            final RemoteViews rv = new RemoteViews(context.getPackageName(),
                    R.layout.baking_widget);
            Gson gson=new Gson();
            //get json string from saved
            SharedPreferences sharedPreferences = context.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE);
            String json = sharedPreferences.getString("json", "");;
            Recipe rec =gson.fromJson(json,Recipe.class);

            //open main activity on click

            rv.setTextViewText(R.id.recipe_title, rec.getName() );
            Intent startAppIntent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, startAppIntent, 0);
            rv.setOnClickPendingIntent(R.id.layout_widget, pendingIntent);

            Intent intent = new Intent(context, BakingAppWidgetService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    appWidgetId);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
            rv.setRemoteAdapter(R.id.listViewWidget, intent);


            appWidgetManager.updateAppWidget(appWidgetId, rv);

        }

        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }


    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

