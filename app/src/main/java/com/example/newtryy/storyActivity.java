package com.example.newtryy;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

public class storyActivity extends AppCompatActivity {
    MediaPlayer player;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        setTitle("story page");

        Intent tovoice = new Intent(storyActivity.this, secondForVoice.class);
        startActivityForResult(tovoice,1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {

            name = data.getStringExtra("value");

            if (name.equals("አንድ")){
                if(player == null){
                    player = MediaPlayer.create(this, R.raw.story1);
                }
                player.start();

            } else if (name.equals("ሁለት")) {
                if(player == null){
                    player = MediaPlayer.create(this, R.raw.story2);
                }
                player.start();
            }
            else if(name.equals("ሶስት")){
                if(player == null){
                    player = MediaPlayer.create(this, R.raw.story3);
                }
                player.start();
            }
        }

    }
}