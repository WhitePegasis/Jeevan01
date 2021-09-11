package com.example.jeevan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.example.jeevan.Models.Users;
import com.example.jeevan.Models.docModel;
import com.example.jeevan.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    ActivitySignUpBinding binding;
    private FirebaseAuth auth;
    FirebaseDatabase database;
    ProgressDialog progressdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressdialog=new ProgressDialog(SignUpActivity.this);

        progressdialog.setTitle("Creating Account");
        progressdialog.setMessage("We're creating your account, thanks for waiting!");

        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();

        binding.signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressdialog.show();
                auth.createUserWithEmailAndPassword(
                        binding.editTextTextEmailAddress.getText().toString(),binding.editTextTextPassword.getText().toString()).
                        addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressdialog.dismiss();
                                if(task.isSuccessful())
                                {

                                    String id=task.getResult().getUser().getUid();
                                    if(binding.type.getText().toString().equals("needer")){
                                        Users user=new Users(binding.editTextTextPersonName.getText().toString(),binding.editTextTextEmailAddress.getText().toString()
                                                ,binding.editTextTextPassword.getText().toString(),binding.type.getText().toString(),"null","null");
                                        database.getReference().child("Needers").child(id).setValue(user);
                                        database.getReference().child("Needers").child(auth.getCurrentUser().getUid()).child("DocAcceptedRequest").setValue("null");
                                        database.getReference().child("Needers").child(auth.getCurrentUser().getUid()).child("DocRequest").setValue("null");
                                    }
                                    else{
                                        docModel docuser=new docModel(binding.editTextTextPersonName.getText().toString(),binding.editTextTextEmailAddress.getText().toString()
                                                ,binding.editTextTextPassword.getText().toString(),binding.type.getText().toString());
                                        database.getReference().child("Doctors").child(id).setValue(docuser);
                                    }

                                    Toast.makeText(SignUpActivity.this, "Task Successful", Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(SignUpActivity.this,LoginActivity.class);
                                    startActivity(intent);
                                }

                                else
                                    Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });
            }
        });

        binding.alreadyuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

    }
}