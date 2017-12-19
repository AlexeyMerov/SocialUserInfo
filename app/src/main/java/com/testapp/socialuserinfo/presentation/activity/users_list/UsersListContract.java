package com.testapp.socialuserinfo.presentation.activity.users_list;


import com.testapp.socialuserinfo.data.repository.model.User;
import com.testapp.socialuserinfo.presentation.base.BaseContract;

import io.realm.RealmResults;

public interface UsersListContract {

    interface View extends BaseContract.View {

        void displayUsers(RealmResults<User> users);
    }

    interface Presenter extends BaseContract.Presenter<View> {

        void loadUsers();
    }

}
