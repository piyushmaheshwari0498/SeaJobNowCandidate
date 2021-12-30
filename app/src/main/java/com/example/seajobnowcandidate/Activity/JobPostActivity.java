package com.example.seajobnowcandidate.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.seajobnowcandidate.Adapter.PostJobsAdapter;
import com.example.seajobnowcandidate.Entity.RetrofitBuilder;
import com.example.seajobnowcandidate.Entity.request.PostJobDetailsRequest;
import com.example.seajobnowcandidate.Entity.response.PostJobDetailsResponse;
import com.example.seajobnowcandidate.Interface.ApiInterface;
import com.example.seajobnowcandidate.R;
import com.example.seajobnowcandidate.Utils.InternetConnection;
import com.example.seajobnowcandidate.databinding.ActivityJobPostBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobPostActivity extends AppCompatActivity {

    int cat_id;
    String cat_name;
    // Array list for recycler view data source
    List<PostJobDetailsRequest> source;

    // adapter class object
    PostJobsAdapter adapter;

    RecyclerView.LayoutManager RecyclerViewLayoutManager;

    ActivityJobPostBinding binding;

    InternetConnection internetConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityJobPostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        internetConnection = new InternetConnection();

        cat_id = Integer.parseInt(getIntent().getStringExtra("cat_id"));
        cat_name = getIntent().getStringExtra("cat_name");
        getSupportActionBar().setTitle(cat_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getPostJobDetails();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void getPostJobDetails() {
        ApiInterface apiInterface = RetrofitBuilder.getRetrofitInstance().create(ApiInterface.class);
        Call<PostJobDetailsResponse> call = apiInterface.getPostJonData(getfetchHashMap());
        call.enqueue(new Callback<PostJobDetailsResponse>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<PostJobDetailsResponse> call, Response<PostJobDetailsResponse> response) {
                if (response.code() == 200 && response.message().equals("OK")) {
//                    if (response.body().getStatusCode() == 1 && response.body().getMessage().equals("Success")) {
                    source = response.body().getData();
                    Log.d("sourcePosts", source.toString());
                    if (!source.isEmpty()) {
                        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
//
//                            // Set LayoutManager on Recycler View
                        binding.rvPostedJobs.setLayoutManager(RecyclerViewLayoutManager);

                        adapter = new PostJobsAdapter(getApplicationContext(), source, R.layout.postjobs_items,true);
                        binding.rvPostedJobs.setAdapter(adapter);
                    } else {
                        binding.llDataFound.setVisibility(View.GONE);
                        binding.llNoDataFound.setVisibility(View.VISIBLE);
                        binding.retryBtn.setVisibility(View.GONE);
                        binding.imgInfo.setImageDrawable(getResources().getDrawable(R.drawable.no_data_found));
                        binding.noDataText.setText(getString(R.string.record_not_found));
                    }
//                    }
                } else {
                    if (response.code() == 404 && response.message().equals("Not Found")) {
                        if(response.message().equals("Failure")){
                            binding.llDataFound.setVisibility(View.GONE);
                            binding.llNoDataFound.setVisibility(View.VISIBLE);
                            binding.retryBtn.setVisibility(View.GONE);
                            binding.imgInfo.setImageDrawable(getResources().getDrawable(R.drawable.no_data_found));
                            binding.noDataText.setText(getString(R.string.record_not_found));
                        }else {
                            try {
                                JSONObject jsonObject = new JSONObject(response.errorBody().string());
                                String message = jsonObject.getString("message");
                                if (!internetConnection.isConnected(getApplicationContext())) {
                                    binding.llDataFound.setVisibility(View.GONE);
                                    binding.llNoDataFound.setVisibility(View.VISIBLE);
                                    binding.retryBtn.setVisibility(View.GONE);
                                    binding.imgInfo.setImageDrawable(getResources().getDrawable(R.drawable.no_data_found));
                                    binding.noDataText.setText(getString(R.string.no_internet));
                                } else {
                                    Log.e("in 404 fetch PostJobfragment", response.message());
                                    Log.e("in", "else");
                                    binding.llDataFound.setVisibility(View.GONE);
                                    binding.llNoDataFound.setVisibility(View.VISIBLE);
                                    binding.retryBtn.setVisibility(View.GONE);
                                    binding.imgInfo.setImageDrawable(getResources().getDrawable(R.drawable.no_data_found));
                                    binding.noDataText.setText(getString(R.string.record_not_found));
                                }
//                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                            } catch (JSONException | IOException e) {
                                e.printStackTrace();
                                Log.e("in exception fetch PostJobfragment", e.getMessage());
                            }
                        }
                    }

                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(Call<PostJobDetailsResponse> call, Throwable t) {
                if (!internetConnection.isConnected(getApplicationContext())) {
                    binding.llDataFound.setVisibility(View.GONE);
                    binding.llNoDataFound.setVisibility(View.VISIBLE);
                    binding.retryBtn.setVisibility(View.GONE);
                    binding.imgInfo.setImageDrawable(getResources().getDrawable(R.drawable.no_data_found));
                    binding.noDataText.setText(getString(R.string.no_internet));
                } else {
                    Log.e("infailure fetch PostJobfragment", t.getMessage());
                    binding.llDataFound.setVisibility(View.GONE);
                    binding.llNoDataFound.setVisibility(View.VISIBLE);
                    binding.retryBtn.setVisibility(View.GONE);
                    binding.imgInfo.setImageDrawable(getResources().getDrawable(R.drawable.no_data_found));
                    binding.noDataText.setText(getString(R.string.record_not_found));
                }
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private HashMap<String, Object> getfetchHashMap() {
        Log.d("cat_id", String.valueOf(cat_id));
        HashMap<String, Object> map = new HashMap<>();
        map.put("API_ACCESS_KEY", "ZkC6BDUzxz");
        map.put("vt_id", cat_id);
        return map;
    }
}