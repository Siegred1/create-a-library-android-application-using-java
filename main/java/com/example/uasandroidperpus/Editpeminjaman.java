package com.example.uasandroidperpus;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Editpeminjaman extends AppCompatActivity {
    private AutoCompleteTextView bookFinder, memberFinder;
    private Calendar Kalender;
    private Button DateButton,returnDate,pinjam,cancel;
    ArrayAdapter<String> bookName, userName;
    DBHelper myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editpeminjaman);
        bookFinder = findViewById(R.id.judulbuku);
        memberFinder = findViewById(R.id.namaPeminjam);

        DateButton = findViewById(R.id.tanggalPinjam);
        returnDate = findViewById(R.id.tanggalKembali);
        Kalender = Calendar.getInstance();
        updateReturnDateButton();
        updateDateButton();
        DateButton.setOnClickListener(v -> showDatePickerDialog());
        returnDate.setOnClickListener(v -> showReturnDatePickerDialog());
        myDB = new DBHelper(Editpeminjaman.this);
        // Mendapatkan referensi ke Spinner
        Spinner StatusPinjam = findViewById(R.id.spinnerStatus);

        // Daftar opsi status
        String[] statusOptions = {"Dipinjam", "Dikembalikan"};

        // Buat adapter untuk Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, statusOptions);

        // Set adapter ke Spinner
        StatusPinjam.setAdapter(adapter);
        pinjam = findViewById(R.id.edit);
        pinjam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get selected values from spinners and other views
                int selectedMemberId = myDB.getMemberId(memberFinder.getText().toString());
                int selectedBookId = myDB.getBookId(bookFinder.getText().toString());
                Spinner statusSpinner = findViewById(R.id.spinnerStatus);

                // Get the selected status from the spinner and convert it to lowercase
                String selectedStatus = statusSpinner.getSelectedItem().toString().toLowerCase();

                // Validate the selected status
                if (!selectedStatus.equals("dipinjam") && !selectedStatus.equals("dikembalikan")) {
                    // Display an error message and return
                    Toast.makeText(Editpeminjaman.this, "Invalid status selected", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Proceed with insertion
                String tanggalPeminjaman = DateButton.getText().toString();
                String tanggalPengembalian = returnDate.getText().toString();

                // Insert data into the peminjaman table
                if (selectedMemberId != -1 && selectedBookId != -1) {
                    // Insert data into the peminjaman table
                    myDB.insertDataPeminjaman(selectedMemberId, selectedBookId, selectedStatus, tanggalPeminjaman, tanggalPengembalian);
                } else {
                    // Handle the case where member or book ID is not valid
                    Toast.makeText(Editpeminjaman.this, "Invalid member or book", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(Editpeminjaman.this, peminjaman.class);
                startActivity(intent);
            }
        });
        cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(view -> finish());

    }

    private void showReturnDatePickerDialog() {
        DatePickerDialog returnDatePicker = new DatePickerDialog(
                this,
                (datePicker, year, month, day) -> {
                    Kalender.set(Calendar.YEAR, year);
                    Kalender.set(Calendar.MONTH, month);
                    Kalender.set(Calendar.DAY_OF_MONTH, day);

                    setDateButton();
                },
                Kalender.get(Calendar.YEAR),
                Kalender.get(Calendar.MONTH),
                Kalender.get(Calendar.DAY_OF_MONTH));
        returnDatePicker.show();
    }

    private void updateReturnDateButton() {
        Calendar returnDateCalendar = (Calendar) Kalender.clone();
        returnDateCalendar.add(Calendar.DAY_OF_MONTH, 7);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String formattedReturnDate = dateFormat.format(returnDateCalendar.getTime());

        returnDate.setText(formattedReturnDate);
    }
    private void setDateButton() {
        Calendar returnDateCalendar = (Calendar) Kalender.clone();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String formattedReturnDate = dateFormat.format(returnDateCalendar.getTime());

        returnDate.setText(formattedReturnDate);
    }

    private void showDatePickerDialog() {
        DatePickerDialog tanggalPilih = new DatePickerDialog(
                this,
                (datePicker, year, month, day) -> {
                    Kalender.set(Calendar.YEAR, year);
                    Kalender.set(Calendar.MONTH, month);
                    Kalender.set(Calendar.DAY_OF_MONTH, day);

                    updateDateButton();
                    updateReturnDateButton();
                },
                Kalender.get(Calendar.YEAR),
                Kalender.get(Calendar.MONTH),
                Kalender.get(Calendar.DAY_OF_MONTH));

        tanggalPilih.show();
    }

    private void updateDateButton() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = dateFormat.format(Kalender.getTime());

        DateButton.setText(formattedDate);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadBooks();
        loadUser();
    }
    private void loadBooks() {
        Cursor cursor = myDB.readBookData();
        if (cursor.getCount() == 0) {
            String[] noItem = {"No book available"};
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, noItem);
            bookFinder.setAdapter(adapter);
        } else {
            if (bookName == null) {
                bookName = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);
            }
            bookName.clear();
            while (cursor.moveToNext()) {
                bookName.add(cursor.getString(1));
            }
            bookName.notifyDataSetChanged();
            bookFinder.setAdapter(bookName);
        }
    }
    private void loadUser() {
        Cursor cursor = myDB.readMemberData();
        if (cursor.getCount() == 0) {
            String[] noItem = {"Member not known"};
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, noItem);
            memberFinder.setAdapter(adapter);
        } else {
            if (userName == null) {
                userName = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);
            }
            userName.clear();
            while (cursor.moveToNext()) {
                userName.add(cursor.getString(1));
            }
            userName.notifyDataSetChanged();
            memberFinder.setAdapter(userName);
        }
    }



}