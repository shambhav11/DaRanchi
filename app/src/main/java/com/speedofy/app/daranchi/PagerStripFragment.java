package com.speedofy.app.daranchi;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PagerStripFragment extends Fragment implements RecyclerViewFragment.OnFragmentInteractionListener {
    String  name="";
    String type="";

    TextView textView;
    RecyclerView dailyRecy;
    TextView textView2;

    TextView textView3;

    Button edit;
    TextView textView4;
    CustomPagerAdapter mCustomPagerAdapter;
    ViewPager mViewPager;


    public void setName(String name) {
        this.name = name;
    }

    public void setType(String branch) {
        this.type = branch;
    }

    public PagerStripFragment() {

        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment'

        View view;


        // textView=(TextView) this.getView().findViewById(R.id.textView34);

        view = inflater.inflate(R.layout.fragment_pager_strip, container, false);
        Log.e("view", "Haan maine view bnaya");
        mCustomPagerAdapter = new CustomPagerAdapter(getChildFragmentManager(), getContext());
        TabLayout tabLayout = view.findViewById(R.id.tabs);
        mViewPager = (ViewPager) view.findViewById(R.id.pager);
        mViewPager.setAdapter(mCustomPagerAdapter);
        tabLayout.setupWithViewPager(mViewPager);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference();

        return view;

    }
    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    class CustomPagerAdapter extends FragmentPagerAdapter {

        Context mContext;

        public CustomPagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            mContext = context;
        }

        @Override
        public Fragment getItem(int position) {

            // Create fragment object
            RecyclerViewFragment fragment = new RecyclerViewFragment();

            // Attach some data to the fragment
            // that we'll use to populate our fragment layouts
            Bundle args = new Bundle();
            if(type.contains("LMPYES")) {
                switch (position) {
                    case 0:

                        fragment.setType("JanYES");
                        break;
                    case 1:
                        fragment.setType("FebYES");
                        break;
                    case 2:
                        fragment.setType("MarYES");
                        break;
                    case 3:
                        fragment.setType("AprilYES");
                        break;
                    case 4:
                        fragment.setType("MayYES");
                        break;
                    case 5:
                        fragment.setType("JuneYES");
                        break;
                    case 6:
                        fragment.setType("JulyYES");
                        break;
                    case 7:
                        fragment.setType("AugYES");
                        break;
                    case 8:
                        fragment.setType("SeptYES");
                        break;
                    case 9:
                        fragment.setType("OctYES");
                        break;
                    case 10:
                        fragment.setType("NovYES");
                        break;
                    case 11:
                        fragment.setType("DecYES");
                        break;
                    default:
                        fragment.setType("ALL");
                }
            }
            else if(type.contains("LMP")) {
                switch (position) {
                    case 0:

                        fragment.setType("Jan");
                        break;
                    case 1:
                        fragment.setType("Feb");
                        break;
                    case 2:
                        fragment.setType("Mar");
                        break;
                    case 3:
                        fragment.setType("April");
                        break;
                    case 4:
                        fragment.setType("May");
                        break;
                    case 5:
                        fragment.setType("June");
                        break;
                    case 6:
                        fragment.setType("July");
                        break;
                    case 7:
                        fragment.setType("Aug");
                        break;
                    case 8:
                        fragment.setType("Sept");
                        break;
                    case 9:
                        fragment.setType("Oct");
                        break;
                    case 10:
                        fragment.setType("Nov");
                        break;
                    case 11:
                        fragment.setType("Dec");
                        break;
                    default:
                        fragment.setType("ALL");
                }
            }
            else if(type.contains("Month"))
            {
                switch (position) {
                    case 0:

                        fragment.setType("1 Month");
                        break;
                    case 1:
                        fragment.setType("2 Months");
                        break;
                    case 2:
                        fragment.setType("3 Months");
                        break;
                }
            }
            args.putInt("page_position", position + 1);
            args.putString("name", name);
            args.putString("month", type);

            // Set the arguments on the fragment
            // that will be fetched in the
            // DemoFragment@onCreateView

            return fragment;
        }

        @Override
        public int getCount() {
            if(type.contains("LMP"))
            return 12;
            else
                return 3;
        }


        @Override
        public CharSequence getPageTitle(int position) {
            if(type.contains("LMP")) {
                switch (position) {
                    case 0:

                        return "Jan";
                    case 1:
                        return "Feb";
                    case 2:
                        return "Mar";
                    case 3:
                        return "Apr";
                    case 4:
                        return "May";
                    case 5:
                        return "June";
                    case 6:
                        return "July";
                    case 7:
                        return "August";
                    case 8:
                        return "Sept";
                    case 9:
                        return "Oct";
                    case 10:
                        return "Nov";
                    case 11:
                        return "Dec";
                    default:
                        return null;
                }
            }
            else
            {
                switch (position) {
                    case 0:

                        return "1 Month";

                    case 1:
                        return "2 Months";

                    case 2:
                        return "3 Months";

                    default:
                        return null;
                }
            }
        }

    }
}