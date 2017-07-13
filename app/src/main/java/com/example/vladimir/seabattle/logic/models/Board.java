package com.example.vladimir.seabattle.logic.models;

import android.support.annotation.IntRange;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {

    public static final int MAX_XY = 9, minXY = 0;

    public static final int BOARD_SIZE = 10;

    private ArrayList<Ship> ships;

    private Cell[][] myBoard;

    private enum Orientation {
        HORIZONTAL,
        VERTICAL
    }

    private Random random = new Random();

    Board() {
        myBoard = new Cell[BOARD_SIZE][BOARD_SIZE];
        CreateEmptyBoard();
    }

    private void CreateEmptyBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                Cell cell = new Cell(i, j, Cell.cellState.EMPTY, null);
                myBoard[i][j] = cell;
            }
        }
    }

    public Cell[][] getBoardCells() {
        return myBoard;
    }

    ArrayList<Ship> createShips() {
        ships = new ArrayList<>();
        int MAX_SHIP_SIZE = 4;
        int MIN_SHIP_SIZE = 1;
        for (int shipCount = MAX_SHIP_SIZE; shipCount >= MIN_SHIP_SIZE; shipCount--) {
            int shipsCount = MAX_SHIP_SIZE + 1 - shipCount;
            for (int shipIndex = 0; shipIndex < shipsCount; shipIndex++) {
                boolean createShip = true;
                ships.add(createShip(shipCount));
            }
        }
        return ships;
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
            if (Validation(lineX, lineY, shipCount, orientation)) {
                while (count < shipCount) {
                    myBoard[lineX][lineY].setShip(ship);
                    myBoard[lineX][lineY].setState(Cell.cellState.ALIVE);
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


    private boolean Validation(@IntRange(from = 0, to = 9) int x, @IntRange(from = 0, to = 9) int y,
                               int shipCount, Orientation orientation) {
        int sum = 0;
        int lineY = y;
        int lineX = x;
        int count = 0;
        while (count < shipCount) {
            for (int i = getNextCell(lineX); i < lineX + 2; i++) {
                for (int j = getNextCell(lineY); j < lineY + 2; j++) {
                    if (i > MAX_XY || j > MAX_XY || i < minXY || j < minXY) {
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

    private int getNextCell(int XY) {
        int counter;
        if (XY != minXY) {
            counter = XY - 1;
        } else {
            counter = XY;
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

    public void shoot(@IntRange(from = 0, to = 9) int x, @IntRange(from = 0, to = 9) int y) {
        myBoard[x][y].shot();
        if (myBoard[x][y].getState() == Cell.cellState.INJURED) {
            myBoard[x][y].markKilledShips();
        }
    }

    public Cell[][] getMyBoard() {
        return myBoard;
    }

    public int getKilledShips() {
        int shipCount = 0;
        for (Ship ship : ships) {
            if (ship.getShipCells().get(0).getState() == Cell.cellState.KILLED) {
                shipCount++;
            }
        }
        return shipCount;
    }

    private List<Cell> addAroundShipCells(List<Cell> shipBorder, int lineX, int lineY) {
        for (int k = getNextCell(lineX); k <= lineX + 1; k++) {
            for (int l = getNextCell(lineY); l <= lineY + 1; l++) {
                if (k >= minXY && k <= MAX_XY && l >= minXY && l <= MAX_XY) {
                    if (myBoard[k][l].getState() != Cell.cellState.ALIVE)
                        if (!shipBorder.contains(myBoard[k][l]))
                            shipBorder.add(myBoard[k][l]);
                }
            }
        }
        return shipBorder;
    }

}