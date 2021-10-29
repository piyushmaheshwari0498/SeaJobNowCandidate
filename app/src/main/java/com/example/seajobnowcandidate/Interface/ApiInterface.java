package com.example.seajobnowcandidate.Interface;

import com.example.seajobnowcandidate.Entity.response.CitySpinnerResponse;
import com.example.seajobnowcandidate.Entity.response.ConfirmPwdResponse;
import com.example.seajobnowcandidate.Entity.response.ForgotPasswordResponse;
import com.example.seajobnowcandidate.Entity.response.LoginResponse;
import com.example.seajobnowcandidate.Entity.response.OTPResponse;
import com.example.seajobnowcandidate.Entity.response.PostSpinnerResponse;
import com.example.seajobnowcandidate.Entity.response.RegisterResponse;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {
    //Login
    @POST("user/login")
    Call<LoginResponse> getLoginDetails(@Body HashMap<String, Object> loginResponse);

    //Register
    @POST("user/candidate_registration")
    Call<RegisterResponse> getRegisterDetails(@Body HashMap<String, Object> loginResponse);

    //Spinner City,state,country
    @GET("user/candidate_registration_assets")
    Call<CitySpinnerResponse> getSpinner();

    // Forgot Password
    @POST("user/forget_password")
    Call<ForgotPasswordResponse> getIndosNumber(@Body HashMap<String, Object> forgotResponse);

    //Verify OTP
    @POST("user/verify_otp")
    Call<OTPResponse> getOTPNumber(@Body HashMap<String, Object> veriftyotpResponse);

    //Confirm Password
    @POST("user/confirmpassword")
    Call<ConfirmPwdResponse> getConfirmpassword(@Body HashMap<String, Object> confirmpasswordResponse);

    //Spinner data which is fetching POst Job Department{} Rank{} etc.
    @GET("company/company_jobPost_assets")
    Call<PostSpinnerResponse> getPostSpinner();
}
