package com.glsebastiany.bakingapp.view;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.Palette;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.glsebastiany.bakingapp.R;
import com.glsebastiany.bakingapp.util.Util;

public class DataBinder {

    private DataBinder() {
        //NO-OP
    }

    @BindingAdapter({"viewToColor","glideImageUrl"})
    public static void setImageUrl(ImageView imageView, View viewToColor, String imageUrl) {

        if (imageUrl == null || TextUtils.isEmpty(imageUrl)) {
            imageUrl = "https://loremflickr.com/600/338/food";
        }

        Context context = imageView.getContext();

        Glide.with(context)
                .asBitmap()
                .load(imageUrl)
                .apply(RequestOptions.centerCropTransform())
                .into(new ColoredImageViewTarget(context, imageView, viewToColor));
    }
    

    static class ColoredImageViewTarget extends BitmapImageViewTarget {

        private final View viewToColor;
        private final Context context;

        public ColoredImageViewTarget(Context context, ImageView viewHolder, View viewToColor) {
            super(viewHolder);
            this.context = context;
            this.viewToColor = viewToColor;
        }

        @Override
        public void onResourceReady(Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
            super.onResourceReady(bitmap, transition);
            Palette palette = Palette.from(bitmap).generate();
            int color = palette.getVibrantColor(ContextCompat.getColor(context, R.color.colorAccent));
            color = Util.adjustAlpha(color, 0.75f);

            viewToColor.setBackgroundColor(color);
        }
    }

}
