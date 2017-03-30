package example.healthassistant;

import android.provider.BaseColumns;

/**
 * Created by prernaa on 3/29/2017.
 */

public class DbContract {

    public static final class DbEntry implements BaseColumns {
        public static final String TABLE_NAME = "Users_login_data";
        public static final String COLUMN_USER_NAME = "username";
        public static final String COLUMN_PASSWORD = "password";
        public static final String COLUMN_EMAIL = "email";
    }

}
