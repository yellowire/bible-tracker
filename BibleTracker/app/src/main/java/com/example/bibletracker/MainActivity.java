package com.example.bibletracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BubbleChart;
import com.github.mikephil.charting.components.IMarker;
import com.github.mikephil.charting.data.BubbleData;
import com.github.mikephil.charting.data.BubbleDataSet;
import com.github.mikephil.charting.data.BubbleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.interfaces.datasets.IBubbleDataSet;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static Bible bible = new Bible();
    public static SharedPreferences pref;
    public static SharedPreferences.Editor editor;
    public static ReadData data = null;
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    int totalChapters = 0;
//    int goalType = 0;
//    int goalValue = 0;
    int goalChapters = -1;
    int goalTime = 0;
    int dateDiff = 0;
    int streakCount = 0;
    int readChapterCount = 0;
    LocalDate prevDate;
    LocalDate nextDate;
    static BubbleChart chart;
    static Map<Entry, String> detailMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar tooly = findViewById(R.id.tooly);
        setSupportActionBar(tooly);

        if (started()) {
/*
            LocalDate date = LocalDate.now().minusMonths(1);
            editor.putString("40 1",date.format(dateFormat));
            editor.putString("40 2",date.format(dateFormat));
            editor.putString("40 3",date.format(dateFormat));
            editor.putString("40 4",date.format(dateFormat));
            editor.putString("40 5",date.format(dateFormat));
            editor.putString("40 6",date.plusDays(1).format(dateFormat));
            editor.putString("40 7",date.plusDays(1).format(dateFormat));
            editor.putString("40 8",date.plusDays(1).format(dateFormat));
            editor.putString("40 9",date.plusDays(1).format(dateFormat));
            editor.putString("40 10",date.plusDays(1).format(dateFormat));
            editor.putString("40 11",date.plusDays(2).format(dateFormat));
            editor.putString("40 12",date.plusDays(2).format(dateFormat));
            editor.putString("40 13",date.plusDays(2).format(dateFormat));
            editor.putString("40 14",date.plusDays(2).format(dateFormat));
            editor.putString("40 15",date.plusDays(3).format(dateFormat));
            editor.putString("40 16",date.plusDays(3).format(dateFormat));
            editor.putString("41 1",date.plusDays(4).format(dateFormat));
            editor.putString("41 2",date.plusDays(4).format(dateFormat));
            editor.putString("41 3",date.plusDays(4).format(dateFormat));
            editor.putString("41 4",date.plusDays(4).format(dateFormat));
            editor.putString("41 5",date.plusDays(5).format(dateFormat));
            editor.putString("41 6",date.plusDays(5).format(dateFormat));
            editor.putString("41 7",date.plusDays(5).format(dateFormat));
            editor.putString("41 8",date.plusDays(5).format(dateFormat));
            editor.putString("41 9",date.plusDays(6).format(dateFormat));
            editor.putString("41 10",date.plusDays(6).format(dateFormat));
            editor.putString("41 11",date.plusDays(6).format(dateFormat));
            editor.putString("41 12",date.plusDays(6).format(dateFormat));
            editor.putString("41 13",date.plusDays(7).format(dateFormat));
            editor.putString("41 14",date.plusDays(7).format(dateFormat));
            editor.putString("41 15",date.plusDays(7).format(dateFormat));
            editor.putString("41 16",date.plusDays(7).format(dateFormat));
            editor.putString("41 17",date.plusDays(8).format(dateFormat));
            editor.putString("41 18",date.plusDays(8).format(dateFormat));
            editor.putString("41 19",date.plusDays(8).format(dateFormat));
            editor.putString("41 20",date.plusDays(8).format(dateFormat));
            editor.putString("41 21",date.plusDays(9).format(dateFormat));
            editor.putString("41 22",date.plusDays(9).format(dateFormat));
            editor.putString("41 23",date.plusDays(9).format(dateFormat));
            editor.putString("41 24",date.plusDays(9).format(dateFormat));
            editor.putString("42 1",date.plusDays(10).format(dateFormat));
            editor.putString("42 2",date.plusDays(10).format(dateFormat));
            editor.putString("42 3",date.plusDays(10).format(dateFormat));
            editor.putString("42 4",date.plusDays(11).format(dateFormat));
            editor.putString("42 5",date.plusDays(11).format(dateFormat));
            editor.putString("42 6",date.plusDays(11).format(dateFormat));
            editor.putString("42 7",date.plusDays(12).format(dateFormat));
            editor.putString("42 8",date.plusDays(12).format(dateFormat));
            editor.putString("42 9",date.plusDays(12).format(dateFormat));
            editor.putString("42 10",date.plusDays(13).format(dateFormat));
            editor.putString("42 11",date.plusDays(13).format(dateFormat));
            editor.putString("42 12",date.plusDays(13).format(dateFormat));
            editor.putString("42 13",date.plusDays(14).format(dateFormat));
            editor.putString("42 14",date.plusDays(14).format(dateFormat));
            editor.putString("42 15",date.plusDays(14).format(dateFormat));
            editor.putString("42 16",date.plusDays(15).format(dateFormat));
            editor.putString("42 17",date.plusDays(15).format(dateFormat));
            editor.putString("42 18",date.plusDays(15).format(dateFormat));
            editor.putString("42 19",date.plusDays(16).format(dateFormat));
            editor.putString("42 20",date.plusDays(16).format(dateFormat));
            editor.putString("42 21",date.plusDays(16).format(dateFormat));
            editor.putString("43 1",date.plusDays(17).format(dateFormat));
            editor.putString("43 2",date.plusDays(17).format(dateFormat));
            editor.putString("43 3",date.plusDays(17).format(dateFormat));
            editor.putString("43 4",date.plusDays(18).format(dateFormat));
            editor.putString("43 5",date.plusDays(18).format(dateFormat));
            editor.putString("43 6",date.plusDays(18).format(dateFormat));
            editor.putString("43 7",date.plusDays(19).format(dateFormat));
            editor.putString("43 8",date.plusDays(19).format(dateFormat));
            editor.putString("43 9",date.plusDays(19).format(dateFormat));
            editor.putString("43 10",date.plusDays(20).format(dateFormat));
            editor.putString("43 11",date.plusDays(20).format(dateFormat));
            editor.putString("43 12",date.plusDays(20).format(dateFormat));
            editor.putString("43 13",date.plusDays(21).format(dateFormat));
            editor.putString("43 14",date.plusDays(21).format(dateFormat));
            editor.putString("43 15",date.plusDays(21).format(dateFormat));
            editor.putString("43 16",date.plusDays(23).format(dateFormat));
            editor.putString("43 17",date.plusDays(23).format(dateFormat));
            editor.putString("43 18",date.plusDays(24).format(dateFormat));
            editor.putString("43 19",date.plusDays(24).format(dateFormat));
            editor.putString("43 20",date.plusDays(24).format(dateFormat));
            editor.putString("43 21",date.plusDays(25).format(dateFormat));
            editor.putString("43 22",date.plusDays(25).format(dateFormat));
            editor.putString("43 23",date.plusDays(25).format(dateFormat));
            editor.putString("43 24",date.plusDays(26).format(dateFormat));
            editor.putString("43 25",date.plusDays(26).format(dateFormat));
            editor.putString("43 26",date.plusDays(26).format(dateFormat));
            editor.putString("43 27",date.plusDays(26).format(dateFormat));
            editor.putString("43 28",date.plusDays(26).format(dateFormat));
            editor.putString("44 1",date.plusDays(27).format(dateFormat));
            editor.putString("44 2",date.plusDays(27).format(dateFormat));
            editor.putString("44 3",date.plusDays(27).format(dateFormat));
            editor.putString("44 4",date.plusDays(27).format(dateFormat));
            editor.putString("44 5",date.plusDays(27).format(dateFormat));
            editor.putString("44 6",date.plusDays(27).format(dateFormat));
            editor.putString("44 7",date.plusDays(28).format(dateFormat));
            editor.putString("44 8",date.plusDays(28).format(dateFormat));
            editor.putString("44 9",date.plusDays(28).format(dateFormat));
            editor.putString("44 10",date.plusDays(28).format(dateFormat));
            editor.putString("44 11",date.plusDays(28).format(dateFormat));
            editor.putString("44 12",date.plusDays(29).format(dateFormat));
            editor.putString("44 13",date.plusDays(29).format(dateFormat));
            editor.putString("44 14",date.plusDays(29).format(dateFormat));
            editor.putString("44 15",date.plusDays(29).format(dateFormat));
            editor.putString("44 16",date.plusDays(29).format(dateFormat));
            editor.putString("lastReadChapter","44 16");
            editor.remove("lastChapterRead");
            editor.apply();
 */

            getFreshData();
            setupKPIs();
            setupProgressBar();
            setupHistoryChart();
            setupGoalView();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        if (started()) {
            getFreshData();
            setupKPIs();
            setupProgressBar();
            setupHistoryChart();
            setupGoalView();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_addChapters:
                goToAddChapter();
                return true;

            case R.id.action_setGoal:
                goToSetGoal();
                return true;

            case R.id.action_deleteData:
                // User chose the "Delete all data" action, verify and delete...
                goToDelete();
                return true;

            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                goToSettings();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
    public boolean started() {
        try {
            pref = getSharedPreferences("BiblePref",0);
            editor = pref.edit();
        } catch(Exception e) {
            Intent intent = new Intent(getApplicationContext(), StartInput.class);
            startActivity(intent);
            return false;
        }
        if (pref.contains("startDate")) {
            return true;
        }
        Intent intent = new Intent(getApplicationContext(), StartInput.class);
        startActivity(intent);
        return false;
    }

    public void getFreshData() {
        data = new ReadData();
        totalChapters = data.getTotalChapterCount();
        readChapterCount = data.getReadChapterCount();
//        goalType = data.getGoalType();
//        goalValue = data.getGoalValue();
        goalChapters = data.getGoalChapters();
        goalTime = data.getGoalTime();
        dateDiff = data.getDaysSinceStart(LocalDate.now());
        streakCount = data.getStreakCount(LocalDate.now());
    }
    public void setupKPIs() {

        setupKPI1();
        setupKPI2();
        setupKPI3();
        setupKPI4();
    }
    public void setupProgressBar() {
        Drawable draw = getResources().getDrawable(R.drawable.customprogressbar);
        ProgressBar progress = findViewById(R.id.progressBar);
        progress.setMax(totalChapters);
        if (data.getGoalToday(LocalDate.now()) != -1) {
            progress.setSecondaryProgress(data.getGoalToday(LocalDate.now()));
        } else {
            progress.setSecondaryProgress(0);
        }
        progress.setProgress(data.getReadChapterCount());
        progress.invalidateDrawable(draw);
        progress.setProgressDrawable(draw);

        // TODO: NEED TO ACCOUNT FOR CONDITION THAT ALL CHAPTERS ARE FINISHED:
        //  SHOW CONGRATULATIONS OR SOMETHING EXCITING, MAYBE ENCOURAGE TO START AGAIN WITH A NEW GOAL
        //  if (data.getNextUp == null) {...}
    }
    public void setupHistoryChart() {

        initChart();
        setChart(LocalDate.now());
    }
    public void setupGoalView() {
        TextView goal = findViewById(R.id.goal);
        String goalString = "none";
        if (goalChapters == 0) {
            goalString = "Finish the Bible in " + goalTime + " months.";
        } else if (goalChapters > 0) {
            if (goalChapters == 1) {
                goalString = "Read 1 chapter every ";
            } else {
                goalString = "Read " + goalChapters + " chapters every ";
            }
            if (goalTime == 1) {
                goalString += "day.";
            } else {
                goalString += goalTime + " days.";
            }
        }
        goal.setText(goalString);
    }

    public void setupKPI1() {
        TextView viewKPI = findViewById(R.id.kpi1);
        String stringKPI = Integer.toString(data.getStreakCount(LocalDate.now()));
        viewKPI.setText(stringKPI);
    }
    public void setupKPI2() {
        int howManyReadDates = data.getDaysActive(LocalDate.now());
        int howManyDays;
        if (data.isReadDate(LocalDate.now())) {
            howManyDays = dateDiff;
        } else {
            howManyDays = dateDiff - 1;
        }


        double active = (float) howManyReadDates / (float) howManyDays * 100;
        String stringActive = Math.round(active) + "%";
        TextView viewKpi2 = findViewById(R.id.kpi2);
        viewKpi2.setText(stringActive);
    }
    public void setupKPI3() {
        TextView viewKPI = findViewById(R.id.kpi3);
        String stringAverage;
        if (dateDiff != 0) {
            double dailyAverage = (float) data.getReadChapterCount() / (float) dateDiff * 10;
            dailyAverage = Math.round(dailyAverage);
            stringAverage = dailyAverage/10 + "";
        } else {
            stringAverage = Integer.toString(data.getReadChapterCount());
        }
        viewKPI.setText(stringAverage);
    }
    public void setupKPI4() {
        double kpi4 = (float) data.getReadChapterCount() / (float) totalChapters * 1000;
        kpi4 = Math.round(kpi4);
        String stringKpi4 = kpi4/10 + "%";
        TextView viewKpi4 = findViewById(R.id.kpi4);
        viewKpi4.setText(stringKpi4);
    }

    public void goToDelete() {
        Intent intent = new Intent(this, DeleteConfirm.class);
        startActivity(intent);
    }
    public void goToSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
    public void goToAddChapter() {
        Intent intent = new Intent(this, AddChapters.class);
        startActivity(intent);
    }
    public void goToSetGoal() {
        Intent intent = new Intent(getApplicationContext(), SetGoal.class);
        startActivity(intent);
    }
    public void initChart() {
        chart = findViewById(R.id.chart);
        chart.setTouchEnabled(true);
        chart.setDrawMarkers(true);
        chart.getLegend().setEnabled(false);
        chart.getAxisLeft().setEnabled(false);
        chart.getAxisRight().setEnabled(false);
        chart.getXAxis().setEnabled(false);
        chart.getXAxis().setDrawGridLines(false);
        chart.getXAxis().setDrawAxisLine(false);
        chart.getXAxis().setAxisMinimum((float)-0.6);
        chart.getXAxis().setAxisMaximum((float)6.6);
        chart.getDescription().setText("");

        IMarker marker = new CustomMarkerView(getApplicationContext(),R.layout.marker);
        chart.setMarker(marker);
    }
    public void setChart(LocalDate date) {
        BubbleDataSet set = setValues(date);
        ArrayList<IBubbleDataSet> dataSets = new ArrayList<>();
        dataSets.add(set);
        BubbleData data = new BubbleData(dataSets);
        chart.highlightValue(0,-1);
        chart.invalidate();
        chart.setData(data);
    }
    public BubbleDataSet setValues(LocalDate date) {
        int weekDay = date.getDayOfWeek().getValue();
        int beforeDays = (weekDay == 7) ? 0 : weekDay;
        int afterDays = 6 - beforeDays;

        prevDate = date.minusDays(beforeDays + 1);
        nextDate = date.plusDays(afterDays + 1);
        String firstMonth = date.minusDays(beforeDays).getMonth().name();
        String lastMonth = date.plusDays(afterDays).getMonth().name();
        TextView title = findViewById(R.id.monthText);
        String months = (firstMonth.compareTo(lastMonth) != 0) ? firstMonth + "/" + lastMonth : firstMonth;
        title.setText(months);

        List<BubbleEntry> values = new ArrayList<>();
        Map<Entry,String> labels = new HashMap<>();
        int count = 0;
        for (int i = beforeDays; i > 0; i--) {
            values.add(setPoint(labels,date.minusDays(i),count));
            count++;
        }
        values.add(setPoint(labels,date,count));
        count++;
        for (int i = 1; i <= afterDays; i++) {
            values.add(setPoint(labels,date.plusDays(i),count));
            count++;
        }

        BubbleDataSet set = new BubbleDataSet(values,"");
        set.setValueFormatter(new MapValueFormatter(labels));
        set.setValueTextSize(14);
        set.setValueTextColor(getResources().getColor(R.color.lightGrey));
        set.setColor(getResources().getColor(R.color.colorPrimaryLight));
        if (set.getMaxSize() == 0) { set.setNormalizeSizeEnabled(false); }

        return set;
    }
    private BubbleEntry setPoint(Map<Entry,String> labels, LocalDate thisDate, int position) {
        BibleBookChapter[] thisDateRead = data.getDateChapters(thisDate);
        int chapterCount;
        String details = "";
        if (thisDateRead != null && thisDateRead.length > 0) {
            chapterCount = thisDateRead.length;
            for (int i = 0; i < thisDateRead.length; i++) {
                details = Bible.theBible[thisDateRead[i].getPosition()].getName() + " " + thisDateRead[i].getChapter() + "\n" + details;
            }
            if (chapterCount == 1) {
                details = chapterCount + " CHAPTER\n" + details;
            } else {
                details = chapterCount + " CHAPTERS\n" + details;
            }
            details = details.substring(0, details.length() - 1);
        } else {
            chapterCount = 0;
            details = "0 CHAPTERS";
        }
        BubbleEntry bubbleEntry = new BubbleEntry(position,1,chapterCount);
        labels.put(bubbleEntry, String.valueOf(thisDate.getDayOfMonth()));
        detailMap.put(bubbleEntry, details);
        return bubbleEntry;
    }
    public static BubbleChart getChart() { return chart; }
    public static Map<Entry, String> getDetailMap() { return detailMap; }

    public void onFabClick(View v) { goToAddChapter(); }
    public void onPrevClick(View v) { setChart(prevDate); }
    public void onNextClick(View v) { setChart(nextDate); }
    public void onSetGoal(View v) {
        goToSetGoal();
    }
}
