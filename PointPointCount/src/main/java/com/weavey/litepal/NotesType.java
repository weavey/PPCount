package com.weavey.litepal;

import android.os.Parcel;
import android.os.Parcelable;

import org.litepal.crud.DataSupport;

/**
 * create by Weavey
 * on date 2016-03-24
 */
public class NotesType extends DataSupport implements Parcelable {

    private int id;

    private String notesType;

    private int orderType; //排序方式

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

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.notesType);
        dest.writeInt(this.orderType);
    }

    public NotesType() {
    }

    protected NotesType(Parcel in) {
        this.notesType = in.readString();
        this.orderType = in.readInt();
    }

    public static final Parcelable.Creator<NotesType> CREATOR = new
            Parcelable.Creator<NotesType>() {
        @Override
        public NotesType createFromParcel(Parcel source) {
            return new NotesType(source);
        }

        @Override
        public NotesType[] newArray(int size) {
            return new NotesType[size];
        }
    };
}
