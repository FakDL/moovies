package com.moovies.presentation

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.target.ViewTarget

class DownloadImageHelper(
    private val requestManager: RequestManager
) {
    fun setImage(imageView: ImageView, imageUrl: String): ViewTarget<ImageView, Drawable> {
        return requestManager
            .load(imageUrl)
            .into(imageView)
    }

}
