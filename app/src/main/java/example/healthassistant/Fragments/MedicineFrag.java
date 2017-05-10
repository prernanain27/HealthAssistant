package example.healthassistant.Fragments;

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
import android.widget.Toast;

import java.util.List;

import example.healthassistant.Models.Medicine;
import example.healthassistant.Models.Prescription;
import example.healthassistant.R;


//Added by nehaq
public class MedicineFrag extends Fragment implements MultiSelectionSpinner.OnMultipleItemsSelectedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    EditText medName;
    EditText medDose;
    EditText medTotal;
    EditText duration;
    Spinner doseDropdown;
    MultiSelectionSpinner medTimeDropdown;
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

        medTimeDropdown = (MultiSelectionSpinner) getActivity().findViewById(R.id.spinner2);
        String[] medTimeItems = new String[]{"Before Breakfast","After Breakfast","Before Lunch","After Lunch","Before Dinner","After Dinner"};
        //ArrayAdapter<String> medTimeAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, medTimeItems);
        //medTimeDropdown.setAdapter(medTimeAdapter);
        medTimeDropdown.setItems(medTimeItems);
        medTimeDropdown.setSelection(new int[]{1});
        medTimeDropdown.setListener(this);

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

                if (medName.getText().toString().trim().matches("")) {
                    Toast.makeText(getActivity(), "Please enter Medicine Name", Toast.LENGTH_SHORT).show();
                }
                else if (medDose.getText().toString().trim().matches("")) {
                    Toast.makeText(getActivity(), "Please enter Medicine Dose", Toast.LENGTH_SHORT).show();
                }
                else if (duration.getText().toString().trim().matches("")) {
                    Toast.makeText(getActivity(), "Please enter the duration", Toast.LENGTH_SHORT).show();
                }
                else {
                    Medicine med = new Medicine();
                    if ((medName.getText().toString() != "") || (medDose.getText().toString() != "")) {

                        med.setMedName(medName.getText().toString());
                        med.setMedDose(medDose.getText().toString());
                        med.setMedDuration(duration.getText().toString());
                        med.setMedTotal(medTotal.getText().toString());
                        med.setMedType(doseDropdown.getSelectedItem().toString());
                        med.setMedTime(medTimeDropdown.getSelectedStrings());
                        med.setDurationType(durationDropdown.getSelectedItem().toString());

                        Prescription.medStaticArrayList.add(med);

                        Log.d("MedicineFrag", med.getMedName());
                        Log.d("Array Size", "" + Prescription.medStaticArrayList.size());

                        Toast.makeText(getActivity(), "Medicine added to Prescription", Toast.LENGTH_SHORT).show();
                        med = null;
                    }
                }
            }
        });

        for(Medicine temp: Prescription.medStaticArrayList){
            Log.d("Object Name:", " " + temp);
        }

    }

    @Override
    public void selectedIndices(List<Integer> indices) {

    }

    @Override
    public void selectedStrings(List<String> _strings) {

        final List<String> strings = _strings;
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                Toast.makeText(getActivity().getApplicationContext(), strings.toString(), Toast.LENGTH_LONG).show();

            }
        });

    }
}
