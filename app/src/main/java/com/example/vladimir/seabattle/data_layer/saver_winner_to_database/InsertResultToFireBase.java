package com.example.vladimir.seabattle.data_layer.saver_winner_to_database;

import android.support.annotation.NonNull;

import com.example.vladimir.seabattle.enteities.FireBaseValues;
import com.example.vladimir.seabattle.logic.models.Result;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class InsertResultToFireBase implements InsertResult {

    private OnInsertResultCallback onInsertResultCallback;

    @Override
    public void setOnInsertResultCallback(OnInsertResultCallback onInsertResultCallback) {
        this.onInsertResultCallback = onInsertResultCallback;
    }

    @Override
    public void insertResult(Result result) {
        if (result != null) {
            FirebaseDatabase.getInstance().getReference()
                    .child(FireBaseValues.RESULTS).child(UUID.randomUUID().toString())
                    .setValue(result)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (onInsertResultCallback != null) {
                        onInsertResultCallback.onSuccessInsert();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    if (onInsertResultCallback != null) {
                        onInsertResultCallback.onErrorInsert();
                    }
                }
            });
        }
    }
}
