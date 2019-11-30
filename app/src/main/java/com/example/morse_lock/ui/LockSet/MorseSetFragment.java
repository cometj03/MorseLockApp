package com.example.morse_lock.ui.LockSet;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.morse_lock.R;

public class MorseSetFragment extends Fragment {

    private MorseSetViewModel morseSetViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        boolean[] isLocked = {false};
        morseSetViewModel =
                ViewModelProviders.of(this).get(MorseSetViewModel.class);
        View root = inflater.inflate(R.layout.fragment_morse_set, container, false);
        Button changeBtn = root.findViewById(R.id.changeBtn);
        Switch switch1 = root.findViewById(R.id.switch1);
        Intent myIntent = new Intent(getActivity(), MorseSetActivity.class);

        morseSetViewModel.getText().observe(this, s -> changeBtn.setOnClickListener(view -> startActivity(myIntent)));
        switch1.setChecked(false);
        morseSetViewModel.getText().observe(this, s -> switch1.setOnCheckedChangeListener((compoundButton, b) -> {
            isLocked[0] = b;
            if (b)
            {
                switch1.setTextColor(Color.parseColor("#000000"));
                switch1.setText("Locked");
                startActivity(myIntent);
            }else{
                switch1.setTextColor(Color.parseColor("#9b9b9b"));
                switch1.setText("UnLocked");
            }
        }));
        return root;
    }
}