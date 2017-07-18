package com.example.vladimir.seabattle.database_wrapper;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

public class ResultsProvider extends ContentProvider {
    private final String LOG_TAG = "myLogs";

    private static final String AUTHORITY = "com.example.vladimir.seabattle";

    private static final String RESULTS_PATH = "results";

    public static final Uri RESULTS_CONTENT_URI = Uri.parse("content://"
            + AUTHORITY + "/" + RESULTS_PATH);

    private static final String RESULTS_CONTENT_TYPE = "vnd.android.cursor.dir/vnd."
            + AUTHORITY + "." + RESULTS_PATH;

    private static final String RESULTS_CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd."
            + AUTHORITY + "." + RESULTS_PATH;

    private static final int URI_RESULTS = 1;

    private static final int URI_RESULTS_USER_NAME = 2;

    private DBController dbHelper;

    private SQLiteDatabase sqLiteDatabase;

    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, RESULTS_PATH, URI_RESULTS);
        uriMatcher.addURI(AUTHORITY, RESULTS_PATH + "/#", URI_RESULTS_USER_NAME);
    }

    public boolean onCreate() {
        Log.d(LOG_TAG, "onCreate");
        dbHelper = new DBController(getContext());
        return true;
    }

    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(getTableNameByUri(uri), projection, selection,
                selectionArgs, null, null, sortOrder);
        Context context = getContext();
        if (context != null) {
            cursor.setNotificationUri(context.getContentResolver(), uri);
        }
        return cursor;
    }

    public Uri insert(@NonNull Uri uri, ContentValues values) {
        if (uriMatcher.match(uri) != URI_RESULTS) {
            throw new IllegalArgumentException("Wrong URI: " + uri);
        }
        sqLiteDatabase = dbHelper.getWritableDatabase();
        long rowID = sqLiteDatabase.insert(getTableNameByUri(uri), null, values);
        Uri resultUri = ContentUris.withAppendedId(uri, rowID);
        Context context = getContext();
        if (context != null) {
            context.getContentResolver().notifyChange(resultUri, null);
        }
        return resultUri;
    }

    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        sqLiteDatabase = dbHelper.getWritableDatabase();
        int contentID = sqLiteDatabase.delete(getTableNameByUri(uri), selection, selectionArgs);
        Context context = getContext();
        if (context != null) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return contentID;
    }

    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        sqLiteDatabase = dbHelper.getWritableDatabase();
        int cnt = sqLiteDatabase
                .update(getTableNameByUri(uri), values, selection, selectionArgs);
        Context context = getContext();
        if (context != null) {
            context.getContentResolver().notifyChange(uri, null);
        }
        return cnt;
    }

    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case URI_RESULTS:
                return RESULTS_CONTENT_TYPE;
            case URI_RESULTS_USER_NAME:
                return RESULTS_CONTENT_ITEM_TYPE;
        }
        return null;
    }

    @SuppressWarnings("SameReturnValue")
    private String getTableNameByUri(@SuppressWarnings("UnusedParameters") Uri uri) {
        return DBController.TABLE_GAME_RESULTS;
    }
}
