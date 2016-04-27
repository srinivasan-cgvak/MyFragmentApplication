package utils;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

/**
 * Created by srinivasan on 3/31/2016.
 */
public class UtilityClass {

    public static void showToastMessage(Context context,String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }

    public boolean isExternalStorageWritable(){
        String storageState = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(storageState)){//To check whether the external storage support both read and write
            return true;
        }
        return false;
    }

    public boolean isExternalStorageReadable(){
        String storageState = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(storageState) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(storageState)){//To check at least it support read
            return true;
        }

        return false;
    }

    public void checkFreeSpaceInMemory(){
      long a = Environment.getExternalStorageDirectory().getFreeSpace();


    }
}
