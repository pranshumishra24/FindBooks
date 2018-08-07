package com.example.pranshumishra.findbooks;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Pranshu Mishra on 7/28/2018.
 */

public class Book implements Parcelable {
    private String bookImage="";
    private String bookName="";
    private String[] bookWriiters;
    private int ratings=0;
    private int noOfRaters=0;
    private String publishDate="";
    private String description="";
    private String buyLink="";
    private int pageCount=0;
    private String[] categories;

    public Book(String bookImage, String bookName, String[] bookWriiters, int ratings, int noOfRaters, String publishDate, String description, String buyLink, int pageCount, String[] categories) {
        this.bookImage = bookImage;
        this.bookName = bookName;
        this.bookWriiters = bookWriiters;
        this.ratings = ratings;
        this.noOfRaters = noOfRaters;
        this.publishDate = publishDate;
        this.description = description;
        this.buyLink = buyLink;
        this.pageCount = pageCount;
        this.categories = categories;
    }

    protected Book(Parcel in) {
        bookImage = in.readString();
        bookName = in.readString();
        bookWriiters = in.createStringArray();
        ratings = in.readInt();
        noOfRaters = in.readInt();
        publishDate = in.readString();
        description = in.readString();
        buyLink = in.readString();
        pageCount = in.readInt();
        categories = in.createStringArray();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    public String getBookImage() {
        return bookImage;
    }

    public String getBookName() {
        return bookName;
    }

    public String[] getBookWriiters() {
        return bookWriiters;
    }

    public int getRatings() {
        return ratings;
    }

    public int getNoOfRaters() {
        return noOfRaters;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public String getDescription() {
        return description;
    }

    public String getBuyLink() {
        return buyLink;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(bookImage);
        dest.writeString(bookName);
        dest.writeArray(bookWriiters);
        dest.writeInt(noOfRaters);
        dest.writeString(publishDate);
        dest.writeString(description);
        dest.writeString(buyLink);
    }
}
