package example.healthassistant;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by prernaa on 3/29/2017.
 */

public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "healthdemo.db";
    private static final int DATABASE_VERSION = 1;
    public DbHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_TABLE= "CREATE TABLE "+
                DbContract.DbEntry.TABLE_NAME + "("+
                DbContract.DbEntry._ID +" INTEGER PRIMARY KEY AUTOINCREMENT , "+
                DbContract.DbEntry.COLUMN_EMAIL + " TEXT  , "+
                DbContract.DbEntry.COLUMN_PASSWORD + "TEXT  "+ ");";
        final String SQL_CREATE_PHR = "CREATE TABLE "+
                DbContract.DbEntryPHR.TABLE_NAME + "("+
                DbContract.DbEntryPHR._ID +" INTEGER PRIMARY KEY AUTOINCREMENT , "+
                DbContract.DbEntryPHR.COLUMN_NAME + " TEXT  , "+
                DbContract.DbEntryPHR.COLUMN_Email + " TEXT , "+
                DbContract.DbEntryPHR.COLUMN_SEX + " TEXT  , "+
                DbContract.DbEntryPHR.COLUMN_DOB + " TEXT  , "+
                DbContract.DbEntryPHR.COLUMN_ADDRESS + " TEXT , "+
                DbContract.DbEntryPHR.COLUMN_PRIMARY_CONTACT + " TEXT , "+
                DbContract.DbEntryPHR.COLUMN_EMERGENCT_CONTACT + " TEXT , "+
                DbContract.DbEntryPHR.COLUMN_CARETAKER_CONTACT + " TEXT , "+
                DbContract.DbEntryPHR.COLUMN_BLOODTYPE + " TEXT  , "+
                DbContract.DbEntryPHR.COLUMN_BLOOD_SIGN + " TEXT  , "+
                DbContract.DbEntryPHR.COLUMN_HEIGHT_FEET + " TEXT , "+
                DbContract.DbEntryPHR.COLUMN_HEIGHT_INCHES + " TEXT  , "+
                DbContract.DbEntryPHR.COLUMN_EYE_SIGN + " TEXT , "+
                DbContract.DbEntryPHR.COLUMN_EYE_SIGHT + " TEXT  , "+
                DbContract.DbEntryPHR.COLUMN_WAKE_UP_TIME + " TEXT  , "+
                DbContract.DbEntryPHR.COLUMN_BREAKFAST_TIME + " TEXT  , "+
                DbContract.DbEntryPHR.COLUMN_LUNCH_TIME + " TEXT  , "+
                DbContract.DbEntryPHR.COLUMN_GYM_TIME + " TEXT  , "+
                DbContract.DbEntryPHR.COLUMN_DINNER_TIME + " TEXT  , "+");";
                //DbContract.DbEntryPHR.COLUMN_SLEEP_TIME + "TEXT  "+

        final String SQL_CREATE_PRESCRIPTION= "CREATE TABLE "+
                DbContract.DbEntryPrescription.TABLE_NAME + "("+
                DbContract.DbEntryPrescription._ID +" INTEGER PRIMARY KEY AUTOINCREMENT , "+
                DbContract.DbEntryPrescription.COLUMN_PRESCRIPTION_NAME + " TEXT  , "+
                DbContract.DbEntryPrescription.COLUMN_DISEASE + " TEXT  , "+
                DbContract.DbEntryPrescription.COLUMN_MED_NAME + " TEXT  , "+
                DbContract.DbEntryPrescription.COLUMN_MED_DOSE + " TEXT  , "+
                DbContract.DbEntryPrescription.COLUMN_MED_TYPE + " TEXT  , "+
                DbContract.DbEntryPrescription.COLUMN_MED_TIME + " TEXT  , "+
                DbContract.DbEntryPrescription.COLUMN_DURATION + " TEXT  , "+
                DbContract.DbEntryPrescription.COLUMN_DURATION_TYPE + " TEXT  , "+
                DbContract.DbEntryPrescription.COLUMN_MED_TOTAL + "TEXT  "+ ");";

        db.execSQL(SQL_CREATE_TABLE);
//        db.execSQL(SQL_CREATE_PHR);
        db.execSQL(SQL_CREATE_PRESCRIPTION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ DbContract.DbEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ DbContract.DbEntryPHR.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ DbContract.DbEntryPrescription.TABLE_NAME);
        onCreate(db);
    }
}
