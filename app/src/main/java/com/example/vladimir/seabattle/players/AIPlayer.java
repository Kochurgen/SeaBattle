package com.example.vladimir.seabattle.players;

import com.example.vladimir.seabattle.logic.models.User;

public class AIPlayer extends Player {

    private static final String COMPUTER_NAME = "Computer";

    public AIPlayer() {
        super(new User(COMPUTER_NAME, COMPUTER_NAME));
    }

}