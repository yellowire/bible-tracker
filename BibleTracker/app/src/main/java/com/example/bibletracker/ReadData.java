package com.example.bibletracker;

import androidx.annotation.Nullable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.example.bibletracker.Bible.theBible;
import static java.time.temporal.ChronoUnit.DAYS;

class ReadData {

    private int totalChapterCount = 0;
    private LocalDate startDate;
    private BibleBookChapter startChapter = null;

    private int daysSinceStart = -1;
    private int streakCount = -1;
    private int daysActive = -1;
    private int readChapterCount = -1;
    private String goalType = null;
    private int goalTime = -1;
    private int goalChapters = -1;
    private int goalToday = -1;
    private BibleBookChapter nextUp = null;
    private BibleBookChapter bullpen = null;
    private BibleBookChapter lastReadChapter = null;
    private BibleBookChapter[] dateChapters = null;

    private DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    ReadData() {

        for (int i=0; i<66; i++) {
            int bookChapters = theBible[i].getChapters();
            totalChapterCount = totalChapterCount + bookChapters;
        };
        String startDateString = MainActivity.pref.getString("startDate",null);
        if (startDateString != null) {
            startDate = LocalDate.parse(startDateString,dateFormat);
        }
        int startBook = MainActivity.pref.getInt("startBook",-1);
        int startChap = MainActivity.pref.getInt("startChapter",-1);
        if (startBook != -1 && startChap != -1) {
            startChapter = theBible[startBook].getThisChapter(startChap);
        } else {
            startChapter = theBible[0].getThisChapter(1);
        }
    }
    private int countDateChapters(LocalDate date) {
        if (isReadDate(date)) {
            int count = 0;
            LocalDate checkDate;
            BibleBookChapter checkChapter = lastReadChapter;
            do {
                checkDate = getChapterReadDate(checkChapter.getPosition(),checkChapter.getChapter());
                if (checkDate != null) {
                    if (date.compareTo(checkDate) == 0) {
                        count++;
                        checkChapter = checkChapter.getPrevChapter();
                    } else if (date.compareTo(checkDate) > 0) {
                        return count;
                    } else {
                        checkChapter = checkChapter.getPrevChapter();
                    }
                } else {
                    return count;
                }
            } while (checkChapter != startChapter.getPrevChapter());
            return count;
        }
        return 0;
    }
    private LocalDate getChapterReadDate(int position, int chapter) {
        LocalDate chapterReadDate = null;
        String date = MainActivity.pref.getString(position + " " + chapter,null);
        if (date == null) {
            chapterReadDate = null;
        } else {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            chapterReadDate = LocalDate.parse(date, format);
        }
        return chapterReadDate;
    }
    private int getChaptersBetween(BibleBookChapter fromChapter, BibleBookChapter toChapter) {
        int chaptersBetween = 0;
        if (fromChapter == toChapter) {
            return 0;
        } else {
            do {
                chaptersBetween++;
                fromChapter = fromChapter.getNextChapter();
            } while (fromChapter != toChapter);
        }
        return chaptersBetween;
    }

    private void setDaysSinceStart(LocalDate date) {
        if (startDate != null && startDate.compareTo(date) != 0) {
            long daysBetween = DAYS.between(startDate,date);
            daysSinceStart = (int) daysBetween + 1;
        } else {
            daysSinceStart = 0;
        };
    }
    private void setReadChapterCount() {
        readChapterCount = 0;
        setLastReadChapter();
        if (lastReadChapter != null) {
            BibleBookChapter thisChapter = lastReadChapter;
            do {
                readChapterCount++;
                thisChapter = thisChapter.getPrevChapter();
            } while (MainActivity.pref.contains(thisChapter.getPosition() + " " + thisChapter.getChapter()));
        }
    }
    private void setStreakCount(LocalDate date) {
        setLastReadChapter();
        streakCount = 0;
        if (lastReadChapter != null) {
            if (isReadDate(date)) {
                do {
                    streakCount++;
                    date = date.minusDays(1);
                } while (isReadDate(date) && date.compareTo(startDate) >= 0);
            } else if (isReadDate(date.minusDays(1))) {
                date = date.minusDays(1);
                do {
                    streakCount++;
                    date = date.minusDays(1);
                } while (isReadDate(date) && date.compareTo(startDate) >= 0);
            }
        }
    }
    private void setDaysActive(LocalDate date) {
        if (date != null && startDate != null) {
            daysSinceStart = getDaysSinceStart(date);
            LocalDate thisDate;
            daysActive = 0;
            for (int i = 0; i < daysSinceStart; i++) {
                thisDate = startDate.plusDays(i);
                if (isReadDate(thisDate)) {
                    daysActive++;
                }
            }
        } else {
            daysActive = 0;
        }
    }
    private void setLastReadChapter() {
        String lastChapterReadString = MainActivity.pref.getString("lastReadChapter", null);
        if (lastChapterReadString != null) {
            String[] lastChapterSplit = lastChapterReadString.split(" ");
            if (lastChapterSplit.length == 2) {
                int position = Integer.parseInt(lastChapterSplit[0]);
                int lastChapterNumber = Integer.parseInt(lastChapterSplit[1]);
                lastReadChapter = theBible[position].getThisChapter(lastChapterNumber);
            } else {
                lastReadChapter = null;
            }
        } else {
            lastReadChapter = null;
        }
    }
    private void setDateChapters(LocalDate date) {
        int count = countDateChapters(date);

        if (count != 0) {
            BibleBookChapter[] chapters = new BibleBookChapter[count];
            BibleBookChapter checkChapter = lastReadChapter;

            while (date.compareTo(getChapterReadDate(checkChapter.getPosition(),checkChapter.getChapter())) != 0) {
                checkChapter = checkChapter.getPrevChapter();
            }
            BibleBookChapter thisChapter = checkChapter;

            for (int i = 0; i < count; i++) {
                chapters[i] = thisChapter;
                thisChapter = thisChapter.getPrevChapter();
            }

            dateChapters = chapters;
        } else {
            dateChapters = null;
        }
    }
    private void setNextUp() {
        setLastReadChapter();
        if (lastReadChapter != null) {
            nextUp = lastReadChapter.getNextChapter();
        } else {
            nextUp = startChapter;
        }
    }
    private void setGoalTime() {
        goalTime = MainActivity.pref.getInt("goalTime",0);
    }
    private void setGoalChapters() {
        goalChapters = MainActivity.pref.getInt("goalChapters",-1);
    }
    private void setGoalToday(LocalDate date) {
        setGoalChapters();
        setGoalTime();
        setDaysSinceStart(date);

        if (goalChapters == 0) {
            int totalDays = (int) DAYS.between(startDate,startDate.plusMonths(goalTime));
            double perDay = (float) totalChapterCount / (float) totalDays;
            double target = Math.round(perDay);
            goalToday = (int) target * daysSinceStart;
        } else if (goalChapters > 0) {
            double periods = (float) daysSinceStart / (float) goalTime;
            double target = goalChapters * periods;
            goalToday = (int) Math.round(target);
        } else {
            goalToday = 0;
        }
    }
    public void setBullpen() {
        setNextUp();
        if (nextUp != null) {
            bullpen = nextUp.getNextChapter();
        } else {
            bullpen = null;
        }
    }

    boolean isReadDate(LocalDate date) {
        setLastReadChapter();
        if (lastReadChapter != null) {
            LocalDate lastReadDate = getChapterReadDate(lastReadChapter.getPosition(),lastReadChapter.getChapter());
            if (lastReadDate != null) {

                if (date.compareTo(lastReadDate) == 0) {
                    return true;
                } else if (date.compareTo(lastReadDate) > 0) {
                    return false;
                } else {
                    BibleBookChapter checkThisChapter = lastReadChapter.getPrevChapter();
                    LocalDate checkChapterDate = getChapterReadDate(checkThisChapter.getPosition(),checkThisChapter.getChapter());
                    if (checkChapterDate != null) {
                        for (int i = 0; i < getChaptersBetween(startChapter,lastReadChapter); i++) {
                            if (date.compareTo(getChapterReadDate(checkThisChapter.getPosition(),checkThisChapter.getChapter())) == 0) {
                                return true;
                            } else if (date.compareTo(getChapterReadDate(checkThisChapter.getPosition(),checkThisChapter.getChapter())) > 0) {
                                return false;
                            } else {
                                checkThisChapter = checkThisChapter.getPrevChapter();
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return false;
    }
    int getTotalChapterCount() { return totalChapterCount; }
    int getDaysSinceStart(LocalDate date) { setDaysSinceStart(date); return daysSinceStart; }
    int getStreakCount(LocalDate date) { streakCount = 0; setStreakCount(date); return streakCount; }
    int getDaysActive(LocalDate date) { setDaysActive(date); return daysActive; }
    int getReadChapterCount() { setReadChapterCount(); return readChapterCount; }
    int getGoalTime() { setGoalTime(); return goalTime; }
    int getGoalChapters() { setGoalChapters(); return goalChapters; }
    int getGoalToday(LocalDate date) { setGoalToday(date); return goalToday; }
    String getGoalType() { return (getGoalChapters() == 0) ? "months" : "days"; }
    LocalDate getStartDate() { return startDate; }
    BibleBookChapter getStartChapter() { return startChapter; }
    BibleBookChapter getNextUp() { setNextUp(); return nextUp; }
    BibleBookChapter getBullpen() { setBullpen(); return bullpen; }
    @Nullable BibleBookChapter[] getDateChapters(LocalDate date) { setDateChapters(date); return dateChapters; }
}
