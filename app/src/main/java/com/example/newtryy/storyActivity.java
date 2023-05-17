package com.example.newtryy;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class storyActivity extends AppCompatActivity {
    MediaPlayer player;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        setTitle("story page");

        if(player == null){
            player = MediaPlayer.create(this, R.raw.choosestory);
        }
        player.start();
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Intent tovoice = new Intent(storyActivity.this, secondForVoice.class);
        startActivityForResult(tovoice,1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {

            name = data.getStringExtra("value");
            TextView tv2 = findViewById(R.id.tvs);
            tv2.setText(name);

            if (name.equals("አንድ") || name.equals("1")){
                if(player == null){
                    player = MediaPlayer.create(this, R.raw.ልቢሶችና_ጫማ_ሰሪው);
                }
                player.start();
            }
            else if (name.equals("ሁለት") || name.equals("2")) {
                if(player == null){
                    player = MediaPlayer.create(this, R.raw.አሊባባና_አስራ_ሁለቱ_ሌቦች);
                }
                player.start();
            }
            else if(name.equals("ሶስት") || name.equals("3")){
                if(player == null){
                    player = MediaPlayer.create(this, R.raw.አላዲንና_አስደናቂዋ_ኩራዝ);
                }
                player.start();
            }
            else if(name.equals("አራት") || name.equals("4")){
                if(player == null){
                    player = MediaPlayer.create(this, R.raw.ፑስ_በቦቴ);
                }
                player.start();
            }
        }
    }
}