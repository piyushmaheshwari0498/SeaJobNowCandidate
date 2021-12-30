package com.example.seajobnowcandidate.ui.home;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.seajobnowcandidate.Activity.LoginActivity;
import com.example.seajobnowcandidate.Adapter.HomeCategoryAdapter;
import com.example.seajobnowcandidate.Adapter.HomeSuggestedAdapter;
import com.example.seajobnowcandidate.Adapter.PostJobsAdapter;
import com.example.seajobnowcandidate.Entity.RetrofitBuilder;
import com.example.seajobnowcandidate.Entity.request.HomeCategoryRequest;
import com.example.seajobnowcandidate.Entity.request.PostJobDetailsRequest;
import com.example.seajobnowcandidate.Entity.request.VesselRequest;
import com.example.seajobnowcandidate.Entity.response.VesselResponse;
import com.example.seajobnowcandidate.Interface.ApiInterface;
import com.example.seajobnowcandidate.MainActivity;
import com.example.seajobnowcandidate.R;
import com.example.seajobnowcandidate.Session.AppSharedPreference;
import com.example.seajobnowcandidate.Utils.Constants;
import com.example.seajobnowcandidate.Utils.InternetConnection;
import com.example.seajobnowcandidate.databinding.HomeFragmentBinding;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    List<VesselRequest> categoryRequestList;
    List<HomeCategoryRequest> categoryRequestList2;
    List<PostJobDetailsRequest> source;
    HomeCategoryAdapter categoryAdapter;
    HomeSuggestedAdapter categoryAdapter2;
    PostJobsAdapter postJobsAdapter;
    ProgressDialog ringProgressDialog;
    AppSharedPreference appSharedPreference;
    HomeViewModel mViewModel;
    HomeFragmentBinding binding;
    InternetConnection internetConnection;

    // Linear Layout Manager
    LinearLayoutManager HorizontalLayout;



    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding = HomeFragmentBinding.inflate(inflater, container, false);

        appSharedPreference = AppSharedPreference.getAppSharedPreference(getContext());
        ringProgressDialog = new ProgressDialog(getContext());
        internetConnection = new InternetConnection();

//        BottomNavigationView navBar = getActivity().findViewById(R.id.bottom_navigation_view);

        ringProgressDialog.setMessage("Loading...");
        ringProgressDialog.show();

        refreshItems();

       /* binding.categoryRV.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && binding.llSuggestedDataFound.getVisibility() == View.VISIBLE) {
                    binding.llSuggestedDataFound.setVisibility(View.GONE);
                } else if (dy < 0 && binding.llSuggestedDataFound.getVisibility() != View.VISIBLE) {
                    binding.llSuggestedDataFound.setVisibility(View.VISIBLE);
                }
            }
        });*/

        binding.retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshItems();
            }
        });

//        binding.categoryRV.setLayoutManager(new MyLinearLayoutManager(getActivity(),1,true));


        return binding.getRoot();
    }

    void refreshItems() {
        // Load items
        // ...
        if (!internetConnection.checkConnection(getContext())) {
            ringProgressDialog.dismiss();
            /*binding.llDataFound.setVisibility(View.GONE);
            binding.llNoDataFound.setVisibility(View.VISIBLE);
            binding.retryBtn.setVisibility(View.VISIBLE);
            binding.imgInfo.setImageDrawable(getResources().getDrawable(R.drawable.no_internet_connection));
            binding.noDataText.setText(getResources().getString(R.string.no_internet));*/
            //  Custom_Toast.warning(getContext(), getResources().getString(R.string.no_internet));
            showInternetDialoag();
        } else {
            binding.llDataFound.setVisibility(View.VISIBLE);
            binding.llNoDataFound.setVisibility(View.GONE);
            AddItemsToRecyclerViewArrayList();
        }
//        onItemsLoadComplete();
    }
   /* void onItemsLoadComplete() {
        // Update the adapter and notify data set changed
        // Stop refresh animation

        binding.swipeRefreshLayout.setRefreshing(false);
    }*/
    // Function to add items in RecyclerView.
    public void AddItemsToRecyclerViewArrayList()
    {
        source = new ArrayList<>();
        source.add(new PostJobDetailsRequest("Engineer","Engineer","Sr.Eng","₹10000 - ₹20000 per hour","Oil Tanker","Indian Ocean","10/10/2021","12/10/2021","",""));
        source.add(new PostJobDetailsRequest("Kitchen Jr. Chef","Hotel","Chef","₹10000 - ₹20000 per hour","Oil Tanker","Indian Ocean","10/10/2021","12/10/2021","",""));
        source.add(new PostJobDetailsRequest("Engineer","Engineer","Sr.Eng","₹10000 - ₹20000 per hour","Oil Tanker","Indian Ocean","10/10/2021","12/10/2021","",""));

        // Adding items to ArrayList
        /*categoryRequestList = new ArrayList<>();
        categoryRequestList.add(new HomeCategoryRequest("Oil Tanker",R.drawable.ship_cargo));
        categoryRequestList.add(new HomeCategoryRequest("Chemical Tanker",R.drawable.bluk));
        categoryRequestList.add(new HomeCategoryRequest("Container",R.drawable.ship_cargo));
        categoryRequestList.add(new HomeCategoryRequest("Bluk Carrier",R.drawable.bluk));
        categoryRequestList.add(new HomeCategoryRequest("General Cargo",R.drawable.ship_cargo));
        categoryRequestList.add(new HomeCategoryRequest("Cement Carrier",R.drawable.bluk));
        categoryRequestList.add(new HomeCategoryRequest("Car Carrier",R.drawable.ship_cargo));
        categoryRequestList.add(new HomeCategoryRequest("Gas Tanker",R.drawable.bluk));*/
        /*categoryRequestList.add(new HomeCategoryRequest("Water-Jet Propulsion",R.drawable.home4));
        categoryRequestList.add(new HomeCategoryRequest("Gas/Tri Fuel Propulsion",R.drawable.home6));*/

        categoryRequestList2 = new ArrayList<>();
        categoryRequestList2.add(new HomeCategoryRequest("Saved",R.drawable.saved));
        categoryRequestList2.add(new HomeCategoryRequest("Applied",R.drawable.applied));
        categoryRequestList2.add(new HomeCategoryRequest("Archived",R.drawable.ahts));
//        categoryRequestList2.add(new HomeCategoryRequest("Interviews",0));


        categoryAdapter2 = new HomeSuggestedAdapter(categoryRequestList2, getActivity());
        binding.statusRV.setAdapter(categoryAdapter2);

       /* postJobsAdapter = new PostJobsAdapter(getActivity(),source,R.layout.home_postjobs_items,false);
        binding.suggestedRV.setAdapter(postJobsAdapter);*/

        // for Recycler view
        HorizontalLayout = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        binding.statusRV.setLayoutManager(HorizontalLayout);

        getPostJobDetails();
    }

    private void getPostJobDetails() {
        ApiInterface apiInterface = RetrofitBuilder.getRetrofitInstance().create(ApiInterface.class);
        Call<VesselResponse> call = apiInterface.getVesselType(getfetchHashMap());
        call.enqueue(new Callback<VesselResponse>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<VesselResponse> call, Response<VesselResponse> response) {
                if (response.code() == 200 && response.message().equals("OK")) {
                    ringProgressDialog.dismiss();
//                    if (response.body().getStatusCode() == 1 && response.body().getMessage().equals("Success")) {
                    categoryRequestList = response.body().getData().getVesseltype();

                    Log.d("categoryRequestList", categoryRequestList.toString());

                    if (categoryRequestList.size()!=0) {
                        categoryAdapter = new HomeCategoryAdapter(categoryRequestList, getActivity());
                        binding.categoryRV.setAdapter(categoryAdapter);
                        LinearLayoutManager layoutManager = new GridLayoutManager(getActivity(), 4);
                        binding.categoryRV.setLayoutManager(layoutManager);
                        binding.categoryRV.setHasFixedSize(true);
                        binding.categoryRV.setItemViewCacheSize(20);
                        binding.categoryRV.setDrawingCacheEnabled(true);
                        binding.categoryRV.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

                    }
//                    }
                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(Call<VesselResponse> call, Throwable t) {
                ringProgressDialog.dismiss();
                Log.e("categoryRequestList failure : ", t.getMessage());
//                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private HashMap<String, Object> getfetchHashMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("API_ACCESS_KEY", "ZkC6BDUzxz");
        map.put("sc_id", appSharedPreference.getString(Constants.INTENT_KEYS.KEY_CANDIDATE_ID));
        return map;
    }

    private void showInternetDialoag() {
        final AlertDialog dialog = new AlertDialog.Builder(getContext())
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
}