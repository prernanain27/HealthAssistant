package example.healthassistant.Activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
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
import java.util.List;

import example.healthassistant.DbContract;
import example.healthassistant.DbHelper;
import example.healthassistant.Fragments.MedicineFrag;
import example.healthassistant.Models.Interferer;
import example.healthassistant.Models.Med_Specification;
import example.healthassistant.Models.Medicine;
import example.healthassistant.Models.Prescription;
import example.healthassistant.R;

import static example.healthassistant.Activities.ViewPrescription.ALL_COLUMNS;
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
    private String TAG ="Add Medicine Activity";

    private static ArrayList<Medicine> medArray = new ArrayList<Medicine>();
    private DbHelper db;
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

                    if(checkForMedDose()) {
                        addData();
                        Intent intent = new Intent(getApplicationContext(), ViewMedicine.class);
                        intent.putExtra("prescriptionName", presName);
                        intent.putExtra("diseaseName", disease);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Error: Medicine Dose exceeds the maximum permitted dose", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }


    private Boolean checkForMedDose()
    {
        Med_Specification spec = new Med_Specification();

        for (Medicine med: Prescription.getMedStaticArrayList()
             ) {

                 spec = getSingleMedSpecData(med.getMedName());

                if(spec!=null)
                {
                    if (Integer.parseInt(med.getMedDose()) <= Integer.parseInt(spec.getMax_dose())
                            ) {

                        return true;
                    }
                }


        }
        return false;
    }

    private Med_Specification getSingleMedSpecData(String medName)
    {
        ArrayList<Med_Specification> tempSpecData = getAllMedSpecData();

        for (Med_Specification med:tempSpecData
             ) {

            if(med.getMed_name().equals(medName))
            {
                return med;
            }
        }

        return null;
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
    public ArrayList<Medicine> getMedicineData(String prescriptionName) {
        //setContentView(R.layout.medicine_item_cv);

        Log.d("ViewMedicine", "Entered Get Medicine Data Method");
        String whereClause = DbContract.DbEntryPrescription.COLUMN_PRESCRIPTION_NAME + "=?";
        ArrayList<Medicine> viewMedicines = new ArrayList<>();
        String[] whereArgs = {prescriptionName};

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


