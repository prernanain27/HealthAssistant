package example.healthassistant.Models;

import java.util.ArrayList;
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
        public static final String BEFORE_BREAKFAST_STRING = "Before Breakfast";
        public static final String AFTER_BREAKFAST_STRING ="After Breakfast";
        public static final String BEFORE_LUNCH_STRING = "Before Lunch";
        public static final String AFTER_LUNCH_STRING = "After Lunch";
        public static final String BEFORE_DINNER_STRING ="Before Dinner";
        public static final String AFTER_DINNER_STRING ="After Dinner";
        private  String medType = "";
        private  String medTotal = "";
        private  String durationType = "";


    public static ArrayList<String> getmedTimeFromBinary(List<String> medTimeBin)
    {

            ArrayList<String> output = new ArrayList<String>();

            for(int i=0;i<6;i++)
            {
                switch (i)
                {
                    case 0:
                    {
                        if(medTimeBin.get(0).equals("1"))
                            output.add("Before Breakfast");
                        break;
                    }
                    case 1:
                    {
                        if(medTimeBin.get(1).equals("1"))
                            output.add("After Breakfast");
                        break;
                    }
                    case 2:
                    {
                        if(medTimeBin.get(2).equals("1"))
                            output.add("Before Lunch");
                        break;

                    }
                    case 3:
                    {

                        if(medTimeBin.get(3).equals("1"))
                            output.add("After Lunch");
                        break;
                    }
                    case 4:
                    {
                        if(medTimeBin.get(4).equals("1"))
                            output.add("Before Dinner");
                        break;
                    }
                    case 5:
                    {
                        if(medTimeBin.get(5).equals("1"))
                            output.add("After Dinner");
                        break;
                    }
                }
            }
            return output;

    }
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
