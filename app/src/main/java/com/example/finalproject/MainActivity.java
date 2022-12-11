// Matthew Hobbick
package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<String> {

    private EditText mBookInput;
    private TextView mTitleText;
    private TextView mAuthorText;
    private ArrayList<SecondActivity.Book> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBookInput = (EditText) findViewById(R.id.bookInput);
        mTitleText = (TextView) findViewById(R.id.titleText);
        mAuthorText = (TextView) findViewById(R.id.authorText);

        if (getSupportLoaderManager().getLoader(0) != null) {
            getSupportLoaderManager().initLoader(0, null, this);
        }

        findViewById(R.id.searchButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchBooks(view);
            }
        });
    }

    public void searchBooks(View view) {

        // Get the search string from the input field.
        String queryString = mBookInput.getText().toString();
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        if (inputManager != null) {
            inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connMgr != null) {
            networkInfo = connMgr.getActiveNetworkInfo();
        }

        if (networkInfo != null && networkInfo.isConnected() && queryString.length() != 0) {
            Bundle queryBundle = new Bundle();
            queryBundle.putString("queryString", queryString);
            getSupportLoaderManager().restartLoader(0, queryBundle, this);
            mAuthorText.setText("");
            mTitleText.setText(R.string.loading);
        } else if (queryString.length() == 0) {
            mAuthorText.setText("");
            mTitleText.setText(R.string.no_search_term);
        } else {
            mAuthorText.setText("");
            mTitleText.setText(R.string.no_network);
        }

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
        ArrayList<SecondActivity.Book> booklist = new ArrayList<>();
        JSONArray itemsArray = null;
        try {
            JSONObject jsonObject = new JSONObject(data);
            itemsArray = jsonObject.getJSONArray("items");

            int i = 0;
            String title = null;
            String authors = null;
            ProgressBar bar = findViewById(R.id.progress_bar);
            Log.e("items array", String.valueOf(itemsArray.length()));

            while (i < itemsArray.length()) {
                JSONObject book = itemsArray.getJSONObject(i);
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");
                bar.setProgress(i / itemsArray.length());
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("on loader finish", "data size" + booklist.size());
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        intent.putExtra("books", booklist);
        intent.putExtra("itemarray", data);
        startActivity(intent);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }


}

