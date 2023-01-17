package com.example.meloobit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.meloobit.adapter.MelobitAdapter;
import com.example.meloobit.fragment.Search;
import com.example.meloobit.fragment.Top_hits;
import com.example.meloobit.manager.RequestManager;
import com.example.meloobit.models.MelobitData;
import com.example.meloobit.models.MelobitResponse;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button tophits;
    ImageView search;

    RecyclerView recyclerviewnew,recyclerviewremix;
    ProgressDialog dialog;
    RequestManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tophits = findViewById(R.id.bt_top);
        search = findViewById(R.id.bt_search);

        recyclerviewnew = findViewById(R.id.rcy_newsong);
        recyclerviewremix =findViewById(R.id.rcy_remixsong);

        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading...⌛");

        tophits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment frag = new Top_hits();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.homelayout, frag).commit();
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment frag = new Search();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.homelayout, frag).commit();
            }
        });

        manager = new RequestManager(this);
        manager.getFixture(listener);

        manager.getFixture_trendartist(l);


    }
    private final Request l = new Request() {
        @Override
        public void didFetch(List<MelobitData> list, String status) {
            dialog.dismiss();
            recyclerviewremix.setHasFixedSize(true);
            recyclerviewremix.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL , false));
            MelobitAdapter adapter = new MelobitAdapter(MainActivity.this, list);
            recyclerviewremix.setAdapter(adapter);
        }

        @Override
        public void didError(String status) {
            dialog.dismiss();
            Toast.makeText(MainActivity.this, status, Toast.LENGTH_SHORT).show();
        }
    };
    private final ResponseListener listener = new ResponseListener() {

        @Override
        public void didFetch(List<MelobitData> list, String status) {
            dialog.dismiss();
            recyclerviewnew.setHasFixedSize(true);
            recyclerviewnew.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL , false));
            MelobitAdapter adapter = new MelobitAdapter(MainActivity.this, list);
            recyclerviewnew.setAdapter(adapter);
        }

        @Override
        public void didError(String status) {
            dialog.dismiss();
            Toast.makeText(MainActivity.this, status, Toast.LENGTH_SHORT).show();
        }

    };

}

