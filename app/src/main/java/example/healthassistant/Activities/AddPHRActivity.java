package example.healthassistant.Activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import example.healthassistant.DbContract;
import example.healthassistant.DbHelper;
import example.healthassistant.Fragments.DetailedInformation;
import example.healthassistant.Fragments.PersonalInformation;
import example.healthassistant.Models.User;
import example.healthassistant.R;
import example.healthassistant.Fragments.TimingInformation;

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
import static example.healthassistant.DbContract.DbEntryPHR.COLUMN_WAKE_UP_TIME;


public class AddPHRActivity extends AppCompatActivity  {
    private Button personal;
    private Button details;
    private Button timings;
    private Button save;
    public String dateFromPicker;
    private SQLiteDatabase mDb;
    SQLiteOpenHelper db;
//    User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = new DbHelper(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phr);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        PersonalInformation personalInformation =new PersonalInformation();
        fragmentTransaction.replace(R.id.contentFrame, personalInformation);
        fragmentTransaction.commit();
        save = (Button)findViewById(R.id.savePHR);
        personal=(Button) findViewById(R.id.personal);
        personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                PersonalInformation personalInformation =new PersonalInformation();
                fragmentTransaction.replace(R.id.contentFrame, personalInformation);
                fragmentTransaction.commit();
            }
        });
        details=(Button) findViewById(R.id.details);
        details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(User.getName()!= "" ||User.getSex()!= ""||User.getPrimary_contact()!= ""||User.getEmergency_contact()!= ""){
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                DetailedInformation detailedInformation =new DetailedInformation();
                fragmentTransaction.replace(R.id.contentFrame, detailedInformation);
             fragmentTransaction.commit();}
                else
                    Toast.makeText(AddPHRActivity.this, "Fill all the mandatory Personal Information",Toast.LENGTH_SHORT).show();

            }
        });
        timings=(Button) findViewById(R.id.timings);
        timings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(User.getBlood_sign()!=""||User.getBoold_type()!=""||User.getHeight_feet()!=""||User.getHeight_inches()!=""){
                FragmentManager fragmentManager = getFragmentManager();
                 FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                TimingInformation timingInformation =new TimingInformation();
                fragmentTransaction.replace(R.id.contentFrame, timingInformation);
                fragmentTransaction.commit();}
                else
                    Toast.makeText(AddPHRActivity.this, "Fill all the mandatory Detailed Information",Toast.LENGTH_SHORT).show();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(User.getName()!= "" ||User.getSex()!= ""||User.getDob()!= ""||User.getPrimary_contact()!= ""||User.getEmergency_contact()!= ""){
                    if(User.getBlood_sign()!=""||User.getBoold_type()!=""||User.getHeight_feet()!=""||User.getHeight_inches()!=""){
                        if(User.getWake_up_time()!=""||User.getBreakfast_time()!=""||User.getLunch_time()!=""||User.getGym_time()!=""||User.getDinner_time()!=""||User.getSleep_time()!=""){
                          //  Toast.makeText(AddPHRActivity.this,"Wohhoo",Toast.LENGTH_SHORT).show();
                        }
                        else Toast.makeText(AddPHRActivity.this, "Fill Timings Mandatory Details",Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(AddPHRActivity.this, "Fill Detailed Mandatory Details",Toast.LENGTH_SHORT).show();

                }
                else
                    Toast.makeText(AddPHRActivity.this, "Fill Personal Mandatory Details",Toast.LENGTH_SHORT).show();
                addData();
            }

        });



    }

    public void addData(){
        Log.d("AddData: ", "Entered into Insert AddPHRActivity");
        //DbHelper db = new DbHelper(getActivity());

        mDb = db.getWritableDatabase();
//        User user = new User();
//        mDb = db.getWritableDatabase();
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
        Log.d(" AddPHRActivity: ", " " + User.getName());
        try {

            long result = mDb.insert(DbContract.DbEntryPHR.TABLE_NAME, null, cv);
            if(result != -1){
                Toast.makeText(this, "Inserted successfully", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this,HomeScreen.class);
                startActivity(i);}
            else
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
        catch (SQLiteException ex)
        {
            String s = ex.getMessage();
        }
        finally {
            db.close();
        }

    }
}
