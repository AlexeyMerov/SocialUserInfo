package com.testapp.socialuserinfo.dagger.module;

import android.content.Context;

import com.testapp.socialuserinfo.presentation.App;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private App mApp;

    public AppModule(App app) {
        mApp = app;
    }

    @Provides
    @Singleton
    public App provideApp() {
        return mApp;
    }

    @Provides
    @Singleton
    public Context provideApplicationContext() {
        return mApp.getApplicationContext();
    }

}
