package com.example.ejercicio_2_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.VideoView;

public class ActivityListarVIdeo extends AppCompatActivity {


    Spinner listaVideo;
    VideoView videoViewer;
    Button btnVolver, btnVerVideo;
    private String[] lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_video);

        videoViewer = (VideoView) findViewById(R.id.VideoViwerAL);
        btnVerVideo = (Button) findViewById(R.id.btnVerVideo);
        btnVolver = (Button) findViewById(R.id.btnVolverAL);
        listaVideo = (Spinner) findViewById(R.id.SpinerVideos);
        lista=fileList();

        ArrayAdapter<String> adapter=new ArrayAdapter<>( this, android.R.layout.simple_spinner_item,lista);
        listaVideo.setAdapter(adapter);

        btnVerVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VerVideo(view);
            }
        });

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               /* Intent intent = new Intent(getApplicationContext(),ActivityVideo.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();*/

                Intent intent = new Intent(getApplicationContext(),ActivityVideo.class );
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

    }

    public void VerVideo(View v) {
        int pos=listaVideo.getSelectedItemPosition();
        videoViewer.setVideoPath(getFilesDir()+"/"+lista[pos]);
        videoViewer.start();
    }

}