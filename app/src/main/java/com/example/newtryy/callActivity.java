package com.example.newtryy;

import androidx.annotation.NonNull;
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
    private static final int REQUEST_CALL = 1;

    public  String name_search(String val){
        String number = null;
        String name = null;

        ArrayList<ArrayList<String>> contacts ;
        contacts = getPhoneContact();

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {

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

                if (ContextCompat.checkSelfPermission(callActivity.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(callActivity.this,
                            new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
                } else {
                    Toast.makeText(getApplicationContext(), "ለ"+name+" እየደወልኩ ነው", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(first.concat(result))));
                }
            }
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
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
            Log.i("mine","list of contacts put in array "+nameArray);
            Log.i("mine","list of numbers put in array "+numberArray);
            Log.i("mine","iterate this times "+cursor.getCount() );
        }
        ArrayList<ArrayList<String>> nameAndNumber = new ArrayList();
        nameAndNumber.add(nameArray);
        nameAndNumber.add(numberArray);

        Log.i("mine","list of list "+ nameAndNumber);

        return nameAndNumber;
    }
}