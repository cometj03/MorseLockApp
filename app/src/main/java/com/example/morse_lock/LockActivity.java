package com.example.morse_lock;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.morse_lock.ui.LockSet.MorseSetActivity;
import com.google.android.material.textfield.TextInputLayout;

public class LockActivity extends AppCompatActivity {
    private Button btn_input, btn_clear;
    private String morsePW, morse;
    private EditText et_input;
    private TextView txt_forget;
    private TextInputLayout txtLayout_morse;
    private boolean isBtnDown, addAble;
    private double howLong, first = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("App is Locked");
        actionBar.setBackgroundDrawable(new ColorDrawable(getColor(R.color.colorAccent)));

        btn_input = findViewById(R.id.btn_input);
        btn_clear = findViewById(R.id.btn_clear);
        txt_forget = findViewById(R.id.txt_forget);
        et_input = findViewById(R.id.et_morse_input);
        txtLayout_morse = findViewById(R.id.txtLayout_morse);
        et_input.setClickable(false);
        et_input.setFocusable(false); // EditText 비활성화

        SharedPreferences pref = getSharedPreferences("LOCK", 0);
        morse = pref.getString("morsePW", "");
        if (morse.length() <= 0)
            finish();

        btn_input.setOnTouchListener(onButtonTouchListener);
        btn_clear.setOnClickListener(view -> clear());

        txt_forget.setOnClickListener(view -> startActivity(new Intent(this, CheckPWActivity.class)));
    }

    private void clear() {
        morsePW = "";
        et_input.setText(morsePW);
        first = 0;
        howLong = 0;
    }

    private void onBtnDown() {
        TouchThread kThread = new TouchThread();
        kThread.start();
    }

    private class TouchThread extends Thread {
        @Override
        public void run() {
            super.run();
            while(isBtnDown)
            {
                try {
                    Thread.sleep(5);
                    howLong += 0.005;
                    if (first != 0) // 처음이 아닐 때
                        if (howLong >= first * 1.3 && addAble) // 길게 누른 상태이면
                        {
                            isBtnDown = false;
                            morsePW += "-";
                            addAble = false;    // 추가 불가능
                            howLong = 0;
                            et_input.setText(morsePW);
                        }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private View.OnTouchListener onButtonTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    txtLayout_morse.setErrorEnabled(false);
                    isBtnDown = true;
                    addAble = true;
                    howLong = 0;
                    onBtnDown();
                    break;

                case MotionEvent.ACTION_UP:
                    isBtnDown = false;
                    if (addAble)    // 추가 가능하면
                    {
                        if (first == 0)
                        {
                            morsePW = "·";
                            first = howLong;
                        }else
                            morsePW += "·";
                    }
                    addAble = false;
                    et_input.setText(morsePW);
                    howLong = 0;
                    if (morsePW.length() >= morse.length())
                    {
                        if (morse.equals(morsePW))
                        {
                            Toast.makeText(LockActivity.this, "잠금을 해제했습니다.", Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            txtLayout_morse.setError("Wrong!");
                            clear();
                        }
                    }
                    break;
                default:
                    break;
            }
            return false;
        }
    };

    // 뒤로가기 버튼 누르면 꺼버리기
    @Override
    public void onBackPressed() {
        finishAffinity();
        System.runFinalization();
        System.exit(0);
    }
}
