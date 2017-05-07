package example.healthassistant.Models;

import java.util.ArrayList;

/**
 * Created by ameethakkar on 4/2/17.
 */

public class
Prescription {
    private static String prescriptionName = "";
    private static String disease = "";
    public static ArrayList <Medicine> medicineArrayList = new ArrayList<Medicine>();

    public static String getPrescriptionName() {
        return prescriptionName;
    }

    public static void setPrescriptionName(String prescriptionName) {
        Prescription.prescriptionName = prescriptionName;
    }

    public static String getDisease() {
        return disease;
    }

    public static void setDisease(String disease) {
        Prescription.disease = disease;
    }

    public static ArrayList<Medicine> getMedicineArrayList() {
        return medicineArrayList;
    }

    public static void setMedicineArrayList(ArrayList<Medicine> medicineArrayList) {
        Prescription.medicineArrayList = medicineArrayList;
    }
}
