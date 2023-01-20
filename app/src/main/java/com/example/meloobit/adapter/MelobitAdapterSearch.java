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

public class MelobitAdapterSearch extends RecyclerView.Adapter<MelobitViewHolderSearch>{
    Context context;
    List<MelobitData> list;

    public MelobitAdapterSearch(Context context, List<MelobitData> list) {
        this.context=context;
        this.list=list;

    }

    @NonNull
    @Override
    public MelobitViewHolderSearch onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MelobitViewHolderSearch(LayoutInflater.from(context).inflate(R.layout.rv_item_search,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MelobitViewHolderSearch holder, int position) {
        Picasso.get().load(list.get(position).image.cover_small.url).into(holder.image_search);
        holder.name_song_search.setText(list.get(position).title);
        holder.count_follower.setText(list.get(position).followersCount);

        int Position = position;

        holder.image_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Song.class);
                intent.putExtra("position",Position);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
class MelobitViewHolderSearch extends RecyclerView.ViewHolder{

    ImageView image_search;
    TextView name_song_search,count_follower;

    public MelobitViewHolderSearch(@NonNull View itemView) {
        super(itemView);
        image_search=itemView.findViewById(R.id.image_search);
        name_song_search=itemView.findViewById(R.id.name_song_search);
        count_follower=itemView.findViewById(R.id.count_follower);
    }
}
