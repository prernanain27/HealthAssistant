package example.healthassistant.Activities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import example.healthassistant.DbContract;
import example.healthassistant.DbHelper;
import example.healthassistant.R;

public class ViewPHR extends AppCompatActivity {
    private SQLiteDatabase mDb;
    SQLiteOpenHelper db;

    public static final String[] ALL_COLUMNS = {
            DbContract.DbEntryPHR.COLUMN_ID,
            DbContract.DbEntryPHR.COLUMN_ADDRESS,
            DbContract.DbEntryPHR.COLUMN_BLOODTYPE,
            DbContract.DbEntryPHR.COLUMN_BLOOD_SIGN,
            DbContract.DbEntryPHR.COLUMN_BREAKFAST_TIME,
            DbContract.DbEntryPHR.COLUMN_CARETAKER_CONTACT,
            DbContract.DbEntryPHR.COLUMN_DINNER_TIME,
            DbContract.DbEntryPHR.COLUMN_DOB,
            DbContract.DbEntryPHR.COLUMN_EMERGENCT_CONTACT,
            DbContract.DbEntryPHR.COLUMN_EYE_SIGHT,
            DbContract.DbEntryPHR.COLUMN_EYE_SIGN,
            DbContract.DbEntryPHR.COLUMN_Email,
            DbContract.DbEntryPHR.COLUMN_GYM_TIME,
            DbContract.DbEntryPHR.COLUMN_HEIGHT_FEET,
            DbContract.DbEntryPHR.COLUMN_HEIGHT_INCHES,
            DbContract.DbEntryPHR.COLUMN_LUNCH_TIME,
            DbContract.DbEntryPHR.COLUMN_NAME,
            DbContract.DbEntryPHR.COLUMN_PRIMARY_CONTACT,
            DbContract.DbEntryPHR.COLUMN_SEX,
            DbContract.DbEntryPHR.COLUMN_WAKE_UP_TIME

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_phr);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView viewPHRHead_textView = (TextView) findViewById(R.id.viewPhrHeading);
        TextView viewPHRData_textView = (TextView) findViewById(R.id.viewPhrData);
        Cursor cursor = getAllRows();
        int indexName = cursor.getColumnIndex(DbContract.DbEntryPHR.COLUMN_NAME);
        Log.d("ViewPHR: "," "+ cursor.getString(indexName));
        int indexAddress = cursor.getColumnIndex(DbContract.DbEntryPHR.COLUMN_ADDRESS);
        int indexDOB = cursor.getColumnIndex(DbContract.DbEntryPHR.COLUMN_DOB);
        int indexSex = cursor.getColumnIndex(DbContract.DbEntryPHR.COLUMN_SEX);
        //int indexBloodType = cursor.getColumnIndex(DbContract.DbEntryPHR.COLUMN_BLOODTYPE);
        //int indexBloodSign = cursor.getColumnIndex(DbContract.DbEntryPHR.COLUMN_BLOOD_SIGN);
        int indexGymTime = cursor.getColumnIndex(DbContract.DbEntryPHR.COLUMN_GYM_TIME);
        int indexLunchTime = cursor.getColumnIndex(DbContract.DbEntryPHR.COLUMN_LUNCH_TIME);
        int indexBreakfastTime = cursor.getColumnIndex(DbContract.DbEntryPHR.COLUMN_BREAKFAST_TIME);
        int indexDinnerTime = cursor.getColumnIndex(DbContract.DbEntryPHR.COLUMN_DINNER_TIME);
        int indexPrimaryContact = cursor.getColumnIndex(DbContract.DbEntryPHR.COLUMN_PRIMARY_CONTACT);
        int indexEmergencyContact = cursor.getColumnIndex(DbContract.DbEntryPHR.COLUMN_EMERGENCT_CONTACT);
        int indexCareTakerContact = cursor.getColumnIndex(DbContract.DbEntryPHR.COLUMN_CARETAKER_CONTACT);
        if(cursor.getCount() != 0) {
            Log.d("View AddPHRActivity: " , "Record Exists");
            viewPHRHead_textView.setText("Name:" + "\n \n" + "Address:" + "\n \n" +
                    "DOB:" + "\n \n" + "Sex:" + "\n \n" + "Gym Time:" + "\n \n" + "Breakfast Time:" + "\n \n" + "Lunch Time:" +
                    "\n \n" + "Dinner Time:" + "\n \n" + "Primary Contact:" + "\n \n" + "Emergency Contact:" + "\n \n" + "CareTaker Contact:");
            viewPHRData_textView.setText(cursor.getString(indexName) + "\n \n" + cursor.getString(indexAddress) + "\n \n" +
                    cursor.getString(indexDOB) + "\n \n" + cursor.getString(indexSex) + "\n \n" + cursor.getString(indexGymTime) +
                    "\n \n" + cursor.getString(indexBreakfastTime)
                    + "\n \n" + cursor.getString(indexLunchTime) + "\n \n" + cursor.getString(indexDinnerTime) +
                    "\n \n" + cursor.getString(indexPrimaryContact) + "\n \n" + cursor.getString(indexEmergencyContact) + "\n \n"
                    + cursor.getString(indexCareTakerContact));
        }
        else {
            Log.d("View AddPHRActivity: " , "No Record Exists");
            Toast.makeText(this, "No Record Exists", Toast.LENGTH_LONG).show();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public Cursor getAllRows() {
        String where = null;
        db = new DbHelper(getApplicationContext());
        Log.d("ViewPHR", " entered get all rows");
        mDb = db.getWritableDatabase();
        Cursor cursor = mDb.query(true, DbContract.DbEntryPHR.TABLE_NAME, ALL_COLUMNS, where, null, null, null, null, null);

        try {
            if (cursor != null) {
                cursor.moveToFirst();
                int indexName = cursor.getColumnIndex(DbContract.DbEntryPHR.COLUMN_NAME);
                Log.d("ViewPHR:GetAllRows", cursor.getString(indexName));
            }

        }catch(Exception e){
            Log.d("Exception Occured: " , " " +e);
        }

        return cursor;
    }

}
