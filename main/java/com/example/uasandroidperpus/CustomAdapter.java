package com.example.uasandroidperpus;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private ArrayList book_id,book_title, book_author, book_pages;


    CustomAdapter(Context context,
                  ArrayList book_id,
                  ArrayList book_title,
                  ArrayList book_author,
                  ArrayList book_pages){
        this.context = context;
        this.book_id = book_id;
        this.book_title = book_title;
        this.book_author = book_author;
        this.book_pages = book_pages;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.book_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.book_id_txt.setText(String.valueOf(book_id.get(position)));
        holder.book_title_txt.setText(String.valueOf(book_title.get(position)));
        holder.book_author_txt.setText(String.valueOf(book_author.get(position)));
        holder.book_pages_txt.setText(String.valueOf(book_pages.get(position)));
        holder.MainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Editbook.class);
                intent.putExtra("id", String.valueOf(book_id.get(holder.getAdapterPosition())));
                intent.putExtra("title", String.valueOf(book_title.get(holder.getAdapterPosition())));
                intent.putExtra("author", String.valueOf(book_author.get(holder.getAdapterPosition())));
                intent.putExtra("pages", String.valueOf(book_pages.get(holder.getAdapterPosition())));

                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return book_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView book_id_txt, book_title_txt, book_author_txt, book_pages_txt;
        LinearLayout MainLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            book_id_txt = itemView.findViewById(R.id.bookid);
            book_title_txt = itemView.findViewById(R.id.booktitle);
            book_author_txt = itemView.findViewById(R.id.author);
            book_pages_txt = itemView.findViewById(R.id.pages);
            MainLayout = itemView.findViewById(R.id.MainLayout);
        }
    }
}
