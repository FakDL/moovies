package com.moovies.presentation.ui.main.recyclerview

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.moovies.presentation.DownloadImageHelper
import com.moovies.presentation.ui.main.recyclerview.FilmHolder.Companion.characterDiff
import com.moovies.domain.model.Film
import com.moovies.presentation.databinding.ItemFilmBinding


class FilmAdapter(
    private val clickLambda: (String) -> Unit,
    private val downloadImageHelper: DownloadImageHelper
) : PagingDataAdapter<Film, FilmHolder>(characterDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmHolder =
        FilmHolder.create(parent, clickLambda, downloadImageHelper)

    override fun onBindViewHolder(holder: FilmHolder, position: Int) {
        val film = getItem(position)
        if (film != null) {
            holder.bind(film)
        }
    }

}

class FilmHolder(
    private val binding: ItemFilmBinding,
    private val clickLambda: (String) -> Unit,
    private val downloadImageHelper: DownloadImageHelper
) : RecyclerView.ViewHolder(binding.root) {


    fun bind(film: Film) {
        downloadImageHelper.setImage(binding.ivPoster, film.imgUrl)
        binding.tvTitle.text = film.title
        itemView.setOnClickListener {
            Log.d("Test", film.id.substring(7, 16) + " in adapter bind")
            clickLambda(film.id.substring(7, 16))
        }
    }


    companion object {
        fun create(
            parent: ViewGroup,
            clickLambda: (String) -> Unit,
            downloadImageHelper: DownloadImageHelper
        ): FilmHolder = FilmHolder(
            ItemFilmBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            clickLambda,
            downloadImageHelper
        )

        val characterDiff = object : DiffUtil.ItemCallback<Film>() {
            override fun areItemsTheSame(old: Film, new: Film): Boolean {
                return old.id == new.id

            }

            override fun areContentsTheSame(old: Film, new: Film): Boolean {
                return old == new
            }

        }
    }
}
