package com.example.vladimir.seabattle.players;

import com.example.vladimir.seabattle.controllers.ShootCallback;
import com.example.vladimir.seabattle.logic.models.User;

public class AIPlayer extends Player {

    public static final String COMPUTER_NAME = "Computer";

    public AIPlayer(ShootCallback callback) {
        super(new User(COMPUTER_NAME, COMPUTER_NAME));
    }

    @Override
    public String getPlayerName() {
        return COMPUTER_NAME;
    }
}