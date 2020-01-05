package com.example.morse_lock.ui.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.morse_lock.R;
import com.example.morse_lock.ui.showMorse.ShowMorseActivity;

import static com.example.morse_lock.ui.settings.SettingsActivity.StringToBitMap;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    static ImageView img_profile;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        //homeViewModel.getText().observe(this, s -> textView.setText(s));

        img_profile = root.findViewById(R.id.profile_picture);
        Button btn_show = root.findViewById(R.id.btn_show);
        TextView txt_welcome = root.findViewById(R.id.txt_welcome);
        TextView txt_email = root.findViewById(R.id.txt_email);

        SharedPreferences pref = this.getActivity().getSharedPreferences("PROFILE", 0);
        //String profileString = pref.getString("profile_img", "");
        String name = pref.getString("username", "");
        String email = pref.getString("email", "");
        //if (profileString.length() > 0)
        //    img_profile.setImageBitmap(StringToBitMap(profileString));
        //else
        //    img_profile.setImageDrawable(getResources().getDrawable(R.mipmap.ic_launcher_round, getActivity().getApplicationContext().getTheme()));

        if (name.length() > 0)
            txt_welcome.setText(name + "님 환영합니다.");
        else
            txt_welcome.setText("username 을 설정해주세요.");
        if (email.length() > 0)
            txt_email.setText("(" + email + ")");
        else
            txt_email.setText("이메일을 설정해주세요.");

        btn_show.setOnClickListener(view -> startActivity(new Intent(getActivity(), ShowMorseActivity.class))); //.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        return root;
    }
}