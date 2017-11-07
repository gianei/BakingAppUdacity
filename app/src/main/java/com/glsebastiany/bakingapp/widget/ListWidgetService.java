package com.glsebastiany.bakingapp.widget;


import android.content.Intent;
import android.widget.RemoteViewsService;


public class ListWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsService.RemoteViewsFactory onGetViewFactory(Intent intent)  {

        return new ListRemoteViewsFactory(this.getApplicationContext(),
                intent.getIntExtra(BakingAppWidgetProvider.EXTRA_RECIPE_INDEX, 0));
    }
}

