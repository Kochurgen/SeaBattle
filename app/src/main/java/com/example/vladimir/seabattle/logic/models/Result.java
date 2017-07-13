package com.example.vladimir.seabattle.logic.models;


import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.vladimir.seabattle.enteritis.ContentType;

import static com.example.vladimir.seabattle.database_wrapper.DBController.CONTENT_TYPE;
import static com.example.vladimir.seabattle.database_wrapper.DBController.GAME_DURATION;
import static com.example.vladimir.seabattle.database_wrapper.DBController.STEP_COUNT;
import static com.example.vladimir.seabattle.database_wrapper.DBController.USER_NAME;

public class Result implements Parcelable {

    private String userName;

    private int stepsCount;

    private long gameDuration;

    private ContentType contentType;

    public Result(String userName, int stepsCount, long gameDuration, ContentType contentType) {
        this.userName = userName;
        this.stepsCount = stepsCount;
        this.gameDuration = gameDuration;
        this.contentType = contentType;
    }

    public Result(Cursor cursor){
        this.userName = cursor.getString(cursor.getColumnIndex(USER_NAME));
        this.stepsCount = cursor.getInt(cursor.getColumnIndex(STEP_COUNT));
        this.gameDuration = cursor.getInt(cursor.getColumnIndex(GAME_DURATION));
        this.contentType = ContentType.valueOf(cursor.getString(cursor.getColumnIndex(CONTENT_TYPE)));
    }

    public long getTimestamp() {
        return gameDuration;
    }

    public int getStepsCount() {
        return stepsCount;
    }

    public String getUserName() {
        return userName;
    }

    public ContentType getContactType(){
        return contentType;
    }


    public ContentValues getContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_NAME, userName);
        contentValues.put(GAME_DURATION, gameDuration);
        contentValues.put(STEP_COUNT, stepsCount);
        contentValues.put(CONTENT_TYPE, contentType.toString());
        return contentValues;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userName);
        dest.writeInt(this.stepsCount);
        dest.writeLong(this.gameDuration);
        dest.writeInt(this.contentType == null ? -1 : this.contentType.ordinal());
    }

    private Result(Parcel in) {
        this.userName = in.readString();
        this.stepsCount = in.readInt();
        this.gameDuration = in.readLong();
        int tmpContentType = in.readInt();
        this.contentType = tmpContentType == -1 ? null : ContentType.values()[tmpContentType];
    }

    public static final Creator<Result> CREATOR = new Creator<Result>() {
        @Override
        public Result createFromParcel(Parcel source) {
            return new Result(source);
        }

        @Override
        public Result[] newArray(int size) {
            return new Result[size];
        }
    };
}
