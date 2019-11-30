package com.example.morse_lock.ui.LockSet;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import com.example.morse_lock.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class MorseSetActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_morse_set);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Set Up");
        actionBar.setDisplayHomeAsUpEnabled(true);

        Button confirm_btn = findViewById(R.id.btn_confirm);
        confirm_btn.setOnClickListener(v -> startActivity(new Intent(MorseSetActivity.this, PWSetActivity.class)));
    }

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
