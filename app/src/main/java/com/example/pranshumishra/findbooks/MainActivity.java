package com.example.pranshumishra.findbooks;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {

    private ProgressBar progressBar;
    private EditText bookSearchName;
    private FloatingActionButton searchButton;
    private String bookSearchText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bookSearchName = findViewById(R.id.editText_bookname);
        searchButton = findViewById(R.id.submitFab);
        bookSearchName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH){
                    bookSearchText = bookSearchName.getText().toString();
                    searchBooks(bookSearchName);
                    return true;
                }
                return false;
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchBooks(bookSearchName);
            }
        });
    }

    private void searchBooks(EditText bookSearchName) {
        bookSearchName.clearFocus();
        InputMethodManager in = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(bookSearchName.getWindowToken(),0);
        bookSearchText = bookSearchName.getText().toString();
        ConnectivityManager cm  = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        boolean isConnected = (info != null) && info.isConnectedOrConnecting();
        if (isConnected){
            getLoaderManager().initLoader(0,null, MainActivity.this).forceLoad();
        }

    }


    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle args) {
        bookSearchName.setVisibility(View.INVISIBLE);
        searchButton.setVisibility(View.INVISIBLE);
        return new BooksLoader(this, bookSearchText);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> data) {
        Intent intent = new Intent(MainActivity.this, ListActivity.class);
        bookSearchName.setVisibility(View.VISIBLE);
        searchButton.setVisibility(View.VISIBLE);

        startActivity(intent);

    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        getLoaderManager().destroyLoader(0);
    }
}
