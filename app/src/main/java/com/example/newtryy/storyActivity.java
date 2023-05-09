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
                    player = MediaPlayer.create(this, R.raw.story1);
                }
                player.start();

            } else if (name.equals("ሁለት") || name.equals("2")) {
                if(player == null){
                    player = MediaPlayer.create(this, R.raw.story2);
                }
                player.start();
            }
            else if(name.equals("ሶስት") || name.equals("3")){
                if(player == null){
                    player = MediaPlayer.create(this, R.raw.story3);
                }
                player.start();
            }
        }

    }
}