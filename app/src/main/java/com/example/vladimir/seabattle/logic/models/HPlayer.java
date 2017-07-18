package com.example.vladimir.seabattle.logic.models;


import com.example.vladimir.seabattle.controllers.ShootCallback;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class HPlayer extends Player {

    private final Random random = new Random();

    private final static int MAX_SHIP_LENGTH = 4;

    private final static int MIN_SHIP_LENGTH = 1;

    private final static int BOARD_LENGTH = 10;

    private int lastX;

    private int lastY;

    private Cell[][] emptyCells;

    private final List<Integer> leftAimsCells = new ArrayList<>();

    private final List<Integer> rightAimsCells = new ArrayList<>();

    private final List<Integer> topAimsCells = new ArrayList<>();

    private final List<Integer> downAimsCells = new ArrayList<>();

    private List<Integer> currentAims = new ArrayList<>();

    private Collection<Cell> shootCells;

    private boolean shootToPotentialAims;

    private boolean aimsCalculated = false;

    {
    }

    public HPlayer(ShootCallback callback) {
        super();
        emptyCells = new Cell[Board.MAX_XY][Board.MAX_XY];
        shootCells = new ArrayList<>();
        createEmptyCells();
    }

    public Player.ShootResult shoot() {
        Player.ShootResult shootResult;
        int[] coordinates = getCoordinates();
        int x = coordinates[0];
        int y = coordinates[1];
        shootResult = shoot(x, y);
        if (shootResult != null) {
            shootHandling(shootResult);
            shootCells.add(board.getCell(x, y));
        }

        return shootResult;
    }

    private int[] getCoordinates() {
        int[] coordinates;
        if (shootToPotentialAims) {
            coordinates = getNextCoordinates();
        } else {
            coordinates = getRandomCoordinate();
        }
        return coordinates;
    }

    private boolean cellShootValidate(int x, int y) {
        if (shootCells.size() != 0) {
            Cell cell = board.getCell(x, y);
            return shootCells.contains(cell);
        } else {
            return false;
        }
    }

    private int[] getRandomCoordinate() {
        int x;
        int y;
        int[] rand = new int[2];
        while (true) {
            x = random.nextInt(BOARD_LENGTH);
            y = random.nextInt(BOARD_LENGTH);
            if (!cellShootValidate(x, y)) {
                break;
            }
        }
        rand[0] = x;
        rand[1] = y;
        lastX = x;
        lastY = y;
        return rand;
    }

    private void createEmptyCells() {
        emptyCells = new Cell[BOARD_LENGTH][BOARD_LENGTH];
        for (int i = Board.MIN_XY; i <= Board.MAX_XY; i++) {
            for (int j = Board.MIN_XY; j <= Board.MAX_XY; j++) {
                emptyCells[i][j] = new Cell(j, i);
            }
        }
    }

    private void calculateAims() {
        clearAims();
        int x = lastX, y = lastY;
        aimsCalculated = true;
        for (int i = x + MIN_SHIP_LENGTH; i < x + MAX_SHIP_LENGTH; ++i) {
            if (i < BOARD_LENGTH) {
                if (emptyCells[i][y].getState() == Cell.CellState.MISSED) break;
                if (emptyCells[i][y].getState() == Cell.CellState.EMPTY) rightAimsCells.add(i);
            } else break;
        }
        for (int i = x - MIN_SHIP_LENGTH; i > x - MAX_SHIP_LENGTH; --i) {
            if (i >= Board.MIN_XY) {
                if (emptyCells[i][y].getState() == Cell.CellState.MISSED) break;
                if (emptyCells[i][y].getState() == Cell.CellState.EMPTY) leftAimsCells.add(i);
            } else break;
        }
        for (int i = y - MIN_SHIP_LENGTH; i > y - MAX_SHIP_LENGTH; --i) {
            if (i >= Board.MIN_XY) {
                if (emptyCells[x][i].getState() == Cell.CellState.MISSED) break;
                if (emptyCells[x][i].getState() == Cell.CellState.EMPTY) topAimsCells.add(i);
            } else break;
        }
        for (int i = y + MIN_SHIP_LENGTH; i < y + MAX_SHIP_LENGTH; ++i) {
            if (i < BOARD_LENGTH) {
                if (emptyCells[x][i].getState() == Cell.CellState.MISSED) break;
                if (emptyCells[x][i].getState() == Cell.CellState.EMPTY) downAimsCells.add(i);
            } else break;
        }
    }

    private int[] getNextCoordinates() {
        if (!aimsCalculated) {
            calculateAims();
        }
        int[] res = new int[2];
        if (!leftAimsCells.isEmpty()) {
            currentAims = leftAimsCells;
            res[0] = leftAimsCells.get(0);
            res[1] = lastY;
            leftAimsCells.remove(0);
        } else if (!rightAimsCells.isEmpty()) {
            currentAims = rightAimsCells;
            res[0] = rightAimsCells.get(0);
            res[1] = lastY;
            rightAimsCells.remove(0);
        } else if (!downAimsCells.isEmpty()) {
            currentAims = downAimsCells;
            res[0] = lastX;
            res[1] = downAimsCells.get(0);
            downAimsCells.remove(0);
        } else if (!topAimsCells.isEmpty()) {
            currentAims = topAimsCells;
            res[0] = lastX;
            res[1] = topAimsCells.get(0);
            topAimsCells.remove(0);
        }
        return res;
    }

    private void shootHandling(Player.ShootResult shootResult) {
        switch (shootResult) {
            case MISSED:
                if (shootToPotentialAims) {
                    currentAims.clear();
                }
                break;
            case INJURED:
                shootToPotentialAims = true;
                break;
            case KILLED:
                shootToPotentialAims = false;
                aimsCalculated = false;
                break;
        }

    }

    private void clearAims() {
        leftAimsCells.clear();
        rightAimsCells.clear();
        downAimsCells.clear();
        topAimsCells.clear();
    }

}