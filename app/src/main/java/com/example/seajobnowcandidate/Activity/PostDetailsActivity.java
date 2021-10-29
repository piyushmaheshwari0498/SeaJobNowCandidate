package com.example.seajobnowcandidate.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.example.seajobnowcandidate.R;
import com.example.seajobnowcandidate.Utils.InternetConnection;
import com.example.seajobnowcandidate.databinding.ActivityPostDetailsBinding;

public class PostDetailsActivity extends AppCompatActivity {
    InternetConnection internetConnection;
    ActivityPostDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        LayoutInflater inflater = getLayoutInflater();

        internetConnection = new InternetConnection();


        binding.toolbarLayout.setTitle(getIntent().getStringExtra("post_title"));

        TextView textView = findViewById(R.id.job_title);

    }
}