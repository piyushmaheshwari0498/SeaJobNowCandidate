package com.example.seajobnowcandidate.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.seajobnowcandidate.Entity.request.LoginRequest;
import com.example.seajobnowcandidate.Entity.response.LoginResponse;
import com.example.seajobnowcandidate.Interface.ApiInterface;
import com.example.seajobnowcandidate.MainActivity;
import com.example.seajobnowcandidate.R;
import com.example.seajobnowcandidate.Session.AppSharedPreference;
import com.example.seajobnowcandidate.Utils.Constants;
import com.example.seajobnowcandidate.Utils.Custom_Toast;
import com.example.seajobnowcandidate.Utils.InternetConnection;
import com.example.seajobnowcandidate.Utils.RetrofitBuilder;
import com.example.seajobnowcandidate.databinding.ActivityLoginBinding;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityLoginBinding activityLoginBinding;
    ProgressDialog ringProgressDialog;
    String indosno,password;
    AppSharedPreference appSharedPreference;
    InternetConnection internetConnection;
    List<LoginRequest> loginRequestList;
    String indos_no,cdc_no;
    String cand_login_flag;
    Boolean indosradioButtonState,cdcradioButtonState;
    LoginRequest loginRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(activityLoginBinding.getRoot());
        ringProgressDialog = new ProgressDialog(LoginActivity.this);
        appSharedPreference = AppSharedPreference.getAppSharedPreference(this);
        internetConnection = new InternetConnection();
        loginRequest= new LoginRequest();

      /*  activityLoginBinding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                checkedId = radioGroup.getCheckedRadioButtonId();
                switch (checkedId) {
                    case R.id.indosNoRadioBtn:
                        if (activityLoginBinding.indosNoRadioBtn.isChecked()) {
                            activityLoginBinding.inputLayoutIndosno.setHint(R.string.hint_indos_no);

                        }
                        break;
                    case R.id.cdcRadioBtn:
                        if (activityLoginBinding.cdcRadioBtn.isChecked()) {
                            activityLoginBinding.inputLayoutIndosno.setHint(R.string.cdc_number);
                        }
                        break;
                }
            }
        });*/
        indosradioButtonState = activityLoginBinding.indosNoRadioBtn.isChecked();
        cdcradioButtonState = activityLoginBinding.cdcRadioBtn.isChecked();
        activityLoginBinding.indosNoRadioBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                indosradioButtonState=isChecked;
            }
        });

        activityLoginBinding.cdcRadioBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cdcradioButtonState=isChecked;

            }
        });


        activityLoginBinding.btnLogin.setOnClickListener(this);
        activityLoginBinding.btnRegister.setOnClickListener(this);
        activityLoginBinding.btnForgotPwd.setOnClickListener(this);

        checkIsloggedIn();
    }
    public void checkIsloggedIn() {
        if (appSharedPreference.getBooleanValue(Constants.IS_LOGGED_IN)) {
            Intent mIntent = new Intent(this, MainActivity.class);
            mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(mIntent);
            finish();
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_login:
            try {
                if (!internetConnection.checkConnection(LoginActivity.this)) {
                    Custom_Toast.warning(LoginActivity.this, getString(R.string.no_internet));
                } else {
                    ringProgressDialog.setMessage("Loading...");
                    ringProgressDialog.show();
                    if (!validateIndosNo() | !validatePassword()) {
                        ringProgressDialog.dismiss();
                        return;
                    }
                    addLoginDetails();
                }
            }
            catch (Exception e) {
                Log.d("loginError", e.getMessage());
            }
         break;
         case  R.id.btn_register:
               Intent i=new Intent(LoginActivity.this,RegisterActivity.class);
               startActivity(i);
          break;
         case  R.id.btn_forgot_pwd:
                Intent intent=new Intent(LoginActivity.this,ForgotPasswordActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void addLoginDetails() {
        if(indosradioButtonState)
        {
            indos_no= activityLoginBinding.inputIndosno.getText().toString();
            cand_login_flag="0";
        }
        else if(cdcradioButtonState){
            cdc_no= activityLoginBinding.inputIndosno.getText().toString();
            cand_login_flag="1";
        }

        ApiInterface apiInterface = RetrofitBuilder.getRetrofitInstance().create(ApiInterface.class);
        Call<LoginResponse> call = apiInterface.getLoginDetails(getHashMap());
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                if (response.code() == 200 && response.message().equals("OK")) {
                    // System.out.println("success" + response.body().getStatusMessage());
                    if (response.body().getStatusCode() == 1 && response.body().getStatusMessage().equals("Success")) {
                       ringProgressDialog.dismiss();
                        loginRequestList = response.body().getData();
                        if (!loginRequestList.isEmpty()) {
                            // TODO: 20-10-2021 Saving Data to SharedPreference
            appSharedPreference.putStringValue(Constants.INTENT_KEYS.KEY_CANDIDATE_NAME, loginRequestList.get(0).getFirstName()+

            loginRequestList.get(0).getMiddleName()+loginRequestList.get(0).getLastName());
            Log.d("name",loginRequestList.get(0).getFirstName()+loginRequestList.get(0).getMiddleName()+loginRequestList.get(0).getLastName());

                            appSharedPreference.putBooleanValue(Constants.IS_LOGGED_IN, true);

                            Intent mIntent = new Intent(LoginActivity.this, MainActivity.class);
                            mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(mIntent);
                        }
                    }
                } else {
                    if (response.code() == 404 && response.message().equals("Not Found")) {
                        try {
                            ringProgressDialog.dismiss();
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            String errormessage = jsonObject.getString("errorMessage");
                             //System.out.println("msg" + errormessage);
                            //Toast.makeText(LoginActivity.this,errormessage,Toast.LENGTH_LONG).show();
                            Log.d("error message", errormessage);
                            if (errormessage.toLowerCase().contains("username")) {
                                activityLoginBinding.inputLayoutIndosno.setError(errormessage);
                            } else if (errormessage.toLowerCase().contains("password")) {
                                activityLoginBinding.inputLayoutPassword.setError(errormessage);
                            }
                           }
                         catch (JSONException | IOException e) {
                            e.printStackTrace();
                          }
                      //    Toast.makeText(LoginActivity.this,errormessage,Toast.LENGTH_LONG).show();
                     //     Log.d("error message", errormessage);

                    }
                    else {
                        //Invoked in case of network error or Establishing error with server
                        //or Error creating Http Request or Error Processing Http Response
                        ringProgressDialog.dismiss();
                        Custom_Toast.warning(LoginActivity.this, getResources().getString(R.string.something_wrong));
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                ringProgressDialog.dismiss();
               // Custom_Toast.warning(LoginActivity.this, t.getMessage());
                Log.d("onFailure login",t.getMessage());
            }
        });
    }
    private HashMap<String, Object> getHashMap() {

        password= activityLoginBinding.inputPassword.getText().toString();
        HashMap<String, Object> map = new HashMap<>();
        map.put("API_ACCESS_KEY", "ZkC6BDUzxz");
        if(cand_login_flag.equals("0"))
        {
            map.put("sc_username", indos_no);
        }
        else {
            map.put("sc_username", cdc_no);
        }
        map.put("sc_password", password);
        map.put("sc_cand_login_flag",cand_login_flag);
       System.out.println("sc_cand_login_flag"+cand_login_flag);
        return map;
    }
    public boolean validateIndosNo() {
        String mob = activityLoginBinding.inputIndosno.getText().toString().trim();
        if (mob.isEmpty()) {
            activityLoginBinding.inputLayoutIndosno.setError("Please Enter Indos Number");
            return false;
        } else {
            activityLoginBinding.inputLayoutIndosno.setError(null);
            return true;
        }
    }
    public boolean validatePassword() {
        String pass = activityLoginBinding.inputPassword.getText().toString().trim();
        if (pass.isEmpty()) {
            activityLoginBinding.inputLayoutPassword.setError("Please Enter Password");
            return false;
        } else {
            activityLoginBinding.inputLayoutPassword.setError(null);
            return true;
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






