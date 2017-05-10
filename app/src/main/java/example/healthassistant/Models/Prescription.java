package example.healthassistant.Models;

import java.util.ArrayList;

/**
 * Created by ameethakkar on 4/2/17.
 */

public class Prescription {
    private  String prescriptionName = "";
    private  String disease = "";
    public  ArrayList <Medicine> medicineArrayList = new ArrayList<Medicine>();
    public static ArrayList<Medicine> medStaticArrayList = new ArrayList<>();

    public  String getPrescriptionName() {
        return prescriptionName;
    }

    public  void setPrescriptionName(String prescriptionName) {
        this.prescriptionName = prescriptionName;
    }

    public static ArrayList<Medicine> getMedStaticArrayList() {
        return medStaticArrayList;
    }

    public static void setMedStaticArrayList(ArrayList<Medicine> medStaticArrayList) {
        Prescription.medStaticArrayList = medStaticArrayList;
    }

    public  String getDisease() {
        return disease;
    }

    public  void setDisease(String disease) {
        this.disease = disease;
    }

    public  ArrayList<Medicine> getMedicineArrayList() {
        return medicineArrayList;
    }

    public  void setMedicineArrayList(ArrayList<Medicine> medicineArrayList) {
        this.medicineArrayList = medicineArrayList;
    }
}
