package com.pol.poletech;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Activity_Wellcome_PoleTech extends AppCompatActivity {

    private ViewPager view_pager;
    private LinearLayout dotsLayout;
    private Button btnBack, btnNext;
    private int[] layouts;
    private TextView[] dots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wellcome_poletech);


        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        view_pager = (ViewPager) findViewById(R.id.view_pager);
        dotsLayout = (LinearLayout) findViewById(R.id.dotsLayout);
        btnNext = (Button) findViewById(R.id.btnNext);
        btnBack = (Button) findViewById(R.id.btnBack);

        layouts = new int[]{
                R.layout.welcome_slide1,
                R.layout.welcome_slide2,
                R.layout.welcome_slide3
        };

        AddBottomDots(0);

        btnBack.setVisibility(View.GONE);

        MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter();
        view_pager.setAdapter(myViewPagerAdapter);
        view_pager.addOnPageChangeListener(onPageChangeListener);

        btnBack.setOnClickListener(btnBackOnClickListener);
        btnNext.setOnClickListener(btnNextOnClickListener);

    }

    View.OnClickListener btnBackOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int currentPage = view_pager.getCurrentItem();
            if (currentPage < layouts.length) {
                view_pager.setCurrentItem(currentPage - 1);
            }
        }
    };

    View.OnClickListener btnNextOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int currentPage = view_pager.getCurrentItem();
            if (currentPage < layouts.length - 1) {
                view_pager.setCurrentItem(currentPage + 1);
            } else {
                SharedPreferences wellcome = getSharedPreferences("polTech", 0);
                SharedPreferences.Editor editor = wellcome.edit();
                editor.putBoolean("statusWellcome?", true);
                editor.commit();
                Intent intent = new Intent(Activity_Wellcome_PoleTech.this, Activity_Login_PoleTech.class);
                startActivity(intent);
                finish();
            }
        }
    };

    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            AddBottomDots(position);

            //First Page
            if (position == 0) {
                btnBack.setVisibility(View.GONE);
            } else {
                btnBack.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    void AddBottomDots(int currentPage) {
        dots = new TextView[layouts.length];

        int[] colorsActive = getResources().getIntArray(R.array.array_light_active);
        int[] colorsInActive = getResources().getIntArray(R.array.array_dark_inactive);

        dotsLayout.removeAllViews();

        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInActive[i]);
            dotsLayout.addView(dots[i]);
        }

        dots[currentPage].setTextColor(colorsActive[currentPage]);
    }


    class MyViewPagerAdapter extends PagerAdapter {

        LayoutInflater layoutInflater;

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }

    }
}
