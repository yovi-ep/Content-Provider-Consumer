package com.yeputra.moviecatalaguecunsumer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.yeputra.moviecatalaguecunsumer.model.MovieFavorite
import com.yeputra.moviecatalaguecunsumer.utils.Constans
import kotlinx.android.synthetic.main.activity_detail_movie.*

class DetailMovieActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)

        initViewConfigure()
    }

    private fun initViewConfigure() {
        var year = ""
        var adult = ""
        val movie = intent.getParcelableExtra<MovieFavorite>(Constans.INTENT_DATA)

        movie.release_date?.let {
            if (it.length > 4) year = String.format("(%s)",it.substring(0,4))
        }
        movie.adult?.let {
            if (it) adult = " | 17+"
        }

        tv_title.text = String.format("%s %s", (movie.title?:"-"), year)
        tv_rating.text = String.format("%s %s", movie.vote_average.toString(), adult)
        tv_overview.text = movie.overview

        Glide.with(this)
                .load(Constans.POSTER_URL + Constans.POSTER_LARGE + movie.backdrop_path)
                .placeholder(R.mipmap.ic_placeholder)
                .into(img_banner)

        Glide.with(this)
                .load(Constans.POSTER_URL + Constans.POSTER_MEDIUM + movie.poster_path)
                .placeholder(R.mipmap.ic_placeholder)
                .into(img_poster)

    }
}

