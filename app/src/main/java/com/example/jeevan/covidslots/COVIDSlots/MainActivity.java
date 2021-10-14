package com.example.jeevan.covidslots.COVIDSlots;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jeevan.LoginActivity;
import com.example.jeevan.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_root);



        Button stats;
        Button slot;
        Button consult;

        stats = (Button) findViewById(R.id.india);
        slot = (Button) findViewById(R.id.slotcheck);
        consult = (Button) findViewById(R.id.chat);
        stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivity();
            }
            public void openNewActivity(){
                Intent intent = new Intent(MainActivity.this, IndiaStatsActivity.class);
                startActivity(intent);
            }
        });

        slot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivity();
            }
            public void openNewActivity(){
                Intent intent = new Intent(MainActivity.this, CowinActivity.class);
                startActivity(intent);
            }
        });

        consult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivity();
            }
            public void openNewActivity(){
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }



}