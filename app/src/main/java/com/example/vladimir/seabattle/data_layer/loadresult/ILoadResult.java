package com.example.vladimir.seabattle.data_layer.loadresult;

public interface ILoadResult {
    void setCallback(OnLoadResultFinished onLoadResultFinished);
    void load();
}
