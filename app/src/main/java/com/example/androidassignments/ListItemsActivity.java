package com.example.androidassignments;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ListItemsActivity extends AppCompatActivity {
    private static final int CAMERA_PERMISSION_CODE = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private ImageButton imageButton;
    private CheckBox myCheckBox;
    private Uri imageUri;

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

        imageButton = findViewById(R.id.imageButton1);
        Switch mySwitch = findViewById(R.id.my_switch);
        myCheckBox = findViewById(R.id.my_checkbox);

        myCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                showExitConfirmationDialog();
            }
        });

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
        }

        imageButton.setOnClickListener(this::imageClicked);

        print("ListItemsActivity started.");
        mySwitch.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            String text;
            int duration;

            if (isChecked) {
                text = "Switch is On";
                duration = Toast.LENGTH_SHORT;
            } else {
                text = "Switch is Off";
                duration = Toast.LENGTH_LONG;
            }
            print(text);
        });
    }

    public void print(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void imageClicked(View imageView) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageButton.setImageBitmap(imageBitmap);
            saveImage(imageBitmap);
        }
    }

    public void saveImage(Bitmap imageBitmap) {
        Date date = new Date();
        String strDateFormat = "hh_mm_ss_a"; // File name cannot contain colons
        java.text.DateFormat dateFormat = new java.text.SimpleDateFormat(strDateFormat);
        String formattedDate = dateFormat.format(date) + ".png"; // Add PNG extension

        try {
            FileOutputStream outputStream = openFileOutput(formattedDate, MODE_PRIVATE);
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
            outputStream.flush();
            outputStream.close();
            Toast.makeText(this, "Image saved: " + formattedDate, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("ListItemsActivity", "Error saving image: " + e.getMessage());
        }
    }

    private void showExitConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ListItemsActivity.this);
        builder.setMessage(R.string.dialog_message)
                .setTitle(R.string.dialog_title)
                .setPositiveButton(R.string.ok, (dialog, id) -> {
                    returnToMainActivity(); // Finish and return to MainActivity
                })
                .setNegativeButton(R.string.cancel, (dialog, id) -> {
                    myCheckBox.setChecked(false); // Uncheck the checkbox
                })
                .show(); // Display the dialog
    }

    private void returnToMainActivity() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("Response", "My information to share");
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                imageButton.setOnClickListener(this::imageClicked);
            } else {
                Toast.makeText(this, "Camera permission is required to take pictures", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
