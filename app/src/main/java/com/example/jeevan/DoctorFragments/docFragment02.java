package com.example.jeevan.DoctorFragments;

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
import com.example.jeevan.Models.Users;
import com.example.jeevan.Models.docModel;
import com.example.jeevan.databinding.FragmentDoc02Binding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;



public class docFragment02 extends Fragment {

    public docFragment02() {
        // Required empty public constructor
    }

    FragmentDoc02Binding binding;
    ArrayList<Users> arraylist=new ArrayList<>();
    FirebaseDatabase database;
    FirebaseAuth auth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        binding = FragmentDoc02Binding.inflate(inflater,container,false);
        database=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();

        docNotificationPageAdapter adapter=new docNotificationPageAdapter(arraylist,getContext());
        binding.notificationRecyclerView.setAdapter(adapter);

        LinearLayoutManager layoutmanager=new LinearLayoutManager(getContext());
        binding.notificationRecyclerView.setLayoutManager(layoutmanager);

        database.getReference().child("Needers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arraylist.clear();
                for(DataSnapshot datasnapshot:snapshot.getChildren()){
                    Users users=datasnapshot.getValue(Users.class);
                    users.setUserid(datasnapshot.getKey());

                    if(datasnapshot.child("DocRequest").getValue(String.class).equals(auth.getCurrentUser().getUid()))
                    {
                        arraylist.add(users);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error "+ error+" occured!!", Toast.LENGTH_SHORT).show();
            }
        });
        return binding.getRoot();
    }
}