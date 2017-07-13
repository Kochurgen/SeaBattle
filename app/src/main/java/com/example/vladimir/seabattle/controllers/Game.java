package com.example.vladimir.seabattle.controllers;

import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.IntRange;
import android.util.Log;

import com.example.vladimir.seabattle.Interfaces.ShootCallback;
import com.example.vladimir.seabattle.data_layer.saver_winner_to_database.InsertResult;
import com.example.vladimir.seabattle.data_layer.saver_winner_to_database.InsertResultToDatabase;
import com.example.vladimir.seabattle.database_wrapper.DBController;
import com.example.vladimir.seabattle.enteritis.ContentType;
import com.example.vladimir.seabattle.logic.models.AIPlayer;
import com.example.vladimir.seabattle.logic.models.Board;
import com.example.vladimir.seabattle.logic.models.HPlayer;
import com.example.vladimir.seabattle.logic.models.Player;
import com.example.vladimir.seabattle.logic.models.Result;

public class Game implements ShootCallback {

    private final static int SHIP_SIZE = 10;

    private final static int MAX_ITERATION = 2;

    private HPlayer humanPlayer;

    private AIPlayer aiPlayer;

    private GameListener gameListener;

    private int count;

    private int finish;

    private int userSteps;

    private int computerSteps;

    private long startTime = 0L;

    private Handler customHandler = new Handler();

    private long updatedTime = 0L;

    private final InsertResult insertResult;

    public interface GameListener {
        void showDialog(final String playerName);

        void updateDate(final Board userBoard, final Board computerBoard);
    }

    public Game(Context context, GameListener gameListener) {
        this.gameListener = gameListener;
        this.insertResult = new InsertResultToDatabase(context);
        int[] cord = new int[2];
        createNewGame();
    }

    public void createNewGame() {
        aiPlayer = new AIPlayer(this);
        humanPlayer = new HPlayer(this);
        updateView();
        userSteps = 0;
        computerSteps = 0;
        count = 0;
        finish = 0;
        startTime = SystemClock.uptimeMillis();
        customHandler.postDelayed(updateTimerThread, 0);
    }

    public void shoot(@IntRange(from = 0, to = 9) int x, @IntRange(from = 0, to = 9) int y) {
        Player.ShootResult shootResult = aiPlayer.shoot(x, y);
        updateView();
        if (shootResult != null) {
            userSteps++;
        }
        if (shootResult != Player.ShootResult.MISSED && !isShipFinished()) {
            return;
        }

        int res = (int) updatedTime;
        Result result;
        if (isShipFinished()) {
            showFinishDialog();
            finish++;
            if (aiPlayer.board.getKilledShips() == SHIP_SIZE) {
                result =
                        new Result(ContentType.HUMAN.toString(), res, userSteps, ContentType.HUMAN);
            } else {
                result = new Result(ContentType.COMPUTER.toString(), res, computerSteps,
                        ContentType.COMPUTER);
            }
            if (finish < MAX_ITERATION) {
                Log.d("Result", result.getUserName());
                writeWinner(result);
            }
        } else {
            runComputerStep();
        }
    }

    private void showFinishDialog() {
        if (humanPlayer.board.getKilledShips() == SHIP_SIZE) {
            count++;
            if (count < MAX_ITERATION) {
                gameListener
                        .showDialog(ContentType.COMPUTER.toString());

            }
        } else {
            count++;
            if (count < MAX_ITERATION) {
                gameListener.showDialog(ContentType.HUMAN.toString());
            }
        }
    }

    private void runComputerStep() {
        while (true) {
            Player.ShootResult shootResult = humanPlayer.shoot();
            if (shootResult != null) {
                computerSteps++;
            }
            if (shootResult == Player.ShootResult.MISSED) {
                updateView();
                break;
            }
            if (isShipFinished()) {
                break;
            }
        }
    }

    private boolean isShipFinished() {
        return humanPlayer.board.getKilledShips() == SHIP_SIZE ||
                aiPlayer.board.getKilledShips() == SHIP_SIZE;
    }

    private void updateView() {
        gameListener.updateDate(humanPlayer.board, aiPlayer.board);
    }


    private Runnable updateTimerThread = new Runnable() {
        public void run() {
            long timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            long timeSwapBuff = 0L;
            updatedTime = timeSwapBuff + timeInMilliseconds;
            customHandler.postDelayed(this, 0);
        }
    };

    private void writeWinner(final Result result) {
        insertResult.insertResult(result);
    }
}

