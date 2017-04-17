package example.healthassistant;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ameethakkar on 4/16/17.
 */
public class CustomAdapter extends ArrayAdapter<Medicine> {
        private final List<Medicine> viewMedicines;
        //private final List<CardView> cardViews;
        public CustomAdapter(Context context, int resource, List<Medicine> viewMedicines) {
            super(context, resource, viewMedicines);
            this.viewMedicines = viewMedicines;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Medicine med = viewMedicines.get(position);
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.medicine_item_cv, null);

// Set the text
            TextView tv = (TextView) row.findViewById(R.id.list_item_med_tv) ;
            TextView tv1 = (TextView) row.findViewById(R.id.list_item_med_tv_heading) ;

            tv1.setText("Medicine Name:" + "\n" + "Medicine Time:" + "\n" +
                    "Medicine Dose:" + "\n" + "Medicine Type:");
            tv.setText(med.getMedName() + "\n" + med.getMedTime() + "\n" +
                    med.getMedDose() + "\n" + med.getMedType());
            Log.d("CustomAdapter: " , " " + med.getMedName() + " "+med.getMedDose());
            return row;
        }
    }


