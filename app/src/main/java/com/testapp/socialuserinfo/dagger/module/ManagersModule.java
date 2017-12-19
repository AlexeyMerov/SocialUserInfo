package com.testapp.socialuserinfo.dagger.module;


import com.testapp.socialuserinfo.data.repository.database.UserDAO;
import com.testapp.socialuserinfo.data.repository.managers.UserManager;
import com.testapp.socialuserinfo.data.repository.network.ServerCommunicator;

import dagger.Module;
import dagger.Provides;

@Module
public class ManagersModule {

    @Provides
    public UserManager provideUserManager(ServerCommunicator serverCommunicator, UserDAO userDAO) {
        return new UserManager(serverCommunicator, userDAO);
    }

}
