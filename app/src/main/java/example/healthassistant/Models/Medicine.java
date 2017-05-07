package example.healthassistant.Models;

import java.util.Enumeration;
import java.util.List;

/**
 * Created by ameethakkar on 4/2/17.
 */

public class Medicine {


        private  String medName = "";
        private  String medDose = "";
        private  String medDuration = "";
        private List<String> medTime ;
        private StringBuilder medTimeString;
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

    public List<String> getMedTime() {


        return medTime;
    }

    public void setMedTime(List<String> medTime) {


        this.medTime = medTime;
    }

    public StringBuilder getMedTimeString() {

        for (String str:this.medTime
                ) {

            medTimeString.append(str+ " , ");
        }
        return medTimeString;
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
