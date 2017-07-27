package com.example.vladimir.seabattle.logic.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import com.example.vladimir.seabattle.enteities.BoardLegend;
import com.example.vladimir.seabattle.R;
import com.example.vladimir.seabattle.logic.models.Board;
import com.example.vladimir.seabattle.logic.models.Cell;

import java.util.HashMap;
import java.util.Map;

public class DrawView extends View {

    private Map<Cell.CellState, Paint> paints;

    private final static int STROKE_WIDTH = 5;

    private static int START_POSITION;

    private static int TOP_POSITION;

    private static int STEP_CELLS;

    private static int START_TOP_USER_INF;

    private static int START_USER_BOARD;

    private int textSize;

    private int textStep;

    private Board board;

    private Paint paint;

    private Paint emptyPaint;

    private Paint injuredPaint;

    private Paint missedPaint;

    private Paint killedPaint;

    private Paint textPaint;

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
        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        textSize = (width / R.dimen.half) / R.dimen.text_size_separator;
        textStep = (width / R.dimen.half) / R.dimen.count_cells;
        paint = new Paint();
        TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(
                attributeSet,
                R.styleable.DrawView,
                0, 0);
        paints = new HashMap<>();
        paints.put(Cell.CellState.ALIVE, initAliveUserPaint(typedArray.getBoolean(R.styleable.DrawView_userVisible, false)));
        paints.put(Cell.CellState.EMPTY, initEmptyPaint());
        paints.put(Cell.CellState.INJURED, initInjuredUserPaint());
        paints.put(Cell.CellState.MISSED, initMissedUserPaint());
        paints.put(Cell.CellState.KILLED, initKilledUserPaint());
        initTextUserPaint();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        STEP_CELLS = getWidth() / R.dimen.count_cells;
        START_USER_BOARD = getWidth() / Board.MAX_XY;
        START_TOP_USER_INF = getHeight() / R.dimen.text_info_step;
        START_POSITION = START_USER_BOARD + R.dimen.text_info_step;
        TOP_POSITION = START_TOP_USER_INF + STEP_CELLS;
        canvas.drawColor(ContextCompat.getColor(getContext(), R.color.colorBoardBackgraund));
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
                paint = paints.get(board.getCell(i, j).getState());
                canvas.drawRect(rect, paint);
            }
        }
        int start = START_POSITION;
        int top = TOP_POSITION;
        for (BoardLegend name : BoardLegend.values()) {
            canvas.drawText(name.toString(), start, START_TOP_USER_INF, textPaint);
            canvas.drawText(String.valueOf(name.count()), startUserBoard - STEP_CELLS, top, textPaint);
            start += textStep;
            top += textStep;
        }
    }

    private Paint initTextUserPaint() {
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.BLUE);
        textPaint.setStrokeWidth(STROKE_WIDTH);
        textPaint.setStyle(Paint.Style.STROKE);
        textPaint.setTextSize(textSize);
        return textPaint;
    }

    private Paint initKilledUserPaint() {
        killedPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        killedPaint.setColor(Color.RED);
        killedPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        killedPaint.setStrokeWidth(STROKE_WIDTH);
        killedPaint.setTextSize(textSize);
        return killedPaint;
    }

    private Paint initMissedUserPaint() {
        missedPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        missedPaint.setColor(Color.GRAY);
        missedPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        missedPaint.setStrokeWidth(STROKE_WIDTH);
        missedPaint.setTextSize(textSize);
        return missedPaint;
    }

    private Paint initInjuredUserPaint() {
        injuredPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        injuredPaint.setColor(Color.CYAN);
        injuredPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        injuredPaint.setStrokeWidth(STROKE_WIDTH);
        injuredPaint.setTextSize(textSize);
        return injuredPaint;
    }

    private Paint initAliveUserPaint(boolean userVisible) {
        Paint aliveUserPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        if (userVisible) {
            aliveUserPaint.setColor(Color.DKGRAY);
            aliveUserPaint.setStyle(Paint.Style.FILL);
        } else {
            aliveUserPaint.setColor(Color.BLUE);
            aliveUserPaint.setStyle(Paint.Style.STROKE);
        }
        aliveUserPaint.setStrokeWidth(STROKE_WIDTH);
        aliveUserPaint.setTextSize(textSize);
        return aliveUserPaint;
    }

    private Paint initEmptyPaint() {
        emptyPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        emptyPaint.setColor(Color.BLUE);
        emptyPaint.setStyle(Paint.Style.STROKE);
        emptyPaint.setStrokeWidth(STROKE_WIDTH);
        emptyPaint.setTextSize(textSize);
        return emptyPaint;
    }

}
