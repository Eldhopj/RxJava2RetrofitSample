package com.example.eldho.rxjava2sample.Network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/";
    private static RetrofitClient mInstance; //Singleton instance
    private Retrofit retrofit; // retrofit object

    //Inside this constructor we initialize the retrofit object
    private RetrofitClient() {

        /**HttpLoggingInterceptor is used to log the data during the network call.*/
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient.build())
                .build();
    }

    // synchronized method to get the Singleton instance of RetrofitClient class
    // synchronized because we need to get single instance only
    public static synchronized RetrofitClient getInstance() {
        if (mInstance == null) {
            mInstance = new RetrofitClient();// This will return the instance of RetrofitClient class
        }
        return mInstance;
    }

    //Method to get the API
    public Api getApi() {
        return retrofit.create(Api.class);
    }
}
