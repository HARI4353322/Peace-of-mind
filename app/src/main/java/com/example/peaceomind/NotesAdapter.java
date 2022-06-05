package com.example.peaceomind;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewAdapter>{

    private ArrayList<NotesData> list;
    private Context context;

    public NotesAdapter(Context context, ArrayList<NotesData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public NotesViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.notes_layout, parent, false);
        return new NotesViewAdapter(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesAdapter.NotesViewAdapter holder, int position) {
        NotesData currentItem = list.get(position);

        holder.notesHeader.setText(currentItem.getNotesHeader());
        holder.notesDescription.setText(currentItem.getNotesDescription());
        holder.date.setText(currentItem.getDate());
        holder.time.setText(currentItem.getTime());


    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NotesViewAdapter extends RecyclerView.ViewHolder {

        private TextView notesHeader,notesDescription,date,time;


        public NotesViewAdapter(@NonNull View itemView) {
            super(itemView);

            notesHeader = itemView.findViewById(R.id.notesHeader);
            notesDescription = itemView.findViewById(R.id.notesDescription);
            date=itemView.findViewById(R.id.date);
            time=itemView.findViewById(R.id.time);
        }
    }
}

