package com.example.vladimir.seabattle.data_layer.saver_winner_to_database;

import android.content.AsyncQueryHandler;
import android.content.Context;
import android.net.Uri;

import com.example.vladimir.seabattle.database_wrapper.ResultsProvider;
import com.example.vladimir.seabattle.logic.models.Result;

public class InsertResultToDatabase extends AsyncQueryHandler implements InsertResult {

    private OnInsertResultCallback onInsertResultCallback;

    public InsertResultToDatabase(Context context) {
        super(context.getContentResolver());
    }

    @Override
    protected void onInsertComplete(int token, Object cookie, Uri uri) {
        super.onInsertComplete(token, cookie, uri);
        if (onInsertResultCallback != null) {
            onInsertResultCallback.onSuccessInsert();
        }
    }

    @Override
    public void insertResult(Result result) {
        startInsert(0, null, ResultsProvider.RESULTS_CONTENT_URI,
                result.getContentValues());
    }

    @Override
    public void setOnInsertResultCallback(
            OnInsertResultCallback onInsertResultCallback) {
        this.onInsertResultCallback = onInsertResultCallback;
    }
}
