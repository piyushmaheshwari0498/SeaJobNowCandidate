package com.example.seajobnowcandidate.Activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.seajobnowcandidate.Adapter.CityAdapter;
import com.example.seajobnowcandidate.Adapter.CountryAdapter;
import com.example.seajobnowcandidate.Adapter.StateAdapter;
import com.example.seajobnowcandidate.Entity.request.CityRequest;
import com.example.seajobnowcandidate.Entity.request.CountryRequest;
import com.example.seajobnowcandidate.Entity.request.SpinnerData;
import com.example.seajobnowcandidate.Entity.request.StateRequest;
import com.example.seajobnowcandidate.Entity.response.CitySpinnerResponse;
import com.example.seajobnowcandidate.Entity.response.RegisterResponse;
import com.example.seajobnowcandidate.Interface.ApiInterface;
import com.example.seajobnowcandidate.R;
import com.example.seajobnowcandidate.Utils.Constants;
import com.example.seajobnowcandidate.Utils.Custom_Toast;
import com.example.seajobnowcandidate.Utils.InternetConnection;
import com.example.seajobnowcandidate.Entity.RetrofitBuilder;
import com.example.seajobnowcandidate.databinding.ActivityRegisterBinding;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    ActivityRegisterBinding activityRegisterBinding;
    ProgressDialog ringProgressDialog;
    String indosno, f_name, m_name, l_name, email, mobile, password, confirm_password, pincode, address;
    DatePickerDialog datePickerDialog;
    Calendar calendar;
    int mDay, mMonth, mYear;
    String dobString = "";

    String selectedCityId = "", selectedStateId = "", selectedCountryId = "";
    String cityname, statename, countryname;
    String selectedCityName, selectedStateName, selectedCountryName;

    CityAdapter customCityAdapter2;
    StateAdapter stateAdapter2;
    CountryAdapter countryAdapter2;
    String selectedRadioButtonFlag = Constants.indosFlag;
    private List<CityRequest> cityRequestList;
    private List<StateRequest> stateRequestList;
    private List<CountryRequest> countryRequestList;
    InternetConnection internetConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityRegisterBinding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(activityRegisterBinding.getRoot());
        ringProgressDialog = new ProgressDialog(RegisterActivity.this);
        internetConnection=new InternetConnection();

        //For Capital Letters
        InputFilter toUpperCaseFilter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {

                StringBuilder stringBuilder = new StringBuilder();

                for (int i = start; i < end; i++) {
                    Character character = source.charAt(i);
                    character = Character.toUpperCase(character); // THIS IS UPPER CASING
                    stringBuilder.append(character);

                }
                return stringBuilder.toString();
            }

        };
        activityRegisterBinding.inputIndosno.setFilters(new InputFilter[] { toUpperCaseFilter });

        activityRegisterBinding.inputLayoutIndosno.setHint(R.string.hint_indos_no);

        activityRegisterBinding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                checkedId = radioGroup.getCheckedRadioButtonId();
                switch (checkedId) {
                    case R.id.indosNoRadioButton:
                        if (activityRegisterBinding.indosNoRadioButton.isChecked()) {
                           // Toast.makeText(getApplicationContext(), activityRegisterBinding.indosNoRadioButton.getText().toString()+"indosNoRadioButton is selected", Toast.LENGTH_SHORT).show();
                            activityRegisterBinding.inputLayoutIndosno.setHint(R.string.hint_indos_no);
                            selectedRadioButtonFlag = Constants.indosFlag;
                        }
                        break;
                    case R.id.cdcRadioButton:
                        if (activityRegisterBinding.cdcRadioButton.isChecked()) {
                           // Toast.makeText(getApplicationContext(), activityRegisterBinding.cdcRadioButton.getText().toString()+"cdcRadioButton is selected", Toast.LENGTH_SHORT).show();
                            activityRegisterBinding.inputLayoutIndosno.setHint(R.string.cdc_number);
                            selectedRadioButtonFlag = Constants.cdcFlag;
                        }
                        break;
                }
            }
        });

        calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
        activityRegisterBinding.inputDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog = new DatePickerDialog(RegisterActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                int newMonth = monthOfYear + 1;
                                String monthObtained = newMonth < 10 ? "0" + newMonth : String.valueOf(newMonth);
                                String dayObtained = dayOfMonth < 10 ? "0" + dayOfMonth : String.valueOf(dayOfMonth);
                                String displayDate = dayObtained + "-" + monthObtained + "-" + year;
                                activityRegisterBinding.inputDob.setText(displayDate);
                                if (dobString != null) {
                                    dobString = displayDate;
                                }
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        getSpinnerData();

        activityRegisterBinding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!internetConnection.checkConnection(getApplicationContext())) {
                    Custom_Toast.warning(RegisterActivity.this, getString(R.string.no_internet));
                } else {
                    if (!validateIndosNo() | !validateFName() | !validateMName() | !validateLName() | !validateEmail() |
                            !validateMobile() | !validatePassword() | !validateConfirmPassword()) {
                        return;
                    }
                    addRegisterDetails();
                }
            }
        });
        activityRegisterBinding.textLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* if (!internetConnection.checkConnection(getApplicationContext())) {
                    Custom_Toast.warning(RegisterActivity.this, getString(R.string.no_internet));
                } else {*/
                    Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(i);
//                }
            }
        });

    }

    public void getSpinnerData() {
        ApiInterface apiInterface = RetrofitBuilder.getRetrofitInstance().create(ApiInterface.class);
        Call<CitySpinnerResponse> call = apiInterface.getSpinner();
        call.enqueue(new Callback<CitySpinnerResponse>() {
            @Override
            public void onResponse(Call<CitySpinnerResponse> call, Response<CitySpinnerResponse> response) {
                if (response.code() == 200 && response.message().equals("OK")) {
                    if (response.body().getStatusCode() == 1 && response.body().getStatusMessage().equals("Success")) {
                        SpinnerData registerData = response.body().getData();
                        //City Data
                        cityRequestList = registerData.getCity();
                        customCityAdapter2 = new CityAdapter(RegisterActivity.this, R.layout.custom_spinner_item, cityRequestList);
                        activityRegisterBinding.spnCity.setThreshold(1);
                        activityRegisterBinding.spnCity.setAdapter(customCityAdapter2);

                        activityRegisterBinding.spnCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                                CityRequest cityRequest = (CityRequest) adapterView.getItemAtPosition(pos);
                                selectedCityId = cityRequest.getCityId();
                                selectedCityName = cityRequest.getCityName();
                                Log.e("cityId", selectedCityId);
                                Log.e("cityName", selectedCityName);
                            }
                        });

                        //State Data
                        stateRequestList = registerData.getState();
                        stateAdapter2 = new StateAdapter(RegisterActivity.this, R.layout.custom_spinner_item, stateRequestList);
                        activityRegisterBinding.spnState.setThreshold(1);
                        activityRegisterBinding.spnState.setAdapter(stateAdapter2);

                        activityRegisterBinding.spnState.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                                StateRequest stateRequest = (StateRequest) adapterView.getItemAtPosition(pos);
                                selectedStateId = stateRequest.getStateId();
                                selectedStateName = stateRequest.getStateName();
                                Log.e("stateId", selectedStateId);
                                Log.e("stateName", selectedStateName);
                            }
                        });


                        //Country Data
                        countryRequestList = registerData.getCountry();
                        countryAdapter2 = new CountryAdapter(RegisterActivity.this, R.layout.custom_spinner_item, countryRequestList);
                        activityRegisterBinding.spnCountry.setThreshold(1);
                        activityRegisterBinding.spnCountry.setAdapter(countryAdapter2);

                        activityRegisterBinding.spnCountry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                                CountryRequest countryRequest = (CountryRequest) adapterView.getItemAtPosition(pos);
                                selectedCountryId = countryRequest.getCountryId();
                                selectedCountryName = countryRequest.getCountryName();
                                Log.e("countryId", selectedCountryId);
                                Log.e("countryName", selectedCountryName);
                            }
                        });
                    }
                }
                else {
                    if (response.code() == 404 && response.message().equals("Not Found")) {
                        try {
                            ringProgressDialog.dismiss();
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            String errormessage = jsonObject.getString("errorMessage");
                            System.out.println("msg" + errormessage);
                            //Toast.makeText(LoginActivity.this,errormessage,Toast.LENGTH_LONG).show();
                            Log.d("error message", errormessage);

                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }
                    }


                    else {
                        ringProgressDialog.dismiss();
                        Custom_Toast.warning(RegisterActivity.this, getResources().getString(R.string.something_wrong));
                    }
                }
            }

            @Override
            public void onFailure(Call<CitySpinnerResponse> call, Throwable t) {

            }
        });
    }

    private void addRegisterDetails() {
        ApiInterface apiInterface = RetrofitBuilder.getRetrofitInstance().create(ApiInterface.class);
        Call<RegisterResponse> call = apiInterface.getRegisterDetails(getHashMap());
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {

                if (response.code() == 200 && response.message().equals("OK")) {
                    // System.out.println("success" + response.body().getStatusMessage());
                    if (response.body().getStatusCode() == 1 && response.body().getStatusMessage().equals("Success")) {
                        ringProgressDialog.dismiss();
                        String msg=response.body().getMessage();
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                        finish();
                    }
                } else {
                    if (response.code() == 404 && response.message().equals("Not Found")) {
                        try {
                            ringProgressDialog.dismiss();
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            String errormessage = jsonObject.getString("message");
                            System.out.println("error_message" + errormessage);
                            if (errormessage.toLowerCase().contains("indo")) {
                                activityRegisterBinding.inputLayoutIndosno.setError(errormessage);
                            } else if (errormessage.toLowerCase().contains("dob")) {
                                activityRegisterBinding.inputLayoutDob.setError(errormessage);
                            } else if (errormessage.toLowerCase().contains("email")) {
                                activityRegisterBinding.inputLayoutEmail.setError(errormessage);
                            } else if (errormessage.toLowerCase().contains("mobile")) {
                                activityRegisterBinding.inputLayoutMobile.setError(errormessage);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        ringProgressDialog.dismiss();
                        Custom_Toast.warning(RegisterActivity.this, getResources().getString(R.string.something_wrong));
                    }
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                ringProgressDialog.dismiss();
                Custom_Toast.warning(RegisterActivity.this, t.getMessage());
                Log.d("onFailure login", t.getMessage());
            }
        });
    }

    private HashMap<String, Object> getHashMap() {
        indosno = activityRegisterBinding.inputIndosno.getText().toString();
        f_name = activityRegisterBinding.inputFirstName.getText().toString();
        m_name = activityRegisterBinding.inputMiddleName.getText().toString();
        l_name = activityRegisterBinding.inputLastName.getText().toString();
        email = activityRegisterBinding.inputEmail.getText().toString();
        mobile = activityRegisterBinding.inputMobile.getText().toString();
        password = activityRegisterBinding.inputPassword.getText().toString();
        confirm_password = activityRegisterBinding.inputConfirmPassword.getText().toString();
        pincode = activityRegisterBinding.inputPincode.getText().toString();
        address = activityRegisterBinding.inputAddress.getText().toString();

        HashMap<String, Object> map = new HashMap<>();
        map.put("API_ACCESS_KEY", "ZkC6BDUzxz");
        map.put("indos_no", indosno);
        map.put("first_name", f_name);
        map.put("middle_name", m_name);
        map.put("last_name", l_name);
        map.put("dob", dobString);
        map.put("city", selectedCityId);
        map.put("state", selectedStateId);
        map.put("country", selectedCountryId);
        map.put("address", address);
        map.put("email", email);
        map.put("mobile", mobile);
        map.put("pincode", pincode);
        map.put("password", password);
        map.put("confirm_password", confirm_password);
        map.put("sc_cand_register_flag", selectedRadioButtonFlag);
        return map;
    }

    public boolean validateIndosNo() {
        String indosno = activityRegisterBinding.inputIndosno.getText().toString().trim();
        if (indosno.isEmpty()) {
            activityRegisterBinding.inputLayoutIndosno.setError("Please Enter Indos Number");
            return false;
        } else {
            activityRegisterBinding.inputLayoutIndosno.setError(null);
            return true;
        }
    }

    public boolean validateFName() {
        String name = activityRegisterBinding.inputFirstName.getText().toString().trim();
        if (name.isEmpty()) {
            activityRegisterBinding.inputLayoutFirstName.setError("Please Enter First Name");
            return false;
        } else {
            activityRegisterBinding.inputLayoutFirstName.setError(null);
            return true;
        }
    }

    public boolean validateMName() {
        String name = activityRegisterBinding.inputMiddleName.getText().toString().trim();
        if (name.isEmpty()) {
            activityRegisterBinding.inputLayoutMiddleName.setError("Please Enter Middle Name");
            return false;
        } else {
            activityRegisterBinding.inputLayoutMiddleName.setError(null);
            return true;
        }
    }

    public boolean validateLName() {
        String name = activityRegisterBinding.inputLastName.getText().toString().trim();
        if (name.isEmpty()) {
            activityRegisterBinding.inputLayoutLastName.setError("Please Enter Last Name");
            return false;
        } else {
            activityRegisterBinding.inputLayoutLastName.setError(null);
            return true;
        }
    }

    public boolean validateEmail() {
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String email = activityRegisterBinding.inputEmail.getText().toString().trim();
        if (email.isEmpty()) {
            activityRegisterBinding.inputLayoutEmail.setError("Please Enter Email Id");
            return false;
        } else if (!email.matches(checkEmail)) {
            activityRegisterBinding.inputLayoutEmail.setError("Enter Valid Email Id");
            return false;
        } else {
            activityRegisterBinding.inputLayoutEmail.setError(null);
            return true;
        }
    }

    public static boolean isValidPhone(String number) {
        // The given argument to compile() method
        // is regular expression. With the help of
        // regular expression we can validate mobile
        // number.
        // 1) Begins with 0 or 91
        // 2) Then contains 7 or 8 or 9.
        // 3) Then contains 9 digits
        String chechNumber = "(0|91)?[6-9][0-9]{9}";
        return number.matches(chechNumber);
    }

    public boolean validateMobile() {
        String mobileno = activityRegisterBinding.inputMobile.getText().toString().trim();

        if (activityRegisterBinding.inputMobile.getText().toString().isEmpty()) {
            activityRegisterBinding.inputLayoutMobile.setError("Please Enter Mobile Number");
            return false;
        } else if (!RegisterActivity.isValidPhone(mobileno)) {
            activityRegisterBinding.inputLayoutMobile.setError("Mobile Number is Invalid");
            return false;
        } else {
            activityRegisterBinding.inputLayoutMobile.setError(null);
            return true;
        }
    }

    public boolean validatePassword() {
        String password = activityRegisterBinding.inputPassword.getText().toString().trim();
        if (password.isEmpty()) {
            activityRegisterBinding.inputLayoutPassword.setError("Please Enter Password");
            return false;
        } else {
            activityRegisterBinding.inputLayoutPassword.setError(null);
            return true;
        }
    }

    public boolean validateConfirmPassword() {
        String confirmpassword = activityRegisterBinding.inputConfirmPassword.getText().toString().trim();
        if (confirmpassword.isEmpty()) {
            activityRegisterBinding.inputLayoutConfirmPassword.setError("Please Enter Confirm Password");
            return false;
        } else {
            activityRegisterBinding.inputLayoutConfirmPassword.setError(null);
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