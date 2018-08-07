package com.example.pranshumishra.findbooks;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pranshu Mishra on 7/28/2018.
 */
public class QueryUtils {
    static List<Book> books = new ArrayList<>();
    private final static String API_URL_STRING = "https://www.googleapis.com/books/v1/volumes?";
    private final static String KEY = "AIzaSyCxiEfiL6vpkwau3Goye4jwnFG5kpQjst8";
    private static String uriBuilder(String bookSearchText) {
        Uri uri = Uri.parse(API_URL_STRING)
                .buildUpon()
                .appendQueryParameter("q", bookSearchText)
                .appendQueryParameter("key", KEY)
                .build();
        return uri.toString();
    }

    private static URL makeURL(String uri) {
        URL url = null;
        try {
            url = new URL(uri);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    private static String getConnection(URL url) throws IOException {
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        String data = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            inputStream = urlConnection.getInputStream();
            if (urlConnection.getResponseCode() == 200) {
                data = readData(inputStream);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return data;
    }

    private static String readData(InputStream inputStream) throws IOException {
        StringBuilder builder = new StringBuilder();
        InputStreamReader reader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line = bufferedReader.readLine();
        builder.append(line);
        while (line != null) {
            line = bufferedReader.readLine();
            builder.append(line);
        }
        return builder.toString();
    }

    public static List<Book> searchBooks(String bookSearchText) {

        String uri = uriBuilder(bookSearchText);
        Log.e("asd",uri);
        URL url = makeURL(uri);
        String data = null;
        try {
            data = getConnection(url);
            books = parseJson(data);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return books;
    }

    private static List<Book> parseJson(String stringData) throws JSONException {
        JSONObject jsonObject = new JSONObject(stringData);
        JSONArray items = jsonObject.optJSONArray("items");
        List<Book> books = new ArrayList<>();
        for (int i = 0; i < items.length(); i++) {
            JSONObject singleBookData = items.optJSONObject(i);
            JSONObject volumeInfo = singleBookData.optJSONObject("volumeInfo");
            String title = volumeInfo.optString("title");
            JSONArray authors = volumeInfo.optJSONArray("authors");
            String[] authorsName = null;
            if (authors != null) {
                authorsName = new String[authors.length()];
                for (int j = 0; j < authors.length(); j++) {
                    authorsName[j] = authors.optString(j);
                }
            }
            String publishedDate = volumeInfo.optString("publishedDate");
            String description = volumeInfo.optString("description");
            int pageCount = volumeInfo.optInt("pageCount");
            JSONArray categories = volumeInfo.optJSONArray("categories");
            String[] categoriesArray = null;
            if (categories != null) {
                categoriesArray = new String[categories.length()];
                for (int j = 0; j < categories.length(); j++) {
                    categoriesArray[j] = categories.optString(j);
                }
            }
            int rating = volumeInfo.optInt("averageRating");
            int ratingCount = volumeInfo.optInt("ratingsCount");
            JSONObject imageLinkObject = volumeInfo.optJSONObject("imageLinks");
            String imageurl = "";
            if(imageLinkObject != null){
                imageurl = imageLinkObject.optString("thumbnail");
            }


            JSONObject saleInfo = singleBookData.optJSONObject("saleInfo");
            String buyLink = saleInfo.optString("buyLink");

            books.add(new Book(imageurl, title, authorsName, rating, ratingCount, publishedDate, description, buyLink, pageCount, categoriesArray));
        }
        return books;
    }
}
