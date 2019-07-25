package com.yeputra.moviecatalaguecunsumer.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.yeputra.moviecatalaguecunsumer.model.FilmType;

import static com.yeputra.moviecatalaguecunsumer.contentprovider.DatabaseContract.FavoriteColumns.TABLE_NAME;

public class FavoriteProvider extends ContentProvider {
    private static final int NOTE = 1;
    private static final int NOTE_ID = 2;
    private static final int NOTE_3 = 3;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private FavoriteHelper favoriteHelper;

    static {
        sUriMatcher.addURI(DatabaseContract.AUTHORITY, TABLE_NAME + "/#", NOTE);
        sUriMatcher.addURI(DatabaseContract.AUTHORITY, TABLE_NAME + "/#", NOTE_ID);
        sUriMatcher.addURI(DatabaseContract.AUTHORITY, TABLE_NAME, NOTE_3);
    }

    @Override
    public boolean onCreate() {
        favoriteHelper = FavoriteHelper.getInstance(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        favoriteHelper.open();
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case NOTE:
                cursor = favoriteHelper.providerQuery(uri.getLastPathSegment());
                break;
            case NOTE_ID:
                cursor = favoriteHelper.providerQueryById(uri.getLastPathSegment());
                break;
            case NOTE_3:
                cursor = favoriteHelper.providerQueryById(FilmType.MOVIE.name());
                break;
            default:
                cursor = null;
                break;
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
