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
        public static final String TABLE_NAME = "PHR";
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
        public static final String TABLE_NAME = "prescription";
        public static final String COLUMN_PRESCRIPTION_NAME = "prescription_name";
        public static final String COLUMN_DISEASE = "disease";
        public static final String COLUMN_MED_NAME = "med_name";
        public static final String COLUMN_MED_DOSE = "med_dose";
        public static final String COLUMN_MED_TYPE = "med_type";
        public static final String COLUMN_MED_TIME = "med_time";
        public static final String COLUMN_MED_TOTAL = "med_total";
        public static final String COLUMN_DURATION = "duration";
        public static final String COLUMN_DURATION_TYPE = "duration_type";
    }

}
