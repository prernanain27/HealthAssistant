package example.healthassistant;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import static example.healthassistant.DbContract.DbEntryPrescription.COLUMN_PRESCRIPTION_NAME;
import static example.healthassistant.DbContract.DbEntryPrescription.COLUMN_DISEASE;
import static example.healthassistant.DbContract.DbEntryPrescription.COLUMN_MED_NAME;
import static example.healthassistant.DbContract.DbEntryPrescription.COLUMN_MED_DOSE;
import static example.healthassistant.DbContract.DbEntryPrescription.COLUMN_MED_TYPE;
import static example.healthassistant.DbContract.DbEntryPrescription.COLUMN_MED_TIME;
import static example.healthassistant.DbContract.DbEntryPrescription.COLUMN_DURATION;
import static example.healthassistant.DbContract.DbEntryPrescription.COLUMN_DURATION_TYPE;
import static example.healthassistant.DbContract.DbEntryPrescription.COLUMN_MED_TOTAL;
import static example.healthassistant.Prescription.*;


public class AddPrescription_AP extends AppCompatActivity {

    private static ArrayList<Medicine> medArray = new ArrayList<Medicine>();
    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_prescription__ap);
        final MedicineFrag medFrag = new MedicineFrag();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        final EditText presName = (EditText) findViewById(R.id.prescriptionName);
        final EditText disease = (EditText) findViewById(R.id.disease);

        Button addMed = (Button) findViewById(R.id.addMed);
//        if (Prescription.getMedicineArrayList().isEmpty()) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.medFrame, medFrag);
        fragmentTransaction.commit();
//    }
        addMed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if(Prescription.getMedicineArrayList().isEmpty()){
//                    Prescription.setMedicineArrayList(medArray);
//                }
                Medicine medicine;
                Log.d("medFrag.med", Medicine.getMedName());

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                MedicineFrag medFrag = new MedicineFrag();
                fragmentTransaction.replace(R.id.medFrame, medFrag);
                fragmentTransaction.commit();
            }
        });

        Button savePrescription = (Button) findViewById(R.id.storePrescription);
        savePrescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Prescription.setPrescriptionName(presName.getText().toString());
                Prescription.setDisease(disease.getText().toString());
//                Prescription.setMedicineArrayList(medArray);
                addData();
            }
        });
    }

    public void addData(){
        SQLiteOpenHelper db = new DbHelper(getApplicationContext());
        mDb = db.getWritableDatabase();
        ContentValues cv = new ContentValues();

        for(int i=0;i< Prescription.medicineArrayList.size();i++) {
            cv.put(COLUMN_PRESCRIPTION_NAME, Prescription.getPrescriptionName());
            cv.put(COLUMN_DISEASE, Prescription.getDisease());
            cv.put(COLUMN_MED_NAME, Prescription.medicineArrayList.get(i).getMedName());
            cv.put(COLUMN_MED_DOSE, Prescription.medicineArrayList.get(i).getMedDose());
            cv.put(COLUMN_MED_TYPE, Prescription.medicineArrayList.get(i).getMedType());
            cv.put(COLUMN_MED_TIME, Prescription.medicineArrayList.get(i).getMedTime());
            cv.put(COLUMN_DURATION, Prescription.medicineArrayList.get(i).getMedDuration());
            cv.put(COLUMN_DURATION_TYPE, Prescription.medicineArrayList.get(i).getDurationType());
            cv.put(COLUMN_MED_TOTAL, Prescription.medicineArrayList.get(i).getMedTotal());

            Log.d("This is row ", " " +i);
            Log.d("Presc Name",Prescription.getPrescriptionName());
            Log.d("Disease",Prescription.getDisease());
            Log.d("Med Name",Prescription.medicineArrayList.get(i).getMedName());
            Log.d("Dose",Prescription.medicineArrayList.get(i).getMedDose());
            Log.d("Type",Prescription.medicineArrayList.get(i).getMedType());
            Log.d("Time",Prescription.medicineArrayList.get(i).getMedTime());
            Log.d("Total",Prescription.medicineArrayList.get(i).getMedTotal());
            Log.d("Duration",Prescription.medicineArrayList.get(i).getMedDuration());
            Log.d("Duration Type",Prescription.medicineArrayList.get(i).getDurationType());

            try {

                long result = mDb.insert(DbContract.DbEntryPrescription.TABLE_NAME, null, cv);
                if (result != -1) {
                    Toast.makeText(this, "Inserted successfully", Toast.LENGTH_SHORT).show();
                    db.close();
                    Intent intent = new Intent(getApplicationContext(), HomeScreen.class);
                    startActivity(intent);
                } else
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            } catch (SQLiteException ex) {
                String s = ex.getMessage();
            }
        }

    }


}
