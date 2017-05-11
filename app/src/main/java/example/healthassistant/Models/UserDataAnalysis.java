package example.healthassistant.Models;

/**
 * Created by Neha on 5/11/2017.
 */

public class UserDataAnalysis {

    private String dose_Date;
    private String dose_Time;
    private String med_List;
    private String isTaken;

    public String getDose_Date() {
        return dose_Date;
    }

    public void setDose_Date(String dose_Date) {
        this.dose_Date = dose_Date;
    }

    public String getDose_Time() {
        return dose_Time;
    }

    public void setDose_Time(String dose_Time) {
        this.dose_Time = dose_Time;
    }

    public String getMed_List() {
        return med_List;
    }

    public void setMed_List(String med_List) {
        this.med_List = med_List;
    }

    public String getIsTaken() {
        return isTaken;
    }

    public void setIsTaken(String isTaken) {
        this.isTaken = isTaken;
    }
}
