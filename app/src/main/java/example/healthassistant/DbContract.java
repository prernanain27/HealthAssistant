package example.healthassistant;

import android.provider.BaseColumns;

/**
 * Created by prernaa on 3/29/2017.
 */

public class DbContract {

    public static final class DbEntry implements BaseColumns {
        public static final String TABLE_NAME = "Users_login_data";
        public static final String COLUMN_PASSWORD = "password";
        public static final String COLUMN_EMAIL = "email";
    }
    public static final class DbEntryPHR implements BaseColumns {
        public static final String TABLE_NAME = "AddPHRActivity";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_Email = "email";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_SEX = "sex";
        public static final String COLUMN_DOB = "dob";
        public static final String COLUMN_ADDRESS = "address";
        public static final String COLUMN_PRIMARY_CONTACT = "primary_contact";
        public static final String COLUMN_EMERGENCT_CONTACT= "emergency_contact";
        public static final String COLUMN_CARETAKER_CONTACT= "caretaker_contact";
        public static final String COLUMN_BLOODTYPE = "blood_type";
        public static final String COLUMN_BLOOD_SIGN = "blood_sign";
        public static final String COLUMN_HEIGHT_FEET = "height_feet";
        public static final String COLUMN_HEIGHT_INCHES = "height_inches";
        public static final String COLUMN_EYE_SIGN = "eye_sign";
        public static final String COLUMN_EYE_SIGHT = "eye_sight";
        public static final String COLUMN_WAKE_UP_TIME = "wake_up_time";
        public static final String COLUMN_BREAKFAST_TIME= "breakfast_time";
        public static final String COLUMN_LUNCH_TIME = "lunch_time";
        public static final String COLUMN_GYM_TIME = "gym_time";
        public static final String COLUMN_DINNER_TIME = "dinner_time";
        //public static final String COLUMN_SLEEP_TIME = "sleep_time";
    }
    public static final class DbEntryPrescription implements BaseColumns {
        public static final String TABLE_NAME = "Prescription";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_PRESCRIPTION_NAME = "prescription_name";
        public static final String COLUMN_DISEASE = "disease";
        public static final String COLUMN_MED_NAME = "med_name";
        public static final String COLUMN_MED_DOSE = "med_dose";
        public static final String COLUMN_MED_TYPE = "med_type";
        public static final String COLUMN_MED_TIME_BB = "med_time_BB";
        public static final String COLUMN_MED_TIME_AB = "med_time_AB";
        public static final String COLUMN_MED_TIME_BL = "med_time_BL";
        public static final String COLUMN_MED_TIME_AL = "med_time_AL";
        public static final String COLUMN_MED_TIME_BD = "med_time_BD";
        public static final String COLUMN_MED_TIME_AD = "med_time_AD";
        public static final String COLUMN_DURATION = "duration";
        public static final String COLUMN_DURATION_TYPE = "duration_type";
//        public static final String COLUMN_MED_TOTAL = "med_total";
    }

    public static final class DbEntryMed implements BaseColumns{
        public static final String TABLE_NAME = "Med_Specification";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_MED_NAME = "med_name";
        public static final String COLUMN_MIN_DOSE = "min_dose";
        public static final String COLUMN_MAX_DOSE = "max_dose";
        public static final String COLUMN_SEPARATION = "separation";
     //   public static final String COLUMN_INTERFERENCE = "interference";

    }

    public static final class DbEntryInterferer implements BaseColumns{
        public static final String TABLE_NAME = "Med_Interferer";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_MED_ID = "med_id";
        public static final String COLUMN_INTERFERER_ID = "interferer_id";
        public static final String COLUMN_MIN_FROM = "min_from";
        public static final String COLUMN_MIN_TO = "min_to";

    }

    public static final class DbEntryAppointment implements BaseColumns{
        public static final String TABLE_NAME = "Doc_Appointment";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_DOC_NAME = "doc_name";
        public static final String COLUMN_DOC_CONTACT = "doc_contact";
        public static final String COLUMN_APPOINT_DATE = "appointment_date";
        public static final String COLUMN_APPOINT_TIME = "appointment_time";
        public static final String COLUMN_APPOINT_DESCRIPTION = "appointment_description";


    }

    public static final class DbEntryMed_Schedule implements BaseColumns {
        public static final String TABLE_NAME = "MED_SCHEDULE";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_TIME = "med_time";
        public static final String COLUMN_MED_NAME = "med_name";
        public static final String COLUMN_MED_DURATION = "med_duration";
        public static final String COLUMN_MED_DOSE = "med_dose";
        public static final String COLUMN_DAYS_LAPSED = "med_days_lapsed";

    }

    public static final class DbEntryUser_DataAnalysis implements BaseColumns {
        public static final String TABLE_NAME = "USER_DATA_ANALYSIS";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_DOSE_DATE = "dose_date";
        public static final String COLUMN_DOSE_TIME = "dose_time";
        public static final String COLUMN_MED_LIST = "med_list";
        public static final String COLUMN_MED_IS_TAKEN = "med_is_taken";

    }
}
