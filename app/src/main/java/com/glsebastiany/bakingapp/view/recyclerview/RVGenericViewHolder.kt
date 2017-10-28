package com.glsebastiany.bakingapp.view.recyclerview

import android.support.v7.widget.RecyclerView

class RVGenericViewHolder(val binding: android.databinding.ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bindModel(model: Any) {
        binding.setVariable(com.glsebastiany.bakingapp.BR.obj, model)
        binding.executePendingBindings()
    }

    fun bindEventHandler(eventHandler: Any) {
        binding.setVariable(com.glsebastiany.bakingapp.BR.eventHandler, eventHandler)
    }
}