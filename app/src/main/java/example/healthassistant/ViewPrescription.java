package example.healthassistant;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.Layout;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;

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
            DbContract.DbEntryPrescription.COLUMN_MED_TIME,
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

   /* public String[] getMedicineData(String prescriptionName) {
        Log.d("ViewPrescription","Entered Get Medicine Data Method");
        String whereClause = DbContract.DbEntryPrescription.COLUMN_PRESCRIPTION_NAME + "=?";
        String[] whereArgs = {prescriptionName};
        mContext = getApplicationContext();
        mDb = db.getWritableDatabase();
        Cursor cursor = null;
        int i = 0;
        try {
            cursor = mDb.query(DbContract.DbEntryPrescription.TABLE_NAME, ALL_COLUMNS, whereClause, whereArgs, null, null, null);
            int indexMedName = cursor.getColumnIndex(DbContract.DbEntryPrescription.COLUMN_MED_NAME);
            int indexMedTime = cursor.getColumnIndex(DbContract.DbEntryPrescription.COLUMN_MED_TIME);
            int indexMedDose = cursor.getColumnIndex(DbContract.DbEntryPrescription.COLUMN_MED_DOSE);
            int indexMedType = cursor.getColumnIndex(DbContract.DbEntryPrescription.COLUMN_MED_TYPE);
            Log.d("GetMedicineData","MedicineName Index");
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    CardView card = new CardView(mContext);

                    // Set the CardView layoutParams
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.WRAP_CONTENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT
                    );
                    card.setLayoutParams(params);

                    // Set CardView corner radius
                    card.setRadius(9);

                    // Set cardView content padding
                    card.setContentPadding(15, 15, 15, 15);

                    // Set a background color for CardView
                    card.setCardBackgroundColor(Color.parseColor("#FFC6D6C3"));

                    // Set the CardView maximum elevation
                    card.setMaxCardElevation(15);

                    // Set CardView elevation
                    card.setCardElevation(9);

                    // Initialize a new TextView to put in CardView
                    TextView tv = new TextView(mContext);
                    tv.setLayoutParams(params);
                    tv.setText(cursor.getString(indexMedName) + "\n" + cursor.getString(indexMedTime) + "\n" +
                            cursor.getString(indexMedDose) + "\n" + cursor.getString(indexMedType));
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);
                    tv.setTextColor(Color.RED);

                    // Put the TextView in CardView
                    card.addView(tv);

                    // Finally, add the CardView in root layout
                    mRelativeLayout.addView(card);
                    Log.d("GetMedicineData"," Inside Cursor Loop MedicineName Index: ");
                    //medicineData[i] = cursor.getString(indexMedName);
                   // medicineData[i+1] = cursor.getString(indexMedTime);
                    //medicineData[i+2] = cursor.getString(indexMedDose);
                    //medicineData[i+3] = cursor.getString(indexMedType);
                   // Log.d("GetMedicineData", "Get Medicine Data Method: " + medicineData[0] + "\n "+ medicineData[2]);
                }
            }
        } catch (Exception e) {
            Log.d("ViewPrescription", "Get Medicine Data method: Exception Raised with a value of " + e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        db.close();
        return medicineData;
    } */

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
