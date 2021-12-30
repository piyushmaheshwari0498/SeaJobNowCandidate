package com.example.seajobnowcandidate.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.seajobnowcandidate.Entity.response.OTPResponse;
import com.example.seajobnowcandidate.Interface.ApiInterface;
import com.example.seajobnowcandidate.R;
import com.example.seajobnowcandidate.Utils.Custom_Toast;
import com.example.seajobnowcandidate.Utils.InternetConnection;
import com.example.seajobnowcandidate.Entity.RetrofitBuilder;
import com.example.seajobnowcandidate.databinding.ActivityVerifyBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyActivity extends AppCompatActivity {
    ActivityVerifyBinding activityVerifyBinding;
    String otpnumber;
    InternetConnection internetConnection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityVerifyBinding=ActivityVerifyBinding.inflate(getLayoutInflater());
        setContentView(activityVerifyBinding.getRoot());
        /*setTitle(Html.fromHtml("<small>Verify OTP</small>"));
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_24dp);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/

        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        internetConnection=new InternetConnection();

//        activityVerifyBinding.subtext.setText(getString(R.string.otp_text) + "\n" + PatternClass.protectEmailAddress("maheshwaripiyush99@gmail.com")
//                + "\n" + PatternClass.maskEmailAddress("maheshwaripiyush99@gmail.com"));
        activityVerifyBinding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(!internetConnection.checkConnection(VerifyActivity.this))
                    {
                        Custom_Toast.warning(VerifyActivity.this, getString(R.string.no_internet));
                    }
                    else if (validate()){
                        getOTPNumber();
                    }

                } catch (Exception e) {
                    Log.e("loginError", e.getLocalizedMessage());
                }
            }
        });

    }
    private void getOTPNumber() {
        ApiInterface apiInterface = RetrofitBuilder.getRetrofitInstance().create(ApiInterface.class);
        Call<OTPResponse> call = apiInterface.getOTPNumber(getHashMap());
        call.enqueue(new Callback<OTPResponse>() {
            @Override
            public void onResponse(Call<OTPResponse> call, Response<OTPResponse> response) {
                if (response.code() == 200 && response.message().equals("OK")) {
                    if (response.body().getStatusCode() == 1 && response.body().getStatusMessage().equals("Success")) {
                        String msg=response.body().getMessage();
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                        Intent i=new Intent(VerifyActivity.this,ConfirmPasswordActivity.class);
                        startActivity(i);
                        finish();
                    }
                }
                else {
                    if (response.code() == 404 && response.message().equals("Not Found")) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            String message = jsonObject.getString("message");
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(VerifyActivity.this, "Some Thing Went Wrong !!", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<OTPResponse> call, Throwable t) {

            }
        });
    }
    private boolean validate()
    {
        otpnumber=activityVerifyBinding.etOtpNumber.getOTP();
        if(otpnumber.isEmpty())
        {
            Custom_Toast.error(getApplicationContext(),"Invalid OTP Number.Please Re-Enter the OTP");
            return false;
        }
        return true;
    }
    private HashMap<String, Object> getHashMap() {
        otpnumber=activityVerifyBinding.etOtpNumber.getOTP();
        HashMap<String, Object> map = new HashMap<>();
        map.put("API_ACCESS_KEY","ZkC6BDUzxz");
        map.put("otp",otpnumber);
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

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // System.out.println("Landscape");
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            // System.out.println("Potrait");
        }
    }
}