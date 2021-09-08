package com.solutionsplayer.AboutUs;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.solutionsplayer.shariflabs.R;


public class AboutUsFragment extends Fragment {
    private TextView title,AboutText;
    private ImageView BackButton;
    AppCompatActivity context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_about_us, container, false);
        context= (AppCompatActivity) getContext();

        /** Setting on Title **/
        title= view.findViewById(R.id.Title);
        title.setText("About Us");
        /** Setting backButton on Top **/
        BackButton=view.findViewById(R.id.back);
        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_navigation_AboutUs_to_navigation_home);
            }
        });



        return view;
    }
}