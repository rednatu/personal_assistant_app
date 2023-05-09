package com.example.newtryy;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.util.Comparator;

public class imageViewActivity extends AppCompatActivity {

    private static final int REQUEST_CAMERA_PERMISSION_CODE = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);

        imageView = findViewById(R.id.image_view);
    }
    public void captureImage(View view){
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED){
            //Request camera permission if not granted
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    REQUEST_CAMERA_PERMISSION_CODE);
            return;
        }
        //open the camera

        Intent intent = new Intent(imageViewActivity.this,cameraActivity.class);
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            //get the captured image as bitmap

//            Bundle extras = data.getExtras();//extras is the file
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
////            Bitmap bitmap = BitmapFactory.decodeFile(filePath);
//
//            imageView.setImageBitmap(imageBitmap);
//            File mSaveBit; // Your image file
//            String filePath = mSaveBit.getPath();//how to get latest file from a path
//            Bitmap bitmap = BitmapFactory.decodeFile(filePath);
//            imageView.setImageBitmap(bitmap);



//            my solution
            String val = data.getStringExtra("value");

//            Path dir = Paths.get("./path/somewhere");  // specify your directory
//
//            Optional<Path> lastFilePath = Files.list(dir)    // here we get the stream with full directory listing
//                    .filter(f -> !Files.isDirectory(f))  // exclude subdirectories from listing
//                    .max(Comparator.comparingLong(f -> f.toFile().lastModified()));  // finally get the last file using simple comparator by lastModified field
//
//            if ( lastFilePath.isPresent() ) // your folder may be empty
//            {
//                // do your code here, lastFilePath contains all you need
//            }
//

            Bitmap bitmap= BitmapFactory.decodeFile(val);
            imageView.setImageBitmap(bitmap);


        }
    }
}