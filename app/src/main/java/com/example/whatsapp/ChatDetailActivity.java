package com.example.whatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.whatsapp.Adapters.ChatAdapter;
import com.example.whatsapp.Models.MessageModle;
import com.example.whatsapp.databinding.ActivityChatDetailBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class ChatDetailActivity extends AppCompatActivity {

    ActivityChatDetailBinding binding;
FirebaseDatabase database;
FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding= ActivityChatDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        database= FirebaseDatabase.getInstance();
        auth= FirebaseAuth.getInstance();

        final  String senderId = auth.getUid();
        String recieveId  = getIntent().getStringExtra("userId");
        String userName =getIntent().getStringExtra("userName");
        String profilepic =getIntent().getStringExtra("profilepic");


        binding.username.setText(userName);
        Picasso.get().load(profilepic).placeholder(R.drawable.persion).into(binding.profileImag);
        binding.backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(ChatDetailActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        final ArrayList<MessageModle> messageModles = new ArrayList<>();
        final ChatAdapter chatAdapter = new ChatAdapter(messageModles,this);
        binding.chatRecyclarview.setAdapter(chatAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.chatRecyclarview.setLayoutManager(layoutManager);

        final String senderRoom = senderId + recieveId;
        final  String receiverRoom = recieveId + senderId;

        database.getReference().child("chats")
                .child(senderRoom)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        messageModles.clear();
                        for(DataSnapshot Snapshot1: snapshot.getChildren()){
                            MessageModle modle= Snapshot1.getValue(MessageModle.class);
                            messageModles.add(modle);
                        }
             chatAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String message =  binding.etMessage.getText().toString();
               final MessageModle modle = new MessageModle(senderId,message);
               modle.setTimetemp(new Date().getTime());

               binding.etMessage.setText("");
               database.getReference().child("chats")
                       .child(senderRoom)
                       .push()
                        .setValue(modle).addOnSuccessListener(new OnSuccessListener<Void>() {
                   @Override
                   public void onSuccess(@NonNull Void unused) {
                       database.getReference().child("chats")
                               .child(receiverRoom)
                               .push()
                               .setValue(modle).addOnSuccessListener(new OnSuccessListener<Void>() {
                           @Override
                           public void onSuccess(@NonNull Void unused) {

                           }
                       });

                   }
               });

            }
        });

    }
}