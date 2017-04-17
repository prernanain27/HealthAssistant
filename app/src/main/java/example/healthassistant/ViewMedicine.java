package example.healthassistant;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Bundle;
//import android.support.v7.widget.Card;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ViewMedicine extends Activity {

    private Context mContext;
    private RelativeLayout mRelativeLayout;
    ListView medicineListView;
    private SQLiteDatabase mDb;
    SQLiteOpenHelper db;
    List<Medicine> viewMedicines = new ArrayList<Medicine>();

    ViewPrescription viewPrescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_medicine);

        medicineListView = (ListView) findViewById(R.id.medicineList);
        Bundle prescription = getIntent().getExtras();
        String prescriptionName = prescription.getString("prescriptionName");
        viewMedicines = getMedicineData(prescriptionName);
        Log.d("ViewMedicineOnCreate: ", " " + viewMedicines.size());
        ArrayAdapter<Medicine> simpleAdapter = new CustomAdapter(this, R.layout.medicine_item_cv, viewMedicines);
        medicineListView.setAdapter(simpleAdapter);
        Log.d("PrintListViewChildCount", " " + medicineListView.getChildCount());
    }


    public List<Medicine> getMedicineData(String prescriptionName) {
        setContentView(R.layout.medicine_item_cv);

        Log.d("ViewMedicine","Entered Get Medicine Data Method");
        String whereClause = DbContract.DbEntryPrescription.COLUMN_PRESCRIPTION_NAME + "=?";
        List<Medicine> viewMedicines = new ArrayList<>();
        String[] whereArgs = {prescriptionName};
        mContext = getApplicationContext();
        db =  new DbHelper(getApplicationContext());
        mDb = db.getWritableDatabase();
        Cursor cursor = null;

        try {
            cursor = mDb.query(DbContract.DbEntryPrescription.TABLE_NAME, viewPrescription.ALL_COLUMNS, whereClause, whereArgs, null, null, null);
            int indexMedName = cursor.getColumnIndex(DbContract.DbEntryPrescription.COLUMN_MED_NAME);
            int indexMedTime = cursor.getColumnIndex(DbContract.DbEntryPrescription.COLUMN_MED_TIME);
            int indexMedDose = cursor.getColumnIndex(DbContract.DbEntryPrescription.COLUMN_MED_DOSE);
            int indexMedType = cursor.getColumnIndex(DbContract.DbEntryPrescription.COLUMN_MED_TYPE);
            int indexMedDuration = cursor.getColumnIndex(DbContract.DbEntryPrescription.COLUMN_DURATION);
            Log.d("GetMedicineData","MedicineName Index");

           // CardView card = (CardView) findViewById(R.id.card_view_medItem);
            if (cursor.getCount() > 0) {

                while (cursor.moveToNext()) {
                    Medicine med = new Medicine();
                    med.setMedName(cursor.getString(indexMedName));
                    med.setMedDose(cursor.getString(indexMedDose));
                    med.setMedDuration(cursor.getString(indexMedDuration));
                    med.setMedType(cursor.getString(indexMedType));
                    med.setMedTime(cursor.getString(indexMedTime));

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
}






