package com.fakdl.moovies.ui.main.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fakdl.moovies.R
import com.fakdl.moovies.repository.network.responses.Result
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_film.*


class FilmAdapter(
    private val list: List<Result>,
    private val clickLambda:(Long) -> Unit
): RecyclerView.Adapter<FilmHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmHolder =
        FilmHolder.create(parent, clickLambda)

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: FilmHolder, position: Int) {
        holder.bind(list[position])
    }

}

class FilmHolder(
    override val containerView: View,
    private val clickLambda: (Long) -> Unit
): RecyclerView.ViewHolder(containerView), LayoutContainer {

    

    fun bind(film: Result) {
//        downloadImageHelper.setImage(iv_poster, film.poster_path)
        tv_title.text = film.title
        itemView.setOnClickListener {
            clickLambda(film.id)
        }
    }

    companion object {
        fun create(parent: ViewGroup, clickLambda: (Long) -> Unit): FilmHolder = FilmHolder(
            LayoutInflater.from(parent.context).
            inflate(R.layout.item_film, parent, false), clickLambda)
    }
}
