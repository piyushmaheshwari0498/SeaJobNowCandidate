package com.example.seajobnowcandidate.ui.PostJob;

import android.content.Context;
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
import android.widget.TextView;
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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostJobFragment extends Fragment {
    PostJobViewModel postJobViewModel;
    FragmentPostjobBinding binding;

    //Spinner Lists
    List<RankRequest> rankRequestList;
    List<DepartmentRequest> departmentRequestList;
    List<ShipTypeRequest> shipTypeRequestList;
    List<SalaryRequest> salaryRequestList;

    //Selected Id & Name
    String selectedRankId, selectedDepartmentId, selectedShipId, selectedSalaryId;
    String selectedRankName = "", selectedDepartmentName = "", selectedShipName = "", selectedSalaryName = "";

    //Adapters
    RankAdapter rankAdapter;
    DepartmentAdapter departmentAdapter;
    ShipTypeAdapter shipTypeAdapter;
    SalaryAdapter salaryAdapter;

    TextInputLayout inputJobName, inputspnDepartment, inputspnSalary, inputspnRank, inputspnShip, inputspnLocation;
    TextInputEditText etJobName, etLocation;
    AutoCompleteTextView spnSalary, spnDepartment, spnRank, spnShip;

    // Array list for recycler view data source
    ArrayList<PostJobDetailsRequest> source;

    // adapter class object
    PostJobsAdapter adapter;

    // Linear Layout Manager
    LinearLayoutManager linearLayoutManager;

    // Layout Manager
    RecyclerView.LayoutManager RecyclerViewLayoutManager;

    String title = "";

    BottomSheetDialog bottomSheetDialog;

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

        binding = FragmentPostjobBinding.inflate(inflater, container, false);

        binding.etsearchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent(getContext(), FindJobActivity.class);
                startActivity(intent);*/
                showBottomSheetDialog();
            }
        });

        AddItemsToRecyclerViewArrayList();

        RecyclerViewLayoutManager = new LinearLayoutManager(getContext());

        // Set LayoutManager on Recycler View
        binding.rvPostedJobs.setLayoutManager(RecyclerViewLayoutManager);

        adapter = new PostJobsAdapter(getContext(), source, R.layout.postjobs_items);
        binding.rvPostedJobs.setAdapter(adapter);


        return binding.getRoot();
    }

    public void fetchData() {
        Chip salaryChip, deptChip, rankChip, shipChip;
        String oldsalary = "";
        String olddept = "";
        String oldrank = "";
        String oldship = "";

        if (oldsalary.equals(selectedSalaryName)) {
            salaryChip = getChip(binding.entryChipGroup, oldsalary);
        } else {
            oldsalary = selectedSalaryName;
            salaryChip = getChip(binding.entryChipGroup, oldsalary);
        }

        if (olddept.equals(selectedDepartmentName))
            deptChip = getChip(binding.entryChipGroup, olddept);
        else {
            olddept = selectedDepartmentName;
            deptChip = getChip(binding.entryChipGroup, olddept);
        }

        if (oldrank.equals(selectedRankName))
            rankChip = getChip(binding.entryChipGroup, oldrank);
        else {
            oldrank = selectedRankName;
            rankChip = getChip(binding.entryChipGroup, oldrank);
        }

        if (oldship.equals(selectedShipName))
            shipChip = getChip(binding.entryChipGroup, oldship);
        else {
            oldship = selectedShipName;
            shipChip = getChip(binding.entryChipGroup, oldship);
        }

        binding.entryChipGroup.removeAllViews();

        if (salaryChip.getText().length() != 0)
            binding.entryChipGroup.addView(salaryChip);

        if (deptChip.getText().length() != 0)
            binding.entryChipGroup.addView(deptChip);

        if (rankChip.getText().length() != 0)
            binding.entryChipGroup.addView(rankChip);

        if (shipChip.getText().length() != 0)
            binding.entryChipGroup.addView(shipChip);

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
                entryChipGroup.removeView(chip);
            }
        });
        return chip;
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
        if (!InternetConnection.checkConnection(getContext())) {
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
    public void AddItemsToRecyclerViewArrayList() {
        // Adding items to ArrayList
        source = new ArrayList<>();
        source.add(new PostJobDetailsRequest("UI/UX Designer", "Enginer", "Master", "₹10000 - ₹20000 per hour", "Oil Tanker", "Indian Ocean", "10/10/2021", "12/10/2021"));
        source.add(new PostJobDetailsRequest("Kitchen Jr. Chef", "Hotel", "Chef", "₹10000 - ₹20000 per hour", "Oil Tanker", "Indian Ocean", "10/10/2021", "12/10/2021"));
        source.add(new PostJobDetailsRequest("Engine", "Enginer", "Master", "₹10000 - ₹20000 per hour", "Oil Tanker", "Indian Ocean", "10/10/2021", "12/10/2021"));
        source.add(new PostJobDetailsRequest("UI/UX Designer", "Enginer", "Master", "₹10000 - ₹20000 per hour", "Oil Tanker", "Indian Ocean", "10/10/2021", "12/10/2021"));
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
        TextView cancel_post = bottomSheetDialog.findViewById(R.id.btn_cancel_post);

        bottomSheetDialog.show();

        inputJobName = bottomSheetDialog.findViewById(R.id.inputJobTitle);
        inputspnRank = bottomSheetDialog.findViewById(R.id.inputspnRank);
        inputspnDepartment = bottomSheetDialog.findViewById(R.id.inputspnDepartment);
        inputspnSalary = bottomSheetDialog.findViewById(R.id.inputspnSalary);
        inputspnShip = bottomSheetDialog.findViewById(R.id.inputspnShip);
        inputspnLocation = bottomSheetDialog.findViewById(R.id.inputspnLocation);

        etJobName = bottomSheetDialog.findViewById(R.id.etJobTitle);

        spnRank = bottomSheetDialog.findViewById(R.id.spnRank);
        spnDepartment = bottomSheetDialog.findViewById(R.id.spnDepartment);
        spnShip = bottomSheetDialog.findViewById(R.id.spnShip);
        spnSalary = bottomSheetDialog.findViewById(R.id.spnSalary);
        etLocation = bottomSheetDialog.findViewById(R.id.etLocation);

        if (!title.isEmpty())
            etJobName.setText(title);

        if (!InternetConnection.checkConnection(getContext())) {
            new ShowSnackbar().shortSnackbar(binding.getRoot(), getString(R.string.no_internet));
        } else {
            getSpinnerData();
        }

        btn_search_job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!InternetConnection.checkConnection(getContext())) {
                    new ShowSnackbar().shortSnackbar(binding.getRoot(), getString(R.string.no_internet));
                } else {
                    bottomSheetDialog.dismiss();
                    title = etJobName.getText().toString();
                    fetchData();
                }
            }
        });


        cancel_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(getApplicationContext(), "Copy is Clicked ", Toast.LENGTH_LONG).show();
                bottomSheetDialog.dismiss();
            }
        });
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
                        rankAdapter = new RankAdapter(getContext(), R.layout.custom_spinner_item, rankRequestList);
                        spnRank.setThreshold(1);
                        spnRank.setAdapter(rankAdapter);

                        if (!selectedRankName.isEmpty())
                            spnRank.setText(selectedRankName);

                        if (!selectedDepartmentName.isEmpty())
                            spnDepartment.setText(selectedDepartmentName);

                        if (!selectedSalaryName.isEmpty())
                            spnSalary.setText(selectedSalaryName);

                        if (!selectedShipName.isEmpty())
                            spnShip.setText(selectedShipName);

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
                        salaryRequestList = registerData.getSalary();
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

                        //Ship Type Data
                        shipTypeRequestList = registerData.getShipType();
                        shipTypeAdapter = new ShipTypeAdapter(getContext(), R.layout.custom_spinner_item, shipTypeRequestList);
                        spnShip.setThreshold(1);
                        spnShip.setAdapter(shipTypeAdapter);

                        spnShip.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                                ShipTypeRequest shipTypeRequest = (ShipTypeRequest) adapterView.getItemAtPosition(pos);
                                selectedShipId = shipTypeRequest.getVtId();
                                selectedShipName = shipTypeRequest.getVtName();
                                Log.e("deptId", selectedDepartmentId);
                                Log.e("deptName", selectedDepartmentName);
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