package com.example.jeevan;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.jeevan.Adapters.chatDetailAdapter;
import com.example.jeevan.Models.chatDetailModel;
import com.example.jeevan.databinding.ActivityChatDetailBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
//fjok
public class chatDetailActivity extends AppCompatActivity {

    ActivityChatDetailBinding binding;
    FirebaseDatabase database;
    FirebaseStorage storage;
    ProgressDialog dialog;
    FirebaseAuth auth;
    ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        binding = ActivityChatDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dialog=new ProgressDialog(this);
        dialog.setMessage("Sending Image...");
        dialog.setCancelable(false);


        //from database taking details of user and using that to set personal chat page username,dp display on the top
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        storage=FirebaseStorage.getInstance();


        final String senderId = auth.getUid();
        String receiverId = getIntent().getStringExtra("userId");
        String profilePic = getIntent().getStringExtra("profilePic");
        String username = getIntent().getStringExtra("username");

        binding.usernametop.setText(username);
        Picasso.get().load(profilePic).placeholder(R.drawable.bg1).into(binding.profileImagedp);


        binding.arrow.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                                 DatabaseReference ref = database.getReference().child("Doctors").child(auth.getCurrentUser().getUid()).child("type");
                                                 ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                                     @Override
                                                     public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                         if (snapshot.exists()) {
                                                             Intent intent;
                                                             intent = new Intent(chatDetailActivity.this, MainActivityDoc.class);
                                                             startActivity(intent);
                                                         } else {
                                                             Intent intent;
                                                             intent = new Intent(chatDetailActivity.this, MainActivityNeeder.class);
                                                             startActivity(intent);
                                                         }
                /*Intent intent = new Intent(chatDetailActivity.this, .class);
                startActivity(intent);*/
                                                     }

                                                     @Override
                                                     public void onCancelled(@NonNull DatabaseError error) {
                                                         Toast.makeText(chatDetailActivity.this, "Error in chat detail activity", Toast.LENGTH_SHORT).show();
                                                     }

                                                 });
                                             }
                                         });

                ArrayList<chatDetailModel> messageList = new ArrayList<>();
                chatDetailAdapter ad = new chatDetailAdapter(messageList, chatDetailActivity.this);
                binding.privatechatRecyclerview.setAdapter(ad);

                LinearLayoutManager layoutManager = new LinearLayoutManager(chatDetailActivity.this);
                layoutManager.setStackFromEnd(true);
                binding.privatechatRecyclerview.setLayoutManager(layoutManager);
                layoutManager.setStackFromEnd(true);

                final String senderroom = senderId + receiverId;
                final String receiverroom = receiverId + senderId;

                database.getReference().child("chats").child(senderroom).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        messageList.clear();
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            chatDetailModel model = snapshot1.getValue(chatDetailModel.class);

                            messageList.add(model);
                        }
                        ad.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


                //sending text

                binding.sendimg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String message = binding.messagesendtext.getText().toString();
                        final chatDetailModel model = new chatDetailModel(senderId, message);
                        model.setTimestamp(new Date().getTime());
                        binding.messagesendtext.setText("");

                        database.getReference().child("chats").child(senderroom).push().setValue(model)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        database.getReference().child("chats").child(receiverroom).push().setValue(model)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {

                                                    }
                                                });
                                    }
                                });
                    }
                });



        /*binding.attachmentimg.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("QueryPermissionsNeeded")
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT).setType("image/");
                if (intent.resolveActivity(getPackageManager()) != null) {
                    activityResultLauncher.launch(intent);
                }
                else{
                    Toast.makeText(ChatDetailActivity.this, "no app found", Toast.LENGTH_SHORT).show();
                }
            }
        });*/

                // uploading image to chats child
                ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
                        new ActivityResultCallback<Uri>() {
                            @Override
                            public void onActivityResult(Uri uri) {
                                // Handle the returned Uri
                                Calendar calendar = Calendar.getInstance(); //creating unique key as push() does
                                StorageReference reference = storage.getReference().child("chats").child(senderroom).child(calendar.getTimeInMillis() + "");
                                dialog.show();
                                reference.putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                        dialog.dismiss();
                                        if (task.isSuccessful()) {
                                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    Toast.makeText(chatDetailActivity.this, "Done!", Toast.LENGTH_SHORT).show();
                                                    String filepath = uri.toString();
                                                    String message = binding.messagesendtext.getText().toString();
                                                    final chatDetailModel model = new chatDetailModel(senderId, message);
                                                    model.setTimestamp(new Date().getTime());
                                                    binding.messagesendtext.setText("");
                                                    model.setImageUrl(filepath);
                                                    model.setMessage("Photo1603");

                                                    database.getReference().child("chats").child(senderroom).push().setValue(model)
                                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void aVoid) {
                                                                    database.getReference().child("chats").child(receiverroom).push().setValue(model)
                                                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                @Override
                                                                                public void onSuccess(Void aVoid) {

                                                                                }
                                                                            });
                                                                }
                                                            });

                                                }
                                            });
                                        }

                                    }
                                });
                            }
                        });


                binding.attachmentimg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Pass in the mime type you'd like to allow the user to select
                        // as the input
                        mGetContent.launch("image/*");
                    }
                });


        /*activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Bundle bundle = result.getData().getExtras();
                    Bitmap bitmap = (Bitmap) bundle.get("data");
                    //imageview.setImageBitmap(bitmap);
                   //Uri selectedImage =bundle.get("data");

                    Calendar calendar=Calendar.getInstance();
                    StorageReference reference= storage.getReference().child("chats").child(senderroom).child(calendar.getTimeInMillis()+"");
                    reference.putFile();
                }
            }
        });*/


            }
        }