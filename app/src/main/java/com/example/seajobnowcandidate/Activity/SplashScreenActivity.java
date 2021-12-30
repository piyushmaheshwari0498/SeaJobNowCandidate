package com.example.seajobnowcandidate.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.seajobnowcandidate.R;
import com.example.seajobnowcandidate.Utils.Custom_Toast;
import com.example.seajobnowcandidate.Utils.InternetConnection;
import com.example.seajobnowcandidate.databinding.ActivitySplashScreenBinding;

public class SplashScreenActivity extends AppCompatActivity {
    ActivitySplashScreenBinding activitySplashScreenBinding;
    InternetConnection internetConnection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySplashScreenBinding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(activitySplashScreenBinding.getRoot());
        internetConnection=new InternetConnection();
        refreshItems();
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
        if (!internetConnection.checkConnection(getApplicationContext())) {
            showInternetDialoag();
        }
        else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //This method will be executed once the timer is over
                    // Start your app main activity
                    Intent mintent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    startActivity(mintent);
                    // close this activity
                    finish();
                }
            }, 2000);

        }

    }
}