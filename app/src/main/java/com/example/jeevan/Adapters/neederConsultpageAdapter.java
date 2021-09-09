package com.example.jeevan.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jeevan.Interfaces.activeDoctorsSelectListeners;
import com.example.jeevan.MainActivityNeeder;
import com.example.jeevan.Models.Users;
import com.example.jeevan.Models.docModel;
import com.example.jeevan.R;
import com.example.jeevan.chatDetailActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class neederConsultpageAdapter extends RecyclerView.Adapter<neederConsultpageAdapter.viewholder2>{

    ArrayList<docModel> arrayList;
    Context context;
    FirebaseDatabase database;
    FirebaseAuth auth;
    private activeDoctorsSelectListeners listener;

    public neederConsultpageAdapter(ArrayList<docModel> arrayList, Context context, activeDoctorsSelectListeners listener) {
        this.arrayList = arrayList;
        this.context = context;
        this.listener=listener;
    }

    @NonNull
    @Override
    public viewholder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.sample_needer_consultpage,parent,false);
        return new neederConsultpageAdapter.viewholder2(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder2 holder, @SuppressLint("RecyclerView") int position) {
        database=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();
        docModel user= arrayList.get(position);
            Picasso.get().load(user.getDp()).placeholder(R.drawable.bg1);
            holder.username.setText(user.getUsername());

            //if user click request button start an onclick listener
            holder.sendRequestBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.OnItemClicked(arrayList.get(position));
                }
            });

            //if DocAcceptedRequest child inside needer has any doctors uid means doctor has accepted the request, so enable the chat button
            database.getReference().child("Needers").child(auth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.child("DocAcceptedRequest").getValue(String.class).equals(user.getUserid())){
                        holder.chatBtn.setEnabled(true);
                        Log.d("sdf",snapshot.child("DocRequest").getValue(String.class) + "== "+user.getUserid());
                    }
                    else {
                        holder.chatBtn.setEnabled(false);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            //once chat button is enabled if user clicks it send him/her to the chatting page along with dp,username etc necessary details
        holder.chatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, chatDetailActivity.class);
                intent.putExtra("userId",user.getUserid());
                intent.putExtra("profilePic",user.getDp());
                intent.putExtra("username",user.getUsername());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    //create a holder to hold all the views of available doctor layout

    public class viewholder2 extends RecyclerView.ViewHolder {
        ImageView img;
        TextView username,lastmessage;
        Button sendRequestBtn,viewDetailsBtn,chatBtn;
        public viewholder2(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.profile_image2);
            username=itemView.findViewById(R.id.usernametextview2);
            lastmessage=itemView.findViewById(R.id.lastmessagetextview2);
            sendRequestBtn=itemView.findViewById(R.id.sendRequestBtn);
            viewDetailsBtn=itemView.findViewById(R.id.viewDetailsBtn);
            chatBtn=itemView.findViewById(R.id.chatBtn2);
        }
    }
}
