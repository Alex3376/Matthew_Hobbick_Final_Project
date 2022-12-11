package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ModelDetailPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model_detail_page);
        SecondActivity.Book book = (SecondActivity.Book) getIntent().getSerializableExtra("Book");
        // Title
        TextView authorText = findViewById(R.id.textView);
        authorText.setText(book.getTitle());
        // description
        TextView descriptText = findViewById(R.id.textView3);
        descriptText.setText(book.getDescription());
        // author

    }

}