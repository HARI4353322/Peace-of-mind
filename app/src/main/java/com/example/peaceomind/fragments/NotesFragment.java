package com.example.peaceomind.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.peaceomind.MusicAdapter;
import com.example.peaceomind.MusicData;
import com.example.peaceomind.NotesAdapter;
import com.example.peaceomind.NotesData;
import com.example.peaceomind.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class NotesFragment extends Fragment {

    private RecyclerView notesRecycler;
    private ArrayList<NotesData> notesData;
    private DatabaseReference databaseReference;
    private NotesAdapter nAdapter;

    private EditText header,description;
    private Button submit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_notes, container, false);

        notesRecycler=v.findViewById(R.id.notesRecycler);

        header=v.findViewById(R.id.header);
        description=v.findViewById(R.id.description);
        submit=v.findViewById(R.id.submit);

        databaseReference= FirebaseDatabase.getInstance().getReference().child("Notes");

        LinearLayoutManager manager=new LinearLayoutManager(getContext());
        notesRecycler.setLayoutManager(manager); //get context instead of this since fragment
        notesRecycler.setHasFixedSize(true);

        notesRetrieve();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(header.getText().toString().isEmpty()){
                    header.setError("Notes Header cannot be empty!");
                    header.requestFocus();
                }
                else if(description.getText().toString().isEmpty()){
                    description.setError("Notes Description cannot be empty!");
                    description.requestFocus();
                }
                else{
                    uploadNotes();
                }
            }
        });

        return v;
    }

    private void uploadNotes() {
        final String uniqueKey=databaseReference.push().getKey();

        String head=header.getText().toString();
        String desc=description.getText().toString();

        Calendar calendarDate=Calendar.getInstance();
        SimpleDateFormat currentDate=new SimpleDateFormat("dd-MM-yy");
        String date=currentDate.format(calendarDate.getTime());

        Calendar calendarTime=Calendar.getInstance();
        SimpleDateFormat currentTime=new SimpleDateFormat("hh:mm");
        String time=currentTime.format(calendarTime.getTime());

        NotesData notesData=new NotesData(head,desc,date,time);

        databaseReference.child(uniqueKey).setValue(notesData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getContext(), "Notice Uploaded", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void notesRetrieve() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //list initialization
                notesData=new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    NotesData data = dataSnapshot.getValue(NotesData.class);
                    notesData.add(0, data);
                }


                notesRecycler.setAdapter(new NotesAdapter(getContext(),notesData));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(getContext(),error.getMessage(), Toast.LENGTH_SHORT).show(); //here too
            }
        });
    }

}