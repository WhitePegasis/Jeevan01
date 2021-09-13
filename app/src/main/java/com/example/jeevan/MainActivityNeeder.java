package com.example.jeevan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.jeevan.Adapters.docMainFragmentAdapter;
import com.example.jeevan.Adapters.neederConsultpageAdapter;
import com.example.jeevan.Adapters.neederMainFragmentAdapter;
import com.example.jeevan.databinding.ActivityMainBinding;
import com.example.jeevan.databinding.ActivityMainNeederBinding;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivityNeeder extends AppCompatActivity {
    ActivityMainNeederBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainNeederBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //attaching adapter to the viewpager
        binding.viewpager2.setAdapter(new neederMainFragmentAdapter(this));
        auth = FirebaseAuth.getInstance();

        //changing tab heading according to the tab position
        new TabLayoutMediator(binding.tabLayout2, binding.viewpager2,
                (tab, position) -> {
                    if (position == 0)
                        tab.setText("Needers Page");
                    else if (position == 1)
                        tab.setText("Active Doctors");
                    else
                        tab.setText("Amazinfo");
                }

        ).attach();

    }

    //inflating menu options in menu icon

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }




    // adding functions to the menu options
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                auth.signOut();
                Toast.makeText(this, "signedout", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(MainActivityNeeder.this, LoginActivity.class);
                database.getReference().child("Needers").child(auth.getCurrentUser().getUid()).child("DocAcceptedRequest").setValue("null");
                database.getReference().child("Needers").child(auth.getCurrentUser().getUid()).child("DocRequest").setValue("null");
                startActivity(intent1);
                break;
            case R.id.settings:
                Toast.makeText(this, "This is setting", Toast.LENGTH_SHORT).show();
                //Intent intent2=new Intent(MainActivity.this,xyz.class);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}