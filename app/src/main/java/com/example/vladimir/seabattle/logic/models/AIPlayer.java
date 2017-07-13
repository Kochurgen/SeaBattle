package com.example.vladimir.seabattle.logic.models;

import com.example.vladimir.seabattle.Interfaces.ShootCallback;

public class AIPlayer extends Player {

    public AIPlayer(ShootCallback callback) {
        super(callback);
        myTurn = true;
    }
}