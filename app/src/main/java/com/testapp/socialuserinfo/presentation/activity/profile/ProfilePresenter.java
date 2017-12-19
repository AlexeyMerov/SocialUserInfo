package com.testapp.socialuserinfo.presentation.activity.profile;


import com.testapp.socialuserinfo.domain.usecase.UserInteractor;
import com.testapp.socialuserinfo.presentation.base.BasePresenter;
import com.testapp.socialuserinfo.utils.DLog;

import javax.inject.Inject;

public class ProfilePresenter extends BasePresenter<ProfileContract.View> implements ProfileContract.Presenter {

    private UserInteractor mUserInteractor;

    @Inject
    public ProfilePresenter(UserInteractor userInteractor) {
        mUserInteractor = userInteractor;
    }

    @Override
    public void loadUser(long id) {
        mUserInteractor.getUser(id).subscribe(user -> {
            getView().displayUser(user);
        }, throwable -> {
            //display empty view state
            DLog.e(throwable.getMessage());
        });
    }
}
