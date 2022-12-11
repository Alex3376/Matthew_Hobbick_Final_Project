// Matthew Hobbick
package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>{
    ArrayList<Book> books = new ArrayList<>();
    BookListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Bundle args = getIntent().getBundleExtra("bundle");
        books = (ArrayList<Book>) getIntent().getSerializableExtra("books");
        String data = getIntent().getStringExtra("itemarray");
        Log.e("second activity", data);
        RecyclerView list = (RecyclerView) findViewById(R.id.book_list);
        adapter = new BookListAdapter(parseData(data));
        list.setAdapter(adapter);
    }

    public ArrayList<Book> parseData(String data) {
        ArrayList<Book> books = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray itemsArray = jsonObject.getJSONArray("items");
            int i = 0;
            while (i < itemsArray.length()) {
                JSONObject book = itemsArray.getJSONObject(i);
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");
                JSONObject searchInfo = book.getJSONObject("searchInfo");
                Book book1 = new Book(volumeInfo.getString("title"),
                        volumeInfo.getString("authors"),
                        searchInfo.getString("textSnippet"),
                        book);
                Log.e("parse data", "title: " + book1.title);
                books.add(book1);
                i++;
            }
        } catch (Exception e) {
            Log.e("second", e.getMessage());
        }
        Log.e("parse data", "" + books.size());
        return books;
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        String queryString = "";

        if (args != null) {
            queryString = args.getString("queryString");
        }
        return new BookLoader(this, queryString);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {

    }

    public static class Book implements Serializable {
        String title;
        String author;
        String description;
        JSONObject book;

        public Book(String title, String author, String description, JSONObject book) {
            this.title = title;
            this.author = author;
            this.description = description;
        }

        public Book() {
            this.title = "";
            this.author = "";
            this.description = "";
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public JSONObject getBook() {
            return book;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setBook(JSONObject book) {
            this.book = book;
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}