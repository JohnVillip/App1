package com.example.villip.app1.dataprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;


public class LinksContentProvider extends ContentProvider {

    private static final String TAG = "MyLogs";

    private static final int LINKS = 1;
    private static final int LINK_ID = 2;

    private DatabaseHelper mDatabaseHelper;
    private final UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private SQLiteDatabase db;

    @Override
    public boolean onCreate() {
        Log.d(TAG, "onCreate");

        mDatabaseHelper = new DatabaseHelper(getContext());
        mUriMatcher.addURI(LinksContract.AUTHORITY, "links", LINKS);
        mUriMatcher.addURI(LinksContract.AUTHORITY, "links/#", LINK_ID);

        db = mDatabaseHelper.getWritableDatabase();
        return (db == null)? false:true;
    }

    @Override
    public Cursor query(final Uri uri, final String[] projection, final String selection, final String[] selectionArgs, final String sortOrder) {

        switch (mUriMatcher.match(uri)) {
            case LINKS: {
                final Cursor cursor = mDatabaseHelper.getReadableDatabase().query(
                        LinksContract.Links.LINKS_TABLE,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );

                cursor.setNotificationUri(getContext().getContentResolver(), LinksContract.Links.CONTENT_URI);

                return cursor;
            }
            default:
                Log.d(TAG, "no URI match");
                return null;
        }
    }

    @Override
    public String getType(final Uri uri) {
        Log.d(TAG, "getType: " + uri);

        switch (mUriMatcher.match(uri)) {
            case LINKS:
                return LinksContract.Links.CONTENT_TYPE;

            case LINK_ID:
                return LinksContract.Links.CONTENT_ITEM_TYPE;

            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        long rowID = db.insert(	LinksContract.Links.LINKS_TABLE, "", values);

        if (rowID > 0)
        {
            Uri _uri = ContentUris.withAppendedId(LinksContract.CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }
        throw new SQLException("Failed to add a record into " + uri);
    }

    @Override
    public int delete(final Uri uri, final String selection, final String[] selectionArgs) {
        int delCount;

        switch (mUriMatcher.match(uri)) {
            case LINK_ID:
                    String idStr = uri.getLastPathSegment();
                    String where = LinksContract.Links._ID + " = " + idStr;
                    if (!TextUtils.isEmpty(selection)) {
                        where += " AND " + selection;
                    }
                    delCount = db.delete(
                            LinksContract.Links.LINKS_TABLE,
                            where,
                            selectionArgs);
                    break;
            default:
        throw new IllegalArgumentException("Wrong URI: " + uri);
    }

            getContext().getContentResolver().notifyChange(uri, null);
        return delCount;
    }

    @Override
    public int update(final Uri uri, final ContentValues values, final String selection, final String[] selectionArgs) {

        int updateCount;

        switch (mUriMatcher.match(uri)) {
            case LINK_ID:
                String idStr = uri.getLastPathSegment();
                String where = LinksContract.Links._ID + " = " + idStr;
                if (!TextUtils.isEmpty(selection)) {
                    where += " AND " + selection;
                }
                updateCount = db.update(
                        LinksContract.Links.LINKS_TABLE,
                        values,
                        where,
                        selectionArgs);
                break;
        default:
        throw new IllegalArgumentException("Wrong URI: " + uri);
    }
        getContext().getContentResolver().notifyChange(uri, null);
        return updateCount;

    }

}
