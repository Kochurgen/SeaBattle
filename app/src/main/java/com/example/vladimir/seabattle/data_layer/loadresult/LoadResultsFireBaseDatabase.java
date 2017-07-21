package com.example.vladimir.seabattle.data_layer.loadresult;

import com.example.vladimir.seabattle.enteities.FireBaseValues;
import com.example.vladimir.seabattle.logic.models.Result;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LoadResultsFireBaseDatabase implements ILoadResult {

    private OnLoadResultFinished onLoadResultFinished;

    @Override
    public void setCallback(OnLoadResultFinished onLoadResultFinished) {
        this.onLoadResultFinished = onLoadResultFinished;
    }

    @Override
    public void load() {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference databaseReference = database.child(FireBaseValues.RESULTS);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    List<Result> results = new ArrayList<Result>();
                    for (DataSnapshot dataSnapshotResult : dataSnapshot.getChildren()) {
                        if (dataSnapshotResult != null) {
                            results.add(dataSnapshotResult.getValue(Result.class));
                        }
                    }
                    if (onLoadResultFinished != null) {
                        onLoadResultFinished.onLoadResultSuccess(TransformResultsUtil.transformResultsForView(results));
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                if (onLoadResultFinished != null) {
                    onLoadResultFinished.onLoadResultError();
                }
            }
        });
    }
}
