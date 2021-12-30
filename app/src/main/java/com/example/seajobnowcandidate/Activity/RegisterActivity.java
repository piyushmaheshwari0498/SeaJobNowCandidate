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
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.seajobnowcandidate.Adapter.CityAdapter;
import com.example.seajobnowcandidate.Adapter.CountryAdapter;
import com.example.seajobnowcandidate.Adapter.FullExpAdapter;
import com.example.seajobnowcandidate.Adapter.RankAdapter;
import com.example.seajobnowcandidate.Adapter.ShipTypeAdapter;
import com.example.seajobnowcandidate.Adapter.StateAdapter;
import com.example.seajobnowcandidate.Entity.RetrofitBuilder;
import com.example.seajobnowcandidate.Entity.request.CityRequest;
import com.example.seajobnowcandidate.Entity.request.CountryRequest;
import com.example.seajobnowcandidate.Entity.request.FullExpRequest;
import com.example.seajobnowcandidate.Entity.request.PostSpinnerDataRequest;
import com.example.seajobnowcandidate.Entity.request.RankRequest;
import com.example.seajobnowcandidate.Entity.request.RegisterRequest;
import com.example.seajobnowcandidate.Entity.request.ShipTypeRequest;
import com.example.seajobnowcandidate.Entity.request.SpinnerData;
import com.example.seajobnowcandidate.Entity.request.StateRequest;
import com.example.seajobnowcandidate.Entity.response.CitySpinnerResponse;
import com.example.seajobnowcandidate.Entity.response.PostSpinnerResponse;
import com.example.seajobnowcandidate.Entity.response.RegisterResponse;
import com.example.seajobnowcandidate.Interface.ApiInterface;
import com.example.seajobnowcandidate.R;
import com.example.seajobnowcandidate.Utils.Constants;
import com.example.seajobnowcandidate.Utils.Custom_Toast;
import com.example.seajobnowcandidate.Utils.InternetConnection;
import com.example.seajobnowcandidate.actions.ShowSnackbar;
import com.example.seajobnowcandidate.databinding.ActivityRegisterBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    public List<FullExpRequest> fullexpList;
    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    List<ShipTypeRequest> shipTypeRequestList;
    List<RankRequest> rankRequestList;
    ShipTypeAdapter shipTypeAdapter;
    RankAdapter rankAdapter;
    ActivityRegisterBinding activityRegisterBinding;
    ProgressDialog ringProgressDialog;
    String indosno, f_name, m_name, l_name, email, mobile, password, confirm_password, pincode, address;
    DatePickerDialog datePickerDialog;
    Calendar calendar;
    int mDay, mMonth, mYear;
    String dobString = "";
    String selectedCityId = "", selectedStateId = "", selectedCountryId = "", selectedStatusId = "0";
    String cityname, statename, countryname;
    String selectedCityName, selectedStateName, selectedCountryName, selectedStatusName;
    CityAdapter customCityAdapter2;
    StateAdapter stateAdapter2;
    CountryAdapter countryAdapter2;
    String selectedRadioButtonFlag = Constants.indosFlag;
    InternetConnection internetConnection;
    FullExpAdapter adapter;
    String sailingdate = "";
    String leave_dur = "";
    String cdiscontinuety = "";
    String creason = "";
    String selectedwordedshipid = "";
    String selectedwordedshipName = "";
    String selectedrankid = "";
    String selectedrankName = "";
    String selectedflexshipid = "";
    String selectedflexshipName = "";
    String current_salary = "";
    String expect_salary = "";
    String selectedcontractprefId = "";
    String selectedleaveprefId = "";
    String other_pref = "";
    String ship_size = "";
    String ship_age = "";
    String trading_area = "";
    String food = "";
    String fam_carrg = "";
    String salary = "";
    String promotion = "";
    String vacation = "";
    String regular_emp = "";
    String fromString = "";
    String todateString = "";
    String shipname;
    String vesseltype;
    String enginetype;
    String enginepower;
    String totaltime;
    String reason_stick;
    String chane_reason;
    String circumtance;
    String signed_off;
    String surgery;
    String achivement;
    private List<CityRequest> cityRequestList;
    private List<StateRequest> stateRequestList;
    private List<CountryRequest> countryRequestList;

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

    public static boolean isDateAfter(String startDate, String endDate) {
        try {
            String myFormatString = "dd-MM-yyyy"; // for example
            SimpleDateFormat df = new SimpleDateFormat(myFormatString);
            Date date1 = df.parse(endDate);
            Date startingDate = df.parse(startDate);

            return date1.after(startingDate);
        } catch (Exception e) {

            return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityRegisterBinding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(activityRegisterBinding.getRoot());
        ringProgressDialog = new ProgressDialog(RegisterActivity.this);
        internetConnection = new InternetConnection();

        fullexpList = new ArrayList<>();

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

        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        activityRegisterBinding.inputIndosno.setFilters(new InputFilter[]{toUpperCaseFilter});

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

        datePickerfunc();

        if (internetConnection.isConnected(this)) {
            getSpinnerData();
        }

        activityRegisterBinding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!InternetConnection.checkConnection(getApplicationContext())) {
                    Custom_Toast.warning(RegisterActivity.this, getString(R.string.no_internet));
                } else {
                    if (selectedStatusId.equals("0")) {
                        new ShowSnackbar().shortSnackbar(activityRegisterBinding.getRoot().getRootView(), "Please Select Status");
                    }
                    if (!validateRank() | !validateIndosNo() | !validateFName() | !validateMName() | !validateLName() | !validateEmail() |
                            !validateMobile() | !validatePassword() | !validateConfirmPassword() | !validateDOB() | !validateStatus()) {
                        return;
                    }

//                    if (!selectedrankid.isEmpty()) {
                    if (selectedrankid.equals("2")) {
                        if (fullexpList.size() != 0) {
                            addRegisterDetails();
                        } else {
                            new ShowSnackbar().shortSnackbar(activityRegisterBinding.getRoot().getRootView(), "Please Enter Experience Details");
                        }
                    } else {
                        addRegisterDetails();
                    }
//                    } else {
//                        new ShowSnackbar().shortSnackbar(activityRegisterBinding.getRoot().getRootView(), "Please Select Rank");
//                        activityRegisterBinding.inputLayoutRank.setError("Please Select Rank");
//                    }
                }
            }
        });
        activityRegisterBinding.textLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        activityRegisterBinding.btnAddExperience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shipname = activityRegisterBinding.inputEtShipname.getText().toString().trim();
                vesseltype = activityRegisterBinding.inputEtVesseltype.getText().toString().trim();
                enginetype = activityRegisterBinding.inputEtEnginetype.getText().toString().trim();
                enginepower = activityRegisterBinding.inputEtEnginePower.getText().toString().trim();
                totaltime = activityRegisterBinding.inputEtTotaltime.getText().toString().trim();
                reason_stick = activityRegisterBinding.inputEtReasonstick.getText().toString().trim();
                chane_reason = activityRegisterBinding.inputEtChange.getText().toString().trim();
                circumtance = activityRegisterBinding.inputEtCircumtance.getText().toString().trim();
                signed_off = activityRegisterBinding.inputEtSignedOff.getText().toString().trim();
                surgery = activityRegisterBinding.inputEtSurgery.getText().toString().trim();
                achivement = activityRegisterBinding.inputEtMajorAchivement.getText().toString().trim();
                fullexpList.add(new FullExpRequest(shipname, vesseltype, enginetype, enginepower, fromString, todateString, totaltime,
                        reason_stick, chane_reason, circumtance, signed_off, surgery, achivement));

                if(!validatefrom() | !validateto()){
                    return;
                }

                if (fullexpList.size() != 0) {

                    activityRegisterBinding.llExpDetails.setVisibility(View.VISIBLE);
                    Log.d("fullexpList", fullexpList.toString());
                    adapter = new FullExpAdapter(fullexpList, RegisterActivity.this);
                    activityRegisterBinding.rVFulexpDetails.setLayoutManager(new LinearLayoutManager(RegisterActivity.this,
                            LinearLayoutManager.HORIZONTAL, false));
//                    SnapHelper snapHelper = new PagerSnapHelper();
//                    snapHelper.attachToRecyclerView(activityRegisterBinding.rVFulexpDetails);
                    activityRegisterBinding.rVFulexpDetails.setAdapter(adapter);

                    for(int i=0;i<fullexpList.size();i++) {
                        try {
                            calculation(fullexpList.get(0).getFromdate(),fullexpList.get(i).getToDate());
                        } catch (ParseException e) {
                            e.printStackTrace();
                            Log.e("calculation error", e.getMessage());
                        }
                    }

                    activityRegisterBinding.etFrom.setText("");
                    activityRegisterBinding.etTodate.setText("");
                    fromString ="";
                    todateString ="";
                }
            }
        });

        activityRegisterBinding.llExpDetails.setVisibility(View.GONE);

    }

    private void calculation(String fromdateS, String todateS) throws ParseException {
        Date currentDate = new Date();
        Date fromdate = new Date();
        Date todate = new Date();

        long diffDays = (24 * 60 * 60 * 1000);

        fromdate = dateFormat.parse(fromdateS);
        todate = dateFormat.parse(todateS);

        long totalsail_days1 = Math.round(Math.abs(fromdate.getTime() - todate.getTime()) / diffDays);
        long total_leavedays1 = Math.round(Math.abs(fromdate.getTime() - currentDate.getTime()) / diffDays);
        long disc_days1 = Math.round(Math.abs(todate.getTime() - currentDate.getTime()) / diffDays);

        //Include end date in calculation (1 day is added)
        totalsail_days1 = totalsail_days1 + 1;
        total_leavedays1 = total_leavedays1 + 1;
        disc_days1 = disc_days1 + 1;

        long leave_days = total_leavedays1 - totalsail_days1;

        Log.d("total_days1", String.valueOf(totalsail_days1));
        Log.d("total_leavedays1", String.valueOf(leave_days));
        Log.d("disc_days1", String.valueOf(disc_days1));

        activityRegisterBinding.inputEtSailing.setText(""+totalsail_days1);
        activityRegisterBinding.inputEtLeave.setText(""+leave_days);
        activityRegisterBinding.inputEtContract.setText(""+disc_days1);
    }

    private void datePickerfunc() {
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

                                dobString = displayDate;

                                Date date = new Date();
                                Date date1 = null;
                                try {
                                    date1 = dateFormat.parse(displayDate);
                                    long diff = Math.abs(date.getTime() - date1.getTime());
                                    long diffDays = diff / (24 * 60 * 60 * 1000);
                                    System.out.println("Difference" + diffDays);
                                    if (diffDays > 18) {
                                        System.out.println("The employee above 18..");
                                        activityRegisterBinding.inputLayoutDob.setError("");
                                    } else {
                                        System.out.println("The employee below 18..");
                                        activityRegisterBinding.inputLayoutDob.setError("Should be 18+");
                                        dobString = "";
                                        activityRegisterBinding.inputDob.setText("");
                                    }
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                System.out.println(dateFormat.format(date1));


                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        activityRegisterBinding.etFrom.setOnClickListener(new View.OnClickListener() {
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
                                activityRegisterBinding.etFrom.setText(displayDate);

                                fromString = displayDate;

                                todateString = "";

                                activityRegisterBinding.inputLayoutTodate.setError("");
                                activityRegisterBinding.etTodate.setText("");
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


        activityRegisterBinding.etTodate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!fromString.isEmpty()) {
                    datePickerDialog = new DatePickerDialog(RegisterActivity.this,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                    int newMonth = monthOfYear + 1;
                                    String monthObtained = newMonth < 10 ? "0" + newMonth : String.valueOf(newMonth);
                                    String dayObtained = dayOfMonth < 10 ? "0" + dayOfMonth : String.valueOf(dayOfMonth);
                                    String displayDate = dayObtained + "-" + monthObtained + "-" + year;


                                    todateString = displayDate;

                                    if (isDateAfter(fromString, todateString)) {
                                        activityRegisterBinding.inputLayoutTodate.setError("");
                                        activityRegisterBinding.etTodate.setText(displayDate);
                                    } else {
                                        activityRegisterBinding.inputLayoutTodate.setError("End date should be greater");
                                    }
                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                } else
                    Toast.makeText(RegisterActivity.this, "Please From Date", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void getSpinnerData() {

        ArrayAdapter<String> adapterMessage = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.status));
        ArrayAdapter<String> adapterprefContarct = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.months));
        ArrayAdapter<String> adapterprefLeave = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.months));

        activityRegisterBinding.spnStatus.setAdapter(adapterMessage);
        activityRegisterBinding.spnpreferedcontract.setAdapter(adapterprefContarct);
        activityRegisterBinding.spnpreferedleave.setAdapter(adapterprefLeave);

        activityRegisterBinding.spnStatus.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                if (pos == 0) {
                    selectedStatusId = "0";
                    activityRegisterBinding.llExperience.setVisibility(View.GONE);
                } else if (pos == 1) {
                    selectedStatusId = "1";
                    activityRegisterBinding.llExperience.setVisibility(View.GONE);
                } else {
                    selectedStatusId = "2";
                    activityRegisterBinding.llExperience.setVisibility(View.VISIBLE);
                }
                Log.e("selectedStatusId", selectedStatusId);
            }
        });

        activityRegisterBinding.spnpreferedcontract.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                if (pos == 0) {
                    selectedcontractprefId = "0";
                } else if (pos == 1) {
                    selectedcontractprefId = "1";
                } else if (pos == 2) {
                    selectedcontractprefId = "2";
                } else {
                    selectedcontractprefId = "3";
                }
                Log.e("selectedcontractprefId", selectedcontractprefId);
            }
        });

        activityRegisterBinding.spnpreferedleave.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                if (pos == 0) {
                    selectedleaveprefId = "0";
                } else if (pos == 1) {
                    selectedleaveprefId = "1";
                } else if (pos == 2) {
                    selectedleaveprefId = "2";
                } else {
                    selectedleaveprefId = "3";
                }
                Log.e("selectedleaveprefId", selectedleaveprefId);
            }
        });

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
                } else {
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
                    } else {
                        ringProgressDialog.dismiss();
                        Custom_Toast.warning(RegisterActivity.this, getResources().getString(R.string.something_wrong));
                    }
                }
            }

            @Override
            public void onFailure(Call<CitySpinnerResponse> call, Throwable t) {

            }
        });

        ApiInterface apiInterface2 = RetrofitBuilder.getRetrofitInstance().create(ApiInterface.class);
        Call<PostSpinnerResponse> call2 = apiInterface2.getPostSpinner();
        call2.enqueue(new Callback<PostSpinnerResponse>() {
            @Override
            public void onResponse(Call<PostSpinnerResponse> call, Response<PostSpinnerResponse> response) {
                if (response.code() == 200 && response.message().equals("OK")) {
                    if (response.body().getStatusCode() == 1 && response.body().getStatusMessage().equals("Success")) {
                        PostSpinnerDataRequest registerData = response.body().getData();

                        //Applied for Rank
                        rankRequestList = registerData.getRank();

                        rankAdapter = new RankAdapter(RegisterActivity.this, R.layout.custom_spinner_item, rankRequestList);
                        activityRegisterBinding.spnrank.setThreshold(1);
                        activityRegisterBinding.spnrank.setAdapter(rankAdapter);

                        activityRegisterBinding.spnrank.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                                activityRegisterBinding.inputLayoutRank.setError("");
                                RankRequest rankRequest = (RankRequest) adapterView.getItemAtPosition(pos);
                                selectedrankid = rankRequest.getActualRankId();
                                selectedrankName = rankRequest.getActualRankName();
                                Log.e("selectedrankid", selectedrankid);
                                Log.e("selectedrankName", selectedrankName);
                            }
                        });

                        //Worked Ship Type
                        shipTypeRequestList = registerData.getShipType();

                        shipTypeAdapter = new ShipTypeAdapter(RegisterActivity.this, R.layout.custom_spinner_item, shipTypeRequestList);
                        activityRegisterBinding.spnshipType.setThreshold(1);
                        activityRegisterBinding.spnshipType.setAdapter(shipTypeAdapter);

                        activityRegisterBinding.spnshipType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                                ShipTypeRequest shipTypeRequest = (ShipTypeRequest) adapterView.getItemAtPosition(pos);
                                selectedwordedshipid = shipTypeRequest.getVtId();
                                selectedwordedshipName = shipTypeRequest.getVtName();
                                Log.e("selectedwordedshipid", selectedwordedshipid);
                                Log.e("selectedwordedshipName", selectedwordedshipName);
                            }
                        });

                        //Flexible Working Ship Type
                        shipTypeRequestList = registerData.getShipType();

                        ShipTypeRequest flexshipTypeRequest = new ShipTypeRequest();
                        flexshipTypeRequest.setVtId("0");
                        flexshipTypeRequest.setVtName("Please Select Vessel");
                        shipTypeRequestList.add(0, flexshipTypeRequest);

                        shipTypeAdapter = new ShipTypeAdapter(RegisterActivity.this, R.layout.custom_spinner_item, shipTypeRequestList);
                        activityRegisterBinding.spnshipflexible.setThreshold(1);
                        activityRegisterBinding.spnshipflexible.setAdapter(shipTypeAdapter);

                        activityRegisterBinding.spnshipflexible.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                                ShipTypeRequest shipTypeRequest = (ShipTypeRequest) adapterView.getItemAtPosition(pos);
                                selectedflexshipid = shipTypeRequest.getVtId();
                                selectedflexshipName = shipTypeRequest.getVtName();
                                Log.e("selectedflexshipid", selectedflexshipid);
                                Log.e("selectedflexshipName", selectedflexshipName);
                            }
                        });

                    } else {
                        if (response.body().getStatusCode() == 0) {
                            if (response.body().getStatusMessage().equals("Fail")) {
                                Toast.makeText(RegisterActivity.this, response.body().getStatusMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(RegisterActivity.this, getString(R.string.something_wrong), Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<PostSpinnerResponse> call, Throwable t) {

            }
        });
    }

    private void addRegisterDetails() {
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

        sailingdate = activityRegisterBinding.inputEtSailing.getText().toString();
        leave_dur = activityRegisterBinding.inputEtLeave.getText().toString();
        cdiscontinuety = activityRegisterBinding.inputEtContract.getText().toString();
        creason = activityRegisterBinding.etreasonwhy.getText().toString();
        current_salary = activityRegisterBinding.inputEtCurrentSal.getText().toString();
        expect_salary = activityRegisterBinding.inputEtexpectedSal.getText().toString();
        other_pref = activityRegisterBinding.inputEtOtherpreference.getText().toString();
        ship_size = activityRegisterBinding.inputEtShipesize.getText().toString();
        ship_age = activityRegisterBinding.inputEtShipage.getText().toString();
        trading_area = activityRegisterBinding.inputEtTradingarea.getText().toString();
        food = activityRegisterBinding.inputEtFood.getText().toString();
        fam_carrg = activityRegisterBinding.inputEtFamilyCarraige.getText().toString();
        salary = activityRegisterBinding.inputEtSalary.getText().toString();
        promotion = activityRegisterBinding.inputEtSalary.getText().toString();
        vacation = activityRegisterBinding.inputEtVacation.getText().toString();
        regular_emp = activityRegisterBinding.inputEtRegularEmployment.getText().toString();

        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setApiAccessKey("ZkC6BDUzxz");
        registerRequest.setIndosNo(indosno);
        registerRequest.setFirstName(f_name);
        registerRequest.setMiddleName(m_name);
        registerRequest.setLastName(l_name);
        registerRequest.setEmail(email);
        registerRequest.setMobile(mobile);
        registerRequest.setPassword(password);
        registerRequest.setConfirmPassword(confirm_password);
        registerRequest.setPincode(pincode);
        registerRequest.setAddress(address);
        registerRequest.setCity(selectedCityId);
        registerRequest.setState(selectedStateId);
        registerRequest.setCountry(selectedCountryId);
        registerRequest.setDob(dobString);
        registerRequest.setRankappliedfor(selectedrankid);

        registerRequest.setScCandRegisterFlag(selectedRadioButtonFlag);

        registerRequest.setSailingDate(sailingdate);
        registerRequest.setLeaveDur(leave_dur);
        registerRequest.setCdiscontinuety(cdiscontinuety);
        registerRequest.setCreason(creason);
        registerRequest.setCurrentSalary(current_salary);
        registerRequest.setExpectSalary(expect_salary);
        registerRequest.setPrefCduration(selectedcontractprefId);
        registerRequest.setPrefLeaveDur(selectedleaveprefId);
        registerRequest.setOtherPref(other_pref);
        registerRequest.setShipSize(ship_size);
        registerRequest.setShipAge(ship_age);
        registerRequest.setTradingArea(trading_area);
        registerRequest.setFood(food);
        registerRequest.setFamCarrg(fam_carrg);
        registerRequest.setSalary(salary);
        registerRequest.setPromotion(promotion);
        registerRequest.setVacation(vacation);
        registerRequest.setRegEmp(regular_emp);
        registerRequest.setShipTypeWorked(selectedwordedshipid);
        registerRequest.setFlexWorkOship(selectedflexshipid);

        registerRequest.setData(fullexpList);


        ApiInterface apiInterface = RetrofitBuilder.getRetrofitInstance().create(ApiInterface.class);
        Call<RegisterResponse> call = apiInterface.getRegisterDetails(registerRequest);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {

                if (response.code() == 200 && response.message().equals("OK")) {
                    // System.out.println("success" + response.body().getStatusMessage());
                    if (response.body().getStatusCode() == 1 && response.body().getStatusMessage().equals("Success")) {
                        ringProgressDialog.dismiss();
                        String msg = response.body().getMessage();
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

        sailingdate = activityRegisterBinding.inputEtSailing.getText().toString();
        leave_dur = activityRegisterBinding.inputEtLeave.getText().toString();
        cdiscontinuety = activityRegisterBinding.inputEtContract.getText().toString();
        creason = activityRegisterBinding.etreasonwhy.getText().toString();
        current_salary = activityRegisterBinding.inputEtCurrentSal.getText().toString();
        expect_salary = activityRegisterBinding.inputEtexpectedSal.getText().toString();
        other_pref = activityRegisterBinding.inputEtOtherpreference.getText().toString();
        ship_size = activityRegisterBinding.inputEtShipesize.getText().toString();
        ship_age = activityRegisterBinding.inputEtShipage.getText().toString();
        trading_area = activityRegisterBinding.inputEtTradingarea.getText().toString();
        food = activityRegisterBinding.inputEtFood.getText().toString();
        fam_carrg = activityRegisterBinding.inputEtFamilyCarraige.getText().toString();
        salary = activityRegisterBinding.inputEtSalary.getText().toString();
        promotion = activityRegisterBinding.inputEtSalary.getText().toString();
        vacation = activityRegisterBinding.inputEtVacation.getText().toString();
        regular_emp = activityRegisterBinding.inputEtRegularEmployment.getText().toString();
//        pref_cduration = selectedcontractprefId;
//        pref_leave_dur = selectedleaveprefId;
//        ship_type_worked = selectedwordedshipid;
//        flex_work_oship = selectedflexshipid;

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
        map.put("sc_rank", selectedrankid);

        map.put("sc_cand_register_flag", selectedRadioButtonFlag);
        map.put("sailing_date", sailingdate);
        map.put("leave_dur", sailingdate);
        map.put("creason", creason);
//        map.put("ship_type_worked", ship_type_worked);
//        map.put("flex_work_oship", flex_work_oship);
        map.put("current_salary", current_salary);
        map.put("expect_salary", expect_salary);
//        map.put("pref_cduration", pref_cduration);
//        map.put("pref_leave_dur", pref_leave_dur);
        map.put("other_pref", other_pref);
        map.put("ship_size", ship_size);
        map.put("ship_age", ship_age);
        map.put("tranding_area", trading_area);
        map.put("food", food);
        map.put("fam_carrg", fam_carrg);
        map.put("salary", salary);
        map.put("promotion", promotion);
        map.put("vacation", vacation);
        map.put("reg_emp", regular_emp);


        return map;
    }

    public boolean validateStatus() {
        if (selectedStatusId.equals("0")) {
            activityRegisterBinding.inputspnStatus.setError("Please Select Status");
            return false;
        } else {
            activityRegisterBinding.inputspnStatus.setError(null);
            return true;
        }
    }

    public boolean validateRank() {
        if (selectedrankid.isEmpty()) {
            activityRegisterBinding.inputLayoutRank.setError("Please Select Rank");
            return false;
        } else {
            activityRegisterBinding.inputLayoutRank.setError(null);
            return true;
        }
    }

    public boolean validatefrom() {
        if (fromString.isEmpty()) {
            activityRegisterBinding.inputLayoutFrom.setError("Please Enter From Date");
            return false;
        } else {
            activityRegisterBinding.inputLayoutFrom.setError(null);
            return true;
        }
    }

    public boolean validateto() {

        if (todateString.isEmpty()) {
            activityRegisterBinding.inputLayoutTodate.setError("Please Enter To Date");
            return false;
        } else {
            activityRegisterBinding.inputLayoutTodate.setError(null);
            return true;
        }
    }

    public boolean validateDOB() {
        String dob = activityRegisterBinding.inputDob.getText().toString().trim();
        if (dob.isEmpty()) {
            activityRegisterBinding.inputLayoutDob.setError("Please Enter Date of Birth");
            return false;
        } else {
            activityRegisterBinding.inputLayoutDob.setError(null);
            return true;
        }
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
        String password = activityRegisterBinding.inputPassword.getText().toString().trim();
        String confirmpassword = activityRegisterBinding.inputConfirmPassword.getText().toString().trim();
        if (confirmpassword.isEmpty()) {
            activityRegisterBinding.inputLayoutConfirmPassword.setError("Please Enter Confirm Password");
            return false;
        } else if (!confirmpassword.equals(password)) {
            activityRegisterBinding.inputLayoutConfirmPassword.setError("Confirm Password not matched");
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