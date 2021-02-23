package com.fakdl.moovies.repository.network

import android.widget.ImageView
import com.bumptech.glide.RequestManager
import com.fakdl.moovies.util.Constants
import javax.inject.Inject

class DownloadImageHelper @Inject constructor(
    private val glide: RequestManager
) {
        fun setImage(imageView: ImageView, imagePath: String) {
            glide
                .load(Constants.IMG_URL + imagePath)
                .into(imageView)
        }

}