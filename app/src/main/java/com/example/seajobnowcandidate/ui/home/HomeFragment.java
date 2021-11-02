package com.example.seajobnowcandidate.ui.home;

import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.seajobnowcandidate.Adapter.HomeCategoryAdapter;
import com.example.seajobnowcandidate.Adapter.HomeSuggestedAdapter;
import com.example.seajobnowcandidate.Adapter.PostJobsAdapter;
import com.example.seajobnowcandidate.Entity.request.HomeCategoryRequest;
import com.example.seajobnowcandidate.Model.PostJobs;
import com.example.seajobnowcandidate.R;
import com.example.seajobnowcandidate.Session.AppSharedPreference;
import com.example.seajobnowcandidate.Utils.AutoFitGridLayoutManager;
import com.example.seajobnowcandidate.Utils.InternetConnection;
import com.example.seajobnowcandidate.databinding.HomeFragmentBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    List<HomeCategoryRequest> categoryRequestList;
    List<HomeCategoryRequest> categoryRequestList2;
    List<PostJobs> source;
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

        internetConnection = new InternetConnection();

        BottomNavigationView navBar = getActivity().findViewById(R.id.bottom_navigation_view);

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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        // TODO: Use the ViewModel
    }

    void refreshItems() {
        // Load items
        // ...

        if (!internetConnection.checkConnection(getContext())) {
            ringProgressDialog.dismiss();
            binding.llDataFound.setVisibility(View.GONE);
            binding.llNoDataFound.setVisibility(View.VISIBLE);
            binding.retryBtn.setVisibility(View.VISIBLE);
            binding.imgInfo.setImageDrawable(getResources().getDrawable(R.drawable.no_internet_connection));
            binding.noDataText.setText(getResources().getString(R.string.no_internet));
            //  Custom_Toast.warning(getContext(), getResources().getString(R.string.no_internet));
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
        source.add(new PostJobs("UI/UX Designer","Enginer","Master","₹10000 - ₹20000 per hour","Oil Tanker","Indian Ocean","10/10/2021","12/10/2021"));
        source.add(new PostJobs("Kitchen Jr. Chef","Hotel","Chef","₹10000 - ₹20000 per hour","Oil Tanker","Indian Ocean","10/10/2021","12/10/2021"));
        source.add(new PostJobs("Engine","Enginer","Master","₹10000 - ₹20000 per hour","Oil Tanker","Indian Ocean","10/10/2021","12/10/2021"));

        // Adding items to ArrayList
        categoryRequestList = new ArrayList<>();
        categoryRequestList.add(new HomeCategoryRequest("Oil Tanker",R.drawable.ship_cargo));
        categoryRequestList.add(new HomeCategoryRequest("Chemical Tanker",R.drawable.bluk));
        categoryRequestList.add(new HomeCategoryRequest("Container",R.drawable.ship_cargo));
        categoryRequestList.add(new HomeCategoryRequest("Bluk Carrier",R.drawable.bluk));
        categoryRequestList.add(new HomeCategoryRequest("General Cargo",R.drawable.ship_cargo));
        categoryRequestList.add(new HomeCategoryRequest("Cement Carrier",R.drawable.bluk));
        categoryRequestList.add(new HomeCategoryRequest("Car Carrier",R.drawable.ship_cargo));
        categoryRequestList.add(new HomeCategoryRequest("Gas Tanker",R.drawable.bluk));
        /*categoryRequestList.add(new HomeCategoryRequest("Water-Jet Propulsion",R.drawable.home4));
        categoryRequestList.add(new HomeCategoryRequest("Gas/Tri Fuel Propulsion",R.drawable.home6));*/

        categoryRequestList2 = new ArrayList<>();
        categoryRequestList2.add(new HomeCategoryRequest("Saved",0));
        categoryRequestList2.add(new HomeCategoryRequest("Applied",0));
        categoryRequestList2.add(new HomeCategoryRequest("Archived",0));
        categoryRequestList2.add(new HomeCategoryRequest("Interviews",0));


        categoryAdapter = new HomeCategoryAdapter(categoryRequestList, getActivity());
        binding.categoryRV.setAdapter(categoryAdapter);

        categoryAdapter2 = new HomeSuggestedAdapter(categoryRequestList2, getActivity());
        binding.statusRV.setAdapter(categoryAdapter2);

        postJobsAdapter = new PostJobsAdapter(getActivity(),source,R.layout.home_postjobs_items);
        binding.suggestedRV.setAdapter(postJobsAdapter);

       /* AutoFitGridLayoutManager autolayoutManager = new AutoFitGridLayoutManager(getActivity(), 500) {
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        };*/
        LinearLayoutManager layoutManager = new GridLayoutManager(getActivity(), 4);
        binding.categoryRV.setLayoutManager(layoutManager);

//        binding.categoryRV.setLayoutManager(autolayoutManager);
        // Set Horizontal Layout Manager
        // for Recycler view
        HorizontalLayout = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        binding.statusRV.setLayoutManager(HorizontalLayout);

        HorizontalLayout = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        binding.suggestedRV.setLayoutManager(HorizontalLayout);
        // Set adapter on recycler view



    }

}