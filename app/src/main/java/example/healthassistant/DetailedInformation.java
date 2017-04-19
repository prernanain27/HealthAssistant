package example.healthassistant;

import android.app.Dialog;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;

public class DetailedInformation extends Fragment implements NumberPicker.OnValueChangeListener{
    private EditText feet;
    private EditText inches;
    private Spinner bloodSign;
    private Spinner bloodType;
    private Spinner eyeSign;
    private EditText eyeSight;
    private Button save;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle
            savedInstanceState) {
        //----inflate the layout for this fragment --
        View view = inflater.inflate(R.layout.activity_fragment2,container, false);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final User user = new User();
        feet = (EditText)getActivity().findViewById(R.id.feet);
        feet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show(feet);
            }
        });
        inches = (EditText) getActivity().findViewById(R.id.inches);
        inches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show(inches);
            }
        });
        bloodSign = (Spinner)getActivity().findViewById(R.id.bloodSign);
       // user.setBlood_sign(bloodSign.getSelectedItem().toString());
        bloodType = (Spinner)getActivity().findViewById(R.id.bloodType);
        eyeSign = (Spinner)getActivity().findViewById(R.id.eyesightSign);
        eyeSight = (EditText)getActivity().findViewById(R.id.eyesight);
       // user.setBoold_type(bloodType.getSelectedItem().toString());
       // user.setHeight_feet(feet.getText().toString());
     //   user.setHeight_inches(inches.getText().toString());
      //  user.setEye_sign(eyeSign.getSelectedItem().toString());
     //   user.setEye_sight(eyeSight.getText().toString());
        save = (Button) getActivity().findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setBlood_sign(bloodSign.getSelectedItem().toString());
                user.setBoold_type(bloodType.getSelectedItem().toString());
                user.setHeight_feet(feet.getText().toString());
                user.setHeight_inches(inches.getText().toString());
                user.setEye_sign(eyeSign.getSelectedItem().toString());
                user.setEye_sight(eyeSight.getText().toString());
            }
        });


    }

    public  void show(final EditText edt){
        final Dialog d = new Dialog(getActivity());
        d.setTitle("NumberPicker");
        d.setContentView(R.layout.dialog);
        Button b1 = (Button) d.findViewById(R.id.button1);
        Button b2 = (Button) d.findViewById(R.id.button2);
        final NumberPicker np = (NumberPicker) d.findViewById(R.id.numberPicker1);
        np.setMaxValue(12); // max value 100
        np.setMinValue(0);   // min value 0
        np.setWrapSelectorWheel(false);
        np.setOnValueChangedListener(this);
        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                edt.setText(String.valueOf(np.getValue())); //set the value to textview
                d.dismiss();
            }
        });
        b2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
        d.show();




    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

    }
}

