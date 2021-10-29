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
import com.example.seajobnowcandidate.Entity.response.ConfirmPwdResponse;
import com.example.seajobnowcandidate.Interface.ApiInterface;
import com.example.seajobnowcandidate.R;
import com.example.seajobnowcandidate.Utils.Custom_Toast;
import com.example.seajobnowcandidate.Utils.InternetConnection;
import com.example.seajobnowcandidate.Utils.RetrofitBuilder;
import com.example.seajobnowcandidate.databinding.ActivityConfirmPasswordBinding;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.HashMap;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmPasswordActivity extends AppCompatActivity {
    ActivityConfirmPasswordBinding activityConfirmPasswordBinding;
    String email,newpwd,confirmpwd;
    InternetConnection internetConnection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityConfirmPasswordBinding=ActivityConfirmPasswordBinding.inflate(getLayoutInflater());
        setContentView(activityConfirmPasswordBinding.getRoot());
        setTitle(Html.fromHtml("<small>Confirm Password</small>"));
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_24dp);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        internetConnection=new InternetConnection();
        activityConfirmPasswordBinding.btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(!internetConnection.checkConnection(ConfirmPasswordActivity.this))
                    {
                        Custom_Toast.warning(ConfirmPasswordActivity.this, getString(R.string.no_internet));
                    }
                    else if (validate()){
                        getConfirmpwd();
                    }

                } catch (Exception e) {
                    Log.e("loginError", e.getLocalizedMessage());
                }
            }
        });
    }
    private void getConfirmpwd() {
        ApiInterface apiInterface = RetrofitBuilder.getRetrofitInstance().create(ApiInterface.class);
        Call<ConfirmPwdResponse> call = apiInterface.getConfirmpassword(getHashMap());
        call.enqueue(new Callback<ConfirmPwdResponse>() {
            @Override
            public void onResponse(Call<ConfirmPwdResponse> call, Response<ConfirmPwdResponse> response) {
                if (response.code() == 200 && response.message().equals("OK")) {
                    if (response.body().getStatusCode() == 1 && response.body().getStatusMessage().equals("Success")) {
                        String msg=response.body().getMessage();
                        System.out.println("msg"+msg);
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                        Intent i=new Intent(ConfirmPasswordActivity.this, LoginActivity.class);
                        startActivity(i);
                    }
                }
                else {
                    if (response.code() == 404 && response.message().equals("Not Found")) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            String message = jsonObject.getString("message");
                          //  Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(ConfirmPasswordActivity.this, "Some Thing Went Wrong !!", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ConfirmPwdResponse> call, Throwable t) {

            }
        });
    }

    private boolean validate()
    {
        email=activityConfirmPasswordBinding.editTextEmail.getText().toString();
        newpwd=activityConfirmPasswordBinding.etNewPassword.getText().toString();
        confirmpwd=activityConfirmPasswordBinding.etConfirmPassword.getText().toString();
        if(email.isEmpty())
        {
            Custom_Toast.error(getApplicationContext(),"Invalid  details.Please Re-Enter the Email Id");
            return false;
        }
        else if(newpwd.isEmpty())
        {
            Custom_Toast.error(getApplicationContext(),"Invalid details.Please Re-Enter the New password");
            return false;
        }
        else if(confirmpwd.isEmpty())
        {
            Custom_Toast.error(getApplicationContext(),"Invalid details.Please Re-Enter the Confirm password");
            return false;
        }
        return true;
    }
    private HashMap<String, Object> getHashMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("API_ACCESS_KEY","ZkC6BDUzxz");
        map.put("sc_pemail",email);
        System.out.println("email"+email);
        map.put("sc_new_password",newpwd);
        map.put("sc_confirm_password",confirmpwd);
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