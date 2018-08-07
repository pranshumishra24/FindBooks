package com.example.pranshumishra.findbooks;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Created by Pranshu Mishra on 7/28/2018.
 */

public class BooksLoader extends AsyncTaskLoader<List<Book>> {

    String bookSearchText;

    public BooksLoader(Context context, String bookSearchText) {
        super(context);
        this.bookSearchText = bookSearchText;
    }

    @Override
    public List<Book> loadInBackground() {
        List<Book> data = QueryUtils.searchBooks(bookSearchText);
        return data;
    }
}
