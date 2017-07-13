package com.example.vladimir.seabattle.logic.models;

import java.util.List;

class Ship {

    private List<Cell> shipCells;

    private List<Cell> shipBoarding;

    List<Cell> getShipCells() {
        return shipCells;
    }

    void setShipCells(List<Cell> shipCells) {
        this.shipCells = shipCells;
    }

    void setShipBoarding(List<Cell> shipBoarding) {
        this.shipBoarding = shipBoarding;
    }

    boolean leftAliveNeighbors() {
        for (Cell cell : shipCells) {
            if (!cell.equals(this) && cell.isAlive()) return true;
        }
        return false;
    }

    void die() {

        for (Cell cell : shipBoarding) {
            cell.setState(Cell.cellState.MISSED);
        }
        for (Cell cell : shipCells) {
            cell.setState(Cell.cellState.KILLED);
        }
    }
}
