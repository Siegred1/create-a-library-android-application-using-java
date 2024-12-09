package com.example.uasandroidperpus;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private Context context;
    public static final String DBName = "library.db";

     DBHelper(@Nullable Context context) {
        super(context, DBName, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL("create table member(id_member INTEGER primary key AUTOINCREMENT, nama_member TEXT, alamat_member TEXT, email TEXT)");
        sqLiteDatabase.execSQL("create table book(id_book INTEGER primary key AUTOINCREMENT, judul_book TEXT, author TEXT,  jumlah_halaman INTEGER  )");
        sqLiteDatabase.execSQL("CREATE TABLE peminjaman (" +
                "id_peminjaman INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "id_member INTEGER, " +
                "id_book INTEGER, " +
                "status_peminjaman TEXT CHECK (status_peminjaman IN ('dipinjam', 'dikembalikan')), "+
                "tanggal_peminjaman TEXT, " +
                "tanggal_pengembalian TEXT, " +
                "FOREIGN KEY (id_member) REFERENCES member(id_member), " +
                "FOREIGN KEY (id_book) REFERENCES book(id_book)" +
                ")");

    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i , int i1){
        sqLiteDatabase.execSQL("drop table if exists member");
        sqLiteDatabase.execSQL("drop table if exists book");
    }




   void insertDataMember(String nama_member, String alamat_member, String email) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nama_member", nama_member);
        contentValues.put("alamat_member", alamat_member);
        contentValues.put("email", email);
        long result = myDB.insert("member", null, contentValues);
        if (result==-1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Added successfully!", Toast.LENGTH_SHORT).show();
        }
    }
    void insertDataBook( String judul_book, String author, int jumlah_halaman) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("judul_book", judul_book);
        contentValues.put("author", author);
        contentValues.put("jumlah_halaman", jumlah_halaman);

        long result = myDB.insert("book", null, contentValues);
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readBookData(){
        String query = "SELECT * FROM "+ "book";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db!= null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;


    }

    Cursor readMemberData(){
        String query = "SELECT * FROM "+ "member";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db!= null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }


    void updateDataBook(String row_id,String judul_book, String author, String pages){
         SQLiteDatabase db = this.getWritableDatabase();
         ContentValues cv = new ContentValues();
         cv.put("judul_book",judul_book);
         cv.put("author",author);
         cv.put("jumlah_halaman",pages);

         long result = db.update("book", cv , "id_book=?", new String[]{row_id});
         if (result == -1) {
            Toast.makeText(context, "Failed to update.", Toast.LENGTH_SHORT).show();
         } else {
            Toast.makeText(context, "Successfully Updated!", Toast.LENGTH_SHORT).show();
         }

    }
    void deleteDataBook(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete("book" , "id_book=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully Delete!", Toast.LENGTH_SHORT).show();
        }
    }

    void updateDataMember(String row_id,String nama_member, String alamat_member, String email_member){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nama_member",nama_member);
        cv.put("alamat_member",alamat_member);
        cv.put("email",email_member);

        long result = db.update("member", cv , "id_member=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "Failed to update.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully Updated!", Toast.LENGTH_SHORT).show();
        }

    }
    void deleteDataMember(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete("member" , "id_member=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully Delete!", Toast.LENGTH_SHORT).show();
        }
    }

    void insertDataPeminjaman(int id_member, int id_book, String status_peminjaman, String tanggal_peminjaman, String tanggal_pengembalian) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id_member", id_member);
        contentValues.put("id_book", id_book);
        contentValues.put("status_peminjaman", status_peminjaman);
        contentValues.put("tanggal_peminjaman", tanggal_peminjaman);
        contentValues.put("tanggal_pengembalian", tanggal_pengembalian);

        long result = myDB.insert("peminjaman", null, contentValues);
        if (result == -1) {
            Toast.makeText(context, "Failed to add peminjaman.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Peminjaman added successfully!", Toast.LENGTH_SHORT).show();
        }
    }
    Cursor readPeminjamanData() {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT  peminjaman.id_peminjaman,member.nama_member, book.judul_book, peminjaman.tanggal_peminjaman, peminjaman.tanggal_pengembalian, peminjaman.status_peminjaman " +
                "FROM peminjaman " +
                "JOIN member ON peminjaman.id_member = member.id_member " +
                "JOIN book ON peminjaman.id_book = book.id_book";

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    int getBookId(String bookName) {
        SQLiteDatabase db = this.getReadableDatabase();
        int bookId = -1;

        Cursor cursor = db.rawQuery("SELECT id_book FROM book WHERE judul_book=?", new String[]{bookName});
        if (cursor.moveToFirst()) {
            bookId = cursor.getInt(0);
        }

        cursor.close();
        return bookId;
    }

    int getMemberId(String memberName) {
        SQLiteDatabase db = this.getReadableDatabase();
        int memberId = -1;

        Cursor cursor = db.rawQuery("SELECT id_member FROM member WHERE nama_member=?", new String[]{memberName});
        if (cursor.moveToFirst()) {
            memberId = cursor.getInt(0);
        }

        cursor.close();
        return memberId;
    }

    public ArrayList<String> getBookNames() {
        ArrayList<String> bookNamesList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        if (db != null) {
            Cursor cursor = db.rawQuery("SELECT judul_book FROM book", null);

            if (cursor.moveToFirst()) {
                do {
                    String bookName = cursor.getString(0);
                    bookNamesList.add(bookName);
                } while (cursor.moveToNext());
            }

            cursor.close();
        }

        return bookNamesList;
    }

    // Method to get all member names
    public ArrayList<String> getMemberNames() {
        ArrayList<String> memberNamesList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        if (db != null) {
            Cursor cursor = db.rawQuery("SELECT nama_member FROM member", null);

            if (cursor.moveToFirst()) {
                do {
                    String memberName = cursor.getString(0);
                    memberNamesList.add(memberName);
                } while (cursor.moveToNext());
            }

            cursor.close();
        }

        return memberNamesList;
    }

    public void updatePeminjamanStatus(String peminjamanId, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("status_peminjaman", status);

        db.update("peminjaman", values, "id_peminjaman = ?", new String[]{peminjamanId});
        db.close();
    }
}
