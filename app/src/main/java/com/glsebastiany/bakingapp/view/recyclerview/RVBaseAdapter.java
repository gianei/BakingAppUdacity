package com.glsebastiany.bakingapp.view.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

public abstract class RVBaseAdapter extends RecyclerView.Adapter<RVGenericViewHolder> {

    @Override
    public RVGenericViewHolder onCreateViewHolder(android.view.ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = android.view.LayoutInflater.from(parent.getContext());

        android.databinding.ViewDataBinding binding = android.databinding.DataBindingUtil.inflate(
                layoutInflater, viewType, parent, false);

        return new RVGenericViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RVGenericViewHolder holder, int position) {
        Object model = getModelObjForPosition(holder.getBinding().getRoot().getContext(), position);
        holder.bindModel(model);

        Object eventHandler = getEventHandlerObjForPosition(position);
        if (eventHandler != null) {
            holder.bindEventHandler(eventHandler);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return getLayoutIdForPosition(position);
    }

    protected abstract Object getModelObjForPosition(Context context, int position);

    protected Object getEventHandlerObjForPosition(int position) {
        return null;
    }

    protected abstract int getLayoutIdForPosition(int position);
}