package com.speedofy.app.daranchi;

import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PagerStripActivity extends AppCompatActivity implements RecyclerViewFragment.OnFragmentInteractionListener {
    private ViewPager viewPager;
    private SectionPageAdapter sectionPageAdapter;
    String type;
    EditText searchBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager_strip);
        sectionPageAdapter=new SectionPageAdapter(getSupportFragmentManager());
        viewPager=findViewById(R.id.viewpager_on_click);
        ActionBar actionBar = getSupportActionBar();
        TextView textView=findViewById(R.id.title);
        searchBar=findViewById(R.id.search_bar);
        textView.setVisibility(View.VISIBLE);
        textView.setText("Patients");
        type=getIntent().getStringExtra("Type");
        findViewById(R.id.drawer_open).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.right_drawer_open).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(searchBar.getVisibility()==View.VISIBLE)
                {
                    searchBar.setVisibility(View.GONE);
                    findViewById(R.id.appbarr).setVisibility(View.VISIBLE);
                    sectionPageAdapter.removeFragment(6);
                    viewPager.setAdapter(sectionPageAdapter);

                }
                else
                {
                    searchBar.setVisibility(View.VISIBLE);
                    findViewById(R.id.appbarr).setVisibility(View.GONE);
                    final RecyclerViewFragment searchFragment=new RecyclerViewFragment();
                    //  searchFragmentAll.set(this);
                    if(type.contains("YES"))
                    searchFragment.setType("AllYES");
                    else
                        searchFragment.setType("All");

                    sectionPageAdapter.addFragment(searchFragment,"Search Results");
                    viewPager.setAdapter(sectionPageAdapter);
                    viewPager.setCurrentItem(sectionPageAdapter.getCount(),true);
                    searchBar.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            searchFragment.setSearchText(s.toString());
                            viewPager.getAdapter().getItemPosition(searchFragment);
                        }
                    });

                }
            }
        });




        TabLayout tabLayout =findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        if(type.contains("YES")) {
            RecyclerViewFragment searchFragmentbce = new RecyclerViewFragment();
            //  searchFragmentAll.set(this);
            searchFragmentbce.setType("AllYES");
            sectionPageAdapter.addFragment(searchFragmentbce, "All Patients");
            RecyclerViewFragment searchFragment2daughters = new RecyclerViewFragment();
            //  searchFragmentAll.set(this);
            searchFragment2daughters.setType("2 daughtersYES");
            sectionPageAdapter.addFragment(searchFragment2daughters, "> 2 daughters");
            RecyclerViewFragment searchFragmentMTP = new RecyclerViewFragment();
            //  searchFragmentAll.set(this);
            searchFragmentMTP.setType("MTPYES");
            sectionPageAdapter.addFragment(searchFragmentMTP, "Indication of MTP");
            RecyclerViewFragment searchFragmentANC = new RecyclerViewFragment();
            //  searchFragmentAll.set(this);
            searchFragmentANC.setType("ANCYES");
            sectionPageAdapter.addFragment(searchFragmentANC, "Undergoing ANC Treatment");
            PagerStripFragment searchFragmentLMP = new PagerStripFragment();
            //  searchFragmentAll.set(this);
            searchFragmentLMP.setType("LMPYES");
            sectionPageAdapter.addFragment(searchFragmentLMP, "LMP");
            if(!type.contains("nomonth")) {
                PagerStripFragment searchFragmentMonth = new PagerStripFragment();
                //  searchFragmentAll.set(this);
                searchFragmentMonth.setType("MonthYES");
                sectionPageAdapter.addFragment(searchFragmentMonth, "Patients Under Suspicion");
            }
            viewPager.setAdapter(sectionPageAdapter);
        }
        else {
            RecyclerViewFragment searchFragmentbce = new RecyclerViewFragment();
            //  searchFragmentAll.set(this);
            searchFragmentbce.setType("All");
            sectionPageAdapter.addFragment(searchFragmentbce, "All Patients");
            RecyclerViewFragment searchFragment2daughters = new RecyclerViewFragment();
            //  searchFragmentAll.set(this);
            searchFragment2daughters.setType("2 daughters");
            sectionPageAdapter.addFragment(searchFragment2daughters, "> 2 daughters");
            RecyclerViewFragment searchFragmentMTP = new RecyclerViewFragment();
            //  searchFragmentAll.set(this);
            searchFragmentMTP.setType("MTP");
            sectionPageAdapter.addFragment(searchFragmentMTP, "Indication of MTP");
            RecyclerViewFragment searchFragmentANC = new RecyclerViewFragment();
            //  searchFragmentAll.set(this);
            searchFragmentANC.setType("ANC");
            sectionPageAdapter.addFragment(searchFragmentANC, "Undergoing ANC Treatment");
            PagerStripFragment searchFragmentLMP = new PagerStripFragment();
            //  searchFragmentAll.set(this);
            searchFragmentLMP.setType("LMP");
            sectionPageAdapter.addFragment(searchFragmentLMP, "LMP");
            PagerStripFragment searchFragmentMonth = new PagerStripFragment();
            //  searchFragmentAll.set(this);
            searchFragmentMonth.setType("Month");
            sectionPageAdapter.addFragment(searchFragmentMonth, "Patients Under Suspicion");

            viewPager.setAdapter(sectionPageAdapter);

        }




    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public static class SectionPageAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList=new ArrayList<>();
        private final List<String> mFragmentTitleList=new ArrayList<>();

        public void addFragment(Fragment fragment,String title)
        {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        public void removeFragment(int position)
        {
            mFragmentList.remove(position);
            mFragmentTitleList.remove(position);
        }

        public SectionPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public int getItemPosition(Object obj)
        {
            RecyclerViewFragment f = (RecyclerViewFragment ) obj;
            if (f != null) {
                Log.e("TAGGGGGG","Fragment update krne ko bola");
                f.update();
            }
            return super.getItemPosition(obj);
        }
    }
}
