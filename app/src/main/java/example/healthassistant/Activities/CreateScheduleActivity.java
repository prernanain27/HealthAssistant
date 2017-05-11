package example.healthassistant.Activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import example.healthassistant.DbContract;
import example.healthassistant.DbHelper;
import example.healthassistant.Models.Interferer;
import example.healthassistant.Models.Med_Specification;
import example.healthassistant.Models.Medicine;
import example.healthassistant.Models.Prescription;
import example.healthassistant.Models.User;
import example.healthassistant.MyReceiver;
import example.healthassistant.MyReceiver_DailySchedule;
import example.healthassistant.R;
import example.healthassistant.SchedularClasses.MedScheduleItem;
import example.healthassistant.SchedularClasses.ODATSchedular;
import example.healthassistant.SchedularClasses.ResourceModel;

import static example.healthassistant.Activities.ViewPrescription.ALL_COLUMNS;
import static example.healthassistant.DbContract.DbEntryInterferer.COLUMN_INTERFERER_ID;
import static example.healthassistant.DbContract.DbEntryInterferer.COLUMN_MED_ID;
import static example.healthassistant.DbContract.DbEntryInterferer.COLUMN_MIN_FROM;
import static example.healthassistant.DbContract.DbEntryInterferer.COLUMN_MIN_TO;
import static example.healthassistant.DbContract.DbEntryMed.COLUMN_MAX_DOSE;
import static example.healthassistant.DbContract.DbEntryMed.COLUMN_MED_NAME;
import static example.healthassistant.DbContract.DbEntryMed.COLUMN_MIN_DOSE;
import static example.healthassistant.DbContract.DbEntryMed.COLUMN_SEPARATION;

public class CreateScheduleActivity extends AppCompatActivity {

    private String TAG = "Create Scedule Activity";
    private Context mContext;
    private SQLiteDatabase mDb;
    ArrayList<Prescription> prescriptions;
    ArrayList<Med_Specification> MSS;
    HashMap<String, String> mUserPreferences;

    ResourceModel finalScheduleModel = new ResourceModel();
    SQLiteOpenHelper db;


    //from prerna
    private PendingIntent pendingIntent;
    private AlarmManager alarmManager;
    public ListView lv;
    List<String> names;
    List<MedScheduleItem> schedule = new ArrayList<MedScheduleItem>();

    alarm[] a = new alarm[20];
//In On Create..........


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_schedule);

        mUserPreferences = new HashMap<>();
        prescriptions = new ArrayList<>();
        MSS = new ArrayList<>();

        db = new DbHelper(getApplicationContext());
        mDb = db.getWritableDatabase();

        String[] projection = {DbContract.DbEntryMed.COLUMN_MED_NAME};
        Cursor data = mDb.query(DbContract.DbEntryMed.TABLE_NAME, projection, null, null, null, null, null, null);
        if (!(data.getCount() > 0)) {
            addData();
        }




        prescriptions = getAllPrescriptions();
        MSS = getAllMedSpecData();
        getUserPreferences();

        ODATSchedular schedular = new ODATSchedular();
        finalScheduleModel = schedular.createODATSchedule(MSS, prescriptions, mUserPreferences);

        //from prerna
        lv = (ListView) findViewById(R.id.listView);
        schedule = finalScheduleModel.getSchedule();
        lv.setAdapter(new CustomAdapter());
        lv.setBackgroundColor(Color.parseColor("#c60505"));
        ring();

    }

    public void ring() {

        for (int i = 0; i < schedule.size(); i++) {
            String time = schedule.get(i).getScheduleTime();
            String[] split = time.split(":");
            int hour = Integer.parseInt(split[0]);
            int min = Integer.parseInt(split[1]);
            a[i] = new alarm();
            a[i].create_alarm(hour, min);
        }

    }

    public class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return schedule.size();
        }

        @Override
        public Object getItem(int position) {
            return schedule.get(position).getScheduleTime();
        }

        @Override
        public long getItemId(int position) {
            return schedule.get(position).hashCode();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.custom_list, container, false);
            }

            TextView tv = (TextView) convertView.findViewById(R.id.time);
            TextView tv1 = (TextView) convertView.findViewById(R.id.medName);
            TextView tv2= (TextView)convertView.findViewById(R.id.med_Dose);
            String medicine = "";
            String dose ="";
            tv.setText(schedule.get(position).getScheduleTime());
            for (String s : schedule.get(position).getMedName().split("_")) {
                medicine = medicine + s + "\n";
                dose = dose+ schedule.get(position).getDose()+"\n";
            }

            tv1.setText(medicine);
            tv2.setText(dose);
            return convertView;
        }
    }

    public class alarm {
        public void create_alarm(int hour, int minutes) {
            alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            Intent alarmIntent = new Intent(getApplicationContext(), MyReceiver_DailySchedule.class); // AlarmReceiver1 = broadcast receiver
            final int _id = (int) System.currentTimeMillis();
            pendingIntent = PendingIntent.getBroadcast(CreateScheduleActivity.this, _id, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmIntent.setData((Uri.parse("custom://" + System.currentTimeMillis())));
            alarmManager.cancel(pendingIntent);
            Calendar alarmStartTime = Calendar.getInstance();
            Calendar now = Calendar.getInstance();
            alarmStartTime.set(Calendar.HOUR_OF_DAY, hour);
            alarmStartTime.set(Calendar.MINUTE, minutes);
            alarmStartTime.set(Calendar.SECOND, 0);
            if (now.after(alarmStartTime)) {
                Log.d("Hey", "Added a day");
                alarmStartTime.add(Calendar.DATE, 1);
            }
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, alarmStartTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            Log.d("Alarm", "Alarms set for everyday" + Integer.toString(hour) + Integer.toString(minutes));

        }
    }

        private void addData() {
            db = new DbHelper(getApplicationContext());

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


            cv.put(COLUMN_MED_NAME, "warfarin");
            cv1.put(COLUMN_MED_NAME, "digoxin");
            cv2.put(COLUMN_MED_NAME, "coumadin");
            cv3.put(COLUMN_MED_NAME, "aspirin");
            cv4.put(COLUMN_MED_NAME, "questron");
            cv5.put(COLUMN_MED_NAME, "unithroid");
            cv6.put(COLUMN_MED_NAME, "methadone");
            cv7.put(COLUMN_MED_NAME, "dopamine");
            cv.put(COLUMN_MIN_DOSE, "1");
            cv1.put(COLUMN_MIN_DOSE, "1");
            cv2.put(COLUMN_MIN_DOSE, "1");
            cv3.put(COLUMN_MIN_DOSE, "1");
            cv4.put(COLUMN_MIN_DOSE, "1");
            cv5.put(COLUMN_MIN_DOSE, "1");
            cv6.put(COLUMN_MIN_DOSE, "1");
            cv7.put(COLUMN_MIN_DOSE, "1");
            cv.put(COLUMN_MAX_DOSE, "3");
            cv1.put(COLUMN_MAX_DOSE, "3");
            cv2.put(COLUMN_MAX_DOSE, "3");
            cv3.put(COLUMN_MAX_DOSE, "3");
            cv4.put(COLUMN_MAX_DOSE, "3");
            cv5.put(COLUMN_MAX_DOSE, "3");
            cv6.put(COLUMN_MAX_DOSE, "3");
            cv7.put(COLUMN_MAX_DOSE, "3");
            cv.put(COLUMN_SEPARATION, "60");
            cv1.put(COLUMN_SEPARATION, "60");
            cv2.put(COLUMN_SEPARATION, "60");
            cv3.put(COLUMN_SEPARATION, "60");
            cv4.put(COLUMN_SEPARATION, "60");
            cv5.put(COLUMN_SEPARATION, "60");
            cv6.put(COLUMN_SEPARATION, "60");
            cv7.put(COLUMN_SEPARATION, "60");
            //  cv.put(COLUMN_INTERFERENCE,"2");

            // CONTENT VALUES FOR MED_INTERFERER TABLE
            ContentValues cv8 = new ContentValues();
            ContentValues cv9 = new ContentValues();
            ContentValues cv10 = new ContentValues();
            ContentValues cv11 = new ContentValues();

            cv8.put(COLUMN_MED_ID, "1");
            cv8.put(COLUMN_INTERFERER_ID, "2");
            cv8.put(COLUMN_MIN_FROM, "30");
            cv8.put(COLUMN_MIN_TO, "30");

            cv9.put(COLUMN_MED_ID, "3");
            cv9.put(COLUMN_INTERFERER_ID, "4");
            cv9.put(COLUMN_MIN_FROM, "40");
            cv9.put(COLUMN_MIN_TO, "40");

            cv10.put(COLUMN_MED_ID, "5");
            cv10.put(COLUMN_INTERFERER_ID, "6");
            cv10.put(COLUMN_MIN_FROM, "50");
            cv10.put(COLUMN_MIN_TO, "50");

            cv11.put(COLUMN_MED_ID, "7");
            cv11.put(COLUMN_INTERFERER_ID, "8");
            cv11.put(COLUMN_MIN_FROM, "60");
            cv11.put(COLUMN_MIN_TO, "60");


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

                if (result != -1 && result1 != -1 && result2 != -1 && result3 != -1 && result4 != -1 && result5 != -1 && result6 != -1 && result7 != -1) {
                    if (result8 != -1 && result9 != -1 && result10 != -1 && result11 != -1) {
                        Toast.makeText(this, "Inserted successfully in both tables", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Inserted successfully in specifications tables", Toast.LENGTH_SHORT).show();
                    }
                } else
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            } catch (SQLiteException ex) {
                String s = ex.getMessage();
            } finally {
                db.close();
            }


        }


        private void getUserPreferences() {
            Log.d(TAG, "getUserPreferences method entered");
            db = new DbHelper(getApplicationContext());
            mDb = db.getWritableDatabase();
            Cursor cursor = null;

            try {
                cursor = getAllRows(DbContract.DbEntryPHR.TABLE_NAME, User.USERPREFCOLUMNNAMES);
                int indexWakeTime = cursor.getColumnIndex(DbContract.DbEntryPHR.COLUMN_WAKE_UP_TIME);
                int indexBreakfastTime = cursor.getColumnIndex(DbContract.DbEntryPHR.COLUMN_BREAKFAST_TIME);
                int indexGymTime = cursor.getColumnIndex(DbContract.DbEntryPHR.COLUMN_GYM_TIME);
                int indexLunchTime = cursor.getColumnIndex(DbContract.DbEntryPHR.COLUMN_LUNCH_TIME);
                int indexDinnerTime = cursor.getColumnIndex(DbContract.DbEntryPHR.COLUMN_DINNER_TIME);


                if (cursor.getCount() > 0) {
                    do {

                        String wakeupTime = cursor.getString(indexWakeTime);
                        String breakfastTime = cursor.getString(indexBreakfastTime);
                        String gymTime = cursor.getString(indexGymTime);
                        String lunchTime = cursor.getString(indexLunchTime);
                        String dinnerTime = cursor.getString(indexDinnerTime);


                        mUserPreferences.put(User.WAKE_UP_TIME, wakeupTime);
                        mUserPreferences.put(User.BREAKFAST_TIME, breakfastTime);
                        mUserPreferences.put(User.GYM_TIME, gymTime);
                        mUserPreferences.put(User.LUNCH_TIME, lunchTime);
                        mUserPreferences.put(User.DINNER_TIME, dinnerTime);

                    } while (cursor.moveToNext());
                }
            } catch (Exception ex) {
                Log.e(TAG, "Exception raised while reading User data" + ex);
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }

            db.close();


        }

        private ArrayList<Med_Specification> getAllMedSpecData() {
            Log.d(TAG, "getAllMedSpecData entered");
            ArrayList<Med_Specification> _medSpecs = new ArrayList<>();
            db = new DbHelper(getApplicationContext());
            mDb = db.getWritableDatabase();
            Cursor cursor = null;

            try {

                cursor = getAllRows(DbContract.DbEntryMed.TABLE_NAME, Med_Specification.ALL_COLUMNS_MEDSPEC);
                int indexMedId = cursor.getColumnIndex(DbContract.DbEntryMed.COLUMN_ID);
                int indexMedName = cursor.getColumnIndex(DbContract.DbEntryMed.COLUMN_MED_NAME);
                int indexMinDose = cursor.getColumnIndex(DbContract.DbEntryMed.COLUMN_MIN_DOSE);
                int indexMaxDose = cursor.getColumnIndex(DbContract.DbEntryMed.COLUMN_MAX_DOSE);
                int indexColSeperation = cursor.getColumnIndex(DbContract.DbEntryMed.COLUMN_SEPARATION);


                if (cursor.getCount() > 0) {
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

                    } while (cursor.moveToNext());
                }


            } catch (Exception ex) {
                Log.e(TAG, "Exception raised while reading Medicine specification data" + ex);
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }

            db.close();

            return _medSpecs;
        }

        private ArrayList<Interferer> getInterfererData(String medId) {
            Log.d(TAG, "getInterfererData method entered");
            ArrayList<Interferer> interfererArrayList = new ArrayList<>();
            String whereClause = DbContract.DbEntryInterferer.COLUMN_MED_ID + "=?";
            String[] whereArgs = {medId};
            db = new DbHelper(getApplicationContext());
            mDb = db.getWritableDatabase();
            Cursor cursor = null;

            try {
                cursor = mDb.query(DbContract.DbEntryInterferer.TABLE_NAME, Interferer.ALL_COLUMNS_Interferer, whereClause, whereArgs, null, null, null);
                int indexMedId = cursor.getColumnIndex(DbContract.DbEntryInterferer.COLUMN_MED_ID);
                int indexInterfererId = cursor.getColumnIndex(DbContract.DbEntryInterferer.COLUMN_INTERFERER_ID);
                int indexMinFrom = cursor.getColumnIndex(DbContract.DbEntryInterferer.COLUMN_MIN_FROM);
                int indexMinTo = cursor.getColumnIndex(DbContract.DbEntryInterferer.COLUMN_MIN_TO);

                if (cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {

                        Interferer intf = new Interferer();
                        intf.setMed_id(cursor.getString(indexMedId));
                        intf.setInterferer_id(cursor.getString(indexInterfererId));
                        intf.setMinFromInterferer(cursor.getString(indexMinFrom));
                        intf.setMinToInterferer(cursor.getString(indexMinTo));

                        interfererArrayList.add(intf);
                    }
                }

            } catch (Exception ex) {
                Log.d(TAG, "Exception reading interferer data \n" + ex);
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
            db.close();

            return interfererArrayList;
        }

        private ArrayList<Prescription> getAllPrescriptions() {
            Log.d(TAG, "getAllPrescriptions method entered :");

            ArrayList<Prescription> temmPres = new ArrayList<>();
            ArrayList<Medicine> tepMeds = new ArrayList<>();
            db = new DbHelper(getApplicationContext());
            mDb = db.getWritableDatabase();
            Cursor cursor = null;

            try {

                cursor = getAllRows(DbContract.DbEntryPrescription.TABLE_NAME, ALL_COLUMNS);
                int indexPresName = cursor.getColumnIndex(DbContract.DbEntryPrescription.COLUMN_PRESCRIPTION_NAME);
                int indexDisease = cursor.getColumnIndex(DbContract.DbEntryPrescription.COLUMN_DISEASE);

                if (cursor.getCount() > 0) {
                    do {
                        Prescription pres = new Prescription();
                        pres.setPrescriptionName(cursor.getString(indexPresName));
                        pres.setDisease(cursor.getString(indexDisease));


                        tepMeds = getMedicineData(pres.getPrescriptionName());
                        pres.setMedicineArrayList(tepMeds);

                        temmPres.add(pres);

                    } while (cursor.moveToNext());
                }

            } catch (Exception ex) {
                Log.e(TAG, "Exception raised while reading prescription data" + ex);
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }

            db.close();
            return temmPres;
        }

        public ArrayList<Medicine> getMedicineData(String prescriptionName) {
            //setContentView(R.layout.medicine_item_cv);

            Log.d("ViewMedicine", "Entered Get Medicine Data Method");
            String whereClause = DbContract.DbEntryPrescription.COLUMN_PRESCRIPTION_NAME + "=?";
            ArrayList<Medicine> viewMedicines = new ArrayList<>();
            String[] whereArgs = {prescriptionName};
            mContext = getApplicationContext();
            db = new DbHelper(getApplicationContext());
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
                Log.d("GetMedicineData", "MedicineName Index");

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

                        Log.d("GetMedicineData", " Inside Cursor Loop MedicineName Index: " + cursor.getString(indexMedName));
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

        public Cursor getAllRows(String tableName, String[] columns) {
            String where = null;
            db = new DbHelper(getApplicationContext());
            Log.d(TAG, " entered get all rows");
            mDb = db.getWritableDatabase();
            Cursor cursor = null;
            if (tableName.equals(DbContract.DbEntryPrescription.TABLE_NAME)) {
                cursor = mDb.query(true, tableName, columns, where, null, DbContract.DbEntryPrescription.COLUMN_PRESCRIPTION_NAME, null, null, null);
            } else {
                cursor = mDb.query(tableName, columns, where, null, null, null, null, null);
            }

            try {
                if (cursor != null) {
                    cursor.moveToFirst();
                    Log.d("GetAllRows", cursor.getString(1));
                }
            } catch (SQLiteException ex) {
                Log.e(TAG, "Exception reading user data");
            }
            db.close();
            return cursor;
        }

        private void createJobModel() {
            Log.d(TAG, "CreateJobModel method entered");
            String whereClause = DbContract.DbEntryMed.COLUMN_ID + "=?";

            Medicine currentMedicine = new Medicine();
            String[] whereArgs = {};

            db = new DbHelper(getApplicationContext());


        }
    }

