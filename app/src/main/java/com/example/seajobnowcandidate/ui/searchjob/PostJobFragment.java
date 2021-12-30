package com.example.seajobnowcandidate.ui.searchjob;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seajobnowcandidate.Adapter.DepartmentAdapter;
import com.example.seajobnowcandidate.Adapter.PostJobsAdapter;
import com.example.seajobnowcandidate.Adapter.RankAdapter;
import com.example.seajobnowcandidate.Adapter.SalaryAdapter;
import com.example.seajobnowcandidate.Adapter.ShipTypeAdapter;
import com.example.seajobnowcandidate.Entity.RetrofitBuilder;
import com.example.seajobnowcandidate.Entity.request.DepartmentRequest;
import com.example.seajobnowcandidate.Entity.request.PostJobDetailsRequest;
import com.example.seajobnowcandidate.Entity.request.PostSpinnerDataRequest;
import com.example.seajobnowcandidate.Entity.request.RankRequest;
import com.example.seajobnowcandidate.Entity.request.SalaryRequest;
import com.example.seajobnowcandidate.Entity.request.ShipTypeRequest;
import com.example.seajobnowcandidate.Entity.response.PostJobDetailsResponse;
import com.example.seajobnowcandidate.Entity.response.PostSpinnerResponse;
import com.example.seajobnowcandidate.Interface.ApiInterface;
import com.example.seajobnowcandidate.R;
import com.example.seajobnowcandidate.Utils.InternetConnection;
import com.example.seajobnowcandidate.actions.ShowSnackbar;
import com.example.seajobnowcandidate.databinding.FragmentPostjobBinding;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostJobFragment extends Fragment {
    PostJobViewModel postJobViewModel;
    FragmentPostjobBinding binding;
    ProgressDialog ringProgressDialog;
    //Spinner Lists
    List<RankRequest> rankRequestList;
    List<DepartmentRequest> departmentRequestList;
    List<ShipTypeRequest> shipTypeRequestList;
    List<SalaryRequest> salaryRequestList;

    //Selected Id & Name
    String selectedRankId = "", selectedDepartmentId = "", selectedShipId = "", selectedSalaryId;
    String selectedRankName = "", selectedDepartmentName = "", selectedShipName = "", selectedMinSalary = "",selectedMaxSalary = "",
            selectedLocation = "";

    //Adapters
    RankAdapter rankAdapter;
    DepartmentAdapter departmentAdapter;
    ShipTypeAdapter shipTypeAdapter;
    SalaryAdapter salaryAdapter;

    TextInputLayout inputJobName, inputspnDepartment, inputminSalary,inputmaxSalary, inputspnRank, inputspnShip, inputspnLocation;
    TextInputEditText etJobName, etLocation,etJobminSalary,etJobmaxSalary;
    AutoCompleteTextView spnDepartment, spnRank, spnShip;

    // Array list for recycler view data source
    List<PostJobDetailsRequest> source;

    // adapter class object
    PostJobsAdapter adapter;

    // Linear Layout Manager
    LinearLayoutManager linearLayoutManager;

    // Layout Manager
    RecyclerView.LayoutManager RecyclerViewLayoutManager;

    String title = "";

    BottomSheetDialog bottomSheetDialog;

    InternetConnection internetConnection;

    Chip salaryChip, maxsalaryChip, deptChip, rankChip, shipChip,locationChip;

    public PostJobFragment() {
        //Blank Constructor for fragment to fragment navigation
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getArguments() != null) {
            title = getArguments().getString("title");
            Log.d("FragitemTitle", title);
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        postJobViewModel = new ViewModelProvider(this).get(PostJobViewModel.class);
        internetConnection = new InternetConnection();
        binding = FragmentPostjobBinding.inflate(inflater, container, false);
        ringProgressDialog = new ProgressDialog(getContext());

        binding.etsearchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent(getContext(), FindJobActivity.class);
                startActivity(intent);*/
                showBottomSheetDialog();
            }
        });

        ringProgressDialog.setMessage("Loading...");
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();

//        AddItemsToRecyclerViewArrayList();
        if (!internetConnection.isConnected(getContext())) {
            showInternetDialoag();
        }else {
            getPostJobDetails();
        }

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!internetConnection.isConnected(getContext())) {
                    showInternetDialoag();
                }else {
                    getPostJobDetails();
                }
            }
        });

       /* binding.rvPostedJobs.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && binding.fabNote.getVisibility() == View.VISIBLE) {
                    binding.fabNote.hide();
                } else if (dy < 0 && binding.fabNote.getVisibility() != View.VISIBLE) {
                    binding.fabNote.show();
                }
            }
        });*/

    }

    private void getPostJobDetails() {
        ApiInterface apiInterface = RetrofitBuilder.getRetrofitInstance().create(ApiInterface.class);
        Call<PostJobDetailsResponse> call = apiInterface.getPostJonData(getfetchHashMap());
        call.enqueue(new Callback<PostJobDetailsResponse>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<PostJobDetailsResponse> call, Response<PostJobDetailsResponse> response) {
                if (response.code() == 200 && response.message().equals("OK")) {

                    ringProgressDialog.dismiss();
                    source = response.body().getData();
                    Log.d("sourcePosts", source.toString());
                    if (!source.isEmpty()) {
                        RecyclerViewLayoutManager = new LinearLayoutManager(getContext());
                        binding.rvPostedJobs.setLayoutManager(RecyclerViewLayoutManager);

                        adapter = new PostJobsAdapter(getContext(), source, R.layout.postjobs_items,true);
                        binding.rvPostedJobs.setAdapter(adapter);
                        binding.rvPostedJobs.setHasFixedSize(true);
                        binding.rvPostedJobs.setItemViewCacheSize(20);
                        binding.rvPostedJobs.setDrawingCacheEnabled(true);
                        binding.rvPostedJobs.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
                    } else {
                        binding.llDataFound.setVisibility(View.GONE);
                        binding.llNoDataFound.setVisibility(View.VISIBLE);
                        binding.retryBtn.setVisibility(View.GONE);
                        binding.imgInfo.setImageDrawable(getResources().getDrawable(R.drawable.no_data_found));
                        binding.noDataText.setText(getString(R.string.record_not_found));
                    }
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
                                if (!internetConnection.isConnected(getContext())) {
                                   showInternetDialoag();
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
                ringProgressDialog.dismiss();
                if (!internetConnection.isConnected(getContext())) {
                   showInternetDialoag();
                } else {
                    Log.e("infailure fetch PostJobfragment", t.getMessage());
                    binding.llDataFound.setVisibility(View.GONE);
                    binding.llNoDataFound.setVisibility(View.VISIBLE);
                    binding.retryBtn.setVisibility(View.GONE);
                    binding.imgInfo.setImageDrawable(getResources().getDrawable(R.drawable.no_data_found));
                    binding.noDataText.setText(getString(R.string.record_not_found));
                }
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private HashMap<String, Object> getfetchHashMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("API_ACCESS_KEY", "ZkC6BDUzxz");
        map.put("vt_id", "");
        return map;
    }

    private void getSearchPostedJob() {
        ApiInterface apiInterface = RetrofitBuilder.getRetrofitInstance().create(ApiInterface.class);
        Call<PostJobDetailsResponse> call = apiInterface.getSearchedJob(getSearchHashMap());
        call.enqueue(new Callback<PostJobDetailsResponse>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<PostJobDetailsResponse> call, Response<PostJobDetailsResponse> response) {
                if (response.code() == 200 && response.message().equals("OK")) {
                    ringProgressDialog.dismiss();
                    source = response.body().getData();
                    Log.d("sourcePosts search", source.toString());
                    if (!source.isEmpty()) {
                        binding.llDataFound.setVisibility(View.VISIBLE);
                        binding.llNoDataFound.setVisibility(View.GONE);
                        RecyclerViewLayoutManager = new LinearLayoutManager(getContext());
                        binding.rvPostedJobs.setLayoutManager(RecyclerViewLayoutManager);

                        adapter = new PostJobsAdapter(getContext(), source, R.layout.postjobs_items,true);
                        binding.rvPostedJobs.setAdapter(adapter);
                        binding.rvPostedJobs.setHasFixedSize(true);
                        binding.rvPostedJobs.setItemViewCacheSize(20);
                        binding.rvPostedJobs.setDrawingCacheEnabled(true);
                        binding.rvPostedJobs.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
                    } else {
                        binding.llDataFound.setVisibility(View.GONE);
                        binding.llNoDataFound.setVisibility(View.VISIBLE);
                        binding.retryBtn.setVisibility(View.GONE);
                        binding.imgInfo.setImageDrawable(getResources().getDrawable(R.drawable.no_data_found));
                        binding.noDataText.setText(getString(R.string.record_not_found));
                    }
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
                                if (!internetConnection.isConnected(getContext())) {
                                   showInternetDialoag();
                                } else {
                                    Log.e("in 404 search PostJobfragment", response.message());
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
                                Log.e("in exception search PostJobfragment", e.getMessage());
                            }
                        }
                    }

                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(Call<PostJobDetailsResponse> call, Throwable t) {
                ringProgressDialog.dismiss();
                if (!internetConnection.isConnected(getContext())) {
                   showInternetDialoag();
                } else {
                    Log.e("infailure search PostJobfragment", t.getMessage());
                    binding.llDataFound.setVisibility(View.GONE);
                    binding.llNoDataFound.setVisibility(View.VISIBLE);
                    binding.retryBtn.setVisibility(View.GONE);
                    binding.imgInfo.setImageDrawable(getResources().getDrawable(R.drawable.no_data_found));
                    binding.noDataText.setText(getString(R.string.record_not_found));
                }
//                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private HashMap<String, Object> getSearchHashMap() {
        Log.d("job_title",title);
        Log.d("rank_id",selectedRankId);
        Log.d("vessel_id",selectedShipId);
        Log.d("min_salary",selectedMinSalary);
        Log.d("max_salary",selectedMaxSalary);

        HashMap<String, Object> map = new HashMap<>();
        map.put("API_ACCESS_KEY", "ZkC6BDUzxz");
        map.put("job_title", title);
        map.put("rank_id", selectedRankId);
        map.put("vessel_id", selectedShipId);
        map.put("min_salary", selectedMinSalary);
        map.put("max_salary", selectedMaxSalary);

        return map;
    }

    public void fetchData() {
        String oldminsalary = "";
        String oldmaxsalary = "";
        String olddept = "";
        String oldrank = "";
        String oldship = "";
        String oldlocation = "";

        if (oldminsalary.equals(selectedMinSalary)) {
            salaryChip = getChip(binding.entryChipGroup, oldminsalary);
        } else {
            oldminsalary = selectedMinSalary;
            salaryChip = getChip(binding.entryChipGroup, oldminsalary);
        }

        if (oldmaxsalary.equals(selectedMaxSalary)) {
            maxsalaryChip = getChip(binding.entryChipGroup, oldmaxsalary);
        } else {
            oldmaxsalary = selectedMaxSalary;
            maxsalaryChip = getChip(binding.entryChipGroup, oldmaxsalary);
        }

        if(!selectedDepartmentId.equals("0")) {
            if (olddept.equals(selectedDepartmentName))
                deptChip = getChip(binding.entryChipGroup, olddept);
            else {
                olddept = selectedDepartmentName;
                deptChip = getChip(binding.entryChipGroup, olddept);
            }
        }

        if(!selectedRankId.equals("0")) {
            if (oldrank.equals(selectedRankName))
                rankChip = getChip(binding.entryChipGroup, oldrank);
            else {
                oldrank = selectedRankName;
                rankChip = getChip(binding.entryChipGroup, oldrank);
            }
        }

        if(!selectedShipId.equals("0")) {
            if (oldship.equals(selectedShipName))
                shipChip = getChip(binding.entryChipGroup, oldship);
            else {
                oldship = selectedShipName;
                shipChip = getChip(binding.entryChipGroup, oldship);
            }
        }

        if (oldlocation.equals(selectedLocation))
            locationChip = getChip(binding.entryChipGroup, oldlocation);
        else {
            oldlocation = selectedLocation;
            locationChip = getChip(binding.entryChipGroup, oldlocation);
        }

        binding.entryChipGroup.removeAllViews();

        if (salaryChip.getText().length() != 0)
            binding.entryChipGroup.addView(salaryChip);

        if (maxsalaryChip.getText().length() != 0)
            binding.entryChipGroup.addView(maxsalaryChip);

        if(!selectedDepartmentId.equals("0")) {
            if (deptChip.getText().length() != 0)
                binding.entryChipGroup.addView(deptChip);
        }
        else{
            binding.entryChipGroup.removeView(deptChip);
        }

        if(!selectedRankId.equals("0")) {
            if (rankChip.getText().length() != 0)
                binding.entryChipGroup.addView(rankChip);
        }
        else{
            binding.entryChipGroup.removeView(rankChip);
        }

        if(!selectedShipId.equals("0")) {
            if (shipChip.getText().length() != 0)
                binding.entryChipGroup.addView(shipChip);
        }
        else{
            binding.entryChipGroup.removeView(shipChip);
        }

        if (locationChip.getText().length() != 0)
            binding.entryChipGroup.addView(locationChip);
        else{
            binding.entryChipGroup.removeView(locationChip);
        }

        if (!title.isEmpty()) {
            binding.etsearchView.setText(title);
        }
    }

    private Chip getChip(final ChipGroup entryChipGroup, String text) {
        final Chip chip = new Chip(getContext());
        int paddingDp = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 10,
                getResources().getDisplayMetrics()
        );
        chip.setPadding(paddingDp, paddingDp, paddingDp, paddingDp);
        chip.setCloseIconEnabled(true);
        chip.setCheckable(false);
        chip.setClickable(true);
        chip.setText(text);
        chip.setOnCloseIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d("removed chip", String.valueOf(chip.getText().toString()));
//                Log.d("removed chipname", String.valueOf(chip.getTransitionName()));

                if(chip.getText().toString().contains(salaryChip.getText())){
                    Log.d("removed chip", "min salary");
                    selectedMinSalary.equals("");
                }

                if(chip.getText().toString().contains(maxsalaryChip.getText())){
                    Log.d("removed chip", "max salary");
                    selectedMaxSalary.equals("");
                }

                if(chip.getText().toString().trim().contains(selectedDepartmentName)){
                    Log.d("removed chip", "department");
                    selectedDepartmentId.equals("");
                    selectedDepartmentName.equals("");
                }

                if(chip.getText().toString().contains(selectedRankName)){
                    Log.d("removed chip", "rank");
                    selectedRankId.equals("");
                    selectedRankName.equals("");
                }

                if(chip.getText().toString().contains(selectedShipName)){
                    selectedShipId.equals("");
                    selectedShipName.equals("");
                }

                if(chip.getText().toString().contains(selectedLocation)){
                    selectedLocation.equals("");
                }

                entryChipGroup.removeView(chip);

                getSearchPostedJob();
            }
        });
        return chip;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    void refreshItems() {
        // Load items
        // ...
        if (!InternetConnection.checkConnection(getContext())) {
            // ringProgressDialog.dismiss();
            showInternetDialoag();
            //  Custom_Toast.warning(getContext(), getResources().getString(R.string.no_internet));
        } else {
//            salereturnDetails();
            getSearchPostedJob();
        }
        onItemsLoadComplete();
    }

    void onItemsLoadComplete() {
        // Update the adapter and notify data set changed
        // Stop refresh animation

//        binding.swipeRefreshLayout.setRefreshing(false);
    }

   /* public void no_internet() {
        binding.llDataFound.setVisibility(View.GONE);
        binding.llNoDataFound.setVisibility(View.VISIBLE);
        binding.retryBtn.setVisibility(View.VISIBLE);
        binding.imgInfo.setImageDrawable(getResources().getDrawable(R.drawable.no_internet_connection));
        binding.noDataText.setText(getResources().getString(R.string.no_internet));
    }

    public void something_wrong() {
        binding.llDataFound.setVisibility(View.GONE);
        binding.llNoDataFound.setVisibility(View.VISIBLE);
        binding.retryBtn.setVisibility(View.VISIBLE);
        binding.imgInfo.setImageDrawable(getResources().getDrawable(R.drawable.no_data_found));
        binding.noDataText.setText(getResources().getString(R.string.something_wrong));
    }*/

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // System.out.println("Landscape");
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            //  System.out.println("Potrait");
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        // ((MainActivity) getActivity()).getSupportActionBar().setTitle("Post Jobs");
    }

    private void showBottomSheetDialog() {
        bottomSheetDialog = new BottomSheetDialog(getContext());
        bottomSheetDialog.setContentView(R.layout.activity_find_job);
        bottomSheetDialog.setCancelable(true);

        //For Scrolling on Keyboard visible
        bottomSheetDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //Expand Dialog on Visible
        bottomSheetDialog.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);

        MaterialButton btn_search_job = bottomSheetDialog.findViewById(R.id.btn_search_job);
//        TextView cancel_post = bottomSheetDialog.findViewById(R.id.btn_cancel_post);

        bottomSheetDialog.show();

        inputJobName = bottomSheetDialog.findViewById(R.id.inputJobTitle);
        inputspnRank = bottomSheetDialog.findViewById(R.id.inputspnRank);
        inputspnDepartment = bottomSheetDialog.findViewById(R.id.inputspnDepartment);
        inputminSalary = bottomSheetDialog.findViewById(R.id.inputJobMinSalary);
        inputmaxSalary = bottomSheetDialog.findViewById(R.id.inputJobMaxSalary);
        inputspnShip = bottomSheetDialog.findViewById(R.id.inputspnShip);
        inputspnLocation = bottomSheetDialog.findViewById(R.id.inputspnLocation);

        etJobName = bottomSheetDialog.findViewById(R.id.etJobTitle);
        etJobminSalary = bottomSheetDialog.findViewById(R.id.etJobMinSalary);
        etJobmaxSalary = bottomSheetDialog.findViewById(R.id.etJobMaxSalary);

        spnRank = bottomSheetDialog.findViewById(R.id.spnRank);
        spnDepartment = bottomSheetDialog.findViewById(R.id.spnDepartment);
        spnShip = bottomSheetDialog.findViewById(R.id.spnShip);
        etLocation = bottomSheetDialog.findViewById(R.id.etLocation);

        if (!title.isEmpty())
            etJobName.setText(title);
        else
            etJobName.setText("");

        if (!selectedMinSalary.isEmpty())
            etJobminSalary.setText(selectedMinSalary);

        if (!selectedMaxSalary.isEmpty())
            etJobmaxSalary.setText(selectedMaxSalary);

        if (!selectedLocation.isEmpty())
            etLocation.setText(selectedLocation);

        if (!selectedMinSalary.isEmpty())
            etJobminSalary.setText(selectedMinSalary);
        else
            etJobminSalary.setText("");

        if (!selectedMaxSalary.isEmpty())
            etJobmaxSalary.setText(selectedMaxSalary);
        else
            etJobmaxSalary.setText("");

        if (!InternetConnection.checkConnection(getContext())) {
            new ShowSnackbar().shortSnackbar(binding.getRoot(), getString(R.string.no_internet));
            bottomSheetDialog.dismiss();
        } else {
            getSpinnerData();
        }

        btn_search_job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!InternetConnection.checkConnection(getContext())) {
                    new ShowSnackbar().shortSnackbar(binding.getRoot(), getString(R.string.no_internet));
                } else {
                    ringProgressDialog.setMessage("Loading...");
                    ringProgressDialog.setCancelable(false);
                    ringProgressDialog.show();

                    bottomSheetDialog.dismiss();
                    title = etJobName.getText().toString();
                    selectedMinSalary = etJobminSalary.getText().toString();
                    selectedMaxSalary = etJobmaxSalary.getText().toString();
                    selectedLocation = etLocation.getText().toString();
                    fetchData();
                    getSearchPostedJob();
                }
            }
        });


     /*   cancel_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(getApplicationContext(), "Copy is Clicked ", Toast.LENGTH_LONG).show();
                bottomSheetDialog.dismiss();
            }
        });*/
    }

    public void getSpinnerData() {
        ApiInterface apiInterface = RetrofitBuilder.getRetrofitInstance().create(ApiInterface.class);
        Call<PostSpinnerResponse> call = apiInterface.getPostSpinner();
        call.enqueue(new Callback<PostSpinnerResponse>() {
            @Override
            public void onResponse(Call<PostSpinnerResponse> call, Response<PostSpinnerResponse> response) {
                if (response.code() == 200 && response.message().equals("OK")) {
                    if (response.body().getStatusCode() == 1 && response.body().getStatusMessage().equals("Success")) {
                        PostSpinnerDataRequest registerData = response.body().getData();

                        //Rank Data
                        rankRequestList = registerData.getRank();

                        RankRequest rankRequest = new RankRequest();
                        rankRequest.setActualRankId("0");
                        rankRequest.setActualRankName("Select Rank");

                        rankRequestList.add(0,rankRequest);
                        rankAdapter = new RankAdapter(getContext(), R.layout.custom_spinner_item, rankRequestList);
                        spnRank.setThreshold(1);
                        spnRank.setAdapter(rankAdapter);

                        if (!selectedRankName.isEmpty())
                            spnRank.setText(selectedRankName);
                        else
                            spnRank.setText("");

                        if (!selectedDepartmentName.isEmpty())
                            spnDepartment.setText(selectedDepartmentName);
                        else
                            spnDepartment.setText("");

                        if (!selectedShipName.isEmpty())
                            spnShip.setText(selectedShipName);
                        else
                            spnShip.setText("");

                        spnRank.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {

                                RankRequest rankRequest = (RankRequest) adapterView.getItemAtPosition(pos);
                                Log.e("rankId", rankRequest.getActualRankId());
                                Log.e("rankName", rankRequest.getActualRankName());
                                selectedRankId = rankRequest.getActualRankId();
                                selectedRankName = rankRequest.getActualRankName();
//                                spnRank.setText(selectedRankName);
                            }
                        });

                        //Department Data
                        departmentRequestList = registerData.getDepartment();

                        DepartmentRequest departmentRequest = new DepartmentRequest();
                        departmentRequest.setCdgmId("0");
                        departmentRequest.setCdgmDesignation("Select Department");
                        departmentRequestList.add(0,departmentRequest);

                        departmentAdapter = new DepartmentAdapter(getContext(), R.layout.custom_spinner_item, departmentRequestList);
                        spnDepartment.setThreshold(1);
                        spnDepartment.setAdapter(departmentAdapter);


                        spnDepartment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                                DepartmentRequest departmentRequest = (DepartmentRequest) adapterView.getItemAtPosition(pos);
                                selectedDepartmentId = departmentRequest.getCdgmId();
                                selectedDepartmentName = departmentRequest.getCdgmDesignation();
                                Log.e("deptId", selectedDepartmentId);
                                Log.e("deptName", selectedDepartmentName);
                            }
                        });

                        //Salary Data
                       /* salaryRequestList = registerData.getSalary();
                        salaryAdapter = new SalaryAdapter(getContext(), R.layout.custom_spinner_item, salaryRequestList);
                        spnSalary.setThreshold(1);
                        spnSalary.setAdapter(salaryAdapter);

                        spnSalary.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                                Log.e("salaryId", salaryRequestList.get(pos).getCsmId());
                                selectedSalaryId = salaryRequestList.get(pos).getCsmId();
                                selectedSalaryName = salaryRequestList.get(pos).getSalary();
                                spnSalary.setText(selectedSalaryName, false);
                            }
                        });
*/
                        //Ship Type Data
                        shipTypeRequestList = registerData.getShipType();

                        ShipTypeRequest shipTypeRequest = new ShipTypeRequest();
                        shipTypeRequest.setVtId("0");
                        shipTypeRequest.setVtName("Select Vessel");
                        shipTypeRequestList.add(0,shipTypeRequest);

                        shipTypeAdapter = new ShipTypeAdapter(getContext(), R.layout.custom_spinner_item, shipTypeRequestList);
                        spnShip.setThreshold(1);
                        spnShip.setAdapter(shipTypeAdapter);

                        spnShip.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                                ShipTypeRequest shipTypeRequest = (ShipTypeRequest) adapterView.getItemAtPosition(pos);
                                selectedShipId = shipTypeRequest.getVtId();
                                selectedShipName = shipTypeRequest.getVtName();
                                Log.e("ShipId", selectedShipId);
                                Log.e("Shipname", selectedShipName);
                            }
                        });

                    } else {
                        if (response.body().getStatusCode() == 0) {
                            if (response.body().getStatusMessage().equals("Fail")) {
                                Toast.makeText(getContext(), response.body().getStatusMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getContext(), getString(R.string.something_wrong), Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<PostSpinnerResponse> call, Throwable t) {

            }
        });
    }

    private void showInternetDialoag() {
        final AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setTitle("Please connect to Internet")
                .setCancelable(false)
                .setMessage("No Internet Connection Available")
                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        refreshItems();
                        getSearchPostedJob();
                    }
                })
                .show();
    }
}