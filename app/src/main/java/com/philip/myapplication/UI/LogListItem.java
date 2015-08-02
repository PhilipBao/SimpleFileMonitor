package com.philip.myapplication.UI;


public class LogListItem {
    private final String FILENAME;
    private final String EVENT;
    private static long Id = 1;
    public final long ID;

    // constructor
    public LogListItem(String fileName, String event) {
        this.FILENAME = fileName;
        this.EVENT = event;
        this.ID = Id;
        Id++;
    }

    public String getFile() {
        return FILENAME;
    }

    public String getEvent() {
        return EVENT;
    }
}
