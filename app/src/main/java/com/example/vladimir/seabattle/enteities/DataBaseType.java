package com.example.vladimir.seabattle.enteities;


public enum DataBaseType {
    LOCAL_BASE("LOCAL_BASE"), FIRE_BASE("FIRE_BASE");

    private final String name;

    DataBaseType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
