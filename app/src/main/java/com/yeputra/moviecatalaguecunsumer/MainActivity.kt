package com.yeputra.moviecatalaguecunsumer

import android.database.Cursor
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.yeputra.moviecatalaguecunsumer.contentprovider.DatabaseContract
import com.yeputra.moviecatalaguecunsumer.contentprovider.FavoriteProvider
import com.yeputra.moviecatalaguecunsumer.contentprovider.FavoriteService
import com.yeputra.moviecatalaguecunsumer.model.FilmType
import com.yeputra.moviecatalaguecunsumer.model.MovieFavorite
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Boolean

class MainActivity : AppCompatActivity() {
    private var adapter = MovieAdapter()
    private lateinit var favoriteService: FavoriteService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        favoriteService = FavoriteService(this)
        swiperefresh.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorAccent))
        swiperefresh.setOnRefreshListener {
            loadData()
        }

        list_item.layoutManager = GridLayoutManager(this, 2)
        list_item.overScrollMode = View.OVER_SCROLL_NEVER
        list_item.adapter = adapter

        loadData()
    }

    private fun loadData() {
        val items = mutableListOf<MovieFavorite>()
        val cursor = contentResolver.query(
            DatabaseContract.FavoriteColumns.CONTENT_URI,
            null,
            null,
            null,
            null)
        cursor?.let {
            for (i in 0 until it.count) {
                getMovieFromCursor(it)?.let { it1 -> items.add(it1) }
            }
        }

        //favoriteService.findAll(FilmType.MOVIE) {

            adapter.setItem(items)
            swiperefresh.isRefreshing = false
        //}
    }

    private fun getMovieFromCursor(cursor: Cursor): MovieFavorite? {
        return if (cursor.count > 0) {
            MovieFavorite(
                "",//cursor.getString(cursor.getColumnIndexOrThrow("_id")),
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.TITLE)),
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.ORIG_TITLE)),
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.OVERVIEW)),
                Boolean.parseBoolean(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.ADULT))),
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.RELEASE_DATE)),
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.RATE)),
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.BACKDROP)),
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.POSTER)),
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.TYPE))
            )
        } else {
            null
        }
    }
}
