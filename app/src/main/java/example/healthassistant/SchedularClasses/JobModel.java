package example.healthassistant.SchedularClasses;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import example.healthassistant.DbContract;
import example.healthassistant.DbHelper;
import example.healthassistant.Models.Medicine;

/**
 * Created by Neha on 5/7/2017.
 */

public class JobModel {

    private String TAG ="Job Model";

    public static final String EXECUTION_TIME ="execution time";
    public static final String RELEASE_TIME = "release time";
    public static final String DEADLINE ="deadline";

    private String deadline;
    private String execution_time;
    private String med_Id;
    private String med_Name;
    private String release_time;
    private Boolean hasInterferer;
    private String duration;
    private String dose;

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    private SQLiteDatabase mDb;
    SQLiteOpenHelper db;
    public JobModel(String medId)
    {
        this.med_Id = medId;
    }


    public JobModel()
    {

    }
    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getExecution_time() {
        return execution_time;
    }

    public void setExecution_time(String execution_time) {
        this.execution_time = execution_time;
    }

    public String getMed_Id() {
        return med_Id;
    }

    public void setMed_Id(String med_Id) {
        this.med_Id = med_Id;
    }

    public String getMed_Name() {
        return med_Name;
    }

    public void setMed_Name(String med_Name) {
        this.med_Name = med_Name;
    }

    public String getRelease_time() {
        return release_time;
    }

    public void setRelease_time(String release_time) {
        this.release_time = release_time;
    }

    public Boolean getHasInterferer() {
        return hasInterferer;
    }

    public void setHasInterferer(Boolean hasInterferer) {
        this.hasInterferer = hasInterferer;
    }
}
