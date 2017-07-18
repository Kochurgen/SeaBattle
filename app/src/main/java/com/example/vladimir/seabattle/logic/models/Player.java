package com.example.vladimir.seabattle.logic.models;

import android.support.annotation.IntRange;

import com.example.vladimir.seabattle.controllers.ShootCallback;

import java.util.List;

public abstract class Player {

    public final Board board;

    public enum ShootResult {
        MISSED,
        INJURED,
        KILLED
    }

    Player() {
        board = new Board();
        board.createShips();
    }

    public ShootResult shoot(@IntRange(from = 0, to = 9) int x, @IntRange(from = 0, to = 9) int y) {
        Cell cell = board.getCell(x, y);
        if (cell.getState() == Cell.CellState.EMPTY || cell.getState() == Cell.CellState.ALIVE) {
            cell.shot();
            switch (cell.getState()) {
                case MISSED:
                    return ShootResult.MISSED;
                case INJURED:
                    return ShootResult.INJURED;
                case KILLED:
                    return ShootResult.KILLED;
                default:
                    return null;
            }
        }
        return null;
    }

}
