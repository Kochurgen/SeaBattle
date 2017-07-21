package com.example.vladimir.seabattle.database_wrapper;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBController extends SQLiteOpenHelper {

    public final static String STEP_COUNT = "step_count";

    public final static String USER_NAME = "user_name";

    public final static String GAME_DURATION = "game_time";

    public final static String CONTENT_TYPE = "content_type";

    final static String TABLE_GAME_RESULTS = "game_results";

    private final static String DB_NAME = "result.sqLiteDatabase";

    private final static int DB_VERSION = 1;

    DBController(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_GAME_RESULTS +
                "( _id INTEGER PRIMARY KEY , " +
                STEP_COUNT + " INTEGER, " +
                GAME_DURATION + " INTEGER, " +
                USER_NAME + " TEXT, " +
                CONTENT_TYPE + " TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GAME_RESULTS + ";");
        onCreate(db);
    }
}