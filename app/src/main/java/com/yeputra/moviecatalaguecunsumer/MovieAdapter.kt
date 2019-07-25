package com.yeputra.moviecatalaguecunsumer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yeputra.moviecatalaguecunsumer.base.BaseRecyclerViewAdapter
import com.yeputra.moviecatalaguecunsumer.model.MovieFavorite
import com.yeputra.moviecatalaguecunsumer.utils.Constans.Companion.POSTER_MEDIUM
import com.yeputra.moviecatalaguecunsumer.utils.Constans.Companion.POSTER_URL
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_movie.*

class MovieAdapter(
    private val onClick: (MovieFavorite) -> Unit
) : BaseRecyclerViewAdapter<MovieAdapter.VHolder, MovieFavorite>() {

    override fun onBindViewHolder(holder: VHolder, item: MovieFavorite, position: Int) {
        holder.binding(item, onClick)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder {
        return VHolder(
                LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.item_movie, parent, false))
    }


    class VHolder(override val containerView: View)
        : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun binding(data: MovieFavorite, onClick: (MovieFavorite) -> Unit) {
            var year = ""

            data.release_date?.let {
                if (it.length > 4) year = "(${it.substring(0,4)})"
            }

            tv_title.text = String.format("%s %s", data.title?:"-", year)
            tv_rating.text = data.vote_average.toString()

            Glide.with(containerView.context)
                    .load(POSTER_URL + POSTER_MEDIUM + data.poster_path)
                    .placeholder(R.mipmap.ic_placeholder)
                    .into(img_poster)

            containerView.setOnClickListener { onClick(data) }
        }
    }
}
