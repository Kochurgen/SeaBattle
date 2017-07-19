package com.example.vladimir.seabattle.data_layer.loadresult;


import com.example.vladimir.seabattle.logic.models.Result;

import java.util.ArrayList;
import java.util.List;

class TransformatorResultsUtil {

    public static List<Result> transformResultsForView(final List<Result> results) {
        List<Result> computerResults = new ArrayList<>();
        List<Result> humanResults = new ArrayList<>();
        for (Result result : results) {
            switch (result.contentType) {
                case COMPUTER:
                    computerResults.add(result);
                    break;
                case HUMAN:
                    humanResults.add(result);
                    break;
            }
        }
        return mergeResult(computerResults, humanResults);
    }

    private static List<Result> mergeResult(final List<Result> compResults, final List<Result> userResults) {
        List<Result> results;
        if (compResults.size() > userResults.size()) {
            results = concatList(compResults, userResults);
        } else {
            results = concatList(userResults, compResults);
        }
        return results;
    }

    private static List<Result> concatList(final List<Result> big, final List<Result> small) {
        List<Result> concatList = new ArrayList<>();
        for (int i = 0; i < small.size(); i++) {
            concatList.add(small.get(i));
            concatList.add(big.get(i));
        }
        concatList.addAll(big.subList(small.size(), big.size()));
        return concatList;
    }
}
