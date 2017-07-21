package com.example.vladimir.seabattle.data_layer.loadresult;


import android.app.LoaderManager;
import android.content.Context;

import com.example.vladimir.seabattle.enteities.Values;

public class FactoryLoaderResultAction {
    public ILoadResult getLoader(Context context, LoaderManager loaderManager) {
        ILoadResult iLoadResult;
        switch (Values.dataBaseType) {
            case FIRE_BASE:
                iLoadResult = new LoadResultsFireBaseDatabase();
                break;
            case LOCAL_BASE:
                iLoadResult =new LoaderResultLocalDatabase(context, loaderManager);
                break;
            default:
                iLoadResult = null;
        }
        return iLoadResult;
    }
}
