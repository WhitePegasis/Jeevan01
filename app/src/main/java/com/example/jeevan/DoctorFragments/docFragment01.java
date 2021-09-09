package com.example.jeevan.DoctorFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.renderscript.ScriptGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

//import com.example.chatapp.ChatDetailActivity;

import com.example.jeevan.Models.Users;
import com.example.jeevan.Models.docModel;
import com.example.jeevan.databinding.FragmentDoc01Binding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Map;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;


public class docFragment01 extends Fragment {


    View view;
    FirebaseDatabase database;
    FirebaseAuth auth;
    //Users user;

    FragmentDoc01Binding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentDoc01Binding.inflate(inflater,container,false);
        view= binding.getRoot();
        database=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();
        binding.switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    database.getReference().child("Doctors").child(auth.getCurrentUser().getUid()).child("status").setValue("Active").addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getActivity(), "User Active: "+auth.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "Error occured: "+e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
                    //if("doctor" == database.getReference().child("users").child(user.getUserid()).child("type")==
                }
                else{
                    database.getReference().child("Doctors").child(auth.getCurrentUser().getUid()).child("status").setValue("UnActive")
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getActivity(), "Deactivated "+auth.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "Error occured: "+e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });

                    Toast.makeText(getActivity(), "Unchecked the button", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}