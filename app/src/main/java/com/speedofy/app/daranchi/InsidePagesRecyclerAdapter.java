package com.speedofy.app.daranchi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class InsidePagesRecyclerAdapter extends RecyclerView.Adapter<InsidePagesRecyclerAdapter.ViewHolder>{
    List<RecyclerActivityModel> dataToday;
    String type;
    int result;
    Context context;
    Activity activity;

    public InsidePagesRecyclerAdapter(List<RecyclerActivityModel> data, String typeData,int result, Context applicationContext,Activity activity)
    {
        this.dataToday=data;
        this.type=typeData;
        this.result=result;
        this.context=applicationContext;
        this.activity=activity;

    }

    @NonNull
    @Override
    public InsidePagesRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(i, viewGroup, false);

        return new InsidePagesRecyclerAdapter.ViewHolder(view);
    }


    public void updateList(List<RecyclerActivityModel> list){
        this.dataToday = list;
        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bindData(dataToday.get(i));
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.inside_pages_recycler_item
                ;
    }


    @Override
    public int getItemCount() {
        return dataToday.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView Name;
        TextView dataa;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Name=itemView.findViewById(R.id.primary_text);
            dataa=itemView.findViewById(R.id.secondary_text);

        }

        void bindData(final RecyclerActivityModel classT){
            Name.setText(classT.getPrimaryData());
            dataa.setText(classT.getSecondaryData());
            Name.getRootView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(type.contains("Clinics"))
                    {
                        if(result!=0) {
                            Intent intent1 = new Intent();
                            intent1.putExtra("Name", classT.getPrimaryData());

                            activity.setResult(100, intent1);
                            activity.finish();
                        }
                        else {
                            Intent intent = new Intent(context, ClinicActivity.class);
                            intent.putExtra("Name", classT.getPrimaryData());
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                            context.startActivity(intent);
                        }

                    }
                    else if (type.contains("Doctors"))
                    {
                        if(result!=0) {
                            Intent intent1 = new Intent();
                            intent1.putExtra("Name", classT.getPrimaryData());

                            activity.setResult(105, intent1);
                            activity.finish();
                        }
                        else {
                            Intent intent = new Intent(context, DoctorActivity.class);
                            intent.putExtra("Name", classT.getPrimaryData());
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                            context.startActivity(intent);
                        }
                    }
                    else if(type.contains("Missing")||type.contains("Machines"))
                    {
                        Log.e("TAG","Missing Documents Tapped");
                    }
                    else
                    {

                        if(result!=0) {
                            Intent intent1 = new Intent();
                            intent1.putExtra("Name", classT.getPrimaryData());
                            activity.setResult(110, intent1);
                            activity.finish();
                        }
                        else {

                            Intent intent = new Intent(context, PatientActivity.class);
                            intent.putExtra("Name", classT.getPrimaryData());
                            intent.putExtra("Verify", classT.getVerificationData());
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                            context.startActivity(intent);
                        }
                    }
                }
            });

        }

    }
}

