package com.solutionsplayer.LabReport;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.solutionsplayer.shariflabs.R;

import java.util.List;


public class LabAdapter extends RecyclerView.Adapter<LabAdapter.Viewholder>{
    private List<LabModel> modelCLassList;
    Context context;
    private int lastPosition = -1;
    String name,desc;
    public LabAdapter(List<LabModel> modelCLassList, Context context) {
        this.modelCLassList = modelCLassList;
        this.context = context;
    }
    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lab_report_row, parent, false);

        return new Viewholder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull final Viewholder holder, final int position) {

                name=modelCLassList.get(position).getList().get(position).getTest_name();
                desc = modelCLassList.get(position).getList().get(position).getTest_details();

        setAnimation(holder.itemView, position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle= new Bundle();
                bundle.putString("trackingid",modelCLassList.get(position).getPtn());
                bundle.putString("patient_name",modelCLassList.get(position).getPatient_name());
                bundle.putString("gender",modelCLassList.get(position).getGender());
                bundle.putString("patient_dob",modelCLassList.get(position).getPatient_dob());
                bundle.putString("passport_no",modelCLassList.get(position).getPassport_no());
                bundle.putString("ticket_pnr_no",modelCLassList.get(position).getTicket_pnr_no());
                bundle.putString("sample_date",modelCLassList.get(position).getSample_date());
                bundle.putString("sample_time",modelCLassList.get(position).getSample_time());
                bundle.putString("test_id",modelCLassList.get(position).getList().get(position).getTest_id());
                bundle.putString("test_name",modelCLassList.get(position).getList().get(position).getTest_name());
                bundle.putString("test_details",modelCLassList.get(position).getList().get(position).getTest_details());
                bundle.putString("test_results",modelCLassList.get(position).getList().get(position).getTest_result());
                Navigation.findNavController(v).navigate(R.id.action_navigation_labReport_to_View_Lab_Report,bundle);
            }
        });


        holder.setData(name,desc);
    }
    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_right);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }
    @Override
    public int getItemCount() {
        return modelCLassList.size();
    }
    class Viewholder extends RecyclerView.ViewHolder {
        private TextView name,desc;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.patient_name);
            desc = itemView.findViewById(R.id.Desc);

        }
        private void setData(String Name,String Desc) {
            name.setText(Name);
            desc.setText(Desc);


        }
    }


}


