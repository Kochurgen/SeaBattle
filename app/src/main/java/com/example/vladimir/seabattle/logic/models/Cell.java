package com.example.vladimir.seabattle.logic.models;

import java.util.List;

public class Cell {

    private int x;
    private int y;
    private Ship ship;
    private cellState state;

    public enum cellState {
        EMPTY,
        ALIVE,
        INJURED,
        KILLED,
        MISSED
    }

    public cellState getState() {
        return state;
    }

    Cell(int x, int y, cellState state, Ship ship) {
        this.x = x;
        this.y = y;
        this.ship = ship;
        this.state = state;
    }

    void setShip(Ship ship) {
        this.ship = ship;
    }

    void setState(cellState state) {
        this.state = state;
    }

    boolean isAlive() {
        return state == cellState.ALIVE;
    }

    boolean isShip() {
        return this.ship != null;
    }

    void shot() {
        switch (state) {
            case EMPTY:
                setState(cellState.MISSED);
                break;
            case ALIVE:
                if (ship.leftAliveNeighbors()) {
                    setState(cellState.INJURED);
                } else {
                    ship.die();
                }
                markKilledShips();
                break;
        }
    }

    void markKilledShips() {
        int count = 0;
        List<Cell> cells = ship.getShipCells();
        for (int i = 0; i < ship.getShipCells().size(); i++) {
            if (cells.get(i).getState() == Cell.cellState.ALIVE) {
                break;
            } else {
                count++;
            }
        }
        if (count == ship.getShipCells().size()) {
            ship.die();
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}