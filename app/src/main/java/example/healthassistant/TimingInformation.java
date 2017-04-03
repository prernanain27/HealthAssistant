package example.healthassistant;

import android.app.Fragment;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
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
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User.setWake_up_time(wakeUp.getText().toString());
                User.setBreakfast_time(breakFast.getText().toString());
                User.setLunch_time(lunch.getText().toString());
                User.setGym_time(gym.getText().toString());
                User.setDinner_time(dinner.getText().toString());
                User.setSleep_time(sleep.getText().toString());
                addData();

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
    public void addData(){
        DbHelper db = new DbHelper(getActivity());
        mDb = db.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME,User.getName());
        cv.put(COLUMN_Email,User.getEmail());
        cv.put(COLUMN_SEX,User.getSex());
        cv.put(COLUMN_DOB,User.getDob());
        cv.put(COLUMN_ADDRESS,User.getAddress());
        cv.put(COLUMN_PRIMARY_CONTACT,User.getPrimary_contact());
        cv.put(COLUMN_EMERGENCT_CONTACT,User.getEmergency_contact());
        cv.put(COLUMN_CARETAKER_CONTACT,User.getCare_taker_contact());
        cv.put(COLUMN_BLOODTYPE,User.getBoold_type());
        cv.put(COLUMN_BLOOD_SIGN,User.getBlood_sign());
        cv.put(COLUMN_HEIGHT_FEET,User.getHeight_feet());
        cv.put(COLUMN_HEIGHT_INCHES,User.getHeight_inches());
        cv.put(COLUMN_EYE_SIGN,User.getEye_sign());
        cv.put(COLUMN_EYE_SIGHT,User.getEye_sight());
        cv.put(COLUMN_WAKE_UP_TIME,User.getWake_up_time());
        cv.put(COLUMN_BREAKFAST_TIME,User.getBreakfast_time());
        cv.put(COLUMN_LUNCH_TIME,User.getLunch_time());
        cv.put(COLUMN_GYM_TIME,User.getGym_time());
        cv.put(COLUMN_DINNER_TIME,User.getDinner_time());
        //cv.put(COLUMN_SLEEP_TIME,User.getSleep_time());

        try {

            long result = mDb.insert(DbContract.DbEntryPHR.TABLE_NAME, null, cv);
            if(result!=-1){
                Toast.makeText(getActivity(), "Inserted successfully", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getActivity(),HomeScreen.class);
            startActivity(i);}
            else
                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
        }
        catch (SQLiteException ex)
        {
            String s = ex.getMessage();
        }

    }
}
