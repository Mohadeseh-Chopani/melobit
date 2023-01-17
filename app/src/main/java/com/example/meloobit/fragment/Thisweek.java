package com.example.meloobit.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.meloobit.R;
import com.example.meloobit.Request;
import com.example.meloobit.adapter.MelobitAdapterTophits;
import com.example.meloobit.manager.RequestManager;
import com.example.meloobit.models.MelobitData;

import java.util.List;


public class Thisweek extends Fragment {

    RecyclerView rv_thisweek;
    ProgressDialog dialog;
    RequestManager manager;
    Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_thisweek, container, false);
        rv_thisweek = view.findViewById(R.id.rv_thiswik);
        context = view.getContext();
        dialog = new ProgressDialog(context);
        dialog.setTitle("Loading...⌛");
        manager = new RequestManager(context);
        manager.getFixture_thisweek(l);

        return view;
    }

    final Request l = new Request() {
        @Override
        public void didFetch(List<MelobitData> list, String status) {
            dialog.dismiss();
            rv_thisweek.setHasFixedSize(true);
            rv_thisweek.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL , false));
            MelobitAdapterTophits adapter = new MelobitAdapterTophits(context, list);
            rv_thisweek.setAdapter(adapter);
        }

        @Override
        public void didError(String status) {
            dialog.dismiss();
            Toast.makeText(context, status, Toast.LENGTH_SHORT).show();
        }
    };
}