package edu.umd.cs.agileandroid.service;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.annotation.Nullable;

import edu.umd.cs.agileandroid.DependencyFactory;
import edu.umd.cs.agileandroid.R;
import edu.umd.cs.agileandroid.SprintActivity;


public class ReminderBackgroundService extends IntentService {

    private static AlarmManager alarmMgr;
    private static PendingIntent alarmIntent;
    private static final int SPRINT_REQUEST = 1;
    private static final int REMINDER_REQUEST = 2;
    private static final int ID = 0;
    public ReminderBackgroundService() {
        super("reminder");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String teamReminder = DependencyFactory.getTeamService(getApplicationContext()).getTeamReminder("");

        if( teamReminder == null || teamReminder.compareTo("") == 0){
            return;
        }else{
            Intent sprintIntent = SprintActivity.newIntent(this);
            PendingIntent pendingSprintIntent = PendingIntent.getActivity(this, SPRINT_REQUEST,
                    sprintIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            Notification.Builder notificationBuilder = new Notification.Builder(this)
                    .setTicker(getString(R.string.reminder_notification))
                    .setSmallIcon(android.R.drawable.ic_menu_compass)
                    .setContentText(teamReminder)
                    .setContentIntent(pendingSprintIntent)
                    .setContentTitle(getString(R.string.reminder_notification))
                    .setAutoCancel(true);

            Notification notification = notificationBuilder.build();
            NotificationManager notificationManager =
                    (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(ID, notification);
        }
    }

    public static void setReminderAlarm(Context context, int intervalInMinutes) {
        intervalInMinutes =  intervalInMinutes * 60 * 1000;
        alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = ReminderBackgroundService.newIntent(context);
        alarmIntent = PendingIntent.getService(context, REMINDER_REQUEST, intent, PendingIntent.FLAG_UPDATE_CURRENT);


        alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime()
                + intervalInMinutes, intervalInMinutes, alarmIntent);

        //alarm in 5 secs
//        alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + 5000, alarmIntent);
    }

    public static void cancelReminderAlarm(Context context){
        if(alarmMgr != null){
            alarmMgr.cancel(alarmIntent);
        }
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, ReminderBackgroundService.class);
    }

}
