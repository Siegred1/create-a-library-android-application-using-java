package com.example.uasandroidperpus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.widget.Toast;

public class Addbook extends AppCompatActivity {
    EditText judul_input, author_input, jumlah_input;
    Button save_btn, clear_btn, cancel_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbook);

        judul_input = findViewById(R.id.judulbuku);
        author_input = findViewById(R.id.author);
        jumlah_input = findViewById(R.id.halaman);
        save_btn = findViewById(R.id.save);

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve the values from EditText fields
                String judul = judul_input.getText().toString().trim();
                String author = author_input.getText().toString().trim();
                String jumlahStr = jumlah_input.getText().toString().trim();

                // Check if any field is empty
                if (judul.isEmpty() || author.isEmpty() || jumlahStr.isEmpty()) {
                    Toast.makeText(Addbook.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!isInteger(jumlahStr)) {
                    Toast.makeText(Addbook.this, "Please enter a valid Number ", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Parse jumlah as an integer
                int jumlah = Integer.parseInt(jumlahStr);

                // If all fields are filled, save the data
                DBHelper myDB = new DBHelper(Addbook.this);
                myDB.insertDataBook(judul, author, jumlah);

                // Navigate to the Listbook activity
                Intent intent = new Intent(Addbook.this, Listbook.class);
                startActivity(intent);
            }
        });

        clear_btn = findViewById(R.id.clear);
        clear_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Clear the EditText fields
                clearEditTextFields();
            }
        });

        cancel_btn = findViewById(R.id.cancel);
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an Intent to navigate back to the previous activity
                Intent intent = new Intent(Addbook.this, Listbook.class);
                startActivity(intent);
            }
        });
    }

    // Method to clear the EditText fields
    private void clearEditTextFields() {
        judul_input.setText("");
        author_input.setText("");
        jumlah_input.setText("");
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
