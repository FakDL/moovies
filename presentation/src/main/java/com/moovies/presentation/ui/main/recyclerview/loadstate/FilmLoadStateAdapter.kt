package com.moovies.presentation.ui.main.recyclerview.loadstate

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class FilmLoadStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<FilmLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: FilmLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): FilmLoadStateViewHolder {
        return FilmLoadStateViewHolder.create(parent, retry)
    }
}
