package com.example.vladimir.seabattle.logic.models;

import android.support.annotation.IntRange;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {

    public static final int MAX_XY = 9;

    public static final int MIN_XY = 0;

    public static final int BOARD_SIZE = 10;

    private List<Ship> ships;

    private final Cell[][] myBoard;

    private enum Orientation {
        HORIZONTAL,
        VERTICAL
    }

    private final Random random = new Random();

    public Board() {
        myBoard = new Cell[BOARD_SIZE][BOARD_SIZE];
        createEmptyBoard();
    }

    private void createEmptyBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                Cell cell = new Cell(i, j);
                myBoard[i][j] = cell;
            }
        }
    }

    public void createShips() {
        ships = new ArrayList<>();
        int MAX_SHIP_SIZE = 4;
        int MIN_SHIP_SIZE = 1;
        for (int shipCount = MAX_SHIP_SIZE; shipCount >= MIN_SHIP_SIZE; shipCount--) {
            int shipsCount = MAX_SHIP_SIZE + 1 - shipCount;
            for (int shipIndex = 0; shipIndex < shipsCount; shipIndex++) {
                ships.add(createShip(shipCount));
            }
        }
    }

    private Ship createShip(int shipCount) {
        List<Cell> shipCells = new ArrayList<>();
        List<Cell> shipBorder = new ArrayList<>();
        Ship ship = new Ship();
        while (shipCells.size() < shipCount) {
            shipCells.clear();
            int randX = random.nextInt(BOARD_SIZE);
            int randY = random.nextInt(BOARD_SIZE);
            int lineY = randY;
            int lineX = randX;
            int count = 0;
            Orientation orientation = getOrientation();
            if (validation(lineX, lineY, shipCount, orientation)) {
                while (count < shipCount) {
                    myBoard[lineX][lineY].setShip(ship);
                    myBoard[lineX][lineY].setState(Cell.CellState.ALIVE);
                    shipCells.add(myBoard[lineX][lineY]);
                    shipBorder = addAroundShipCells(shipBorder, lineX, lineY);
                    count++;
                    if (orientation == Orientation.HORIZONTAL) {
                        lineY = getNextCord(randY, count);
                    } else {
                        lineX = getNextCord(randX, count);
                    }
                }
            }
        }
        ship.setShipBoarding(shipBorder);
        ship.setShipCells(shipCells);
        return ship;
    }


    private boolean validation(@IntRange(from = 0, to = 9) final int x,
                               @IntRange(from = 0, to = 9) final int y,
                               final int shipCount, final Orientation orientation) {
        int sum = 0;
        int lineY = y;
        int lineX = x;
        int count = 0;
        while (count < shipCount) {
            for (int i = getNextCell(lineX); i < lineX + 2; i++) {
                for (int j = getNextCell(lineY); j < lineY + 2; j++) {
                    if (i > MAX_XY || j > MAX_XY || i < MIN_XY || j < MIN_XY) {
                        sum += 1;
                    } else {
                        if (myBoard[i][j].isShip()) {
                            sum += 1;
                        }
                    }
                }
            }
            count++;
            if (orientation == Orientation.HORIZONTAL) {
                lineY = getNextCord(y, count);
            } else {
                lineX = getNextCord(x, count);
            }
        }
        return sum == 0;
    }

    private int getNextCell(int xy) {
        int counter;
        if (xy != MIN_XY) {
            counter = xy - 1;
        } else {
            counter = xy;
        }
        return counter;
    }

    private int getNextCord(int rand, int count) {
        int line;
        if (rand > MAX_XY) {
            line = rand - count;
        } else {
            line = rand + count;
        }
        return line;
    }

    private Orientation getOrientation() {
        if (random.nextBoolean()) {
            return Orientation.HORIZONTAL;
        } else {
            return Orientation.VERTICAL;
        }
    }

    public Cell getCell(@IntRange(from = 0, to = 9) int x, @IntRange(from = 0, to = 9) int y) {
        return myBoard[x][y];
    }

    public int getKilledShips() {
        int shipCount = 0;
        for (Ship ship : ships) {
            if (ship.getShipCells().get(0).getState() == Cell.CellState.KILLED) {
                shipCount++;
            }
        }
        return shipCount;
    }

    private List<Cell> addAroundShipCells(List<Cell> shipBorder, int lineX, int lineY) {
        for (int k = getNextCell(lineX); k <= lineX + 1; k++) {
            for (int l = getNextCell(lineY); l <= lineY + 1; l++) {
                if (k >= MIN_XY && k <= MAX_XY && l >= MIN_XY && l <= MAX_XY) {
                    if (myBoard[k][l].getState() != Cell.CellState.ALIVE)
                        if (!shipBorder.contains(myBoard[k][l]))
                            shipBorder.add(myBoard[k][l]);
                }
            }
        }
        return shipBorder;
    }

}