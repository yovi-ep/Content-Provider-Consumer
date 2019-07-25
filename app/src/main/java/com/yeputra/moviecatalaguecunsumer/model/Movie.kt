package com.yeputra.moviecatalaguecunsumer.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieFavorite (
        val id: String?,
        val title: String?,
        val origTitle: String?,
        val overview: String?,
        val adult: Boolean?,
        val release_date: String?,
        val vote_average: String?,
        val backdrop_path: String?,
        val poster_path: String?,
        val type: String?
) : Parcelable