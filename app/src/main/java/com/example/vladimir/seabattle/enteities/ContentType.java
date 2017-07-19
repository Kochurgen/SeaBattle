package com.example.vladimir.seabattle.enteities;

public enum ContentType {
    COMPUTER("COMPUTER"), HUMAN("HUMAN");

    private final String name;

    ContentType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
