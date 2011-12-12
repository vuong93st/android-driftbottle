package com.douya.bottle.activity.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.douya.R;

public class BottleAppWidgetProvider extends AppWidgetProvider{
	private static final String UPDATE_ACTION = "com.douya.bottle.UPDATE_APP_WIDGET";
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		System.out.println("onupdate");
		//super.onUpdate(context, appWidgetManager, appWidgetIds);
		Intent intent = new Intent();
		intent.setAction(UPDATE_ACTION);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0,
				intent, 0);
		RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
				R.layout.appwidget);
		remoteViews.setOnClickPendingIntent(R.id.bottle_number, pendingIntent);
		appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
	}

	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		System.out.println("onDeleted");
		super.onDeleted(context, appWidgetIds);
	}

	@Override
	public void onDisabled(Context context) {
		System.out.println("onDisabled");
		super.onDisabled(context);
	}

	@Override
	public void onEnabled(Context context) {
		System.out.println("onEnabled");
		super.onEnabled(context);
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		if (UPDATE_ACTION.equals(action)) {
			RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
					R.layout.appwidget);
			remoteViews.setImageViewResource(R.id.user_pic, R.drawable.face1);
			AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
			ComponentName componentName = new ComponentName(context,BottleAppWidgetProvider.class);
			appWidgetManager.updateAppWidget(componentName, remoteViews);
		} else {
			super.onReceive(context, intent);
		}
	}

}
