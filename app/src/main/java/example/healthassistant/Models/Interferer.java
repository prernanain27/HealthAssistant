package example.healthassistant.Models;

import example.healthassistant.DbContract;

/**
 * Created by Neha on 5/6/2017.
 */

public class Interferer {

    public static final String[] ALL_COLUMNS_Interferer = {DbContract.DbEntryInterferer.COLUMN_ID,
                                                            DbContract.DbEntryInterferer.COLUMN_MED_ID,
                                                            DbContract.DbEntryInterferer.COLUMN_INTERFERER_ID,
                                                            DbContract.DbEntryInterferer.COLUMN_MIN_FROM,
                                                            DbContract.DbEntryInterferer.COLUMN_MIN_TO};

    private String med_id;
    private String interferer_id;
    private String minToInterferer;
    private String minFromInterferer;

    public String getMed_id() {
        return med_id;
    }

    public void setMed_id(String med_id) {
        this.med_id = med_id;
    }

    public String getInterferer_id() {
        return interferer_id;
    }

    public void setInterferer_id(String interferer_id) {
        this.interferer_id = interferer_id;
    }

    public String getMinToInterferer() {
        return minToInterferer;
    }

    public void setMinToInterferer(String minToInterferer) {
        this.minToInterferer = minToInterferer;
    }

    public String getMinFromInterferer() {
        return minFromInterferer;
    }

    public void setMinFromInterferer(String minFromInterferer) {
        this.minFromInterferer = minFromInterferer;
    }
}
