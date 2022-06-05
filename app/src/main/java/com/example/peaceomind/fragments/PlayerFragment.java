package com.example.peaceomind.fragments;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.peaceomind.MusicData;
import com.example.peaceomind.R;
import com.example.peaceomind.databinding.FragmentPlayerBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class PlayerFragment extends Fragment {

    private ArrayList<MusicData> playingQueue;
    private DatabaseReference databaseReference;
    private int position = 0;
    private  MediaPlayer mediaPlayer = new MediaPlayer();
    private FragmentPlayerBinding binding;
    private Timer timer = new Timer();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Musics");
        musicRetrieve();
        binding = FragmentPlayerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            setPosition(getArguments().getInt("position"));
        }
        binding.playPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    binding.playPauseButton.setImageResource(R.drawable.ic_play);
                } else {
                    mediaPlayer.start();
                    binding.playPauseButton.setImageResource(R.drawable.ic_pause);
                }
            }
        });
        binding.progressSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void setPosition(int position) {
        this.position = position;
        if (playingQueue!=null){

            MusicData song = playingQueue.get(position);
            try {
                mediaPlayer.setDataSource(song.getMusicLink());
                mediaPlayer.prepare();
            }catch(IOException e) {

            }
            Glide.with(this).load(song.getMusicImage())
                    .into(binding.image);
            binding.title.setText(song.getMusicName());
            mediaPlayer.start();
            binding.progressSlider.setMax(mediaPlayer.getDuration());
            //binding.songTotalTime.setText(getReadableDurationString((long)mediaPlayer.getDuration()));
        }
    }

    private void musicRetrieve() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //list initialization
                playingQueue = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    MusicData data = dataSnapshot.getValue(MusicData.class);
                    playingQueue.add(0, data);
                }
                setPosition(position);

                timer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                       binding.progressSlider.setProgress(mediaPlayer.getCurrentPosition());
                       //binding.songCurrentProgress.setText(getReadableDurationString((long)mediaPlayer.getCurrentPosition()));
                    }
                }, 0, 1000);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show(); //here too
            }
        });
    }


    private String getReadableDurationString(Long songDurationMillis) {
        double minutes = songDurationMillis / 1000d / 60;
        double seconds = songDurationMillis / 1000d % 60;
        String text;
        if (minutes < 60) {
            text = String.format(
                    Locale.getDefault(),
                    "%02d:%02d",
                    minutes,
                    seconds
            );
        } else {
            double hours = minutes / 60;
            minutes %= 60;
            text = String.format(
                    Locale.getDefault(),
                    "%02d:%02d:%02d",
                    hours,
                    minutes,
                    seconds
            );
        }
        return text;
    }


}