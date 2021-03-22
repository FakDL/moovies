package com.moovies.presentation.ui.main.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.moovies.presentation.R
import com.moovies.presentation.DownloadImageHelper
import com.moovies.presentation.ui.main.recyclerview.FilmHolder.Companion.characterDiff
import com.moovies.domain.model.Film
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_film.*


class FilmAdapter(
    private val clickLambda: (Long) -> Unit,
    private val downloadImageHelper: DownloadImageHelper
): PagingDataAdapter<Film, FilmHolder>(characterDiff){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmHolder =
        FilmHolder.create(parent, clickLambda,downloadImageHelper)


    override fun onBindViewHolder(holder: FilmHolder, position: Int) {
        val film = getItem(position)
        if (film != null) {
            holder.bind(film)
        }
    }
    fun withMyLoadStateFooter(
        footer: LoadStateAdapter<*>,
        loadLambda: () -> Unit,
        notLoadLambda: () -> Unit
    ): ConcatAdapter {
        addLoadStateListener { loadStates ->
            footer.loadState = when (loadStates.refresh) {
                is LoadState.NotLoading -> {
                    notLoadLambda
                    loadStates.append
                }
                else -> {
                    loadLambda
                    loadStates.refresh
                }
            }
        }
        return ConcatAdapter(this, footer)
    }
}

class FilmHolder(
    override val containerView: View,
    private val clickLambda: (Long) -> Unit,
    private val downloadImageHelper: DownloadImageHelper
): RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(film: Film) {
        downloadImageHelper.setImage(iv_poster, film.img_url)
        tv_title.text = film.title
//        itemView.setOnClickListener {
//            clickLambda(film.id)
//        }
    }

    companion object {
        fun create(parent: ViewGroup, clickLambda: (Long) -> Unit, downloadImageHelper: DownloadImageHelper): FilmHolder = FilmHolder(
            LayoutInflater.from(parent.context).
            inflate(R.layout.item_film, parent, false), clickLambda, downloadImageHelper)
        val characterDiff = object: DiffUtil.ItemCallback<Film>() {
            override fun areItemsTheSame(old: Film, new: Film): Boolean {
                return old.id == new.id

            }

            override fun areContentsTheSame(old: Film, new: Film): Boolean {
                return old == new
            }

        }
    }
}
