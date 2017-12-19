package com.testapp.socialuserinfo.presentation.activity.profile;


import com.testapp.socialuserinfo.data.repository.model.User;
import com.testapp.socialuserinfo.presentation.base.BaseContract;

public interface ProfileContract {

    interface View extends BaseContract.View {

        void displayUser(User user);

    }

    interface Presenter extends BaseContract.Presenter<View> {

        void loadUser(long id);

    }

}
