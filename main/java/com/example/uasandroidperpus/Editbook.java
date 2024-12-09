package com.example.uasandroidperpus;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Editbook extends AppCompatActivity {

    EditText title_input,author_input,pages_input;
    Button edit_button, cancel, delete_button;

    String id, title, author,pages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editbook);

        title_input = findViewById(R.id.judulbuku2);
        author_input = findViewById(R.id.author2);
        pages_input = findViewById(R.id.halaman2);
        edit_button = findViewById(R.id.edit);
        delete_button = findViewById(R.id.delete);

        getAndSetIntentData();
        // Inside Editbook activity's onClickListener
        edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve the updated values from EditText fields
                String updatedTitle = title_input.getText().toString().trim();
                String updatedAuthor = author_input.getText().toString().trim();
                String updatedPages = pages_input.getText().toString().trim();


                // Check if any field is empty
                if (updatedTitle.isEmpty() || updatedAuthor.isEmpty() || updatedPages.isEmpty()) {
                    Toast.makeText(Editbook.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!isInteger(updatedPages)) {
                    Toast.makeText(Editbook.this, "Please enter a valid integer for Pages", Toast.LENGTH_SHORT).show();
                    return;
                }



                DBHelper myDB = new DBHelper(Editbook.this);
                // Call updateDataBook with the updated values
                myDB.updateDataBook(id, updatedTitle, updatedAuthor, updatedPages);
                // Close the activity or navigate to another screen if needed
                finish();
            }
        });
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();

            }
        });
        cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Editbook.this, Listbook.class);
                startActivity(intent);
            }
        });


    }
    void getAndSetIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("title")&&
                getIntent().hasExtra("author")&& getIntent().hasExtra("pages")){
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            author = getIntent().getStringExtra("author");
            pages = getIntent().getStringExtra("pages");

            title_input.setText(title);
            author_input.setText(author);
            pages_input.setText(pages);


        }else {
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }
    }
    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete "+ title+"?");
        builder.setMessage("Are you sure you want to delete"+title+"?");
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DBHelper myDB = new DBHelper(Editbook.this);
                myDB.deleteDataBook(id);

                Intent refreshIntent = new Intent(Editbook.this, Listbook.class);
                startActivity(refreshIntent);

                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
    private boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}