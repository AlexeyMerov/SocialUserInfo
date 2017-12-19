package com.testapp.socialuserinfo.dagger.component;


import com.testapp.socialuserinfo.dagger.module.PresentersModule;
import com.testapp.socialuserinfo.dagger.scope.PresenterScope;
import com.testapp.socialuserinfo.presentation.activity.main.MainActivity;
import com.testapp.socialuserinfo.presentation.activity.profile.ProfileActivity;
import com.testapp.socialuserinfo.presentation.activity.users_list.UsersListActivity;

import dagger.Component;

@PresenterScope
@Component(modules = {PresentersModule.class}, dependencies = {InteractorsComponent.class})
public interface PresentersComponent {

    //Activity

    void inject(MainActivity activity);

    void inject(UsersListActivity activity);

    void inject(ProfileActivity fragment);

    // Fragments

}
