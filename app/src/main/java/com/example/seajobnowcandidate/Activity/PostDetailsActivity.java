package com.example.seajobnowcandidate.Activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.seajobnowcandidate.Entity.RetrofitBuilder;
import com.example.seajobnowcandidate.Entity.request.PostJobDetailsRequest;
import com.example.seajobnowcandidate.Entity.response.PostJobDetailsResponse;
import com.example.seajobnowcandidate.Interface.ApiInterface;
import com.example.seajobnowcandidate.R;
import com.example.seajobnowcandidate.Utils.Constants;
import com.example.seajobnowcandidate.Utils.InternetConnection;
import com.example.seajobnowcandidate.actions.ShowSnackbar;
import com.example.seajobnowcandidate.databinding.ActivityPostDetailsBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostDetailsActivity extends AppCompatActivity {
    InternetConnection internetConnection;
    ActivityPostDetailsBinding binding;
    List<PostJobDetailsRequest> source;
    TextView textview_job_salary;
    TextView textview_postedDate;
    TextView textview_experience;
    TextView textview_location;
    TextView textview_rank;
    TextView textview_department;
    TextView textview_ship_type;
    TextView textview_expiry_date;
    TextView textview_opening;
    TextView textview_applicants;
    TextView textview_job_desc;

    TextView tv_company_name;
    TextView tv_company_logo;
    ImageView company_logo;
    CardView cc_logo;
    CardView cc_tv_logo;
    TextView tv_company_mobile;
    TextView tv_company_email;
    String IMAGEURL = "http://192.168.1.15/seajobsnow/public/uploads/company_logo/";

    String post_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        post_id = getIntent().getStringExtra("post_id");
        internetConnection = new InternetConnection();

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbarLayout.setTitle(getIntent().getStringExtra("post_title"));

        textview_job_salary = findViewById(R.id.job_salary);
        textview_postedDate = findViewById(R.id.textview_postedDate);
        textview_experience = findViewById(R.id.textview_experience);
        textview_location = findViewById(R.id.textview_location);
        textview_rank = findViewById(R.id.textview_rank);
        textview_department = findViewById(R.id.textview_department);
        textview_ship_type = findViewById(R.id.textview_ship_type);
        textview_expiry_date = findViewById(R.id.textview_expiry_date);
        textview_opening = findViewById(R.id.textview_opening);
        textview_job_desc = findViewById(R.id.tv_desc);

        tv_company_name = findViewById(R.id.company_name);

        tv_company_logo = findViewById(R.id.tv_company_logo);
        company_logo = findViewById(R.id.company_logo);
        cc_logo = findViewById(R.id.cc_logo);
        cc_tv_logo = findViewById(R.id.cc_tv_logo);
        tv_company_mobile = findViewById(R.id.tv_company_mobile);
        tv_company_email = findViewById(R.id.tv_company_email);


        refreshItems();

    }

    private void getPostJobDetails() {
        ApiInterface apiInterface = RetrofitBuilder.getRetrofitInstance().create(ApiInterface.class);
        Call<PostJobDetailsResponse> call = apiInterface.getPostJobDetailsByPostIdData(getfetchHashMap());
        call.enqueue(new Callback<PostJobDetailsResponse>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<PostJobDetailsResponse> call, Response<PostJobDetailsResponse> response) {
                if (response.code() == 200 && response.message().equals("OK")) {
                    source = response.body().getData();
                    Log.d("sourcePosts", source.toString());
                    if (!source.isEmpty()) {
                        for (PostJobDetailsRequest postJobDetailsRequest : source) {
                            textview_location.setText(postJobDetailsRequest.getCjmJobLocation());
                            textview_rank.setText(postJobDetailsRequest.getCjmRank());
                            textview_department.setText(postJobDetailsRequest.getCjmDepartment());
                            textview_ship_type.setText(postJobDetailsRequest.getCjmVesselType());
                            textview_expiry_date.setText(postJobDetailsRequest.getCjmExpiryDate());
                            textview_postedDate.setText("Posted : " + postJobDetailsRequest.getCjmStartDate());
                            textview_experience.setText(postJobDetailsRequest.getCjmExperienceInMonths() + " Months");
                            textview_job_salary.setText(postJobDetailsRequest.getCjmSalary());
                            textview_job_desc.setText(postJobDetailsRequest.getCjmDescription());
                            textview_opening.setText(postJobDetailsRequest.getCjmJobOpenings());

                            tv_company_name.setText(postJobDetailsRequest.getComp_name());
                            tv_company_email.setText(postJobDetailsRequest.getCompEmail());
                            tv_company_mobile.setText(postJobDetailsRequest.getCompMobile());

                            //REGEX FOR CHanging Localahost to IP
                            // replaceAll("localhost", "192.168.1.15");
                            String url = IMAGEURL + postJobDetailsRequest.getCompany_logo();
                            if (!postJobDetailsRequest.getCompany_logo().isEmpty()) {
                                cc_logo.setVisibility(View.VISIBLE);
                                cc_tv_logo.setVisibility(View.GONE);
                                Glide.with(PostDetailsActivity.this)
                                        .load(url)
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .into(company_logo);
                            }
                            else{
                                cc_logo.setVisibility(View.GONE);
                                cc_tv_logo.setVisibility(View.VISIBLE);
                                if(!postJobDetailsRequest.getComp_name().isEmpty()){
                                    Log.d("Company Name",
                                            new Constants().nameInitials(postJobDetailsRequest.getComp_name()));
                                    tv_company_logo.setText(new Constants().nameInitials(postJobDetailsRequest.getComp_name()));
                                }
                            }
                        }
                    }
                } else {
                    if (response.code() == 404 && response.message().equals("Not Found")) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            String message = jsonObject.getString("message");
                            if (!internetConnection.isConnected(PostDetailsActivity.this)) {
                                new ShowSnackbar().shortSnackbar(binding.getRoot(), getString(R.string.no_internet));
                            } else {
                                Log.e("in 404 fetch PostJobActivity", response.message());
                                Log.e("in", "else");
                                new ShowSnackbar().shortSnackbar(binding.getRoot(), getString(R.string.something_wrong));
                            }
                            Toast.makeText(PostDetailsActivity.this, message, Toast.LENGTH_LONG).show();
                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                            Log.e("in exception fetch PostJobActivity", e.getMessage());
                        }
                    } else {
                        if (!internetConnection.isConnected(PostDetailsActivity.this)) {
                            new ShowSnackbar().shortSnackbar(binding.getRoot(), getString(R.string.no_internet));
                        } else {
                            Log.e("in", "else");
                            new ShowSnackbar().shortSnackbar(binding.getRoot(), getString(R.string.something_wrong));
                        }
//                        Toast.makeText(PostDetails_Activity.this, "Some Thing Went Wrong !!", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(Call<PostJobDetailsResponse> call, Throwable t) {
                if (!internetConnection.isConnected(PostDetailsActivity.this)) {
                    new ShowSnackbar().shortSnackbar(binding.getRoot(), getString(R.string.no_internet));
                } else {
                    Log.e("infailure fetch PostJobActivity", t.getMessage());
                    new ShowSnackbar().shortSnackbar(binding.getRoot(), getString(R.string.something_wrong));
                }
                Toast.makeText(PostDetailsActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private HashMap<String, Object> getfetchHashMap() {
        HashMap<String, Object> map = new HashMap<>();
        Log.d("post_id", post_id);
        map.put("API_ACCESS_KEY", "ZkC6BDUzxz");
        map.put("jobPost_id", post_id);
        return map;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showInternetDialoag() {
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Please connect to Internet")
                .setCancelable(false)
                .setMessage("No Internet Connection Available")
                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        refreshItems();
                    }
                })
                .show();
    }

    private void refreshItems() {
        if (!internetConnection.isConnected(PostDetailsActivity.this)) {
            showInternetDialoag();
        }else{
            getPostJobDetails();
        }

    }
}