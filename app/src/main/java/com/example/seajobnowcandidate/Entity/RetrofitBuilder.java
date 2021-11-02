package com.example.seajobnowcandidate.Entity;
import com.example.seajobnowcandidate.Utils.Defines;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBuilder {
    public static final String BASE_URL = "http://192.168.1.15/seaJobsNowAPI/";
    private static Retrofit retrofit;
    private static OkHttpClient okHttpClient;

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null && okHttpClient == null) {

            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);

            okHttpClient = new OkHttpClient.Builder()
                     .readTimeout(30, TimeUnit.SECONDS)
                     .writeTimeout(30, TimeUnit.SECONDS)
                    .connectTimeout(300, TimeUnit.MILLISECONDS)
//                    .addNetworkInterceptor(new StethoInterceptor())
                    .addInterceptor(httpLoggingInterceptor)
                    .build();

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
//                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }
}


