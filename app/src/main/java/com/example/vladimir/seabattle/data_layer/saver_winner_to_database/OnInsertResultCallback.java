package com.example.vladimir.seabattle.data_layer.saver_winner_to_database;

import com.example.vladimir.seabattle.logic.models.Result;

public interface OnInsertResultCallback {
    void onSuccessInsert();
    void onErrorInsert();
}
