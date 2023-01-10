package com.example.meloobit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meloobit.MainActivity;
import com.example.meloobit.R;
import com.example.meloobit.models.MelobitData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MelobitAdapter extends RecyclerView.Adapter<MelobitViewHolder>{
    Context context;
    List<MelobitData> list;

    public MelobitAdapter(Context context, List<MelobitData>list){this.context = context; this.list = list;}

    @NonNull
    @Override
    public MelobitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MelobitViewHolder(LayoutInflater.from(context).inflate(R.layout.rv_item , parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull MelobitViewHolder holder, int position) {
        final MelobitData data = list.get(position);
        Picasso.get().load(String.valueOf(data.results.get(position).image.cover_small)).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class MelobitViewHolder extends RecyclerView.ViewHolder{
    ImageView imageView;
    public MelobitViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.image_song1);
    }
}