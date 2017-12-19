package com.testapp.socialuserinfo.presentation;


import android.app.Application;

import com.testapp.socialuserinfo.dagger.component.ApiComponent;
import com.testapp.socialuserinfo.dagger.component.DaggerApiComponent;
import com.testapp.socialuserinfo.dagger.component.DaggerAppComponent;
import com.testapp.socialuserinfo.dagger.component.DaggerDaoComponent;
import com.testapp.socialuserinfo.dagger.component.DaggerInteractorsComponent;
import com.testapp.socialuserinfo.dagger.component.DaggerManagersComponent;
import com.testapp.socialuserinfo.dagger.component.DaoComponent;
import com.testapp.socialuserinfo.dagger.component.InteractorsComponent;
import com.testapp.socialuserinfo.dagger.component.ManagersComponent;
import com.testapp.socialuserinfo.dagger.module.ApiModule;
import com.testapp.socialuserinfo.dagger.module.AppModule;
import com.testapp.socialuserinfo.dagger.module.DaoModule;
import com.testapp.socialuserinfo.dagger.module.InteractorsModule;
import com.testapp.socialuserinfo.dagger.module.ManagersModule;
import com.testapp.socialuserinfo.utils.DLog;

import io.reactivex.plugins.RxJavaPlugins;
import io.realm.Realm;
import io.realm.RealmConfiguration;


public class App extends Application {

    private InteractorsComponent mInteractorsComponent;

    public InteractorsComponent getInteractorsComponent() {
        return mInteractorsComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        initializeDagger();

        Realm.init(getApplicationContext());
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("_metadata.realm")
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);

        RxJavaPlugins.setErrorHandler(e -> DLog.d("Application", "RxJavaPlugins error: ", e));

        Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            DLog.e("SocialUserInfo", "", e);
            defaultUncaughtExceptionHandler.uncaughtException(t, e);
        });
    }

    private void initializeDagger() {
        DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();

        ApiComponent apiComponent = DaggerApiComponent.builder()
                .apiModule(new ApiModule())
                .build();

        DaoComponent daoComponent = DaggerDaoComponent.builder()
                .daoModule(new DaoModule())
                .build();

        ManagersComponent managersComponent = DaggerManagersComponent.builder()
                .apiComponent(apiComponent)
                .daoComponent(daoComponent)
                .managersModule(new ManagersModule())
                .build();

        mInteractorsComponent = DaggerInteractorsComponent.builder()
                .managersComponent(managersComponent)
                .interactorsModule(new InteractorsModule())
                .build();
    }
}
