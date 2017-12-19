package com.testapp.socialuserinfo.dagger.module;


import com.testapp.socialuserinfo.dagger.scope.ApiScope;
import com.testapp.socialuserinfo.data.repository.network.ApiService;
import com.testapp.socialuserinfo.data.repository.network.ServerCommunicator;

import java.util.concurrent.TimeUnit;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;


@Module
public class ApiModule {

    private static final String API_URL = "http://jsonstub.com/";

    @Provides
    @ApiScope
    public ServerCommunicator provideServerCommunicator(@Named("apiservice") ApiService apiService) {
        return new ServerCommunicator(apiService);
    }

    @Provides
    @ApiScope
    @Named("apiservice")
    public ApiService provideApiService(@Named("api") Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }

    @Provides
    @ApiScope
    @Named("api")
    public Retrofit provideRetrofit(Retrofit.Builder builder) {
        return builder.baseUrl(API_URL).build();
    }

    @Provides
    @ApiScope
    public Retrofit.Builder provideRetrofitBuilder() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectionPool(new ConnectionPool(5, 30, TimeUnit.SECONDS))
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS);

//        if (BuildConfig.DEBUG) {
//            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
//            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//            builder.addInterceptor(httpLoggingInterceptor)
//                    .addNetworkInterceptor(new StethoInterceptor());
//        }

        return new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());

    }

}
