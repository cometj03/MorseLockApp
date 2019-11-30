package com.example.morse_lock.ui.settings;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.morse_lock.R;

import java.io.InputStream;

public class SettingsFragment extends Fragment {

    Context mBase;
    private SettingsViewModel settingsViewModel;
    ImageView profile;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        settingsViewModel =
                ViewModelProviders.of(this).get(SettingsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_settings, container, false);
        //final TextView textView = root.findViewById(R.id.text_settings);
        //settingsViewModel.getText().observe(this, s -> textView.setText(s));
        profile = root.findViewById(R.id.profile_picture);
        settingsViewModel.getText().observe(this, s ->
            profile.setOnClickListener(v ->
                startActivityForResult(new Intent(Intent.ACTION_GET_CONTENT).setType("image/*"), 1002)));
        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1002)
        {
            try {
                InputStream i = getContentResolver().openInputStream(data.getData());
                Bitmap img = BitmapFactory.decodeStream(i);
                i.close();
                settingsViewModel.getText().observe(this, s -> profile.setImageBitmap(img));
            }catch (Exception ignored) {}
        }
    }

    private ContentResolver getContentResolver() {
        return mBase.getContentResolver();
    }
}