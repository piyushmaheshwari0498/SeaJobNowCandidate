package com.example.seajobnowcandidate.ui.profile;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.seajobnowcandidate.Activity.LoginActivity;
import com.example.seajobnowcandidate.Adapter.HomeCategoryAdapter;
import com.example.seajobnowcandidate.Entity.RetrofitBuilder;
import com.example.seajobnowcandidate.Entity.request.CandidateDetailsRequest;
import com.example.seajobnowcandidate.Entity.response.CandidateDetailsResponse;
import com.example.seajobnowcandidate.Entity.response.VesselResponse;
import com.example.seajobnowcandidate.Interface.ApiInterface;
import com.example.seajobnowcandidate.MainActivity;
import com.example.seajobnowcandidate.R;
import com.example.seajobnowcandidate.Session.AppSharedPreference;
import com.example.seajobnowcandidate.Utils.Constants;
import com.example.seajobnowcandidate.Utils.InternetConnection;
import com.example.seajobnowcandidate.Utils.PatternClass;
import com.example.seajobnowcandidate.actions.ShowSnackbar;
import com.example.seajobnowcandidate.databinding.ProfileFragmentBinding;
import com.example.seajobnowcandidate.ui.home.HomeViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    private ProfileViewModel mViewModel;
    ProfileFragmentBinding binding;
    boolean isInfo = false;
    boolean isPassport = false;
    boolean isTotalExp = false;
    boolean isFullExp = false;
    boolean isFamily = false;
    boolean isPreAdd = false;
    boolean isPernebtAdd = false;
    boolean isVac = false;
    boolean issea = false;
    boolean isgrowth = false;
    boolean iscourse = false;
    boolean isPermanentAdd = false;

    InternetConnection connection;
    AppSharedPreference appSharedPreference;
    List<CandidateDetailsRequest> candidateDetailsRequests;
    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = ProfileFragmentBinding.inflate(inflater, container, false);

        appSharedPreference = AppSharedPreference.getAppSharedPreference(getContext());
        binding.llInfoData.setVisibility(View.VISIBLE);
        isInfo = true;
        binding.llPassportData.setVisibility(View.GONE);

        connection = new InternetConnection();

        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheetDialog();
            }
        });

        binding.llFamily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.imgFamily.animate().rotationBy(180).setDuration(300).start();
                if(isFamily) {
                    isFamily = false;
                    binding.llFamilyData.setVisibility(View.GONE);
                } else {
                    // binding.imgInfo.animate().rotationBy(180).setDuration(500).start();
                    isFamily = true;
                    binding.llFamilyData.setVisibility(View.VISIBLE);
                }

            }
        });

        binding.llPresent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.imgPreAddress.animate().rotationBy(180).setDuration(300).start();
                if(isPreAdd) {
                    isPreAdd = false;
                    binding.llPresentData.setVisibility(View.GONE);
                } else {
                    // binding.imgInfo.animate().rotationBy(180).setDuration(500).start();
                    isPreAdd = true;
                    binding.llPresentData.setVisibility(View.VISIBLE);
                }

            }
        });

        binding.llPermanent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.imgPermanentAddress.animate().rotationBy(180).setDuration(300).start();
                if(isPernebtAdd) {
                    isPernebtAdd = false;
                    binding.llPermanentData.setVisibility(View.GONE);
                } else {
                    // binding.imgInfo.animate().rotationBy(180).setDuration(500).start();
                    isPernebtAdd = true;
                    binding.llPermanentData.setVisibility(View.VISIBLE);
                }

            }
        });

        binding.llPassport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.imgPassport.animate().rotationBy(180).setDuration(300).start();
                if(isPassport) {
                    isPassport = false;
                    binding.llPassportData.setVisibility(View.GONE);
                } else {
                    // binding.imgInfo.animate().rotationBy(180).setDuration(500).start();
                    isPassport = true;
                    binding.llPassportData.setVisibility(View.VISIBLE);
                }
            }
        });

        binding.llTotalExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.imgTotalExp.animate().rotationBy(180).setDuration(300).start();
                if(isTotalExp) {
                    isTotalExp = false;
                    binding.llTotalExpData.setVisibility(View.GONE);
                } else {
                    // binding.imgInfo.animate().rotationBy(180).setDuration(500).start();
                    isTotalExp = true;
                    binding.llTotalExpData.setVisibility(View.VISIBLE);
                }

            }
        });

        binding.llTotalExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.imgTotalExp.animate().rotationBy(180).setDuration(300).start();
                if(isTotalExp) {
                    isTotalExp = false;
                    binding.llTotalExpData.setVisibility(View.GONE);
                } else {
                    // binding.imgInfo.animate().rotationBy(180).setDuration(500).start();
                    isTotalExp = true;
                    binding.llTotalExpData.setVisibility(View.VISIBLE);
                }

            }
        });

        binding.llFullExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.imgFullexp.animate().rotationBy(180).setDuration(300).start();
                if(isFullExp) {
                    isFullExp = false;
                    binding.llFullExpData.setVisibility(View.GONE);
                } else {
                    // binding.imgInfo.animate().rotationBy(180).setDuration(500).start();
                    isFullExp = true;
                    binding.llFullExpData.setVisibility(View.VISIBLE);
                }

            }
        });

        binding.llVaccinaion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.imgVaccination.animate().rotationBy(180).setDuration(300).start();
                if(isVac) {
                    isVac = false;
                    binding.llVaccinaionData.setVisibility(View.GONE);
                } else {
                    // binding.imgInfo.animate().rotationBy(180).setDuration(500).start();
                    isVac = true;
                    binding.llVaccinaionData.setVisibility(View.VISIBLE);
                }

            }
        });

        binding.llVaccinaion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.imgVaccination.animate().rotationBy(180).setDuration(300).start();
                if(isVac) {
                    isVac = false;
                    binding.llVaccinaionData.setVisibility(View.GONE);
                } else {
                    // binding.imgInfo.animate().rotationBy(180).setDuration(500).start();
                    isVac = true;
                    binding.llVaccinaionData.setVisibility(View.VISIBLE);
                }

            }
        });


        binding.llSajourney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.imgSea.animate().rotationBy(180).setDuration(300).start();
                if(issea) {
                    issea = false;
                    binding.llSeajourneyData.setVisibility(View.GONE);
                } else {
                    // binding.imgInfo.animate().rotationBy(180).setDuration(500).start();
                    issea = true;
                    binding.llSeajourneyData.setVisibility(View.VISIBLE);
                }

            }
        });

        binding.llGrowht.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.imgGrowth.animate().rotationBy(180).setDuration(300).start();
                if(isgrowth) {
                    isgrowth = false;
                    binding.llGrowthData.setVisibility(View.GONE);
                } else {
                    // binding.imgInfo.animate().rotationBy(180).setDuration(500).start();
                    isgrowth = true;
                    binding.llGrowthData.setVisibility(View.VISIBLE);
                }

            }
        });

        binding.llCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.imgcourse.animate().rotationBy(180).setDuration(300).start();
                if(iscourse) {
                    iscourse = false;
                    binding.llCourseData.setVisibility(View.GONE);
                } else {
                    // binding.imgInfo.animate().rotationBy(180).setDuration(500).start();
                    iscourse = true;
                    binding.llCourseData.setVisibility(View.VISIBLE);
                }

            }
        });

        if(!connection.isConnected(getContext())){
            showInternetDialoag();
        }else{
            getProfilDetails();
        }

        return binding.getRoot();
    }

    public void closeLayout(LinearLayout linearLayout){

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        // TODO: Use the ViewModel
    }

    private void showBottomSheetDialog() {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
        bottomSheetDialog.setContentView(R.layout.logout_bottom_sheet);

        MaterialButton logout_yes = bottomSheetDialog.findViewById(R.id.btn_logout_yes);
        MaterialButton logout_no = bottomSheetDialog.findViewById(R.id.btn_logout_no);

        bottomSheetDialog.show();

        logout_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bottomSheetDialog.dismiss();
                appSharedPreference.logoutUser(getContext());
                Intent intent = new Intent(getContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        logout_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(getApplicationContext(), "Copy is Clicked ", Toast.LENGTH_LONG).show();
                bottomSheetDialog.dismiss();
            }
        });
    }

    private void getProfilDetails() {
        ApiInterface apiInterface = RetrofitBuilder.getRetrofitInstance().create(ApiInterface.class);
        Call<CandidateDetailsResponse> call = apiInterface.getProfileDetails(getfetchHashMap());
        call.enqueue(new Callback<CandidateDetailsResponse>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<CandidateDetailsResponse> call, Response<CandidateDetailsResponse> response) {
                if (response.code() == 200 && response.message().equals("OK")) {
                    candidateDetailsRequests = response.body().getData();
                    Log.d("categoryRequestList", candidateDetailsRequests.toString());

                    if (candidateDetailsRequests.size()!=0) {
                        for(CandidateDetailsRequest request : candidateDetailsRequests){
                            binding.textname.setText(request.getScFullName());
                            binding.tvRankApplied.setText(String.valueOf(request.getScRank()));
                            binding.tvMobile.setText(request.getScPmobile1());
                            binding.tvEmail.setText(request.getScPemail());
                            binding.tvDateOfBirth.setText(request.getScDob());

                            if(request.getScGender().equals("1")){
                                binding.tvGender.setText("Male");
                            }else{
                                binding.tvGender.setText("Female");
                            }

                            if(request.getScMstatus().equals("1"))
                                binding.tvMarital.setText("Single");
                            else if(request.getScMstatus().equals("2"))
                                binding.tvMarital.setText("Married");
                            else if(request.getScMstatus().equals("3"))
                                binding.tvMarital.setText("Seprated");
                            else if(request.getScMstatus().equals("4"))
                                binding.tvMarital.setText("Divorced");
                            else
                                binding.tvMarital.setText("Widowed");


                            binding.tvHeightWeight.setText(request.getScHeight()+" cm/ "+request.getScWeight()+" kg");
                            binding.tvBloodGroup.setText(String.valueOf(request.getScBloodgrp()));
                            binding.tvSpouseName.setText(request.getScYspouseName());
                            binding.tvSpouseNumber.setText(request.getScYspouseMobile1());
                            binding.tvSpouseEmail.setText(request.getScYspouseEmail());
                            binding.tvDependent.setText(request.getScYtotalChildren());
                            binding.tvNextKin.setText(request.getScAlternateNo());

                            binding.tvPreAddress.setText(request.getScAddress());
                            binding.tvCityState.setText(request.getScPcity()+", "+request.getScPstate());
                            binding.tvCountryPin.setText(request.getScPcountry()+", "+request.getScPpincode());

                            binding.tvPermanentAddress.setText(request.getScAddress());
                            binding.tvPermanentCityState.setText(request.getScPcity()+", "+request.getScPstate());
                            binding.tvPermanentCountryPin.setText(request.getScPcountry()+", "+request.getScPpincode());
                            binding.tvDescribeYouself.setText(request.getScSelfDesc());
                            binding.tvHobPass.setText(request.getScHobbies());
                            binding.tvDescribeFamily.setText(request.getScFamilyDetails());
                            binding.tvDescribeFamily.setText(request.getScFamilyDetails());

                            binding.tvPassportNumber.setText(String.valueOf(request.getScPassport()));
                            binding.tvValidityDate.setText(request.getScValidity());
                            binding.tvVisaPlace.setText(request.getScVisaPlace());
                            binding.tvIssuedDate.setText(request.getScYellowIssueDate()); // visa issue date

                            binding.tvCoc.setText(request.getScCoc());
                            binding.tvCocGrade.setText(request.getScCocGrade());
                            binding.tvCocIssued.setText(request.getScCocIssuedBy());
                            binding.tvCocIssueDate.setText(request.getScCocIssuedDt());
                            binding.tvCocExpiryDate.setText(request.getScCocExpDt());

                            binding.tvCdcBook.setText(request.getScCdcBookNo());
                            binding.tvCdcIssued.setText(request.getScCdcIssuedBy());
                            binding.tvCdcIssueDate.setText(request.getScCdcIssuedDt());
                            binding.tvCdcExpiryDate.setText(request.getScCdcIssuedDt());

                            binding.tvSailingExp.setText(request.getScTexpSailingDate());
                            binding.tvLeaveDuration.setText(request.getScTexpLeaveDur());
                            binding.tvDisContractType.setText(request.getScTexpCdiscont());
                            binding.tvDisContractReason.setText(request.getScTexpCreason());
                            binding.tvOldShipType.setText(request.getScTexpShipTypeWorked());
                            binding.tvFlexibleWork.setText(request.getScTexpFlexWorkOship());

                            binding.tvCurentSalary.setText(request.getScTexpCurrentSalary());
                            binding.tvExpectedSalary.setText(request.getScTexpExpectSalary());
                            binding.tvContractDuration.setText(request.getScTexpPrefCduration());
                            binding.tvPreferredLeaveDuration.setText(request.getScTexpPrefLeaveDur());
                            binding.tvOtherPreference.setText(request.getScTexpOtherPref());

                            binding.tvShipSize.setText(request.getScTexpShipSize());
                            binding.tvShipAge.setText(request.getScTexpShipAge());
                            binding.tvTradingArea.setText(request.getScTexpTradingArea());
                            binding.tvFoodPreference.setText(request.getScTexpFood());
                            binding.tvFamilyCarriage.setText(request.getScTexpFamCarrg());
                            binding.tvPromotion.setText(request.getScTexpPromotion());
                            binding.tvVacation.setText(request.getScTexpVacation());
                            binding.tvVacation.setText(request.getScTexpVacation());
                            binding.tvSal.setText(request.getScTexpSalary());
                            binding.tvRegular.setText(request.getScTexpRegEmp());

                            binding.tvVacinationName.setText(request.getScVaccinationName());
                            binding.tvVacinationFirst.setText(request.getScV1stDoseDt());
                            binding.tvVacinationSecond.setText(request.getSc2ndDoseDt());

                            binding.tvJourneyBegin.setText(request.getScSjBegin());
                            binding.tvOpinion.setText(request.getScSjEmpOpinion());
                            binding.tvDescribePoint.setText(request.getScSjGaveUp());
                            binding.tvDescribeProfession.setText(request.getScSjProfTerm());
                            binding.tvOnboardFriends.setText(request.getScSjOnboardFrnd());
                            binding.tvSkillAnswers.setText(request.getScSjKeyAreas());

                            binding.tvFutureGoals.setText(request.getScGcFgoal());
                            binding.tvPrefectEmployee.setText(request.getScGcPrefEmp());
                            binding.tvKnowAboutUs.setText(request.getScGcAboutUs());

                            binding.tvStcw.setText(request.getScCourseCtcw());
                            binding.tvCourseDetails1.setText(request.getScCourseVdetails());
                            binding.tvCoursedone.setText(request.getScCourseVcertificate());
                            binding.tvCourseDetails2.setText(request.getScCourseVdetails());

                        }

                    }
                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(Call<CandidateDetailsResponse> call, Throwable t) {
                Log.d("CandidateDetailsResponse failure : ", t.getMessage());
//                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private HashMap<String, Object> getfetchHashMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("API_ACCESS_KEY", "ZkC6BDUzxz");
        map.put("sc_id", appSharedPreference.getString(Constants.INTENT_KEYS.KEY_CANDIDATE_ID));
        return map;
    }

    private void showInternetDialoag() {
        final AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setTitle("Please connect to Internet")
                .setCancelable(false)
                .setMessage("No Internet Connection Available")
                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        refreshItems();
                        getProfilDetails();
                    }
                })
                .show();
    }

}