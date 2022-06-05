package com.example.peaceomind;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;
import androidx.navigation.Navigator;
import androidx.navigation.fragment.FragmentNavigatorExtrasKt;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MusicAdapter extends RecyclerView.Adapter<com.example.peaceomind.MusicAdapter.MusicViewAdapter> {

    private ArrayList<MusicData> list;
    private Context context;

    public MusicAdapter(Context context, ArrayList<MusicData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MusicViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.music_layout, parent, false);
        return new MusicViewAdapter(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicViewAdapter holder, int position) {
        MusicData currentItem = list.get(position);

        holder.musicName.setText(currentItem.getMusicName());

        Glide.with(context).load(currentItem.getMusicImage()).into(holder.musicImage);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MusicViewAdapter extends RecyclerView.ViewHolder {

        private TextView musicName;

        private ImageView musicImage;

        public MusicViewAdapter(@NonNull View itemView) {
            super(itemView);

            musicName = itemView.findViewById(R.id.musicName);
            musicImage = itemView.findViewById(R.id.musicImg);
            itemView.setOnClickListener(view -> {
                Bundle bundle = new Bundle();
                bundle.putInt("position", getAdapterPosition());
                Navigation.findNavController(view).navigate(R.id.fragment_player, bundle);
            });
        }
    }
}
