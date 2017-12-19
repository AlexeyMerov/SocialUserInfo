package com.testapp.socialuserinfo.data.repository.database;


import com.testapp.socialuserinfo.data.repository.model.User;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmResults;

public class UserDAO extends RealmHelper {

    @Inject
    public UserDAO() {
    }

    public RealmResults<User> getUsers() {
        Realm realm = Realm.getDefaultInstance();
        return realm.where(User.class).findAll();
    }

    public void saveUser(User user) {
        executeTransaction(realm -> realm.copyToRealmOrUpdate(user));
    }

    public User getUser(long id) {
        Realm realm = Realm.getDefaultInstance();
        return realm.where(User.class).equalTo(User.ID_KEY, id).findFirst();
    }
}
