package com.testapp.socialuserinfo.dagger.component;

import android.content.Context;

import com.testapp.socialuserinfo.dagger.module.AppModule;
import com.testapp.socialuserinfo.presentation.App;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    void inject(App app);

    App getApp();

    Context getContext();

}
