package com.testapp.socialuserinfo.presentation.activity.users_list;


import com.testapp.socialuserinfo.domain.usecase.UserInteractor;
import com.testapp.socialuserinfo.presentation.base.BasePresenter;
import com.testapp.socialuserinfo.utils.DLog;

import javax.inject.Inject;

public class UsersListPresenter extends BasePresenter<UsersListContract.View> implements UsersListContract.Presenter {

    private UserInteractor mUserInteractor;

    @Inject
    public UsersListPresenter(UserInteractor userInteractor) {
        mUserInteractor = userInteractor;
    }


    @Override
    public void loadUsers() {
        mUserInteractor.getUsers().subscribe(users -> {
            if (!users.isEmpty()) getView().displayUsers(users);
            else {
                //display empty state
            }
        }, throwable -> DLog.e(throwable.getMessage()));
    }
}
