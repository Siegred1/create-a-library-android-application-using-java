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

import java.util.regex.Pattern;

public class Editmember extends AppCompatActivity {

    EditText nama_input, alamat_input, email_input;
    Button edit_button, cancel, delete_button;

    String id, nama, alamat, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editmember);

        nama_input = findViewById(R.id.namalengkap);
        alamat_input = findViewById(R.id.alamat);
        email_input = findViewById(R.id.email);
        edit_button = findViewById(R.id.edit);
        delete_button = findViewById(R.id.delete);

        getAndSetIntentData();

        edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve the updated values from EditText fields
                String updatedNama = nama_input.getText().toString().trim();
                String updatedAlamat = alamat_input.getText().toString().trim();
                String updatedEmail = email_input.getText().toString().trim();

                // Check if any field is empty
                if (updatedNama.isEmpty() || updatedAlamat.isEmpty() || updatedEmail.isEmpty()) {
                    Toast.makeText(Editmember.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check if the email is valid
                if (!isValidEmail(updatedEmail)) {
                    Toast.makeText(Editmember.this, "Invalid email address", Toast.LENGTH_SHORT).show();
                    return;
                }

                DBHelper myDB = new DBHelper(Editmember.this);
                // Call updateDataBook with the updated values
                myDB.updateDataMember(id, updatedNama, updatedAlamat, updatedEmail);
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
                Intent intent = new Intent(Editmember.this, Listmember.class);
                startActivity(intent);
            }
        });
    }

    void getAndSetIntentData() {
        if (getIntent().hasExtra("id") && getIntent().hasExtra("nama") &&
                getIntent().hasExtra("alamat") && getIntent().hasExtra("email")) {
            id = getIntent().getStringExtra("id");
            nama = getIntent().getStringExtra("nama");
            alamat = getIntent().getStringExtra("alamat");
            email = getIntent().getStringExtra("email");

            nama_input.setText(nama);
            alamat_input.setText(alamat);
            email_input.setText(email);
        } else {
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + nama + "?");
        builder.setMessage("Are you sure you want to delete" + nama + "?");
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DBHelper myDB = new DBHelper(Editmember.this);
                myDB.deleteDataMember(id);

                Intent refreshIntent = new Intent(Editmember.this, Listmember.class);
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
    private boolean isValidEmail(String email) {
        // Define a regular expression for a valid email address
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        // Create a pattern object
        Pattern pattern = Pattern.compile(emailRegex);

        // Check if the email matches the pattern
        return pattern.matcher(email).matches();
    }
}
