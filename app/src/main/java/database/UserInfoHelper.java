package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by srinivasan on 4/11/2016.
 */
public class UserInfoHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "UserInfo.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_USER = "CREATE TABLE "+ UserInfoContract.UserEntry.TABLE_NAME +" ("+
            UserInfoContract.UserEntry._ID + " INTEGER PRIMARY KEY,"+
            UserInfoContract.UserEntry.COLUMN_NAME_USER_NAME + TEXT_TYPE + COMMA_SEP +
            UserInfoContract.UserEntry.COLUMN_NAME_AGE + TEXT_TYPE +" )";

    private static final String SQL_DELETE_USER =
            "DROP TABLE IF EXISTS "+ UserInfoContract.UserEntry.TABLE_NAME;

    private static UserInfoHelper helperInstance;

    public static synchronized UserInfoHelper getHelperInstance(Context context){
        if(helperInstance == null){
            helperInstance = new UserInfoHelper(context);
        }
        return helperInstance;
    }

    public UserInfoHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_USER);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }
}
