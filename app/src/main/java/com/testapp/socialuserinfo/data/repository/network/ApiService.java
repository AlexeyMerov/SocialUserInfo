package com.testapp.socialuserinfo.data.repository.network;


import com.testapp.socialuserinfo.data.repository.model.User;

import io.reactivex.Single;
import retrofit2.http.GET;


public interface ApiService {

    @GET("getUser")
    Single<User> getUser();

}
