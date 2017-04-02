package example.healthassistant;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


public class PHR extends AppCompatActivity  {
    private Button personal;
    private Button details;
    private Button timings;
    private Button save;
    public String dateFromPicker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phr);
        save = (Button)findViewById(R.id.savePHR);
        personal=(Button) findViewById(R.id.personal);
        personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment1 fragment1 =new Fragment1();
                fragmentTransaction.replace(R.id.contentFrame,fragment1);
                fragmentTransaction.commit();
            }
        });
        details=(Button) findViewById(R.id.details);
        details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(User.getName()!= "" ||User.getSex()!= ""||User.getPrimary_contact()!= ""||User.getEmergency_contact()!= ""){
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment2 fragment2 =new Fragment2();
                fragmentTransaction.replace(R.id.contentFrame,fragment2);
             fragmentTransaction.commit();}
                else
                    Toast.makeText(PHR.this, "Fill all the mandatory Personal Information",Toast.LENGTH_SHORT).show();

            }
        });
        timings=(Button) findViewById(R.id.timings);
        timings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(User.getBlood_sign()!=""||User.getBoold_type()!=""||User.getHeight_feet()!=""||User.getHeight_inches()!=""){
                FragmentManager fragmentManager = getFragmentManager();
                 FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment3 fragment3 =new Fragment3();
                fragmentTransaction.replace(R.id.contentFrame,fragment3);
                fragmentTransaction.commit();}
                else
                    Toast.makeText(PHR.this, "Fill all the mandatory Detailed Information",Toast.LENGTH_SHORT).show();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(User.getName()!= "" ||User.getSex()!= ""||User.getDob()!= ""||User.getPrimary_contact()!= ""||User.getEmergency_contact()!= ""){
                    if(User.getBlood_sign()!=""||User.getBoold_type()!=""||User.getHeight_feet()!=""||User.getHeight_inches()!=""){
                        if(User.getWake_up_time()!=""||User.getBreakfast_time()!=""||User.getLunch_time()!=""||User.getGym_time()!=""||User.getDinner_time()!=""||User.getSleep_time()!=""){
                            Toast.makeText(PHR.this,"Wohhoo",Toast.LENGTH_SHORT).show();
                        }
                        else Toast.makeText(PHR.this, "Fill Timings Mandatory Details",Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(PHR.this, "Fill Detailed Mandatory Details",Toast.LENGTH_SHORT).show();

                }
                else
                    Toast.makeText(PHR.this, "Fill Personal Mandatory Details",Toast.LENGTH_SHORT).show();
            }
        });



    }


}
