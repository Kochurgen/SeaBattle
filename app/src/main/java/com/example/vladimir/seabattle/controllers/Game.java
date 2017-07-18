package com.example.vladimir.seabattle.controllers;

import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.IntRange;

import com.example.vladimir.seabattle.data_layer.saver_winner_to_database.InsertResult;
import com.example.vladimir.seabattle.data_layer.saver_winner_to_database.InsertResultToDatabase;
import com.example.vladimir.seabattle.enteritis.ContentType;
import com.example.vladimir.seabattle.logic.models.AIPlayer;
import com.example.vladimir.seabattle.logic.models.Board;
import com.example.vladimir.seabattle.logic.models.HPlayer;
import com.example.vladimir.seabattle.logic.models.Player;
import com.example.vladimir.seabattle.logic.models.Result;

import java.util.concurrent.TimeUnit;

public class Game implements ShootCallback {

    private final static int SHIP_SIZE = 10;

    private HPlayer humanPlayer;

    private AIPlayer aiPlayer;

    private final GameListener gameListener;

    private boolean isNotFinished;

    private int userSteps;

    private int computerSteps;

    private long startTime = 0L;

    private final Handler customHandler = new Handler();

    private long updatedTime = 0L;

    private final InsertResult insertResult;

    public interface GameListener {
        void showDialog(final ContentType player);

        void updateDate(final Board userBoard, final Board computerBoard);
    }

    public Game(Context context, GameListener gameListener) {
        this.gameListener = gameListener;
        this.insertResult = new InsertResultToDatabase(context);
        createNewGame();
    }

    public void createNewGame() {
        aiPlayer = new AIPlayer(this);
        humanPlayer = new HPlayer(this);
        updateView();
        userSteps = 0;
        computerSteps = 0;
        isNotFinished = true;
        startTime = SystemClock.uptimeMillis();
        customHandler.postDelayed(updateTimerThread, 0);
    }

    public void shoot(@IntRange(from = 0, to = 9) int x, @IntRange(from = 0, to = 9) int y) {
        Player.ShootResult shootResult = aiPlayer.shoot(x, y);
        updateView();
        if (shootResult != null) {
            userSteps++;
        }
        if ((shootResult != Player.ShootResult.MISSED) && (!isShipFinished())) {
            return;
        }

        Result result;
        if (isShipFinished()) {
            if (isComputerShipsFinished()) {
                result = new Result(ContentType.HUMAN.toString(), userSteps, updatedTime,
                        ContentType.HUMAN);
            } else {
                result = new Result(ContentType.COMPUTER.toString(), computerSteps, updatedTime,
                        ContentType.COMPUTER);
            }
            finishProcessing(result);
        } else {
            runComputerStep();
        }
    }

    private void finishProcessing(Result result) {
        if (isNotFinished) {
            isNotFinished = false;
            showFinishDialog();
            writeWinner(result);
        }
    }

    private void showFinishDialog() {
        ContentType winnerType;
        if (isHumanShipsFinished()) {
            winnerType = ContentType.COMPUTER;
        } else {
            winnerType = ContentType.HUMAN;
        }
        if (gameListener != null) {
            gameListener.showDialog(winnerType);
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
        return isHumanShipsFinished() || isComputerShipsFinished();
    }

    private boolean isComputerShipsFinished() {
        return aiPlayer.board.getKilledShips() == SHIP_SIZE;
    }

    private boolean isHumanShipsFinished() {
        return humanPlayer.board.getKilledShips() == SHIP_SIZE;
    }

    private void updateView() {
        gameListener.updateDate(humanPlayer.board, aiPlayer.board);
    }


    private final Runnable updateTimerThread = new Runnable() {
        public void run() {
            long timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            long timeSwapBuff = 0L;
            updatedTime = timeSwapBuff + timeInMilliseconds;
            final int delay = 1;
            customHandler.postDelayed(this, TimeUnit.SECONDS.toMillis(delay));
        }
    };

    private void writeWinner(final Result result) {
        insertResult.insertResult(result);
    }
}

