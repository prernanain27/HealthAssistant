package example.healthassistant.Activities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import example.healthassistant.DbContract;
import example.healthassistant.DbHelper;
import example.healthassistant.R;

import static example.healthassistant.DbContract.DbEntryAppointment.COLUMN_DOC_CONTACT;
import static example.healthassistant.DbContract.DbEntryInterferer.COLUMN_INTERFERER_ID;
import static example.healthassistant.DbContract.DbEntryInterferer.COLUMN_MED_ID;
import static example.healthassistant.DbContract.DbEntryInterferer.COLUMN_MIN_FROM;
import static example.healthassistant.DbContract.DbEntryInterferer.COLUMN_MIN_TO;
import static example.healthassistant.DbContract.DbEntryMed.COLUMN_MAX_DOSE;
import static example.healthassistant.DbContract.DbEntryMed.COLUMN_MED_NAME;
import static example.healthassistant.DbContract.DbEntryMed.COLUMN_MIN_DOSE;
import static example.healthassistant.DbContract.DbEntryMed.COLUMN_SEPARATION;

public class Medicine_Schedule extends AppCompatActivity {
    private SQLiteDatabase mDb;
    SQLiteOpenHelper db;
    public static int a = 1 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine__schedule);
        db = new DbHelper(getApplicationContext());
        mDb = db.getWritableDatabase();
        String[] projection = {DbContract.DbEntryMed.COLUMN_MED_NAME};
        Cursor data = mDb.query(DbContract.DbEntryMed.TABLE_NAME,projection, null,null,null,null,null,null);
        if(!(data.getCount()>0)){
            addData();
        }



        Intent intent = new Intent(this,CreateScheduleActivity.class);
        startActivity(intent);

    }

    private void addData() {
        Log.d("AddData: ", "Entered into Insert Med_Spec");
        mDb = db.getWritableDatabase();
        // Content Values for MED_SPECIFICATION TABLE
        ContentValues cv = new ContentValues();
        ContentValues cv1 = new ContentValues();
        ContentValues cv2 = new ContentValues();
        ContentValues cv3 = new ContentValues();
        ContentValues cv4 = new ContentValues();
        ContentValues cv5 = new ContentValues();
        ContentValues cv6 = new ContentValues();
        ContentValues cv7 = new ContentValues();


        cv.put(COLUMN_MED_NAME,"warfarin");
        cv1.put(COLUMN_MED_NAME,"digoxin");
        cv2.put(COLUMN_MED_NAME,"coumadin");
        cv3.put(COLUMN_MED_NAME,"aspirin");
        cv4.put(COLUMN_MED_NAME,"questron");
        cv5.put(COLUMN_MED_NAME,"unithroid");
        cv6.put(COLUMN_MED_NAME,"methadone");
        cv7.put(COLUMN_MED_NAME,"dopamine");
        cv.put(COLUMN_MIN_DOSE,"1");
        cv1.put(COLUMN_MIN_DOSE,"1");
        cv2.put(COLUMN_MIN_DOSE,"1");
        cv3.put(COLUMN_MIN_DOSE,"1");
        cv4.put(COLUMN_MIN_DOSE,"1");
        cv5.put(COLUMN_MIN_DOSE,"1");
        cv6.put(COLUMN_MIN_DOSE,"1");
        cv7.put(COLUMN_MIN_DOSE,"1");
        cv.put(COLUMN_MAX_DOSE,"3");
        cv1.put(COLUMN_MAX_DOSE,"3");
        cv2.put(COLUMN_MAX_DOSE,"3");
        cv3.put(COLUMN_MAX_DOSE,"3");
        cv4.put(COLUMN_MAX_DOSE,"3");
        cv5.put(COLUMN_MAX_DOSE,"3");
        cv6.put(COLUMN_MAX_DOSE,"3");
        cv7.put(COLUMN_MAX_DOSE,"3");
        cv.put(COLUMN_SEPARATION,"60");
        cv1.put(COLUMN_SEPARATION,"60");
        cv2.put(COLUMN_SEPARATION,"60");
        cv3.put(COLUMN_SEPARATION,"60");
        cv4.put(COLUMN_SEPARATION,"60");
        cv5.put(COLUMN_SEPARATION,"60");
        cv6.put(COLUMN_SEPARATION,"60");
        cv7.put(COLUMN_SEPARATION,"60");
        //  cv.put(COLUMN_INTERFERENCE,"2");

        // CONTENT VALUES FOR MED_INTERFERER TABLE
        ContentValues cv8 = new ContentValues();
        ContentValues cv9 = new ContentValues();
        ContentValues cv10 = new ContentValues();
        ContentValues cv11 = new ContentValues();

        cv8.put(COLUMN_MED_ID , "1" );
        cv8.put(COLUMN_INTERFERER_ID , "2");
        cv8.put (COLUMN_MIN_FROM , "30");
        cv8.put (COLUMN_MIN_TO , "30");

        cv9.put(COLUMN_MED_ID , "3" );
        cv9.put(COLUMN_INTERFERER_ID , "4");
        cv9.put (COLUMN_MIN_FROM , "40");
        cv9.put (COLUMN_MIN_TO , "40");

        cv10.put(COLUMN_MED_ID , "5" );
        cv10.put(COLUMN_INTERFERER_ID , "6");
        cv10.put (COLUMN_MIN_FROM , "50");
        cv10.put (COLUMN_MIN_TO , "50");

        cv11.put(COLUMN_MED_ID , "7" );
        cv11.put(COLUMN_INTERFERER_ID , "8");
        cv11.put (COLUMN_MIN_FROM , "60");
        cv11.put (COLUMN_MIN_TO , "60");


        try {

            long result = mDb.insert(DbContract.DbEntryMed.TABLE_NAME, null, cv);
            long result1 = mDb.insert(DbContract.DbEntryMed.TABLE_NAME, null, cv1);
            long result2 = mDb.insert(DbContract.DbEntryMed.TABLE_NAME, null, cv2);
            long result3 = mDb.insert(DbContract.DbEntryMed.TABLE_NAME, null, cv3);
            long result4 = mDb.insert(DbContract.DbEntryMed.TABLE_NAME, null, cv4);
            long result5 = mDb.insert(DbContract.DbEntryMed.TABLE_NAME, null, cv5);
            long result6 = mDb.insert(DbContract.DbEntryMed.TABLE_NAME, null, cv6);
            long result7 = mDb.insert(DbContract.DbEntryMed.TABLE_NAME, null, cv7);
            long result8 = mDb.insert(DbContract.DbEntryInterferer.TABLE_NAME, null, cv8);
            long result9 = mDb.insert(DbContract.DbEntryInterferer.TABLE_NAME, null, cv9);
            long result10 = mDb.insert(DbContract.DbEntryInterferer.TABLE_NAME, null, cv10);
            long result11 = mDb.insert(DbContract.DbEntryInterferer.TABLE_NAME, null, cv11);

            if(result != -1&& result1 != -1 && result2 != -1 && result3 != -1 && result4 != -1 && result5 != -1 && result6 != -1 && result7 != -1) {
                if(result8 != -1 && result9 != -1 && result10 != -1 && result11 != -1 ) {
                    Toast.makeText(this, "Inserted successfully in both tables", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(this, "Inserted successfully in specifications tables", Toast.LENGTH_SHORT).show();
                }
            }
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
