package com.example.vladimir.seabattle.logic.views;

import android.content.Context;
import android.support.annotation.IntRange;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.example.vladimir.seabattle.enteritis.BoardLegend;

import static com.example.vladimir.seabattle.logic.models.Board.BOARD_SIZE;
import static com.example.vladimir.seabattle.logic.models.Board.minXY;


public class ComputerDrawView extends DrawView {

    private static final int START_POSITION = 100;

    private static final int CELL_STEP = 70;

    private GestureDetector detector;

    private OnCellClick OnCellClick;

    public interface OnCellClick {
        void onCellClick(@IntRange(from = 0, to = 9) int x, @IntRange(from = 0, to = 9) int y);
    }

    public void setOnCellClick(OnCellClick OnCellClick) {
        this.OnCellClick = OnCellClick;
    }

    public ComputerDrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        detector = new GestureDetector(getContext(), new MyGestureListener());
    }

    public ComputerDrawView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        detector.onTouchEvent(event);
        int X = (int) event.getX() - START_POSITION;
        int Y = (int) event.getY() - START_POSITION;
        if ((X < 0) || (X > CELL_STEP * BoardLegend.values().length) ||
                (Y < 0) || (Y > CELL_STEP * BoardLegend.values().length)) {
            return false;
        } else {
            int cellX = (int) Math.floor(X / CELL_STEP);
            int cellY = (int) Math.floor(Y / CELL_STEP);
            if (OnCellClick != null) {
                if (isRange(cellX, cellY)) {
                    OnCellClick.onCellClick(cellX, cellY);
                }
                return true;
            } else {
                return false;
            }
        }
    }

    private boolean isRange(int x, int y) {
        return (x >= minXY && x < BOARD_SIZE) && (y >= minXY && y < BOARD_SIZE);
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent event) {
            return true;
        }
    }
}
