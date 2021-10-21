package com.example.jeevan.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.jeevan.Models.chatDetailModel;
import com.example.jeevan.R;
import com.example.jeevan.databinding.SampleReceiverBinding;
import com.example.jeevan.databinding.SampleSenderBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class chatDetailAdapter extends RecyclerView.Adapter{
    ArrayList<chatDetailModel> messagemodel;
    Context context;
    SampleSenderBinding binding;
    SampleReceiverBinding binding2;

    int SENDER_VIEW_TYPE=1;
    int RECEIVER_VIEW_TYPE=2;

    public chatDetailAdapter(ArrayList<chatDetailModel> messagemodel, Context context) {
        this.messagemodel = messagemodel;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding=SampleSenderBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        if(viewType==SENDER_VIEW_TYPE)
        {
            View view=LayoutInflater.from(context).inflate(R.layout.sample_sender,parent,false);
            binding=SampleSenderBinding.bind(view);//trying to bind
            return new SenderViewholder(view);
        }
        else{
            View view=LayoutInflater.from(context).inflate(R.layout.sample_receiver,parent,false);
            binding2= SampleReceiverBinding.bind(view);
            return new ReceiverViewholder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        chatDetailModel messageList=messagemodel.get(position);
        if(holder.getClass()==SenderViewholder.class)
        {
            SenderViewholder viewholder=(SenderViewholder)holder; // viewholder to get sample layout instances
            binding.senderimage.setVisibility(View.GONE);
            binding.sendermessage.setVisibility(View.VISIBLE);
            if(messageList.getMessage().equals("Photo1603")){
                binding.senderimage.setVisibility(View.VISIBLE);
                binding.sendermessage.setVisibility(View.GONE);
                Toast.makeText(context, "InsideBindViewHolder: "+messageList.getImageUrl() , Toast.LENGTH_SHORT).show();
                Glide.with(context).load(messageList.getImageUrl())
                        .into(viewholder.sentImage);


            }
            viewholder.senderMsg.setText(messageList.getMessage());
        }
        else
        {
            ReceiverViewholder viewholder2=(ReceiverViewholder)holder;
            binding2.receiverimage.setVisibility(View.GONE);
            binding2.receivermessage.setVisibility(View.VISIBLE);
            if(messageList.getMessage().equals("Photo1603")){
                binding2.receiverimage.setVisibility(View.VISIBLE);
                binding2.receivermessage.setVisibility(View.GONE);
                Toast.makeText(context, "InsideBindViewHolder2: "+messageList.getImageUrl() , Toast.LENGTH_SHORT).show();
                Glide.with(context).load(messageList.getImageUrl())
                        .into(viewholder2.receivedImage);
            }
            ((ReceiverViewholder)holder).receiverMsg.setText(messageList.getMessage());
            //((ReceiverViewholder)holder).receivedImage.setImageURI(messageList.getImageUrl());
        }

    }

    @Override
    public int getItemCount() {
        return messagemodel.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(messagemodel.get(position).getuId().equals(FirebaseAuth.getInstance().getUid()))
        {
            return SENDER_VIEW_TYPE;
        }
        else
        {
            return RECEIVER_VIEW_TYPE;
        }
    }

    public class ReceiverViewholder extends RecyclerView.ViewHolder {
        TextView receiverMsg,receiverTime;
        ImageView receivedImage;

        public ReceiverViewholder(@NonNull View itemView) {
            super(itemView);
            receiverMsg=itemView.findViewById(R.id.receivermessage);
            receiverTime=itemView.findViewById(R.id.receivertime);
            receivedImage=itemView.findViewById(R.id.receiverimage);
        }
    }
    public class SenderViewholder extends RecyclerView.ViewHolder{

        TextView senderMsg,senderTime;
        ImageView sentImage;

        public SenderViewholder(@NonNull View itemView) {
            super(itemView);
            senderMsg=itemView.findViewById(R.id.sendermessage);
            senderTime=itemView.findViewById(R.id.sendertime);
            sentImage=itemView.findViewById(R.id.senderimage);
        }
    }
}
