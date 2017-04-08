package example.healthassistant;

import java.util.Enumeration;

/**
 * Created by ameethakkar on 4/2/17.
 */

public class Medicine {
    private static String medName = "";
    private static String medDose = "";
    private static String medDuration = "";
    private static String medTime = "";
    private static String medType = "";
    private static String medTotal = "";
    private static String durationType = "";


    public static String getMedName() {
        return medName;
    }

    public static void setMedName(String medName) {
        Medicine.medName = medName;
    }

    public static String getMedDose() {
        return medDose;
    }

    public static void setMedDose(String medDose) {
        Medicine.medDose = medDose;
    }

    public static String getMedDuration() {
        return medDuration;
    }

    public static void setMedDuration(String medDuration) {
        Medicine.medDuration = medDuration;
    }

    public static String getMedTime() {
        return medTime;
    }

    public static void setMedTime(String medTime) {
        Medicine.medTime = medTime;
    }

    public static String getMedType() {
        return medType;
    }

    public static void setMedType(String medType) {
        Medicine.medType = medType;
    }

    public static String getMedTotal() {
        return medTotal;
    }

    public static void setMedTotal(String medTotal) {
        Medicine.medTotal = medTotal;
    }

    public static String getDurationType() {
        return durationType;
    }

    public static void setDurationType(String durationType) {
        Medicine.durationType = durationType;
    }
}
