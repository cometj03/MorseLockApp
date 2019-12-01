package com.example.morse_lock.ui.LockSet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.morse_lock.MainActivity;
import com.example.morse_lock.R;
import com.google.android.material.textfield.TextInputLayout;

import java.time.chrono.MinguoChronology;
import java.util.Set;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class PWSetActivity extends AppCompatActivity {
    private String morseCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pw_set);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("PW set");
        actionBar.setDisplayHomeAsUpEnabled(true);

        // 모스 코드 값 받기
        Intent getIntent = getIntent();
        morseCode = getIntent.getExtras().getString("MorseCode");
        Toast.makeText(this, "받은 코드 : " + morseCode, Toast.LENGTH_SHORT).show();

        TextInputLayout txtLayout_pw = findViewById(R.id.txtLayout_pw);
        EditText et_pw_input = findViewById(R.id.et_pw_input);
        Button btnDone = findViewById(R.id.btn_done);
        et_pw_input.requestFocus();
        // 키보드 보이게 하는 부분
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        String pw = et_pw_input.getText().toString();
        btnDone.setOnClickListener(v -> {
            SharedPreferences pref = getSharedPreferences("LOCK", 0);
            if (pw.length() <= 0)
                txtLayout_pw.setError("PW is NULL");
            else if (pw.length() < 4)
                txtLayout_pw.setError("at least 4");
            else {
                // pw 저장
                txtLayout_pw.setErrorEnabled(false);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("pw", pw);
                editor.commit();
                Toast.makeText(this, "설정 완료", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(PWSetActivity.this, MainActivity.class));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                // 키보드 숨기기
                InputMethodManager immhide = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                immhide.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
