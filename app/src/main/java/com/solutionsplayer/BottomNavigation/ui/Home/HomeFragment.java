package com.solutionsplayer.BottomNavigation.ui.Home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.solutionsplayer.shariflabs.R;
import com.solutionsplayer.shariflabs.databinding.FragmentHomeBinding;

import java.util.ArrayList;



public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    AppCompatActivity compatActivity;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        compatActivity = (AppCompatActivity) getContext();
        //compatActivity.getSupportActionBar().hide();
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView AboutUs = binding.getRoot().findViewById(R.id.aboutUs);
        final TextView OurServices = binding.getRoot().findViewById(R.id.ourService);
        final TextView Booking = binding.getRoot().findViewById(R.id.booking);
        final TextView TestRate = binding.getRoot().findViewById(R.id.testRate);
        final TextView LabReport = binding.getRoot().findViewById(R.id.labReport);
        final TextView ContactUs = binding.getRoot().findViewById(R.id.contactUs);
        /** CardView initialize **/
        final CardView AboutCard = binding.getRoot().findViewById(R.id.aboutCard);
        final CardView serviceCard = binding.getRoot().findViewById(R.id.serviceCard);
        final CardView bookingCard = binding.getRoot().findViewById(R.id.bookingCard);
        final CardView lapReportCard = binding.getRoot().findViewById(R.id.labCard);
        final CardView TestRateCard = binding.getRoot().findViewById(R.id.rateCard);
        final CardView contactCard = binding.getRoot().findViewById(R.id.contactCard);
        homeViewModel.getTextAboutUs().observe(getViewLifecycleOwner(), new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                AboutUs.setText(strings.get(0));
                OurServices.setText(strings.get(1));
                Booking.setText(strings.get(2));
                TestRate.setText(strings.get(3));
                LabReport.setText(strings.get(4));
                ContactUs.setText(strings.get(5));
            }
        });

        /** CardView ClickEvent **/

        AboutCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_navigation_home_to_navigation_AboutUs);


            }
        });


        serviceCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_navigation_home_to_navigation_services);
            }
        });


        bookingCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_navigation_home_to_navigation_booking);
            }
        });


        contactCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Navigation.findNavController(v).navigate(R.id.action_navigation_home_to_navigation_contactus);
            }
        });

        lapReportCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Navigation.findNavController(v).navigate(R.id.action_navigation_home_to_navigation_labReport);
            }
        });

        TestRateCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_navigation_home_to_navigation_testRate);
            }
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}