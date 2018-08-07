package com.example.pranshumishra.findbooks;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Pranshu Mishra on 8/7/2018.
 */

public class BooksAdapter extends ArrayAdapter<Book> {

    public BooksAdapter(@NonNull Context context) {
        super(context, 0, QueryUtils.books);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listView = convertView;
        if (listView == null) {
            listView = LayoutInflater.from(getContext()).inflate(R.layout.single_view, parent, false);
        }

        Book book = getItem(position);
        TextView bookName = listView.findViewById(R.id.bookname);
        bookName.setText(book.getBookName());
        TextView writerName = listView.findViewById(R.id.writername);
        String string = "";
        String ss[] = book.getBookWriiters();
        if (ss != null) {
            for (String s : ss) {
                string = string + s + '\n';
            }

        }
        writerName.setText(string);
        RatingBar ratingBar = listView.findViewById(R.id.rating);
        ratingBar.setNumStars(book.getRatings());
        ImageView imageView = listView.findViewById(R.id.bookImage);
        if(!book.getBookImage().isEmpty()){
            Picasso.get().load(book.getBookImage())
                    .into(imageView);
        }

        return listView;
    }
}
