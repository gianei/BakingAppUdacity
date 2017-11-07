package com.glsebastiany.bakingapp;

import android.app.Application;

import com.glsebastiany.bakingapp.injection.component.ApplicationComponent;
import com.glsebastiany.bakingapp.injection.component.DaggerApplicationComponent;
import com.glsebastiany.bakingapp.injection.module.ApplicationModule;

import timber.log.Timber;
import timber.log.Timber.DebugTree;


public class MyApplication extends Application {

    public MyApplication(){
        super();
    }

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new DebugTree());
        }

        applicationComponent = buildApplicationComponent();

    }

    public ApplicationComponent buildApplicationComponent() {
        return DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}