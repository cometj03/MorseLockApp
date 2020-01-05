package com.example.morse_lock.ui.LockSet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.morse_lock.R;

public class MorseSetFragment extends Fragment {

    private MorseSetViewModel morseSetViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        SharedPreferences pref = this.getActivity().getSharedPreferences("LOCK", 0);
        SharedPreferences.Editor editor = pref.edit();
        boolean[] isLocked = {false};
        isLocked[0] = pref.getBoolean("isLocked", false);
        int[] sensitivity = {pref.getInt("sensitivity", 0)};
        if (sensitivity[0] == 0)
            sensitivity[0] = 50;

        morseSetViewModel = ViewModelProviders.of(this).get(MorseSetViewModel.class);
        View root = inflater.inflate(R.layout.fragment_morse_set, container, false);

        Button changeBtn = root.findViewById(R.id.changeBtn);
        Switch switch1 = root.findViewById(R.id.switch1);
        SeekBar bar_sensitivity = root.findViewById(R.id.bar_sensitivity);
        TextView txt_sensitivity = root.findViewById(R.id.txt_sensitivity);

        bar_sensitivity.setProgress(sensitivity[0]);
        txt_sensitivity.setText((double)sensitivity[0] / 200.0 + "s");

        Intent myIntent = new Intent(getActivity(), MorseSetActivity.class);

        // 감도 조절 SeekBar
        bar_sensitivity.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                sensitivity[0] = i;
                txt_sensitivity.setText((double)i / 200.0 + "s");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                editor.putBoolean("isSensitivity", true);
                editor.putInt("sensitivity", sensitivity[0]);
                editor.commit();
            }
        });

        // 버튼 비활성화 or 활성화
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
            if (b)
            {
                switch1.setTextColor(Color.parseColor("#000000"));
                switch1.setText("Locked");
                myIntent.putExtra("title", "set morse");
                startActivity(myIntent);
            }else{
                Toast.makeText(getActivity(), "잠금 설정을 해제했습니다.", Toast.LENGTH_SHORT).show();
                switch1.setTextColor(Color.parseColor("#9b9b9b"));
                switch1.setText("UnLocked");
                changeBtn.setEnabled(false);
                editor.putBoolean("isLocked", false);
                editor.commit();
            }
        }));
        return root;
    }
}