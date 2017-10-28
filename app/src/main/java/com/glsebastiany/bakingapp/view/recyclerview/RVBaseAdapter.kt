
package com.glsebastiany.bakingapp.view.recyclerview

import android.support.v7.widget.RecyclerView

abstract class RVBaseAdapter : RecyclerView.Adapter<RVGenericViewHolder>() {

    override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): RVGenericViewHolder {
        val layoutInflater = android.view.LayoutInflater.from(parent.context)

        val binding = android.databinding.DataBindingUtil.inflate<android.databinding.ViewDataBinding>(
                layoutInflater, viewType, parent, false)

        return RVGenericViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RVGenericViewHolder,
                                  position: Int) {
        val model = getModelObjForPosition(holder.binding.root.context, position)
        holder.bindModel(model)

        val eventHandler = getEventHandlerObjForPosition(position)
        if (eventHandler != null) {
            holder.bindEventHandler(eventHandler)
        }
    }

    override fun getItemViewType(position: Int): Int = getLayoutIdForPosition(position)

    protected abstract fun getModelObjForPosition(context: android.content.Context, position: Int): Any

    open protected fun getEventHandlerObjForPosition(position: Int): Any? = null

    protected abstract fun getLayoutIdForPosition(position: Int): Int
}