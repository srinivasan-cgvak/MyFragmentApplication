package database;

import android.provider.BaseColumns;

/**
 * Created by srinivasan on 4/11/2016.
 */
public class UserInfoContract {

    public UserInfoContract() {
    }

    public static abstract class UserEntry implements BaseColumns{
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_NAME_ENTRY_ID = "entryid";
        public static final String COLUMN_NAME_USER_NAME = "username";
        public static final String COLUMN_NAME_AGE = "age";
    }
}
