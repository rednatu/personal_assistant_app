package com.example.newtryy;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    MediaPlayer player;
    private void camera() {
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivity(camera);
    }

    private void call() throws InterruptedException {

        if(player == null){
            player = MediaPlayer.create(this, R.raw.callwho);
        }
        player.start();
        Thread.sleep(3000);
        Intent tocallvoice = new Intent(MainActivity.this, callActivity.class);
        startActivity(tocallvoice);
    }
//useless kb
    public  String processor(String val){
// translate translates the amharic to its english equivalent
        String eval=translate(val);


        String[][] kb ={{"photo","p"},
                         {"call","c"}
        };

        for (int i = 0; i < kb.length; i++) {
            for (int j = 0; j < kb[i].length; j++) {
                if (kb[i][j].equals(eval)) {
                    System.out.println(kb[i][j+1]);
                    int lastIndex = (kb[i].length - 1);
                    return kb[i][lastIndex]; // answer; return aa;

                }
            }
        }
        return "nf";
    }
//to translate the received voice to english because the kb search wont work other wise.
    public String translate(String val) {
        String translate="";
        if (val.equals("ፎቶ አንሺ") || val.equals("ፎቶ" )|| val.equals("ፎቶ አንሽ" )){
            Toast.makeText(getApplicationContext(), "photo translated", Toast.LENGTH_LONG).show();
            translate= "photo";
        }
        else if (val.equals("ስልክ") || val.equals("ስልክ ደውል") || val.equals("ስልክ ደዋይ")||val.equals("ደውል")||val.equals("ደወይ")) {
            Toast.makeText(getApplicationContext(), "call translated", Toast.LENGTH_LONG).show();
            translate= "call";
        }
        return translate;
    }

    String val;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Personal Assistant Robot App");

        TextView tv = findViewById(R.id.textView);

        tv.setBackgroundColor(Color.parseColor("#26d0aa"));

        btn = findViewById(R.id.btnn);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent tovoice = new Intent(MainActivity.this, secondForVoice.class);
                startActivityForResult(tovoice,1);
            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
// value is the word received from google
            val = data.getStringExtra("value");
// search the word from kb
          String result =  processor(val);
          if (result.equals("nf")){
              Toast.makeText(getApplicationContext(), "ይሄንን ማድርግ አልችልም፣ ሌላ ይሞክሩ", Toast.LENGTH_LONG).show();
              Intent tovoice = new Intent(MainActivity.this, secondForVoice.class);
              startActivityForResult(tovoice,1);
          } else {
              TextView tv2 = findViewById(R.id.textView4);
              tv2.setText(result);

              if (result.equals("p")){
                  camera();
                 // there was an intent for opening secondForVoice, it works like a recurssion but not a right way to implement recurssion
              } else if (result.equals("c")) {
                  try {
                      call();
                  } catch (InterruptedException e) {
                      throw new RuntimeException(e);
                  }
              }
          }
    }
 }
}
