package com.example.jeevan.Adapters;

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

//import com.example.jeevan.ChatDetailActivity;
import com.example.jeevan.Models.Users;
import com.example.jeevan.Models.docModel;
import com.example.jeevan.R;
import com.example.jeevan.chatDetailActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class docNotificationPageAdapter extends RecyclerView.Adapter<docNotificationPageAdapter.viewholder>{

    ArrayList<Users> arraylist;
    Context context;
    FirebaseDatabase database;
    FirebaseAuth auth;

    public docNotificationPageAdapter(ArrayList<Users> array, Context context) {
        this.arraylist = array;
        this.context = context;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.sample_doc_notification,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {


        Users user= arraylist.get(position);
        Picasso.get().load(user.getDp()).placeholder(R.drawable.bg1);
        holder.username.setText(user.getUsername());
        database=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();

        holder.acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.chatBtn.setEnabled(true);
                database.getReference().child("Needers").child(user.getUserid()).child("DocAcceptedRequest")
                        .setValue(auth.getCurrentUser().getUid());
            }
        });
        holder.declineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.chatBtn.setEnabled(false);
                database.getReference().child("Needers").child(user.getUserid()).child("DocRequest").setValue("null");
                database.getReference().child("Needers").child(user.getUserid()).child("DocAcceptedRequest").setValue("null");

            }
        });
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
        return arraylist.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView username,lastmessage;
        Button acceptBtn,declineBtn,chatBtn;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.profile_image);
            username=itemView.findViewById(R.id.usernametextview);
            lastmessage=itemView.findViewById(R.id.lastmessagetextview);
            acceptBtn=itemView.findViewById(R.id.acceptBtn);
            declineBtn=itemView.findViewById(R.id.declineBtn);
            chatBtn=itemView.findViewById(R.id.chatBtn);
        }
    }
}