package com.glsebastiany.bakingapp.core;

import com.glsebastiany.bakingapp.MyApplication;
import com.glsebastiany.bakingapp.core.injection.MockedApplicationModule;
import com.glsebastiany.bakingapp.injection.component.ApplicationComponent;
import com.glsebastiany.bakingapp.injection.component.DaggerApplicationComponent;

public class MockApplication extends MyApplication {

    @Override
    public ApplicationComponent buildApplicationComponent() {
        return DaggerApplicationComponent
                .builder()
                .applicationModule(new MockedApplicationModule(this))
                .build();
    }

}
