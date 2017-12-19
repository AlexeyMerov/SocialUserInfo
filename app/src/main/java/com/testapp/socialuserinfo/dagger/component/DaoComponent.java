package com.testapp.socialuserinfo.dagger.component;


import com.testapp.socialuserinfo.dagger.module.DaoModule;
import com.testapp.socialuserinfo.data.repository.database.UserDAO;

import dagger.Component;

@Component(modules = {DaoModule.class})
public interface DaoComponent {

    UserDAO getUserDAO();

}
