package com.pix.testpropertyanimation.test;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewStub;

import com.pix.testpropertyanimation.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TestSampleActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private SamplePagerAdapter mAdapter;
    private List<SamplePagerModule> dataList = new ArrayList<>();
    {
        dataList.add(new SamplePagerModule("transX/Y",R.layout.layout_translation_sample,R.layout.layout_translation_sample));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_sample);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        mAdapter = new SamplePagerAdapter(getSupportFragmentManager(),dataList);
        viewPager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    // 每页module数据
    public class SamplePagerModule implements Serializable {
        public SamplePagerModule(String tabTitle,int sample1Res, int sample2Res) {
            this.tabTitle = tabTitle;
            this.sample1Res = sample1Res;
            this.sample2Res = sample2Res;
        }

        public int sample1Res;
        public int sample2Res;
        public String tabTitle;
    }

    // 每页的adapter
    private class SamplePagerAdapter extends FragmentPagerAdapter {
        private List<SamplePagerModule> mList;
        public SamplePagerAdapter(FragmentManager fm, List<SamplePagerModule> list) {
            super(fm);
            this.mList = list;
        }

        @Override
        public Fragment getItem(int position) {
            return SamplePagerFragment.newInstance(mList.get(position));
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mList.get(position).tabTitle;
        }
    }




}
