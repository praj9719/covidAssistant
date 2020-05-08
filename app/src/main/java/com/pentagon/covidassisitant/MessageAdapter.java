package com.pentagon.covidassisitant;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageHolder> {
    private Activity context;
    private List<Message> mList;
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;

    MessageAdapter(Activity context, List<Message> mList){
        this.context = context;
        this.mList = mList;
    }

    @Override
    public int getItemViewType(int position) {
        Message message = mList.get(position);
        if (message.isB()){
            return VIEW_TYPE_MESSAGE_SENT;
        }else {
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    @NonNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_MESSAGE_SENT){
            View view = LayoutInflater.from(context).inflate(R.layout.send_layout, parent, false);
            return new MessageHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.recive_layout, parent, false);
            return new MessageHolder(view);
        }
    }
    @Override
    public void onBindViewHolder(@NonNull MessageHolder holder, int position) {
        Message message = mList.get(position);
        holder.mMessage.setText(message.getMessage());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MessageHolder extends RecyclerView.ViewHolder {
        public TextView mMessage;
        public MessageHolder(@NonNull View itemView) {
            super(itemView);
            mMessage = itemView.findViewById(R.id.text_view_message);
        }
    }
}
