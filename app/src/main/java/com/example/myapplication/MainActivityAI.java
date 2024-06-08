package com.example.myapplication;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.ViewGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivityAI extends AppCompatActivity {

    AISurfaceView ds;
    Thread thread;
    boolean userAskBack = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_ai);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ds = new AISurfaceView(this);
        thread = new Thread(ds);
        thread.start();

        ViewGroup.LayoutParams layoutParams = new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        addContentView(ds, layoutParams);

    }

    @Override
    public void finish() {
        super.finish();
        userAskBack = true;
        ds.threadRunning = false;
        while (true){
            try {
                thread.join(); //הורס אותו
            }
            catch (Exception e){
                e.printStackTrace();
            }
            break;
        }
    }

    public void moveToProfileFragment() {

    }
}