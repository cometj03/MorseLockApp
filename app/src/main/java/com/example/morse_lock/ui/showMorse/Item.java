package com.example.morse_lock.ui.showMorse;

import android.graphics.drawable.Drawable;

public class Item {
    private Drawable iconDrawable;
    private String morseTxt;

    public Drawable getIconDrawable() {
        return iconDrawable;
    }

    public void setIconDrawable(Drawable iconDrawable) {
        this.iconDrawable = iconDrawable;
    }

    public String getMorseTxt() {
        return morseTxt;
    }

    public void setMorseTxt(String morseTxt) {
        this.morseTxt = morseTxt;
    }
}
