package com.example.seajobnowcandidate.ui.PostJob;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.seajobnowcandidate.Activity.FindJobActivity;
import com.example.seajobnowcandidate.Adapter.DepartmentAdapter;
import com.example.seajobnowcandidate.Adapter.PostJobsAdapter;
import com.example.seajobnowcandidate.Adapter.RankAdapter;
import com.example.seajobnowcandidate.Adapter.SalaryAdapter;
import com.example.seajobnowcandidate.Adapter.ShipTypeAdapter;
import com.example.seajobnowcandidate.Entity.request.DepartmentRequest;
import com.example.seajobnowcandidate.Entity.request.EmployementTypeRequest;
import com.example.seajobnowcandidate.Entity.request.PostSpinnerDataRequest;
import com.example.seajobnowcandidate.Entity.request.RankRequest;
import com.example.seajobnowcandidate.Entity.request.SalaryRequest;
import com.example.seajobnowcandidate.Entity.request.ShipTypeRequest;
import com.example.seajobnowcandidate.Entity.response.PostSpinnerResponse;
import com.example.seajobnowcandidate.Interface.ApiInterface;
import com.example.seajobnowcandidate.Model.PostJobs;
import com.example.seajobnowcandidate.R;
import com.example.seajobnowcandidate.Utils.InternetConnection;
import com.example.seajobnowcandidate.Entity.RetrofitBuilder;
import com.example.seajobnowcandidate.databinding.FragmentPostjobBinding;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostJobFragment extends Fragment {
    PostJobViewModel postJobViewModel;
    FragmentPostjobBinding binding;
    DatePickerDialog datePickerDialog;
    Calendar calendar;
    int mDay, mMonth, mYear;
    //Spinner Lists
    List<RankRequest> rankRequestList;
    List<DepartmentRequest> departmentRequestList;
    List<EmployementTypeRequest> employementTypeRequestList;
    List<ShipTypeRequest> shipTypeRequestList;
    List<SalaryRequest> salaryRequestList;

    //Selected Id & Name
    String selectedRankId,selectedDepartmentId,selectedShipId,selectedSalaryId,selectedEmployementTypeId;
    String selectedRankName,selectedDepartmentName,selectedShipName,selectedSalaryName,selectedEmployementTypeMame;

    //Adapters
    RankAdapter rankAdapter;
    DepartmentAdapter departmentAdapter;
    ShipTypeAdapter shipTypeAdapter;
    SalaryAdapter salaryAdapter;
    String fromdate, todate;
    TextInputLayout inputJobName,inputStartDate,inputEndDate,inputspnSalary;
    TextInputLayout inputspnDepartment,inputspnRank,inputspnShip,inputspnLocation;
    TextInputEditText etJobName,startDate,endDate;
    AutoCompleteTextView spnSalary,spnDepartment,spnRank,spnShip,spnLocation,spnEmployementType;

    InternetConnection internetConnection;

    // Array list for recycler view data source
    ArrayList<PostJobs> source;

    // adapter class object
    PostJobsAdapter adapter;

    // Linear Layout Manager
    LinearLayoutManager linearLayoutManager;

    // Layout Manager
    RecyclerView.LayoutManager RecyclerViewLayoutManager;

    public PostJobFragment() {
        //Blank Constructor for fragment to fragment navigation
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

//      getActivity().setTitle(R.string.menu_post_job);

        postJobViewModel = new ViewModelProvider(this).get(PostJobViewModel.class);

        internetConnection = new InternetConnection();

        binding = FragmentPostjobBinding.inflate(inflater, container, false);

        binding.etsearchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), FindJobActivity.class);
                startActivity(intent);
            }
        });

        AddItemsToRecyclerViewArrayList();

        RecyclerViewLayoutManager = new LinearLayoutManager(getContext());

        // Set LayoutManager on Recycler View
        binding.rvPostedJobs.setLayoutManager(RecyclerViewLayoutManager);

        adapter = new PostJobsAdapter(getContext(),source,R.layout.postjobs_items);
        binding.rvPostedJobs.setAdapter(adapter);


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        refreshItems();

        binding.retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshItems();
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    void refreshItems() {
        // Load items
        // ...
        if (!internetConnection.checkConnection(getContext())) {
            // ringProgressDialog.dismiss();
            no_internet();
            //  Custom_Toast.warning(getContext(), getResources().getString(R.string.no_internet));
        } else {
            binding.llDataFound.setVisibility(View.VISIBLE);
            binding.llNoDataFound.setVisibility(View.GONE);
//            salereturnDetails();
        }
        onItemsLoadComplete();
    }

    void onItemsLoadComplete() {
        // Update the adapter and notify data set changed
        // Stop refresh animation

//        binding.swipeRefreshLayout.setRefreshing(false);
    }

    public void no_internet() {
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
    }

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

    // Function to add items in RecyclerView.
    public void AddItemsToRecyclerViewArrayList()
    {
        // Adding items to ArrayList
        source = new ArrayList<>();
        source.add(new PostJobs("UI/UX Designer","Enginer","Master","₹10000 - ₹20000 per hour","Oil Tanker","Indian Ocean","10/10/2021","12/10/2021"));
        source.add(new PostJobs("Kitchen Jr. Chef","Hotel","Chef","₹10000 - ₹20000 per hour","Oil Tanker","Indian Ocean","10/10/2021","12/10/2021"));
        source.add(new PostJobs("Engine","Enginer","Master","₹10000 - ₹20000 per hour","Oil Tanker","Indian Ocean","10/10/2021","12/10/2021"));
        source.add(new PostJobs("UI/UX Designer","Enginer","Master","₹10000 - ₹20000 per hour","Oil Tanker","Indian Ocean","10/10/2021","12/10/2021"));
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
                        rankAdapter = new RankAdapter(getContext(),R.layout.custom_spinner_item, rankRequestList);
                        spnRank.setThreshold(1);
                        spnRank.setAdapter(rankAdapter);


                        spnRank.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                                Log.e("rankId", rankRequestList.get(pos).getActualRankId());
                                selectedRankId = rankRequestList.get(pos).getActualRankId();
                                selectedRankName = rankRequestList.get(pos).getActualRankName();
//                                spnRank.setText(selectedRankName);
                            }
                        });

                        //Department Data
                        departmentRequestList = registerData.getDepartment();
                        departmentAdapter = new DepartmentAdapter(getContext(),R.layout.custom_spinner_item, departmentRequestList);
                        spnDepartment.setThreshold(1);
                        spnDepartment.setAdapter(departmentAdapter);


                        spnDepartment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                                Log.e("departmentId", departmentRequestList.get(pos).getCdgmId());
                                selectedDepartmentId = departmentRequestList.get(pos).getCdgmId();
                                selectedDepartmentName = departmentRequestList.get(pos).getCdgmDesignation();
//                                spnDepartment.setText(selectedDepartmentName);
                            }
                        });

                        //Salary Data
                        salaryRequestList = registerData.getSalary();
                        salaryAdapter = new SalaryAdapter(getContext(),R.layout.custom_spinner_item, salaryRequestList);
                        spnSalary.setThreshold(1);
                        spnSalary.setAdapter(salaryAdapter);


                        spnSalary.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                                Log.e("salaryId", salaryRequestList.get(pos).getCsmId());
                                selectedSalaryId = salaryRequestList.get(pos).getCsmId();
                                selectedSalaryName = salaryRequestList.get(pos).getSalary();
//                                spnSalary.setText(selectedSalaryName);
                            }
                        });

                        //Ship Type Data
                        shipTypeRequestList = registerData.getShipType();
                        shipTypeAdapter = new ShipTypeAdapter(getContext(),R.layout.custom_spinner_item, shipTypeRequestList);
                        spnShip.setThreshold(1);
                        spnShip.setAdapter(shipTypeAdapter);


                        spnShip.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                                Log.e("shipId", shipTypeRequestList.get(pos).getVtId());
                                selectedShipId = shipTypeRequestList.get(pos).getVtId();
                                selectedShipName = shipTypeRequestList.get(pos).getVtName();
//                                spnShip.setText(selectedShipName);
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

}