package com.solutionsplayer.LabReport;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.solutionsplayer.CustomProgressDialog.LoadingDialog;
import com.solutionsplayer.shariflabs.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class labReport extends Fragment {
    SendingClass obj = new SendingClass();
    private TextView title, SignMsg,Label;
    private ImageView BackButton, Searchicon;
    public RecyclerView recyclerView;
    AppCompatActivity context;
    ArrayList<LabModel> testlist = new ArrayList<>();
    ArrayList<LabModel> ParentList = new ArrayList<>();
    RelativeLayout SearchCard;
    EditText searchid;
    Button Searchbtn;
    LabModel labModel,labModel2;
    String id;
    LabAdapter labadapter;
    public RequestQueue requestQueue;
    public String url = "https://shariflabs.com/api/track.php";
    public LoadingDialog loadingbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lab_report, container, false);

        /** Here is Context **/
        context = (AppCompatActivity) getContext();
        /** Setting on Title **/
        title = view.findViewById(R.id.Title);
        title.setText("Lab Report");
        /**ProgressDialog is here**/

        loadingbar = new LoadingDialog(context);

        /** Setting backButton on Top **/
        BackButton = view.findViewById(R.id.back);
        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_navigation_labReport_to_navigation_home);
            }
        });
        /** Setting SearchButton on Top **/
        Searchicon = view.findViewById(R.id.Search);
        SearchCard = view.findViewById(R.id.cardSearch);

        /** Setting Searchbox on Search icon on Top **/
        Searchicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SearchCard.getVisibility() == View.VISIBLE) {
                    SearchCard.setVisibility(View.GONE);

                } else if (SearchCard.getVisibility() == View.GONE) {

                    SearchCard.setVisibility(View.VISIBLE);
                }

            }
        });
        /** Setting on Card on SearchBox**/
        Searchbtn = view.findViewById(R.id.Searchbtn);
        searchid = view.findViewById(R.id.searchid);
        Searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingbar.startLoadingDialog("Please Wait For a While");
                id = searchid.getText().toString();
                if (!TextUtils.isEmpty(id)) {
                    hideKeybaord(view);
                    testlist.clear();
                    ParentList.clear();
                    networkRequest(context, url, id);


                }
                else {
                    loadingbar.dismiss();
                    Toast.makeText(context, "Please Enter Tracking id", Toast.LENGTH_SHORT).show();
                }
            }
        });

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
                        Searchbtn.setEnabled(true);


                    }
                }
                else {
                    Searchbtn.setEnabled(false);

                    Toast.makeText(finalcontext,R.string.No_Internet, Toast.LENGTH_SHORT).show();
                }

            }
        };

        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        getContext().registerReceiver((BroadcastReceiver) br, intentFilter);




        /** RecyclerView Here **/
        recyclerView = view.findViewById(R.id.reportRecycler);
        SignMsg = view.findViewById(R.id.signMSG);
        Label = view.findViewById(R.id.response);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(manager);




        return view;
    }


    public void networkRequest(Context context, String url, String id1) {
        requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, addJsonParams(id1, "0"),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        ParentList.clear();
                        Log.d("response", response.toString());
                        try {
                            Label.setVisibility(View.GONE);
                            loadingbar.dismiss();
                            String ptn = response.getString("ptn");
                            String patient_name = response.getString("patient_name");
                            String gender = response.getString("report_date");
                            String patient_dob = response.getString("patient_dob");
                            String passport_no = response.getString("passport_no");
                            String ticket_pnr_no = response.getString("ticket_pnr_no");
                            String sample_date = response.getString("sample_date");
                            String sample_time = response.getString("sample_time");
                            JSONArray test = response.getJSONArray("tests");
                            for (int j = 0; j < test.length(); j++) {
                                JSONObject testJSONObject = test.getJSONObject(j);
                                    String test_name = testJSONObject.getString("test_name");
                                    String test_id = testJSONObject.getString("test_id");
                                    String test_result = testJSONObject.getString("test_result");
                                    String test_details = testJSONObject.getString("details");


                             labModel= new LabModel(test_name,test_id,test_result,test_details);
                            testlist.add(labModel);
                            labModel2= new LabModel(ptn,patient_name,patient_dob,passport_no,ticket_pnr_no,sample_date,sample_time,testlist,gender);
                            ParentList.add(labModel2);
                                }
                            if (!ParentList.isEmpty()) {
                                SignMsg.setVisibility(View.GONE);
                                SearchCard.setVisibility(View.GONE);

                            }

                             labadapter = new LabAdapter(ParentList, context);
                            recyclerView.setAdapter(labadapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        SignMsg.setVisibility(View.GONE);
                        loadingbar.dismiss();
                        Label.setVisibility(View.VISIBLE);
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;


            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                6000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    @Override
    public void onPause() {
        super.onPause();

        if (SearchCard.getVisibility() == View.VISIBLE) {
            SearchCard.setVisibility(View.GONE);

        } else if (SearchCard.getVisibility() == View.GONE) {

            SearchCard.setVisibility(View.VISIBLE);
        }

    }

    private void hideKeybaord(View v) {
        InputMethodManager inputMethodManager = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            inputMethodManager = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
        }
        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
    }

    public JSONObject addJsonParams(String id, String testid) {
        JSONObject jsonobject = new JSONObject();
        try {

            ///***//
            Log.d("addJsonParams", "addJsonParams");
            jsonobject.put("id", id);
            jsonobject.put("test_id", testid);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return jsonobject;
    }

}