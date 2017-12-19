package com.testapp.socialuserinfo.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;


public class GlideLoader {

    private GlideLoader() {

    }

    public static void loadImage(ImageView imageView, Object linkPath) {
        Glide.with(imageView.getContext())
                .applyDefaultRequestOptions(new RequestOptions()
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                        .placeholder(R.drawable.ic_add_photo_to_request)
//                        .error(R.drawable.ic_add_photo_to_request)
                )
                .load(linkPath)
                .into(imageView);
    }

}
