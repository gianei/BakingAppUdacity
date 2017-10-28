package com.glsebastiany.bakingapp.view.recyclerview;

import android.view.View;

public interface RVEventHandler<T> {

    void onClick(View view, T obj);

}
