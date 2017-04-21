package example.healthassistant;

import android.app.Fragment;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import static example.healthassistant.DbContract.DbEntryPHR.COLUMN_ADDRESS;
import static example.healthassistant.DbContract.DbEntryPHR.COLUMN_BLOODTYPE;
import static example.healthassistant.DbContract.DbEntryPHR.COLUMN_BLOOD_SIGN;
import static example.healthassistant.DbContract.DbEntryPHR.COLUMN_BREAKFAST_TIME;
import static example.healthassistant.DbContract.DbEntryPHR.COLUMN_CARETAKER_CONTACT;
import static example.healthassistant.DbContract.DbEntryPHR.COLUMN_DINNER_TIME;
import static example.healthassistant.DbContract.DbEntryPHR.COLUMN_DOB;
import static example.healthassistant.DbContract.DbEntryPHR.COLUMN_EMERGENCT_CONTACT;
import static example.healthassistant.DbContract.DbEntryPHR.COLUMN_EYE_SIGHT;
import static example.healthassistant.DbContract.DbEntryPHR.COLUMN_EYE_SIGN;
import static example.healthassistant.DbContract.DbEntryPHR.COLUMN_Email;
import static example.healthassistant.DbContract.DbEntryPHR.COLUMN_GYM_TIME;
import static example.healthassistant.DbContract.DbEntryPHR.COLUMN_HEIGHT_FEET;
import static example.healthassistant.DbContract.DbEntryPHR.COLUMN_HEIGHT_INCHES;
import static example.healthassistant.DbContract.DbEntryPHR.COLUMN_LUNCH_TIME;
import static example.healthassistant.DbContract.DbEntryPHR.COLUMN_NAME;
import static example.healthassistant.DbContract.DbEntryPHR.COLUMN_PRIMARY_CONTACT;
import static example.healthassistant.DbContract.DbEntryPHR.COLUMN_SEX;
//import static example.healthassistant.DbContract.DbEntryPHR.COLUMN_SLEEP_TIME;
import static example.healthassistant.DbContract.DbEntryPHR.COLUMN_WAKE_UP_TIME;

public class TimingInformation extends Fragment {

    private EditText wakeUp;
    private EditText breakFast;
    private EditText lunch;
    private EditText gym;
    private EditText dinner;
    private EditText sleep;
    private Calendar calendar;
    private Button save;
    private SQLiteDatabase mDb;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle
            savedInstanceState) {
        //----inflate the layout for this fragment --
        View view = inflater.inflate(R.layout.activity_fragment3,container, false);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        save = (Button)getActivity().findViewById(R.id.save);
        calendar = java.util.Calendar.getInstance();
        wakeUp = (EditText)getView().findViewById(R.id.wakeupTime);
        wakeUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeSet(wakeUp);

            }
        });

        breakFast= (EditText)getView().findViewById(R.id.breakfastTime);
        breakFast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeSet(breakFast);

            }
        });

        lunch = (EditText)getView().findViewById(R.id.lunchTime);
        lunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeSet(lunch);

            }
        });

        gym = (EditText)getView().findViewById(R.id.gymTime);
        gym.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeSet(gym);

            }
        });

        dinner = (EditText)getView().findViewById(R.id.dinnerTime);
        dinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeSet(dinner);

            }
        });

        sleep = (EditText)getView().findViewById(R.id.sleepTime);
        sleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeSet(sleep);

            }
        });
//        final User user = new User();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User.setWake_up_time(wakeUp.getText().toString());
                User.setBreakfast_time(breakFast.getText().toString());
                User.setLunch_time(lunch.getText().toString());
                User.setGym_time(gym.getText().toString());
                User.setDinner_time(dinner.getText().toString());
                User.setSleep_time(sleep.getText().toString());
                Toast.makeText(getActivity().getApplicationContext(),"Information Saved",Toast.LENGTH_SHORT).show();

//                addData();

            }
        });

    }

    private void timeSet (final EditText idofEditText){
        int hour = calendar.get(java.util.Calendar.HOUR_OF_DAY);
        int minute = calendar.get(java.util.Calendar.MINUTE);



        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                idofEditText.setText ( selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();


    }

}
