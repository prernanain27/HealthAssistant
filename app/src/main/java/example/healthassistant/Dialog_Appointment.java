package example.healthassistant;

import android.*;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;

import static example.healthassistant.DbContract.DbEntryAppointment.COLUMN_DOC_CONTACT;

public class Dialog_Appointment extends AppCompatActivity {
    private SQLiteDatabase mDb;
    String text = "";
    String phone = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog__appointment);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        DbHelper db = new DbHelper(this);
        mDb = db.getReadableDatabase();
        String hour = Integer.toString(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
        String minutes = "";
        int min = (Calendar.getInstance().get(Calendar.MINUTE));
         minutes = Integer.toString(min);
        String value1 = hour+":"+minutes;
        String[] projection = {DbContract.DbEntryAppointment.COLUMN_DOC_NAME,COLUMN_DOC_CONTACT, DbContract.DbEntryAppointment.COLUMN_APPOINT_TIME, DbContract.DbEntryAppointment.COLUMN_APPOINT_DESCRIPTION};
        Cursor data = mDb.query(DbContract.DbEntryAppointment.TABLE_NAME,projection, DbContract.DbEntryAppointment.COLUMN_APPOINT_TIME + " = ?",new String[]{value1}
                ,null,null,null,null);
        if(data.getCount()>0) {
            while (data.moveToNext()) {
                phone = data.getString(1).toString();
                text = "Meet Dr." + data.getString(0).toString() + " at " + data.getString(2).toString() + "\n Regarding :" + data.getString(3).toString();
            }
        }
        builder.setMessage(text).setCancelable(
                false).setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        sendSms(phone,text);
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
        Log.d("Dialog","Dialog box created");

    }
    public void sendSms(String sms, String text){
        //For checking whether user has granted the permission to send messages.
        if( ContextCompat.checkSelfPermission(this, android.Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED)
        {
            try {
                String description = text + "But I am unable to come today. Please reschedule it.";
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage("5623506072", null, description , null, null);
                Toast.makeText( getApplicationContext(),"Sending your request via sms ", Toast.LENGTH_SHORT ).show();
            }
            catch (Exception e)
            {
                Toast.makeText( getApplicationContext(),"Sending sms failed"  , Toast.LENGTH_SHORT ).show();
                e.printStackTrace();

            }
        }
        else
        {
            Toast.makeText( getApplicationContext(),"Permission for sending messages is not granted"  , Toast.LENGTH_SHORT ).show();
            ActivityCompat.requestPermissions(this, new String[] { android.Manifest.permission.SEND_SMS} , 123);
        }
    }
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 123) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                sendSms(phone,text);
            }

        }
    }
}
