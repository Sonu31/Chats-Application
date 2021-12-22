package com.example.whatsapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsapp.Models.MessageModle;
import com.example.whatsapp.R;
import com.google.firebase.auth.FirebaseAuth;

import java.time.temporal.Temporal;
import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter       {

    ArrayList<MessageModle>  messageModles;
    Context context;
    int SENDER_VIEW_TYPE =1;
    int RECCEIVER_VIEW_TYPE=2;


    public ChatAdapter(ArrayList<MessageModle> messageModles, Context context) {
        this.messageModles = messageModles;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


if (viewType== SENDER_VIEW_TYPE)
{
    View view= LayoutInflater.from(context).inflate(R.layout.sample_sender,parent,false);
    return new senderViewVolder(view);
}
        else

{
    View view= LayoutInflater.from(context).inflate(R.layout.sample_reciever,parent,false);
    return new RecieverViewVolder(view);
}
    }

    @Override
    public int getItemViewType(int position) {
if(messageModles.get(position).getuId().equals(FirebaseAuth.getInstance().getUid())) {

    return SENDER_VIEW_TYPE;
}
    else
    {
        return RECCEIVER_VIEW_TYPE;
    }

}


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MessageModle messageModle= messageModles.get(position);
        if(holder.getClass()== senderViewVolder.class){
            ((senderViewVolder)holder).senderMsg.setText(messageModle.getMessage());
        }
        else {
            ((RecieverViewVolder)holder).recieverMsg.setText(messageModle.getMessage());
        }



    }

    @Override
    public int getItemCount() {

        return messageModles.size();
    }

    public class  RecieverViewVolder extends RecyclerView.ViewHolder {


        TextView recieverMsg, recievertime;


        public RecieverViewVolder(@NonNull View itemView) {
            super(itemView);

            recieverMsg = itemView.findViewById(R.id.recieverText);
            recievertime = itemView.findViewById(R.id.reiverTime);
        }
    }

    public class  senderViewVolder extends RecyclerView.ViewHolder{

        TextView senderMsg, sendertime;
        public senderViewVolder(@NonNull View itemView) {
            super(itemView);

            senderMsg = itemView.findViewById(R.id.senderText);
            sendertime = itemView.findViewById(R.id.senderTime);
        }
    }



}
