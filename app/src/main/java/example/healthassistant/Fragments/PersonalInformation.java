package example.healthassistant.Fragments;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import example.healthassistant.Models.User;
import example.healthassistant.R;


public class PersonalInformation extends Fragment  {
    private EditText name;
    private TextView dob;
    private Spinner sex;
    private java.util.Calendar calendar;
    private int year,month,day;
    private EditText address;
    private EditText primary_contact;
    private EditText emergency_contact;
    private EditText careTaker_contact;
    private Button save;
    private SQLiteDatabase mDb;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle
            savedInstanceState) {
        //----inflate the layout for this fragment v --
        View view = inflater.inflate(R.layout.activity_fragment1,container, false);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        save = (Button)getActivity().findViewById(R.id.save);
        name = (EditText)getActivity().findViewById(R.id.name);
        // user.setName(name.getText().toString());
        sex = (Spinner)getActivity().findViewById(R.id.spinnerSex);
        // user.setSex(sex.getSelectedItem().toString());
        address = (EditText)getActivity().findViewById(R.id.address);
        // user.setAddress(address.getText().toString());
        primary_contact = (EditText)getActivity().findViewById(R.id.primaryContact);
        // user.setPrimary_contact(primary_contact.getText().toString());
        emergency_contact = (EditText) getActivity().findViewById(R.id.emergencyContact);
        // user.setEmergency_contact(emergency_contact.getText().toString());
        careTaker_contact = (EditText)getActivity().findViewById(R.id.caretakerContact);
        // user.setCare_taker_contact(careTaker_contact.getText().toString());
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().trim().matches("")) {
                    Toast.makeText(getActivity(), "Please enter Name", Toast.LENGTH_SHORT).show();
                }
                else if (primary_contact.getText().toString().trim().matches("")) {
                    Toast.makeText(getActivity(), "Please enter Primary Contact Number", Toast.LENGTH_SHORT).show();
                }
                else if (emergency_contact.getText().toString().trim().matches("")) {
                    Toast.makeText(getActivity(), "Please enter Emergency Contact Number", Toast.LENGTH_SHORT).show();
                }
                else {
                    User.setEmergency_contact(emergency_contact.getText().toString());
                    User.setCare_taker_contact(careTaker_contact.getText().toString());
                    User.setPrimary_contact(primary_contact.getText().toString());
                    User.setAddress(address.getText().toString());
                    User.setSex(sex.getSelectedItem().toString());
                    User.setDob(dob.getText().toString());
                    User.setName(name.getText().toString());
                    Toast.makeText(getActivity().getApplicationContext(), "Information Saved", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dob = (TextView)getActivity().findViewById(R.id.dob);


        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int yy = calendar.get(Calendar.YEAR);
                int mm = calendar.get(Calendar.MONTH);
                int dd = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String date = String.valueOf(year) +"-"+String.valueOf(monthOfYear)
                                +"-"+String.valueOf(dayOfMonth);
                        //tfDescription.setText(date);
                        dob.setText(date);
                    }
                }, yy, mm, dd);
                datePicker.show();
                //dob.setText(User.getDob());

            }
        });

    }


}

