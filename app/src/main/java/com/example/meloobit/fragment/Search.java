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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.meloobit.ProgressDialogcustom;
import com.example.meloobit.R;
import com.example.meloobit.ResponseListener;
import com.example.meloobit.adapter.MelobitAdapterSearch;
import com.example.meloobit.adapter.MelobitAdapterThisweek;
import com.example.meloobit.manager.RequestManager;
import com.example.meloobit.models.MelobitData;

import java.util.List;

public class Search extends Fragment {

    EditText edt_search;
    Button btn_search;
    RecyclerView rv_search;
    RequestManager manager;
    ProgressDialog dialog;
    ProgressDialogcustom dialog1;
    Context context;
    String query;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_search, container, false);

        rv_search = view.findViewById(R.id.rv_search);
        btn_search=view.findViewById(R.id.btn_search);
        edt_search=view.findViewById(R.id.edt_search);
        context = view.getContext();

        manager=new RequestManager(context);
        dialog1 = new ProgressDialogcustom(context);

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                query = edt_search.getText().toString().trim();
                query = "Masood Saeedi";
                query.replace(" ","%20");
                if(!query.equals("")){
                    manager.getFixture_search(listener,query);
                    dialog1.show();
                }

                edt_search.setText(" ");

            }
        });
        return view;
    }

    private final ResponseListener listener = new ResponseListener() {

        @Override
        public void didFetch(List<MelobitData> list, String status) {
            dialog1.dismiss();
            rv_search.setHasFixedSize(true);
            rv_search.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL , false));
            MelobitAdapterSearch adapter = new MelobitAdapterSearch(context, list);
            rv_search.setAdapter(adapter);
        }

        @Override
        public void didError(String status) {
            dialog1.dismiss();
            Toast.makeText(context,status, Toast.LENGTH_SHORT).show();
        }
    };


}