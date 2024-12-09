package com.example.uasandroidperpus;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class updatepeminjaman extends AppCompatActivity {

    private AutoCompleteTextView bookFinder, memberFinder, TanggalPeminjaman, TanggalPengembalian;
    private Spinner StatusPinjam;
    private Button pinjam, cancel;
    ArrayAdapter<String> bookNameAdapter, memberNameAdapter;
    DBHelper myDB;
    String peminjamanId, selectedBookName, selectedMemberName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatepeminjaman);

        bookFinder = findViewById(R.id.judulbuku);
        memberFinder = findViewById(R.id.namaPeminjam);
        TanggalPeminjaman = findViewById(R.id.tanggalPinjam);
        TanggalPengembalian = findViewById(R.id.tanggalKembali);
        StatusPinjam = findViewById(R.id.spinnerStatus);

        pinjam = findViewById(R.id.edit);
        cancel = findViewById(R.id.cancel);

        myDB = new DBHelper(this);

        // Daftar opsi status
        String[] statusOptions = {"dipinjam", "dikembalikan"};

        // Buat adapter untuk Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, statusOptions);
        StatusPinjam.setAdapter(adapter);

        // Populate AutoCompleteTextViews with book and member names
        bookNameAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, myDB.getBookNames());
        memberNameAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, myDB.getMemberNames());

        bookFinder.setAdapter(bookNameAdapter);
        memberFinder.setAdapter(memberNameAdapter);

        // Get intent extras
        peminjamanId = getIntent().getStringExtra("id");
        selectedMemberName = getIntent().getStringExtra("nama");
        selectedBookName = getIntent().getStringExtra("book_name");
        String peminjamanDate = getIntent().getStringExtra("peminjaman_date");
        String pengembalianDate = getIntent().getStringExtra("pengembalian_date");
        String status = getIntent().getStringExtra("status");

// Set initial values
        memberFinder.setText(selectedMemberName);
        bookFinder.setText(selectedBookName);
        TanggalPeminjaman.setText(peminjamanDate);
        TanggalPengembalian.setText(pengembalianDate);  // Add this line to set the return date
        StatusPinjam.setSelection(adapter.getPosition(status));

        if (status.equals("dikembalikan")) {
            pinjam.setVisibility(View.INVISIBLE);
            StatusPinjam.setEnabled(false); // Disable the spinner
        } else {
            pinjam.setVisibility(View.VISIBLE); // Show the button for other statuses
            StatusPinjam.setEnabled(true); // Enable the spinner for other statuses
        }


        pinjam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatePeminjaman();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void updatePeminjaman() {
        String status = StatusPinjam.getSelectedItem().toString();

        // Check if the status is valid
        if (!status.equals("dipinjam") && !status.equals("dikembalikan")) {
            Toast.makeText(this, "Invalid status value", Toast.LENGTH_SHORT).show();
            return;  // Don't proceed with the update
        }

        // Update peminjaman status
        myDB.updatePeminjamanStatus(peminjamanId, status);

        // Inform the user about the status update
        Toast.makeText(this, "Peminjaman status updated successfully!", Toast.LENGTH_SHORT).show();

        // Finish the activity
        finish();
    }
}
