package com.moovies.presentation.ui.main.fragments

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.RequestManager
import com.moovies.domain.model.FilmRatings
import com.moovies.presentation.DownloadImageHelper
import com.moovies.presentation.R
import com.moovies.presentation.databinding.FragmentDetailsBinding
import com.moovies.presentation.viewmodels.main.DetailsFetchingViewState
import com.moovies.presentation.viewmodels.main.DetailsViewModel
import kotlinx.android.synthetic.main.fragment_details.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailsFragment
@Inject constructor(
    private val viewModelFactory: ViewModelProvider.Factory,
    requestManager: RequestManager
) : Fragment() {

    private lateinit var filmId: String

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailsViewModel by viewModels {
        viewModelFactory
    }

    private val downloadImageHelper = DownloadImageHelper(requestManager)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        val args: DetailsFragmentArgs by navArgs()
        filmId = args.filmId
        Log.d("Test", "$filmId in onViewCreated details fragment")
        viewModel.setFilmDetails(filmId)
    }

    private fun setupLikeListener() {
        binding.fabLike.setOnClickListener {
            lifecycleScope.launch {
                viewModel.likeFilm(filmId)
            }
        }
    }

    private fun setupDeleteListener() {
        binding.fabLike.setOnClickListener {
            lifecycleScope.launch {
                viewModel.deleteFromFav(filmId)
            }
        }
    }

    private fun setupObservers() {
        viewModel.viewState.observe(viewLifecycleOwner, { state ->
            when (state) {
                is DetailsFetchingViewState.Success -> {
                    binding.clDetails.isVisible = true
                    binding.pbDetails.isVisible = false
                    downloadImageHelper.setImage(binding.ivPoster, state.film.imgUrl)
                    setRatingView(state.ratings)
                    setDurationView(state.film.duration)
                    val title = state.film.title + "(" + state.film.year + ")"
                    binding.tvTitle.text = title
                }
                is DetailsFetchingViewState.Loading -> {
                    binding.clDetails.isVisible = false
                    binding.pbDetails.isVisible = true
                }
            }
        })
        viewModel.isLiked.observe(viewLifecycleOwner, {
            if (it) {
                setupDeleteListener()
                binding.fabLike.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.ic_delete,
                        null
                    )
                )
            } else {
                setupLikeListener()
                binding.fabLike.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.ic_favorite,
                        null
                    )
                )
            }
        })
    }


    private fun setRatingView(ratings: FilmRatings) {
        if (ratings.ratingCount == 0) {
            val str = "Рейтинга пока нет"
            binding.tvRating.text = str
        } else {
            val str = ratings.rating.toString() + "/10 \n" + ratings.ratingCount
            val spannable = SpannableString(str)
            spannable.setSpan(RelativeSizeSpan(2f), 0, 3, 0)
            spannable.setSpan(ForegroundColorSpan(Color.BLACK), 0, 3, 0)
            binding.tvRating.text = spannable
        }
    }

    private fun setDurationView(duration: Int) {
        if (duration == 0) {
            binding.tvDuration.isVisible = false
        } else {
            val str = "Длительность: $duration минут."
            binding.tvDuration.text = str
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}