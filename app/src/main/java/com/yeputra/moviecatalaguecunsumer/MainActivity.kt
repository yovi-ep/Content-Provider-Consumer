package com.yeputra.moviecatalaguecunsumer

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.yeputra.moviecatalaguecunsumer.contentprovider.DatabaseContract
import com.yeputra.moviecatalaguecunsumer.model.MovieFavorite
import com.yeputra.moviecatalaguecunsumer.utils.Constans
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        swiperefresh.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorAccent))
        swiperefresh.setOnRefreshListener {
            loadData()
        }

        adapter = MovieAdapter {
            val `in` = Intent(this, DetailMovieActivity::class.java)
            `in`.putExtra(Constans.INTENT_DATA, it)
            startActivityForResult(`in`, 0)
        }
        list_item.layoutManager = GridLayoutManager(this, 2)
        list_item.overScrollMode = View.OVER_SCROLL_NEVER
        list_item.adapter = adapter

        loadData()
    }

    @SuppressLint("Recycle")
    private fun loadData() {
        val items = mutableListOf<MovieFavorite>()
        val cursor = contentResolver.query(
            DatabaseContract.FavoriteColumns.CONTENT_URI,
            null,
            null,
            null,
            null)

        cursor?.let {
            it.moveToFirst()
            for (i in 0 until it.count) {
                getMovieFromCursor(it)?.let { it1 -> items.add(it1) }
                it.moveToNext()
            }
        }

        adapter.setItem(items)
        swiperefresh.isRefreshing = false
    }

    private fun getMovieFromCursor(cursor: Cursor): MovieFavorite? {
        return if (cursor.count > 0) {
            MovieFavorite(
                cursor.getString(cursor.getColumnIndexOrThrow("_id")),
                cursor.getString(cursor.getColumnIndex(DatabaseContract.FavoriteColumns.TITLE)),
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.ORIG_TITLE)),
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.OVERVIEW)),
                (cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.ADULT))?:"0").toBoolean(),
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
