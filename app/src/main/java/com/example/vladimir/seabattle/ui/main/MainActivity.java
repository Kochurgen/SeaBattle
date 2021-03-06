package com.example.vladimir.seabattle.ui.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.IntRange;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.vladimir.seabattle.R;
import com.example.vladimir.seabattle.controllers.Game;
import com.example.vladimir.seabattle.enteities.ContentType;
import com.example.vladimir.seabattle.logic.models.Board;
import com.example.vladimir.seabattle.logic.models.User;
import com.example.vladimir.seabattle.logic.views.ComputerDrawView;
import com.example.vladimir.seabattle.logic.views.UserDrawView;
import com.example.vladimir.seabattle.ui.AddUserNameDialog;
import com.example.vladimir.seabattle.ui.result.ResultActivity;

public class MainActivity extends AppCompatActivity implements Game.GameListener,
        ComputerDrawView.OnCellClick {

    private ComputerDrawView computerDrawView;

    private UserDrawView userView;

    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AddUserNameDialog addUserNameDialog = new AddUserNameDialog(this, new AddUserNameDialog.OnAddUserNameListener() {
            @Override
            public void onAddedUserName(final String firstName, final String lastName) {
                game = new Game(getApplicationContext(), new User(firstName, lastName), MainActivity.this);
            }
        });
        userView = (UserDrawView) findViewById(R.id.drawUserView);
        computerDrawView = (ComputerDrawView) findViewById(R.id.drawComputerView);
        computerDrawView.setOnCellClick(this);
        addUserNameDialog.show();
    }

    @Override
    public void showEndGameDialog(final ContentType player) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton(R.string.positive_button, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                game.createNewGame();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(R.string.negative_button, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.setTitle(getString(R.string.template_winner, player));
        alertDialog.setMessage(getString(R.string.start_new_game_question));
        alertDialog.show();
    }

    @Override
    public void updateDate(final Board userBoard, final Board computerBoard) {
        userView.setBoard(userBoard);
        computerDrawView.setBoard(computerBoard);
    }

    @Override
    public void onCellClick(@IntRange(from = 0, to = 9) int x, @IntRange(from = 0, to = 9) int y) {
        game.shoot(x, y);
    }

}