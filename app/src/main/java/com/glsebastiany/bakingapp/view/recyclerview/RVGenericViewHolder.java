package com.glsebastiany.bakingapp.view.recyclerview;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

public class RVGenericViewHolder extends RecyclerView.ViewHolder {

    private ViewDataBinding binding;

    public RVGenericViewHolder(ViewDataBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bindModel(Object model) {
        binding.setVariable(com.glsebastiany.bakingapp.BR.obj, model);
        binding.executePendingBindings();
    }

    public void bindEventHandler(Object eventHandler) {
        binding.setVariable(com.glsebastiany.bakingapp.BR.eventHandler, eventHandler);
    }

    public ViewDataBinding getBinding() {
        return binding;
    }
}