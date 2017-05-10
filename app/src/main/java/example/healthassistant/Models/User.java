package example.healthassistant.Models;

import java.util.HashMap;

import example.healthassistant.DbContract;

/**
 * Created by prernaa on 4/1/2017.
 */

public class User {

    public static final String WAKE_UP_TIME ="Wake up Time";
    public static final String BREAKFAST_TIME = "Breakfast Time";
    public static final String LUNCH_TIME ="Lunch Time";
    public static final String GYM_TIME ="Gym Time";
    public static final String DINNER_TIME ="Dinner Time";
    public static final String SLEEP_TIME ="Sleep Time";

    public static final String[] ALL_COLUMNS = {
            DbContract.DbEntryPHR.COLUMN_ID,
            DbContract.DbEntryPHR.COLUMN_ADDRESS,
            DbContract.DbEntryPHR.COLUMN_BLOODTYPE,
            DbContract.DbEntryPHR.COLUMN_BLOOD_SIGN,
            DbContract.DbEntryPHR.COLUMN_BREAKFAST_TIME,
            DbContract.DbEntryPHR.COLUMN_CARETAKER_CONTACT,
            DbContract.DbEntryPHR.COLUMN_DINNER_TIME,
            DbContract.DbEntryPHR.COLUMN_DOB,
            DbContract.DbEntryPHR.COLUMN_EMERGENCT_CONTACT,
            DbContract.DbEntryPHR.COLUMN_EYE_SIGHT,
            DbContract.DbEntryPHR.COLUMN_EYE_SIGN,
            DbContract.DbEntryPHR.COLUMN_Email,
            DbContract.DbEntryPHR.COLUMN_GYM_TIME,
            DbContract.DbEntryPHR.COLUMN_HEIGHT_FEET,
            DbContract.DbEntryPHR.COLUMN_HEIGHT_INCHES,
            DbContract.DbEntryPHR.COLUMN_LUNCH_TIME,
            DbContract.DbEntryPHR.COLUMN_NAME,
            DbContract.DbEntryPHR.COLUMN_PRIMARY_CONTACT,
            DbContract.DbEntryPHR.COLUMN_SEX,
            DbContract.DbEntryPHR.COLUMN_WAKE_UP_TIME

    };
    private static String email = "";
    private static String name = "";
    private static String sex = "";
    private static String dob = "";
    private static String address = "";
    private static String primary_contact = "";
    private static String emergency_contact = "";
    private static String care_taker_contact = " ";
    private static String boold_type= "";
    private static String blood_sign = "";
    private static String height_feet = "";
    private static String height_inches= "";
    private static String eye_sign = "";
    private static String eye_sight= "";
    private static String wake_up_time = "";
    private static String breakfast_time = "";
    private static String lunch_time = "";
    private static String gym_time = "";
    private static String dinner_time = "";
    private static String sleep_time = "";

    public static final String[] USERPREFCOLUMNNAMES ={DbContract.DbEntryPHR.COLUMN_WAKE_UP_TIME,DbContract.DbEntryPHR.COLUMN_BREAKFAST_TIME,
                                                        DbContract.DbEntryPHR.COLUMN_GYM_TIME,
                                                        DbContract.DbEntryPHR.COLUMN_LUNCH_TIME,
                                                        DbContract.DbEntryPHR.COLUMN_DINNER_TIME};
    private HashMap<String,String> userPreferences;

    public HashMap<String, String> getUserPreferences() {

        return userPreferences;
    }

    public void setUserPreferences(HashMap<String, String> userPreferences) {
        this.userPreferences = userPreferences;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        User.email = email;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        User.name = name;
    }

    public static String getSex() {
        return sex;
    }

    public static void setSex(String sex) {
        User.sex = sex;
    }

    public static String getDob() {
        return dob;
    }

    public static void setDob(String dob) {
        User.dob = dob;
    }

    public static String getAddress() {
        return address;
    }

    public static void setAddress(String address) {
        User.address = address;
    }

    public static String getPrimary_contact() {
        return primary_contact;
    }

    public static void setPrimary_contact(String primary_contact) {
        User.primary_contact = primary_contact;
    }

    public static String getEmergency_contact() {
        return emergency_contact;
    }

    public static void setEmergency_contact(String emergency_contact) {
        User.emergency_contact = emergency_contact;
    }

    public static String getCare_taker_contact() {
        return care_taker_contact;
    }

    public static void setCare_taker_contact(String care_taker_contact) {
        User.care_taker_contact = care_taker_contact;
    }

    public static String getBoold_type() {
        return boold_type;
    }

    public static void setBoold_type(String boold_type) {
        User.boold_type = boold_type;
    }

    public static String getBlood_sign() {
        return blood_sign;
    }

    public static void setBlood_sign(String blood_sign) {
        User.blood_sign = blood_sign;
    }

    public static String getHeight_feet() {
        return height_feet;
    }

    public static void setHeight_feet(String height_feet) {
        User.height_feet = height_feet;
    }

    public static String getHeight_inches() {
        return height_inches;
    }

    public static void setHeight_inches(String height_inches) {
        User.height_inches = height_inches;
    }

    public static String getEye_sign() {
        return eye_sign;
    }

    public static void setEye_sign(String eye_sign) {
        User.eye_sign = eye_sign;
    }

    public static String getEye_sight() {
        return eye_sight;
    }

    public static void setEye_sight(String eye_sight) {
        User.eye_sight = eye_sight;
    }

    public static String getWake_up_time() {
        return wake_up_time;
    }

    public static void setWake_up_time(String wake_up_time) {
        User.wake_up_time = wake_up_time;
    }

    public static String getBreakfast_time() {
        return breakfast_time;
    }

    public static void setBreakfast_time(String breakfast_time) {
        User.breakfast_time = breakfast_time;
    }

    public static String getLunch_time() {
        return lunch_time;
    }

    public static void setLunch_time(String lunch_time) {
        User.lunch_time = lunch_time;
    }

    public static String getGym_time() {
        return gym_time;
    }

    public static void setGym_time(String gym_time) {
        User.gym_time = gym_time;
    }

    public static String getDinner_time() {
        return dinner_time;
    }

    public static void setDinner_time(String dinner_time) {
        User.dinner_time = dinner_time;
    }

    public static String getSleep_time() {
        return sleep_time;
    }

    public static void setSleep_time(String sleep_time) {
        User.sleep_time = sleep_time;
    }
}
