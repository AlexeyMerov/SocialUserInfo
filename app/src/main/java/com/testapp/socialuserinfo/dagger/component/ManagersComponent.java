package com.testapp.socialuserinfo.dagger.component;


import com.testapp.socialuserinfo.dagger.module.ManagersModule;
import com.testapp.socialuserinfo.dagger.scope.ManagersScope;
import com.testapp.socialuserinfo.data.repository.managers.UserManager;

import dagger.Component;

@ManagersScope
@Component(modules = {ManagersModule.class}, dependencies = {ApiComponent.class, DaoComponent.class})
public interface ManagersComponent {

    UserManager getUserManager();

}
