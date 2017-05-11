package example.healthassistant.Models;

/**
 * Created by Neha on 5/11/2017.
 */

public class Med_Scedule {
    private String med_time;
    private String med_Name;
    private String duration;
    private String dose;
    private String days_lapsed;

    public String getMed_time() {
        return med_time;
    }

    public void setMed_time(String med_time) {
        this.med_time = med_time;
    }

    public String getMed_Name() {
        return med_Name;
    }

    public void setMed_Name(String med_Name) {
        this.med_Name = med_Name;
    }

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

    public String getDays_lapsed() {
        return days_lapsed;
    }

    public void setDays_lapsed(String days_lapsed) {
        this.days_lapsed = days_lapsed;
    }
}
