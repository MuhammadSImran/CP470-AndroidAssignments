package com.example.androidassignments;

import android.os.Bundle;
import android.util.Log;

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