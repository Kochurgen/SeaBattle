package com.example.vladimir.seabattle.logic.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.vladimir.seabattle.enteities.BoardLegend;
import com.example.vladimir.seabattle.R;
import com.example.vladimir.seabattle.logic.models.Board;

public class DrawView extends View {

    private boolean userVisible;

    private final static int TEXT_SIZE = 50;

    private final static int TEXT_STEP = 71;

    private final static int STROKE_WIDTH = 5;

    private final static int STEP_CELLS = 70;

    private final static int START_TOP_USER_INF = 80;

    private  final static int START_USER_BOARD = 100;

    private  final static int START_POSITION = 115;

    private  final static int TOP_POSITION = 150;

    private Board board;

    private Paint paint;

    private Paint emptyPaint;

    private Paint aliveUserPaint;

    private Paint injuredPaint;

    private Paint missedPaint;

    private Paint killedPaint;

    private Paint textPaint;

    {
        initEmptyPaint();
        initAliveUserPaint();
        initInjuredUserPaint();
        initMissedUserPaint();
        initKilledUserPaint();
        initTextUserPaint();
    }

    public DrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }


    public DrawView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public void setBoard(Board board) {
        this.board = board;
        invalidate();
    }

    private void init(AttributeSet attributeSet) {
        paint = new Paint();
        TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(
                attributeSet,
                R.styleable.DrawView,
                0, 0);
        this.userVisible = typedArray.getBoolean(R.styleable.DrawView_userVisible, false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(ContextCompat.getColor(getContext(), R.color.colorBoardBackgraund));
        paint = textPaint;
        if (board != null) {
            updateUserBoard(canvas);
        }
    }

    private void updateUserBoard(Canvas canvas) {
        int startUserBoard = START_USER_BOARD;
        for (int i = Board.MIN_XY; i <= Board.MAX_XY; i++) {
            for (int j = Board.MIN_XY; j <= Board.MAX_XY; j++) {
                Rect rect = new Rect(new Rect(0, 0, STEP_CELLS, STEP_CELLS));
                rect.offset(i * STEP_CELLS + startUserBoard, j * STEP_CELLS + startUserBoard);
                switch (board.getCell(i, j).getState()) {
                    case EMPTY:
                        canvas.drawRect(rect, emptyPaint);
                        break;
                    case ALIVE:
                        if (userVisible) {
                            canvas.drawRect(rect, aliveUserPaint);
                        } else {
                            canvas.drawRect(rect, emptyPaint);
                        }
                        break;
                    case MISSED:
                        canvas.drawRect(rect, missedPaint);
                        break;
                    case INJURED:
                        canvas.drawRect(rect, injuredPaint);
                        break;
                    case KILLED:
                        canvas.drawRect(rect, killedPaint);
                        break;
                    default:
                        canvas.drawRect(rect, emptyPaint);
                }
            }
        }

        int start = START_POSITION;
        int top = TOP_POSITION;
        for (BoardLegend name : BoardLegend.values()) {
            canvas.drawText(name.toString(), start, START_TOP_USER_INF, paint);
            canvas.drawText(String.valueOf(name.count()), startUserBoard - STEP_CELLS, top, paint);
            start += TEXT_STEP;
            top += TEXT_STEP;
        }
    }

    private void initTextUserPaint() {
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.BLUE);
        textPaint.setStrokeWidth(STROKE_WIDTH);
        textPaint.setStyle(Paint.Style.STROKE);
        textPaint.setTextSize(TEXT_SIZE);
    }

    private void initKilledUserPaint() {
        killedPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        killedPaint.setColor(Color.RED);
        killedPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        killedPaint.setStrokeWidth(STROKE_WIDTH);
        killedPaint.setTextSize(TEXT_SIZE);
    }

    private void initMissedUserPaint() {
        missedPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        missedPaint.setColor(Color.GRAY);
        missedPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        missedPaint.setStrokeWidth(STROKE_WIDTH);
        missedPaint.setTextSize(TEXT_SIZE);
    }

    private void initInjuredUserPaint() {
        injuredPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        injuredPaint.setColor(Color.CYAN);
        injuredPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        injuredPaint.setStrokeWidth(STROKE_WIDTH);
        injuredPaint.setTextSize(TEXT_SIZE);
    }

    private void initAliveUserPaint() {
        aliveUserPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        aliveUserPaint.setColor(Color.DKGRAY);
        aliveUserPaint.setStyle(Paint.Style.FILL);
        aliveUserPaint.setStrokeWidth(STROKE_WIDTH);
        aliveUserPaint.setTextSize(TEXT_SIZE);
    }

    private void initEmptyPaint() {
        emptyPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        emptyPaint.setColor(Color.BLUE);
        emptyPaint.setStyle(Paint.Style.STROKE);
        emptyPaint.setStrokeWidth(STROKE_WIDTH);
        emptyPaint.setTextSize(TEXT_SIZE);
    }

}
