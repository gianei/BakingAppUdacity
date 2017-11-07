package com.glsebastiany.bakingapp.injection.component;

import android.content.Context;

import com.glsebastiany.bakingapp.MyApplication;
import com.glsebastiany.bakingapp.injection.module.ApplicationModule;
import com.glsebastiany.bakingapp.injection.scopes.ApplicationContext;
import com.glsebastiany.bakingapp.view.main.MainActivity;
import com.glsebastiany.bakingapp.view.main.MainActivityViewModel;
import com.glsebastiany.bakingapp.widget.BakingAppWidgetProvider;
import com.glsebastiany.bakingapp.widget.ListRemoteViewsFactory;

import dagger.Component;

@ApplicationContext
@Component(modules = {
        ApplicationModule.class})
public interface ApplicationComponent {

    @ApplicationContext
    Context context();

    @ApplicationContext
    MyApplication myApplication();

    void inject(MainActivityViewModel mainActivityViewModel);

    void inject(ListRemoteViewsFactory listRemoteViewsFactory);

    void inject(BakingAppWidgetProvider bakingAppWidgetProvider);

    void inject(MainActivity mainActivity);

}
