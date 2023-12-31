package com.mastercoding.apiit.ui.notice;

import android.annotation.SuppressLint;

import android.content.Context;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.mastercoding.apiit.R;

import java.util.ArrayList;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.NoticeViewAdapter> {

    private final Context context;
    private final ArrayList<NoticeData> list;

    public NoticeAdapter(Context context, ArrayList<NoticeData> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public NoticeViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.neewsfeed_item_layout, parent, false);


        return new NoticeViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeViewAdapter holder, @SuppressLint("RecyclerView") int position) {

        NoticeData currentItem = list.get(position);

        holder.NoticeTitle.setText(currentItem.getTitle());
        holder.date.setText(currentItem.getDate());
        holder.time.setText(currentItem.getTime());


        try {
            if (currentItem.getImage() != null)
                Glide.with(context).load(currentItem.getImage()).into(holder.NoticeImage);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    public static class NoticeViewAdapter extends RecyclerView.ViewHolder {


        private final TextView NoticeTitle;
        private final TextView date;
        private final TextView time;
        private final ImageView NoticeImage;




        public NoticeViewAdapter(@NonNull View itemView) {
            super(itemView);

            NoticeTitle = itemView.findViewById(R.id.NoticeTitle);
            NoticeImage = itemView.findViewById(R.id.NoticeImage);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
        }
    }


}
