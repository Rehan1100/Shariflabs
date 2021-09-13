package com.solutionsplayer.TestRate;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
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
import com.solutionsplayer.ProjectBackend.Repositry.ShareefLabRepositry;
import com.solutionsplayer.ProjectBackend.TestRateModel;
import com.solutionsplayer.shariflabs.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.INPUT_METHOD_SERVICE;


public class testRate extends Fragment {
    RequestQueue requestQueue;
    private TextView title, SignMsg;
    private ImageView BackButton,Searchicon;
    public RecyclerView recyclerView;
    TestRateModel testRateModel;
    RelativeLayout SearchCard;
    SearchView searchView;

    testRateAdapter adapter;
    AppCompatActivity finalcontext;
    ArrayList<TestRateModel> list = new ArrayList<>();
    ShareefLabRepositry repositry;
    TestViewModel viewModel;
    ProgressBar progressBar;
    public String testRateurl = "https://shariflabs.pk/api/fetchtests.php";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_test_rate, container, false);
        finalcontext = (AppCompatActivity) getContext();
        /** Setting on SignMessage Background or progressbar**/
        SignMsg = view.findViewById(R.id.signMSG);
        progressBar = view.findViewById(R.id.progressBar);
        /** Setting on Title **/
        title = view.findViewById(R.id.Title);
        title.setText("Test Rate");
        /** Setting backButton on Top **/
        BackButton = view.findViewById(R.id.back);
        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hideKeybaord(v);
                SearchCard.setVisibility(View.GONE);
                Navigation.findNavController(v).navigate(R.id.action_navigation_testRate_to_navigation_home);

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


        /** RecyclerView Here **/
        recyclerView = view.findViewById(R.id.reportRecycler);

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


                        networkRequest(context, testRateurl);


                    }
                }
                else {
                    Toast.makeText(context,R.string.No_Internet, Toast.LENGTH_SHORT).show();

                }

            }
        };

        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        getContext().registerReceiver((BroadcastReceiver) br, intentFilter);









        LinearLayoutManager manager = new LinearLayoutManager(finalcontext);
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(manager);
        repositry = new ShareefLabRepositry(getActivity().getApplication());
        viewModel = new ViewModelProvider(finalcontext).get(TestViewModel.class);
        list = new ArrayList<>();
        adapter = new testRateAdapter(list, finalcontext);
        viewModel.getALLCategry().observe(finalcontext, new Observer<List<TestRateModel>>() {
            @Override
            public void onChanged(List<TestRateModel> list) {
                if (list.isEmpty()) {
                    SignMsg.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    SignMsg.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                }

                adapter.getAllCategories(list);
                recyclerView.setAdapter(adapter);


            }
        });
        /** Setting on Card on SearchBox**/
        searchView = view.findViewById(R.id.searchtestname);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // TODO: setFilter
                if(adapter!=null)
                {
                    adapter.getFilter().filter(newText);

                }
                return true;
            }
        });



        return view;
    }

    private void networkRequest(Context context, String url) {
        requestQueue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("response", response.toString());
                list.clear();

                try {
                    JSONArray jsonarray = new JSONArray(response);

                    for (int i = 0; i < jsonarray.length(); i++) {
                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                        String test_id = jsonobject.getString("test_id");
                        String test_name = jsonobject.getString("test_name");
                        String test_fee = jsonobject.getString("test_fee");
                        String test_desc = jsonobject.getString("test_desc");

                        testRateModel = new TestRateModel(test_id, test_name, test_fee, test_desc);

                        list.add(testRateModel);


                    }
                    repositry.insert(list);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context, "SomeThing Went Wrong Try again", Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", "0");
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                6000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }
    private void hideKeybaord(View v) {
        InputMethodManager inputMethodManager = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            inputMethodManager = (InputMethodManager) finalcontext.getSystemService(INPUT_METHOD_SERVICE);
        }
        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
    }

}