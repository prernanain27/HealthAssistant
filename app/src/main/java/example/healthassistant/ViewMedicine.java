package example.healthassistant;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Bundle;
//import android.support.v7.widget.Card;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.util.TypedValue;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewMedicine extends Activity {

    private Context mContext;
    private RelativeLayout mRelativeLayout;
    ListView medicineListView;
    private SQLiteDatabase mDb;
    SQLiteOpenHelper db;

    ViewPrescription viewPrescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_medicine);
        CardView[] cardViews = new CardView[20];
        medicineListView = (ListView) findViewById(R.id.medicineList);
        Bundle prescription = getIntent().getExtras();
        String prescriptionName = prescription.getString("prescriptionName");
        cardViews = getMedicineData(prescriptionName);
        ArrayAdapter<CardView> simpleAdapter = new ArrayAdapter<CardView>(this, R.layout.medicine_item_cv, cardViews);
        medicineListView.setAdapter(simpleAdapter);
        medicineListView.getChildCount();
    }


    public CardView[] getMedicineData(String prescriptionName) {
        setContentView(R.layout.medicine_item_cv);
        Log.d("ViewMedicine","Entered Get Medicine Data Method");
        String whereClause = DbContract.DbEntryPrescription.COLUMN_PRESCRIPTION_NAME + "=?";
        TextView tv = (TextView) findViewById(R.id.list_item_med_tv) ;
        TextView tv1 = (TextView) findViewById(R.id.list_item_med_tv_heading) ;
        String[] whereArgs = {prescriptionName};
        CardView[] cardViewArrayList = new CardView[10];
        mContext = getApplicationContext();
        db =  new DbHelper(getApplicationContext());
        mDb = db.getWritableDatabase();
        Cursor cursor = null;
        int i = 0;
        try {
            cursor = mDb.query(DbContract.DbEntryPrescription.TABLE_NAME, viewPrescription.ALL_COLUMNS, whereClause, whereArgs, null, null, null);
            int indexMedName = cursor.getColumnIndex(DbContract.DbEntryPrescription.COLUMN_MED_NAME);
            int indexMedTime = cursor.getColumnIndex(DbContract.DbEntryPrescription.COLUMN_MED_TIME);
            int indexMedDose = cursor.getColumnIndex(DbContract.DbEntryPrescription.COLUMN_MED_DOSE);
            int indexMedType = cursor.getColumnIndex(DbContract.DbEntryPrescription.COLUMN_MED_TYPE);
            Log.d("GetMedicineData","MedicineName Index");
            CardView card = (CardView) findViewById(R.id.card_view_medItem);
            if (cursor.getCount() > 0) {

                while (cursor.moveToNext()) {
                    tv1.setText("Medicine Name:" + "\n" + "Medicine Time:" + "\n" +
                            "Medicine Dose:" + "\n" + "Medicine Type:");
                    tv.setText(cursor.getString(indexMedName) + "\n" + cursor.getString(indexMedTime) + "\n" +
                           cursor.getString(indexMedDose) + "\n" + cursor.getString(indexMedType));
                    cardViewArrayList[i] = card;
                    i++;

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
        return cardViewArrayList;
    }


}


