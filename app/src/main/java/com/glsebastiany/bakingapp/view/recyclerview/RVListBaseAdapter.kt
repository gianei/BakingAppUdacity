package com.glsebastiany.bakingapp.view.recyclerview

import android.content.Context

abstract class RVListBaseAdapter<T> : RVBaseAdapter() {

    private var list : List<T>? = listOf()

    override fun getModelObjForPosition(context: Context, position: Int): Any =
            list?.get(position) as Any

    override fun getItemCount(): Int = list?.count() ?: 0

    fun updateItems(items: List<T>?) {
        list = items

        notifyDataSetChanged()
    }

    fun indexOf(item: T): Int = list?.indexOf(item) ?: -1

}