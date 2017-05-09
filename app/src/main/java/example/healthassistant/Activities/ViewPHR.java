package example.healthassistant.Activities;

import android.content.ContentValues;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import example.healthassistant.DbContract;
import example.healthassistant.DbHelper;
import example.healthassistant.R;

public class ViewPHR extends AppCompatActivity {
    private SQLiteDatabase mDb;
    SQLiteOpenHelper db;
    EditText viewPHRName_editText;
    EditText viewPHRAddress_editText ;
    EditText viewPHRDOB_editText ;
    Spinner viewPHRSex_editText ;
    EditText viewPHRGym_editText ;
    EditText viewPHRBreakfast_editText;
    EditText viewPHRLunch_editText ;
    EditText viewPHRDinner_editText ;
    EditText viewPHRPrimaryContact_editText ;
    EditText viewPHREmergencyContact_editText ;
    EditText viewPHRCaretakerContact_editText ;
    EditText viewPHRHeight_editText;
    EditText viewPHRWeight_editText;
    Button savePHR;

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

         viewPHRName_editText = (EditText) findViewById(R.id.viewPhrName);
         viewPHRAddress_editText = (EditText) findViewById(R.id.viewPhrAddress);
         viewPHRDOB_editText = (EditText) findViewById(R.id.viewPhrDOB);
         viewPHRSex_editText = (Spinner) findViewById(R.id.viewPhrSex);
         viewPHRGym_editText = (EditText) findViewById(R.id.viewPhrGym);
         viewPHRBreakfast_editText = (EditText) findViewById(R.id.viewPhrBreakfast);
         viewPHRLunch_editText = (EditText) findViewById(R.id.viewPhrLunch);
         viewPHRDinner_editText = (EditText) findViewById(R.id.viewPhrDinner);
         viewPHRPrimaryContact_editText = (EditText) findViewById(R.id.viewPhrPrimaryContact);
         viewPHREmergencyContact_editText = (EditText) findViewById(R.id.viewPhrEmergencyContact);
         viewPHRCaretakerContact_editText = (EditText) findViewById(R.id.viewPhrCaretakerContact);
         viewPHRHeight_editText = (EditText) findViewById(R.id.viewPhrHeight);
         viewPHRWeight_editText = (EditText) findViewById(R.id.viewPhrWeight);
         viewPHRSex_editText.setEnabled(false);

        savePHR = (Button) findViewById(R.id.savePHR);


        Cursor cursor = getAllRows();
        int indexName = cursor.getColumnIndex(DbContract.DbEntryPHR.COLUMN_NAME);
        Log.d("ViewPHR: "," "+ cursor.getString(indexName));
        int indexAddress = cursor.getColumnIndex(DbContract.DbEntryPHR.COLUMN_ADDRESS);
        int indexDOB = cursor.getColumnIndex(DbContract.DbEntryPHR.COLUMN_DOB);
        int indexSex = cursor.getColumnIndex(DbContract.DbEntryPHR.COLUMN_SEX);
        final int indexHeight = cursor.getColumnIndex(DbContract.DbEntryPHR.COLUMN_HEIGHT_FEET);
        int indexWeight = cursor.getColumnIndex(DbContract.DbEntryPHR.COLUMN_EYE_SIGHT);
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
            ArrayAdapter myAdap = (ArrayAdapter) viewPHRSex_editText.getAdapter(); //cast to an ArrayAdapter

            int spinnerPosition = myAdap.getPosition(cursor.getString(indexSex));


            viewPHRName_editText.setText(cursor.getString(indexName));
            viewPHRAddress_editText.setText(cursor.getString(indexAddress));
         //   viewPHRDOB_editText.setText(cursor.getString(indexDOB));
            viewPHRSex_editText.setSelection(spinnerPosition);
            viewPHRHeight_editText.setText(cursor.getString(indexHeight));
            viewPHRWeight_editText.setText(cursor.getString(indexWeight));
            viewPHRGym_editText.setText(cursor.getString(indexGymTime));
            viewPHRBreakfast_editText.setText(cursor.getString(indexBreakfastTime));
            viewPHRLunch_editText.setText(cursor.getString(indexLunchTime));
            viewPHRDinner_editText.setText(cursor.getString(indexDinnerTime));
            viewPHRPrimaryContact_editText.setText(cursor.getString(indexPrimaryContact));
            viewPHREmergencyContact_editText.setText(cursor.getString(indexEmergencyContact));
            viewPHRCaretakerContact_editText.setText(cursor.getString(indexCareTakerContact));

        }
        else {
            Log.d("View AddPHRActivity: " , "No Record Exists");
            Toast.makeText(this, "No Record Exists", Toast.LENGTH_LONG).show();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "You can now edit your information", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                viewPHRName_editText.setEnabled(true);
                viewPHRAddress_editText.setEnabled(true);
                viewPHRDOB_editText.setEnabled(true);
                viewPHRSex_editText.setEnabled(true);
                viewPHRHeight_editText.setEnabled(true);
                viewPHRWeight_editText.setEnabled(true);
                viewPHRGym_editText.setEnabled(true);
                viewPHRBreakfast_editText.setEnabled(true);
                viewPHRLunch_editText.setEnabled(true);
                viewPHRDinner_editText.setEnabled(true);
                viewPHRPrimaryContact_editText.setEnabled(true);
                viewPHREmergencyContact_editText.setEnabled(true);
                viewPHRCaretakerContact_editText.setEnabled(true);
                savePHR.setVisibility(View.VISIBLE);

            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        savePHR.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                updateAllRows();
            }
        });
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

    public void updateAllRows(){
        String where = null;
        db = new DbHelper(getApplicationContext());
        Log.d("ViewPHR", " entered get all rows");
        mDb = db.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbContract.DbEntryPHR.COLUMN_NAME, viewPHRName_editText.getText().toString());
        contentValues.put(DbContract.DbEntryPHR.COLUMN_ADDRESS, viewPHRAddress_editText.getText().toString());
        contentValues.put(DbContract.DbEntryPHR.COLUMN_DOB, viewPHRDOB_editText.getText().toString());
        contentValues.put(DbContract.DbEntryPHR.COLUMN_SEX, viewPHRSex_editText.getSelectedItem().toString());
        contentValues.put(DbContract.DbEntryPHR.COLUMN_HEIGHT_FEET, viewPHRHeight_editText.getText().toString());
        contentValues.put(DbContract.DbEntryPHR.COLUMN_EYE_SIGHT, viewPHRWeight_editText.getText().toString());
        contentValues.put(DbContract.DbEntryPHR.COLUMN_GYM_TIME, viewPHRGym_editText.getText().toString());
        contentValues.put(DbContract.DbEntryPHR.COLUMN_BREAKFAST_TIME, viewPHRBreakfast_editText.getText().toString());
        contentValues.put(DbContract.DbEntryPHR.COLUMN_LUNCH_TIME, viewPHRLunch_editText.getText().toString());
        contentValues.put(DbContract.DbEntryPHR.COLUMN_DINNER_TIME, viewPHRDinner_editText.getText().toString());
        contentValues.put(DbContract.DbEntryPHR.COLUMN_PRIMARY_CONTACT, viewPHRPrimaryContact_editText.getText().toString());
        contentValues.put(DbContract.DbEntryPHR.COLUMN_EMERGENCT_CONTACT, viewPHREmergencyContact_editText.getText().toString());
        contentValues.put(DbContract.DbEntryPHR.COLUMN_CARETAKER_CONTACT, viewPHRCaretakerContact_editText.getText().toString());

        try {
            mDb.update(DbContract.DbEntryPHR.TABLE_NAME, contentValues, null, null);
            viewPHRName_editText.setEnabled(false);
            viewPHRAddress_editText.setEnabled(false);
            viewPHRDOB_editText.setEnabled(false);
            viewPHRSex_editText.setEnabled(false);
            viewPHRGym_editText.setEnabled(false);
            viewPHRBreakfast_editText.setEnabled(false);
            viewPHRLunch_editText.setEnabled(false);
            viewPHRDinner_editText.setEnabled(false);
            viewPHRPrimaryContact_editText.setEnabled(false);
            viewPHREmergencyContact_editText.setEnabled(false);
            viewPHRCaretakerContact_editText.setEnabled(false);
            savePHR.setVisibility(View.INVISIBLE);
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),"Record not updated, something went wrong!",Toast.LENGTH_LONG);
        }finally {
            Toast.makeText(getApplicationContext(),"Record updated successfully!",Toast.LENGTH_LONG);

        }


        /*try {
            if (cursor != null) {
                cursor.moveToFirst();
                int indexName = cursor.getColumnIndex(DbContract.DbEntryPHR.COLUMN_NAME);
                Log.d("ViewPHR:GetAllRows", cursor.getString(indexName));
            }

        }catch(Exception e){
            Log.d("Exception Occured: " , " " +e);
        }*/

    }

}
