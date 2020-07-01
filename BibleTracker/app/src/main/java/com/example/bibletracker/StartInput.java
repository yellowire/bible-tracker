package com.example.bibletracker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.NumberPicker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.example.bibletracker.Bible.theBible;

@RequiresApi(api = Build.VERSION_CODES.O)
public class StartInput extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (MainActivity.editor != null) {
            MainActivity.editor.clear();
            MainActivity.editor.apply();
        }
        setContentView(R.layout.activity_start_input);

        String[] bibleBooks = new String[66];
        for (int i=0; i<66; i++) {
            bibleBooks[i] = theBible[i].getName();
        }

        NumberPicker bookList = findViewById(R.id.books);
        bookList.setMinValue(0);
        bookList.setDisplayedValues(bibleBooks);
        bookList.setMaxValue(65);

        int numberOf = theBible[0].getChapters();
        NumberPicker chaps = findViewById(R.id.chapters);
        chaps.setMinValue(1);
        chaps.setMaxValue(numberOf);

        bookList.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                int numberOf = theBible[newVal].getChapters();
                NumberPicker chaps = findViewById(R.id.chapters);
                chaps.setMinValue(1);
                chaps.setMaxValue(numberOf);
            }
        });


    }

    public void onStartInputSubmit(View v) {
        NumberPicker books = findViewById(R.id.books);
        int startBook = books.getValue();
        NumberPicker chapters = findViewById(R.id.chapters);
        int startChapter = chapters.getValue();
        DatePicker date = findViewById(R.id.startDate);
        int startDay = date.getDayOfMonth();
        int startMonth = date.getMonth() + 1;
        int startYear = date.getYear();
        LocalDate startDate = LocalDate.of(startYear, startMonth, startDay);

        if (!saveInt("startBook",startBook) || !saveInt("startChapter",startChapter) || !saveDate("startDate",startDate)) {
            Log.d("STARTINPUT","Save to shared preferences failed!");
        } else {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    }

    public Boolean saveDate(String key, LocalDate value) {

        try {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String valueString = value.format(format);
            MainActivity.editor.putString(key,valueString);
            MainActivity.editor.apply();
        }
        catch(Exception e) {
            Log.d("STARTINPUT","Save to shared preferences failed: " + e.getMessage());
            return false;
        }

        return true;
    }

    public Boolean saveInt(String key, int value) {

        try {
            MainActivity.editor.putInt(key,value);
            MainActivity.editor.apply();
        }
        catch(Exception e) {
            Log.d("STARTINPUT","Save to shared preferences failed: " + e.getMessage());
            return false;
        }

        return true;
    }

}
