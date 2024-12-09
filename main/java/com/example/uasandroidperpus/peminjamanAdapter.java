package com.example.uasandroidperpus;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class peminjamanAdapter extends RecyclerView.Adapter<peminjamanAdapter.MyViewHolder> {

    Context context;
    ArrayList peminjamanID, memberName, bookName, peminjamanDate, pengembalianDate, statusPeminjaman;

    public peminjamanAdapter(Context context, ArrayList peminjamanID, ArrayList memberName,
                             ArrayList bookName, ArrayList peminjamanDate, ArrayList pengembalianDate,
                             ArrayList statusPeminjaman) {
        this.context = context;
        this.peminjamanID = peminjamanID;
        this.memberName = memberName;
        this.bookName = bookName;
        this.peminjamanDate = peminjamanDate;
        this.pengembalianDate = pengembalianDate;
        this.statusPeminjaman = statusPeminjaman;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.peminjaman_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.peminjamanIDTextView.setText(String.valueOf(peminjamanID.get(position)));
        holder.memberNameTextView.setText(String.valueOf(memberName.get(position)));
        holder.bookNameTextView.setText(String.valueOf(bookName.get(position)));
        holder.peminjamanDateTextView.setText(String.valueOf(peminjamanDate.get(position)));
        holder.pengembalianDateTextView.setText(String.valueOf(pengembalianDate.get(position)));
        holder.statusPeminjamanTextView.setText(String.valueOf(statusPeminjaman.get(position)));
        holder.MainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, updatepeminjaman.class);
                intent.putExtra("id", String.valueOf(peminjamanID.get(holder.getAdapterPosition())));
                intent.putExtra("nama", String.valueOf(memberName.get(holder.getAdapterPosition())));
                intent.putExtra("book_name", String.valueOf(bookName.get(holder.getAdapterPosition())));
                intent.putExtra("peminjaman_date", String.valueOf(peminjamanDate.get(holder.getAdapterPosition())));
                intent.putExtra("pengembalian_date", String.valueOf(pengembalianDate.get(holder.getAdapterPosition())));
                intent.putExtra("status", String.valueOf(statusPeminjaman.get(holder.getAdapterPosition())));

                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return peminjamanID.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView peminjamanIDTextView, memberNameTextView, bookNameTextView, peminjamanDateTextView, pengembalianDateTextView, statusPeminjamanTextView;
        LinearLayout MainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            peminjamanIDTextView = itemView.findViewById(R.id.peminjamanID);
            memberNameTextView = itemView.findViewById(R.id.MemberName);
            bookNameTextView = itemView.findViewById(R.id.BookName);
            peminjamanDateTextView = itemView.findViewById(R.id.peminjamandate);
            pengembalianDateTextView = itemView.findViewById(R.id.pengembaliandate);
            statusPeminjamanTextView = itemView.findViewById(R.id.memberName);
            MainLayout = itemView.findViewById(R.id.MainLayout);
        }
    }
}
