package com.glsebastiany.bakingapp.core;


import android.app.Application;
import android.content.Context;
import android.support.test.runner.AndroidJUnitRunner;

public class MockedApplicationTestRunner extends AndroidJUnitRunner {
    @Override
    public Application newApplication(
            ClassLoader cl, String className, Context context)
            throws InstantiationException,
            IllegalAccessException,
            ClassNotFoundException {
        return super.newApplication(
                cl, MockApplication.class.getName(), context);
    }
}