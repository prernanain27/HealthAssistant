package example.healthassistant;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Dialog_daily_medicine extends Activity {
    String text = "";
    SQLiteDatabase mDb;
    DbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_daily_medicine);

        Intent i = getIntent();
        text += i.getStringExtra("data");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(text).setCancelable(
                false).setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        addData("1");
                        dialog.cancel();
                    }
                }).setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        addData("0");
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
        Log.d("Dialog","Dialog box created");

    }
    public void addData(String isTaken){
        db = new DbHelper(getApplicationContext());
        mDb = db.getWritableDatabase();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c.getTime());
        String hour =Integer.toString(c.get(Calendar.HOUR_OF_DAY));
        String minute = Integer.toString(c.get(Calendar.MINUTE));
        String finalTime = hour +":" + minute;
        ContentValues cv = new ContentValues();
        cv.put(DbContract.DbEntryUser_DataAnalysis.COLUMN_DOSE_DATE,formattedDate);
        cv.put(DbContract.DbEntryUser_DataAnalysis.COLUMN_DOSE_TIME,finalTime);
        cv.put(DbContract.DbEntryUser_DataAnalysis.COLUMN_MED_LIST,text);
        cv.put(DbContract.DbEntryUser_DataAnalysis.COLUMN_MED_IS_TAKEN,isTaken);
        try{
            long result = mDb.insert(DbContract.DbEntryUser_DataAnalysis.TABLE_NAME, null, cv);

            if (result != -1)
                Toast.makeText(this, "Inserted successfully", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
        catch (SQLiteException ex){
            String s = ex.getMessage();
        }

    }

}
