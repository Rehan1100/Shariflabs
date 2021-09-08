package com.solutionsplayer.TestRate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.solutionsplayer.ProjectBackend.TestRateModel;
import com.solutionsplayer.shariflabs.R;

import java.util.ArrayList;
import java.util.List;

public class testRateAdapter extends RecyclerView.Adapter<testRateAdapter.Viewholder> implements Filterable {
    private List<TestRateModel> modelCLassList;
    private List<TestRateModel> modelCLassList2;
    List<TestRateModel> copylist;
    List<TestRateModel> filterlist;
    Context context;
    private int lastPosition = -1;

    public testRateAdapter(List<TestRateModel> modelCLassList, Context context) {
        this.modelCLassList = modelCLassList;
        this.modelCLassList2 = modelCLassList;
        this.copylist = new ArrayList<>(modelCLassList);
        this.filterlist=modelCLassList;
        this.context = context;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.test_rate_rows, parent, false);

        return new Viewholder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final Viewholder holder, final int position) {

        final String name = modelCLassList.get(position).getTest_name();
        final String desc = modelCLassList.get(position).getTest_desc();

        setAnimation(holder.itemView, position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


        holder.setData(name, desc);
    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_right);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,FilterResults results) {
                modelCLassList.clear();
                modelCLassList.addAll(modelCLassList2);
                modelCLassList = (ArrayList<TestRateModel>) results.values; // has the filtered values
                notifyDataSetChanged();  // notifies the data with new filtered values
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                ArrayList<TestRateModel> FilteredArrList = new ArrayList<TestRateModel>();

                if (modelCLassList == null) {
                    modelCLassList = new ArrayList<TestRateModel>(modelCLassList); // saves the original data in mOriginalValues
                }

                if (constraint == null || constraint.length() == 0) {

                    // set the Original result to return
                    results.count = modelCLassList.size();
                    results.values = modelCLassList;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (TestRateModel data : modelCLassList2) {
                        if (data.getTest_name().toLowerCase().contains(constraint.toString().toLowerCase())) {
                            FilteredArrList.add(data);

                        }
                    }
                    // set the Filtered result to return
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }
        };
        return filter;
    }

    @Override
    public int getItemCount() {
        return modelCLassList.size();
    }

    public void getAllCategories(List<TestRateModel> data) {
        this.modelCLassList = data;

    }

    class Viewholder extends RecyclerView.ViewHolder {
        private TextView name, desc;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.patient_name);
            desc = itemView.findViewById(R.id.Desc);

        }

        private void setData(String Name, String Desc) {
            name.setText(Name);
            desc.setText(Desc);


        }
    }


}


