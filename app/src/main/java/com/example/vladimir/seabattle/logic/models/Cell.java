package com.example.vladimir.seabattle.logic.models;

import java.util.List;

public class Cell {

    private final int x;

    private final int y;

    private Ship ship;

    private CellState state;

    public enum CellState {
        EMPTY,
        ALIVE,
        INJURED,
        KILLED,
        MISSED
    }

    public CellState getState() {
        return state;
    }

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.state = CellState.EMPTY;
    }

    void setShip(Ship ship) {
        this.ship = ship;
    }

    void setState(CellState state) {
        this.state = state;
    }

    boolean isAlive() {
        return state == CellState.ALIVE;
    }

    boolean isShip() {
        return this.ship != null;
    }

    public void shot() {
        switch (state) {
            case EMPTY:
                setState(CellState.MISSED);
                break;
            case ALIVE:
                if (ship.leftAliveNeighbors()) {
                    setState(CellState.INJURED);
                } else {
                    ship.die();
                }
                markKilledShips();
                break;
        }
    }

    private void markKilledShips() {
        int count = 0;
        List<Cell> cells = ship.getShipCells();
        for (int i = 0; i < ship.getShipCells().size(); i++) {
            if (cells.get(i).getState() == CellState.ALIVE) {
                break;
            } else {
                count++;
            }
        }
        if (count == ship.getShipCells().size()) {
            ship.die();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cell cell = (Cell) o;

        if (x != cell.x) return false;
        if (y != cell.y) return false;
        if (ship != null ? !ship.equals(cell.ship) : cell.ship != null) return false;
        return state == cell.state;

    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + (ship != null ? ship.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        return result;
    }

}