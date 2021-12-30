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
import com.example.seajobnowcandidate.Entity.response.ForgotPasswordResponse;
import com.example.seajobnowcandidate.Interface.ApiInterface;
import com.example.seajobnowcandidate.R;
import com.example.seajobnowcandidate.Utils.Custom_Toast;
import com.example.seajobnowcandidate.Utils.InternetConnection;
import com.example.seajobnowcandidate.Entity.RetrofitBuilder;
import com.example.seajobnowcandidate.databinding.ActivityFogotPasswordBinding;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.HashMap;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {
    ActivityFogotPasswordBinding activityFogotPasswordBinding;
    String indosno;
    InternetConnection internetConnection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityFogotPasswordBinding = ActivityFogotPasswordBinding.inflate(getLayoutInflater());
        setContentView(activityFogotPasswordBinding.getRoot());
        internetConnection=new InternetConnection();
        /*setTitle(Html.fromHtml("<small>Forgot Password</small>"));
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_24dp);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activityFogotPasswordBinding.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("btnclick","");
                try {
                    if(!internetConnection.checkConnection(ForgotPasswordActivity.this))
                    {
                        Custom_Toast.warning(ForgotPasswordActivity.this, getString(R.string.no_internet));
                    }
                    else if (validate()){
                        forgetpassword();
                    }

                } catch (Exception e) {
                    Log.e("loginError", e.getLocalizedMessage());
                }
            }
        });


    }

    private void forgetpassword() {
        ApiInterface apiInterface = RetrofitBuilder.getRetrofitInstance().create(ApiInterface.class);
        Call<ForgotPasswordResponse> call = apiInterface.getIndosNumber(getHashMap());
        call.enqueue(new Callback<ForgotPasswordResponse>() {
            @Override
            public void onResponse(Call<ForgotPasswordResponse> call, Response<ForgotPasswordResponse> response) {
                if (response.code() == 200 && response.message().equals("OK")) {
                    if (response.body().getStatusCode() == 1 && response.body().getStatusMessage().equals("Success")) {
                        String msg=response.body().getMessage();
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                        Intent i=new Intent(ForgotPasswordActivity.this,VerifyActivity.class);
                        startActivity(i);
                        finish();
                        Log.e("otp", msg);

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
                        Toast.makeText(ForgotPasswordActivity.this, "Something Went Wrong !!", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ForgotPasswordResponse> call, Throwable t) {

            }
        });
    }
    private boolean validate()
    {
        indosno=activityFogotPasswordBinding.etIndosNumber.getText().toString();
        System.out.println("validate"+indosno);
        if(indosno.isEmpty())
        {
            Custom_Toast.info2(getApplicationContext(),"Invalid Indos Number.Please Re-Enter the Indos Number");
            return false;
        }
        return true;
    }

    private HashMap<String, Object> getHashMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("API_ACCESS_KEY","ZkC6BDUzxz");
        map.put("sc_indos",indosno);
        System.out.println("indos"+indosno);
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
            //   System.out.println("Potrait");
        }
    }
}