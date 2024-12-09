package com.example.uasandroidperpus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

public class Listbook extends AppCompatActivity {

    RecyclerView listbook;
    ImageButton addbook;
    ImageButton backbtn;

    DBHelper myDB;

    ArrayList<String> book_id, book_title, author, jumlah;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listbook);

        listbook = findViewById(R.id.listbook);
        addbook = findViewById(R.id.addbookbtn);
        addbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Listbook.this, Addbook.class);
                startActivity(intent);
            }
        });
        backbtn = findViewById(R.id.backbtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Listbook.this, peminjaman.class);
                startActivity(intent);
            }
        });
        myDB = new DBHelper(Listbook.this);
        book_id = new ArrayList<>();
        book_title = new ArrayList<>();
        author = new ArrayList<>();
        jumlah = new ArrayList<>();

        customAdapter = new CustomAdapter(Listbook.this, book_id, book_title, author, jumlah);
        listbook.setAdapter(customAdapter);
        listbook.setLayoutManager(new LinearLayoutManager(Listbook.this));

        // Move the storedatainarrays call after setting up the adapter
    }

    @Override
    protected void onResume() {
        super.onResume();
        storedatainarrays();
    }

    void storedatainarrays(){
        Cursor cursor = myDB.readBookData();
        if(cursor.getCount()==0){
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
        else {
            book_id.clear();
            book_title.clear();
            author.clear();
            jumlah.clear();

            while (cursor.moveToNext()){
                book_id.add(cursor.getString(0));
                book_title.add(cursor.getString(1));
                author.add(cursor.getString(2));
                jumlah.add(cursor.getString(3));
            }

            // Notify the adapter about the dataset change
            customAdapter.notifyDataSetChanged();
        }
        cursor.close();
    }
}
