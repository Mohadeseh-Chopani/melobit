package com.example.meloobit.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meloobit.R;
import com.example.meloobit.Song;
import com.example.meloobit.models.MelobitData;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MelobitAdapterToday extends RecyclerView.Adapter<MelobitViewHolderToday>{
    Context context;
    List<MelobitData> list;

    public MelobitAdapterToday(Context context, List<MelobitData>list){this.context = context; this.list = list;}

    @NonNull
    @Override
    public MelobitViewHolderToday onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MelobitViewHolderToday(LayoutInflater.from(context).inflate(R.layout.rv_item_tophits , parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull MelobitViewHolderToday holder, int position) {
        Picasso.get().load(list.get(position).image.cover_small.url).into(holder.imageView);
        holder.namesong.setText(list.get(position).title);
        holder.nameartist.setText(list.get(position).artists.get(0).fullName);

        String result = "today";
        int Position = position;
        int size = list.size();

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Song.class);
                intent.putExtra("result",result);
                intent.putExtra("position",Position);
                intent.putExtra("size",size);
                view.getContext().startActivity(intent);
            }
        });
    }



    @Override
    public int getItemCount() {
        return list.size();
    }
}

class MelobitViewHolderToday extends RecyclerView.ViewHolder {
    ImageView imageView;
    TextView namesong, nameartist, id;

    public MelobitViewHolderToday(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.image_song2);
        nameartist = itemView.findViewById(R.id.nameartist);
        namesong = itemView.findViewById(R.id.nanesong);
    }
}
