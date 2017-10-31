package com.glsebastiany.bakingapp.core

import com.glsebastiany.bakingapp.MyApplication
import com.glsebastiany.bakingapp.core.injection.MockedApplicationModule
import com.glsebastiany.bakingapp.injection.component.ApplicationComponent
import com.glsebastiany.bakingapp.injection.component.DaggerApplicationComponent

class MockApplication : MyApplication() {

    override fun buildApplicationComponent(): ApplicationComponent {
        return DaggerApplicationComponent
                .builder()
                .applicationModule(MockedApplicationModule(this))
                .build()
    }
}
