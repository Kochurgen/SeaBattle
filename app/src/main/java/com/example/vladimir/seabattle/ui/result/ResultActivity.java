package com.example.vladimir.seabattle.ui.result;

import android.content.AsyncQueryHandler;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.vladimir.seabattle.database_wrapper.DBController;
import com.example.vladimir.seabattle.database_wrapper.ResultsProvider;
import com.example.vladimir.seabattle.enteritis.ContentType;
import com.example.vladimir.seabattle.data_layer.localresult.ILoadResult;
import com.example.vladimir.seabattle.data_layer.localresult.OnLoadResultFinished;
import com.example.vladimir.seabattle.R;
import com.example.vladimir.seabattle.data_layer.localresult.LoaderResultLocalDatabase;
import com.example.vladimir.seabattle.logic.models.Result;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity implements OnLoadResultFinished {

    private RecyclerView recyclerView;

    private ResultsAdapter resultsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        List<Result> blankResult = new ArrayList<>();
        startLoad();
        recyclerView = (RecyclerView) findViewById(R.id.listRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        resultsAdapter = new ResultsAdapter(blankResult);
        recyclerView.setAdapter(resultsAdapter);
    }

    @Override
    public void onLoadResultSuccess(List<Result> results) {
        resultsAdapter = new ResultsAdapter(results);
        recyclerView.setAdapter(resultsAdapter);
    }

    @Override
    public void onLoadResultError() {

    }

    private void startLoad() {
        ILoadResult iLoadResult =
                new LoaderResultLocalDatabase(getApplicationContext(), getLoaderManager());
        iLoadResult.setCallback(this);
        iLoadResult.load();
    }

}
