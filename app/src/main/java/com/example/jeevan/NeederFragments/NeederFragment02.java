package com.example.jeevan.NeederFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.jeevan.Adapters.docNotificationPageAdapter;
import com.example.jeevan.Adapters.neederConsultpageAdapter;
import com.example.jeevan.Interfaces.activeDoctorsSelectListeners;
import com.example.jeevan.Models.Users;
import com.example.jeevan.Models.docModel;
import com.example.jeevan.R;
import com.example.jeevan.databinding.FragmentDoc02Binding;
import com.example.jeevan.databinding.FragmentNeeder2Binding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class NeederFragment02 extends Fragment implements activeDoctorsSelectListeners {
    FragmentNeeder2Binding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;
    ArrayList<docModel> arraylist=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNeeder2Binding.inflate(inflater, container, false);
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        neederConsultpageAdapter adapter = new neederConsultpageAdapter(arraylist, getContext(), this);


        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.activeDoctorRecyclerView.setLayoutManager(layoutManager);


        database.getReference().child("Doctors").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arraylist.clear();
                for (DataSnapshot datasnapshot : snapshot.getChildren()) {
                    docModel users = datasnapshot.getValue(docModel.class);
                    users.setUserid(datasnapshot.getKey());
                    //users.setDocStatus(datasnapshot.child("mail").getValue(String.class));
                    //I guess mail should be status
                    //Log.d("statuscheck","status is: "+datasnapshot.child("status").getValue(String.class));

                    //if doctor has swiped to active add it to the arraylist which will be added to the needersCosult page
                    if (datasnapshot.child("status").getValue(String.class).equals("Active")){
                        arraylist.add(users);
                    }
                }
                adapter.notifyDataSetChanged();

            }

            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error "+ error+" occured!!", Toast.LENGTH_SHORT).show();
            }

        });
        binding.activeDoctorRecyclerView.setAdapter(adapter);
        return binding.getRoot();

    }



   @Override
   public void OnItemClicked(docModel model) {
        database.getReference().child("Needers").child(auth.getCurrentUser().getUid()).child("DocRequest").setValue(model.getUserid())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getContext(), "Sent Request", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}