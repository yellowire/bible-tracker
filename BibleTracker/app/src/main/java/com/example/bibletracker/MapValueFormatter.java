package com.example.bibletracker;

import com.github.mikephil.charting.data.BubbleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.Map;

public class MapValueFormatter extends ValueFormatter {

    private final Map<Entry, String> labels;

    public MapValueFormatter(Map<Entry, String> labels) {
        this.labels = labels;
    }

    @Override
    public String getBubbleLabel(BubbleEntry bubbleEntry) {
        String label = labels.get(bubbleEntry);
        if (label == null) {
            return "";
        }
        return label;
    }

}