package com.example.androidassignments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.material.snackbar.Snackbar;
import android.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.androidassignments.databinding.ActivityTestToolbarBinding;


public class TestToolbar extends AppCompatActivity {

    private ActivityTestToolbarBinding binding;
    private String newMessage = "Default Snackbar message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the content view and initialize view binding
        binding = ActivityTestToolbarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set up the toolbar as the action bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set up the Floating Action Button (FAB) click listener
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "For support, contact imra5970@mylaurier.ca", Snackbar.LENGTH_LONG)
                        .setAnchorView(R.id.fab)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu for the toolbar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        // Handle toolbar item selections
        int id = menuItem.getItemId();
        if (id == R.id.action_one) {
            Log.d("Toolbar", "Choice 1 selected");
            Snackbar.make(findViewById(R.id.toolbar), newMessage, Snackbar.LENGTH_LONG).show();
        } else if (id == R.id.action_two) {
            Log.d("Toolbar", "Choice 2 selected");

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.dialogue_back)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int id) {
                            // User clicked OK button, finish the activity
                            finish();
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog, do nothing
                            dialog.dismiss();
                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();

            //Intent intent = new Intent(this, MainActivity.class);
           // startActivity(intent);
        } else if (id == R.id.action_three) {
            Log.d("Toolbar", "Choice 3 selected");
            showCustomDialog();
        } else if (id == R.id.action_about) {
            Toast.makeText(this, "Version 2.4, by Muhammad Imran", Toast.LENGTH_LONG).show();
        } else {
            return super.onOptionsItemSelected(menuItem);
        }
        return true;
    }
    private void showCustomDialog() {
        // Inflate the custom layout
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.dialog_custom, null);

        // Find the EditText in the custom layout
        final EditText editTextNewMessage = dialogView.findViewById(R.id.edittext_new_message);

        // Create the AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView)
                .setTitle("Enter a new message")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Get the inputted message and save it
                        newMessage = editTextNewMessage.getText().toString();
                        if (newMessage.isEmpty()) {
                            newMessage = "Default Snackbar message";  // Set a default if input is empty
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        // Show the dialog
        builder.create().show();
    }
}