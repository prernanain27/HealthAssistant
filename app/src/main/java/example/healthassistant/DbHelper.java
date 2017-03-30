package example.healthassistant;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by prernaa on 3/29/2017.
 */

public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "health.db";
    private static final int DATABASE_VERSION = 1;
    public DbHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_TABLE= "CREATE TABLE "+
                DbContract.DbEntry.TABLE_NAME + "("+
                DbContract.DbEntry._ID +" INTEGER PRIMARY KEY AUTOINCREMENT , "+
                DbContract.DbEntry.COLUMN_USER_NAME + " TEXT NOT NULL , "+
                DbContract.DbEntry.COLUMN_PASSWORD + " TEXT NOT NULL , "+
                DbContract.DbEntry.COLUMN_EMAIL + "TEXT NOT NULL "+ ");";
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ DbContract.DbEntry.TABLE_NAME);
        onCreate(db);
    }
}
