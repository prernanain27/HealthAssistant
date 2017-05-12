package example.healthassistant.Activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import example.healthassistant.R;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class HomeScreen extends AppCompatActivity {
    List<Map<String,String>> homeList = new ArrayList<Map<String,String>>();

    private void initList(){

        homeList.add(createList("listItem","View & Edit PHR"));
        homeList.add(createList("listItem","Add Prescription"));

    }

    private HashMap<String,String> createList(String key, String name){
        HashMap<String,String> list = new HashMap<String, String>();
        list.put(key, name);
        return list;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        setTitle("Home");

        initList();
        ListView lv = (ListView) findViewById(R.id.homeListView);
        CardView viewPrescriptionCard = (CardView) findViewById(R.id.card_view1);
        Button viewPresBtn = (Button) findViewById(R.id.prescription);
        Button viewMed = (Button) findViewById(R.id.medSchedule);
        Button appoint = (Button) findViewById(R.id.appointment);
        appoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent meet = new Intent(HomeScreen.this, Appointment_Scheduler.class);
                startActivity(meet);

            }
        });
        viewMed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent med = new Intent(getApplicationContext(),ViewScheduleActivity.class);
                startActivity(med);
            }
        });
        viewPresBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewPresCard = new Intent(getApplicationContext(),ViewPrescription.class);
                startActivity(viewPresCard);
            }
        });

        Button viewPharmLocator = (Button)findViewById(R.id.pharmacy);
        viewPharmLocator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewPharmLocator = new Intent(getApplicationContext(),MapsActivity.class);
                startActivity(viewPharmLocator);
            }
        });
        appoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent( getApplicationContext(),Appointment_Scheduler.class);
                startActivity(i);
            }
        });
        SimpleAdapter simpleAdpt = new SimpleAdapter(this, homeList,android.R.layout.simple_expandable_list_item_1,
                new String[] {"listItem"}, new int[] {android.R.id.text1});

        lv.setAdapter(simpleAdpt);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parentAdapter, View view,
                                    int position, long id) {
                if(position == 1){
                    Intent intent = new Intent(getApplicationContext(),AddPrescription_AP.class);
                    startActivity(intent);
                }
                if(position == 0){
                    Intent intent = new Intent(getApplicationContext(),ViewPHR.class);
                    startActivity(intent);
                }


            }});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_uninstall) {
            Uri packageURI = Uri.parse("package:example.healthassistant");
            Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
            startActivity(uninstallIntent);
            return true;
        }
        if (id == R.id.action_signout) {
            Intent signoutIntent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(signoutIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
