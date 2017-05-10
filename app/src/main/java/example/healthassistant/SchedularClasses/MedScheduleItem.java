package example.healthassistant.SchedularClasses;

/**
 * Created by Neha on 5/7/2017.
 */

public class MedScheduleItem {

    private String scheduleTime;
    private String medName;
    private String duration;
    private String dose;

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getScheduleTime() {
        return scheduleTime;
    }

    public void setScheduleTime(String scheduleTime) {
        this.scheduleTime = scheduleTime;
    }

    public String getMedName() {
        return medName;
    }

    public void setMedName(String medName) {
        this.medName = medName;
    }
}
