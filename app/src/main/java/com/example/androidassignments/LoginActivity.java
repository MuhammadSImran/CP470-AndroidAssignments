package com.example.androidassignments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    Button loginButton;
    EditText emailText;
    EditText passwordText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Log.d("LoginActivity", "onCreate called");

        emailText = findViewById(R.id.email_Text);
        passwordText = findViewById(R.id.password_Text);
        loginButton = findViewById(R.id.login_button);

        sharedPreferences = getSharedPreferences("LoginPrefs",MODE_PRIVATE);

        String savedEmail = sharedPreferences.getString("DefaultEmail","email@domain.com");

        emailText.setText(savedEmail);

        loginButton.setOnClickListener(v -> {
            String enteredEmail = emailText.getText().toString();
            String enteredpassword = passwordText.getText().toString();

            if (!validateEmail(enteredEmail)) {
                emailText.setError("Invalid email address");
                return; // Stop further execution if email is invalid
            }

            if (TextUtils.isEmpty(enteredpassword)) {
                passwordText.setError("Password cannot be empty");
                return; // Stop further execution if password is empty
            }

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("DefaultEmail", enteredEmail);
            editor.apply();

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        });

    }
    @Override
    protected void onStart(){
        super.onStart();
        Log.d("LoginActivity", "onStart called");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.d("LoginActivity","onResume called");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.d("LoginActivity","onPause called");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.d("LoginActivity","onStop called");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d("LoginActivity","onDestroy called");
    }

    @Override
    protected void onSaveInstanceState(Bundle outstate){
        super.onSaveInstanceState(outstate);

        Log.d("LoginActivity","onSaveInstanceState called");

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);

        Log.d("LoginActivity","onRestoreInstanceState called");
    }

    private boolean validateEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

}