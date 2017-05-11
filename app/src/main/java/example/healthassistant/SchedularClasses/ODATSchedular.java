package example.healthassistant.SchedularClasses;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.vision.Detector;

import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.joda.time.LocalTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import example.healthassistant.DbContract;
import example.healthassistant.DbHelper;
import example.healthassistant.Models.Med_Specification;
import example.healthassistant.Models.Medicine;
import example.healthassistant.Models.Prescription;
import example.healthassistant.Models.User;

/**
 * Created by Neha on 5/7/2017.
 */

public  class ODATSchedular {

    public String TAG = "ODATSChedular";

    private ArrayList<MedScheduleItem> UserSchedule;
    private  ArrayList<Med_Specification> mMSSData;
    private ArrayList<JobModel> mCurrentProcessor;
    private Context mContext;
    ArrayList<MedScheduleItem> tempSchedule = new ArrayList<>();
    private ResourceModel resourceModel;
    private SQLiteDatabase mDb;
    SQLiteOpenHelper db;
    private ArrayList<ResourceModel> resourceModelMeds;

    String[][] tempMedItemArray = new String[20][20];
    private HashMap<String,String> mUserPreferences;


    public ODATSchedular()
    {
        mMSSData = new ArrayList<>();
        mCurrentProcessor = new ArrayList<>();
        resourceModel = new ResourceModel();
        resourceModelMeds = new ArrayList<>();
        mUserPreferences = new HashMap<>();
        tempSchedule = new ArrayList<>();
    }

    public HashMap<String, String> getUserPreferences() {
        return mUserPreferences;
    }

    public void setUserPreferences(HashMap<String, String> userPreferences) {
        this.mUserPreferences = userPreferences;
    }

    private Boolean feasible;

    public ArrayList<MedScheduleItem> getUserSchedule() {
        return UserSchedule;
    }

    public void setUserSchedule(ArrayList<MedScheduleItem> userSchedule) {
        UserSchedule = userSchedule;
    }

    public ArrayList<Med_Specification> getmMSSData() {
        return mMSSData;
    }

    public void setmMSSData(ArrayList<Med_Specification> mMSSData) {
        this.mMSSData = mMSSData;
    }

    public ResourceModel getResourceModel() {
        return resourceModel;
    }

    public void setResourceModel(ResourceModel resourceModel) {
        this.resourceModel = resourceModel;
    }

    public ArrayList<ResourceModel> getResourceModelMeds() {
        return resourceModelMeds;
    }

    public void setResourceModelMeds(ArrayList<ResourceModel> resourceModelMeds) {
        this.resourceModelMeds = resourceModelMeds;
    }

    public Boolean getFeasible() {
        return feasible;
    }

    public void setFeasible(Boolean feasible) {
        this.feasible = feasible;
    }

    public  ResourceModel createODATSchedule(ArrayList<Med_Specification> MSSData, ArrayList<Prescription> PresData, HashMap<String ,String> userPreferences, Context ctxt)
    {

        ArrayList<String> temp = new ArrayList<>();
        MedScheduleItem medItem = new MedScheduleItem();
        mContext = ctxt;

        Medicine currentMedicine = new Medicine();
        this.resourceModel.setSchedule(tempSchedule);
        mMSSData = MSSData;
        mUserPreferences = userPreferences;
        Med_Specification medFromSpec = new Med_Specification();

        for (Prescription prescription:PresData
             ) {



                for (Medicine medicine : prescription.getMedicineArrayList()
                        ) {

                    currentMedicine = medicine;
                    medFromSpec = getMedSpecObject(MSSData, medicine.getMedName());
                    //this.resourceModel.setMed_Id(medFromSpec.getMed_id());
                    this.resourceModel.setFeasible(true);


                    temp = Medicine.getmedTimeFromBinary(medicine.getMedTime());
                    medicine.setMedTime(temp);
                    mCurrentProcessor = setProcessor(medicine, medFromSpec);

                    for (JobModel job:mCurrentProcessor
                            ) {


                        if (tempMedItemArray[0][0]==null) {


                            MedScheduleItem item = new MedScheduleItem();
                            item.setScheduleTime(job.getRelease_time());
                            item.setMedName(job.getMed_Name());
                            item.setDose(currentMedicine.getMedDose());

                            tempMedItemArray[0][0] = job.getRelease_time();
                            tempMedItemArray[1][0] = job.getMed_Name();

                            //tempSchedule.add(item);

                        } else {

                            checkAvailableTime(job);
                        }

                    }


            }







        }


        for(int i=0 ;i<tempMedItemArray[0].length;i++)
        {
            if(tempMedItemArray[0][i]!=null) {
                medItem = new MedScheduleItem();
                medItem.setMedName(tempMedItemArray[1][i]);
                medItem.setScheduleTime(tempMedItemArray[0][i]);
                medItem.setDose(currentMedicine.getMedDose());
                //medItem.setDuration(currentMedicine.getMedDuration());

                tempSchedule.add(medItem);

            }
            else
            {
                break;
            }
        }


        resourceModel.setSchedule(tempSchedule);


    return resourceModel;

    }

    private void insertScheduleToDB(Medicine med,MedScheduleItem scheduleItem)
    {
        db = new DbHelper((mContext));

        Log.d("Insert Schedule in DB: ", "Entered into Insert Scedule");
        mDb = db.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DbContract.DbEntryMed_Schedule.COLUMN_TIME,scheduleItem.getScheduleTime());
        cv.put(DbContract.DbEntryMed_Schedule.COLUMN_MED_NAME,med.getMedName());
        cv.put(DbContract.DbEntryMed_Schedule.COLUMN_MED_DURATION,med.getMedDuration());
        cv.put(DbContract.DbEntryMed_Schedule.COLUMN_MED_DOSE,med.getMedDose());


        try
        {
            long result = mDb.insert(DbContract.DbEntryMed_Schedule.TABLE_NAME, null, cv);

            if(result!=-1)
            {
                Toast.makeText(mContext,"Schedule Item successfully created",Toast.LENGTH_SHORT).show();
            }

        }
        catch (Exception ex)
        {
            Log.e(TAG,"Exception inserting schedule data");
        }





    }

    private void checkAvailableTime(JobModel job) {


        int i=0;
        Boolean found = false;

        for( i =0 ;i<tempMedItemArray[0].length;i++) {

            if(tempMedItemArray[0][i]!=null) {
                if (tempMedItemArray[0][i].contains(job.getRelease_time())) {
                    found = true;
                    //tempMedItemArray[1][i] += job.getMed_Name();
                    break;
                }
            }
            else
            {
                break;
            }
        }
        if(found)
        {

            tempMedItemArray[1][i] += "_"+job.getMed_Name();
        }
        else {

            while (tempMedItemArray[0][i + 1] != null) {
                i += 1;
            }
            tempMedItemArray[0][i] = job.getRelease_time();
            tempMedItemArray[1][i] = job.getMed_Name();
        }







       /* for (MedScheduleItem sItem:tempSchedule
             ) {


            if(sItem.getScheduleTime().equals(job.getRelease_time()))
            {
                medItem.setMedName(sItem.getMedName()+" "+job.getMed_Name());
                medItem.setScheduleTime(job.getRelease_time());
                //tempSchedule.removeIf(obj->obj.getScheduleTime().equals(job.getRelease_time()));

                Iterator<MedScheduleItem> itr = tempSchedule.iterator();
                while (itr.hasNext())
                {
                    if(itr.next().getScheduleTime().equals(job.getRelease_time()))
                    {
                        itr.remove();
                    }
                }
                break;
            }
            else
            {
                MedScheduleItem item = new MedScheduleItem();
                item.setMedName(job.getMed_Name());
                item.setScheduleTime(job.getRelease_time());

                tempSchedule.add(item);
                break;
            }

        }

        tempSchedule.add(medItem);
        */
    }



    /**
     * This function returns a med_Specification object from list of objects based on MedName
     * @param medSpecList
     * @param MedName
     * @return
     */
    private  Med_Specification getMedSpecObject(ArrayList<Med_Specification> medSpecList,String MedName)
    {
        Med_Specification specObjReturn = new Med_Specification();
        for (Med_Specification med:medSpecList
             ) {

            if(med.getMed_name().equals(MedName.toLowerCase()))
            {
                specObjReturn = med;

            }
        }
        return specObjReturn;
    }
    public  void setDoseSize()
    {

    }

    /**
     * this function creates processor intance for every medicine given in prescription
     * @param med
     * @param medSpecObj
     * @return
     */
    public ArrayList<JobModel> setProcessor(Medicine med, Med_Specification medSpecObj)
    {

        ArrayList<JobModel> tempProcessor = new ArrayList<>();
        HashMap<String,String> JMTimes = new HashMap<>();

        for (String time:med.getMedTime()
             ) {


                    JMTimes = getJobModelTimeValues(time);


            //for testing
            for (String key:JMTimes.keySet()
                 ) {
                Log.d(key,JMTimes.get(key));
            }

            JobModel objJob = new JobModel();

            objJob.setMed_Name(med.getMedName());
            objJob.setExecution_time(JMTimes.get(JobModel.EXECUTION_TIME));
            objJob.setDeadline(JMTimes.get(JobModel.DEADLINE));
            objJob.setRelease_time(JMTimes.get(JobModel.RELEASE_TIME));

            tempProcessor.add(objJob);
        }

        return tempProcessor;

    }

    /**
     * This function returns a hashmap for deadline, execution time and release time
     * values for a prescription based time string
     * @param time
     * @return
     */
    private HashMap<String,String> getJobModelTimeValues(String time)
    {
        HashMap<String,String> jMTimes = new HashMap<>();
        String executionTime ="";
        String deadline ="";
        String releaseTime="";
        DateTimeFormatter fm = ISODateTimeFormat.hourMinute();

        String wakeUpTime = mUserPreferences.get(User.WAKE_UP_TIME);
        String breakfastTime = mUserPreferences.get(User.BREAKFAST_TIME);
        //String lunchTime = mUserPreferences.get(User.LUNCH_TIME);
        String lunchTime = "13:30";
        //String dinnerTime = mUserPreferences.get(User.DINNER_TIME);
        String dinnerTime = "20:00";

        LocalTime wUTime = fm.parseLocalTime(wakeUpTime);
        LocalTime bFTime = fm.parseLocalTime(breakfastTime);
        LocalTime lTime = fm.parseLocalTime("13:30");
        LocalTime dTime = fm.parseLocalTime("20:00");

            if(time.equals(Medicine.BEFORE_BREAKFAST_STRING))
            {

                Period p = new Period(bFTime,wUTime);
                executionTime = String.valueOf( p.getHours());
                deadline = breakfastTime;
                releaseTime = bFTime.minusMinutes(30).toString();

                jMTimes.put(JobModel.EXECUTION_TIME,executionTime);
                jMTimes.put(JobModel.DEADLINE,deadline);
                jMTimes.put(JobModel.RELEASE_TIME,releaseTime);



            }
            else if(time.toString().equals(Medicine.AFTER_BREAKFAST_STRING) || time.toString().equals(Medicine.BEFORE_LUNCH_STRING))
            {

                int hours = Hours.hoursBetween(bFTime,lTime).getHours();
                executionTime = String.valueOf(hours);
                deadline = lunchTime;
                releaseTime = lTime.minusMinutes(30).toString();

                jMTimes.put(JobModel.EXECUTION_TIME,executionTime);
                jMTimes.put(JobModel.DEADLINE,deadline);
                jMTimes.put(JobModel.RELEASE_TIME,releaseTime);

            }


           else if(time.equals(Medicine.AFTER_LUNCH_STRING) || time.equals(Medicine.BEFORE_DINNER_STRING))
            {

                Period p = new Period(lTime,dTime);
                executionTime = String.valueOf(p.getHours());
                deadline = dinnerTime;
                releaseTime = dTime.minusMinutes(30).toString();

                jMTimes.put(JobModel.EXECUTION_TIME,executionTime);
                jMTimes.put(JobModel.DEADLINE,deadline);
                jMTimes.put(JobModel.RELEASE_TIME,releaseTime);

            }
            else if(time.equals(Medicine.AFTER_DINNER_STRING))
            {
                Period p = new Period(dTime.plusHours(2),dTime);
                executionTime = String.valueOf(p.getHours());
                deadline = dTime.plusHours(2).toString();
                releaseTime = dTime.plusHours(1).toString();

                jMTimes.put(JobModel.EXECUTION_TIME,executionTime);
                jMTimes.put(JobModel.DEADLINE,deadline);
                jMTimes.put(JobModel.RELEASE_TIME,releaseTime);


            }


        return jMTimes;
    }
    public  void setResource()
    {

    }

    public  void updateData()
    {

    }

    /**
     * this utility function returns time object wrapped in date time object for input string sent to the function
     * @param time
     * @return
     */
    public static LocalTime getDateTimeFromString(String time)
    {

        DateTimeFormatter fm = ISODateTimeFormat.hourMinute();

        DateTimeFormatter formatter = DateTimeFormat.forPattern("hh:mm");

        DateTime date;
        LocalTime timeTemp = new LocalTime();
        try {
            date  = formatter.parseDateTime(time);
             timeTemp = fm.parseLocalTime(time);
            //Log.d("wohooooo",timeTemp.toString());

        }
        catch (Exception ex)
        {
            Log.e("Parse Exception","Exception parsing time string"+ex.getMessage());
            return null;
        }

        return timeTemp;


    }

}
