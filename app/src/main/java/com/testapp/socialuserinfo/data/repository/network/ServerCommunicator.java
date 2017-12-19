package com.testapp.socialuserinfo.data.repository.network;


import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.ObservableTransformer;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ServerCommunicator {

    private static final int DEFAULT_TIMEOUT = 10; // seconds
    private static final long DEFAULT_RETRY_ATTEMPTS = 4;
    private static final String TAG = ServerCommunicator.class.getSimpleName();
    private ApiService mApiService;

    @Inject
    public ServerCommunicator(ApiService service) {
        mApiService = service;
    }

    private static <T> ObservableTransformer<T, T> observableTransformer() {
        return observable -> observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .retry(DEFAULT_RETRY_ATTEMPTS);
    }

    private static <T> SingleTransformer<T, T> singleTransformer() {
        return upstream -> upstream
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .retry(DEFAULT_RETRY_ATTEMPTS);
    }

}
