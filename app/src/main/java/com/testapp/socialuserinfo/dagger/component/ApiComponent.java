package com.testapp.socialuserinfo.dagger.component;


import com.testapp.socialuserinfo.dagger.module.ApiModule;
import com.testapp.socialuserinfo.dagger.scope.ApiScope;
import com.testapp.socialuserinfo.data.repository.network.ServerCommunicator;

import dagger.Component;


@ApiScope
@Component(modules = {ApiModule.class})
public interface ApiComponent {

    ServerCommunicator getServerCommunicator();

}
