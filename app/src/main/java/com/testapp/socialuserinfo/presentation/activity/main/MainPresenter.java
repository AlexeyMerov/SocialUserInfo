package com.testapp.socialuserinfo.presentation.activity.main;


import com.testapp.socialuserinfo.domain.usecase.UserInteractor;
import com.testapp.socialuserinfo.presentation.base.BasePresenter;

import javax.inject.Inject;

public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {

    private UserInteractor mUserInteractor;

    @Inject
    public MainPresenter(UserInteractor userInteractor) {
        mUserInteractor = userInteractor;
    }


    @Override
    public void saveUser(String avatarUrl, String email, String firstName, String lastName, String phoneNumber, String birthdayDate, String gender) {
        mUserInteractor.saveUser(avatarUrl, email, firstName, lastName, phoneNumber, birthdayDate, gender);
    }

}
