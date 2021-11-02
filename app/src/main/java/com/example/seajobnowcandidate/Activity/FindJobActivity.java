package com.example.seajobnowcandidate.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.seajobnowcandidate.Adapter.PostJobsAdapter;
import com.example.seajobnowcandidate.Model.PostJobs;
import com.example.seajobnowcandidate.R;
import com.example.seajobnowcandidate.databinding.ActivityFindJobBinding;

import java.util.ArrayList;

public class FindJobActivity extends AppCompatActivity {

    ActivityFindJobBinding binding;
    // Array list for recycler view data source
    ArrayList<PostJobs> source;

    // adapter class object
    PostJobsAdapter adapter;

    // Linear Layout Manager
    LinearLayoutManager linearLayoutManager;

    // Layout Manager
    RecyclerView.LayoutManager RecyclerViewLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityFindJobBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnCancelPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.btnSearchJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddItemsToRecyclerViewArrayList();
                RecyclerViewLayoutManager = new LinearLayoutManager(FindJobActivity.this);

                // Set LayoutManager on Recycler View
                binding.searchedJobsRv.setLayoutManager(RecyclerViewLayoutManager);

                adapter = new PostJobsAdapter(FindJobActivity.this,source,R.layout.postjobs_items);
                binding.searchedJobsRv.setAdapter(adapter);
            }
        });


    }

    public void AddItemsToRecyclerViewArrayList()
    {
        // Adding items to ArrayList
        source = new ArrayList<>();
        source.add(new PostJobs("UI/UX Designer","Enginer","Master","₹10000 - ₹20000 per hour","Oil Tanker","Indian Ocean","10/10/2021","12/10/2021"));
        source.add(new PostJobs("Kitchen Jr. Chef","Hotel","Chef","₹10000 - ₹20000 per hour","Oil Tanker","Indian Ocean","10/10/2021","12/10/2021"));
        source.add(new PostJobs("Engine","Enginer","Master","₹10000 - ₹20000 per hour","Oil Tanker","Indian Ocean","10/10/2021","12/10/2021"));
        source.add(new PostJobs("UI/UX Designer","Enginer","Master","₹10000 - ₹20000 per hour","Oil Tanker","Indian Ocean","10/10/2021","12/10/2021"));
    }

}