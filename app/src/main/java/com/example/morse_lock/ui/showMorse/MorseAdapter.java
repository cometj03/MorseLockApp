package com.example.morse_lock.ui.showMorse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.morse_lock.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MorseAdapter extends RecyclerView.Adapter<MorseAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView morseTxt;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            morseTxt = itemView.findViewById(R.id.txt_morse);
            imageView = itemView.findViewById(R.id.img_morse);
        }
    }

    private ArrayList<Item> mData = null;
    MorseAdapter(ArrayList<Item> list) { mData = list; }

    @Override
    public MorseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.recycler_item, parent, false);
        return new MorseAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = mData.get(position);
        holder.morseTxt.setText(item.getMorseTxt());
        holder.imageView.setImageDrawable(item.getIconDrawable());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
