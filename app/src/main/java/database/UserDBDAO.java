package database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by srinivasan on 4/11/2016.
 */
public class UserDBDAO {
    protected SQLiteDatabase database;
    private UserInfoHelper userInfoHelper;
    private Context mContext;

    public UserDBDAO(Context mContext) {
        this.mContext = mContext;
        userInfoHelper = UserInfoHelper.getHelperInstance(mContext);
        open();
    }

    public void open() throws SQLException{
        if(userInfoHelper == null){
            userInfoHelper = UserInfoHelper.getHelperInstance(mContext);
        }
        database = userInfoHelper.getWritableDatabase();
    }
}
