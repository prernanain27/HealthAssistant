package example.healthassistant;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by prernaa on 3/29/2017.
 */

public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "healthdemo.db";
    private static final int DATABASE_VERSION = 4;
    public DbHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("DbHelper OnCreate: " , "entered create table");
        final String SQL_CREATE_TABLE = "CREATE TABLE "+
                DbContract.DbEntry.TABLE_NAME + "("+
                DbContract.DbEntry._ID +" INTEGER PRIMARY KEY AUTOINCREMENT , "+
                DbContract.DbEntry.COLUMN_EMAIL +   " TEXT  , "+
                DbContract.DbEntry.COLUMN_PASSWORD + " TEXT  "+");";

        final String SQL_CREATE_MED_SCHEDULE = "CREATE TABLE "+
                DbContract.DbEntryMed_Schedule.TABLE_NAME + "(" +
                DbContract.DbEntryMed_Schedule.COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT , "+
                DbContract.DbEntryMed_Schedule.COLUMN_TIME + " TEXT , " +
                DbContract.DbEntryMed_Schedule.COLUMN_MED_NAME + " TEXT , " +
                DbContract.DbEntryMed_Schedule.COLUMN_MED_DURATION + " TEXT , " +
                DbContract.DbEntryMed_Schedule.COLUMN_MED_DOSE + " TEXT , " +
                DbContract.DbEntryMed_Schedule.COLUMN_DAYS_LAPSED + " TEXT " + ");";


        final String SQL_CREATE_USER_DATA_ANALYSIS = "CREATE TABLE "+
                DbContract.DbEntryUser_DataAnalysis.TABLE_NAME + "(" +
                DbContract.DbEntryUser_DataAnalysis.COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT , "+
                DbContract.DbEntryUser_DataAnalysis.COLUMN_DOSE_DATE + " TEXT , " +
                DbContract.DbEntryUser_DataAnalysis.COLUMN_DOSE_TIME + " TEXT , " +
                DbContract.DbEntryUser_DataAnalysis.COLUMN_MED_LIST + " TEXT , " +
                DbContract.DbEntryUser_DataAnalysis.COLUMN_MED_IS_TAKEN + " TEXT " + ");";

        final String SQL_CREATE_MED_SPEC = "CREATE TABLE "+
                DbContract.DbEntryMed.TABLE_NAME + "(" +
                DbContract.DbEntryMed.COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT , "+
                DbContract.DbEntryMed.COLUMN_MED_NAME + " TEXT , " +
                DbContract.DbEntryMed.COLUMN_MIN_DOSE + " TEXT , " +
                DbContract.DbEntryMed.COLUMN_MAX_DOSE + " TEXT , " +
                DbContract.DbEntryMed.COLUMN_SEPARATION + " TEXT " + ");";
            //    DbContract.DbEntryMed.COLUMN_INTERFERENCE + "TEXT , " + ")";


        final String SQL_CREATE_MED_INTERFERER = "CREATE TABLE "+
                DbContract.DbEntryInterferer.TABLE_NAME + "(" +
                DbContract.DbEntryInterferer.COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT , "+
                DbContract.DbEntryInterferer.COLUMN_MED_ID + " TEXT , " +
                DbContract.DbEntryInterferer.COLUMN_INTERFERER_ID + " TEXT , " +
                DbContract.DbEntryInterferer.COLUMN_MIN_FROM + " TEXT , " +
                DbContract.DbEntryInterferer.COLUMN_MIN_TO + " TEXT " + ");";

        final String SQL_CREATE_DOC_APPOINTMENT = "CREATE TABLE "+
                DbContract.DbEntryAppointment.TABLE_NAME + "(" +
                DbContract.DbEntryAppointment.COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT , "+
                DbContract.DbEntryAppointment.COLUMN_DOC_NAME + " TEXT , " +
                DbContract.DbEntryAppointment.COLUMN_DOC_CONTACT + " TEXT , " +
                DbContract.DbEntryAppointment.COLUMN_APPOINT_DATE + " TEXT , " +
                DbContract.DbEntryAppointment.COLUMN_APPOINT_TIME + " TEXT , " +
                DbContract.DbEntryAppointment.COLUMN_APPOINT_DESCRIPTION + " TEXT " + ");";


        final String SQL_CREATE_PHR = "CREATE TABLE "+
                DbContract.DbEntryPHR.TABLE_NAME + "("+
                DbContract.DbEntryPHR.COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT , "+
                DbContract.DbEntryPHR.COLUMN_NAME +         " TEXT  , "+
                DbContract.DbEntryPHR.COLUMN_Email +        " TEXT  , "+
                DbContract.DbEntryPHR.COLUMN_SEX +          " TEXT  , "+
                DbContract.DbEntryPHR.COLUMN_DOB +          " TEXT  , "+
                DbContract.DbEntryPHR.COLUMN_ADDRESS +      " TEXT  , "+
                DbContract.DbEntryPHR.COLUMN_PRIMARY_CONTACT +  " TEXT , "+
                DbContract.DbEntryPHR.COLUMN_EMERGENCT_CONTACT +" TEXT , "+
                DbContract.DbEntryPHR.COLUMN_CARETAKER_CONTACT +" TEXT , "+
                DbContract.DbEntryPHR.COLUMN_BLOODTYPE +    " TEXT  , "+
                DbContract.DbEntryPHR.COLUMN_BLOOD_SIGN +   " TEXT  , "+
                DbContract.DbEntryPHR.COLUMN_HEIGHT_FEET +  " TEXT  , "+
                DbContract.DbEntryPHR.COLUMN_HEIGHT_INCHES +" TEXT  , "+
                DbContract.DbEntryPHR.COLUMN_EYE_SIGN +     " TEXT  , "+
                DbContract.DbEntryPHR.COLUMN_EYE_SIGHT +    " TEXT  , "+
                DbContract.DbEntryPHR.COLUMN_WAKE_UP_TIME + " TEXT  , "+
                DbContract.DbEntryPHR.COLUMN_BREAKFAST_TIME+" TEXT  , "+
                DbContract.DbEntryPHR.COLUMN_LUNCH_TIME +   " TEXT  , "+
                DbContract.DbEntryPHR.COLUMN_GYM_TIME +     " TEXT  , "+
                DbContract.DbEntryPHR.COLUMN_DINNER_TIME +  " TEXT  "+");";
                //DbContract.DbEntryPHR.COLUMN_SLEEP_TIME + "TEXT  "+
            Log.d("DBHelper:OnCreate: ","Create Table AddPHRActivity Command: " + SQL_CREATE_PHR);

        final String SQL_CREATE_PRESCRIPTION= "CREATE TABLE "+
                DbContract.DbEntryPrescription.TABLE_NAME + "("+
                DbContract.DbEntryPrescription.COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT , "+
                DbContract.DbEntryPrescription.COLUMN_PRESCRIPTION_NAME + " TEXT  , "+
                DbContract.DbEntryPrescription.COLUMN_DISEASE + " TEXT  , "+
                DbContract.DbEntryPrescription.COLUMN_MED_NAME + " TEXT  , "+
                DbContract.DbEntryPrescription.COLUMN_MED_DOSE + " TEXT  , "+
                DbContract.DbEntryPrescription.COLUMN_MED_TYPE + " TEXT  , "+
                DbContract.DbEntryPrescription.COLUMN_MED_TIME_BB + " TEXT  , "+
                DbContract.DbEntryPrescription.COLUMN_MED_TIME_AB + " TEXT  , "+
                DbContract.DbEntryPrescription.COLUMN_MED_TIME_BL + " TEXT  , "+
                DbContract.DbEntryPrescription.COLUMN_MED_TIME_AL + " TEXT  , "+
                DbContract.DbEntryPrescription.COLUMN_MED_TIME_BD + " TEXT  , "+
                DbContract.DbEntryPrescription.COLUMN_MED_TIME_AD + " TEXT  , "+
                DbContract.DbEntryPrescription.COLUMN_DURATION + " TEXT  , "+
                DbContract.DbEntryPrescription.COLUMN_DURATION_TYPE + " TEXT "+");";
//                DbContract.DbEntryPrescription.COLUMN_MED_TOTAL + "TEXT  "+ ");";

        db.execSQL(SQL_CREATE_TABLE);


        //kept try catch to see if the table is being created or not
        try {
            db.execSQL(SQL_CREATE_PHR);
            db.execSQL(SQL_CREATE_PRESCRIPTION);
            db.execSQL(SQL_CREATE_MED_SPEC);
            db.execSQL(SQL_CREATE_MED_INTERFERER);
            db.execSQL(SQL_CREATE_DOC_APPOINTMENT);
            db.execSQL(SQL_CREATE_MED_SCHEDULE);
            db.execSQL(SQL_CREATE_USER_DATA_ANALYSIS);

            Log.d("Create Table"," Prescription Successful");
        }
        catch (Exception e){
            Log.d("There is issue"," in creating table");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ DbContract.DbEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ DbContract.DbEntryPHR.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ DbContract.DbEntryPrescription.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ DbContract.DbEntryMed.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ DbContract.DbEntryInterferer.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ DbContract.DbEntryAppointment.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ DbContract.DbEntryMed_Schedule.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ DbContract.DbEntryUser_DataAnalysis.TABLE_NAME);
        onCreate(db);
    }
}
