package com.bymotors.android.appmanager.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.bymotors.android.appmanager.R;
import com.bymotors.android.appmanager.adapter.ApplicationAdapter;
import com.bymotors.android.appmanager.adapter.ApplicationAdapter.ApplicationOnClickHandler;
import com.bymotors.android.appmanager.model.AppModel;
import com.bymotors.android.appmanager.utils.AppFetch;
import com.bymotors.android.appmanager.utils.RefreshList;
import com.bymotors.android.appmanager.utils.SortType;

import java.util.List;

public class ApplicationsFragment extends Fragment implements ApplicationOnClickHandler, RefreshList {

    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;

    private RecyclerView.LayoutManager mLayoutManager;
    private ApplicationAdapter mAppAdapter;
    private AppFetch mAppFetch;

    Context mContext;

    public ApplicationsFragment() { }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_applications, container,false);

        mContext = getContext();

        mProgressBar = (ProgressBar) view.findViewById(R.id.progress_bar);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.apps_recycle_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);

        refresh(SortType.NONE);

        return view;
    }

    @Override
    public void onClickItem(AppModel appModel) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + appModel.getDesc()));
        startActivity(intent);
    }

    @Override
    public void refresh(SortType sort) {
        ApplicationsAsyncTask appAsyncTask = new ApplicationsAsyncTask(sort);
        appAsyncTask.execute();
    }


    public class ApplicationsAsyncTask extends AsyncTask<Void, Void, List<AppModel>> {

        private SortType mSortType;

        public ApplicationsAsyncTask(SortType mSortType) {
            this.mSortType = mSortType;
        }

        @Override
        protected void onPreExecute() {
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<AppModel> doInBackground(Void... params) {
            mAppFetch = new AppFetch(mContext);

            return mAppFetch.getApplicationList(mSortType);
        }

        @Override
        protected void onPostExecute(List<AppModel> appModelList) {
            mAppAdapter = new ApplicationAdapter(mContext, appModelList, ApplicationsFragment.this);
            mRecyclerView.setAdapter(mAppAdapter);

            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }
}
