package com.weavey.litepal;

import android.os.Parcel;
import android.os.Parcelable;

import org.litepal.crud.DataSupport;

/**
 * create by Weavey
 * on date 2016-03-24
 */
public class Notes extends DataSupport implements Parcelable {

    private int id;

    private String notesType;

    private String title;

    private String position;

    private String weather;

    private String time;

    private String content;

    private int backColor;

    private int textColor;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNotesType() {
        return notesType;
    }

    public void setNotesType(String notesType) {
        this.notesType = notesType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getBackColor() {
        return backColor;
    }

    public void setBackColor(int backColor) {
        this.backColor = backColor;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.notesType);
        dest.writeString(this.title);
        dest.writeString(this.position);
        dest.writeString(this.weather);
        dest.writeString(this.time);
        dest.writeString(this.content);
        dest.writeInt(this.backColor);
        dest.writeInt(this.textColor);
    }

    public Notes() {
    }

    protected Notes(Parcel in) {
        this.id = in.readInt();
        this.notesType = in.readString();
        this.title = in.readString();
        this.position = in.readString();
        this.weather = in.readString();
        this.time = in.readString();
        this.content = in.readString();
        this.backColor = in.readInt();
        this.textColor = in.readInt();
    }

    public static final Parcelable.Creator<Notes> CREATOR = new Parcelable.Creator<Notes>() {
        @Override
        public Notes createFromParcel(Parcel source) {
            return new Notes(source);
        }

        @Override
        public Notes[] newArray(int size) {
            return new Notes[size];
        }
    };
}
