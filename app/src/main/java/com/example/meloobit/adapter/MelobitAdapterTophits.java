package com.example.meloobit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meloobit.R;
import com.example.meloobit.models.MelobitData;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MelobitAdapterTophits extends RecyclerView.Adapter<MelobitViewHolderTophits>{
    Context context;
    List<MelobitData> list;

    public MelobitAdapterTophits(Context context, List<MelobitData>list){this.context = context; this.list = list;}

    @NonNull
    @Override
    public MelobitViewHolderTophits onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MelobitViewHolderTophits(LayoutInflater.from(context).inflate(R.layout.rv_item_tophits , parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull MelobitViewHolderTophits holder, int position) {
        Picasso.get().load(list.get(position).image.cover_small.url).into(holder.imageView);
        holder.namesong.setText(list.get(position).title);
        holder.nameartist.setText(list.get(position).artists.get(0).fullName);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}

class MelobitViewHolderTophits extends RecyclerView.ViewHolder{
    ImageView imageView;
    TextView namesong,nameartist;

    public MelobitViewHolderTophits(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.image_song2);
        nameartist = itemView.findViewById(R.id.nameartist);
        namesong = itemView.findViewById(R.id.nanesong);
    }
}