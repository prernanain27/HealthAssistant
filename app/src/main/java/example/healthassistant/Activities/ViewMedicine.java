package example.healthassistant.Activities;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
//import android.support.v7.widget.Card;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import example.healthassistant.DbContract;
import example.healthassistant.DbHelper;
import example.healthassistant.Models.Medicine;
import example.healthassistant.R;

public class ViewMedicine extends Activity {

    private Context mContext;
    private RelativeLayout mRelativeLayout;
    ListView medicineListView;
    TextView diseaseTextView;
    private SQLiteDatabase mDb;
    SQLiteOpenHelper db;
    List<Medicine> viewMedicines = new ArrayList<Medicine>();

    ViewPrescription viewPrescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_medicine);
        diseaseTextView = (TextView) findViewById(R.id.tv_disease);

        medicineListView = (ListView) findViewById(R.id.medicineList);
        Bundle prescription = getIntent().getExtras();
        String prescriptionName = prescription.getString("prescriptionName");
        String diseaseName = prescription.getString("diseaseName");
        diseaseTextView.setText(diseaseName);
        viewMedicines = getMedicineData(prescriptionName, diseaseName);
        Log.d("ViewMedicineOnCreate: ", " " + viewMedicines.size());
        medicineListView.setAdapter(new MedicineAdapter());
        Log.d("PrintListViewChildCount", " " + medicineListView.getChildCount());
    }


    public  List<Medicine> getMedicineData(String prescriptionName, String disease) {
        Log.d("ViewMedicine","Entered Get Medicine Data Method");
        String whereClause = DbContract.DbEntryPrescription.COLUMN_PRESCRIPTION_NAME + "=?  and " + DbContract.DbEntryPrescription.COLUMN_DISEASE + "=?";
        List<Medicine> viewMedicines = new ArrayList<>();
        String[] whereArgs = {prescriptionName, disease};
        mContext = getApplicationContext();
        db =  new DbHelper(getApplicationContext());
        mDb = db.getWritableDatabase();
        Cursor cursor = null;

        try {
            cursor = mDb.query(DbContract.DbEntryPrescription.TABLE_NAME, viewPrescription.ALL_COLUMNS, whereClause, whereArgs, null, null, null);
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
            Log.d("GetMedicineData","MedicineName Index");

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

                    Log.d("GetMedicineData"," Inside Cursor Loop MedicineName Index: " + cursor.getString(indexMedName));
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

   /* private String getDisease(String prescriptionName) {
        String diseaseName = "";
        String whereClause = DbContract.DbEntryPrescription.COLUMN_PRESCRIPTION_NAME + "=?";
        String[] whereArgs = {prescriptionName};
        mContext = getApplicationContext();
        db =  new DbHelper(getApplicationContext());
        mDb = db.getWritableDatabase();
        Cursor cursor = null;
        try {

            cursor = mDb.query(DbContract.DbEntryPrescription.TABLE_NAME, viewPrescription.ALL_COLUMNS, whereClause, whereArgs, null, null, null);
            int indexDisease = cursor.getColumnIndex(DbContract.DbEntryPrescription.COLUMN_DISEASE);
            Log.d("getDisease","Disease Name Index " + indexDisease);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    diseaseName = cursor.getString(indexDisease);
                    Log.d("getDisease", diseaseName);
                }
            }
        } catch (Exception e) {
            Log.d("Populate List View", " exception details: " + e);
        } finally {

            db.close();

        }
        return diseaseName;
    }
*/
    private class MedicineAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return viewMedicines.size();
        }

        @Override
        public String getItem(int position) {
            return viewMedicines.get(position).getMedName();
        }

        @Override
        public long getItemId(int position) {
            return viewMedicines.get(position).hashCode();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.medicine_item_cv, container, false);
            }

            String[] medTime = viewMedicines.get(position).getMedTime().toArray(new String[0]);
            /*((TextView) convertView.findViewById(R.id.list_item_med_tv))
                    .setText(getItem(position));*/

            TextView tv = (TextView) convertView.findViewById(R.id.list_item_med_tv) ;
            TextView tv1 = (TextView) convertView.findViewById(R.id.list_item_med_tv_heading) ;

            tv1.setText("Medicine Name:" + "\n" + "Medicine Time:" + "\n" +
                    "Medicine Dose:" + "\n" + "Medicine Type:");
            tv.setText(viewMedicines.get(position).getMedName() + "\n" + getMedTimeString(medTime) + "\n" +
                    viewMedicines.get(position).getMedDose() + "\n" + viewMedicines.get(position).getMedType());
            return convertView;
        }


        private String getMedTimeString(String[] inputTime)
        {
            StringBuilder output = new StringBuilder();

            for(int i=0;i<6;i++)
            {
                switch (i)
                {
                    case 0:
                    {
                        if(inputTime[0].equals("1"))
                            output = output.append("Before Breakfast,");
                        break;
                    }
                    case 1:
                    {
                        if(inputTime[1].equals("1"))
                            output = output.append("After Breakfast ");
                        break;
                    }
                    case 2:
                    {
                        if(inputTime[2].equals("1"))
                            output = output.append("Before Lunch ");
                        break;

                    }
                    case 3:
                    {

                        if(inputTime[3].equals("1"))
                            output = output.append("After Lunch ");
                        break;
                    }
                    case 4:
                    {
                        if(inputTime[4].equals("1"))
                            output = output.append("Before Dinner ");
                        break;
                    }
                    case 5:
                    {
                        if(inputTime[5].equals("1"))
                            output = output.append("After Dinner ");
                        break;
                    }
                }
            }
            return output.toString();
        }
    }
}






