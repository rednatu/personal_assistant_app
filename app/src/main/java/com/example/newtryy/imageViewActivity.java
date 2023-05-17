package com.example.newtryy;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.Manifest;
import android.widget.Toast;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class imageViewActivity extends AppCompatActivity {

    private static final int REQUEST_CAMERA_PERMISSION_CODE = 1;

    private static final int REQUEST_IMAGE_CAPTURE = 2;

    private ImageView imageView;

    private File file;
    private String folderName = "MyPhotoDir";

    String imageFileName = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);

        imageView = findViewById(R.id.image_view);
        captureImage();

    }

    public void captureImage(){
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED){
            //Request camera permission if not granted
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    REQUEST_CAMERA_PERMISSION_CODE);
            return;
        }
        //open the custom camera
        Intent intent = new Intent(imageViewActivity.this, cameraActivity.class);
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);

        Toast.makeText(imageViewActivity.this,"Saved",Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            imageFileName = data.getStringExtra("value");
            Log.d("mytag2",""+imageFileName);

            file = new File(getExternalFilesDir(folderName), "/" + imageFileName);

            Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            imageView.setImageBitmap(myBitmap);


            final Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    finish();
                    timer.cancel();
                }
            },3000);
        }
    }


}