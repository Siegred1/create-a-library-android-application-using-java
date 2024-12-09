package com.example.uasandroidperpus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class Addmember extends AppCompatActivity {

    EditText Nama, Alamat, email;
    Button save,clear,cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addmember);
        Nama = findViewById(R.id.namalengkap);
        Alamat = findViewById(R.id.alamat);
        email = findViewById(R.id.email);

        save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve user input
                String nama = Nama.getText().toString().trim();
                String alamat = Alamat.getText().toString().trim();
                String emailAddress = email.getText().toString().trim();

                if (nama.isEmpty() || alamat.isEmpty() || emailAddress.isEmpty()) {
                    Toast.makeText(Addmember.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Validate email
                if (!isValidEmail(emailAddress)) {
                    Toast.makeText(Addmember.this, "Invalid email address", Toast.LENGTH_SHORT).show();
                    return;
                }


                // Insert data into the database
                TambahAkun(nama, alamat, emailAddress);

                // Navigate to the list activity
                Intent intent = new Intent(Addmember.this, Listmember.class);
                startActivity(intent);
            }
        });
        clear = findViewById(R.id.clear);
        clear.setOnClickListener(v -> {
            Nama.setText("");
            Alamat.setText("");
            email.setText("");
        });
        cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(view -> finish());
    }
    private void TambahAkun(String username, String alamat, String email ){
        DBHelper myDB = new DBHelper(Addmember.this);
        myDB.insertDataMember(username, alamat, email);
        finish();
    }
    private boolean isValidEmail(String email) {
        // Define a regular expression for a valid email address
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        // Create a pattern object
        Pattern pattern = Pattern.compile(emailRegex);

        // Check if the email matches the pattern
        return pattern.matcher(email).matches();
    }
}