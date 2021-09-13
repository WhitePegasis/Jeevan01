package com.example.jeevan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.jeevan.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity{

    ActivityLoginBinding binding;
    ProgressDialog progressdialogue;
    FirebaseDatabase database;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding=ActivityLoginBinding.inflate(getLayoutInflater());
        database=FirebaseDatabase.getInstance();
        setContentView(binding.getRoot());
        progressdialogue= new ProgressDialog(LoginActivity.this);
        progressdialogue.setTitle("Login");
        progressdialogue.setMessage("Logging in to your account, thanks for waiting!");
        auth=FirebaseAuth.getInstance();

        //if user is already logged in directly send him/her to main page
        /*if(auth.getCurrentUser()!=null)
        {
            Toast.makeText(this, ""+auth.getCurrentUser(), Toast.LENGTH_SHORT).show();
            // if user is a doctor open doctors page else needers page
            DatabaseReference ref=database.getReference().child("Doctors").child(auth.getCurrentUser().getUid()).child("type");
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        Intent intent;
                        intent = new Intent(LoginActivity.this, MainActivityDoc.class);
                        startActivity(intent);
                    }
                    else{
                        Intent intent;
                        intent = new Intent(LoginActivity.this, MainActivityNeeder.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        }*/

        //if  user id not logged in take users email and password for login and send him/her to the respective page
        binding.loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressdialogue.show();
                auth.signInWithEmailAndPassword(binding.EmailAddress.getText().toString()
                        , binding.Password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressdialogue.dismiss();
                        if (task.isSuccessful()) {
                            DatabaseReference ref=database.getReference().child("Doctors").child(auth.getCurrentUser().getUid()).child("type");
                            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(snapshot.exists()){
                                        Intent intent;
                                        intent = new Intent(LoginActivity.this, MainActivityDoc.class);
                                        startActivity(intent);
                                    }
                                    else{
                                        Intent intent;
                                        intent = new Intent(LoginActivity.this, MainActivityNeeder.class);
                                        startActivity(intent);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }
                });
            }
        });

        //if user wants to register send him/her to signup page
        binding.notregisteredtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });


    }
}