package com.example.morse_lock.ui.showMorse;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.morse_lock.R;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ShowMorseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_morse_activity);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Morse code table");
        actionBar.setDisplayHomeAsUpEnabled(true);

        String arr = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        int[] morse_icon = { R.drawable.morse_a,  R.drawable.morse_b, R.drawable.morse_c, R.drawable.morse_d, R.drawable.morse_e, R.drawable.morse_f, R.drawable.morse_g,
                R.drawable.morse_h, R.drawable.morse_i, R.drawable.morse_j, R.drawable.morse_k, R.drawable.morse_l, R.drawable.morse_m, R.drawable.morse_n, R.drawable.morse_o,
                R.drawable.morse_p, R.drawable.morse_q, R.drawable.morse_r, R.drawable.morse_s, R.drawable.morse_t, R.drawable.morse_u, R.drawable.morse_v, R.drawable.morse_w,
                R.drawable.morse_x, R.drawable.morse_y, R.drawable.morse_z, R.drawable.morse_1, R.drawable.morse_2, R.drawable.morse_3, R.drawable.morse_4, R.drawable.morse_5,
                R.drawable.morse_6, R.drawable.morse_7, R.drawable.morse_8, R.drawable.morse_9, R.drawable.morse_0};  // 아이콘 추가한 후 노가다로 여기 채워야 함
        int len = arr.length();
        ArrayList<Item> data = new ArrayList<>();
        for (int i = 0; i <len; i++)
        {
            // data 에 값 입력
            data.add(addItem(getResources().getDrawable(morse_icon[i], getApplicationContext().getTheme()), String.valueOf(arr.charAt(i))));
        }

        RecyclerView recyclerView = findViewById(R.id.recycler1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MorseAdapter morseAdapter = new MorseAdapter(data);
        recyclerView.setAdapter(morseAdapter);
    }

    public Item addItem(Drawable icon, String morse)
    {
        Item item = new Item();
        item.setIconDrawable(icon);
        item.setMorseTxt(morse);
        return item;
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
