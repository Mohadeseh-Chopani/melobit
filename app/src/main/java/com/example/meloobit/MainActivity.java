package com.example.meloobit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.meloobit.adapter.MelobitAdapter;
import com.example.meloobit.fragment.Search;
import com.example.meloobit.fragment.Top_hits;
import com.example.meloobit.fragment.Top_hits;
import com.example.meloobit.manager.RequestManager;
import com.example.meloobit.models.MelobitResponse;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button tophits;
    ImageView search;

    RecyclerView recyclerview;
    ProgressDialog dialog;
    RequestManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tophits = findViewById(R.id.bt_top);
        search = findViewById(R.id.bt_search);


        recyclerview = findViewById(R.id.rcy_newsong);
        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading...⌛");
        manager = new RequestManager(this);
        manager.getFixture(listener);

        tophits = findViewById(R.id.bt_top);
        search = findViewById(R.id.bt_search);


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



    }
    private final ResponseListener listener = new ResponseListener() {

        @Override
        public void didFetch(MelobitResponse response, String status) {
            dialog.dismiss();

            recyclerview.setHasFixedSize(true);
            recyclerview.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL , false));
            MelobitAdapter adapter = new MelobitAdapter(MainActivity.this, response.data);
            recyclerview.setAdapter(adapter);
        }

        @Override
        public void didError(String status) {
            dialog.dismiss();
            Toast.makeText(MainActivity.this, status, Toast.LENGTH_SHORT).show();
        }
    };


}

