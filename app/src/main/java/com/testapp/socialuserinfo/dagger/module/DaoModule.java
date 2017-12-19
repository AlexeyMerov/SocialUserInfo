package com.testapp.socialuserinfo.dagger.module;


import com.testapp.socialuserinfo.data.repository.database.UserDAO;

import dagger.Module;
import dagger.Provides;

@Module
public class DaoModule {

    @Provides
    public UserDAO provideUserDAO() {
        return new UserDAO();
    }

}
