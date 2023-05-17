package com.example.newtryy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {

    MediaPlayer player;
    private void camera() {
        Intent customCamera = new Intent(MainActivity.this, imageViewActivity.class);
        startActivity(customCamera);
    }
    private void story() {
        if(player == null){
            player = MediaPlayer.create(this, R.raw.story);
        }
        player.start();
        Intent toStory = new Intent(MainActivity.this, storyActivity.class);
        startActivity(toStory);
    }
    private void call() throws InterruptedException {
        if(player == null){
            player = MediaPlayer.create(this, R.raw.callwho);
        }
        player.start();
        Thread.sleep(3000);
        Intent toPhone = new Intent(MainActivity.this, callActivity.class);
        startActivity(toPhone);
    }
    private void music() throws InterruptedException {

        if(player == null){
            player = MediaPlayer.create(this, R.raw.whosemusic);
        }
        player.start();
        Thread.sleep(3000);
        Intent toPhone = new Intent(MainActivity.this, musicActivity.class);
        startActivity(toPhone);
    }

    public  String processor(String val){

        String[][] kb ={{"ፎቶ አንሺ","p"},
                        {"ፎቶ","p"},
                        {"ፎቶ አንሽ","p"},
                        {"photo","p"},
                        {"ስልክ","c"},
                        {"ስልክ ደውል","c"},
                        {"ስልክ ደዋይ","c"},
                        {"ደውል","c"},
                        {"ደወይ","c"},
                        {"ተረት","s"},
                        {"ተረት ተረት","s"},
                        {"ተረክ","s"},
                        {"ተረት አውሪ","s"},
                        {"አውሪልኝ","s"},
                        {"show","g"},
                        {"ሙዚቃ","m"},
                        {"ሙዚቃ ክፍቺ","m"},
                        {"ሙዚቃ ክፍች","m"},
                        {"ዘፈን","m"},
                        {"ዘፈን ክፈች","m"},
                        {"ዘፈን ክፈቺ","m"}
        };

        for (int i = 0; i < kb.length; i++) {
            for (int j = 0; j < kb[i].length; j++) {
                if (kb[i][j].equals(val)) {
                    System.out.println(kb[i][j+1]);
                    int lastIndex = (kb[i].length - 1);
                    return kb[i][lastIndex];

                }
            }
        }
        return "notFound";
    }

    String val;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Personal Assistant Robot App");

        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);


    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @org.jetbrains.annotations.NotNull MenuItem item) {

                int id = item.getItemId();
                item.setChecked(true);
                drawerLayout.closeDrawer(GravityCompat.START);
                switch (id)
                {
                    case R.id.nav_home:
                        replaceFragment(new HomeFragment());break;
                    case R.id.trash:
                        replaceFragment(new AboutFragment());break;
                    case R.id.settings:
                        Toast.makeText(MainActivity.this, "Settings is Clicked",Toast.LENGTH_SHORT).show();break;
                    case R.id.nav_login:
                        Toast.makeText(MainActivity.this, "Login is Clicked",Toast.LENGTH_SHORT).show();break;
                    default:
                        return true;
                }
                return true;
            }
        });


//        TextView tv = findViewById(R.id.textView);

//        tv.setBackgroundColor(Color.parseColor("#26d0aa"));

        btn = findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent toVoice = new Intent(MainActivity.this, secondForVoice.class);
                startActivityForResult(toVoice,1);
            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
// val is the word received from google
            val = data.getStringExtra("value");
// search the word from kb
          String result =  processor(val);
          if (result.equals("notFound")){
              Toast.makeText(getApplicationContext(), "ትእዛዙ የለም፣ ሌላ ይሞክሩ", Toast.LENGTH_LONG).show();
              if(player == null){
                  player = MediaPlayer.create(this, R.raw.tryagain);
              }
              player.start();

              final Timer timer = new Timer();
              timer.schedule(new TimerTask() {
                  @Override
                  public void run() {
                      Intent tovoice = new Intent(MainActivity.this, secondForVoice.class);
                      startActivityForResult(tovoice,1);
                      timer.cancel();
                  }
              },5000);

          }
          else {
              TextView tv2 = findViewById(R.id.textView4);
              tv2.setText(result);
              if (result.equals("p")){
                  camera();
              }
              else if (result.equals("c")) {
                  try {
                      call();
                  } catch (InterruptedException e) {
                      throw new RuntimeException(e);
                  }
              } else if (result.equals("s")) {
                  story();
              } else if (result.equals("g")) {
                  gallery();
              } else if (result.equals("m")) {
                  try {
                      music();
                  } catch (InterruptedException e) {
                      throw new RuntimeException(e);
                  }
              }
          }
    }
 }



    private void gallery() {
        Intent intent = new Intent(MainActivity.this, CustomGalleryActivity.class);
        startActivity(intent);
    }

private void replaceFragment(Fragment fragment){
    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    fragmentTransaction.replace(R.id.frameLayout, fragment);
    fragmentTransaction.commit();
}
}
