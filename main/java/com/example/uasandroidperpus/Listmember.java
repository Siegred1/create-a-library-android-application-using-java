package com.example.uasandroidperpus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

public class Listmember extends AppCompatActivity {
    RecyclerView memberList;
    DBHelper myDB;
    ImageButton addmember,backbtn;
    ArrayList<String> memberID = new ArrayList<>(), memberUsername = new ArrayList<>(),
            memberAlamat= new ArrayList<>(), memberEmail = new ArrayList<>();

    MemberAdapter memberAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listmember);
        myDB = new DBHelper(Listmember.this);
        memberList = findViewById(R.id.listmember);

        memberAdapter = new MemberAdapter(Listmember.this,memberID, memberUsername,
                memberAlamat,memberEmail );

        memberList.setAdapter(memberAdapter);
        memberList.setLayoutManager(new LinearLayoutManager(Listmember.this));

        backbtn = findViewById(R.id.backbtn);
        backbtn.setOnClickListener(view -> {
            Intent intent = new Intent(Listmember.this, peminjaman.class);
            startActivity(intent);
        });
        addmember = findViewById(R.id.addmemberbtn);
        addmember.setOnClickListener(view -> {
            Intent intent = new Intent(Listmember.this, Addmember.class);
            startActivity(intent);
        });
    }
    @Override
    protected void onResume(){
        super.onResume();
        storedatainarrays();
    }
    void storedatainarrays(){
        Cursor cursor = myDB.readMemberData();
        if(cursor.getCount()==0){
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
        else {
            memberID.clear();
            memberUsername.clear();
            memberAlamat.clear();
            memberEmail.clear();

            while (cursor.moveToNext()){
                memberID.add(cursor.getString(0));
                memberUsername.add(cursor.getString(1));
                memberAlamat.add(cursor.getString(2));
                memberEmail.add(cursor.getString(3));
            }

            // Notify the adapter about the dataset change
            memberAdapter.notifyDataSetChanged();
        }
        cursor.close();
    }
}