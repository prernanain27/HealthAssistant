package example.healthassistant;

import java.util.Enumeration;

/**
 * Created by ameethakkar on 4/2/17.
 */

public class Medicine {


        private  String medName = "";
        private  String medDose = "";
        private  String medDuration = "";
        private  String medTime = "";
        private  String medType = "";
        private  String medTotal = "";
        private  String durationType = "";


    public String getMedName() {
        return medName;
    }

    public void setMedName(String medName) {
        this.medName = medName;
    }

    public String getMedDose() {
        return medDose;
    }

    public void setMedDose(String medDose) {
        this.medDose = medDose;
    }

    public String getMedDuration() {
        return medDuration;
    }

    public void setMedDuration(String medDuration) {
        this.medDuration = medDuration;
    }

    public String getMedTime() {
        return medTime;
    }

    public void setMedTime(String medTime) {
        this.medTime = medTime;
    }

    public String getMedType() {
        return medType;
    }

    public void setMedType(String medType) {
        this.medType = medType;
    }

    public String getMedTotal() {
        return medTotal;
    }

    public void setMedTotal(String medTotal) {
        this.medTotal = medTotal;
    }

    public String getDurationType() {
        return durationType;
    }

    public void setDurationType(String durationType) {
        this.durationType = durationType;
    }
}
