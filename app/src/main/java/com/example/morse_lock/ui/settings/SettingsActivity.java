package com.example.morse_lock.ui.settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.morse_lock.MainActivity;
import com.example.morse_lock.R;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    private ImageView profile;
    private String profileString, name, email;
    private EditText et_name, et_email;
    private SharedPreferences pref;
    private Bitmap img;
    private Boolean isChanged;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("settings");
        actionBar.setDisplayHomeAsUpEnabled(true);

        Button btn_reset = findViewById(R.id.btn_profile_reset);
        profile = findViewById(R.id.profile_picture);
        et_name = findViewById(R.id.et_name);
        et_email = findViewById(R.id.et_email);

        isChanged = false;  // 이미지 안 바뀜
        pref = getSharedPreferences("PROFILE", 0);
        profileString = pref.getString("profile_img", "");
        name = pref.getString("username", "");
        email = pref.getString("email", "");
        if (profileString.length() > 0)
            profile.setImageBitmap(StringToBitMap(profileString));
        else
            profile.setImageDrawable(getResources().getDrawable(R.mipmap.ic_launcher_round, getApplicationContext().getTheme()));
        if (name.length() > 0)
            et_name.setText(name);
        if (email.length() > 0)
            et_email.setText(email);

        profile.setOnClickListener(v -> startActivityForResult(new Intent(Intent.ACTION_GET_CONTENT).setType("image/*"), 1002));
        btn_reset.setOnClickListener(view -> {
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("profile_img", "");
            editor.putString("username", "");
            editor.putString("email", "");
            editor.commit();
            Toast.makeText(this, "프로필을 초기화 했습니다.", Toast.LENGTH_SHORT).show();
            profile.setImageDrawable(getResources().getDrawable(R.mipmap.ic_launcher_round, getApplicationContext().getTheme()));
            et_name.setText("");
            et_email.setText("");
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1002)
        {
            try {
                InputStream i = getContentResolver().openInputStream(data.getData());
                img = BitmapFactory.decodeStream(i);
                i.close();
                profile.setImageBitmap(img);
                isChanged = true;
            } catch (Exception ignored) {}
        }
    }

    // Bitmap -> String 변환
    public static String BitMapToString(Bitmap img) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        img.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        return imageEncoded;
    }

    // String -> Bitmap 변환
    public static Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch (Exception e){
            e.getMessage();
            return null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_confirm, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{    //toolbar의 back키 눌렀을 때 동작
                Intent mainIntent = new Intent(this, MainActivity.class);
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);    // 이전 액티비티 삭제
                mainIntent.putExtra("noLock", false);
                startActivity(mainIntent);
                return true;
            }
            case R.id.action_confirm:{  //toolbar의 check키 눌렀을 때 동작
                // 저장하는 코드
                name = et_name.getText().toString();
                email = et_email.getText().toString();
                SharedPreferences.Editor editor = pref.edit();
                if (isChanged)
                    editor.putString("profile_img", BitMapToString(img));
                editor.putString("username", name);
                editor.putString("email", email);
                editor.commit();
                Toast.makeText(this, "저장되었습니다.", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
