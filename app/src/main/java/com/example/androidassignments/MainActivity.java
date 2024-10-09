package com.example.androidassignments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Log.d("MainActivity", "onCreate called");

        Button button = findViewById(R.id.button);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ListItemsActivity.class);

            startActivityForResult(intent, 10);

        });

        Button chatbutton = findViewById(R.id.chat_button);
        chatbutton.setOnClickListener(v ->{
            Log.d("MainActivity", "User clicked Start Chat");
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10) {
            Log.i("MainActivity", "Returned to MainActivity.onActivityResult");
            if(resultCode == Activity.RESULT_OK){
                String messagePassed = data.getStringExtra("Response");
                if(messagePassed != null){
                    Toast.makeText(this,"ListItemsActivity passed:" + messagePassed, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.d("MainActivity", "onStart called");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.d("MainActivity","onResume called");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.d("MainActivity","onPause called");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.d("MainActivity","onStop called");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d("MainActivity","onDestroy called");
    }

    @Override
    protected void onSaveInstanceState(Bundle outstate){
        super.onSaveInstanceState(outstate);

        Log.d("MainActivity","onSaveInstanceState called");

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);

        Log.d("MainActivity","onRestoreInstanceState called");
    }

}