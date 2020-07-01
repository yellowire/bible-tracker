package com.example.bibletracker;

import android.util.Log;

import java.time.LocalDate;

import static com.example.bibletracker.Bible.theBible;

class BibleBookChapter {
    private int chapter;
    private int position;

    BibleBookChapter(int pos, int chap) {
        position = pos;
        chapter = chap;
    }


    int getPosition() { return position; }
    int getChapter() { return chapter; }
    BibleBookChapter getNextChapter() {
        if (chapter == theBible[position].getChapters()) {
            if (position < 65) {
                return theBible[position+1].getThisChapter(1);
            } else {
                return theBible[0].getThisChapter(1);
            }
        } else {
            return theBible[position].getThisChapter(chapter+1);
        }
    }
    BibleBookChapter getPrevChapter() {
        if (chapter == 1) {
            if (position > 0) {
                return theBible[position-1].getThisChapter(theBible[position-1].getChapters());
            } else {
                return theBible[65].getThisChapter(theBible[65].getChapters());
            }
        } else {
            return theBible[position].getThisChapter(chapter-1);
        }
    }
}
