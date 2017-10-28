package com.glsebastiany.bakingapp.util

import android.content.Context
import com.glsebastiany.bakingapp.MyApplication
import com.glsebastiany.bakingapp.injection.component.ApplicationComponent

fun Context.getApplicationComponent() : ApplicationComponent =
        (this.applicationContext as MyApplication).applicationComponent
