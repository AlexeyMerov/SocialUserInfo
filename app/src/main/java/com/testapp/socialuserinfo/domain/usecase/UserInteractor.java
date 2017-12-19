package com.testapp.socialuserinfo.domain.usecase;


import com.testapp.socialuserinfo.data.repository.managers.UserManager;
import com.testapp.socialuserinfo.data.repository.model.User;

import javax.inject.Inject;

import io.reactivex.Single;
import io.realm.RealmResults;

public class UserInteractor {

    private UserManager mUserManager;

    @Inject
    public UserInteractor(UserManager userManager) {
        mUserManager = userManager;
    }

    public Single<RealmResults<User>> getUsers() {
        return mUserManager.getUsers();
    }

    public void saveUser(String avatarUrl, String email, String firstName, String lastName, String phoneNumber, String birthdayDate, String gender) {
        mUserManager.saveUser(new User(avatarUrl, firstName, lastName, email, phoneNumber, gender, birthdayDate));
    }

    public Single<User> getUser(long id) {
        return mUserManager.getUser(id);
    }
}
