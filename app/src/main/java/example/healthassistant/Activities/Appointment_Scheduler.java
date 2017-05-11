package example.healthassistant.Activities;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import example.healthassistant.DbContract;
import example.healthassistant.DbHelper;
import example.healthassistant.MyReceiver;
import example.healthassistant.R;

import static example.healthassistant.DbContract.DbEntryAppointment.COLUMN_APPOINT_DATE;
import static example.healthassistant.DbContract.DbEntryAppointment.COLUMN_APPOINT_DESCRIPTION;
import static example.healthassistant.DbContract.DbEntryAppointment.COLUMN_APPOINT_TIME;
import static example.healthassistant.DbContract.DbEntryAppointment.COLUMN_DOC_CONTACT;
import static example.healthassistant.DbContract.DbEntryAppointment.COLUMN_DOC_NAME;

public class Appointment_Scheduler extends AppCompatActivity {

    EditText docName;
    EditText docContact;
    EditText date;
    EditText time;
    private Calendar calendar;
    Button set;
    private SQLiteDatabase mDb;
    SQLiteOpenHelper db;
    EditText desc;

    public static final String[] ALL_COLUMNS = {
            DbContract.DbEntryAppointment.COLUMN_ID,
            DbContract.DbEntryAppointment.COLUMN_DOC_NAME,
            DbContract.DbEntryAppointment.COLUMN_DOC_CONTACT,
            DbContract.DbEntryAppointment.COLUMN_APPOINT_DATE,
            DbContract.DbEntryAppointment.COLUMN_APPOINT_TIME

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment__scheduler);
        db = new DbHelper(getApplicationContext());
        calendar = java.util.Calendar.getInstance();
        date = (EditText) findViewById(R.id.appointmentDate);
        date.setInputType(InputType.TYPE_NULL);
        desc = (EditText) findViewById(R.id.description);
        docName = (EditText) findViewById(R.id.docName);
        docContact = (EditText) findViewById(R.id.contactNumber);
        time = (EditText) findViewById(R.id.appointmentTime);
        set = (Button) findViewById(R.id.setAppointment);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int yy = calendar.get(Calendar.YEAR);
                int mm = calendar.get(Calendar.MONTH);
                int dd = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(Appointment_Scheduler.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String date1 = String.valueOf(year) +"-"+String.valueOf(monthOfYear) +"-"+String.valueOf(dayOfMonth);
                        date.setText(date1);
                    }
                }, yy, mm, dd);
                datePicker.show();
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeSet(time);
            }
        });
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(docName.getText() != null && docContact.getText() !=null && date.getText() != null && time.getText() != null){
                        addData();
                    }
                else
                        Toast.makeText(Appointment_Scheduler.this,"Fill all the fields",Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void addData() {
        mDb = db.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_DOC_NAME, docName.getText().toString() );
        cv.put(COLUMN_DOC_CONTACT, docContact.getText().toString());
        cv.put(COLUMN_APPOINT_DATE, date.getText().toString());
        cv.put(COLUMN_APPOINT_TIME, time.getText().toString());
        cv.put(COLUMN_APPOINT_DESCRIPTION,desc.getText().toString());
        try{
            long result = mDb.insert(DbContract.DbEntryAppointment.TABLE_NAME, null, cv);
            if(result != -1){
                Toast.makeText(Appointment_Scheduler.this,"Reminder for appointment is set",Toast.LENGTH_SHORT).show();
                putAlarm();
            }
            else
                Toast.makeText(Appointment_Scheduler.this, "Something went wrong",Toast.LENGTH_SHORT).show();
        }
        catch (Exception ex){
            String s = ex.getMessage();
        }
        finally {
            db.close();
        }
    }

    private void putAlarm() {
        String where = null;
        db = new DbHelper(getApplicationContext());
        Log.d("ViewPHR", " entered get all rows");
        mDb = db.getWritableDatabase();
        Cursor cursor = mDb.query(true, DbContract.DbEntryAppointment.TABLE_NAME, ALL_COLUMNS, where, null, null, null, null, null);
        if(cursor !=null){
            cursor.moveToLast();
            String date = cursor.getString(3);
            String time = cursor.getString(4);
            String[] dateSplit = date.split("-");
            String[] timeSplit = time.split(":");
            alarm s = new alarm();
            s.test_alarm(Integer.parseInt(dateSplit[0]),Integer.parseInt(dateSplit[1]),Integer.parseInt(dateSplit[2]),Integer.parseInt(timeSplit[0]),Integer.parseInt(timeSplit[1]));
            Toast.makeText(Appointment_Scheduler.this,cursor.getString(0),Toast.LENGTH_SHORT).show();
        }
    }

    private void timeSet (final EditText idofEditText){
        int hour = calendar.get(java.util.Calendar.HOUR_OF_DAY);
        int minute = calendar.get(java.util.Calendar.MINUTE);

        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(Appointment_Scheduler.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                idofEditText.setText ( selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();


    }

    public class alarm{
        public void test_alarm(int year,int month, int day, int hour, int minutes){
            Calendar myAlarmDate = Calendar.getInstance();
            myAlarmDate.setTimeInMillis(System.currentTimeMillis());
            myAlarmDate.set(year, month , day, hour , minutes , 0);
            final int _id = (int) System.currentTimeMillis();
            AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);

            Intent _myIntent = new Intent(getApplicationContext(), MyReceiver.class);
            _myIntent.putExtra("MyMessage","HERE I AM PASSING THEPERTICULAR MESSAGE WHICH SHOULD BE SHOW ON RECEIVER OF ALARM");
            PendingIntent _myPendingIntent = PendingIntent.getBroadcast(getApplicationContext(), _id, _myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.set(AlarmManager.RTC_WAKEUP, myAlarmDate.getTimeInMillis(),_myPendingIntent);
            Log.e("Setting alarm","with date and time");
        }
    }
}
