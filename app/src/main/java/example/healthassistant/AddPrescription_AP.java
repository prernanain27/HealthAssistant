package example.healthassistant;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;


public class AddPrescription_AP extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_prescription__ap);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Spinner doseDropdown = (Spinner)findViewById(R.id.spinner1);
        String[] doseItems = new String[]{"Tablet", "Syrup", "Drops"};
        ArrayAdapter<String> doseAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, doseItems);
        doseDropdown.setAdapter(doseAdapter);

        Spinner medFormDropdown = (Spinner)findViewById(R.id.spinner2);
        String[] medFormItems = new String[]{"Tablet","Capsule","Syrup", "Drops"};
        ArrayAdapter<String> medFormAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, medFormItems);
        medFormDropdown.setAdapter(medFormAdapter);

        Spinner durationDropdown = (Spinner)findViewById(R.id.spinner3);
        String[] durationItems = new String[]{"Days", "Months"};
        ArrayAdapter<String> durationAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, durationItems);
        durationDropdown.setAdapter(durationAdapter);

        EditText medName = (EditText) findViewById(R.id.medName);
        EditText medDoze = (EditText) findViewById(R.id.dose);
        EditText medTotal = (EditText) findViewById(R.id.medTotal);
        EditText duration = (EditText) findViewById(R.id.duration);
    }


}
