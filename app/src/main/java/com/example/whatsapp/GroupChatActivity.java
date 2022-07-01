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
import com.example.whatsapp.databinding.ActivityGroupChatBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class GroupChatActivity extends AppCompatActivity {


    ActivityGroupChatBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityGroupChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        binding.backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(GroupChatActivity.this,MainActivity.class);
                 startActivity(intent);
            }
        });
        final FirebaseDatabase database= FirebaseDatabase.getInstance();
    final ArrayList<MessageModle> messageModles = new ArrayList<>();

    final  String senderId = FirebaseAuth.getInstance().getUid();
    binding.username.setText("Friends Group");
        final ChatAdapter adapter= new ChatAdapter(messageModles,this);
        binding.chatRecyclarview.setAdapter(adapter);

        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        binding.chatRecyclarview.setLayoutManager(layoutManager);

        database.getReference().child("GroupChat")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        messageModles.clear();
                        for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                            MessageModle modle=dataSnapshot.getValue(MessageModle.class);
                            messageModles.add(modle);
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final  String message= binding.etMessage.getText().toString();
                final MessageModle modle=new MessageModle(senderId,message);
                modle.setTimetemp(new Date().getTime());
                binding.etMessage.setText("");
                database.getReference().child("Group Chat")
                        .push()
                        .setValue(modle).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(@NonNull Void unused) {

                    }
                });



            }
        });


    }
}