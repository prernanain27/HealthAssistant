package example.healthassistant;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Intent;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import example.healthassistant.Activities.Appointment_Scheduler;
import example.healthassistant.Models.Med_Scedule;
import example.healthassistant.Models.Med_Specification;
import example.healthassistant.Models.Medicine;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class Daily_ScheduleNotification extends IntentService {
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

    public Daily_ScheduleNotification() {
        super("Daily_ScheduleNotification");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, Daily_ScheduleNotification.class);
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
        Intent intent = new Intent(context, Daily_ScheduleNotification.class);
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


        //1) access schedule table and populate list of med_schedule list
        Cursor cursor = getAllRows(DbContract.DbEntryMed_Schedule.TABLE_NAME, Med_Scedule.ALL_COLUMNS);
        if(cursor.getCount()>0)
        {
            while (cursor.moveToNext()) {
                text = text + cursor.getString(1).toString() + "\t" + cursor.getString(3).toString() + "\n";

                updateLapsedForMedicine(cursor.getString(0).toString(), cursor.getString(1).toString(), cursor.getString(2).toString());
            }
        }
        //2) update date lapsed field for all medicines
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
                .setContentTitle("Take the Medicines:")
                .setContentText(text).build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL | Notification.FLAG_SHOW_LIGHTS;
        notification.defaults |= Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE;
        notification.ledARGB = 0xFFFFA500;
        notification.ledOnMS = 800;
        notification.ledOffMS = 1000;
        notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, notification);
        Log.i("notif","Notifications sent.");
        Intent i = new Intent(getBaseContext(),Dialog_daily_medicine.class);
        i.putExtra("data",text);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplication().startActivity(i);
    }

    private void updateLapsedForMedicine(String timeString, String medName,String duration) {

        DbHelper db = new DbHelper(getApplicationContext());
        mDb = db.getWritableDatabase();

        int du = Integer.parseInt(duration)-1;

        String dL = String.valueOf(du);
        String whereClause = DbContract.DbEntryMed_Schedule.COLUMN_TIME + "=?  and " + DbContract.DbEntryMed_Schedule.COLUMN_MED_NAME + "=?";

        String[] whereArgs = {timeString, medName};

        ContentValues cv = new ContentValues();
        cv.put(DbContract.DbEntryMed_Schedule.COLUMN_DAYS_LAPSED,dL);

        try {

            mDb.update(DbContract.DbEntryMed_Schedule.TABLE_NAME, cv, whereClause, whereArgs);
        }
        catch (Exception ex)
        {
            Log.e("Daily Sched Notif","exception while updating days_lapsed"+ex.getMessage());
        }

    }

    public Cursor getAllRows(String tableName, String[] columns) {
        String where = null;
        DbHelper db = new DbHelper(getApplicationContext());
        Log.d("Notification Service", " entered get all rows");
        mDb = db.getWritableDatabase();
        Cursor cursor = null;
        cursor = mDb.query(tableName, columns, where, null, null, null, null, null);


        try {
            if (cursor != null) {
                cursor.moveToFirst();
                Log.d("GetAllRows", cursor.getString(1));
            }
        } catch (SQLiteException ex) {
            Log.e("Notification Service", "Exception reading user data");
        }
        db.close();
        return cursor;
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
