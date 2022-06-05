package com.example.peaceomind;

public class NotesData {
    String notesHeader;
    String notesDescription;
    String date;
    String time;

    public NotesData() {
    }

    public NotesData(String notesHeader, String notesDescription, String date, String time) {
        this.notesHeader = notesHeader;
        this.notesDescription = notesDescription;
        this.date=date;
        this.time=time;
    }

    public String getNotesHeader() {
        return notesHeader;
    }

    public void setNotesHeader(String notesHeading) {
        this.notesHeader = notesHeading;
    }

    public String getNotesDescription() {
        return notesDescription;
    }

    public void setNotesDescription(String notesDescription) {
        this.notesDescription = notesDescription;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}