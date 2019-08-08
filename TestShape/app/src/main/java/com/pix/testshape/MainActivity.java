package com.pix.testshape;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * 展示shape的Demo
 */
public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ShapePagerAdapter mAdapter;
    private List<ShapeModule> shapeList = new ArrayList();
    {
        shapeList.add(new ShapeModule(R.layout.layout_shape_demo1,"corners & solid"));
        shapeList.add(new ShapeModule(R.layout.layout_shape_gradient,"gradient"));
        shapeList.add(new ShapeModule(R.layout.layout_shape_stroke,"stroke"));
        shapeList.add(new ShapeModule(R.layout.layout_shape_rectangle,"rectangle"));
        shapeList.add(new ShapeModule(R.layout.layout_shape_oval,"oval"));
        shapeList.add(new ShapeModule(R.layout.layout_shape_ring,"ring"));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        mAdapter = new ShapePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    public class ShapePagerAdapter extends FragmentPagerAdapter {

        public ShapePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return shapeList.size();
        }

        @Override
        public Fragment getItem(int position) {
            return ShapeFragment.newInstance(shapeList.get(position));
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return shapeList.get(position).title;
        }
    }


}
