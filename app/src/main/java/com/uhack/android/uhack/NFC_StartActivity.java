package com.uhack.android.uhack;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NFC_StartActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private String startingLocation="0,0";
    private String endingLocation="0,0";
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc__start);
        mAuth=FirebaseAuth.getInstance();
        sharedPreferences=getSharedPreferences("PREF",MODE_PRIVATE);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users");


        final DatabaseReference Ref= myRef.child(mAuth.getCurrentUser().getUid()+"");


        if(sharedPreferences.getBoolean("travelstart",true)){
            Ref.child("startingLocation").setValue("0,0");
            Ref.child("endingLocation").setValue("0,0");

            if(sharedPreferences.getBoolean("firstTime",true))
            {   Ref.child("credits").setValue(0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("firstTime",false);
                editor.apply();
            }

            sharedPreferences.edit().putBoolean("travelstart",false).apply();
        }
        else
        {
            startActivity(new Intent(this,NFC_FinishActivity.class));
            sharedPreferences.edit().putBoolean("travelstart",true).apply();

        }



    }
}
