package example.healthassistant.Activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import example.healthassistant.DbContract;
import example.healthassistant.DbHelper;
import example.healthassistant.Fragments.MedicineFrag;
import example.healthassistant.Models.Medicine;
import example.healthassistant.Models.Prescription;
import example.healthassistant.R;

import static example.healthassistant.DbContract.DbEntryPrescription.COLUMN_DISEASE;
import static example.healthassistant.DbContract.DbEntryPrescription.COLUMN_DURATION;
import static example.healthassistant.DbContract.DbEntryPrescription.COLUMN_DURATION_TYPE;
import static example.healthassistant.DbContract.DbEntryPrescription.COLUMN_MED_DOSE;
import static example.healthassistant.DbContract.DbEntryPrescription.COLUMN_MED_NAME;
import static example.healthassistant.DbContract.DbEntryPrescription.COLUMN_MED_TIME_AB;
import static example.healthassistant.DbContract.DbEntryPrescription.COLUMN_MED_TIME_AD;
import static example.healthassistant.DbContract.DbEntryPrescription.COLUMN_MED_TIME_AL;
import static example.healthassistant.DbContract.DbEntryPrescription.COLUMN_MED_TIME_BB;
import static example.healthassistant.DbContract.DbEntryPrescription.COLUMN_MED_TIME_BD;
import static example.healthassistant.DbContract.DbEntryPrescription.COLUMN_MED_TIME_BL;
import static example.healthassistant.DbContract.DbEntryPrescription.COLUMN_MED_TYPE;
import static example.healthassistant.DbContract.DbEntryPrescription.COLUMN_PRESCRIPTION_NAME;

public class AddMedicine extends AppCompatActivity {
    private static ArrayList<Medicine> medArray = new ArrayList<Medicine>();
    private SQLiteDatabase mDb;
    Medicine medicine = new Medicine();
    Prescription pres = new Prescription();
    String presName;
    String disease;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final MedicineFrag addMedFrag = new MedicineFrag();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.addMedFrame, addMedFrag);
        fragmentTransaction.commit();
        Bundle prescription = getIntent().getExtras();
        presName = prescription.getString("presName");
        disease = prescription.getString("disease");
        Button addMed = (Button) findViewById(R.id.addMed_frag);
        addMed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if(Prescription.getMedicineArrayList().isEmpty()){
//                    Prescription.setMedicineArrayList(medArray);
//                }
                //Medicine medicine;
                Log.d("medFrag.med", medicine.getMedName());

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                MedicineFrag medFrag = new MedicineFrag();
                fragmentTransaction.replace(R.id.addMedFrame, medFrag);
                fragmentTransaction.commit();
            }
        });

        Button savePrescription = (Button) findViewById(R.id.addMedicine);
        savePrescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pres.setPrescriptionName(presName);
                pres.setDisease(disease);

                if (presName.trim().matches("")) {
                    Toast.makeText(getApplicationContext(), "Error: Prescription Name not passed", Toast.LENGTH_SHORT).show();
                }
                else if (disease.trim().matches("")) {
                    Toast.makeText(getApplicationContext(), "Error: Disease Name not passed", Toast.LENGTH_SHORT).show();
                }
                else {
                    addData();
                    Intent intent = new Intent(getApplicationContext(), ViewMedicine.class);
                    intent.putExtra("prescriptionName", presName);
                    intent.putExtra("diseaseName", disease);
                    startActivity(intent);
                }
            }
        });
    }

    public void addData(){
        SQLiteOpenHelper db = new DbHelper(getApplicationContext());
        mDb = db.getWritableDatabase();
        ContentValues cv = new ContentValues();

        String BB= "0",AB="0",BL ="0",AL= "0",BD="0",AD ="0";
        for (Medicine temp : Prescription.getMedStaticArrayList())
        {
            cv.put(COLUMN_PRESCRIPTION_NAME, pres.getPrescriptionName());
            cv.put(COLUMN_DISEASE, pres.getDisease());
            cv.put(COLUMN_MED_NAME, temp.getMedName());
            cv.put(COLUMN_MED_DOSE, temp.getMedDose());
            cv.put(COLUMN_MED_TYPE, temp.getMedType());

            for (String time:temp.getMedTime()
                    ) {
                switch (time)
                {
                    case "Before Breakfast": {
                        BB="1";
                        break;
                    }
                    case "After Breakfast": {
                        AB = "1";
                        break;
                    }
                    case "Before Lunch": {
                        BL = "1";
                        break;
                    }
                    case"After Lunch": {
                        AL ="1";
                        break;
                    }
                    case"Before Dinner": {
                        BD ="1";
                        break;
                    }
                    case "After Dinner": {
                        AD="1";
                        break;
                    }


                }
            }

            cv.put(COLUMN_MED_TIME_BB,BB);
            cv.put(COLUMN_MED_TIME_AB,AB);
            cv.put(COLUMN_MED_TIME_BL,BL);
            cv.put(COLUMN_MED_TIME_AL,AL);
            cv.put(COLUMN_MED_TIME_BD,BD);
            cv.put(COLUMN_MED_TIME_AD, AD);
            cv.put(COLUMN_DURATION, temp.getMedDuration());
            cv.put(COLUMN_DURATION_TYPE, temp.getDurationType());

            Log.d("AddData:MedName",temp.getMedName());
            Log.d("AddData:MedObject: ", temp.toString());

            try {

                long result = mDb.insert(DbContract.DbEntryPrescription.TABLE_NAME, null, cv);
                if (result != -1) {
                    Toast.makeText(this, "Inserted successfully", Toast.LENGTH_SHORT).show();


                } else
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            } catch (SQLiteException ex) {
                String s = ex.getMessage();
            }
        }
        db.close();

    }
    }


