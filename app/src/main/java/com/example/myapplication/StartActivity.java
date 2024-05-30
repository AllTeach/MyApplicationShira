package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

public class StartActivity extends FragmentActivity {
    private ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        viewPager = findViewById(R.id.view_pager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), getLifecycle());
        viewPager.setAdapter(adapter);

        // Set the ViewPager2 to display the SignupTabFragment
        viewPager.setCurrentItem(1);


        // Retrieve the user's email from shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences("details", MODE_PRIVATE);
        String userEmail = sharedPreferences.getString("KEY_USER_EMAIL", "");

        // If the user's email exists, show the dialog
        if (!userEmail.isEmpty()) {
            new AlertDialog.Builder(this)
                    .setTitle("Hello " + userEmail)
                    .setMessage("Do you want to continue with your current details?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Continue with current details
                            moveToProfileActivity();
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Close the dialog
                            dialog.dismiss();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }

    public void moveToProfileActivity() {
        // Start the ProfileActivity
        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);
        finish();
    }
}