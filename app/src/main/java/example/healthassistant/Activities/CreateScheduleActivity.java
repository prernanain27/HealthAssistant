package example.healthassistant.Activities;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import example.healthassistant.DbContract;
import example.healthassistant.DbHelper;
import example.healthassistant.Models.Interferer;
import example.healthassistant.Models.Med_Specification;
import example.healthassistant.Models.Medicine;
import example.healthassistant.Models.Prescription;
import example.healthassistant.Models.User;
import example.healthassistant.R;
import example.healthassistant.SchedularClasses.ODATSchedular;

import static example.healthassistant.Activities.ViewPrescription.ALL_COLUMNS;

public class CreateScheduleActivity extends AppCompatActivity {

   private String TAG = "Create Scedule Activity";
    private Context mContext;
    private SQLiteDatabase mDb;
    ArrayList<Prescription> prescriptions;
    ArrayList<Med_Specification> MSS;
    HashMap<String,String> mUserPreferences;
    SQLiteOpenHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_schedule);

        mUserPreferences = new HashMap<>();
        prescriptions = new ArrayList<>();
        MSS = new ArrayList<>();
        prescriptions = getAllPrescriptions();
        MSS = getAllMedSpecData();
        getUserPreferences();

        ODATSchedular schedular = new ODATSchedular();
        schedular.createODATSchedule(MSS,prescriptions,mUserPreferences);

    }

    private  void getUserPreferences()
    {
        Log.d(TAG,"getUserPreferences method entered");
        db=new DbHelper(getApplicationContext());
        mDb = db.getWritableDatabase();
        Cursor cursor = null;

        try
        {
            cursor = getAllRows(DbContract.DbEntryPHR.TABLE_NAME, User.USERPREFCOLUMNNAMES);
            int indexWakeTime = cursor.getColumnIndex(DbContract.DbEntryPHR.COLUMN_WAKE_UP_TIME);
            int indexBreakfastTime = cursor.getColumnIndex(DbContract.DbEntryPHR.COLUMN_BREAKFAST_TIME);
            int indexGymTime = cursor.getColumnIndex(DbContract.DbEntryPHR.COLUMN_GYM_TIME);
            int indexLunchTime = cursor.getColumnIndex(DbContract.DbEntryPHR.COLUMN_LUNCH_TIME);
            int indexDinnerTime = cursor.getColumnIndex(DbContract.DbEntryPHR.COLUMN_DINNER_TIME);


            if(cursor.getCount()>0)
            {
                do{

                    String wakeupTime = cursor.getString(indexWakeTime);
                    String breakfastTime = cursor.getString(indexBreakfastTime);
                    String gymTime = cursor.getString(indexGymTime);
                    String lunchTime = cursor.getString(indexLunchTime);
                    String dinnerTime = cursor.getString(indexDinnerTime);


                    mUserPreferences.put(User.WAKE_UP_TIME,wakeupTime);
                    mUserPreferences.put(User.BREAKFAST_TIME,breakfastTime);
                    mUserPreferences.put(User.GYM_TIME,gymTime);
                    mUserPreferences.put(User.LUNCH_TIME,lunchTime);
                    mUserPreferences.put(User.DINNER_TIME,dinnerTime);

                }while (cursor.moveToNext());
            }
        }
        catch (Exception ex)
        {
            Log.e(TAG,"Exception raised while reading User data"+ex);
        }
        finally {
            if(cursor!=null)
            {
                cursor.close();
            }
        }

        db.close();


    }
    private ArrayList<Med_Specification> getAllMedSpecData()
    {
        Log.d(TAG,"getAllMedSpecData entered");
        ArrayList<Med_Specification> _medSpecs = new ArrayList<>();
        db = new DbHelper(getApplicationContext());
        mDb = db.getWritableDatabase();
        Cursor cursor = null;

        try{

            cursor = getAllRows(DbContract.DbEntryMed.TABLE_NAME,Med_Specification.ALL_COLUMNS_MEDSPEC);
            int indexMedId = cursor.getColumnIndex(DbContract.DbEntryMed.COLUMN_ID);
            int indexMedName = cursor.getColumnIndex(DbContract.DbEntryMed.COLUMN_MED_NAME);
            int indexMinDose = cursor.getColumnIndex(DbContract.DbEntryMed.COLUMN_MIN_DOSE);
            int indexMaxDose = cursor.getColumnIndex(DbContract.DbEntryMed.COLUMN_MAX_DOSE);
            int indexColSeperation = cursor.getColumnIndex(DbContract.DbEntryMed.COLUMN_SEPARATION);


            if(cursor.getCount()>0)
            {
                do {

                    Med_Specification MSSObj = new Med_Specification();
                    MSSObj.setMed_id(cursor.getString(indexMedId));
                    MSSObj.setMed_name(cursor.getString(indexMedName));
                    MSSObj.setMax_dose(cursor.getString(indexMaxDose));
                    MSSObj.setMin_dose(cursor.getString(indexMinDose));
                    MSSObj.setMin_seperation(cursor.getString(indexColSeperation));


                    ArrayList<Interferer> temp = getInterfererData(MSSObj.getMed_id());
                    MSSObj.setInterfererList(getInterfererData(MSSObj.getMed_id()));


                    _medSpecs.add(MSSObj);

                }while (cursor.moveToNext());
            }



        }catch (Exception ex)
        {
            Log.e(TAG,"Exception raised while reading Medicine specification data"+ex);
        }
        finally {
            if(cursor!=null)
            {
                cursor.close();
            }
        }

        db.close();

        return _medSpecs;
    }

    private ArrayList<Interferer> getInterfererData(String medId)
    {
        Log.d(TAG,"getInterfererData method entered");
        ArrayList<Interferer> interfererArrayList = new ArrayList<>();
        String whereClause = DbContract.DbEntryInterferer.COLUMN_MED_ID + "=?";
        String[] whereArgs = {medId};
        db= new DbHelper(getApplicationContext());
        mDb = db.getWritableDatabase();
        Cursor cursor =null;

        try
        {
            cursor = mDb.query(DbContract.DbEntryInterferer.TABLE_NAME,Interferer.ALL_COLUMNS_Interferer,whereClause,whereArgs,null,null,null);
            int indexMedId = cursor.getColumnIndex(DbContract.DbEntryInterferer.COLUMN_MED_ID);
            int indexInterfererId = cursor.getColumnIndex(DbContract.DbEntryInterferer.COLUMN_INTERFERER_ID);
            int indexMinFrom = cursor.getColumnIndex(DbContract.DbEntryInterferer.COLUMN_MIN_FROM);
            int indexMinTo = cursor.getColumnIndex(DbContract.DbEntryInterferer.COLUMN_MIN_TO);

            if(cursor.getCount()>0)
            {
                while (cursor.moveToNext()) {

                    Interferer intf = new Interferer();
                    intf.setMed_id(cursor.getString(indexMedId));
                    intf.setInterferer_id(cursor.getString(indexInterfererId));
                    intf.setMinFromInterferer(cursor.getString(indexMinFrom));
                    intf.setMinToInterferer(cursor.getString(indexMinTo));

                    interfererArrayList.add(intf);
                }
            }

        }
        catch (Exception ex)
        {
            Log.d(TAG,"Exception reading interferer data \n"+ex);
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        db.close();

        return interfererArrayList;
    }

    private ArrayList<Prescription> getAllPrescriptions()
    {
        Log.d(TAG,"getAllPrescriptions method entered :");

        ArrayList<Prescription> temmPres = new ArrayList<>();
        ArrayList<Medicine> tepMeds = new ArrayList<>();
        db = new DbHelper(getApplicationContext());
        mDb = db.getWritableDatabase();
        Cursor cursor = null;

        try
        {

            cursor = getAllRows(DbContract.DbEntryPrescription.TABLE_NAME,ALL_COLUMNS);
            int indexPresName = cursor.getColumnIndex(DbContract.DbEntryPrescription.COLUMN_PRESCRIPTION_NAME);
            int indexDisease = cursor.getColumnIndex(DbContract.DbEntryPrescription.COLUMN_DISEASE);

            if(cursor.getCount()>0)
            {
                do
                {
                    Prescription pres = new Prescription();
                    pres.setPrescriptionName(cursor.getString(indexPresName));
                    pres.setDisease(cursor.getString(indexDisease));


                    tepMeds = getMedicineData(pres.getPrescriptionName());
                    pres.setMedicineArrayList(tepMeds);

                    temmPres.add(pres);

                }while(cursor.moveToNext());
            }

        }
        catch (Exception ex)
        {
            Log.e(TAG,"Exception raised while reading prescription data"+ex);
        }
        finally {
            if(cursor!=null)
            {
                cursor.close();
            }
        }

        db.close();
        return temmPres;
    }

    public  ArrayList<Medicine> getMedicineData(String prescriptionName) {
        //setContentView(R.layout.medicine_item_cv);

        Log.d("ViewMedicine","Entered Get Medicine Data Method");
        String whereClause = DbContract.DbEntryPrescription.COLUMN_PRESCRIPTION_NAME + "=?";
        ArrayList<Medicine> viewMedicines = new ArrayList<>();
        String[] whereArgs = {prescriptionName};
        mContext = getApplicationContext();
        db =  new DbHelper(getApplicationContext());
        mDb = db.getWritableDatabase();
        Cursor cursor = null;

        try {
            cursor = mDb.query(DbContract.DbEntryPrescription.TABLE_NAME, ALL_COLUMNS, whereClause, whereArgs, null, null, null);
            int indexMedName = cursor.getColumnIndex(DbContract.DbEntryPrescription.COLUMN_MED_NAME);
            int indexMedTime_BB = cursor.getColumnIndex(DbContract.DbEntryPrescription.COLUMN_MED_TIME_BB);
            int indexMedTime_AB = cursor.getColumnIndex(DbContract.DbEntryPrescription.COLUMN_MED_TIME_AB);
            int indexMedTime_BL = cursor.getColumnIndex(DbContract.DbEntryPrescription.COLUMN_MED_TIME_BL);
            int indexMedTime_AL = cursor.getColumnIndex(DbContract.DbEntryPrescription.COLUMN_MED_TIME_AL);
            int indexMedTime_BD = cursor.getColumnIndex(DbContract.DbEntryPrescription.COLUMN_MED_TIME_BD);
            int indexMedTime_AD = cursor.getColumnIndex(DbContract.DbEntryPrescription.COLUMN_MED_TIME_AD);
            int indexMedDose = cursor.getColumnIndex(DbContract.DbEntryPrescription.COLUMN_MED_DOSE);
            int indexMedType = cursor.getColumnIndex(DbContract.DbEntryPrescription.COLUMN_MED_TYPE);
            int indexMedDuration = cursor.getColumnIndex(DbContract.DbEntryPrescription.COLUMN_DURATION);
            Log.d("GetMedicineData","MedicineName Index");

            // CardView card = (CardView) findViewById(R.id.card_view_medItem);
            if (cursor.getCount() > 0) {

                while (cursor.moveToNext()) {
                    Medicine med = new Medicine();
                    med.setMedName(cursor.getString(indexMedName));
                    med.setMedDose(cursor.getString(indexMedDose));
                    med.setMedDuration(cursor.getString(indexMedDuration));
                    med.setMedType(cursor.getString(indexMedType));
                    List<String> times = new ArrayList<>();
                    times.add(cursor.getString(indexMedTime_BB));
                    times.add(cursor.getString(indexMedTime_AB));
                    times.add(cursor.getString(indexMedTime_BL));
                    times.add(cursor.getString(indexMedTime_AL));
                    times.add(cursor.getString(indexMedTime_BD));
                    times.add(cursor.getString(indexMedTime_AD));
                    med.setMedTime(times);
                    viewMedicines.add(med);

                    Log.d("GetMedicineData"," Inside Cursor Loop MedicineName Index: " + cursor.getString(indexMedName));
                }
            }
        } catch (Exception e) {
            Log.d("ViewMedicine", "Get Medicine Data method: Exception Raised with a value of " + e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        db.close();
        return viewMedicines;
    }
    public Cursor getAllRows(String tableName,String[] columns){
        String where = null;
        db =  new DbHelper(getApplicationContext());
        Log.d(TAG," entered get all rows");
        mDb = db.getWritableDatabase();
        Cursor cursor = null;
        if(tableName.equals( DbContract.DbEntryPrescription.TABLE_NAME)) {
            cursor = mDb.query(true, tableName, columns, where, null, DbContract.DbEntryPrescription.COLUMN_PRESCRIPTION_NAME, null, null, null);
        }
        else
        {
            cursor = mDb.query(tableName,columns,where,null,null,null,null,null);
        }

        try {
            if (cursor != null) {
                cursor.moveToFirst();
                Log.d("GetAllRows", cursor.getString(1));
            }
        }catch (SQLiteException ex)
        {
            Log.e(TAG,"Exception reading user data");
        }
        db.close();
        return  cursor;
    }
    private void createJobModel()
    {
        Log.d(TAG,"CreateJobModel method entered");
        String whereClause = DbContract.DbEntryMed.COLUMN_ID + "=?";

        Medicine currentMedicine = new Medicine();
        String[] whereArgs = {};

        db = new DbHelper(getApplicationContext());


    }
}
