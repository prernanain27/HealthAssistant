package example.healthassistant.Models;

import java.util.List;

import example.healthassistant.DbContract;

/**
 * Created by Neha on 5/6/2017.
 */

public class Med_Specification {

    public static final String[] ALL_COLUMNS_MEDSPEC= {DbContract.DbEntryMed.COLUMN_ID
                                                        ,DbContract.DbEntryMed.COLUMN_MED_NAME
                                                        ,DbContract.DbEntryMed.COLUMN_MIN_DOSE,
                                                        DbContract.DbEntryMed.COLUMN_MAX_DOSE,
                                                        DbContract.DbEntryMed.COLUMN_SEPARATION};
    private String med_id;
    private String min_dose;
    private String max_dose;
    private String min_seperation;
    private String med_name;

    public String getMed_name() {
        return med_name;
    }

    public void setMed_name(String med_name) {
        this.med_name = med_name;
    }

    public static String[] getAllColumnsMedspec() {

        return ALL_COLUMNS_MEDSPEC;
    }

    private List<Interferer> interfererList;

    public List<Interferer> getInterfererList() {
        return interfererList;
    }

    public void setInterfererList(List<Interferer> interfererList) {
        this.interfererList = interfererList;
    }

    public String getMed_id() {
        return med_id;
    }

    public void setMed_id(String med_id) {
        this.med_id = med_id;
    }

    public String getMin_dose() {
        return min_dose;
    }

    public void setMin_dose(String min_dose) {
        this.min_dose = min_dose;
    }

    public String getMax_dose() {
        return max_dose;
    }

    public void setMax_dose(String max_dose) {
        this.max_dose = max_dose;
    }

    public String getMin_seperation() {
        return min_seperation;
    }

    public void setMin_seperation(String min_seperation) {
        this.min_seperation = min_seperation;
    }
}
