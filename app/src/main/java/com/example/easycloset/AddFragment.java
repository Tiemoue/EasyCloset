package com.example.easycloset;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class AddFragment extends Fragment {

    private Button btnCoats;
    private Button btnJackets;
    private Button btnSweaters;
    private Button btnShirts;
    private Button btnHoodies;
    private Button btnShorts;
    private Button btnPants;
    private Button btnJoggers;
    private Button btnBoots;
    private Button btnSneakers;
    private Button btnSlides;
    private Button btnHeadwear;
    private Button btnDone;
    private MainActivity activity;

    public AddFragment() {
    }

    public AddFragment(MainActivity mainActivity) {
        activity = mainActivity;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        btnDone = view.findViewById(R.id.btDone);
        btnCoats = view.findViewById(R.id.btCoat);

        btnCoats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.setFragmentContainer(activity.getUploadFragment());
            }
        });
    }
}