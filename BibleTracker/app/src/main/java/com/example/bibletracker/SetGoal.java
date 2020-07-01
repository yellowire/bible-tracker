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
    NumberPicker monthsPicker;
    RadioGroup typeGroup;
    LinearLayout chapterSide;
    LinearLayout monthsSide;
    RadioButton chapterButton;
    RadioButton monthsButton;
    String goalType;

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
        monthsPicker = findViewById(R.id.monthsPicker);
        chapterPicker.setMinValue(1);
        chapterPicker.setMaxValue(10);
        monthsPicker.setMinValue(6);
        monthsPicker.setMaxValue(60);

        typeGroup = findViewById(R.id.chapterMonthsGroup);
        chapterSide = findViewById(R.id.chapterLayout);
        monthsSide = findViewById(R.id.monthsLayout);
        chapterButton = findViewById(R.id.chapterRadio);
        monthsButton = findViewById(R.id.monthsRadio);
        if (monthsButton.isChecked()) {
            goalType = "months";
        } else {
            goalType = "chapters";
        }

        typeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId) {
                    case R.id.monthsRadio:
                        chapterButton.setBackground(null);
                        monthsButton.setBackground(getDrawable(R.drawable.underline));
                        chapterSide.setVisibility(View.GONE);
                        monthsSide.setVisibility(View.VISIBLE);
                        goalType = "months";
                        break;
                    case R.id.chapterRadio:
                        monthsButton.setBackground(null);
                        chapterButton.setBackground(getDrawable(R.drawable.underline));
                        goalType = "chapter";
                        monthsSide.setVisibility(View.GONE);
                        chapterSide.setVisibility(View.VISIBLE);
                        break;
                    default:
                        goalType = null;
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
    }

    public void onSaveGoal(View v) {
        String goal;
        NumberPicker picker;
        if (goalType.equals("months")) {
            picker = findViewById(R.id.monthsPicker);
        } else if (goalType.equals("chapters")) {
            picker = findViewById(R.id.chapterPicker);
        } else {
            picker = null;
        }
        goal = Integer.toString(picker.getValue());

        SharedPreferences pref = getApplicationContext().getSharedPreferences("BiblePref",0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("goalValue",goal);
        editor.putString("goalType",goalType);
        editor.apply();
        finish();
    }
    public void onCancelClick(View view) { finish(); }
}
