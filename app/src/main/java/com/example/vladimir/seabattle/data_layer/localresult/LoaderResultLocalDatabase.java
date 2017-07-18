package com.example.vladimir.seabattle.data_layer.localresult;


import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;

import com.example.vladimir.seabattle.database_wrapper.DBController;
import com.example.vladimir.seabattle.database_wrapper.ResultsProvider;
import com.example.vladimir.seabattle.enteritis.ContentType;
import com.example.vladimir.seabattle.logic.models.Result;

import java.util.ArrayList;
import java.util.List;

public class LoaderResultLocalDatabase implements ILoadResult,
        LoaderManager.LoaderCallbacks<Cursor> {

    private static final int ID = 3;

    private static final String KEY_COMPUTER = "COMPUTER";

    private static final String KEY_HUMAN = "HUMAN";

    private final Context context;

    private final LoaderManager loaderManager;

    private OnLoadResultFinished onLoadResultFinished;

    public LoaderResultLocalDatabase(final Context context, final LoaderManager loaderManager) {
        this.context = context;
        this.loaderManager = loaderManager;
    }

    @Override
    public void setCallback(final OnLoadResultFinished onLoadResultFinished) {
        this.onLoadResultFinished = onLoadResultFinished;
    }

    @Override
    public void load() {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_COMPUTER, ContentType.COMPUTER.toString());
        bundle.putString(KEY_HUMAN, ContentType.HUMAN.toString());
        loaderManager.initLoader(ID, bundle, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(context, ResultsProvider.RESULTS_CONTENT_URI, null,
                DBController.USER_NAME + " = ? OR " + DBController.USER_NAME + " = ? ",
                new String[]{bundle.getString(KEY_COMPUTER), bundle.getString(KEY_HUMAN)}, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        List<Result> computerData = new ArrayList<>();
        List<Result> userData = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            try {
                do {
                    ContentType contentType = ContentType.valueOf(
                            cursor.getString(cursor.getColumnIndex(DBController.CONTENT_TYPE)));
                    switch (contentType) {
                        case COMPUTER:
                            computerData.add(new Result(cursor));
                            break;
                        case HUMAN:
                            userData.add(new Result(cursor));
                            break;
                    }
                } while (cursor.moveToNext());
            } finally {
                cursor.close();
            }
        }
        if (onLoadResultFinished != null) {
            onLoadResultFinished.onLoadResultSuccess(mergeResult(computerData, userData));
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private List<Result> mergeResult(List<Result> compResults, List<Result> userResults) {
        List<Result> results;
        if (compResults.size() > userResults.size()) {
            results = concatList(compResults, userResults);
        } else {
            results = concatList(userResults, compResults);
        }
        return results;
    }

    private List<Result> concatList(List<Result> big, List<Result> small) {
        List<Result> concatList = new ArrayList<>();
        for (int i = 0; i < small.size(); i++) {
            concatList.add(small.get(i));
            concatList.add(big.get(i));
        }
        concatList.addAll(big.subList(small.size(), big.size()));
        return concatList;
    }
}
