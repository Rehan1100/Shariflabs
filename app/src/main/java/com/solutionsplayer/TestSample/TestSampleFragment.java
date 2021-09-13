package com.solutionsplayer.TestSample;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.solutionsplayer.shariflabs.R;

public class TestSampleFragment extends Fragment {

    private TextView title;

    ImageView BackButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_test_sample, container, false);
        BackButton = view.findViewById(R.id.back);
        title = view.findViewById(R.id.Title);
        title.setText("TestSample");

        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.sampletohome);
            }
        });
        return  view;
    }
}