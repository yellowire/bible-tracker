package com.example.bibletracker;

import static com.example.bibletracker.Bible.theBible;

class BibleBook {
    public BibleBookChapter[] BibleBook;
    private String name;
    private int chapters;
    private int position;

    BibleBook(String title, int chaps, int pos) {
        BibleBook = new BibleBookChapter[chaps + 1];
        name = title;
        chapters = chaps;
        chaps++;
        position = pos;

        for (int i=1; i<chaps; i++) {
            BibleBook[i] = new BibleBookChapter(position,i);
        }
    }


    String getName() { return name; }
    int getChapters() { return chapters; }
    BibleBookChapter getThisChapter(int chapter) { BibleBook book = theBible[position]; BibleBookChapter chap = book.BibleBook[chapter]; return chap; }
}
