package com.glsebastiany.bakingapp.util;

import android.content.Context;
import android.graphics.Color;

import com.glsebastiany.bakingapp.MyApplication;
import com.glsebastiany.bakingapp.injection.component.ApplicationComponent;

public class Util {

    public static ApplicationComponent getApplicationComponent(Context context) {
        return ((MyApplication) context.getApplicationContext()).getApplicationComponent();
    }

    public static int adjustAlpha(int color, float factor) {
        int alpha = Math.round(Color.alpha(color) * factor);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(alpha, red, green, blue);
    }

}

