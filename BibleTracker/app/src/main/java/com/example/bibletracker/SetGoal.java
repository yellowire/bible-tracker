package com.example.bibletracker;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class SetGoal extends AppCompatActivity {

    NumberPicker chapterPicker;
    NumberPicker daysPicker;
    NumberPicker monthsPicker;
    RadioGroup typeGroup;
    LinearLayout chapterSide;
    LinearLayout monthsSide;
    RadioButton chapterButton;
    RadioButton monthsButton;
    int goalTime;
    int goalChapters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_goal);
        Toolbar tooly = findViewById(R.id.tooly);
        setSupportActionBar(tooly);
        ActionBar barry = getSupportActionBar();
        assert barry != null;
        barry.setDisplayHomeAsUpEnabled(true);

        setupGoalViews();
    }
    public void setupGoalViews() {
        chapterPicker = findViewById(R.id.chapterPicker);
        daysPicker = findViewById(R.id.daysPicker);
        monthsPicker = findViewById(R.id.monthsPicker);
        chapterPicker.setMinValue(1);
        chapterPicker.setMaxValue(20);
        daysPicker.setMinValue(1);
        daysPicker.setMaxValue(7);
        monthsPicker.setMinValue(6);
        monthsPicker.setMaxValue(60);

        typeGroup = findViewById(R.id.chapterMonthsGroup);
        chapterSide = findViewById(R.id.chapterLayout);
        monthsSide = findViewById(R.id.monthsLayout);
        chapterButton = findViewById(R.id.chapterRadio);
        monthsButton = findViewById(R.id.monthsRadio);

        typeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId) {
                    case R.id.monthsRadio:
                        chapterButton.setBackground(null);
                        monthsButton.setBackground(getDrawable(R.drawable.underline));
                        chapterSide.setVisibility(View.GONE);
                        monthsSide.setVisibility(View.VISIBLE);
                        break;
                    case R.id.chapterRadio:
                        monthsButton.setBackground(null);
                        chapterButton.setBackground(getDrawable(R.drawable.underline));
                        monthsSide.setVisibility(View.GONE);
                        chapterSide.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });

        final TextView chapterLabel = findViewById(R.id.textView6);
        chapterPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if (newVal > 1) {
                    chapterLabel.setText("chapters");
                } else {
                    chapterLabel.setText("chapter");
                }
            }
        });

        final TextView dayLabel = findViewById(R.id.textView8);
        daysPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if (newVal > 1) {
                    dayLabel.setText("days");
                } else {
                    dayLabel.setText("day");
                }
            }
        });
    }

    public void onSaveGoal(View v) {
        int checkedId = typeGroup.getCheckedRadioButtonId();
        switch(checkedId) {
            case R.id.monthsRadio:
                goalChapters = 0;
                goalTime = monthsPicker.getValue();
                break;
            case R.id.chapterRadio:
                goalChapters = chapterPicker.getValue();
                goalTime = daysPicker.getValue();
                break;
            default:
                goalChapters = -1;
                goalTime = 0;
        }

        SharedPreferences pref = getApplicationContext().getSharedPreferences("BiblePref",0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("goalChapters",goalChapters);
        editor.putInt("goalTime",goalTime);
        editor.apply();
        finish();
    }
    public void onCancelClick(View view) { finish(); }
}
