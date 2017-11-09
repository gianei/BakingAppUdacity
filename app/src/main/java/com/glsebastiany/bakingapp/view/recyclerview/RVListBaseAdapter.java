package com.glsebastiany.bakingapp.view.recyclerview;

import android.content.Context;

import java.util.List;

public abstract class RVListBaseAdapter<T> extends RVBaseAdapter {

    private List<T> list;

    @Override
    public Object getModelObjForPosition(Context context, int position) {
        if (list == null){
            return null;
        } else {
            return list.get(position);
        }
    }

    @Override
    public int getItemCount() {
        if (list == null){
            return 0;
        } else {
            return list.size();
        }
    }

    public void updateItems(List<T> items) {
        list = items;

        notifyDataSetChanged();
    }

    public int indexOf(T item){
        if (list == null) {
            return -1;
        } else {
            return list.indexOf(item);
        }
    }

}