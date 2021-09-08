package com.solutionsplayer.BottomNavigation.ui.ContactsUs;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.solutionsplayer.shariflabs.R;
import com.solutionsplayer.shariflabs.databinding.FragmentContactusBinding;


public class ContactUsFragment extends Fragment {

    private ContactUsViewModel contactUsViewModel;
    private FragmentContactusBinding binding;
    CardView Call,Email;
    AppCompatActivity context;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        contactUsViewModel = new ViewModelProvider(this).get(ContactUsViewModel.class);

        binding = FragmentContactusBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        context= (AppCompatActivity) getContext();
        final TextView textView = binding.getRoot().findViewById(R.id.Title);
        final ImageView BackButton = binding.getRoot().findViewById(R.id.back);
        Call=binding.getRoot().findViewById(R.id.contactCard);
        Email=binding.getRoot().findViewById(R.id.EmailCard);
        contactUsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Navigation.findNavController(v).navigate(R.id.action_navigation_contactus_to_navigation_home);
            }
        });
        Call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + "+92512120967"));
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.CALL_PHONE},   //request specific permission from user
                            10);


                    return ;
                } else {

                    //have got permission
                    try {
                        startActivity(callIntent);
                        //call activity and make phone call
                    } catch (android.content.ActivityNotFoundException ex) {
                        Log.d("call", "yourActivity is not founded");
                        //        Toast.makeText(root.getContext(), "yourActivity is not founded", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        Email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(Intent.ACTION_SENDTO); // it's not ACTION_SEND
                intent.setData(Uri.parse("mailto:shariflabsanddiagnostics@gmail.com")); // or just "mailto:" for blank
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
                startActivity(intent);

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