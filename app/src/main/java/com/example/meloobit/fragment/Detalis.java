package com.example.meloobit.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meloobit.MainActivity;
import com.example.meloobit.R;
import com.example.meloobit.ResponseListener;
import com.example.meloobit.Song;
import com.example.meloobit.adapter.MelobitAdapterThisweek;
import com.example.meloobit.manager.RequestManager;
import com.example.meloobit.models.MelobitData;

import java.io.IOException;
import java.util.List;


public class Detalis extends Fragment {
    ImageView back;
    TextView lyrics;
    ProgressDialog dialog;
    RequestManager manager;
    Context context;
    int position;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detalis, container, false);

        int position = getArguments().getInt("position");
        context = view.getContext();
        back = view.findViewById(R.id.back_detalis);
        lyrics = view.findViewById(R.id.txt_lyrics);

        dialog = new ProgressDialog(context);
        dialog.setTitle("Loading...⌛");
        manager = new RequestManager(context);
        manager.getFixture_detalissong(l);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        return view;
    }

    final ResponseListener l = new ResponseListener() {
        @Override
        public void didFetch(List<MelobitData> list, String status) {
            dialog.dismiss();

            try {
                lyrics.setText(list.get(position).lyrics);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void didError(String status) {
            dialog.dismiss();
            Toast.makeText(context, status, Toast.LENGTH_SHORT).show();
        }
    };
}