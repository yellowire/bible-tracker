package com.example.bibletracker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.example.bibletracker.Bible.theBible;

@RequiresApi(api = Build.VERSION_CODES.O)
public class AddChapters extends AppCompatActivity {

    public static Bible bible = new Bible();
    public static SharedPreferences pref;
    public static SharedPreferences.Editor editor;
    public static ReadData data = null;
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    BibleBookChapter nextUp = null;
    BibleBookChapter bullpen = null;
    List<String> addToPrefs = new ArrayList<String>();
    String newLastRead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_chapters);
        Toolbar tooly = findViewById(R.id.tooly);
        setSupportActionBar(tooly);
        ActionBar barry = getSupportActionBar();
        assert barry != null;
        barry.setDisplayHomeAsUpEnabled(true);

        pref = getSharedPreferences("BiblePref",0);
        editor = pref.edit();

        getFreshData();
        setupDueList();
    }

    public void getFreshData() {
        data = new ReadData();
        nextUp = data.getNextUp();
        bullpen = data.getBullpen();
    }
    public void setupDueList() {

        int numberToShow = 5;

        LinearLayout readsDue = findViewById(R.id.readsDue);
        BibleBookChapter thisChapter = nextUp;
        if (thisChapter != null) {
            BibleBookChapter[] due = new BibleBookChapter[numberToShow];
            for (int i = 0; i < numberToShow; i++) {
                due[i] = thisChapter;
                thisChapter = thisChapter.getNextChapter();
            }

            readsDue.removeAllViews();
            CheckBox[] nextDue = new CheckBox[due.length];
            bullpen = thisChapter;

            for (int i = 0; i < due.length; i++) {
                nextDue[i] = new CheckBox(this);
                final BibleBookChapter dueChapter = due[i];
                final CheckBox thisCheck = buildCheckBox(nextDue[i], dueChapter);

                thisCheck.setOnCheckedChangeListener(
                        new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked) {
                                if (isChecked) {
                                    whenCheckedChanged(thisCheck, dueChapter);
                                }
                            }
                        }
                );
                readsDue.addView(thisCheck);
            }
        }
    }

    public CheckBox buildCheckBox(CheckBox thisCheck, BibleBookChapter thisChapter) {
        thisCheck.setTextSize(18);
        String dueString = theBible[thisChapter.getPosition()].getName() + " " + Integer.toString(thisChapter.getChapter());
        thisCheck.setText(dueString);
        return thisCheck;
    }
    public void whenCheckedChanged(final CheckBox thisCheck, final BibleBookChapter thisChapter) {
        LinearLayout checkParent = findViewById(R.id.readsDue);
        CheckBox first = (CheckBox) checkParent.getChildAt(0);
        if (thisCheck.isChecked() && thisCheck.getText().toString().compareTo(first.getText().toString()) == 0) {
            startAddSequence(thisCheck, thisChapter);
        } else {
            int position = 1;
            for (int i = 1; i < checkParent.getChildCount(); i++) {
                CheckBox thisChild = (CheckBox) checkParent.getChildAt(i);
                if (thisCheck.getText().toString().compareTo(thisChild.getText().toString()) == 0) {
                    position = i;
                }
            }
            boolean allChecked = true;
            for (int i = 0; i < position; i++) {
                CheckBox checky = (CheckBox) checkParent.getChildAt(i);
                if (!checky.isChecked()) {
                    allChecked = false;
                }
            }
            if (allChecked) {
                startAddSequence(thisCheck, thisChapter);
            } else {
                first.startAnimation(AnimationUtils.loadAnimation(this,R.anim.hey_you));
                thisCheck.setAlpha(1.0f);
                thisCheck.setText(thisCheck.getText().toString());
                thisCheck.setChecked(false);
            };
        }
    }
    public void showBullpen() {
        LinearLayout readsDue = findViewById(R.id.readsDue);
        CheckBox newCheck = new CheckBox(this);
        final BibleBookChapter thisChapter = bullpen;
        final CheckBox thisCheck = buildCheckBox(newCheck,thisChapter);
        thisCheck.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked) {
                        if (isChecked) {
                            whenCheckedChanged(thisCheck, thisChapter);
                        }
                    }
                }
        );
        readsDue.addView(thisCheck);
        bullpen = thisChapter.getNextChapter();
    }
    private void startAddSequence(final CheckBox thisCheck, final BibleBookChapter thisChapter) {
        animateStrikeThrough(thisCheck);
        thisCheck.animate().alpha(0.0f).setStartDelay(300).setDuration(200).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (thisCheck.isChecked()) {
                    String prev = pref.getString(thisChapter.getPrevChapter().getPosition() + " " + thisChapter.getPrevChapter().getChapter(), null);
                    if (prev != null) {
                        thisCheck.setVisibility(View.GONE);
                        showBullpen();
                        addToPrefChaps(thisChapter);
                    } else {
                        if (addToPrefs.contains(thisChapter.getPrevChapter().getPosition() + " " + thisChapter.getPrevChapter().getChapter())) {
                            thisCheck.setVisibility(View.GONE);
                            showBullpen();
                            addToPrefChaps(thisChapter);
                        } else {
                            thisCheck.setAlpha(1.0f);
                            thisCheck.setText(thisCheck.getText().toString());
                            thisCheck.setChecked(false);
                        }
                    }
                } else {
                    thisCheck.setAlpha(1.0f);
                    thisCheck.setText(thisCheck.getText().toString());
                    thisCheck.setChecked(false);
                }
            }
        });
    }
    private void addToPrefChaps(BibleBookChapter chapter) {
        String chapterString = chapter.getPosition() + " " + chapter.getChapter();
        addToPrefs.add(chapterString);
        newLastRead = chapterString;
    }
    private void animateStrikeThrough(final CheckBox check) {
        final int ANIM_DURATION = 300;              //duration of animation in millis
        final int length = check.getText().length();
        new CountDownTimer(ANIM_DURATION, ANIM_DURATION/(length)) {
            Spannable span = new SpannableString(check.getText());
            StrikethroughSpan strikethroughSpan = new StrikethroughSpan();

            @Override
            public void onTick(long millisUntilFinished) {
                //calculate end position of strikethrough in textview
                int endPosition = (int) (((millisUntilFinished-ANIM_DURATION)*-1)/(ANIM_DURATION/length));
                endPosition = Math.min(endPosition+1, length);
                span.setSpan(strikethroughSpan, 0, endPosition,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                check.setText(span);
            }

            @Override
            public void onFinish() { }
        }.start();
    }
    public void onCancelClick(View v) { finish(); }
    public void onSaveClick(View v) {
        LocalDate today = LocalDate.now();
        String todayString = today.format(dateFormat);

        for (int i = 0; i < addToPrefs.size(); i++) {
            String chapterString = addToPrefs.get(i);
            editor.putString(chapterString,todayString);
        }
        editor.putString("lastReadChapter",newLastRead);
        editor.apply();
        finish();
    }
}