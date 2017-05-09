package example.healthassistant;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import java.util.Calendar;

import example.healthassistant.Activities.Appointment_Scheduler;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class NotificationService1 extends IntentService {
    private NotificationManager notificationManager;
    private PendingIntent pendingIntent;
    private static int NOTIFICATION_ID = 1;
    private SQLiteDatabase mDb;
    Notification notification;
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "example.healthassistant.action.FOO";
    private static final String ACTION_BAZ = "example.healthassistant.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "example.healthassistant.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "example.healthassistant.extra.PARAM2";

    public NotificationService1() {
        super("NotificationService1");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, NotificationService1.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, NotificationService1.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Context context = this.getApplicationContext();
        String text = "";
        DbHelper db = new DbHelper(this);
        mDb = db.getReadableDatabase();
        notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent mIntent = new Intent(this, Appointment_Scheduler.class);
        Bundle bundle = new Bundle();
        bundle.putString("test", "test");
        mIntent.putExtras(bundle);
        pendingIntent = PendingIntent.getActivity(context, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        String year = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));
        String month = Integer.toString(Calendar.getInstance().get(Calendar.MONTH));
        String day = Integer.toString(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        String hour = Integer.toString(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
        String minutes = "";
        int min = (Calendar.getInstance().get(Calendar.MINUTE));
         minutes = Integer.toString(min);
        String value1 = hour+":"+minutes;
        String value = year+"-"+month+"-"+day;
        String[] projection = {DbContract.DbEntryAppointment.COLUMN_DOC_NAME, DbContract.DbEntryAppointment.COLUMN_APPOINT_TIME};
        Cursor data = mDb.query(DbContract.DbEntryAppointment.TABLE_NAME,projection, DbContract.DbEntryAppointment.COLUMN_APPOINT_TIME + " = ?",new String[]{value1}
                ,null,null,null,null);
        if(data.getCount()>0){
            while(data.moveToNext())
            text = "Meet Dr."+ data.getString(0).toString() + " at " + data.getString(1).toString();
        }
        Resources res = this.getResources();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        notification = new NotificationCompat.Builder(this)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.ic_launcher))
                .setTicker("ticker value")
                .setAutoCancel(true)
                .setPriority(8)
                .setSound(soundUri)
                .setContentTitle("DOCTOR's APPOINTMENT:")
                .setContentText(text).build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL | Notification.FLAG_SHOW_LIGHTS;
        notification.defaults |= Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE;
        notification.ledARGB = 0xFFFFA500;
        notification.ledOnMS = 800;
        notification.ledOffMS = 1000;
        notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, notification);
        Log.i("notif","Notifications sent.");
        Intent i = new Intent(getBaseContext(),Dialog_Appointment.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplication().startActivity(i);
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
