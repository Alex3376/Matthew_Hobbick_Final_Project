package com.example.finalproject;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.ViewHolder> {
    ArrayList<SecondActivity.Book> data = new ArrayList<>();

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTitle;
        TextView mAuthor;

        public ViewHolder(View view) {
            super(view);
            mTitle = view.findViewById(R.id.title);
            mAuthor = view.findViewById(R.id.author);

        }

        public TextView getmTitle() {
            return mTitle;
        }

        public TextView getmAuthor() {
            return mAuthor;
        }
    }

    public BookListAdapter(ArrayList<SecondActivity.Book> data) {
        this.data = data;
    }

    public void setItems(ArrayList<SecondActivity.Book> books) {
        if (books.size() == 0) {
            Log.e("adapter", "passed in data is null");
        }
        this.data = books;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item, parent, false);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecyclerView rv = view.findViewById(R.id.book_list);
                TextView text = view.findViewById(R.id.title);
                String title = (String) text.getText();
                SecondActivity.Book book2 = null;
                for (SecondActivity.Book book: data) {
                    if (book.title == title) {
                        book2 = book;
                        break;
                    }
                }

                Intent intent = new Intent(view.getContext(), ModelDetailPage.class);
                intent.putExtra("Book", book2);
                view.getContext().startActivity(intent);
            }
        });
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookListAdapter.ViewHolder holder, int position) {
        holder.getmAuthor().setText(data.get(position).getAuthor());
        holder.getmTitle().setText(data.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
