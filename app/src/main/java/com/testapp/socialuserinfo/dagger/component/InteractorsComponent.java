package com.testapp.socialuserinfo.dagger.component;


import com.testapp.socialuserinfo.dagger.module.InteractorsModule;
import com.testapp.socialuserinfo.dagger.scope.InteractorScope;
import com.testapp.socialuserinfo.domain.usecase.UserInteractor;

import dagger.Component;

@InteractorScope
@Component(modules = {InteractorsModule.class}, dependencies = {ManagersComponent.class})
public interface InteractorsComponent {

    UserInteractor getUserInteractor();

}
