package com.solutionsplayer.TestSample;

import static android.content.Context.INPUT_METHOD_SERVICE;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.solutionsplayer.AirLineCard.AirlineFragment;
import com.solutionsplayer.AirLineCard.AirlineModel;
import com.solutionsplayer.BookingAppointment.Booking;
import com.solutionsplayer.CustomProgressDialog.LoadingDialog;
import com.solutionsplayer.shariflabs.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import kotlin.text.Regex;

public class TestSampleFragment extends Fragment {
    RequestQueue requestQueue;
    private TextView title;
    TextInputEditText name,phoneNo,PassportNumber,NumberofPassenger
            ,Cnic,DepartureCity,ArrivalCity,
            AdditionalNotes;
    static TextInputEditText SampleCollectionon;
    AutoCompleteTextView Airline;
    MaterialButton Submit;
    ImageView BackButton;
    public ArrayAdapter<String> adapter;
    public ArrayList<String> data = new ArrayList<>();
    public String item;
    public LoadingDialog loadingbar;
    AppCompatActivity finalcontext;
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

        name = view.findViewById(R.id.edit_name);
        phoneNo = view.findViewById(R.id.editphoneNumber);
        PassportNumber = view.findViewById(R.id.edit_passport_number);
        NumberofPassenger = view.findViewById(R.id.edit_number_of_passanger);
        Cnic = view.findViewById(R.id.edit_cnic);
        Airline = view.findViewById(R.id.edit_airline);
        DepartureCity = view.findViewById(R.id.edit_DepartureCity);
         ArrivalCity= view.findViewById(R.id.edit_ArrivalCity);
        SampleCollectionon = view.findViewById(R.id.edit_SampleCollection);
        AdditionalNotes = view.findViewById(R.id.edit_AdditionalNotes);
        Submit = view.findViewById(R.id.submitbtn);
        finalcontext= (AppCompatActivity) getContext();
        loadingbar = new LoadingDialog(finalcontext);



        data.add(0, "Select Airline");
        data.add("AIR ARABIA");
        data.add("Air Indus");
        data.add("Al-Jazeera Airways");
        data.add("AMERICAN AIRLINES");
        data.add("Avia Traffic Company");
        data.add("BISHKEK");
        data.add("BRITISH AIRWAYS");
        data.add("EMIRATES AIRLINE");
        data.add("FLY DUBAI");
        data.add("GULF AIR");
        data.add("Jazeera Airways");
        data.add("KAM AIR");
        data.add("Oman Air");
        data.add("PIA");
        data.add("QATAR AIRWAYS");
        data.add("SALAM AIR");
        data.add("SERENE AIR");
        data.add("SRILANKAN AIRLINES");
        data.add("Uzbekistan Airways");
        data.add("Virgin Atlantic");
        data.add("ENT AIR");
        data.add("KUWAIT AIRWAYS");
        data.add("IRAN AIR");
        data.add("AIRBLUE");
        data.add("ETIHAD AIRWAYS");
        data.add("IHCL");
        data.add("CCHASEEB");

        //Add TextWatcher
        name.addTextChangedListener(CreateTextWatcher);

        phoneNo.addTextChangedListener(CreateTextWatcher);
        PassportNumber.addTextChangedListener(CreateTextWatcher);
        NumberofPassenger.addTextChangedListener(CreateTextWatcher);
        Cnic.addTextChangedListener(CreateTextWatcher);
        Airline.addTextChangedListener(CreateTextWatcher);
        DepartureCity.addTextChangedListener(CreateTextWatcher);
        ArrivalCity.addTextChangedListener(CreateTextWatcher);
        SampleCollectionon.addTextChangedListener(CreateTextWatcher);
        AdditionalNotes.addTextChangedListener(CreateTextWatcher);


        BroadcastReceiver br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle extras = intent.getExtras();
                NetworkInfo info = (NetworkInfo) extras
                        .getParcelable("networkInfo");
                NetworkInfo.State state = info.getState();
                ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

                if (activeNetwork != null) {
                    if ((((NetworkInfo) activeNetwork).getType() == ConnectivityManager.TYPE_WIFI)
                            && ((((NetworkInfo) activeNetwork)).getState() == NetworkInfo.State.CONNECTED)) {
                        Submit.setEnabled(true);


                    }
                }
                else {
                    Submit.setEnabled(false);

                    Toast.makeText(context,R.string.No_Internet, Toast.LENGTH_SHORT).show();
                }

            }
        };

        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        getContext().registerReceiver((BroadcastReceiver) br, intentFilter);



        SampleCollectionon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showTruitonTimePickerDialog(v);
                showTruitonDatePickerDialog(v);
            }
        });

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SubmitDetails(v, finalcontext);


            }
        });

        adapter = new ArrayAdapter(finalcontext, R.layout.dropdownmenu, data);

        adapter.setDropDownViewResource(R.layout.dropdownmenu);

        Airline.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (!parent.getItemAtPosition(position).equals("Select Serivces")) {

                    item = parent.getItemAtPosition(position).toString();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                Toast.makeText(finalcontext, "please select any one", Toast.LENGTH_SHORT).show();
            }
        });
        Airline.setAdapter(adapter);




        return  view;
    }

    private Boolean valideUsername() {

        String regix = "[0-9]+";
        String val = name.getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";
        if (val.isEmpty()) {
            name.setError("Field can't be Empty");
            name.setFocusable(true);
            return false;
        }  if (val.length() >= 15) {
            name.setError("UserName is Too Long");
            name.setFocusable(true);
            return false;
        } if (!val.matches(noWhiteSpace)) {
            name.setError("White Space are not Allowed");
            name.setFocusable(true);
            return false;
        } else {
            name.setError(null);
            return true;

        }


    }

    private boolean validPhoneNumber() {
        String val = phoneNo.getText().toString();
        //  String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (val.isEmpty()) {
            phoneNo.setError("Phone number is must");
            phoneNo.setFocusable(true);
            return false;
        }
        else if (val.length() < 9) {
            phoneNo.setError("Enter Correct Number");
            phoneNo.setFocusable(true);
            return false;
        }
        else {
            phoneNo.setError(null);
            return true;
        }
    }

    private boolean validPassportNumber() {
        String val = PassportNumber.getText().toString();
        //  String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (val.isEmpty()) {
            PassportNumber.setError("Passport number not be null");
            PassportNumber.setFocusable(true);
            return false;
        }else {
            PassportNumber.setError(null);
            return true;
        }
    }

    private boolean validNumberofPassanger() {
        String val = NumberofPassenger.getText().toString();
        //  String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (val.isEmpty()) {
            NumberofPassenger.setError("Must Enter Number of Passenger");
            NumberofPassenger.setFocusable(true);
            return false;
        } else {
            NumberofPassenger.setError(null);
            return true;
        }
    }

    private boolean ValidCnicNumber() {
        String val = Cnic.getText().toString();
        String cnicpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (val.isEmpty()) {
            Cnic.setError("Cnic number not be null");
            Cnic.setFocusable(true);
            return false;
        } else if (cnicpattern.trim().matches("^[0-9]{9}[vVxX]$")) {
            Cnic.setError("Enter Correct Cnic");
            Cnic.setFocusable(true);
            return false;
        } else {
            Cnic.setError(null);
            return true;
        }
    }

    private boolean validDepartureCity() {
        String val = DepartureCity.getText().toString();
        if (val.isEmpty()) {
            DepartureCity.setError("DepartureCity not be null");
            DepartureCity.setFocusable(true);
            return false;
        }  else {
            DepartureCity.setError(null);
            return true;
        }
    }

    private boolean validArrivalCity() {
        String val = ArrivalCity.getText().toString();
        if (val.isEmpty()) {
            ArrivalCity.setError("ArrivalCity not be null");
            ArrivalCity.setFocusable(true);
            return false;
        }  else {
            ArrivalCity.setError(null);
            return true;
        }
    }

    private boolean validSampleCollection() {
        String val = SampleCollectionon.getText().toString();
        if (val.isEmpty()) {
            SampleCollectionon.setError("SampleCollectionon not be null");
            SampleCollectionon.setFocusable(true);
            return false;
        } else {
            SampleCollectionon.setError(null);
            return true;
        }
    }
    private boolean validAdditionalNotes() {
        String val = AdditionalNotes.getText().toString();
        if (val.isEmpty()) {
            AdditionalNotes.setError("AdditionalNotes not be null");
            AdditionalNotes.setFocusable(true);
            return false;
        } else {
            AdditionalNotes.setError(null);
            return true;
        }
    }

    private boolean validAirline() {
        String listofAirline = Airline.getText().toString();
        if (listofAirline.equals("Select Airline")) {
            Toast.makeText(finalcontext, "Select Airline", Toast.LENGTH_SHORT).show();
            return false;
        } else {

            return true;
        }

    }

    private TextWatcher CreateTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            String Name = name.getText().toString().trim();
            String Phoneno = phoneNo.getText().toString().trim();
            String passportNumber = PassportNumber.getText().toString().trim();
            String numberofPassanger = NumberofPassenger.getText().toString().trim();
            String cnic = Cnic.getText().toString().trim();
            String departureCity = DepartureCity.getText().toString().trim();
            String arrivalCity = ArrivalCity.getText().toString().trim();
            String sampleCollection = SampleCollectionon.getText().toString().trim();
            String additionalNotes = AdditionalNotes.getText().toString().trim();


            Submit.setEnabled(!Name.isEmpty() && !Phoneno.isEmpty() && !passportNumber.isEmpty() && !numberofPassanger.isEmpty()
                    && !cnic.isEmpty()&& !departureCity.isEmpty() && !arrivalCity.isEmpty() && !sampleCollection.isEmpty()
                    && !additionalNotes.isEmpty());


        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    public void SubmitDetails(View v, Context context1) {
        if (!valideUsername() | !validPhoneNumber() | !validPassportNumber()| !validNumberofPassanger() | !validAirline() | !ValidCnicNumber() | !validDepartureCity() | !validArrivalCity() | !validSampleCollection() | !validAdditionalNotes()) {
            Submit.setEnabled(false);
            return;
        } else {
            Submit.setEnabled(true);
            isUser(context1, v);

        }
    }

    public void isUser(Context context1, View view) {

        loadingbar.startLoadingDialog("Make Some Patience!");

        BackgroundBookingTestSample bg= new BackgroundBookingTestSample(context1,view);
        bg.execute("https://shariflabs.pk/api/collection.php");

    }


    class BackgroundBookingTestSample extends AsyncTask<String, String, String> {
        Context context;
        View view;

        public BackgroundBookingTestSample(Context context, View view) {
            this.context = context;
            this.view = view;
        }

        @Override
        protected String doInBackground(String... url) {
            requestQueue = Volley.newRequestQueue(context);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url[0], new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    hideKeybaord(view);
                    loadingbar.dismiss();
                    Navigation.findNavController(view).navigate(R.id.sampletohome);
                    Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    loadingbar.dismiss();
                    Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("name", name.getText().toString());
                    params.put("phone_no", phoneNo.getText().toString());
                    params.put("cnic", Cnic.getText().toString());
                    params.put("airline", Airline.getText().toString());
                    params.put("dep_city", DepartureCity.getText().toString());
                    params.put("arrival_city", ArrivalCity.getText().toString());
                    params.put("collection_time", SampleCollectionon.getText().toString());
                    params.put("notes", AdditionalNotes.getText().toString());
                    return params;
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    6000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(stringRequest);
            return "ok";
        }

    }

    private void hideKeybaord(View v) {
        InputMethodManager inputMethodManager = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            inputMethodManager = (InputMethodManager)finalcontext.getSystemService(INPUT_METHOD_SERVICE);
        }
        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);
    }




    public void showTruitonDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();

        newFragment.show(finalcontext.getSupportFragmentManager(), "datePicker");
    }

    public static class DatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it

            DatePickerDialog datePickerDialog= new DatePickerDialog(getActivity(),R.style.DialogTheme, this, year, month, day);
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            return datePickerDialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user

            SampleCollectionon.setText(day + "/" + (month + 1) + "/" + year);
        }
    }

    public void showTruitonTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(finalcontext.getSupportFragmentManager(), "timePicker");
    }

    public static class TimePickerFragment extends DialogFragment implements
            TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(),R.style.DialogTheme, this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
            SampleCollectionon.setText(SampleCollectionon.getText() + " -" + hourOfDay + ":"	+ minute);
        }
    }



}