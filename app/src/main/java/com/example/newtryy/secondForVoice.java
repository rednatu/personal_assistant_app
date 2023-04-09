package com.example.newtryy;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.widget.TextView;

import java.util.ArrayList;


public class secondForVoice extends AppCompatActivity {
    //  because value="" is never used
    //    String value= "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("second ac");
        setContentView(R.layout.activity_second_for_voice);
        voiceAutomation();
    }

    private void voiceAutomation() {
        Intent voice = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);//to recognize my message
        voice.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        voice.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "am-ET");
        voice.putExtra(RecognizerIntent.EXTRA_PROMPT, "order me");
        //pass this request to the os
        startActivityForResult(voice, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            ArrayList<String> arrayList = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);


            String inputted = arrayList.get(0).toString();
            Intent intent = new Intent();
            intent.putExtra("value", inputted);
            setResult(RESULT_OK, intent);
            finish();

        }
    }
}