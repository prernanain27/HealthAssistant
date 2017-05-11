package example.healthassistant.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import example.healthassistant.DbContract;
import example.healthassistant.DbHelper;
import example.healthassistant.Models.Med_Specification;
import example.healthassistant.Models.Prescription;
import example.healthassistant.R;
import example.healthassistant.SchedularClasses.CreateSchedule;
import example.healthassistant.SchedularClasses.MedScheduleItem;
import example.healthassistant.SchedularClasses.ResourceModel;

public class ViewPrescription extends AppCompatActivity {
    private SQLiteDatabase mDb;
    SQLiteOpenHelper db;
    ListView prescriptionListView;
    ImageButton buttonDeletePres;
    Button createSchedule;
    FloatingActionButton floatingActionButtonEdit;
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
        buttonDeletePres = (ImageButton) findViewById(R.id.list_item_prescription_button);
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

        prescriptionListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                final String  selectedFromList = prescriptionListView.getItemAtPosition(position).toString();
                Log.d("ViewPrescription" , "ListItem Child Count: " + prescriptionListView.getChildCount());
                Log.d("ViewPresciption", "Selected Item position: " + position + " Selected Item: " + selectedFromList);
                TextView textViewListItemDisease = (TextView) prescriptionListView.getChildAt(position).findViewById(R.id.list_item_disease_tv);
                Log.d("ViewPrescription" , "ListItem Child Element: " + textViewListItemDisease.getText());
                final String selectedFromListDisease = textViewListItemDisease.getText().toString();
                    buttonDeletePres = (ImageButton) prescriptionListView.getChildAt(position).findViewById(R.id.list_item_prescription_button);
                    buttonDeletePres.setVisibility(View.VISIBLE);
                    buttonDeletePres.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            deletePrescription(selectedFromList, selectedFromListDisease);
                            //prescriptionListView = (ListView) findViewById(R.id.prescriptionList);
                            for(Prescription temp:viewPrescription){
                                if(temp.getDisease() == selectedFromListDisease && temp.getPrescriptionName() ==selectedFromList ){
                                    viewPrescription.remove(temp);
                                }
                            }

                            //viewPrescription.retainAll(populatePrescriptionData());
                            prescriptionListView.setAdapter(new PrescriptionAdapter());
                            prescriptionListView.invalidateViews();
                        }
                    });
                return true;
            }
        });

        createSchedule = (Button)findViewById(R.id.createScheduleButton);
        createSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CreateSchedule createSchedule = new CreateSchedule(getApplicationContext());
            }
        });



    }

    private void deletePrescription(String prescriptionName, String diseaseName) {
        String whereClause = DbContract.DbEntryPrescription.COLUMN_PRESCRIPTION_NAME + "=?  and " + DbContract.DbEntryPrescription.COLUMN_DISEASE + "=?";
        String[] whereArgs = {prescriptionName, diseaseName};
        db = new DbHelper(getApplicationContext());
        mDb = db.getWritableDatabase();
        Cursor cursor = null;

        try {
            mDb.delete(DbContract.DbEntryPrescription.TABLE_NAME, whereClause, whereArgs);
        } catch (Exception e) {
            Log.d("ViewMedicine", "Get Medicine Data method: Exception Raised with a value of " + e);
        }finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        db.close();
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
            buttonDeletePres = (ImageButton) convertView.findViewById(R.id.list_item_prescription_button);
            TextView textViewPrescription = (TextView) convertView.findViewById(R.id.list_item_prescription_tv);
            TextView textViewDisease = (TextView) convertView.findViewById(R.id.list_item_disease_tv);
            buttonDeletePres.setVisibility(View.INVISIBLE);
            textViewPrescription.setText(viewPrescription.get(position).getPrescriptionName());
            Log.d("viewPrescription", "PrescriptionName: " + viewPrescription.get(position).getPrescriptionName());
            textViewDisease.setText(viewPrescription.get(position).getDisease());
            Log.d("viewPrescription", "Disease: " + viewPrescription.get(position).getDisease());

            return convertView;
        }
    }
}