package com.technotrack.denislos.mylittlewidget;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.widget.RemoteViews;
import android.widget.Toast;


import java.util.Calendar;
/**
 * Created by denis on 5/20/17.
 */

public class MyAppWidgetProvider extends AppWidgetProvider
  {
    private static final int TIME_MAX_VALUE = 99;
    private static final int TIME_MIN_VALUE = 0;

    private static final String MY_PREFS_NAME = "MY_APP_WIDGET_PROVIDER_SHARED_PREFERENCES";
    private static final String TIME_VALUE_PREF = "TIME_VALUE_PREF";

    private static final String CHANGE_TIME_VALUE = "CHANGE_TIME_VALUE";
    private static final String SET_PRESSED = "SET_PRESSED";
    private static final String TIMER_INTERRUPTION = "TIMER_INTERRUPTION";
    private static final String ALARM_REPEATING_ACTION = "ALARM_REPEATING_ACTION";

    private static final String IS_POSITIVE_INC = "IS_POSITIVE_INC";

    private static final long REFRESH_INTERVAL = 60;


    @Override
    public void onReceive(Context context, Intent intent)
      {
        super.onReceive(context, intent);


        String action = intent.getAction();
        if ( action.compareTo(CHANGE_TIME_VALUE) == 0)
          {
            onTimeValueChanged(context, intent);
          }
        else if (action.compareTo(ALARM_REPEATING_ACTION) == 0)
          {
            onAlarmAction(context, intent);
          }
        else if (action.compareTo(SET_PRESSED) == 0)
          {
            onSetPressed(context, intent);
          }
        else if (action.compareTo(TIMER_INTERRUPTION) == 0)
          {
            CharSequence message = "Requested time has not been elapsed";
            int duration = Toast.LENGTH_SHORT;
            Toast.makeText(context, message, duration).show();

            onTimerInterruption(context, intent);
          }

      }



    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
      {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        final int length = appWidgetIds.length;

        for ( int cnt = 0; cnt < length; cnt++)
          {
            appWidgetManager.updateAppWidget(appWidgetIds[cnt], setDefaultListeners(context));
          }

        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(TIME_VALUE_PREF, 0);
        editor.commit();
      }



    public void onTimeValueChanged(Context context, Intent intent)
      {
        ComponentName componentName = new ComponentName(context.getPackageName(), getClass().getName());
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(componentName);

        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        int number = sharedPreferences.getInt(TIME_VALUE_PREF, 0);

        boolean isPositive = intent.getExtras().getBoolean(IS_POSITIVE_INC);
        if ( isPositive && number < TIME_MAX_VALUE)
          number++;
        else if ( !isPositive && number > TIME_MIN_VALUE)
          number--;



        int length = appWidgetIds.length;
        for ( int cnt = 0; cnt < length; cnt++)
          {
            RemoteViews views = setDefaultListeners(context);
            views.setTextViewText(R.id.time_value, String.format("%d", number));

            appWidgetManager.updateAppWidget(appWidgetIds[cnt], views);
          }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(TIME_VALUE_PREF, number);
        editor.commit();
      }



    public void onSetPressed(Context context, Intent intent)
      {
        ComponentName componentName = new ComponentName(context.getPackageName(), getClass().getName());
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(componentName);
        int length = appWidgetIds.length;

        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        int number = sharedPreferences.getInt(TIME_VALUE_PREF, 0);

        if ( number > 0 )
          {
            for (int cnt = 0; cnt < length; cnt++)
              {
                RemoteViews views = setTimerSetListeners(context);

                appWidgetManager.updateAppWidget(appWidgetIds[cnt], views);
              }

            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            ;
            PendingIntent alarmPendingIntent = getAlarmPendingIntent(context);

            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime(), REFRESH_INTERVAL * 1000, alarmPendingIntent);
          }
        else
          {
            CharSequence message = "Please, set a non-zero interval";
            int duration = Toast.LENGTH_SHORT;
            Toast.makeText(context, message, duration).show();

            for (int cnt = 0; cnt < length; cnt++)
              {
                RemoteViews views = setDefaultListeners(context);

                appWidgetManager.updateAppWidget(appWidgetIds[cnt], views);
              }
          }

      }



    public void onTimerInterruption(Context context, Intent intent)
      {
        ComponentName componentName = new ComponentName(context.getPackageName(), getClass().getName());
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(componentName);


        int length = appWidgetIds.length;
        for ( int cnt = 0; cnt < length; cnt++)
          {
            RemoteViews views = setTimerSetListeners(context);

            appWidgetManager.updateAppWidget(appWidgetIds[cnt], views);
          }
      }


    public void onAlarmAction(Context context, Intent intent)
      {
        ComponentName componentName = new ComponentName(context.getPackageName(), getClass().getName());
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(componentName);
        int length = appWidgetIds.length;

        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        int number = sharedPreferences.getInt(TIME_VALUE_PREF, 0);
        number--;

        if ( number > 0 )
          {

            for (int cnt = 0; cnt < length; cnt++)
              {
                RemoteViews views = setTimerSetListeners(context);
                views.setTextViewText(R.id.time_value, String.format("%d", number));

                appWidgetManager.updateAppWidget(appWidgetIds[cnt], views);
              }

          }
        else
          {
            AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
            PendingIntent alarmPendingIntent = getAlarmPendingIntent(context);

            alarmManager.cancel(alarmPendingIntent);

            for ( int cnt = 0; cnt < length; cnt++)
              {
                RemoteViews views = setDefaultListeners(context);
                views.setTextViewText(R.id.time_value, String.format("%d", number));

                appWidgetManager.updateAppWidget(appWidgetIds[cnt], views);
              }

            Notification.Builder notificationBuilder = new Notification.Builder(context);
            notificationBuilder.setSmallIcon(R.drawable.technotrack_24)
                               .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.technotrack_128))
                               .setContentTitle("Alarm")
                               .setContentText("Time has elapsed");

            Notification notification = notificationBuilder.build();
            NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0, notification);
          }


        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(TIME_VALUE_PREF, number);
        editor.commit();
        ;
      }





    public RemoteViews setTimerSetListeners(Context context)
      {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.my_appwidget);

        Intent intent = new Intent(context, MyAppWidgetProvider.class);
        intent.setAction(TIMER_INTERRUPTION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        views.setOnClickPendingIntent(R.id.plus_button, pendingIntent);
        views.setOnClickPendingIntent(R.id.set_button, pendingIntent);
        views.setOnClickPendingIntent(R.id.minus_button, pendingIntent);

        return views;
      }




    public RemoteViews setDefaultListeners(Context context)
      {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.my_appwidget);

        Intent plusIntent = new Intent(context, MyAppWidgetProvider.class);
        Bundle plusBundle = new Bundle();
        plusBundle.putBoolean(IS_POSITIVE_INC, true);
        plusIntent.putExtras(plusBundle);
        plusIntent.setAction(CHANGE_TIME_VALUE);
        PendingIntent plusPendingIntent = PendingIntent.getBroadcast(context, 1, plusIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent minusIntent = new Intent(context, MyAppWidgetProvider.class);
        Bundle minusBundle = new Bundle();
        minusBundle.putBoolean(IS_POSITIVE_INC, false);
        minusIntent.putExtras(minusBundle);
        minusIntent.setAction(CHANGE_TIME_VALUE);
        PendingIntent minusPendingIntent = PendingIntent.getBroadcast(context, 2, minusIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent setIntent = new Intent(context, MyAppWidgetProvider.class);
        setIntent.setAction(SET_PRESSED);
        PendingIntent setPendingIntent = PendingIntent.getBroadcast(context, 3, setIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        views.setOnClickPendingIntent(R.id.plus_button, plusPendingIntent);
        views.setOnClickPendingIntent(R.id.set_button, setPendingIntent);
        views.setOnClickPendingIntent(R.id.minus_button, minusPendingIntent);

        return views;
      }



    public PendingIntent getAlarmPendingIntent(Context context)
      {
        Intent alarmIntent = new Intent(context, MyAppWidgetProvider.class);
        alarmIntent.setAction(ALARM_REPEATING_ACTION);
        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);

        return alarmPendingIntent;
      }


  }
