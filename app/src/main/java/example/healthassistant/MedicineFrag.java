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
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    EditText medName;
    EditText medDose;
    EditText medTotal;
    EditText duration;
    Spinner doseDropdown;
    Spinner medTimeDropdown;
    Spinner durationDropdown;
    Prescription prescription = new Prescription();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public Medicine med = new Medicine();

   // private OnFragmentInteractionListener mListener;

    public MedicineFrag() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static MedicineFrag newInstance(String param1, String param2) {
        MedicineFrag fragment = new MedicineFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
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

//        String doseTxt = doseDropdown.getSelectedItem().toString();
//        String medTimeTxt = medTimeDropdown.getSelectedItem().toString();
//        String durationTxt = durationDropdown.getSelectedItem().toString();
        Log.d("MedicineName", medName.getText().toString());

        Button saveMed = (Button) getActivity().findViewById(R.id.saveMedicine);
        saveMed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if((medName.getText().toString()!="") || (medDose.getText().toString()) != "") {
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
                    Log.d("DoseItems",doseDropdown.toString());
                }
            }
        });

    }
    // TODO: Rename method, update argument and hook method into UI event
   /* public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

   /* @Override
   /* public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

    /*@Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
   /* public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }*/
}
