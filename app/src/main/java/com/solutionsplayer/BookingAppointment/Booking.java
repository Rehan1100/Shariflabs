package com.solutionsplayer.BookingAppointment;

import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.solutionsplayer.CustomProgressDialog.LoadingDialog;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.solutionsplayer.shariflabs.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class Booking extends Fragment {
    private TextView title;
    private ImageView BackButton;
    AppCompatActivity finalcontext;
    TextInputEditText Name, Email, CellNo, ReservationDate, Message;
    AutoCompleteTextView Sercices;
    String saveCurrentDate, DeadlineDate;
    int Year, month, day;
    public int Startday, Startmonth, StartYear;
    public String item;
    MaterialButton Submit;
    public ArrayList<String> data;
    RequestQueue requestQueue;
    public LoadingDialog loadingbar;
    public ArrayAdapter<String> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking, container, false);
        finalcontext = (AppCompatActivity) getContext();
        title = view.findViewById(R.id.Title);
        Name = view.findViewById(R.id.edit_name);
        Email = view.findViewById(R.id.edit_email);
        CellNo = view.findViewById(R.id.edit_cellno);
        ReservationDate = view.findViewById(R.id.edit_reservationdate);
        Sercices = view.findViewById(R.id.dropdown_services);
        Message = view.findViewById(R.id.edit_message);
        Submit = view.findViewById(R.id.submitbtn);
        loadingbar = new LoadingDialog(finalcontext);
        //Add TextWatcher
        Name.addTextChangedListener(CreateTextWatcher);
        Email.addTextChangedListener(CreateTextWatcher);
        ReservationDate.addTextChangedListener(CreateTextWatcher);
        Sercices.addTextChangedListener(CreateTextWatcher);
        CellNo.addTextChangedListener(CreateTextWatcher);
        Message.addTextChangedListener(CreateTextWatcher);
        title.setText("Booking");
        /** Setting backButton on Top **/
        BackButton = view.findViewById(R.id.back);
        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_navigation_booking_to_navigation_home);
            }
        });
        //DateCode
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        saveCurrentDate = dateFormat.format(calendar.getTime());
        String[] dateParts = saveCurrentDate.split("/");
        int getday = Integer.parseInt(dateParts[0]);
        int getmonth = Integer.parseInt(dateParts[1]);
        int getyear = Integer.parseInt(dateParts[2]);
        final Calendar lastCalender = Calendar.getInstance();
        ReservationDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Year = lastCalender.get(Calendar.YEAR);
                month = lastCalender.get(Calendar.MONTH);
                day = lastCalender.get(Calendar.DAY_OF_MONTH);

                final DatePickerDialog datePickerDialog = new DatePickerDialog(finalcontext,R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String finalmonth = null;
                        int month1 = month + 1;
                        Startday = (dayOfMonth);
                        Startmonth = (month1);
                        StartYear = year;
                        if (month1 <= 9) {
                            finalmonth = "0" + (month1);
                        } else {
                            finalmonth = String.valueOf(month1);
                        }
                        DeadlineDate = dayOfMonth + "/" + (finalmonth) + "/" + year;

                        if (getday > Startday && getmonth > Startmonth && getyear > StartYear) {

                            ReservationDate.setText("");
                            Toast.makeText(finalcontext, "You're Selecting the Previous date", Toast.LENGTH_SHORT).show();
                        } else if (getday == Startday && getmonth == Startmonth && getyear == StartYear) {
                            ReservationDate.setText("");
                            Toast.makeText(finalcontext, "You're Selecting the current date", Toast.LENGTH_SHORT).show();
                        } else if (getday < Startday || (getday > Startday && (getmonth < Startmonth || getyear < StartYear)) || (getday == Startday && (getmonth < Startmonth || getyear < Startmonth))) {

                            if (getyear <= StartYear) {
                                ReservationDate.setText(DeadlineDate);
                            }
                        }
                    }
                }, Year, month, day);
                datePickerDialog.show();
            }
        });
        /** dropdown is here **/
        data = new ArrayList<>();
        data.add(0, "Select Services");
        data.add("Covid-19 Test");
        data.add("Pathology");
        data.add("Radiology");
        data.add("ECG/Ultrasound");
        data.add("Dexa Scan");
        adapter = new ArrayAdapter(finalcontext, R.layout.dropdownmenu, data);

        adapter.setDropDownViewResource(R.layout.dropdownmenu);

        Sercices.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        Sercices.setAdapter(adapter);


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


        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               SubmitDetails(v, finalcontext);


            }
        });


        return view;
    }

    public void SubmitDetails(View v, Context context1) {
        if (!valideUsername() | !valideEmailAdress() | !validDate() | !validService() | !validMessage() | !validPhoneNumber()) {

            Submit.setEnabled(false);

            return;
        } else {
            Submit.setEnabled(true);
            isUser(context1, v);

        }
    }

    private boolean validPhoneNumber() {
        String val = CellNo.getText().toString();
        //  String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (val.isEmpty()) {
            CellNo.setError("Phone number is must");
            CellNo.setFocusable(true);
            return false;
        } else if (CellNo.getText().length() < 9) {
            CellNo.setError("Enter Correct Number");
            CellNo.setFocusable(true);
            return false;
        } else {
            CellNo.setError(null);
            return true;
        }
    }

    private boolean validMessage() {
        if (!TextUtils.isEmpty(Message.getText().toString())) {
            Message.setError(null);
            return true;
        } else {
            Message.setError("Please Enter your Message");
            Message.setFocusable(true);
            return false;
        }

    }

    private TextWatcher CreateTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            String name = Name.getText().toString().trim();
            String email = Email.getText().toString().trim();
            String msg = Message.getText().toString().trim();
            String date = ReservationDate.getText().toString().trim();
            String cell = CellNo.getText().toString().trim();
            //Drawable image= profileImage.getDrawable();


            Submit.setEnabled(!name.isEmpty() && !email.isEmpty() && !msg.isEmpty() && !date.isEmpty() && !cell.isEmpty());


        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    private boolean validService() {
        String servies = Sercices.getText().toString();
        if (servies.equals("Select Services")) {
            Toast.makeText(finalcontext, "Select Services", Toast.LENGTH_SHORT).show();
            return false;
        } else {

            return true;
        }

    }

    private boolean validDate() {
        String date = ReservationDate.getText().toString();
        if (TextUtils.isEmpty(date)) {

            Toast.makeText(finalcontext, "Select Date", Toast.LENGTH_SHORT).show();
            return false;
        } else {

            return true;
        }

    }

    private Boolean valideUsername() {

        String regix = "[0-9]+";
        String val = Name.getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";
        if (val.isEmpty()) {
            Name.setError("Field can't be Empty");
            Name.setFocusable(true);
            return false;
        } else if (val.length() >= 15) {
            Name.setError("UserName is Too Long");
            Name.setFocusable(true);
            return false;
        } else if (!val.matches(noWhiteSpace)) {
            Name.setError("White Space are not Allowed");
            Name.setFocusable(true);
            return false;
        } else {
            Name.setError(null);
            Submit.setEnabled(true);
            return true;
        }


    }

    private Boolean valideEmailAdress() {

        String val = Email.getText().toString();
        //  String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String emailPattern = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
        if (val.isEmpty()) {
            Email.setError("Field can't be Empty");
            Email.setFocusable(true);
            return false;
        } else if (!val.matches(emailPattern)) {
            Email.setError("Thats Not Email Pattern");
            Email.setFocusable(true);
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(Email.getText().toString()).matches()) {
            Email.setError("Please provide valid email");
            Email.setFocusable(true);
            return false;

        } else {
            Email.setError(null);
            return true;
        }
    }

    public void isUser(Context context1, View view) {

        loadingbar.startLoadingDialog("Please Wait for Booking");

       /* loadingbar.dismiss();
        Navigation.findNavController(view).navigate(R.id.action_navigation_booking_to_navigation_home);
*/
        BackgroundBookingAppointment bg= new BackgroundBookingAppointment(context1,view);
        bg.execute("https://shariflabs.com/api/setdata.php");

    }


    class BackgroundBookingAppointment extends AsyncTask<String, String, String> {
        Context context;
        View view;

        public BackgroundBookingAppointment(Context context, View view) {
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
                    Navigation.findNavController(view).navigate(R.id.action_navigation_booking_to_navigation_home);
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

                    params.put("patient_name", Name.getText().toString());
                    params.put("patient_email", Email.getText().toString());
                    params.put("patient_cellno", CellNo.getText().toString());
                    params.put("services", Sercices.getText().toString());
                    params.put("reservation_date", ReservationDate.getText().toString());
                    params.put("message", Message.getText().toString());

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

}