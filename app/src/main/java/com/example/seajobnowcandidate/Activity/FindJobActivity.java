package com.example.seajobnowcandidate.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.seajobnowcandidate.databinding.ActivityFindJobBinding;
import com.example.seajobnowcandidate.ui.FindJobs.FindJobViewModel;
import com.example.seajobnowcandidate.ui.searchjob.PostJobFragment;

public class FindJobActivity extends AppCompatActivity {

    FindJobViewModel viewModel;
    ActivityFindJobBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(FindJobViewModel.class);

        binding = ActivityFindJobBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        /*binding.btnCancelPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });*/

        binding.btnSearchJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("title", binding.etJobTitle.getText().toString());
                PostJobFragment fragobj = new PostJobFragment();
                fragobj.setArguments(bundle);
                Log.d("ACTitemTitle", binding.etJobTitle.getText().toString());
                // viewModel.selectItem(binding.etJobTitle.getText().toString());
                finish();
            }
        });


    }

}