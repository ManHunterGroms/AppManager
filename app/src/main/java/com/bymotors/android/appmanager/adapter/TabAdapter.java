package com.bymotors.android.appmanager.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.bymotors.android.appmanager.R;
import com.bymotors.android.appmanager.activity.MainActivity;
import com.bymotors.android.appmanager.fragment.ApplicationsFragment;

/**
 * Created by bymot on 27.06.2017.
 */

public class TabAdapter extends FragmentPagerAdapter {

    private Context mContex;

    private ApplicationsFragment applicationsFragment;

    public TabAdapter(FragmentManager fm, MainActivity mainActivity) {
        super(fm);
        this.mContex = mainActivity.getApplicationContext();
        applicationsFragment = new ApplicationsFragment();
        mainActivity.refresh = applicationsFragment;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0) {
            return applicationsFragment;
        }

        if(position == 1) {
            return new ApplicationsFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0) {
            return mContex.getString(R.string.app_tab);
        }

        if(position == 1) {
            return mContex.getString(R.string.backup_tab);
        }

        return null;
    }
}
