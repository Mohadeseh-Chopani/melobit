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
import com.example.meloobit.ResponseListener;
import com.example.meloobit.adapter.MelobitAdapterToday;
import com.example.meloobit.manager.RequestManager;
import com.example.meloobit.models.MelobitData;

import java.util.List;

public class Today extends Fragment {

    RecyclerView rv_topday;
    ProgressDialog dialog;
    RequestManager manager;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_top_day, container, false);
        rv_topday = view.findViewById(R.id.rv_topday);
        context = view.getContext();
        dialog = new ProgressDialog(context);
        dialog.setTitle("Loading...⌛");
        manager = new RequestManager(context);
        manager.getFixture_topday(l);



        return view;



    }
    final ResponseListener l = new ResponseListener() {
        @Override
        public void didFetch(List<MelobitData> list, String status) {
            dialog.dismiss();
            rv_topday.setHasFixedSize(true);
            rv_topday.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL , false));
            MelobitAdapterToday adapter = new MelobitAdapterToday(context, list);
            rv_topday.setAdapter(adapter);

        }

        @Override
        public void didError(String status) {
            dialog.dismiss();
            Toast.makeText(context, status, Toast.LENGTH_SHORT).show();
        }
    };
}