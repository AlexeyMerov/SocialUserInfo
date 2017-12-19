package com.testapp.socialuserinfo.dagger.module;


import com.testapp.socialuserinfo.dagger.scope.InteractorScope;
import com.testapp.socialuserinfo.data.repository.managers.UserManager;
import com.testapp.socialuserinfo.domain.usecase.UserInteractor;

import dagger.Module;
import dagger.Provides;

@Module
public class InteractorsModule {

    @Provides
    @InteractorScope
    public UserInteractor provideUserInteractor(UserManager userManager) {
        return new UserInteractor(userManager);
    }

}
