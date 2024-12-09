package com.example.uasandroidperpus;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.MyViewHolder> {

    Context context;
    ArrayList memberID, memberUsername, memberAlamat, memberEmail;


    public MemberAdapter(Context context, ArrayList memberID,ArrayList memberUsername,
                         ArrayList memberAlamat, ArrayList memberEmail) {
        this.context = context;
        this.memberID = memberID;
        this.memberUsername = memberUsername;
        this.memberAlamat = memberAlamat;
        this.memberEmail = memberEmail;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.member_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.memberIDTextview.setText(String.valueOf(memberID.get(position)));
        holder.usernameTextView.setText(String.valueOf(memberUsername.get(position)));
        holder.alamatTextView.setText(String.valueOf(memberAlamat.get(position)));
        holder.emailTextView.setText(String.valueOf(memberEmail.get(position)));
        holder.MainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Editmember.class);
                intent.putExtra("id", String.valueOf(memberID.get(holder.getAdapterPosition())));
                intent.putExtra("nama", String.valueOf(memberUsername.get(holder.getAdapterPosition())));
                intent.putExtra("alamat", String.valueOf(memberAlamat.get(holder.getAdapterPosition())));
                intent.putExtra("email", String.valueOf(memberEmail.get(holder.getAdapterPosition())));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return memberID.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView memberIDTextview, usernameTextView, alamatTextView, emailTextView;
        LinearLayout MainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            memberIDTextview = itemView.findViewById(R.id.memberID);
            usernameTextView = itemView.findViewById(R.id.MemberName);
            alamatTextView = itemView.findViewById(R.id.alamat);
            emailTextView = itemView.findViewById(R.id.email);
            MainLayout = itemView.findViewById(R.id.MainLayout);
        }
    }
}