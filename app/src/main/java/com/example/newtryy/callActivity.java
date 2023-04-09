package com.example.newtryy;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class callActivity extends AppCompatActivity {

    String name;

    public  String name_search(String val){
        String number = null;
        String name = null;

// try to replace this list with the contacts
// up to this step we won't change a thing
        ArrayList<ArrayList<String>> contacts ;
        contacts = getPhoneContact();

//        String[][] phonebook = {{"ሰላም","tel:0911134679"},
//                                {"ከበደ","tel:0946917326"},
//                                {"አበበ","tel:0911234577"},
//                                {"አሰፋ","tel:0911269477"},
//                                {"አበባ","tel:0911233077"}
//        };
        for (int i = 0; i < contacts.size(); i++) {
            for (int j = 0; j <contacts.get(i).size(); j++) {
                if (contacts.get(i).get(j).equals(val)) {
                    name = contacts.get(i).get(j);
                    number= contacts.get(i+1).get(j);
                    System.out.println(name);
                    System.out.println(number);
                    

                }
            }
        }

        if (name==null){
            return "nf";
        }
        else {
            return number;
        }


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        setTitle("name of call Page");

        Intent tovoice = new Intent(callActivity.this, secondForVoice.class);
        startActivityForResult(tovoice,1);

    }

    private void voiceAutomation2() {
        Intent voice = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);//to recognize my message
        voice.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        voice.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "am-ET");
        voice.putExtra(RecognizerIntent.EXTRA_PROMPT, "ማን ጋር ልደውል");
        //pass this request to the os
        startActivityForResult(voice, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
//            ArrayList<String> dataa=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
//            name = dataa.get(0).toString();
            name = data.getStringExtra("value");
            String result =  name_search(name);

            if (result.equals("nf")){
                Toast.makeText(getApplicationContext(), "የስም ዝርዝሩ የለም", Toast.LENGTH_LONG).show();
                Intent tovoice = new Intent(callActivity.this, secondForVoice.class);
                startActivityForResult(tovoice,1);
            }
            else {
                TextView tv2 = (TextView) findViewById(R.id.textView4);
                tv2.setText(name);

                String first = "tel:";


                Intent callIntent = new Intent(Intent.ACTION_DIAL);//CALL_PHONE argiw
                callIntent.setData(Uri.parse(first.concat(result)));
                startActivity(callIntent);
            }
            }


    }

    private ArrayList getPhoneContact(){

        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED
        ){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_CONTACTS},0);
        }
        ContentResolver contentResolver = getContentResolver();
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        Cursor cursor = contentResolver.query(uri,null,null,null,null);
        Log.i("DEMO","total number of contacts ::: " + cursor.getCount());

        ArrayList <String> nameArray = new ArrayList<>();
        ArrayList <String> numberArray = new ArrayList<>();

        if(cursor.getCount() > 0){

            while (cursor.moveToNext()){
                String contactName = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String contactNumber = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));

                //append on array
                nameArray.add(contactName);
                numberArray.add(contactNumber);

            }
            Log.i("mine","list of contacts put in array "+nameArray );
            Log.i("mine","list of numbers put in array "+numberArray);
            Log.i("mine","iterate this times "+cursor.getCount() );
        }
        ArrayList<ArrayList<String>> nameAndNumber = new ArrayList();
        nameAndNumber.add(nameArray);
        nameAndNumber.add(numberArray);

        Log.i("mine","list of list "+ nameAndNumber );

        return nameAndNumber;
    }
}