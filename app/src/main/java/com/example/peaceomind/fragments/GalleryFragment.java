package com.example.peaceomind.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.peaceomind.MusicAdapter;
import com.example.peaceomind.MusicData;
import com.example.peaceomind.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GalleryFragment extends Fragment {

    private RecyclerView musicRecycler;
    private ArrayList<MusicData> musicData;
    private DatabaseReference databaseReference;
    private MusicAdapter mAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_gallery, container, false);

        musicRecycler=v.findViewById(R.id.musicRecycler);

        databaseReference= FirebaseDatabase.getInstance().getReference().child("Musics");

        LinearLayoutManager manager=new LinearLayoutManager(getContext());
        musicRecycler.setLayoutManager(manager); //get context instead of this since fragment
        musicRecycler.setHasFixedSize(true);

        musicRetrieve();

        return v;
    }

    private void musicRetrieve() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //list initialization
                musicData=new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    MusicData data = dataSnapshot.getValue(MusicData.class);
                    musicData.add(0, data);
                }


                musicRecycler.setAdapter(new MusicAdapter(getContext(),musicData));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(getContext(),error.getMessage(), Toast.LENGTH_SHORT).show(); //here too
            }
        });
    }

}