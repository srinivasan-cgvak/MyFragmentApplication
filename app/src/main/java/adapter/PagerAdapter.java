package adapter;



import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.myfragmentapplication_test.DetailFragment;
import com.myfragmentapplication_test.ItemFragment;

/**
 * Created by srinivasan on 4/5/2016.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {

    int tabCount;

    public PagerAdapter(FragmentManager fm,int tabcount) {
        super(fm);
        this.tabCount = tabcount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                ItemFragment itemFragment = new ItemFragment();
                return itemFragment;
            case 1:
                DetailFragment detailFragment = new DetailFragment();
                return detailFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
