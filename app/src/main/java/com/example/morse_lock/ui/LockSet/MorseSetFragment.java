package com.example.morse_lock.ui.LockSet;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
        SharedPreferences pref = this.getActivity().getSharedPreferences("LOCK", 0);
        boolean[] isLocked = {false};
        isLocked[0] = pref.getBoolean("isLocked", false);
        morseSetViewModel =
                ViewModelProviders.of(this).get(MorseSetViewModel.class);
        View root = inflater.inflate(R.layout.fragment_morse_set, container, false);
        Button changeBtn = root.findViewById(R.id.changeBtn);
        Switch switch1 = root.findViewById(R.id.switch1);

        Intent myIntent = new Intent(getActivity(), MorseSetActivity.class);

        changeBtn.setEnabled(isLocked[0]);
        switch1.setChecked(isLocked[0]);
        if (isLocked[0]){
            switch1.setText("Locked");
            switch1.setTextColor(Color.parseColor("#000000"));
        }else {
            switch1.setText("UnLocked");
            switch1.setTextColor(Color.parseColor("#9b9b9b"));
        }

        morseSetViewModel.getText().observe(this, s ->
            changeBtn.setOnClickListener(view -> {
                myIntent.putExtra("title", "change morse");
                startActivity(myIntent);
            }));
        morseSetViewModel.getText().observe(this, s -> switch1.setOnCheckedChangeListener((compoundButton, b) -> {
            final Boolean[] isChecked = {false};
            if (b)
            {
                switch1.setTextColor(Color.parseColor("#000000"));
                switch1.setText("Locked");
                myIntent.putExtra("title", "set morse");
                startActivity(myIntent);
            }else{
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), android.R.style.Theme_Material_Light_Dialog_Alert);   // 알림창
                builder.setTitle("알림").setMessage("잠금을 해제하시겠습니까?");
                builder.setPositiveButton("확인", (dialog, which) -> {    // 확인 버튼
                    switch1.setTextColor(Color.parseColor("#9b9b9b"));
                    switch1.setText("UnLocked");
                    isChecked[0] = true;
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean("isLocked", false);
                    editor.commit();
                });
                builder.setNegativeButton("취소", ((dialog, which) -> isChecked[0] = false));
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
            switch1.setChecked(!isChecked[0]);
        }));
        return root;
    }
}