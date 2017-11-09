package com.glsebastiany.bakingapp.view.recipe.list;

import com.glsebastiany.bakingapp.R;
import com.glsebastiany.bakingapp.repository.model.Step;
import com.glsebastiany.bakingapp.view.recyclerview.RVEventHandler;
import com.glsebastiany.bakingapp.view.recyclerview.RVListBaseAdapter;

class RecipeStepsListAdapter extends RVListBaseAdapter<Step> {

    private final RVEventHandler<Step> rvEventHandler;

    public RecipeStepsListAdapter(RVEventHandler<Step> rvEventHandler) {
        this.rvEventHandler = rvEventHandler;
    }

    @Override
    public int getLayoutIdForPosition(int position) {
        return R.layout.list_item_step;
    }

    @Override
    public Object getEventHandlerObjForPosition(int position) {
        return rvEventHandler;
    }
}