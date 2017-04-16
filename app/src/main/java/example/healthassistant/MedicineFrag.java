package example.healthassistant;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Fragment;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


public class MedicineFrag extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    EditText medName;
    EditText medDose;
    EditText medTotal;
    EditText duration;
    Spinner doseDropdown;
    Spinner medTimeDropdown;
    Spinner durationDropdown;
    Prescription prescription = new Prescription();

    // TODO: Rename and change types of parameters
   // public Medicine med = new Medicine();


    public MedicineFrag() {
    }


    // TODO: Rename and change types and number of parameters
    public static MedicineFrag newInstance(String param1, String param2) {
        MedicineFrag fragment = new MedicineFrag();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_medicine, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState){

        super.onActivityCreated(savedInstanceState);

        doseDropdown = (Spinner) getActivity().findViewById(R.id.spinner1);

        String[] doseItems = new String[]{"Tablet","Capsule","Syrup", "Drops"};
        ArrayAdapter<String> doseAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, doseItems);
        doseDropdown.setAdapter(doseAdapter);

        medTimeDropdown = (Spinner) getActivity().findViewById(R.id.spinner2);
        String[] medTimeItems = new String[]{"Before Breakfast","After Breakfast","Before Lunch","After Lunch","Before Dinner","After Dinner"};
        ArrayAdapter<String> medTimeAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, medTimeItems);
        medTimeDropdown.setAdapter(medTimeAdapter);

        durationDropdown = (Spinner)getActivity().findViewById(R.id.spinner3);
        String[] durationItems = new String[]{"Days", "Months"};
        ArrayAdapter<String> durationAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, durationItems);
        durationDropdown.setAdapter(durationAdapter);

         medName = (EditText) getActivity().findViewById(R.id.medName);
         medDose = (EditText) getActivity().findViewById(R.id.dose);
         medTotal = (EditText) getActivity().findViewById(R.id.medTotal);
         duration = (EditText) getActivity().findViewById(R.id.duration);


        Log.d("MedicineName", medName.getText().toString());

        Button saveMed = (Button) getActivity().findViewById(R.id.saveMedicine);
        saveMed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Medicine med = new Medicine();
                if((medName.getText().toString()!="") || (medDose.getText().toString() != "")) {

                    med.setMedName(medName.getText().toString());
                    med.setMedDose(medDose.getText().toString());
                    med.setMedDuration(duration.getText().toString());
                    med.setMedTotal(medTotal.getText().toString());
                    med.setMedType(doseDropdown.getSelectedItem().toString());
                    med.setMedTime(medTimeDropdown.getSelectedItem().toString());
                    med.setDurationType(durationDropdown.getSelectedItem().toString());

                    prescription.medicineArrayList.add(med);

                    Log.d("MedicineFrag", med.getMedName());
                    Log.d("Array Size",  "" + prescription.medicineArrayList.size());
                    //Log.d("Object Name:", " " + prescription.medicineArrayList.get(0));

                    //Log.d("Object Name:", " " + prescription.medicineArrayList.get());
                    //Log.d("DoseItems",doseDropdown.toString());
                    med = null;
                }
            }
        });

        for(Medicine temp: Prescription.medicineArrayList){
            Log.d("Object Name:", " " + temp);
        }

    }

}
