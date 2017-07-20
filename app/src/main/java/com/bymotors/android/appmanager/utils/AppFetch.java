package com.bymotors.android.appmanager.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;

import com.bymotors.android.appmanager.model.AppModel;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by bymot on 27.06.2017.
 */

public class AppFetch {

    private Context mContext;

    private Intent mAppsIntent;
    private PackageManager mPackageManager;


    public AppFetch(Context mContext) {
        this.mContext = mContext;

        mPackageManager = mContext.getPackageManager();

        mAppsIntent = new Intent(Intent.ACTION_MAIN, null); //try without null
        mAppsIntent.addCategory(Intent.CATEGORY_LAUNCHER);
    }

    public List<AppModel> getApplicationList(SortType sortType) {
        List<ResolveInfo> appList = sortApplications(sortType);

        List<AppModel> appModelList = new ArrayList<>();

        for (ResolveInfo appInfo : appList) {
            AppModel appModel = new AppModel();

            appModel.setName(appInfo.loadLabel(mPackageManager).toString());
            appModel.setDesc(appInfo.activityInfo.packageName);
            appModel.setIcon(((BitmapDrawable) appInfo.loadIcon(mPackageManager)).getBitmap());
            appModel.setSize(new File(appInfo.activityInfo.applicationInfo.publicSourceDir).length());
            try {
                appModel.setDate(new Date(mPackageManager.getPackageInfo(appInfo.activityInfo.packageName, 0).firstInstallTime));
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            appModelList.add(appModel);
        }

        return appModelList;
    }

    private List<ResolveInfo> sortApplications(SortType sortType) {
        final PackageManager pm = mContext.getPackageManager();

        List<ResolveInfo> appList = pm.queryIntentActivities(mAppsIntent, 0);

        Comparator<ResolveInfo> comparator = null;

        if (sortType == SortType.NAME) {
            comparator = new ResolveInfo.DisplayNameComparator(mContext.getPackageManager());
        }

        if (sortType == SortType.DATE) {
            comparator = new Comparator<ResolveInfo>() {
                @Override
                public int compare(ResolveInfo ri1, ResolveInfo ri2) {
                    String packageName1 = ri1.activityInfo.packageName;
                    String packageName2 = ri2.activityInfo.packageName;

                    try {
                        long time1 = pm
                                .getPackageInfo(packageName1, 0)
                                .firstInstallTime;

                        long time2 = pm
                                .getPackageInfo(packageName2, 0)
                                .firstInstallTime;

                        return (int) (time2 - time1);
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                        return 0;
                    }
                }
            };
        }

        if (sortType == SortType.SIZE) {
            comparator = new Comparator<ResolveInfo>() {
                @Override
                public int compare(ResolveInfo ri1, ResolveInfo ri2) {
                    File file1 = new File(ri1.activityInfo.applicationInfo.publicSourceDir);
                    File file2 = new File(ri2.activityInfo.applicationInfo.publicSourceDir);

                    long fileSize1 = file1.length();
                    long fileSize2 = file2.length();

                    return (int) (fileSize2 - fileSize1);
                }
            };
        }

        if(comparator != null) {
            Collections.sort(appList, comparator);
        }

        return appList;
    }
}
