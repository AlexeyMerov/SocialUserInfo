package com.testapp.socialuserinfo.dagger.module;


import com.testapp.socialuserinfo.dagger.scope.PresenterScope;
import com.testapp.socialuserinfo.domain.usecase.UserInteractor;
import com.testapp.socialuserinfo.presentation.activity.main.MainPresenter;
import com.testapp.socialuserinfo.presentation.activity.profile.ProfilePresenter;
import com.testapp.socialuserinfo.presentation.activity.users_list.UsersListPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class PresentersModule {

    @Provides
    @PresenterScope
    public MainPresenter provideMainPresenter(UserInteractor userInteractor) {
        return new MainPresenter(userInteractor);
    }

    @Provides
    @PresenterScope
    public UsersListPresenter provideUsersListPresenter(UserInteractor userInteractor) {
        return new UsersListPresenter(userInteractor);
    }

    @Provides
    @PresenterScope
    public ProfilePresenter provideProfilePresenter(UserInteractor userInteractor) {
        return new ProfilePresenter(userInteractor);
    }

}
