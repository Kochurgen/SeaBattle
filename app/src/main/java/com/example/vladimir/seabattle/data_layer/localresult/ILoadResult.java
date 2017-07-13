package com.example.vladimir.seabattle.data_layer.localresult;

public interface ILoadResult {
    void setCallback(OnLoadResultFinished onLoadResultFinished);
    void load();
}
