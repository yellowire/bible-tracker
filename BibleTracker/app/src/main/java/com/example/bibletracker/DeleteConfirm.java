package com.example.bibletracker;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import static com.example.bibletracker.MainActivity.pref;

public class DeleteConfirm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_confirm);
        Toolbar tooly = findViewById(R.id.tooly);
        setSupportActionBar(tooly);
        ActionBar barry = getSupportActionBar();
        assert barry != null;
        barry.setDisplayHomeAsUpEnabled(true);
    }

    public void onCancelClick(View view) {
        finish();
    }

    public void onDeleteClick(View view) {
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.apply();

        finish();
    }
}