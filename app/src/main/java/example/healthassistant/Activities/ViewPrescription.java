package example.healthassistant.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import example.healthassistant.Activities.ViewMedicine;
import example.healthassistant.DbContract;
import example.healthassistant.DbHelper;
import example.healthassistant.R;

public class ViewPrescription extends AppCompatActivity {
    private SQLiteDatabase mDb;
    SQLiteOpenHelper db;
    ListView prescriptionListView;
    private String[] medicineData = {
        "Medicine Name",
            "Medicine Type",
            "Medicine Dose",
            "Medicine Time"

    };

    public static final String[] ALL_COLUMNS = {

            DbContract.DbEntryPrescription.COLUMN_ID,
            DbContract.DbEntryPrescription.COLUMN_PRESCRIPTION_NAME,
            DbContract.DbEntryPrescription.COLUMN_DISEASE,
            DbContract.DbEntryPrescription.COLUMN_MED_NAME,
            DbContract.DbEntryPrescription.COLUMN_MED_DOSE,
            DbContract.DbEntryPrescription.COLUMN_MED_TYPE,
            DbContract.DbEntryPrescription.COLUMN_MED_TIME_BB,
            DbContract.DbEntryPrescription.COLUMN_MED_TIME_AB,
            DbContract.DbEntryPrescription.COLUMN_MED_TIME_BL,
            DbContract.DbEntryPrescription.COLUMN_MED_TIME_AL,
            DbContract.DbEntryPrescription.COLUMN_MED_TIME_BD,
            DbContract.DbEntryPrescription.COLUMN_MED_TIME_AD,
            DbContract.DbEntryPrescription.COLUMN_DURATION,
            DbContract.DbEntryPrescription.COLUMN_DURATION_TYPE

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_prescription);

        prescriptionListView = (ListView) findViewById(R.id.prescriptionList);
        populateListView();
        prescriptionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent viewMedicine = new Intent(getApplicationContext(), ViewMedicine.class);
                Cursor c = ((SimpleCursorAdapter)parent.getAdapter()).getCursor();
                c.moveToPosition(position);
                String selectedFromList = c.getString(1);
                Log.d("ViewPresciption", "Selected Item position " + position + "Selected Item: " + selectedFromList);
                //medicineData = getMedicineData(selectedFromList);
               // viewMedicine.putExtra("medicineData", medicineData);
                //Log.d("ViewPrescription", "Selected Prescription: " + medicineData[1] + "\n" + medicineData[2]);
                viewMedicine.putExtra("prescriptionName", selectedFromList);
                startActivity(viewMedicine);
            }
        });
    }

    public Cursor getAllRows(){
        String where = null;
        db =  new DbHelper(getApplicationContext());
        Log.d("ViewPrescription"," entered get all rows");
        mDb = db.getWritableDatabase();
        Cursor cursor = mDb.query(true, DbContract.DbEntryPrescription.TABLE_NAME, ALL_COLUMNS, where, null, DbContract.DbEntryPrescription.COLUMN_PRESCRIPTION_NAME,null,null,null);
        if(cursor != null){
            cursor.moveToFirst();
            Log.d("GetAllRows", cursor.getString(1));
        }
        return  cursor;
    }



    private void populateListView() {
        try {
                Cursor cursor = getAllRows();
                Log.d("PopulateListView", cursor.getString(1));
                String[] fromFieldNames = new String[]{DbContract.DbEntryPrescription.COLUMN_PRESCRIPTION_NAME};
                Log.d("PopulateListView",fromFieldNames[0]);
                SimpleCursorAdapter myCursorAdaptor;
                int[] toViewIDs = new int[]{android.R.id.text1};
                myCursorAdaptor = new SimpleCursorAdapter(getBaseContext(), R.layout.activity_view_prescription, cursor, fromFieldNames, toViewIDs, 0);
                prescriptionListView.setAdapter(myCursorAdaptor);
//            }
        } catch (Exception e) {
            Log.d("Populate List View","cursor exception details: " + e);
        }
        finally {

                db.close();

        }
    }
}
