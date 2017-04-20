package example.healthassistant;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.view.MotionEvent;
import android.view.View;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class HomeScreen extends AppCompatActivity {
    List<Map<String,String>> homeList = new ArrayList<Map<String,String>>();

    private void initList(){

        homeList.add(createList("listItem","View PHR"));
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


}
