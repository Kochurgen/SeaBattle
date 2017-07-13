package com.example.vladimir.seabattle.logic.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.vladimir.seabattle.enteritis.BoardLegend;
import com.example.vladimir.seabattle.R;
import com.example.vladimir.seabattle.logic.models.Board;

public class DrawView extends View {

    private boolean userVisible;

    private final static int TEXT_SIZE = 50;

    private final static int TEXT_STEP = 71;

    private final static int STROKE_WIDTH = 5;

    private final static int ALPHA_COLOR = 80;

    private final static int RED = 102;

    private final static int GREEN = 204;

    private final static int BLUE = 255;

    private final static int STEP_CELLS = 70;

    private final static int startTopUserInf = 80;

    private Board board;

    private Paint paint;

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
        canvas.drawARGB(ALPHA_COLOR, RED, GREEN, BLUE);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(STROKE_WIDTH);
        paint.setStyle(Paint.Style.STROKE);
        paint.setTextSize(TEXT_SIZE);
        updateUserBoard(canvas);
    }

    public void updateUserBoard(Canvas canvas) {
        Rect commandRect = new Rect(0, STEP_CELLS, STEP_CELLS, 0);
        int startUserBoard = 100;
        for (int i = Board.minXY; i <= Board.MAX_XY; i++) {
            for (int j = Board.minXY; j <= Board.MAX_XY; j++) {
                switch (board.getCell(i, j).getState()) {
                    case EMPTY:
                        paint.setColor(Color.BLUE);
                        paint.setStyle(Paint.Style.STROKE);
                        break;
                    case ALIVE:
                        paint.setColor(userVisible ? Color.DKGRAY : Color.BLUE);
                        paint.setStyle(userVisible ? Paint.Style.FILL : Paint.Style.STROKE);
                        break;
                    case MISSED:
                        paint.setColor(Color.GRAY);
                        paint.setStyle(Paint.Style.FILL_AND_STROKE);
                        break;
                    case INJURED:
                        paint.setColor(Color.CYAN);
                        paint.setStyle(Paint.Style.FILL_AND_STROKE);
                        break;
                    case KILLED:
                        paint.setColor(Color.RED);
                        paint.setStyle(Paint.Style.FILL_AND_STROKE);
                        break;
                }

                Rect rect = new Rect(commandRect);
                rect.offset(i * STEP_CELLS + startUserBoard, j * STEP_CELLS + startUserBoard);
                canvas.drawRect(rect, paint);
            }
        }

        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        int start = 115;
        int top = 150;
        for (BoardLegend name : BoardLegend.values()) {
            canvas.drawText(name.toString(), start, startTopUserInf, paint);
            canvas.drawText(String.valueOf(name.count()), startUserBoard - STEP_CELLS, top, paint);
            start += TEXT_STEP;
            top += TEXT_STEP;
        }
    }

}
