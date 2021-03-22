package com.moovies.presentation.ui.main.recyclerview.loadstate

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.moovies.presentation.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.film_load_state_footer_view_item.*

class FilmLoadStateViewHolder(
    override val containerView: View,
    retry: () -> Unit
) : RecyclerView.ViewHolder(containerView), LayoutContainer {


    init {
        retry_button.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            error_msg.text = loadState.error.localizedMessage
        }

        progress_bar.isVisible = loadState is LoadState.Loading
        retry_button.isVisible = loadState !is LoadState.Loading
        error_msg.isVisible = loadState !is LoadState.Loading
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): FilmLoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.film_load_state_footer_view_item, parent, false)
            return FilmLoadStateViewHolder(view, retry)
        }
    }
}