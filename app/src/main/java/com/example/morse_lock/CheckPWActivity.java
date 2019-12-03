package com.example.morse_lock;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class CheckPWActivity extends AppCompatActivity {
    private String beforePW, inputPW;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_pw);

        TextInputLayout txtLayout_pw = findViewById(R.id.txtLayout_pw);
        EditText et_pw_input = findViewById(R.id.et_pw_input);
        Button btnDone = findViewById(R.id.btn_done);

        SharedPreferences pref = getSharedPreferences("LOCK", 0);
        beforePW = pref.getString("pw", "");

        btnDone.setOnClickListener(view -> {
            inputPW = et_pw_input.getText().toString();
            if (inputPW.length() <= 0)
            {
                txtLayout_pw.setError("Wrong!");
            }else if (beforePW.equals(inputPW))
            {
                SharedPreferences.Editor editor = pref.edit();
                editor.putBoolean("isLocked", false);
                editor.commit();
                Intent mainIntent = new Intent(this, MainActivity.class);
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);    // 이전 액티비티 삭제
                mainIntent.putExtra("noLock", false);
                startActivity(mainIntent);
                Toast.makeText(this, "잠금을 초기화 했습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
