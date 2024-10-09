package com.example.androidassignments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class ChatWindow extends AppCompatActivity {

    ListView chatListView;
    EditText messageInput;
    Button sendButton;
    ArrayList<String> chatMessages;
    ChatAdapter chatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat_window);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        chatListView = findViewById(R.id.chat_list_view);
        messageInput = findViewById(R.id.message_input);
        sendButton = findViewById(R.id.send_button);

        chatMessages = new ArrayList<>();

          chatAdapter = new ChatAdapter(this);
          chatListView.setAdapter(chatAdapter);
          chatAdapter.notifyDataSetChanged();

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = messageInput.getText().toString().trim();
                Log.d("ChatWindow", "Send button clicked");

                if (!message.isEmpty()) {
                    Log.d("ChatWindow", "Message to add: " + message);
                    chatMessages.add(message);
                    chatAdapter.notifyDataSetChanged();
                    Log.d("ChatWindow", "Total messages: " + chatMessages.size());

                    messageInput.setText("");
                } else {
                    Log.d("ChatWindow", "Empty message, not adding.");
                }
            }
        });
    }
    private class ChatAdapter extends ArrayAdapter<String> {

        // Constructor for ChatAdapter
        public ChatAdapter(Context ctx) {
            super(ctx, 0, chatMessages);
        }

        @Override
        public int getCount() {
            return chatMessages.size();
        }

        @Override
        public String getItem(int position) {
            return chatMessages.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Log.d("ChatWindow", "getView called for position: " + position);
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();


            View result = convertView;


            if (position % 2 == 0) {

                result = inflater.inflate(R.layout.chat_row_incoming, parent, false);
            } else {

                result = inflater.inflate(R.layout.chat_row_outgoing, parent, false);
            }


            TextView message = (TextView) result.findViewById(R.id.message_text);


            message.setText(getItem(position));

            return result;


        }
    }
}
