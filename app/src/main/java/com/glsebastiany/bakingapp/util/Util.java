package com.glsebastiany.bakingapp.util;

import android.content.Context;

import com.glsebastiany.bakingapp.MyApplication;
import com.glsebastiany.bakingapp.injection.component.ApplicationComponent;

public class Util {

    public static ApplicationComponent getApplicationComponent(Context context) {
        return ((MyApplication) context.getApplicationContext()).getApplicationComponent();
    }

}

