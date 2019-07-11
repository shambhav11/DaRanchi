package com.speedofy.app.daranchi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecyclerViewFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RecyclerViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecyclerViewFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String type,searchText;
    List<RecyclerActivityModel> recyclerActivityModels;
    RecyclerActivityAdapter recyclerActivityAdapter;
    Activity activity;
    private String mParam2;
    int flag;
    String firstData,secondData,verificationData;

    public void setType(String type) {
        this.type = type;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    private OnFragmentInteractionListener mListener;

    public RecyclerViewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecyclerViewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecyclerViewFragment newInstance(String param1, String param2) {
        RecyclerViewFragment fragment = new RecyclerViewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_recycler_view, container, false);
        final RecyclerView recyclerView=view.findViewById(R.id.item_recy);
        recyclerActivityModels=new ArrayList<>();
        activity=getActivity();
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        if(type.contains("All"))
        {
            FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
            DatabaseReference databaseReference=firebaseDatabase.getReference();
            recyclerActivityModels=new ArrayList<>();
            databaseReference.child("Patients").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                    {
                        firstData=dataSnapshot1.getKey();
                        for(DataSnapshot snapshot: dataSnapshot1.getChildren())
                        {
                            for(DataSnapshot snapshot1 : snapshot.getChildren()) {
                                if (snapshot1.getKey().contains("Clinic")) {
                                    secondData = snapshot1.getValue().toString();
                                }
                                if (snapshot1.getKey().equals("LMP")) {
                                    verificationData = snapshot1.getValue().toString();
                                }
                            }

                        }
                        recyclerActivityModels.add(new RecyclerActivityModel(firstData, "",verificationData, type));

                    }
                    if(type.contains("YES"))
                        recyclerActivityAdapter=new RecyclerActivityAdapter(recyclerActivityModels,type,1,getActivity(),activity);
                    else
                        recyclerActivityAdapter=new RecyclerActivityAdapter(recyclerActivityModels,type,0,getActivity(),activity);

                    view.findViewById(R.id.indeterminateBar).setVisibility(View.GONE);

                    recyclerView.setAdapter(recyclerActivityAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }
            else if(type.contains("2 daughters")) {
               FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = firebaseDatabase.getReference();
                recyclerActivityModels=new ArrayList<>();
                databaseReference.child("Patients").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            firstData = dataSnapshot1.getKey();
                            flag=0;
                            Log.e("2 beti"," Mai loop mei ghum rha hun");
                            for (DataSnapshot snapshot : dataSnapshot1.getChildren()) {
                                for(DataSnapshot snapshot1 : snapshot.getChildren()) {
                                    if (snapshot1.getKey().contains("Daughters")) {

                                        int i = Integer.parseInt(snapshot1.getValue().toString());
                                        if (i >= 2)
                                            flag = 1;

                                    } else if (snapshot1.getKey().contains("Clinic")) {
                                        secondData = snapshot1.getValue().toString();
                                    }
                                    else if (snapshot1.getKey().equals("LMP")) {
                                        verificationData = snapshot1.getValue().toString();
                                    }
                                }
                            }
                            if(flag==1)
                            {
                                recyclerActivityModels.add(new RecyclerActivityModel(firstData,"",verificationData,type));
                            }
                        }

                        if(type.contains("YES"))
                            recyclerActivityAdapter=new RecyclerActivityAdapter(recyclerActivityModels,type,1,getActivity(),activity);
                        else
                            recyclerActivityAdapter=new RecyclerActivityAdapter(recyclerActivityModels,type,0,getActivity(),activity);

                        view.findViewById(R.id.indeterminateBar).setVisibility(View.GONE);

                        recyclerView.setAdapter(recyclerActivityAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        else if(type.contains("MTP")) {
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = firebaseDatabase.getReference();
            recyclerActivityModels = new ArrayList<>();
            databaseReference.child("Patients").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        firstData = dataSnapshot1.getKey();
                        flag = 0;
                        Log.e("MTP", " Mai loop mei ghum rha hun");
                        for (DataSnapshot snapshot : dataSnapshot1.getChildren()) {
                            for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                if (snapshot1.getKey().contains("Indication of MTP") && snapshot1.getValue().toString().contains("YES")) {
                                    flag = 1;

                                } else if (snapshot1.getKey().contains("Clinic")) {
                                    secondData = snapshot1.getValue().toString();
                                }
                                if (snapshot1.getKey().equals("LMP")) {
                                    verificationData = snapshot1.getValue().toString();
                                }
                            }
                        }
                        if (flag == 1) {
                            recyclerActivityModels.add(new RecyclerActivityModel(firstData, "", verificationData, type));
                        }
                    }

                    if(type.contains("YES"))
                        recyclerActivityAdapter=new RecyclerActivityAdapter(recyclerActivityModels,type,1,getActivity(),activity);
                    else
                        recyclerActivityAdapter=new RecyclerActivityAdapter(recyclerActivityModels,type,0,getActivity(),activity);

                    view.findViewById(R.id.indeterminateBar).setVisibility(View.GONE);

                    recyclerView.setAdapter(recyclerActivityAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else if(type.contains("ANC")) {
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = firebaseDatabase.getReference();
            recyclerActivityModels = new ArrayList<>();
            databaseReference.child("Patients").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        firstData = dataSnapshot1.getKey();
                        flag = 0;
                        Log.e("2 beti", " Mai loop mei ghum rha hun");
                        for (DataSnapshot snapshot : dataSnapshot1.getChildren()) {
                            for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                if (snapshot1.getKey().contains("ANC") && snapshot1.getValue().toString().contains("YES")) {

                                    flag = 1;

                                } else if (snapshot1.getKey().contains("Clinic")) {
                                    secondData = snapshot1.getValue().toString();
                                }
                                if (snapshot1.getKey().equals("LMP")) {
                                    verificationData = snapshot1.getValue().toString();
                                }
                            }
                        }

                        if (flag == 1) {
                            recyclerActivityModels.add(new RecyclerActivityModel(firstData, "",verificationData, type));
                        }
                    }
                    if(type.contains("YES"))
                        recyclerActivityAdapter=new RecyclerActivityAdapter(recyclerActivityModels,type,1,getActivity(),activity);
                    else
                        recyclerActivityAdapter=new RecyclerActivityAdapter(recyclerActivityModels,type,0,getActivity(),activity);

                    view.findViewById(R.id.indeterminateBar).setVisibility(View.GONE);

                    recyclerView.setAdapter(recyclerActivityAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else if(type.contains("Jan")||type.contains("Feb")||type.contains("Mar")||type.contains("April")||type.contains("May")||type.contains("June")||type.contains("July")||type.contains("Aug")||type.contains("Sept")||type.contains("Oct")||type.contains("Nov")||type.contains("Dec")) {
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = firebaseDatabase.getReference();
            recyclerActivityModels = new ArrayList<>();
            databaseReference.child("Patients").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        firstData = dataSnapshot1.getKey();
                        flag = 0;
                        Log.e("2 beti", " Mai loop mei ghum rha hun");
                        for (DataSnapshot snapshot : dataSnapshot1.getChildren()) {
                            for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                                if (snapshot1.getKey().contains("LMP Month") && snapshot1.getValue().toString().contains(type.substring(0,3))) {
                                    flag = 1;

                                } else if (snapshot1.getKey().contains("Clinic")) {
                                    secondData = snapshot1.getValue().toString();
                                }
                                if (snapshot1.getKey().equals("LMP")) {
                                    verificationData = snapshot1.getValue().toString();
                                }
                            }
                        }
                        if (flag == 1) {
                            recyclerActivityModels.add(new RecyclerActivityModel(firstData, "",verificationData, type));
                        }
                    }

                    if(type.contains("YES"))
                        recyclerActivityAdapter=new RecyclerActivityAdapter(recyclerActivityModels,type,1,getActivity(),activity);
                    else
                        recyclerActivityAdapter=new RecyclerActivityAdapter(recyclerActivityModels,type,0,getActivity(),activity);

                    view.findViewById(R.id.indeterminateBar).setVisibility(View.GONE);

                    recyclerView.setAdapter(recyclerActivityAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else if(type.contains("Month")) {
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            final DatabaseReference databaseReference = firebaseDatabase.getReference();
            recyclerActivityModels = new ArrayList<>();
            databaseReference.child("Patients").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        firstData = dataSnapshot1.getKey();
                        flag = 0;
                        Log.e("2 beti", " Mai loop mei ghum rha hun");
                        for (DataSnapshot snapshot : dataSnapshot1.getChildren()) {
                            flag = 0;
                            for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                if (snapshot1.getKey().contains("Date")) {
                                    Calendar calendar = Calendar.getInstance();
                                    String number=type.substring(0,1);
                                    int numberNum=Integer.parseInt(number.trim());
                                    SimpleDateFormat mdformat = new SimpleDateFormat("yyyy/MM/dd");
                                    String strDate =mdformat.format(calendar.getTime());
                                    String month=strDate.substring(strDate.indexOf("/")+1,strDate.lastIndexOf("/"));
                                    String date=snapshot1.getValue().toString();
                                    String visitMonth=date.substring(date.indexOf("/")+1,date.lastIndexOf("/"));
                                    String visitDate=date.substring(0,date.indexOf("/"));
                                    String todayDate=strDate.substring(strDate.lastIndexOf("/")+1);
                                    int thisMonth=Integer.parseInt(month.trim());
                                    int thisDate=Integer.parseInt(todayDate.trim());
                                    int visiDate=Integer.parseInt(visitDate.trim());
                                    int visiMonth=Integer.parseInt(visitMonth.trim());
                                    if(((thisMonth)-visiMonth)==numberNum && thisDate-visiDate>=0)
                                    flag = 1;
                                    else if (((thisMonth)-visiMonth)==(numberNum+1) && thisDate-visiDate<=0)
                                        flag=1;


                                } else if (snapshot1.getKey().contains("Clinic")) {
                                    secondData = snapshot1.getValue().toString();
                                }
                                if (snapshot1.getKey().equals("LMP")) {
                                    verificationData = snapshot1.getValue().toString();
                                }
                            }
                        }
                        if (flag == 1) {
                            recyclerActivityModels.add(new RecyclerActivityModel(firstData, "", verificationData, type));
                        }
                    }

                    if (type.contains("YES"))
                        recyclerActivityAdapter = new RecyclerActivityAdapter(recyclerActivityModels, type, 1, getActivity(), activity);
                    else
                        recyclerActivityAdapter = new RecyclerActivityAdapter(recyclerActivityModels, type, 0, getActivity(), activity);

                    view.findViewById(R.id.indeterminateBar).setVisibility(View.GONE);

                    recyclerView.setAdapter(recyclerActivityAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void update()
    {
        if(searchText!=null)
        {
            List<RecyclerActivityModel> temp = new ArrayList();
            for(RecyclerActivityModel d: recyclerActivityModels){
                //or use .equal(text) with you want equal match
                //use .toLowerCase() for better matches
                if(d.getPrimaryData().toLowerCase().contains(searchText.toLowerCase())){
                    temp.add(d);
                }
            }
            //update recyclerview

            recyclerActivityAdapter.updateList(temp);
        }

    }
}
