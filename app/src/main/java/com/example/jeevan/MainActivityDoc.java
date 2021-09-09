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
import com.example.jeevan.databinding.ActivityMainBinding;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivityDoc extends AppCompatActivity {
    ActivityMainBinding binding;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.viewpager.setAdapter(new docMainFragmentAdapter(this));
        auth = FirebaseAuth.getInstance();

        new TabLayoutMediator(binding.tablayout, binding.viewpager,
                (tab, position) -> {
                    if (position == 0)
                        tab.setText("Doc Page");
                    else if (position == 1)
                        tab.setText("Notifications");
                    else
                        tab.setText("Amazinfo");
                }

        ).attach();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                auth.signOut();
                Toast.makeText(this, "signedout", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(MainActivityDoc.this, LoginActivity.class);
                startActivity(intent1);
                break;
            case R.id.settings:
                Toast.makeText(this, "This is setting", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
