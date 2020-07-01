package com.example.bibletracker;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;

public class ToBeAdded extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_be_added);
        Toolbar tooly = findViewById(R.id.tooly);
        setSupportActionBar(tooly);
        ActionBar barry = getSupportActionBar();
        assert barry != null;
        barry.setDisplayHomeAsUpEnabled(true);
    }

    public void onBackClicked(View view) {
        finish();
    }
}