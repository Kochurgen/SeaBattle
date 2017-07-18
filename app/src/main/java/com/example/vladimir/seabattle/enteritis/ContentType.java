package com.example.vladimir.seabattle.enteritis;

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
