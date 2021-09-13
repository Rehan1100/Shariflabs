package com.solutionsplayer.AirLineCard;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.solutionsplayer.shariflabs.R;

public class AirlineFragment extends Fragment {


    private TextView title;
    ImageView BackButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_airline, container, false);
        BackButton = view.findViewById(R.id.back);
        title = view.findViewById(R.id.Title);
        title.setText("Air line");

        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.airlinetohome);
            }
        });
        return  view;    }
}