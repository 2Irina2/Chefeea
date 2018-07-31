package com.example.android.chefeea;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.android.chefeea.Classes.Recipe;
import com.example.android.chefeea.Classes.RecipeWidgetRemoteViewsService;

/**
 * Implementation of App Widget functionality.
 * Listview implementation from https://github.com/commonsguy/cw-omnibus/tree/master/AppWidget
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    private static Recipe mRecipe;

    public static final String ACTION_UPDATE_INGREDIENTS = "ACTION_UPDATE_INGREDIENTS";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);
        views.setTextViewText(R.id.appwidget_title, widgetText);

        Intent intent;
        if(mRecipe == null){
            intent = new Intent(context, MainActivity.class);
        } else {
            intent = new Intent(context, RecipeActivity.class);
            intent.putExtra("recipe",mRecipe);
        }

        Intent svcIntent=new Intent(context, RecipeWidgetRemoteViewsService.class);
        views.setRemoteAdapter(R.id.appwidget_listView, svcIntent);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.app_widget, pendingIntent);


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }



    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(
                    context.getPackageName(),
                    R.layout.recipe_widget_provider
            );
            Intent intent = new Intent(context, RecipeWidgetRemoteViewsService.class);
            views.setRemoteAdapter(R.id.appwidget_listView, intent);
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (ACTION_UPDATE_INGREDIENTS.equals(intent.getAction())) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);
            mRecipe = intent.getParcelableExtra("recipe");
            views.setTextViewText(R.id.appwidget_title, mRecipe.getRecipeIngredients().get(0));
            // This time we dont have widgetId. Reaching our widget with that way.
            ComponentName appWidget = new ComponentName(context, RecipeWidgetProvider.class);
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            // Instruct the widget manager to update the widget



            appWidgetManager.updateAppWidget(appWidget, views);
        }
    }

}

