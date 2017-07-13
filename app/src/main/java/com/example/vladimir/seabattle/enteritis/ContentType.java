package com.example.vladimir.seabattle.enteritis;

public enum ContentType {
    COMPUTER("COMPUTER"), HUMAN("HUMAN");

    private String name;

    ContentType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
