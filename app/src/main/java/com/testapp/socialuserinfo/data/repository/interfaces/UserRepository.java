package com.testapp.socialuserinfo.data.repository.interfaces;


import com.testapp.socialuserinfo.data.repository.model.User;

import io.reactivex.Single;
import io.realm.RealmResults;

public interface UserRepository {

    Single<RealmResults<User>> getUsers();

    void saveUser(User user);

}
