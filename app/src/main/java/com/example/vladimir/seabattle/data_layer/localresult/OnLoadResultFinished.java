package com.example.vladimir.seabattle.data_layer.localresult;

import com.example.vladimir.seabattle.logic.models.Result;

import java.util.List;

public interface OnLoadResultFinished {
    void onLoadResultSuccess(List<Result> results);
    void onLoadResultError();
}
