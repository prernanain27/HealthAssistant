package example.healthassistant.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import example.healthassistant.Activities.ViewMedicine;
import example.healthassistant.DbContract;
import example.healthassistant.DbHelper;
import example.healthassistant.Models.Medicine;
import example.healthassistant.Models.Prescription;
import example.healthassistant.R;

public class ViewPrescription extends AppCompatActivity {
    private SQLiteDatabase mDb;
    SQLiteOpenHelper db;
    ListView prescriptionListView;
    ArrayList<Prescription> viewPrescription = new ArrayList<Prescription>();
    private String[] medicineData = {
            "Medicine Name",
            "Medicine Type",
            "Medicine Dose",
            "Medicine Time"

    };
    public static final String[] PRESCRIPTION_COLUMNS = {


            DbContract.DbEntryPrescription.COLUMN_PRESCRIPTION_NAME,
            DbContract.DbEntryPrescription.COLUMN_DISEASE

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
        ViewMedicine viewMedicine = new ViewMedicine();
        prescriptionListView = (ListView) findViewById(R.id.prescriptionList);
        //populateListView();
         viewPrescription.addAll(populatePrescriptionData());
        prescriptionListView.setAdapter(new PrescriptionAdapter());
        prescriptionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent viewMedicine = new Intent(getApplicationContext(), ViewMedicine.class);
                String selectedFromList = prescriptionListView.getItemAtPosition(position).toString();
                Log.d("ViewPrescription" , "ListItem Child Count: " + prescriptionListView.getChildCount());
                Log.d("ViewPresciption", "Selected Item position: " + position + " Selected Item: " + selectedFromList);
                TextView textViewListItemDisease = (TextView) prescriptionListView.getChildAt(position).findViewById(R.id.list_item_disease_tv);
                Log.d("ViewPrescription" , "ListItem Child Element: " + textViewListItemDisease.getText());
                String selectedFromListDisease = textViewListItemDisease.getText().toString();
                viewMedicine.putExtra("prescriptionName", selectedFromList);
                viewMedicine.putExtra("diseaseName", selectedFromListDisease);
                startActivity(viewMedicine);
            }
        });
    }


    private ArrayList<Prescription> populatePrescriptionData() {
        ArrayList<Prescription> prescriptionList = new ArrayList<Prescription>();
        db =  new DbHelper(getApplicationContext());
        mDb = db.getWritableDatabase();
        Cursor cursor = null;
        try {

             cursor = mDb.query(true,DbContract.DbEntryPrescription.TABLE_NAME, PRESCRIPTION_COLUMNS, null, null, null, null, null, null);
            int indexDisease = cursor.getColumnIndex(DbContract.DbEntryPrescription.COLUMN_DISEASE);
            int indexPrescription = cursor.getColumnIndex(DbContract.DbEntryPrescription.COLUMN_PRESCRIPTION_NAME);
            Log.d("GetPrescriptionData","PrescriptionName Index " + indexPrescription);
            Log.d("GetPrescriptionData","Disease Index " + indexDisease);
            if (cursor.getCount() > 0) {
                int i = 0;
                while (cursor.moveToNext()) {
                    Prescription prescription = new Prescription();
                    prescription.setDisease(cursor.getString(indexDisease));
                    prescription.setPrescriptionName(cursor.getString(indexPrescription));
                    prescriptionList.add(prescription);
                    Log.d("populatePrescription","Disease Name: " + prescriptionList.get(i).getDisease());
                    Log.d("populatePrescription", "Prescription Name: " + cursor.getString(indexPrescription));
                    i++;
                }
            }
        } catch (Exception e) {
            Log.d("Populate List View", " exception details: " + e);
        } finally {

            db.close();

        }
        return prescriptionList;
    }

    private class PrescriptionAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return viewPrescription.size();
        }

        @Override
        public String getItem(int position) {
            return viewPrescription.get(position).getPrescriptionName();
        }

        @Override
        public long getItemId(int position) {
            return viewPrescription.get(position).hashCode();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.prescription_item, container, false);
            }
            CheckBox checkBoxPrescription = (CheckBox) convertView.findViewById(R.id.list_item_prescription_checkbox);
            TextView textViewPrescription = (TextView) convertView.findViewById(R.id.list_item_prescription_tv);
            TextView textViewDisease = (TextView) convertView.findViewById(R.id.list_item_disease_tv);
            checkBoxPrescription.setVisibility(View.INVISIBLE);
            textViewPrescription.setText(viewPrescription.get(position).getPrescriptionName());
            Log.d("viewPrescription", "PrescriptionName: " + viewPrescription.get(position).getPrescriptionName());
            textViewDisease.setText(viewPrescription.get(position).getDisease());
            Log.d("viewPrescription", "Disease: " + viewPrescription.get(position).getDisease());
            return convertView;
        }
    }
}