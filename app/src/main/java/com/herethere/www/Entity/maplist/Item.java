package com.herethere.www.Entity.maplist;


import android.os.Parcel;

/**
 * Created by jnshim on 2017-09-25.
 */

public class Item implements android.os.Parcelable {

    private String image;
    private String title;
    private String content;
    private int checkStats;

    public Item(String image, String title, String content, int checkStats) {
        this.image = image;
        this.title = title;
        this.content = content;
        this.checkStats = checkStats;
    }

    public Item(Parcel in) {
        this.image = in.readString();
        this.title = in.readString();
        this.content = in.readString();
        this.checkStats = in.readInt();
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel source) {
            return new Item(source);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(image);
        dest.writeString(title);
        dest.writeString(content);
        dest.writeInt(checkStats);
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCheckStats() {
        return checkStats;
    }

    public void setCheckStats(int checkStats) {
        this.checkStats = checkStats;
    }
}
