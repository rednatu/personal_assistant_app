package com.example.newtryy;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class musicActivity extends AppCompatActivity {
    String musicname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        Intent tovoice = new Intent(musicActivity.this, secondForVoice.class);
        startActivityForResult(tovoice,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {

            musicname = data.getStringExtra("value");
            //here make yt search and play the closest one
            //make user friendly later


//            if (musicname.equals()){
//
//            }
        }
    }
}