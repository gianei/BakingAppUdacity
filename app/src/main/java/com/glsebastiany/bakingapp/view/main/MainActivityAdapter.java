package com.glsebastiany.bakingapp.view.main;

import com.glsebastiany.bakingapp.R;
import com.glsebastiany.bakingapp.repository.model.Recipe;
import com.glsebastiany.bakingapp.view.recyclerview.RVEventHandler;
import com.glsebastiany.bakingapp.view.recyclerview.RVListBaseAdapter;

public class MainActivityAdapter extends RVListBaseAdapter<Recipe> {

    private final RVEventHandler<Recipe> eventListener;

    public MainActivityAdapter(RVEventHandler<Recipe> eventListener) {
        this.eventListener = eventListener;
    }

    @Override
    public int getLayoutIdForPosition(int position) {
        return R.layout.card_recipe;
    }

    @Override
    public Object getEventHandlerObjForPosition(int position) {
        return eventListener;
    }
}