package com.example.androidassignments;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ListItemsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_items);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    @Override
    protected void onStart(){
        super.onStart();
        Log.d("ListItemsActivity", "onStart called");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.d("ListItemsActivity","onResume called");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.d("ListItemsActivity","onPause called");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.d("ListItemsActivity","onStop called");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d("ListItemsActivity","onDestroy called");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);

        Log.d("ListItemsActivity","onSaveInstanceState called");

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);

        Log.d("ListItemsActivity","onRestoreInstanceState called");
    }
}