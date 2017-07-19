package com.example.vladimir.seabattle.ui.result;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.vladimir.seabattle.data_layer.loadresult.ILoadResult;
import com.example.vladimir.seabattle.data_layer.loadresult.LoadResultsFireBaseDatabase;
import com.example.vladimir.seabattle.data_layer.loadresult.OnLoadResultFinished;
import com.example.vladimir.seabattle.R;
import com.example.vladimir.seabattle.data_layer.loadresult.LoaderResultLocalDatabase;
import com.example.vladimir.seabattle.logic.models.Result;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity implements OnLoadResultFinished {

    private RecyclerView rvResults;

    private ResultsAdapter resultsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        List<Result> blankResult = new ArrayList<>();
        startLoad();
        rvResults = (RecyclerView) findViewById(R.id.listRecyclerView);
        rvResults.setLayoutManager(new LinearLayoutManager(this));
        resultsAdapter = new ResultsAdapter(blankResult);
        rvResults.setAdapter(resultsAdapter);
    }

    @Override
    public void onLoadResultSuccess(List<Result> results) {
        resultsAdapter = new ResultsAdapter(results);
        rvResults.setAdapter(resultsAdapter);
    }

    @Override
    public void onLoadResultError() {

    }

    private void startLoad() {
        ILoadResult iLoadResult = new LoadResultsFireBaseDatabase();
//                new LoaderResultLocalDatabase(getApplicationContext(), getLoaderManager());
        iLoadResult.setCallback(this);
        iLoadResult.load();
    }

}
