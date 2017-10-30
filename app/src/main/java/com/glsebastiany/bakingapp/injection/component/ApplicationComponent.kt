package com.glsebastiany.bakingapp.injection.component

import android.content.Context
import com.glsebastiany.bakingapp.MyApplication
import com.glsebastiany.bakingapp.injection.module.ApplicationModule
import com.glsebastiany.bakingapp.injection.scopes.ApplicationContext
import com.glsebastiany.bakingapp.view.main.MainActivity
import com.glsebastiany.bakingapp.view.main.MainActivityViewModel
import com.glsebastiany.bakingapp.widget.BakingAppWidgetProvider
import com.glsebastiany.bakingapp.widget.ListRemoteViewsFactory
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

    fun inject(listRemoteViewsFactory: ListRemoteViewsFactory)

    fun inject(bakingAppWidgetProvider: BakingAppWidgetProvider)

    fun inject(mainActivity: MainActivity)

}
