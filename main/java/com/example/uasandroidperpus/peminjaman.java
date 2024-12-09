package com.example.uasandroidperpus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

public class peminjaman extends AppCompatActivity {
    RecyclerView listbukupinjam;
    ImageButton bookbtn,memberbtn,addborrowbtn;
    ArrayList<String> peminjamanID, memberName, bookName, peminjamanDate, pengembalianDate, statusPeminjaman;
    DBHelper myDB;

    peminjamanAdapter peminjamanAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        setContentView(R.layout.activity_peminjaman);

        bookbtn = findViewById(R.id.bookbtn);
        bookbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(peminjaman.this, Listbook.class);
                startActivity(intent);
            }
        });
        memberbtn = findViewById(R.id.memberbtn);
        memberbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(peminjaman.this, Listmember.class);
                startActivity(intent);
            }
        });
        addborrowbtn = findViewById(R.id.addborrowbtn);
        addborrowbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(peminjaman.this, Editpeminjaman.class);
                startActivity(intent);
            }
        });
        myDB = new DBHelper(peminjaman.this);
        peminjamanID = new ArrayList<>();
        memberName = new ArrayList<>();
        bookName = new ArrayList<>();
        peminjamanDate = new ArrayList<>();
        pengembalianDate = new ArrayList<>();
        statusPeminjaman = new ArrayList<>();

        listbukupinjam = findViewById(R.id.listbukupinjam);
        peminjamanAdapter = new peminjamanAdapter(peminjaman.this, peminjamanID, memberName, bookName, peminjamanDate,pengembalianDate,statusPeminjaman);
        listbukupinjam.setAdapter(peminjamanAdapter);
        listbukupinjam.setLayoutManager(new LinearLayoutManager(peminjaman.this));
    }
    protected void onResume() {
        super.onResume();
        storedatainarrays();
    }
    void storedatainarrays(){
        Cursor cursor = myDB.readPeminjamanData();
        if(cursor.getCount()==0){
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
        else {
            peminjamanID.clear();
            memberName.clear();
            bookName.clear();
            statusPeminjaman.clear();
            peminjamanDate.clear();
            pengembalianDate.clear();


            while (cursor.moveToNext()){
                peminjamanID.add(cursor.getString(0));
                memberName.add(cursor.getString(1));
                bookName.add(cursor.getString(2));
                peminjamanDate.add(cursor.getString(3));
                pengembalianDate.add(cursor.getString(4));
                statusPeminjaman.add(cursor.getString(5));


            }

            // Notify the adapter about the dataset change
            peminjamanAdapter.notifyDataSetChanged();
        }
        cursor.close();
    }

}