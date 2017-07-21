package com.example.vladimir.seabattle.data_layer.saver_winner_to_database;

import android.content.Context;

import com.example.vladimir.seabattle.enteities.Values;

public class FactoryInsertResultAction {

    public InsertResult getInsertAction(Context context) {
        InsertResult insertResult;
        switch (Values.dataBaseType) {
            case FIRE_BASE:
                insertResult = new InsertResultToFireBase();
                break;
            case LOCAL_BASE:
                insertResult = new InsertResultToDatabase(context);
                break;
            default:
                insertResult = null;
        }
        return insertResult;
    }
}
