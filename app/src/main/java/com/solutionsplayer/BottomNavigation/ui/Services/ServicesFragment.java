package com.solutionsplayer.BottomNavigation.ui.Services;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.solutionsplayer.shariflabs.R;
import com.solutionsplayer.shariflabs.databinding.FragmentServicesBinding;


public class ServicesFragment extends Fragment {

    private ServicesViewModel servicesViewModel;
    private FragmentServicesBinding binding;
    AppCompatActivity context;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        servicesViewModel = new ViewModelProvider(this).get(ServicesViewModel.class);

        binding = FragmentServicesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        context= (AppCompatActivity) getContext();
        final TextView Title = binding.getRoot().findViewById(R.id.Title);
        final ImageView BackButton = binding.getRoot().findViewById(R.id.back);

        servicesViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Title.setText(s);
            }
        });

        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Navigation.findNavController(v).navigate(R.id.action_navigation_services_to_navigation_home);


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