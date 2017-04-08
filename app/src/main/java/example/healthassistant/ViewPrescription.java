package example.healthassistant;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ViewPrescription extends AppCompatActivity {

    private SQLiteDatabase mDb;
    SQLiteOpenHelper db;
    ListView prescriptionListView;
    private static final String[] ALL_COLUMNS = {

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
//            imgData = new ImageData(getApplicationContext());
//            if(imgData != null) {

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
