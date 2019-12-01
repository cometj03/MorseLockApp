package com.example.morse_lock.ui.LockSet;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.Touch;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.morse_lock.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class MorseSetActivity extends AppCompatActivity {
    private Button btn_confirm, btn_input, btn_clear;
    private EditText et_input;
    private boolean isBtnDown, addAble;
    private double howLong = 0, first = 0;
    String morsePW = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_morse_set);

        Intent getTitle = getIntent();
        String title = getTitle.getExtras().getString("title");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(title);
        actionBar.setDisplayHomeAsUpEnabled(true);

        btn_confirm = findViewById(R.id.btn_confirm);
        btn_input = findViewById(R.id.btn_input);
        et_input = findViewById(R.id.et_morse_input);
        btn_clear = findViewById(R.id.btn_clear);
        et_input.setClickable(false);
        et_input.setFocusable(false); // EditText 비활성화

        Intent myIntent =  new Intent(MorseSetActivity.this, PWSetActivity.class);
        btn_confirm.setOnClickListener(v -> {
            myIntent.putExtra("MorseCode", morsePW);
            startActivity(myIntent);
        });
        btn_input.setOnTouchListener(onButtonTouchListener);
        btn_clear.setOnClickListener(view -> {
            morsePW = "";
            et_input.setText(morsePW);
            first = 0;
            howLong = 0;
        });
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
                    {
                        if (howLong >= first * 1.5) // 길게 누른 상태이면
                        {
                            isBtnDown = false;
                            addAble = false;
                            morsePW += "-";
                            et_input.setText(morsePW);
                            break;
                        }
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
                    isBtnDown = true;
                    addAble = true;
                    howLong = 0;
                    onBtnDown();
                    break;

                case MotionEvent.ACTION_UP:
                    isBtnDown = false;
                    if (first == 0)
                    {
                        morsePW += "·";
                        first = howLong;
                    }else if (addAble){
                        morsePW += "·";
                    }
                    et_input.setText(morsePW);
                    howLong = 0;
                    break;
                default:
                    break;
            }
            return false;
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
