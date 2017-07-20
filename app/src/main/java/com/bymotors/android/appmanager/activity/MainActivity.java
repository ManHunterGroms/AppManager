package com.bymotors.android.appmanager.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.bymotors.android.appmanager.R;
import com.bymotors.android.appmanager.adapter.TabAdapter;
import com.bymotors.android.appmanager.utils.RefreshList;
import com.bymotors.android.appmanager.utils.SortType;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private TabAdapter mTabAdapter;

    public RefreshList refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.app_name);
        setSupportActionBar(mToolbar);

        mTabAdapter = new TabAdapter(getSupportFragmentManager(), this);

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mTabAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        item.setChecked(true);

        int id = item.getItemId();

        switch (id) {
            case R.id.action_sort_name:
                refresh.refresh(SortType.NAME);
                break;

            case R.id.action_sort_date:
                refresh.refresh(SortType.DATE);
                break;

            case R.id.action_sort_size:
                refresh.refresh(SortType.SIZE);
                break;

            case R.id.action_sort_none:
                refresh.refresh(SortType.NONE);
                break;
        }

        return true;
    }

}
