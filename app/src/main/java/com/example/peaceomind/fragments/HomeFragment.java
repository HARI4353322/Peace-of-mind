package com.example.peaceomind.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.peaceomind.R;
import com.example.peaceomind.databinding.FragmentHomeBinding;
import com.smarteist.autoimageslider.DefaultSliderView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderLayout;

public class HomeFragment extends Fragment {


    private FragmentHomeBinding binding;
    private Button music,mind,diary,about;

    private SliderLayout sliderLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_home, container, false);

        music=v.findViewById(R.id.button);
        mind=v.findViewById(R.id.button2);
        diary=v.findViewById(R.id.button3);
        about=v.findViewById(R.id.button4);

        music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.nav_gallery);
            }
        });

        mind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.nav_slideshow);
            }
        });

        diary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.nav_notes);
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.nav_about);
            }
        });

        sliderLayout=v.findViewById(R.id.slider);
        sliderLayout.setIndicatorAnimation(IndicatorAnimations.WORM);
        sliderLayout.setSliderTransformAnimation(SliderAnimations.CUBEINROTATIONTRANSFORMATION);
        sliderLayout.setScrollTimeInSec(3);
        setSliderView();

        return v;
    }

    private void setSliderView() {
        for (int i=0;i<5;i++){
            DefaultSliderView sV=new DefaultSliderView(getContext());
            if (i==0){
            sV.setImageDrawable(R.drawable.p1);
            }
            else if(i==1){
                sV.setImageDrawable(R.drawable.p2);
               }
            else if(i==2){
                sV.setImageDrawable(R.drawable.p3);
                   }
            else if(i==3){
                sV.setImageDrawable(R.drawable.p4);
             }
            else if(i==4){
                sV.setImageDrawable(R.drawable.p5);
            }
            sV.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
            sliderLayout.addSliderView(sV);
        }
    }

}