package com.solutionsplayer.LabReport.ViewReport;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.solutionsplayer.shariflabs.R;


public class ViewLabReportFragment extends Fragment {
    String tracking_id, patient_name, test_id, test_name, test_result,test_details,sample_time,report_date,patient_dob,passport_no,ticket_pnr_no,sample_date,gender_text;
    TextView date, trackingid, patientname, gender, dateofbirth, passportno, SampleTime, SampleDate, Sn, Title, Result, Ticket, Details, verfifiedDetails,ReportDownload;
    String url;
    Intent intent1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_lab_report, container, false);
        tracking_id = getArguments().getString("trackingid");
        patient_name = getArguments().getString("patient_name");
        gender_text = getArguments().getString("gender");
        patient_dob = getArguments().getString("patient_dob");
        passport_no = getArguments().getString("passport_no");
        ticket_pnr_no = getArguments().getString("ticket_pnr_no");
        sample_date = getArguments().getString("sample_date");
        sample_time = getArguments().getString("sample_time");
        test_id = getArguments().getString("test_id");
        test_name = getArguments().getString("test_name");
        test_details = getArguments().getString("test_details");
        test_result = getArguments().getString("test_results");
        date = view.findViewById(R.id.Date);
        trackingid = view.findViewById(R.id.trackingid);
        patientname = view.findViewById(R.id.patient_name);
        gender = view.findViewById(R.id.gendertext);
        dateofbirth = view.findViewById(R.id.dateofbirthtext);
        passportno = view.findViewById(R.id.passportnotext);
        Ticket = view.findViewById(R.id.tickettext);
        SampleTime = view.findViewById(R.id.sampletimetext);
        SampleDate = view.findViewById(R.id.sampledatetext);
        Sn = view.findViewById(R.id.sntext);
        Title = view.findViewById(R.id.testtext);
        Result = view.findViewById(R.id.resulttext);
        Details = view.findViewById(R.id.testdetails);
        verfifiedDetails = view.findViewById(R.id.verfifiedDetails);
        ReportDownload = view.findViewById(R.id.ReportDownload);

        trackingid.setText(tracking_id);
        patientname.setText(patient_name);
        gender.setText(gender_text);
        dateofbirth.setText(patient_dob);
        passportno.setText(passport_no);
        Ticket.setText(ticket_pnr_no);
        SampleTime.setText(sample_time);
        SampleDate.setText(sample_date);
        Sn.setText(test_id);
        Title.setText(test_name);
        Result.setText(test_result);
        Details.setText(test_details);
        verfifiedDetails.setText("http://shariflabs.pk/track/" + tracking_id+"/"+test_id);

        url="http://shariflabs.pk/Pdfgenerator/"+tracking_id+"/"+test_id+"";


        BroadcastReceiver br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context finalcontext, Intent intent) {
                Bundle extras = intent.getExtras();
                NetworkInfo info = (NetworkInfo) extras
                        .getParcelable("networkInfo");
                NetworkInfo.State state = info.getState();
                ConnectivityManager cm = (ConnectivityManager)finalcontext.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

                if (activeNetwork != null) {
                    if ((((NetworkInfo) activeNetwork).getType() == ConnectivityManager.TYPE_WIFI)
                            && ((((NetworkInfo) activeNetwork)).getState() == NetworkInfo.State.CONNECTED)) {

                    ReportDownload.setClickable(true);


                    }
                }
                else {
                    Toast.makeText(finalcontext,R.string.No_Internet, Toast.LENGTH_SHORT).show();
                }

            }
        };

        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        getContext().registerReceiver((BroadcastReceiver) br, intentFilter);



        ReportDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(url);
                intent1 = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent1);

            }
        });


        return view;
    }

}