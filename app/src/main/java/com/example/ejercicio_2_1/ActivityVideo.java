package com.example.ejercicio_2_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ActivityVideo extends AppCompatActivity {


    VideoView video;
    FloatingActionButton btnTomarVideo;
    Button btnGaleriaAV;

    static final int PETICION_ACCESO_CAM = 100;
    static final int TAKE_VIDEO_REQUEST = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_video);

        btnTomarVideo = (FloatingActionButton) findViewById(R.id.btnVideoAV);
        video = (VideoView) findViewById(R.id.videoView);
        btnGaleriaAV = (Button) findViewById(R.id.btnGaleriaAV);

        btnTomarVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PermisoCamara();
            }
        });

        btnGaleriaAV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ActivityListarVIdeo.class);
                startActivity(intent);
            }
        });

    }

    private void PermisoCamara() {

        if (ContextCompat.checkSelfPermission(getApplicationContext(),  Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},PETICION_ACCESO_CAM);
        }else{
            CapturarVideo();
        }
    }

    private void CapturarVideo() {
        Intent takepic = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        if(takepic.resolveActivity(getPackageManager()) != null)
        {
            startActivityForResult(takepic,TAKE_VIDEO_REQUEST);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==TAKE_VIDEO_REQUEST && resultCode==RESULT_OK) {
            Uri videoUri=data.getData();
            video.setVideoURI(videoUri);
            video.start();
            try {
                AssetFileDescriptor videoAsset = getContentResolver().openAssetFileDescriptor(data.getData(), "r");
                FileInputStream in =videoAsset.createInputStream();
                FileOutputStream archivo = openFileOutput(CrearVideo(), Context.MODE_PRIVATE);
                byte[] buf = new byte[1024];
                int leng;
                while ((leng = in.read(buf)) > 0) {
                    archivo.write(buf, 0, leng);
                }
            }catch (IOException e)
            {
                Toast.makeText(this, "Error Al Capturar VIdeo", Toast.LENGTH_LONG).show();
            }
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PETICION_ACCESO_CAM) {

            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                CapturarVideo();
            }

        }else{
            Toast.makeText(getApplicationContext(),"Acepte los persmisos de la camara!!",Toast.LENGTH_LONG).show();
        }
    }


    private String CrearVideo() {
        String fecha = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String nombre = fecha + ".mp4 ";
        return nombre;

    }
}