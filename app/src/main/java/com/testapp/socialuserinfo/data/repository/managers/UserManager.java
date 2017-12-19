package com.testapp.socialuserinfo.data.repository.managers;


import com.testapp.socialuserinfo.data.repository.database.UserDAO;
import com.testapp.socialuserinfo.data.repository.interfaces.UserRepository;
import com.testapp.socialuserinfo.data.repository.model.User;
import com.testapp.socialuserinfo.data.repository.network.ServerCommunicator;

import javax.inject.Inject;

import io.reactivex.Single;
import io.realm.RealmResults;

public class UserManager implements UserRepository {

    private ServerCommunicator mServerCommunicator;
    private UserDAO mUserDAO;
    private Single<User> mUser;

    @Inject
    public UserManager(ServerCommunicator serverCommunicator, UserDAO userDAO) {
        mServerCommunicator = serverCommunicator;
        mUserDAO = userDAO;
    }

    @Override
    public Single<RealmResults<User>> getUsers() {
        return Single.just(mUserDAO.getUsers());
    }

    @Override
    public void saveUser(User user) {
        mUserDAO.saveUser(user);
    }

    public Single<User> getUser(long id) {
        return Single.just(mUserDAO.getUser(id));
    }
}
