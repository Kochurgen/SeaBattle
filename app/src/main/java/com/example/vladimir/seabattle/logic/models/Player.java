package com.example.vladimir.seabattle.logic.models;

import android.support.annotation.IntRange;

import com.example.vladimir.seabattle.Interfaces.ShootCallback;

import java.util.List;

public abstract class Player {

    List<Ship> ships;

    public Board board;

    public boolean myTurn;

    public enum ShootResult {
        MISSED,
        INJURED,
        KILLED
    }

    Player(ShootCallback callback) {
        board = new Board();
        ships = board.createShips();
    }

    public ShootResult shoot(@IntRange(from = 0, to = 9) int x, @IntRange(from = 0, to = 9) int y) {
        Cell cell = board.getCell(x, y);
        if (cell.getState() == Cell.cellState.EMPTY || cell.getState() == Cell.cellState.ALIVE) {
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
