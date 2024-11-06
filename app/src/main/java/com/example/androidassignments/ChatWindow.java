package com.example.androidassignments;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

    private static final String ACTIVITY_NAME = "ChatWindow";
    private ChatDatabaseHelper dbHelper;
    private SQLiteDatabase db;

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
        Log.d("ChatWindow", "onCreate called");
        chatListView = findViewById(R.id.chat_list_view);
        messageInput = findViewById(R.id.message_input);
        sendButton = findViewById(R.id.send_button);

        chatMessages = new ArrayList<>();

        dbHelper = new ChatDatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        Cursor cursor = db.query(ChatDatabaseHelper.TABLE_NAME, null, null, null, null, null, null);
        Log.i(ACTIVITY_NAME, "Cursor’s column count = " + cursor.getColumnCount());


        for (int i = 0; i < cursor.getColumnCount(); i++) {
            Log.i(ACTIVITY_NAME, "Column " + i + ": " + cursor.getColumnName(i));
        }

        while (cursor.moveToNext()) {
            String message = cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE));
            chatMessages.add(message); // Add each message to the chatMessages ArrayList
            Log.i(ACTIVITY_NAME, "SQL MESSAGE: " + message); // Log each message
        }

        cursor.close();

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

                    ContentValues values = new ContentValues();
                    values.put(ChatDatabaseHelper.KEY_MESSAGE, message); // Add message to ContentValues
                    db.insert(ChatDatabaseHelper.TABLE_NAME, null, values); // Insert into database
                    Log.i(ACTIVITY_NAME, "Inserted message into database: " + message);

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
    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
        dbHelper.close();
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
