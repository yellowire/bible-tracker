package com.example.bibletracker;

import android.content.Context;
import android.graphics.Canvas;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BubbleChart;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;

import java.util.Map;

class CustomMarkerView extends MarkerView {

    private TextView markerContent;
    private BubbleChart chart = MainActivity.getChart();

    public CustomMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);

        markerContent = (TextView) findViewById(R.id.marker);
    }

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {

        Map<Entry, String> details = MainActivity.getDetailMap();
        if (details.containsKey(e)) {
            markerContent.setText(details.get(e));
        } else {
            markerContent.setText("(Nothing read)");
        }

        super.refreshContent(e, highlight);
    }

    @Override
    public void draw(Canvas canvas, float posX, float posY) {
        // take offsets into consideration
        int chartWidth = 0;
        float offsetX = getOffset().getX();
        posY=0;//fix at top
        float width = getWidth();

        if(chart != null) {
            chartWidth = chart.getWidth();
            posX += chartWidth / (float) 14;
        }
        if(posX + offsetX < 0) {
            offsetX = - posX - (chartWidth / (float) 7);
        } else if(posX + width + offsetX > chartWidth) {
            offsetX = 0 - width - (chartWidth / (float) 7);
        }
        posX += offsetX;
        // translate to the correct position and draw
        canvas.translate(posX, posY);
        draw(canvas);
        canvas.translate(-posX, -posY);
    }
}
