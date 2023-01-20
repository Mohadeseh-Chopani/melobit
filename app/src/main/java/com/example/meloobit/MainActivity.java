package com.example.meloobit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.meloobit.adapter.MelobitAdapterNewsong;
import com.example.meloobit.adapter.MelobitAdapterTrendartist;
import com.example.meloobit.adapter.ViewPagerAdapter;
import com.example.meloobit.fragment.Search;
import com.example.meloobit.fragment.Top_hits;
import com.example.meloobit.manager.RequestManager;
import com.example.meloobit.models.MelobitData;

import java.util.List;

public class MainActivity extends AppCompatActivity{
    Button tophits;
    ImageView search,home;
    ViewPager vp_slider;
    RecyclerView recyclerviewnew,recyclerviewremix;
    ProgressDialogcustom dialog1;
    RequestManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vp_slider = findViewById(R.id.view_pager_slider);



        tophits = findViewById(R.id.bt_top);
        search = findViewById(R.id.bt_search);
        home =findViewById(R.id.bt_home);


        recyclerviewnew = findViewById(R.id.rcy_newsong);
        recyclerviewremix =findViewById(R.id.rcy_remixsong);

//        dialog = new ProgressDialog(this);
//        dialog.setTitle("Loading...⌛");

        dialog1 = new ProgressDialogcustom(MainActivity.this);
        dialog1.show();

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,MainActivity.class));
            }
        });
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
        manager.getFixture(l1);
        manager.getFixture_trendartist(l);
        manager.getFixture_slider(l2);


    }
    private final ResponsLisrenerTerndArtist l = new ResponsLisrenerTerndArtist() {
        @Override
        public void didFetch(List<MelobitData> list, String status) {
            dialog1.dismiss();
            recyclerviewremix.setHasFixedSize(true);
            recyclerviewremix.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL , false));
            MelobitAdapterTrendartist adapter = new MelobitAdapterTrendartist(MainActivity.this, list);
            recyclerviewremix.setAdapter(adapter);
        }

        @Override
        public void didError(String status) {
            dialog1.dismiss();
            Toast.makeText(MainActivity.this, status, Toast.LENGTH_SHORT).show();
        }
    };
    private final ResponseListener l1 = new ResponseListener() {

        @Override
        public void didFetch(List<MelobitData> list, String status) {
            dialog1.dismiss();
            recyclerviewnew.setHasFixedSize(true);
            recyclerviewnew.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL , false));
            MelobitAdapterNewsong adapter = new MelobitAdapterNewsong(MainActivity.this, list);
            recyclerviewnew.setAdapter(adapter);
        }

        @Override
        public void didError(String status) {
            dialog1.dismiss();
            Toast.makeText(MainActivity.this, status, Toast.LENGTH_SHORT).show();
        }

    };

    private final ResponseListenerSlider l2 = new ResponseListenerSlider() {

        @Override
        public void didFetch(List<MelobitData> list, String status) {
            ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(MainActivity.this, list);
            vp_slider.setAdapter(viewPagerAdapter);
        }

        @Override
        public void didError(String status) {
            dialog1.dismiss();
            Toast.makeText(MainActivity.this, status, Toast.LENGTH_SHORT).show();
        }

    };

}

