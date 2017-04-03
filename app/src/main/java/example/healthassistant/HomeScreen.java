package example.healthassistant;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import android.widget.AdapterView;
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

        homeList.add(createPlanet("listItem","Edit PHR"));
        homeList.add(createPlanet("listItem","Add Prescription"));

    }

    private HashMap<String,String> createPlanet(String key, String name){
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

        SimpleAdapter simpleAdpt = new SimpleAdapter(this, homeList, android.R.layout.simple_list_item_1,
                new String[] {"listItem"}, new int[] {android.R.id.text1});

        lv.setAdapter(simpleAdpt);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parentAdapter, View view,
                                    int position, long id) {
                if(position == 1){
                    Intent intent = new Intent(getApplicationContext(),AddPrescription_AP.class);
                    startActivity(intent);
                }


            }});
    }


}
