package com.testapp.socialuserinfo.presentation.activity.main;


import com.testapp.socialuserinfo.presentation.base.BaseContract;

public interface MainContract {

    interface View extends BaseContract.View {


    }

    interface Presenter extends BaseContract.Presenter<View> {

        void saveUser(String avatarUrl, String email, String firstName, String lastName, String phoneNumber, String birthdayDate, String gender);

    }

}
