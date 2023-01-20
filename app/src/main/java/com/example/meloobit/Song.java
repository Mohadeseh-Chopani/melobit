package com.example.meloobit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meloobit.adapter.MelobitAdapterThisweek;
import com.example.meloobit.adapter.ViewPagerAdapter;
import com.example.meloobit.fragment.Detalis;
import com.example.meloobit.fragment.Top_hits;
import com.example.meloobit.manager.RequestManager;
import com.example.meloobit.models.MelobitData;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

public class Song extends AppCompatActivity {
    ImageView image_cover,play,previous,next,back;
    ProgressDialog dialog;
    RequestManager manager;
    String url_cover,name_song,url_song,out,out2,result;;
    TextView namesong, pass, due, lyrics,nameartist;
    SeekBar seekBar;
    MediaPlayer mediaplayer = new MediaPlayer();;
    Integer difference;
    Handler handler = new Handler();
    int position;
    int size;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);

        Intent i = getIntent();
        result = i.getStringExtra("result");
        position = i.getIntExtra("position",0);
        size = i.getIntExtra("size",0);

        manager = new RequestManager(this);

        Toast.makeText(Song.this, result, Toast.LENGTH_SHORT).show();

        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading...⌛");


        if (result.equals("week")){
            manager.getFixture_thisweek(l);
        }else if (result.equals("today")) {
            manager.getFixture_topday(l);
        }else if (result.equals("new")){
            manager.getFixture(l);
        } else if (result.equals("latest")){
            manager.getFixture_slider(l2);
        }



        mediaplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        image_cover = findViewById(R.id.image_cover);
        namesong = findViewById(R.id.name_song);
        play = findViewById(R.id.play);
        seekBar = findViewById(R.id.seekBar);
        pass = findViewById(R.id.time_pass);
        due = findViewById(R.id.time_due);
        previous = findViewById(R.id.previous);
        next = findViewById(R.id.next);
        back = findViewById(R.id.back);
        lyrics = findViewById(R.id.lyrics_song);
        nameartist = findViewById(R.id.name_artist);




        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    mediaplayer.seekTo(progress*1000);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaplayer.isPlaying()) {
                    mediaplayer.pause();
                    initializeSeekbar();
                    play.setImageResource(R.drawable.icon_play);
                }else {
                    mediaplayer.start();
                    initializeSeekbar();
                    play.setImageResource(R.drawable.icon_pause);
                }

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        lyrics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment frag = new Detalis();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

                Bundle data = new Bundle();
                data.putInt("position", position);
                frag.setArguments(data);

                fragmentTransaction.replace(R.id.layout_song, frag).commit();
                Toast.makeText(Song.this, "lyrics", Toast.LENGTH_SHORT).show();


//                Fragment argumentFragment = new ArgumentFragment();//Get Fragment Instance
//                Bundle data = new Bundle();//Use bundle to pass data
//                data.putString("data", "This is Argument Fragment");//put string, int, etc in bundle with a key value
//                argumentFragment.setArguments(data);//Finally set argument bundle to fragment
//                fragmentManager.beginTransaction().replace(R.id.fragmentContainer, argumentFragment).commit();

            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaplayer.stop();
                Intent intent = new Intent(Song.this, MainActivity.class);
                view.getContext().startActivity(intent);
            }
        });
    }



    private final ResponseListener l = new ResponseListener() {

        @Override
        public void didFetch(List<MelobitData> list, String status) {
            dialog.dismiss();
            Picasso.get().load(list.get(position).image.cover_small.url).into(image_cover);
            namesong.setText(list.get(position).title);
            nameartist.setText(list.get(position).fullName);
            try {
                mediaplayer.setDataSource(list.get(position).audio.medium.url);
                mediaplayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void didError(String status) {
            dialog.dismiss();
            Toast.makeText(Song.this, status, Toast.LENGTH_SHORT).show();
        }
    };

    private final ResponseListenerSlider l2 = new ResponseListenerSlider() {

        @Override
        public void didFetch(List<MelobitData> list, String status) {
            dialog.dismiss();
            Picasso.get().load(list.get(position).image.cover_small.url).into(image_cover);
            namesong.setText(list.get(position).title);
            nameartist.setText(list.get(position).fullName);
            try {
                mediaplayer.setDataSource(list.get(position).audio.medium.url);
                mediaplayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void didError(String status) {
            dialog.dismiss();
            Toast.makeText(Song.this, status, Toast.LENGTH_SHORT).show();
        }

    };



    private void initializeSeekbar() {
        seekBar.setMax(mediaplayer.getDuration()/1000);
        Song.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(mediaplayer != null){
                    int currentPosition=mediaplayer.getCurrentPosition()/1000;
                    seekBar.setProgress(currentPosition);
                    out=String.format("%02d:%02d",seekBar.getProgress()/60,seekBar.getProgress()%60);

                    pass.setText(out);
                    difference=mediaplayer.getDuration()/1000-mediaplayer.getCurrentPosition()/1000;
                    out2=String.format("%02d:%02d",difference/60,difference%60);
                    due.setText(out2);
                }
                handler.postDelayed(this,1000);

            }
        });
    }





}