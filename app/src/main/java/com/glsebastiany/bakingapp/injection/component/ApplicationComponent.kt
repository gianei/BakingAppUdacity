package com.glsebastiany.bakingapp.injection.component

import android.content.Context
import com.glsebastiany.bakingapp.MyApplication
import com.glsebastiany.bakingapp.injection.module.ApplicationModule
import com.glsebastiany.bakingapp.injection.scopes.ApplicationContext
import com.glsebastiany.bakingapp.view.main.MainActivityViewModel
import dagger.Component

@ApplicationContext
@Component(modules = arrayOf(
        ApplicationModule::class))
interface ApplicationComponent {

    @ApplicationContext
    fun context(): Context

    @ApplicationContext
    fun myApplication(): MyApplication

    fun inject(mainActivityViewModel: MainActivityViewModel)

}
