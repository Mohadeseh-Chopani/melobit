package com.example.meloobit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meloobit.fragment.Detalis;
import com.example.meloobit.manager.RequestManager;
import com.example.meloobit.models.MelobitData;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

public class Song extends AppCompatActivity {

    private static final int PERMISSIONS_STORAGE_CODE = 1000;
    ImageView image_cover,play,previous,next,back,download;
    ProgressDialog dialog;
    RequestManager manager;
    String url_cover,name_song,url_song,out,out2,result, urlsong;
    TextView namesong, pass, due, lyrics,nameartist;
    SeekBar seekBar;
    MediaPlayer mediaplayer = new MediaPlayer();;
    Integer difference;
    Handler handler = new Handler();
    int position;
    int size;
    FragmentManager fragmentManager;
    ProgressDialogcustom dialog1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);

        dialog1 = new ProgressDialogcustom(Song.this);
        dialog1.show();



        Intent i = getIntent();
        result = i.getStringExtra("result");
        position = i.getIntExtra("position",0);
        size = i.getIntExtra("size",0);

        manager = new RequestManager(this);

        Toast.makeText(Song.this, result, Toast.LENGTH_SHORT).show();



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
        download = findViewById(R.id.download);





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
            Picasso.get().load(list.get(position).image.cover_small.url).into(image_cover);
            namesong.setText(list.get(position).title);
            nameartist.setText(list.get(position).fullName);
            urlsong = list.get(position).audio.medium.url;
            try {
                mediaplayer.setDataSource(list.get(position).audio.medium.url);
                mediaplayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }

            dialog1.dismiss();


            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mediaplayer.isPlaying()) {
                        mediaplayer.stop();
                        seekBar.setProgress(0);
                        pass.setText("00:00");
                        due.setText("00:00");
                    }
                    position=position+1;
                    Picasso.get().load(list.get(position).image.cover_small.url).into(image_cover);
                    namesong.setText(list.get(position).title);
                    nameartist.setText(list.get(position).fullName);
//                    try {
//                        //  mediaplayer.setDataSource(list.get(position+1).audio.medium.url);
//                        mediaplayer.prepare();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                    initializeSeekbar();
                }
            });

            previous.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mediaplayer.isPlaying()) {
                        mediaplayer.stop();
                        seekBar.setProgress(0);
                        pass.setText("00:00");
                        due.setText("00:00");
                    }
                    position=position-1;
                    Picasso.get().load(list.get(position).image.cover_small.url).into(image_cover);
                    namesong.setText(list.get(position).title);
                    nameartist.setText(list.get(position).fullName);
                    try {
                        mediaplayer.setDataSource(list.get(position).audio.medium.url);
                        mediaplayer.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    initializeSeekbar();
                }
            });

            download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Download(list);
                }
            });
        }

        @Override
        public void didError(String status) {
            Toast.makeText(Song.this, status, Toast.LENGTH_SHORT).show();
            dialog1.dismiss();
        }
    };

    private final ResponseListenerSlider l2 = new ResponseListenerSlider() {

        @Override
        public void didFetch(List<MelobitData> list, String status) {
            Picasso.get().load(list.get(position).image.cover_small.url).into(image_cover);
            namesong.setText(list.get(position).title);
            nameartist.setText(list.get(position).fullName);
            try {
                mediaplayer.setDataSource(list.get(position).audio.medium.url);
                mediaplayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }

            dialog1.dismiss();

            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mediaplayer.isPlaying()) {
                        mediaplayer.stop();
                        seekBar.setProgress(0);
                        pass.setText("00:00");
                        due.setText("00:00");
                    }
                    position=position+1;
                    Picasso.get().load(list.get(position).image.cover_small.url).into(image_cover);
                    namesong.setText(list.get(position).title);
                    nameartist.setText(list.get(position).fullName);
//                    try {
//                        mediaplayer.release();
//                        mediaplayer.setDataSource(list.get(position).audio.medium.url);
//                        mediaplayer.prepare();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                    initializeSeekbar();
                }
            });

            previous.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mediaplayer.isPlaying()) {
                        mediaplayer.stop();
                        seekBar.setProgress(0);
                        pass.setText("00:00");
                        due.setText("00:00");
                    }
                    position=position-1;
                    Picasso.get().load(list.get(position).image.cover_small.url).into(image_cover);
                    namesong.setText(list.get(position).title);
                    nameartist.setText(list.get(position).fullName);
//                    try {
//                        mediaplayer.release();
//                        mediaplayer.setDataSource(list.get(position).audio.medium.url);
//                        mediaplayer.prepare();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                    initializeSeekbar();
                }
            });

            download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Download(list);
                }
            });


        }

        @Override
        public void didError(String status) {
            Toast.makeText(Song.this, status, Toast.LENGTH_SHORT).show();
            dialog1.dismiss();
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

    private void Download(List<MelobitData> list){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED){
                String[] permissions ={Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permissions,PERMISSIONS_STORAGE_CODE);}
            else {
                startDownload(list);
            }
        }
        else{
            startDownload(list);
        }
    }

    private void startDownload(List<MelobitData> list){
        String url =list.get(position).audio.medium.url;
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI |
                DownloadManager.Request.NETWORK_MOBILE);
        request.setDescription("Downloading file....");
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setTitle(list.get(position).title);
        request.setMimeType("audio/MP3");
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,""+System.currentTimeMillis());
        DownloadManager manager=(DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);

    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults, List<MelobitData> list) {
        switch (requestCode){
            case PERMISSIONS_STORAGE_CODE:{
                if(grantResults.length >0 && grantResults[0]==
                        PackageManager.PERMISSION_GRANTED){
                    startDownload(list);
                }
                else {
                    Toast.makeText(this,"permission denied...",Toast.LENGTH_SHORT).show();
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }




}