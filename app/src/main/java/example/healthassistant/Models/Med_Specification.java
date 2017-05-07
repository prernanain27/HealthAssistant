package example.healthassistant.Models;

import java.util.List;

/**
 * Created by Neha on 5/6/2017.
 */

public class Med_Specification {

    private int med_id;
    private int min_dose;
    private int max_dose;
    private int min_seperation;
    private List<Interferer> interfererList;

    public List<Interferer> getInterfererList() {
        return interfererList;
    }

    public void setInterfererList(List<Interferer> interfererList) {
        this.interfererList = interfererList;
    }

    public int getMed_id() {
        return med_id;
    }

    public void setMed_id(int med_id) {
        this.med_id = med_id;
    }

    public int getMin_dose() {
        return min_dose;
    }

    public void setMin_dose(int min_dose) {
        this.min_dose = min_dose;
    }

    public int getMax_dose() {
        return max_dose;
    }

    public void setMax_dose(int max_dose) {
        this.max_dose = max_dose;
    }

    public int getMin_seperation() {
        return min_seperation;
    }

    public void setMin_seperation(int min_seperation) {
        this.min_seperation = min_seperation;
    }
}
