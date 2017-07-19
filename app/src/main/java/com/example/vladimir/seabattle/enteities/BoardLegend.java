package com.example.vladimir.seabattle.enteities;

public enum BoardLegend {
    A(1), B(2), C(3), D(4), E(5), F(6), G(7), H(8), I(9), J(10);

    private final int index;

    BoardLegend(int index) {
        this.index = index;
    }

    public int count() {
        return index;
    }

}
